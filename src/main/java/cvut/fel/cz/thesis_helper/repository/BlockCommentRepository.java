package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.BlockComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockCommentRepository extends JpaRepository<BlockComment, Integer> {
}
