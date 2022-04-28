package cvut.fel.cz.thesis_helper.model;

import cvut.fel.cz.thesis_helper.model.AbstractEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "comment")
@Data
public class Comment extends AbstractEntity {
    @Column(name = "text")
    private String text;

}