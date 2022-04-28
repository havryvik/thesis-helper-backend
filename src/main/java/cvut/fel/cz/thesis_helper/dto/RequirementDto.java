package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Requirement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequirementDto {
    private Integer id;
    private String name;
    private Integer value;
    private String comment;

    public Requirement toRequirement(){
        Requirement requirement = new Requirement();
        requirement.setName(name);
        requirement.setValue(value);
        return requirement;
    }

    public RequirementDto(Requirement requirement){
        this.id = requirement.getId();
        this.name = requirement.getName();
        if(requirement.getValue()!=null)
            this.value = requirement.getValue();
        if(requirement.getComment()!=null)
            this.comment = requirement.getComment().getText();
    }
}
