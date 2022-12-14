package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Criterion;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CriterionDto {
    private Integer id;
    private Integer value;
    private Integer number;
    private String comment;

    public Criterion toCriterion(){
        Criterion criterion = new Criterion();
        criterion.setValue(this.getValue());
        criterion.setNumber(getNumber());
        return criterion;
    }

    public CriterionDto(Criterion criterion){
        this.setId(criterion.getId());
        if(criterion.getValue()!=null)
            this.setValue(criterion.getValue());
        if(criterion.getFinalComment()!=null)
            this.setComment(criterion.getFinalComment().getText());
        this.setNumber(criterion.getNumber());
    }
}
