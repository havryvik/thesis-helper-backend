package cvut.fel.cz.thesis_helper.model;

import cvut.fel.cz.thesis_helper.model.myEnums.BasicBlocksEnum;
import cvut.fel.cz.thesis_helper.model.myEnums.FulfilmentEnum;
import cvut.fel.cz.thesis_helper.model.myEnums.FinalMarkPattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "approach")
public class Approach extends AbstractEntity {
    @Column(name = "fulfilment_evaluation", nullable = false)
    @Enumerated(EnumType.STRING)
    private FulfilmentEnum fulfilmentEvaluation;

    @Column(name = "basic_blocks_evaluation", nullable = false)
    @Enumerated(EnumType.STRING)
    private BasicBlocksEnum basicBlocksEvaluation;

    @Column(name = "criterion_evaluation", nullable = false)
    private Boolean criterionEvaluation;

    @Column(name = "coefficient", nullable = false)
    private Boolean coefficient = false;

    @Column(name = "auto_fulfillment", nullable = false)
    private Boolean autoFulfilment = false;

    @Column(name = "final_mark_pattern", nullable = false)
    @Enumerated(EnumType.STRING)
    private FinalMarkPattern finalMarkPattern;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "weights_id")
    private Weights weights;



}