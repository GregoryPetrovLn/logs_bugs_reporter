package telran.logs.bugs.impl;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import telran.logs.bugs.dto.artifact.ArtifactDto;
import telran.logs.bugs.dto.bug.*;
import telran.logs.bugs.dto.enums.BugStatus;
import telran.logs.bugs.dto.enums.OpenningMethod;
import telran.logs.bugs.dto.interfaces.EmailBugsCount;
import telran.logs.bugs.dto.programmer.ProgrammerDto;
import telran.logs.bugs.interfaces.*;
import telran.logs.bugs.jpa.entities.*;
import telran.logs.bugs.jpa.repo.*;


import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BugsReporterImpl implements BugsReporter {
    BugRepository bugRepository;
    ArtifactRepository artifactRepository;
    ProgrammerRepository programmerRepository;

    @Autowired
    public BugsReporterImpl(BugRepository bugRepository, ArtifactRepository artifactRepository, ProgrammerRepository programmerRepository) {
        this.bugRepository = bugRepository;
        this.artifactRepository = artifactRepository;
        this.programmerRepository = programmerRepository;
    }
    //=======================================================
    //=======================================================
    //=======================================================

    @Override
    @Transactional
    public ProgrammerDto addProgrammer(ProgrammerDto programmerDto) {
        programmerRepository.save(new Programmer(programmerDto.id, programmerDto.name, programmerDto.email));
        return programmerDto;
    }

    @Override
    public ArtifactDto addArtifact(ArtifactDto artifactDto) {
        //FIXME
        Programmer programmer = getProgrammerById(artifactDto.programmerId);
        artifactRepository.save(new Artifact(artifactDto.artifactId, programmer));
        return null;
    }



    @Override
    public BugResponseDto openBug(BugDto bugDto) {
        LocalDate dateOpen = bugDto.dateOpen != null ? bugDto.dateOpen : LocalDate.now();

        Bug bug = new Bug(bugDto.description,
                dateOpen,
                null,
                BugStatus.OPENED,
                bugDto.seriousness,
                OpenningMethod.MANUAL,
                null);

        bugRepository.save(bug);
        return toBugResponseDto(bug);
    }


    @Override
    @Transactional
    public BugResponseDto openAndAssignBug(BugAssignDto bugDto) {
        Programmer programmer = programmerRepository.findById(bugDto.programmerId).orElse(null);
        LocalDate dateOpen = bugDto.dateOpen != null ? bugDto.dateOpen : LocalDate.now();
        Bug bug = new Bug(bugDto.description,
                dateOpen,
                null,
                BugStatus.ASSIGNED,
                bugDto.seriousness,
                OpenningMethod.MANUAL,
                programmer);
        bug = bugRepository.save(bug);
        return toBugResponseDto(bug);
    }

    @Override
    @Transactional
    public void assignBug(AssignBugData assignBugData) {
        Bug bug = bugRepository.findById(assignBugData.bugId).orElse(null);
        bug.setDescription(bug.getDescription() + "\nAssignment Description: " + assignBugData.description);
        Programmer programmer = programmerRepository.findById(assignBugData.programmerId).orElse(null);
        bug.setStatus(BugStatus.ASSIGNED);
        bug.setProgrammer(programmer);
    }

    @Override
    public List<BugResponseDto> getNoneAssignedBugs() {
        return null;
    }

    @Override
    public void closeBug(CloseBugData closeData) {

    }

    @Override
    public List<BugResponseDto> getUnclosedBugsMoreDuration(int days) {
        return null;
    }

    @Override
    public List<BugResponseDto> getBugsByProgrammerId(long programmerId) {
        List<Bug> bugs = bugRepository.findByProgrammerId(programmerId);
        return bugs.isEmpty() ? new LinkedList<>() : toListBugResponseDto(bugs);
    }



    @Override
    public List<EmailBugsCount> getEmailBugsCount() {
        return null;
    }

    @Override
    public List<String> getProgrammersMostBugs(int nProgrammers) {
        return null;
    }

    @Override
    public List<String> getProgrammersLeastBugs(int nProgrammers) {
        return null;
    }

    //=========================================================
    //=========================================================
    //=========================================================

    /**
     * @param bug
     * @return
     */
    private BugResponseDto toBugResponseDto(Bug bug) {
        Programmer programmer = bug.getProgrammer();
        long programmerId = programmer == null ? 0 : programmer.getId();

        return new BugResponseDto(bug.getId(), bug.getSeriousness(),
                bug.getDescription(),
                bug.getDateOpen(),
                programmerId,
                bug.getDateClose(),
                bug.getStatus(),
                bug.getOpenningMethod());
    }

    /**
     *
     * @param bugs
     * @return
     */
    private List<BugResponseDto> toListBugResponseDto(List<Bug> bugs) {
        return bugs.stream().map(this::toBugResponseDto).collect(Collectors.toList());
    }

    /**
     *
     * @param programmerId
     * @return
     */
    private Programmer getProgrammerById(long programmerId) {
        //FIXME
        return new Programmer();
    }
}
