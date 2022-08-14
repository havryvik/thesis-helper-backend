package cvut.fel.cz.thesis_helper.dto;

import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.model.Supervisor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SupervisorDto {
    private String email;
    private String nameSurname;
    public SupervisorDto (Supervisor supervisor){
        this.email = supervisor.getEmail();
        this.nameSurname = supervisor.getNameSurname();
    }
}
