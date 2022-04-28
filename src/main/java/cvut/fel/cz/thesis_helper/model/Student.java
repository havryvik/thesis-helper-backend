package cvut.fel.cz.thesis_helper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student")
public class Student extends Account {
    @Column(name = "thesis_field", nullable = false)
    private String thesisField;

    @Column(name = "thesis_theme", nullable = false)
    private String thesisTheme;

    @JsonIgnoreProperties(value = {"id, role, students, password, confirmPassword"})
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "teacher_id")
    private Supervisor supervisor;

    @OneToOne(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "approach_id")
    private Approach approach;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

}