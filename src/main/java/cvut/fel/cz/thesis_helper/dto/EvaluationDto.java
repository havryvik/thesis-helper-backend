package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Evaluation;
import lombok.Data;

@Data
public class EvaluationDto {
    private Integer studentId;
    private Integer finalMark;
    private String finalComment;

    public EvaluationDto (Evaluation evaluation){
        if (evaluation.getFinalComment()!=null)
            this.finalComment = evaluation.getFinalComment().getText();
        else this.finalComment = "";
        this.finalMark= evaluation.getFinalMark();
        this.studentId = evaluation.getStudent().getId();
    }
}
