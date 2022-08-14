package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.dto.SupervisorDto;
import cvut.fel.cz.thesis_helper.service.impl.SupervisorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/thesis_helper/supervisor")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class StudentController {

    private final SupervisorServiceImpl supervisorService;

    @Autowired
    public StudentController(SupervisorServiceImpl supervisorService) {
        this.supervisorService = supervisorService;
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @GetMapping("/{studentId}")
    public SupervisorDto getStudentSupervisor(@PathVariable Integer studentId){
        return supervisorService.getSupervisorByStudentId(studentId);
    }
}
