package cvut.fel.cz.thesis_helper.service.impl;

import cvut.fel.cz.thesis_helper.dto.AccountDto;
import cvut.fel.cz.thesis_helper.exception.UserExceptions;
import cvut.fel.cz.thesis_helper.model.Account;
import cvut.fel.cz.thesis_helper.model.Role;
import cvut.fel.cz.thesis_helper.model.Student;
import cvut.fel.cz.thesis_helper.model.Supervisor;
import cvut.fel.cz.thesis_helper.repository.RoleRepository;
import cvut.fel.cz.thesis_helper.repository.StudentRepository;
import cvut.fel.cz.thesis_helper.repository.SupervisorRepository;
import cvut.fel.cz.thesis_helper.security.SecurityUtils;
import cvut.fel.cz.thesis_helper.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    private final StudentRepository studentRepository;
    private final SupervisorRepository supervisorRepository;
    private final StudentServiceImpl studentService;
    private final SupervisorServiceImpl supervisorService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(StudentRepository studentRepository, SupervisorRepository supervisorRepository, StudentServiceImpl studentService, SupervisorServiceImpl supervisorService, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.supervisorRepository = supervisorRepository;
        this.studentService = studentService;
        this.supervisorService = supervisorService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account findByEmail(String email) {
        Student student = studentRepository.findByEmail(email);
        Supervisor supervisor = supervisorRepository.findByEmail(email);
        if (student==null && supervisor==null)
            throw UserExceptions.userNotFoundException(email);
        if(student!=null) return student;
        else return supervisor;
    }

    @Override
    public Account register(AccountDto accountDto) {
        if (!accountDto.getPassword().equals(accountDto.getConfirmPassword()))
            throw UserExceptions.passwordsDoesNotMatch();
        String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
        Role role = roleRepository.findByName(accountDto.getRole());
        if (accountDto.getRole().equals("SUPERVISOR"))
            return supervisorService.register(accountDto.toSupervisor(role, encodedPassword));
        System.out.println("SERVICE register "+accountDto);
        System.out.println(accountDto.toStudent(role, encodedPassword));
        return studentService.register(accountDto.toStudent(role, encodedPassword));
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        System.out.println("SERVICE UPDATE ACCOUNT");

        if(!Objects.equals(SecurityUtils.getCurrentUser().getId(), accountDto.getId()))
            throw UserExceptions.accessDeniedException();
        if (accountDto.getRole().equals("STUDENT")){
            Student student = studentRepository.findById(accountDto.getId()).get();
            student.setNameSurname(accountDto.getNameSurname());
            student.setThesisTheme(accountDto.getThesisTheme());
            student.setThesisField(accountDto.getThesisField());
            studentRepository.save(student);
            return new AccountDto(student);
        }
        else {
            Supervisor supervisor = supervisorRepository.findById(accountDto.getId()).get();
            supervisor.setNameSurname(accountDto.getNameSurname());
            supervisorRepository.save(supervisor);
            return new AccountDto(supervisor);
        }
    }

    @Override
    public AccountDto getCurrentUser(){
        Account account = SecurityUtils.getCurrentUser();
        if (account.getRole().getName().equals("STUDENT")){
            Student student = studentRepository.findByEmail(account.getEmail());
            if (student==null) return null;
            else return new AccountDto(student);
        }
        else {
            Supervisor supervisor = supervisorRepository.findByEmail(account.getEmail());
            if (supervisor==null) return null;
            else return new AccountDto(supervisor);        }
    }
}
