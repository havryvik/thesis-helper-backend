package cvut.fel.cz.thesis_helper.service;

import cvut.fel.cz.thesis_helper.dto.ApproachDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.model.Weights;

import java.util.List;

public interface ApproachService {
    void addApproachToStudent(ApproachDto approach, Integer studentId);
    ApproachDto getApproachById(Integer approachId);
    ApproachDto getApproachByStudentId(Integer studentId);
    void updateApproach(ApproachDto approachDto, Integer approachId);
    void addWeightToApproach(Integer approachId, Weights weight);

    Weights getApproachWeights(Integer approachId);

    List<RequirementDto> getExtraRequirementsByApproachId(Integer approachId);
}
