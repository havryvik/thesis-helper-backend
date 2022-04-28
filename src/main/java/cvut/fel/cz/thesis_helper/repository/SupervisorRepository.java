package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupervisorRepository extends JpaRepository<Supervisor, Integer> {
    Supervisor findByEmail(String email);
    Optional<Supervisor> findById(Integer id);
}