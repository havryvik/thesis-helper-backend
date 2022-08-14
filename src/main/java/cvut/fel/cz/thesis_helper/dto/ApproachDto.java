package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Approach;
import cvut.fel.cz.thesis_helper.model.myEnums.BasicBlocksEnum;
import cvut.fel.cz.thesis_helper.model.myEnums.FulfilmentEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApproachDto {
    private Integer id;
    private String fulfilmentEvaluation;
    private String basicBlocksEvaluation;
    private Boolean criterionEvaluation;
    private Boolean coefficient;
    private Boolean autoFulfilment;
    private String finalMarkPattern;

    public Approach toApproach(){
        Approach approach = new Approach();
        approach.setFulfilmentEvaluation(FulfilmentEnum.valueOf(fulfilmentEvaluation));
        approach.setBasicBlocksEvaluation(BasicBlocksEnum.valueOf(basicBlocksEvaluation));
        approach.setCriterionEvaluation(criterionEvaluation);
        approach.setCoefficient(coefficient);
        approach.setAutoFulfilment(autoFulfilment);
        return approach;
    }

    public ApproachDto (Approach approach){
        this.id = approach.getId();
        this.fulfilmentEvaluation = approach.getFulfilmentEvaluation().name();
        this.basicBlocksEvaluation = approach.getBasicBlocksEvaluation().name();
        this.criterionEvaluation = approach.getCriterionEvaluation();
        this.coefficient = approach.getCoefficient();
        this.autoFulfilment = approach.getAutoFulfilment();
        this.finalMarkPattern = approach.getFinalMarkPattern().name();
    }
}
