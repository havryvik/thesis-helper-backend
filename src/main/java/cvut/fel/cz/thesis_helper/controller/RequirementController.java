package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.service.impl.RequirementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/thesis_helper/requirements")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class RequirementController {

    private final RequirementServiceImpl requirementService;

    @Autowired
    public RequirementController(RequirementServiceImpl requirementService) {
        this.requirementService = requirementService;
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRequirement(@PathVariable Integer id) {
        requirementService.removeRequirement(id);
    }


}
