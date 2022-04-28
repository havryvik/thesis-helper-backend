package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Approach;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApproachRepository extends JpaRepository<Approach, Integer> {
    Optional<Approach> findById(@NonNull Integer id);

}
