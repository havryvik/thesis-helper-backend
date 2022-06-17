package cvut.fel.cz.thesis_helper.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "block_comment")
@Data
public class BlockComment extends AbstractComment {
    @Column(name = "block_number", nullable = false)
    private Integer blockNumber;

}