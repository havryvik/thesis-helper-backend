package cvut.fel.cz.thesis_helper.repository;

import cvut.fel.cz.thesis_helper.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}