package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.dto.EvaluationDto;
import cvut.fel.cz.thesis_helper.dto.StudentDto;
import cvut.fel.cz.thesis_helper.dto.SupervisorDto;
import cvut.fel.cz.thesis_helper.exception.EvaluationException;
import cvut.fel.cz.thesis_helper.exception.UserExceptions;
import cvut.fel.cz.thesis_helper.model.*;
import cvut.fel.cz.thesis_helper.repository.StudentRepository;
import cvut.fel.cz.thesis_helper.repository.SupervisorRepository;
import cvut.fel.cz.thesis_helper.security.SecurityUtils;
import cvut.fel.cz.thesis_helper.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupervisorServiceImpl implements SupervisorService {

    private Account getCurrentUser(){
        final Account currentUser = SecurityUtils.getCurrentUser();
        assert currentUser != null;
        return currentUser;
    }

    private final SupervisorRepository supervisorRepository;
    private  final StudentRepository studentRepository;
    private final StudentServiceImpl studentService;

    @Autowired
    public SupervisorServiceImpl(SupervisorRepository supervisorRepository, StudentRepository studentRepository, StudentServiceImpl studentService) {
        this.supervisorRepository = supervisorRepository;
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }

    @Override
    public Supervisor register(Supervisor supervisor) {
        if(supervisorRepository.findByEmail(supervisor.getEmail())!=null)
            throw UserExceptions.userAlreadyExists(supervisor.getEmail());
        return supervisorRepository.save(supervisor);
    }

    @Override
    public List<StudentDto> getStudents() {
        Account account = getCurrentUser();
        Supervisor supervisor = supervisorRepository.findByEmail(account.getEmail());
        if (supervisor == null)
            throw UserExceptions.accessDeniedException();
        List<Student> students = supervisor.getStudents();
        return students.stream().map(StudentDto::new).collect(Collectors.toList());
    }

    @Override
    public Supervisor findByEmail(String email) {
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        if (supervisor==null)
            throw UserExceptions.supervisorNotFoundException(email);
        return supervisor;
    }

    @Override
    public Supervisor findById(Integer id) {
        Optional<Supervisor> tmpSupervisor = supervisorRepository.findById(id);
        if (tmpSupervisor.isEmpty())
            throw UserExceptions.userNotFoundById(id);
        return tmpSupervisor.get();
    }

    @Override
    public void addStudent(String email) {
        Student student = studentRepository.findByEmail(email);
        if(student==null)
            throw UserExceptions.studentNotFoundException(email);
        Supervisor supervisor = supervisorRepository.findByEmail(getCurrentUser().getEmail());
        if(student.getSupervisor()!=null && student.getSupervisor().equals(supervisor))
            throw UserExceptions.studentIsAlreadyAdded(email);
        supervisor.getStudents().add(student);
        student.setSupervisor(supervisor);
        studentRepository.save(student);
        supervisorRepository.save(supervisor);
    }

    @Override
    public void removeStudent(String email) {
        Student student = studentRepository.findByEmail(email);
        if(student==null)
            throw UserExceptions.studentNotFoundException(email);
        Supervisor supervisor = supervisorRepository.findByEmail(getCurrentUser().getEmail());
        if(!student.getSupervisor().equals(supervisor))
            throw UserExceptions.studentIsNotAdded(email);
        student.setSupervisor(null);
        studentRepository.save(student);
    }

    @Override
    public EvaluationDto getStudentEval(Integer studentId) {
        Student student = studentService.findById(studentId);
        Evaluation evaluation = student.getEvaluation();
        if (evaluation==null)
            throw EvaluationException.studentDoesNotHaveEvaluation();
        return new EvaluationDto(evaluation);
    }

    @Override
    public SupervisorDto getSupervisorByStudentId(Integer studentId) {
        Student student = studentService.findById(studentId);
        if (student.getSupervisor()!=null)
            return new SupervisorDto(student.getSupervisor());
        throw new UserExceptions("Student does not have a supervisor");
    }
}
