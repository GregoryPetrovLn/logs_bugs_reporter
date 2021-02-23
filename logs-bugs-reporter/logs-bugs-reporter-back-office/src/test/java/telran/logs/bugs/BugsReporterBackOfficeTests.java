package telran.logs.bugs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import telran.logs.bugs.dto.bug.BugResponseDto;
import telran.logs.bugs.dto.enums.BugStatus;
import telran.logs.bugs.dto.enums.OpenningMethod;
import telran.logs.bugs.dto.enums.Seriousness;
import telran.logs.bugs.jpa.repo.ArtifactRepository;
import telran.logs.bugs.jpa.repo.BugRepository;
import telran.logs.bugs.jpa.repo.ProgrammerRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static telran.logs.bugs.api.LogsInfoApi.BUGS_CONTROLLER;
import static telran.logs.bugs.api.LogsInfoApi.PROGRAMMERS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
public class BugsReporterBackOfficeTests {
    private static final String CLASS_PATH_SQL = "classpath:fillDB.sql";

    WebTestClient webTestClient;
    BugRepository bugRepository;
    ProgrammerRepository programmerRepository;
    ArtifactRepository artifactRepository;

    @Autowired
    public BugsReporterBackOfficeTests(BugRepository bugRepository, ProgrammerRepository programmerRepository, WebTestClient webTestClient, ArtifactRepository artifactRepository) {
        this.bugRepository = bugRepository;
        this.programmerRepository = programmerRepository;
        this.artifactRepository = artifactRepository;
        this.webTestClient = webTestClient;
    }

    @Test
    void testSetup() {
        assertNotNull(webTestClient);
        assertNotNull(bugRepository);
        assertNotNull(programmerRepository);
        assertNotNull(artifactRepository);
    }

    @Test
    @Sql(scripts = CLASS_PATH_SQL)
    void testGetProgrammersBugsById() {
        List<BugResponseDto> expected = new ArrayList<>();

        expected.add(new BugResponseDto(1, Seriousness.BLOCKING, "BLOCKING bug description", LocalDate.of(2020, 1, 1), 1,
                null, BugStatus.ASSIGNED, OpenningMethod.MANUAL));
        expected.add(new BugResponseDto(2, Seriousness.CRITICAL, "CRITICAL bug description", LocalDate.of(2021, 2, 2), 1,
                null, BugStatus.OPENED, OpenningMethod.AUTOMATIC));
        expected.add(new BugResponseDto(3, Seriousness.MINOR, "MINOR bug description", LocalDate.of(2021, 3, 3), 1,
                LocalDate.of(2021, 3, 10), BugStatus.CLOSED, OpenningMethod.AUTOMATIC));
//        expected.add(new BugResponseDto(4, Seriousness.CRITICAL, "CRITICAL bug description",
//                LocalDate.of(2019, 4, 4), 2, null, BugStatus.OPENED, OpenningMethod.AUTOMATIC));
//        expected.add(new BugResponseDto(5, Seriousness.COSMETIC, "COSMETIC bug description",
//                LocalDate.of(2020, 5, 5), 3, LocalDate.of(2020, 5, 12),
//                BugStatus.CLOSED, OpenningMethod.MANUAL));

        webTestClient.get().uri(BUGS_CONTROLLER + PROGRAMMERS + "/1")
                .exchange().expectStatus().isOk()
                .expectBodyList(BugResponseDto.class).isEqualTo(expected);

        webTestClient.get().uri(BUGS_CONTROLLER+PROGRAMMERS+"/12345")
                .exchange().expectStatus().isOk()
                .expectBodyList(BugResponseDto.class).isEqualTo(new ArrayList<>());

    }


}
