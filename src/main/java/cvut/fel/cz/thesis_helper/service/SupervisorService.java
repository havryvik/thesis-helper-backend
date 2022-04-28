package cvut.fel.cz.thesis_helper.service;

import cvut.fel.cz.thesis_helper.dto.ApproachDto;
import cvut.fel.cz.thesis_helper.dto.EvaluationDto;
import cvut.fel.cz.thesis_helper.dto.StudentDto;
import cvut.fel.cz.thesis_helper.model.Approach;
import cvut.fel.cz.thesis_helper.model.Supervisor;

import java.util.List;

public interface SupervisorService {
    Supervisor register(Supervisor supervisor);
    List<StudentDto> getStudents();
    Supervisor findByEmail(String email);
    Supervisor findById(Integer id);
    void addStudent(String email);
    void removeStudent(String email);

    EvaluationDto getStudentEval(Integer studentId);
}
