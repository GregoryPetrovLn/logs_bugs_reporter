package telran.logs.bugs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.jdbc.Sql;

import telran.logs.bugs.dto.enums.BugStatus;
import telran.logs.bugs.dto.enums.LogType;
import telran.logs.bugs.dto.enums.OpenningMethod;
import telran.logs.bugs.dto.enums.Seriousness;
import telran.logs.bugs.dto.log.LogDto;
import telran.logs.bugs.jpa.entities.*;
import telran.logs.bugs.repo.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Import(TestChannelBinderConfiguration.class)
public class OpenningBugsTest {
	public static final String CLASS_PATH_SQL = "classpath:programmerArtifact.sql";
    private static final @NotEmpty String EXISTING_ARTIFACT = "artifact1";
    private static final String RESULT = "Something wrong";
    @Autowired
    BugsRepo bugsRepo;
    @Autowired
    InputDestination input;
    LogDto logExceptions[] = {
            new LogDto(new Date(), LogType.AUTHENTICATION_EXCEPTION, EXISTING_ARTIFACT,
                    0, RESULT),
            new LogDto(new Date(), LogType.AUTHORIZATION_EXCEPTION, EXISTING_ARTIFACT,
                    0, RESULT),
            new LogDto(new Date(), LogType.SERVER_EXCEPTION, EXISTING_ARTIFACT,
                    0, RESULT),
            new LogDto(new Date(), LogType.BAD_REQUEST_EXCEPTION, "no exist",
                    0, RESULT)
    };
    Programmer prExpected = new Programmer(123, "Moshe", "moshe@gmail.com");
    Bug bugs[] = {
            new Bug("AUTHENTICATION_EXCEPTION Something wrong", LocalDate.now(),
                    null, BugStatus.ASSIGNED, Seriousness.BLOCKING, OpenningMethod.AUTOMATIC, prExpected),
            new Bug("AUTHORIZATION_EXCEPTION Something wrong", LocalDate.now(),
                    null, BugStatus.ASSIGNED, Seriousness.CRITICAL, OpenningMethod.AUTOMATIC, prExpected),
            new Bug("SERVER_EXCEPTION Something wrong", LocalDate.now(),
                    null, BugStatus.ASSIGNED, Seriousness.CRITICAL, OpenningMethod.AUTOMATIC, prExpected),
            new Bug("BAD_REQUEST_EXCEPTION Something wrong", LocalDate.now(),
                    null, BugStatus.OPENED, Seriousness.MINOR, OpenningMethod.AUTOMATIC, null),
    };

    @Test
    @Sql(scripts = CLASS_PATH_SQL)
    void programmerAuthentication() {
        runTest(0);

    }

    @Test
    @Sql(scripts = CLASS_PATH_SQL)
    void programmerServer() {
        runTest(2);

    }

    @Test
    @Sql(scripts = CLASS_PATH_SQL)
    void noProgrammerBadRequest() {
        runTest(3);

    }

    @Test
    @Sql(scripts = CLASS_PATH_SQL)
    void programmerAuthorization() {
        runTest(1);

    }

    private void runTest(int index) {
        input.send(new GenericMessage<LogDto>(logExceptions[index]));
        assertEquals(bugs[index], bugsRepo.findAll().get(0));
    }
}
