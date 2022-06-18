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
@Table(name = "evaluation")
public class Evaluation extends AbstractEntity {
    @OneToOne(mappedBy = "evaluation", orphanRemoval = true)
    private Student student;

    @Column(name = "final_mark")
    private Integer finalMark;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "final_comment_id")
    private FinalComment finalComment;

    @OneToMany(mappedBy = "evaluation", orphanRemoval = true)
    private List<Requirement> requirements = new ArrayList<>();

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "evaluation_id")
    private List<EvalPerBlock> evalPerBlocks = new ArrayList<>();

    @Column(name = "actual_mark")
    private Integer actualMark;

    @Column(name = "increment")
    private Integer increment;

    @Column(name = "coefficient")
    private Double coefficient;

}