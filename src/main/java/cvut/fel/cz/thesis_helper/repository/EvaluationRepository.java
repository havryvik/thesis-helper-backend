package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
}
