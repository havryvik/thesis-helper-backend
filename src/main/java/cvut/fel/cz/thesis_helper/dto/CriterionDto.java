package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Criterion;
import lombok.Data;

@Data
public class CriterionDto extends RequirementDto{
    private Integer Number;
    private String Comment;

    public Criterion toCriterion(){
        Criterion criterion = new Criterion();
        criterion.setName(this.getName());
        criterion.setValue(this.getValue());
        criterion.setNumber(getNumber());
        return criterion;
    }

    public CriterionDto(Criterion criterion){
        this.setId(criterion.getId());
        this.setName(criterion.getName());
        if(criterion.getValue()!=null)
            this.setValue(criterion.getValue());
        if(criterion.getComment()!=null)
            this.setComment(criterion.getComment().getText());
        this.setNumber(criterion.getNumber());
    }
}
