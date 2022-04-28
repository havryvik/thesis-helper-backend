package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Approach;
import cvut.fel.cz.thesis_helper.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
    Student findByApproach(Approach approach);
}
