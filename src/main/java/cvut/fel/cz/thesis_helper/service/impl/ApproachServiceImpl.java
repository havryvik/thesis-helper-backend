package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.dto.ApproachDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.exception.ApproachException;
import cvut.fel.cz.thesis_helper.exception.EnumException;
import cvut.fel.cz.thesis_helper.exception.UserExceptions;
import cvut.fel.cz.thesis_helper.model.*;
import cvut.fel.cz.thesis_helper.model.myEnums.BasicBlocksEnum;
import cvut.fel.cz.thesis_helper.model.myEnums.FinalMarkPattern;
import cvut.fel.cz.thesis_helper.model.myEnums.FulfilmentEnum;
import cvut.fel.cz.thesis_helper.repository.ApproachRepository;
import cvut.fel.cz.thesis_helper.repository.StudentRepository;
import cvut.fel.cz.thesis_helper.repository.SupervisorRepository;
import cvut.fel.cz.thesis_helper.repository.WeightsRepository;
import cvut.fel.cz.thesis_helper.security.SecurityUtils;
import cvut.fel.cz.thesis_helper.service.ApproachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApproachServiceImpl implements ApproachService {

    private final StudentRepository studentRepository;
    private final ApproachRepository approachRepository;
    private final SupervisorRepository supervisorRepository;
    private final WeightsRepository weightsRepository;

    @Autowired
    public ApproachServiceImpl(StudentRepository studentRepository, ApproachRepository approachRepository, SupervisorRepository supervisorRepository, WeightsRepository weightsRepository) {
        this.studentRepository = studentRepository;
        this.approachRepository = approachRepository;
        this.supervisorRepository = supervisorRepository;
        this.weightsRepository = weightsRepository;
    }

    @Override
    public ApproachDto getApproachById(Integer approachId) {
        Optional<Approach> approach = approachRepository.findById(approachId);
        if(approach.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        return new ApproachDto(approach.get());
    }

    @Override
    public void updateApproach(ApproachDto approachDto, Integer approachId) {
        Optional<Approach> approachOpt = approachRepository.findById(approachId);
        if(approachOpt.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        Approach approach = approachOpt.get();
        if(!Objects.equals(getCurrentUser().getId(), studentRepository.findByApproach(approach).getSupervisor().getId()))
            throw UserExceptions.accessDeniedException();
        Approach approachToUpdate;
        try{
            approachToUpdate = approachDto.toApproach();
            System.out.println("CONVERT TO APPROACH - OK");
        } catch (IllegalArgumentException ex){
            throw EnumException.badArgumentException();
        }
        if(!checkCorrectApproachTypes(approachToUpdate))
            throw EnumException.badArgumentException();
        System.out.println("SET MARK - OK");
        approach.setFulfilmentEvaluation(approachToUpdate.getFulfilmentEvaluation());
        approach.setCoefficient(approachToUpdate.getCoefficient());
        approach.setAutoFulfilment(approachToUpdate.getAutoFulfilment());
        approach.setCriterionEvaluation(approachToUpdate.getCriterionEvaluation());
        approach.setBasicBlocksEvaluation(approachToUpdate.getBasicBlocksEvaluation());
        approach.setFinalMarkPattern(getFinalMarkPattern(approachToUpdate));
        if(approach.getBasicBlocksEvaluation()!=BasicBlocksEnum.weight&&approach.getWeights()!=null){
            weightsRepository.delete(approach.getWeights());
            approach.setWeights(null);
        }
        approachRepository.save(approach);
    }

    @Override
    public ApproachDto getApproachByStudentId(Integer studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if(student.isEmpty())
            throw UserExceptions.userNotFoundById(studentId);
        Student student1 =student.get();
        if (!Objects.equals(getCurrentUser().getId(), student1.getSupervisor().getId()))
            throw UserExceptions.accessDeniedException();
        if(student1.getApproach()==null)
            throw ApproachException.approachDoesNotConfigured(studentId);
        return new ApproachDto(student1.getApproach());
    }

    @Override
    public void addApproachToStudent(ApproachDto approachDto, Integer studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty())
            throw UserExceptions.userNotFoundById(studentId);
        Student student = studentOpt.get();
        if (!Objects.equals(getCurrentUser().getId(), student.getSupervisor().getId()))
            throw UserExceptions.accessDeniedException();
        Approach approach;
        try{
            approach = approachDto.toApproach();
        } catch (IllegalArgumentException ex){
            throw EnumException.badArgumentException();
        }
        if(!checkCorrectApproachTypes(approach))
            throw EnumException.badArgumentException();
        approach.setFinalMarkPattern(getFinalMarkPattern(approach));
        student.setApproach(approach);
        studentRepository.save(student);
    }

    @Override
    public void addWeightToApproach(Integer approachId, Weights weight) {
        Optional<Approach> approachOpt = approachRepository.findById(approachId);
        if(approachOpt.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        Approach approach = approachOpt.get();
        if(approach.getBasicBlocksEvaluation()!=BasicBlocksEnum.weight)
            throw ApproachException.approachDoesNotSupportWeights();
        Weights currentWeights = approach.getWeights();
        if(currentWeights!=null){
            currentWeights.setActivity(weight.getActivity());
            currentWeights.setCitation(weight.getCitation());
            currentWeights.setLanguageLevel(weight.getLanguageLevel());
            currentWeights.setProfessionalLevel(weight.getProfessionalLevel());
            currentWeights.setMaxPoints(weight.getMaxPoints());
            weightsRepository.save(currentWeights);
            return;
        }
        approach.setWeights(weight);
        approachRepository.save(approach);
    }

    @Override
    public Weights getApproachWeights(Integer approachId) {
        Optional<Approach> approachOpt = approachRepository.findById(approachId);
        if(approachOpt.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        Approach approach = approachOpt.get();
        if(approach.getWeights()==null)
            throw ApproachException.weightsDoesNotExists();
        return approach.getWeights();
    }

    @Override
    public List<RequirementDto> getExtraRequirementsByApproachId(Integer approachId) {
        Optional<Approach> approachOpt = approachRepository.findById(approachId);
        if (approachOpt.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        Approach approach = approachOpt.get();
        Student student = studentRepository.findByApproach(approach);
        Evaluation evaluation = student.getEvaluation();
        List<Requirement> requirements = evaluation.getRequirements();
        if (requirements!=null){
            List<Requirement> tmpReq = requirements.stream().filter(requirement -> requirement.getBlockNumber()==null).collect(Collectors.toList());
            return tmpReq.stream().map(RequirementDto::new).collect(Collectors.toList());
        }
        return null;
    }

    public boolean checkCorrectApproachTypes(Approach approach){
        FulfilmentEnum fulfilment = approach.getFulfilmentEvaluation();
        BasicBlocksEnum basicBlocks = approach.getBasicBlocksEvaluation();
        if(approach.getAutoFulfilment()&&!approach.getCriterionEvaluation()){
            System.out.println("WRONG 1");
            return false;
        }
        switch (fulfilment){
            case points:{
                if (basicBlocks== BasicBlocksEnum.marks||basicBlocks== BasicBlocksEnum.percent){
                    System.out.println("WRONG 2");
                    return false;}
                break;
            }
            case percent:{
                if(!(basicBlocks== BasicBlocksEnum.percent)){
                    System.out.println("WRONG 3");
                    return false;}
                break;
            }
        }
        return true;
    }

    public FinalMarkPattern getFinalMarkPattern(Approach approach){
        FulfilmentEnum fulfilment = approach.getFulfilmentEvaluation();
        BasicBlocksEnum basicBlocks = approach.getBasicBlocksEvaluation();
        if (approach.getCoefficient()&&(basicBlocks== BasicBlocksEnum.points||basicBlocks== BasicBlocksEnum.weight))
            return FinalMarkPattern.sumC;
        if (fulfilment==FulfilmentEnum.words&&(basicBlocks== BasicBlocksEnum.points||basicBlocks== BasicBlocksEnum.weight))
            return FinalMarkPattern.sumIncr;
        if (fulfilment==FulfilmentEnum.points&&(basicBlocks== BasicBlocksEnum.points||basicBlocks== BasicBlocksEnum.weight))
            return FinalMarkPattern.sumAp;
        return FinalMarkPattern.avgIncr;
    }

    private Account getCurrentUser(){
        final Account currentUser = SecurityUtils.getCurrentUser();
        assert currentUser != null;
        return currentUser;
    }
}
