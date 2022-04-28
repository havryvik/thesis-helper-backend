package cvut.fel.cz.thesis_helper.controller;


import cvut.fel.cz.thesis_helper.dto.ApproachDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.model.Weights;
import cvut.fel.cz.thesis_helper.service.impl.ApproachServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value="/thesis_helper/approach")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class ApproachController {


    private final ApproachServiceImpl approachService;

    @Autowired
    public ApproachController(ApproachServiceImpl approachService) {
        this.approachService = approachService;
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    @GetMapping("{approachId}")
    public ApproachDto getApproach(@PathVariable Integer approachId){
        return approachService.getApproachById(approachId);
    }


    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @PutMapping("{approachId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateApproach(@PathVariable Integer approachId, @RequestBody ApproachDto approachDto){
        approachService.updateApproach(approachDto, approachId);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @PutMapping("{approachId}/weights")
    @ResponseStatus(HttpStatus.OK)
    public void addWeights(@PathVariable Integer approachId, @RequestBody Weights weights){
        approachService.addWeightToApproach(approachId, weights);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @GetMapping("{approachId}/weights")
    @ResponseStatus(HttpStatus.OK)
    public Weights getWeights(@PathVariable Integer approachId){
        return approachService.getApproachWeights(approachId);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @GetMapping("{approachId}/extra-requirements")
    @ResponseStatus(HttpStatus.OK)
    public List<RequirementDto> getExtraRequirementsByApproachId(@PathVariable Integer approachId){
        return approachService.getExtraRequirementsByApproachId(approachId);
    }
}
