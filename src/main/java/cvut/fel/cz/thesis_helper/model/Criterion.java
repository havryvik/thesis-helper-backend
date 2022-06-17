package cvut.fel.cz.thesis_helper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Criterion extends AbstractEntity{
    @Column(name = "value")
    private Integer value;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private FinalComment finalComment;

    @Column(name = "number")
    private Integer number;
}