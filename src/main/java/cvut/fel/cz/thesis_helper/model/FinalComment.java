package cvut.fel.cz.thesis_helper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "final_comment")
@Data
public class FinalComment extends AbstractComment {
}