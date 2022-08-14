package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Role;
import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.model.Supervisor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    private Integer id;
    private String email;
    private String password;
    private String nameSurname;
    private String confirmPassword;
    private String role;
    private String thesisField;
    private String thesisTheme;
    private Integer approachId;
    private Integer evaluationId;


    public Supervisor toSupervisor(Role role, String encodedPassword){
        Supervisor supervisor = new Supervisor();
        supervisor.setEmail(getEmail());
        supervisor.setNameSurname(getNameSurname());
        supervisor.setPassword(encodedPassword);
        supervisor.setRole(role);
        supervisor.setConfirmPassword(getConfirmPassword());
        return supervisor;
    }

    public Student toStudent(Role role, String encodedPassword){
        Student student = new Student();
        student.setEmail(getEmail());
        student.setNameSurname(getNameSurname());
        student.setThesisField(getThesisField());
        student.setThesisTheme(getThesisTheme());
        student.setPassword(encodedPassword);
        student.setConfirmPassword(getConfirmPassword());
        student.setRole(role);
        return student;
    }

    public AccountDto (Student student){
        this.id = student.getId();
        this.email = student.getEmail();
        this.role = student.getRole().getName();
        this.nameSurname = student.getNameSurname();
        this.thesisField = student.getThesisField();
        this.thesisTheme = student.getThesisTheme();
        this.approachId = student.getApproach().getId();
        if(student.getEvaluation()!=null)
            this.evaluationId = student.getEvaluation().getId();
    }

    public AccountDto (Supervisor supervisor){
        this.id = supervisor.getId();
        this.email = supervisor.getEmail();
        this.role = supervisor.getRole().getName();
        this.nameSurname = supervisor.getNameSurname();
    }

}
