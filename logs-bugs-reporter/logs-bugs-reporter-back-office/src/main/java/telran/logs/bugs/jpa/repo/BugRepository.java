package telran.logs.bugs.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.logs.bugs.jpa.entities.Bug;

import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findByProgrammerId(long programmerId);
}

