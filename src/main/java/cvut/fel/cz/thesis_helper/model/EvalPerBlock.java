package cvut.fel.cz.thesis_helper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "eval_per_block")
public class EvalPerBlock extends AbstractEntity {

    @Column(name = "block_number", nullable = false)
    private Integer blockNumber;

    @Column(name = "value", nullable = false)
    private String value;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "eval_per_block_id")
    @OrderBy("number ASC")
    private List<Criterion> criteria = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private FinalComment finalComment;

}