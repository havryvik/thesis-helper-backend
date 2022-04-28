package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.controller.util.RestUtils;
import cvut.fel.cz.thesis_helper.dto.AccountDto;
import cvut.fel.cz.thesis_helper.dto.EvalPerBlockDto;
import cvut.fel.cz.thesis_helper.dto.EvaluationDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.model.EvalPerBlock;
import cvut.fel.cz.thesis_helper.service.impl.EvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value="/thesis_helper/evaluation")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class EvaluationController {

    private final EvaluationServiceImpl evaluationService;

    @Autowired
    public EvaluationController(EvaluationServiceImpl evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping("/{approachId}")
    public void addRequirements(@RequestBody List<RequirementDto> requirementDtos, @PathVariable Integer approachId){
        evaluationService.addRequirementToEvaluationByApproachId(requirementDtos, approachId);
    }

    @PutMapping("/{evaluationId}/block/{blockNumber}")
    public void updateBlockEvaluation(@PathVariable Integer evaluationId, @PathVariable Integer blockNumber, @RequestBody EvalPerBlockDto evalPerBlockDto){
        evaluationService.updateBlockEvaluation(evaluationId, blockNumber, evalPerBlockDto);
    }

    @PutMapping("/{evaluationId}")
    public void updateEvaluation(@PathVariable Integer evaluationId, @RequestBody EvaluationDto evaluationDto){
        evaluationService.updateEvaluation(evaluationId, evaluationDto);
    }

    @GetMapping("/{evaluationId}")
    public EvaluationDto getEvaluation( @PathVariable Integer evaluationId){
        return evaluationService.getEvaluation(evaluationId);
    }

    @PutMapping("/{evaluationId}/extra-requirements")
    public void updateRequirements(@PathVariable Integer evaluationId, @RequestBody List<RequirementDto> requirementDtos){
        evaluationService.updateExtraRequirements(evaluationId, requirementDtos);
    }

    @GetMapping("/{evaluationId}/block/{blockNumber}")
    public EvalPerBlockDto getBlockEvaluation(@PathVariable Integer evaluationId, @PathVariable Integer blockNumber){
        return evaluationService.getBlockEvaluation(evaluationId, blockNumber);
    }

}
