package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Evaluation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class EvaluationFullDto extends EvaluationDto{

    private List<EvalPerBlockDto> blockDtos;
    private List<RequirementDto> requirementDtos;

    public EvaluationFullDto(Evaluation evaluation) {
        super(evaluation);
        this.blockDtos = evaluation.getEvalPerBlocks()
                .stream().map(EvalPerBlockDto::new).collect(Collectors.toList());
        if (evaluation.getRequirements()!=null){
            this.requirementDtos = evaluation.getRequirements()
                    .stream().map(RequirementDto::new).collect(Collectors.toList());
        }
    }

}
