package cvut.fel.cz.thesis_helper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "weights")
public class Weights extends AbstractEntity {
    @Column(name = "activity", nullable = false)
    private Integer activity;

    @Column(name = "professional_level", nullable = false)
    private Integer professionalLevel;

    @Column(name = "language_level", nullable = false)
    private Integer languageLevel;

    @Column(name = "citation", nullable = false)
    private Integer citation;

    @Column(name = "max_points", nullable = false)
    private Integer maxPoints;

}