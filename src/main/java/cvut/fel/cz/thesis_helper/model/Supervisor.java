package cvut.fel.cz.thesis_helper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "teacher")
public class Supervisor extends Account {

    @JsonIgnore
    @OneToMany(mappedBy = "supervisor", cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, orphanRemoval = true)
    @OrderBy("nameSurname DESC")
    private List<Student> students = new ArrayList<>();


}