package telran.logs.bugs.interfaces;

import telran.logs.bugs.dto.artifact.ArtifactDto;
import telran.logs.bugs.dto.bug.*;
import telran.logs.bugs.dto.interfaces.EmailBugsCount;
import telran.logs.bugs.dto.programmer.ProgrammerDto;

import java.util.List;

public interface BugsReporter {
    ProgrammerDto addProgrammer(ProgrammerDto programmerDto);

    ArtifactDto addArtifact(ArtifactDto artifactDto);

    BugResponseDto openBug(BugDto bugDto);

    BugResponseDto openAndAssignBug(BugAssignDto bugDto);

    void assignBug(AssignBugData assignBugData);

    List<BugResponseDto> getNoneAssignedBugs();

    void closeBug(CloseBugData closeData);

    List<BugResponseDto> getUnclosedBugsMoreDuration(int days);

    List<BugResponseDto> getBugsByProgrammerId(long programmerId);

    List<EmailBugsCount> getEmailBugsCount();

    List<String> getProgrammersMostBugs(int nProgrammers);

    List<String> getProgrammersLeastBugs(int nProgrammers);

}
