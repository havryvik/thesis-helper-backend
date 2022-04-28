package cvut.fel.cz.thesis_helper.security;

import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.model.Supervisor;
import cvut.fel.cz.thesis_helper.repository.StudentRepository;
import cvut.fel.cz.thesis_helper.repository.SupervisorRepository;
import cvut.fel.cz.thesis_helper.service.StudentService;
import cvut.fel.cz.thesis_helper.service.SupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StudentRepository studentService;
    @Autowired
    private SupervisorRepository teacherService;

    @Override
    public UsrDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Supervisor supervisor = teacherService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        if (supervisor != null) return UserDetailsFactory.create(supervisor);
        if (student != null) return UserDetailsFactory.create(student);
        else throw new UsernameNotFoundException("User was not found!");
    }
}
