package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.dto.*;
import cvut.fel.cz.thesis_helper.exception.ApproachException;
import cvut.fel.cz.thesis_helper.exception.EvaluationException;
import cvut.fel.cz.thesis_helper.model.*;
import cvut.fel.cz.thesis_helper.repository.*;
import cvut.fel.cz.thesis_helper.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final ApproachRepository approachRepository;
    private final StudentRepository studentRepository;
    private final EvaluationRepository evaluationRepository;
    private final RequirementRepository requirementRepository;
    private final CommentRepository commentRepository;
    private final CriterionRepository criterionRepository;
    private final EvalPerBlockRepository evalPerBlockRepository;

    @Autowired
    public EvaluationServiceImpl(ApproachRepository approachRepository, StudentRepository studentRepository, EvaluationRepository evaluationRepository, RequirementRepository requirementRepository, CommentRepository commentRepository, CriterionRepository criterionRepository, EvalPerBlockRepository evalPerBlockRepository) {
        this.approachRepository = approachRepository;
        this.studentRepository = studentRepository;
        this.evaluationRepository = evaluationRepository;
        this.requirementRepository = requirementRepository;
        this.commentRepository = commentRepository;
        this.criterionRepository = criterionRepository;
        this.evalPerBlockRepository = evalPerBlockRepository;
    }

    @Override
    public void addRequirementToEvaluationByApproachId(List<RequirementDto> requirementDtos, Integer approachId) {
        Optional<Approach> approachOpt = approachRepository.findById(approachId);
        if (approachOpt.isEmpty())
            throw ApproachException.approachByIdNotFound(approachId);
        Approach approach = approachOpt.get();
        Student student = studentRepository.findByApproach(approach);
        Evaluation evaluation = student.getEvaluation();
        requirementDtos.forEach(requirementDto ->{
            Requirement requirement = requirementDto.toRequirement();
            requirement.setEvaluation(evaluation);
            requirementRepository.save(requirement);
        });
    }


    @Override
    public void updateEvaluation(Integer evaluationId, EvaluationDto evaluationDto) {
        Evaluation evaluation = findById(evaluationId);
        FinalComment finalComment = new FinalComment();
        finalComment.setText(evaluationDto.getFinalComment());
        commentRepository.save(finalComment);
        evaluation.setFinalComment(finalComment);
        evaluation.setFinalMark(evaluationDto.getFinalMark());
        evaluation.setCoefficient(evaluationDto.getCoefficient());
        evaluation.setIncrement(evaluationDto.getIncrement());
        evaluation.setActualMark(evaluationDto.getActualMark());
        evaluationRepository.save(evaluation);
    }

    @Override
    public void updateBlockEvaluation(Integer evaluationId, Integer blockNumber, EvalPerBlockDto evalPerBlockDto) {
        Evaluation evaluation = findById(evaluationId);
        Optional<EvalPerBlock> evalPerBlockOpt = evaluation.getEvalPerBlocks().stream().filter(evalPerBlock ->
                Objects.equals(evalPerBlock.getBlockNumber(), blockNumber)).findFirst();
        if (evalPerBlockOpt.isEmpty()){
            saveNewEvalPerBlock(evalPerBlockDto,evaluation);
        } else {
            EvalPerBlock evalPerBlock = evalPerBlockOpt.get();
            evalPerBlock.setValue(evalPerBlockDto.getValue());
            evalPerBlock.getFinalComment().setText(evalPerBlockDto.getComment());
            List<CriterionDto> criteria = evalPerBlockDto.getCriterionDtos();
            evalPerBlock.getCriteria().forEach(criterion -> {
                    for(CriterionDto cr: criteria ){
                        if(Objects.equals(cr.getNumber(), criterion.getNumber())) {
                            criterion.getFinalComment().setText(cr.getComment());
                            criterion.setValue(cr.getValue());
                            criterionRepository.save(criterion);
                            break;
                        }
                    }
                });
            evalPerBlockRepository.save(evalPerBlock);
        }
    }

    private void saveNewEvalPerBlock(EvalPerBlockDto evalPerBlockDto, Evaluation evaluation){
        EvalPerBlock evalPerBlock = evalPerBlockDto.toEvalPerBlock();
        FinalComment finalComment = new FinalComment();
        finalComment.setText(evalPerBlockDto.getComment());
        commentRepository.save(finalComment);
        evalPerBlock.setFinalComment(finalComment);
        if(evalPerBlockDto.getCriterionDtos() != null) {
            List<Criterion> criteria = evalPerBlockDto.getCriterionDtos().stream().map(criterionDto -> {
                Criterion criterion = criterionDto.toCriterion();
                FinalComment finalComment1 = new FinalComment();
                finalComment1.setText(criterionDto.getComment());
                commentRepository.save(finalComment1);
                criterion.setFinalComment(finalComment1);
                return criterionRepository.save(criterion);
            }).collect(Collectors.toList());
            evalPerBlock.setCriteria(criteria);
        }
        evalPerBlockRepository.save(evalPerBlock);
        evaluation.getEvalPerBlocks().add(evalPerBlock);
        evaluationRepository.save(evaluation);
    }

    private Evaluation findById(Integer evaluationId){
        Optional<Evaluation> evaluationOptional = evaluationRepository.findById(evaluationId);
        if (evaluationOptional.isEmpty())
            throw EvaluationException.evaluationByIdNotFound(evaluationId);
        return evaluationOptional.get();
    }

    @Override
    public void updateExtraRequirements(Integer evaluationId, List<RequirementDto> requirementDtos) {
        Evaluation evaluation = findById(evaluationId);
        List<Requirement> requirements = evaluation.getRequirements();
        requirementDtos.forEach(requirementDto -> {
            Requirement requirement = requirements.stream().filter(requirement1 -> Objects.equals(requirement1.getId(), requirementDto.getId())).findFirst().get();
            requirement.setValue(requirementDto.getValue());
            if (requirement.getFinalComment()==null) {
                FinalComment finalComment = new FinalComment();
                finalComment.setText(requirementDto.getComment());
                commentRepository.save(finalComment);
            } else{
                requirement.getFinalComment().setText(requirementDto.getComment());
            }
            requirementRepository.save(requirement);
        });
    }

    @Override
    public EvaluationDto getEvaluation(Integer evaluationId) {
        return new EvaluationDto(findById(evaluationId));
    }

    @Override
    public EvalPerBlockDto getBlockEvaluation(Integer evaluationId, Integer blockNumber) {
        Evaluation evaluation = findById(evaluationId);
        Optional<EvalPerBlock> evalPerBlock = evaluation.getEvalPerBlocks().stream().filter(evalPerBlock1 ->
                Objects.equals(evalPerBlock1.getBlockNumber(), blockNumber)).findFirst();
        if(evalPerBlock.isEmpty())
            throw EvaluationException.canNotFindBlockEval(blockNumber);
        return new EvalPerBlockDto(evalPerBlock.get());
    }

    @Override
    public EvaluationFullDto getEvaluationOverview(Integer evaluationId) {
        Evaluation evaluation = findById(evaluationId);
        return new EvaluationFullDto(evaluation);
    }
}
