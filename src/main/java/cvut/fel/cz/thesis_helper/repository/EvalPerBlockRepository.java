package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.EvalPerBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvalPerBlockRepository extends JpaRepository<EvalPerBlock, Integer> {
}
