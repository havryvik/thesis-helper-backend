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
@Table(name = "role")
public class Role extends AbstractEntity {
    @Column(name = "role", nullable = false)
    private String name;


}