package cvut.fel.cz.thesis_helper.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public class AbstractComment extends AbstractEntity{
    @Column(name = "text")
    private String text;
}
