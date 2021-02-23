package telran.logs.bugs.controllers;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import telran.logs.bugs.dto.artifact.ArtifactDto;
import telran.logs.bugs.dto.bug.*;
import telran.logs.bugs.dto.interfaces.EmailBugsCount;
import telran.logs.bugs.dto.programmer.ProgrammerDto;
import telran.logs.bugs.impl.BugsReporterImpl;
import java.util.List;
import static telran.logs.bugs.api.LogsInfoApi.*;

@RequestMapping(BUGS_CONTROLLER)
@RestController
public class BugsController {
    Logger LOG = LoggerFactory.getLogger(BugsController.class);
    @Autowired
    BugsReporterImpl bugsReporter;


    @PostMapping(PROGRAMMERS)
    public ProgrammerDto addProgrammer(@RequestBody ProgrammerDto programmerDto) {
        ProgrammerDto dto = bugsReporter.addProgrammer(programmerDto);
        LOG.debug("received ProgrammerDto {} ", dto);
        return dto;
    }

    @PostMapping(ARTIFACTS)
    public ArtifactDto addArtifact(@RequestBody ArtifactDto artifactDto) {
        //FIXME ??
        ArtifactDto dto = bugsReporter.addArtifact(artifactDto);
        LOG.debug("received ArtifactDto {} ", dto);
        return dto;
    }

    @PostMapping(OPEN)
    public BugResponseDto openBug(@RequestBody BugDto bugDto) {
        BugResponseDto bug = bugsReporter.openBug(bugDto);
        LOG.debug("received BugResponseDto {} ", bug);
        return bug;
    }

    @PostMapping(OPEN + ASSIGN)
    public BugResponseDto openAndAssignBug(@RequestBody BugAssignDto bugAssignDto) {
        BugResponseDto dto = bugsReporter.openAndAssignBug(bugAssignDto);
        LOG.debug("received BugResponseDto {} ", dto);
        return dto;
    }

    @PostMapping(ASSIGN)
    public void assignBug(@RequestBody AssignBugData assignBugData) {
        bugsReporter.assignBug(assignBugData);
        LOG.debug("assigned bug {}", assignBugData);
    }

    @GetMapping(PROGRAMMERS + ID)
    public List<BugResponseDto> getBugsByProgrammerId(@PathVariable(name = "id") long programmerId) {
        List<BugResponseDto> dto = bugsReporter.getBugsByProgrammerId(programmerId);
        LOG.debug("received list dto {}", dto);
        return dto;
    }
//
//    @PostMapping()
//    public void closeBug(CloseBugData closeData) {
//        //TODO
//    }
//
//    @PostMapping()
//    public List<BugResponseDto> getNoneAssignedBugs() {
//        //TODO
//        return null;
//    }
//
//    @PostMapping()
//    public List<BugResponseDto> getUnclosedBugsMoreDuration(int days) {
//        //TODO
//        return null;
//    }
//
//    @PostMapping()
//    public List<EmailBugsCount> getEmailBugsCount() {
//        //TODO
//        return null;
//    }
//
//    @PostMapping()
//    public List<String> getProgrammersMostBugs(int nProgrammers) {
//        //TODO
//        return null;
//    }
//
//    @PostMapping()
//    public List<String> getProgrammersLeastBugs(int nProgrammers) {
//        //TODO
//        return null;
//    }


}
