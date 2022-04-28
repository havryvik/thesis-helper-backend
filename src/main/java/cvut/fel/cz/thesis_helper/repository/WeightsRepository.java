package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Weights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;

@Repository
public interface WeightsRepository extends JpaRepository<Weights, Integer> {
}
