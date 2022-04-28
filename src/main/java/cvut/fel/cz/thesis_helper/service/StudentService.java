package cvut.fel.cz.thesis_helper.service;

import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.model.Supervisor;

import java.util.List;

public interface StudentService {
    Student register(Student student);
    Student findByEmail(String email);
    Student findById(Integer id);
    Supervisor getTeacher(Student student);

    List<RequirementDto> getExtraRequirements(Integer studentId);
}
