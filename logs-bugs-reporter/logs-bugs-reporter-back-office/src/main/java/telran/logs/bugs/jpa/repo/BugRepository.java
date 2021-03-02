package telran.logs.bugs.jpa.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.logs.bugs.dto.*;
import telran.logs.bugs.dto.enums.BugStatus;
import telran.logs.bugs.dto.enums.Seriousness;
import telran.logs.bugs.jpa.entities.Bug;

public interface BugRepository extends JpaRepository<Bug, Long> {

    List<Bug> findByProgrammerId(long programmerId);

    List<Bug> findByStatus(BugStatus status);

    List<Bug> findByStatusNotAndDateOpenBefore(BugStatus closed, LocalDate dateOpen);

    /**
     *
     */
    String emailBugsCounts = "select programmer.email as email, count(b) as count from Bug b right join b.programmer programmer  group by programmer.email"
            + " order by count(b) desc";
    @Query(emailBugsCounts)
    List<EmailBugsCount> emailBugsCounts();

    /**
     *
     */
    String programmersMostBugs = "select p.email from bugs b join programmers p on programmer_id = p.id "
            + "group by p.email order by count(b.id) desc limit :n_programmers";
    @Query(value = programmersMostBugs, nativeQuery = true)
    List<String> programmersMostBugs(@Param("n_programmers") int nProgrammers);

    /**
     *
     */
    String programmersLeastBugs = "select p.email from bugs b right join programmers p on b.programmer_id = p.id "
            + "group by p.email order by count(b.id) limit :n_programmers";
    @Query(value = programmersLeastBugs, nativeQuery = true)
    List<String> programmersLeastBugs(@Param("n_programmers") int nProgrammers);

    /**
     *
     */
    String seriousnessMostBugs = "select seriousness from bugs  "
            + "group by seriousness order by count(*) desc limit :n_types";
    @Query(value = seriousnessMostBugs, nativeQuery = true)
    List<Seriousness> seriousnessMostBugs(@Param("n_types") int nTypes);

    long countBySeriousness(Seriousness s); //returns count of bugs of the given seriousness

}
