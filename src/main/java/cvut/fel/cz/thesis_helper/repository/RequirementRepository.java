package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Requirement;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
    Optional<Requirement> findById(@NonNull Integer id);
}
