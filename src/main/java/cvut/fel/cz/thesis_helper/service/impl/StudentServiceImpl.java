package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.exception.UserExceptions;
import cvut.fel.cz.thesis_helper.model.*;
import cvut.fel.cz.thesis_helper.model.myEnums.BasicBlocksEnum;
import cvut.fel.cz.thesis_helper.model.myEnums.FinalMarkPattern;
import cvut.fel.cz.thesis_helper.model.myEnums.FulfilmentEnum;
import cvut.fel.cz.thesis_helper.repository.ApproachRepository;
import cvut.fel.cz.thesis_helper.repository.EvaluationRepository;
import cvut.fel.cz.thesis_helper.repository.RoleRepository;
import cvut.fel.cz.thesis_helper.repository.StudentRepository;
import cvut.fel.cz.thesis_helper.security.SecurityUtils;
import cvut.fel.cz.thesis_helper.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ApproachRepository approachRepository;
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ApproachRepository approachRepository, EvaluationRepository evaluationRepository) {
        this.studentRepository = studentRepository;
        this.approachRepository = approachRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public Student register(Student student) {
        System.out.println("REST register "+student);
        if(studentRepository.findByEmail(student.getEmail())!=null)
            throw UserExceptions.userAlreadyExists(student.getEmail());
        Approach approach = new Approach();
        approach.setFulfilmentEvaluation(FulfilmentEnum.words);
        approach.setAutoFulfilment(false);
        approach.setCoefficient(false);
        approach.setCriterionEvaluation(false);
        approach.setBasicBlocksEvaluation(BasicBlocksEnum.marks);
        approach.setFinalMarkPattern(FinalMarkPattern.avgIncr);
        approachRepository.save(approach);
        student.setApproach(approach);
        Evaluation evaluation = new Evaluation();
        evaluationRepository.save(evaluation);
        student.setEvaluation(evaluation);
        return studentRepository.save(student);
    }

    @Override
    public Student findById(Integer id) {
        Optional<Student> tmpStudent = studentRepository.findById(id);
        if (tmpStudent.isEmpty())
            throw UserExceptions.userNotFoundById(id);
        return tmpStudent.get();
    }

    @Override
    public List<RequirementDto> getExtraRequirements(Integer studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty())
            throw UserExceptions.userNotFoundById(studentId);
        Student student = studentOptional.get();
        return student.getEvaluation().getRequirements().stream().map(RequirementDto::new).collect(Collectors.toList());
    }
}
