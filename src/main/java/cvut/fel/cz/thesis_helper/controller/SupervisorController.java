package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.dto.ApproachDto;
import cvut.fel.cz.thesis_helper.dto.EvaluationDto;
import cvut.fel.cz.thesis_helper.dto.RequirementDto;
import cvut.fel.cz.thesis_helper.dto.StudentDto;
import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.service.impl.ApproachServiceImpl;
import cvut.fel.cz.thesis_helper.service.impl.StudentServiceImpl;
import cvut.fel.cz.thesis_helper.service.impl.SupervisorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value="/thesis_helper/students")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class SupervisorController {

    private final SupervisorServiceImpl supervisorService;
    private final StudentServiceImpl studentService;
    private final ApproachServiceImpl approachService;

    @Autowired
    public SupervisorController(SupervisorServiceImpl supervisorService, StudentServiceImpl studentService, ApproachServiceImpl approachService) {
        this.supervisorService = supervisorService;
        this.studentService = studentService;
        this.approachService = approachService;
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @GetMapping()
    public List<StudentDto> getStudents(){
        System.out.println("Rest endpoint");
        return supervisorService.getStudents();
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @GetMapping("/{studentId}/extra-requirements")
    public List<RequirementDto> getExtraRequirementsByStudent(@PathVariable Integer studentId){
        return studentService.getExtraRequirements(studentId);
    }

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable Integer studentId){
        return studentService.findById(studentId);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @GetMapping("/{studentId}/approach")
    public ApproachDto getStudentApproach(@PathVariable Integer studentId){
        return approachService.getApproachByStudentId(studentId);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @PutMapping("/{studentId}/approach")
    @ResponseStatus(HttpStatus.OK)
    public void addStudentApproach(@PathVariable Integer studentId, @RequestBody ApproachDto approachDto){
        approachService.addApproachToStudent(approachDto, studentId);
    }

    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void addStudent(@RequestBody String email){
        System.out.println(email);
        supervisorService.addStudent(email);
    }

    @PutMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeStudent(@PathVariable Integer studentId){
        Student student = studentService.findById(studentId);
        supervisorService.removeStudent(student.getEmail());
    }

    @GetMapping("/{studentId}/evaluation")
    public void getStudentEvaluation(@PathVariable Integer studentId){
        supervisorService.getStudentEval(studentId);
    }
}
