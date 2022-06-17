package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.EvalPerBlock;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
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
        this.comment = evalPerBlock.getFinalComment().getText();
        this.criterionDtos = evalPerBlock.getCriteria()
                .stream().map(CriterionDto::new).collect(Collectors.toList());
    }


}
