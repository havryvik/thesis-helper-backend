package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.EvalPerBlock;
import cvut.fel.cz.thesis_helper.model.Evaluation;
import cvut.fel.cz.thesis_helper.model.Requirement;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EvalPerBlockDto{
    private String value;
    private Integer blockNumber;
    private String comment;
    private List<CriterionDto> criterionDtos;

    public EvalPerBlock toEvalPerBlock(){
        EvalPerBlock evalPerBlock = new EvalPerBlock();
        evalPerBlock.setBlockNumber(blockNumber);
        evalPerBlock.setValue(value);
        return evalPerBlock;
    }

    public EvalPerBlockDto(EvalPerBlock evalPerBlock){
        this.blockNumber = evalPerBlock.getBlockNumber();
        this.value = evalPerBlock.getValue();
        this.comment = evalPerBlock.getComment().getText();
        this.criterionDtos = evalPerBlock.getCriteria().stream().map(CriterionDto::new).collect(Collectors.toList());
    }


}
