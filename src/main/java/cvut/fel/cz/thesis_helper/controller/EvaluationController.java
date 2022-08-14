package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.dto.*;
import cvut.fel.cz.thesis_helper.service.impl.EvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/thesis_helper/evaluation")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class  EvaluationController {

    private final EvaluationServiceImpl evaluationService;

    @Autowired
    public EvaluationController(EvaluationServiceImpl evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/{approachId}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void addRequirements(@RequestBody List<RequirementDto> requirementDtos, @PathVariable Integer approachId){
        evaluationService.addRequirementToEvaluationByApproachId(requirementDtos, approachId);
    }

    @PutMapping("/{evaluationId}/block/{blockNumber}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void updateBlockEvaluation(@PathVariable Integer evaluationId, @PathVariable Integer blockNumber, @RequestBody EvalPerBlockDto evalPerBlockDto){
        evaluationService.updateBlockEvaluation(evaluationId, blockNumber, evalPerBlockDto);
    }

    @PutMapping("/{evaluationId}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void updateEvaluation(@PathVariable Integer evaluationId, @RequestBody EvaluationDto evaluationDto){
        evaluationService.updateEvaluation(evaluationId, evaluationDto);
    }

    @GetMapping("/{evaluationId}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    public EvaluationDto getEvaluation( @PathVariable Integer evaluationId){
        return evaluationService.getEvaluation(evaluationId);
    }

    @GetMapping("/{evaluationId}/overview")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    public EvaluationFullDto getEvaluationOverview(@PathVariable Integer evaluationId){
        return evaluationService.getEvaluationOverview(evaluationId);
    }

    @PutMapping("/{evaluationId}/extra-requirements")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    public void updateRequirements(@PathVariable Integer evaluationId, @RequestBody List<RequirementDto> requirementDtos){
        evaluationService.updateExtraRequirements(evaluationId, requirementDtos);
    }

    @GetMapping("/{evaluationId}/block/{blockNumber}")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    public EvalPerBlockDto getBlockEvaluation(@PathVariable Integer evaluationId, @PathVariable Integer blockNumber){
        return evaluationService.getBlockEvaluation(evaluationId, blockNumber);
    }

}
