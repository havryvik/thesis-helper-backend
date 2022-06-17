package cvut.fel.cz.thesis_helper.dto;


import cvut.fel.cz.thesis_helper.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StudentDto {

    private Integer id;
    private String email;
    private String nameSurname;
    private String thesisField;
    private String thesisTheme;
    private Integer approachId;
    private Integer evaluationId;

    public StudentDto (Student student){
        this.id = student.getId();
        this.email = student.getEmail();
        this.nameSurname = student.getNameSurname();
        this.thesisField = student.getThesisField();
        this.thesisTheme = student.getThesisTheme();
        this.approachId = student.getApproach().getId();
        if(student.getEvaluation()!=null)
            this.evaluationId = student.getEvaluation().getId();
    }
}
