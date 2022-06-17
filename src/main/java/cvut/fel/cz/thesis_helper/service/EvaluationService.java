package cvut.fel.cz.thesis_helper.service;

import cvut.fel.cz.thesis_helper.dto.EvalPerBlockDto;
import cvut.fel.cz.thesis_helper.dto.EvaluationDto;
import cvut.fel.cz.thesis_helper.dto.EvaluationFullDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;

import java.util.List;

public interface EvaluationService {
    void addRequirementToEvaluationByApproachId(List<RequirementDto> requirementDto, Integer approachId);

    List<RequirementDto> getRequirements(Integer evaluationId);

    void updateEvaluation(Integer evaluationId, EvaluationDto evaluationDto);

    void updateBlockEvaluation(Integer evaluationId, Integer blockNumber, EvalPerBlockDto evalPerBlockDto);

    void updateExtraRequirements(Integer evaluationId, List<RequirementDto> requirementDtos);

    EvaluationDto getEvaluation(Integer evaluationId);

    EvalPerBlockDto getBlockEvaluation(Integer evaluationId, Integer blockNumber);

    EvaluationFullDto getEvaluationOverview(Integer evaluationId);
}
