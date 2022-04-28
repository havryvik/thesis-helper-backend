package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.controller.util.RestUtils;
import cvut.fel.cz.thesis_helper.dto.AccountDto;
import cvut.fel.cz.thesis_helper.dto.AuthenticationRequestDto;
import cvut.fel.cz.thesis_helper.dto.AuthenticationResponseDto;
import cvut.fel.cz.thesis_helper.model.Account;
import cvut.fel.cz.thesis_helper.security.TokenProvider;
import cvut.fel.cz.thesis_helper.service.impl.AccountServiceImpl;
import cvut.fel.cz.thesis_helper.service.impl.StudentServiceImpl;
import cvut.fel.cz.thesis_helper.service.impl.SupervisorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/thesis_helper")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class AuthenticationController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final SupervisorServiceImpl teacherService;
    private final StudentServiceImpl studentService;
    private final AccountServiceImpl accountService;

    @Autowired
    public AuthenticationController(BCryptPasswordEncoder passwordEncoder, TokenProvider tokenProvider, SupervisorServiceImpl teacherService, StudentServiceImpl studentService, AccountServiceImpl accountService) {
        this.tokenProvider = tokenProvider;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto userRequest){
        String email = userRequest.getEmail();
        Account user = accountService.findByEmail(email);
        if (!passwordEncoder.matches(userRequest.getPassword(),user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        String token = tokenProvider.createToken(email, user.getRole());
        return ResponseEntity.ok(new AuthenticationResponseDto(token, email, user.getRole().getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AccountDto accountDto){
        System.out.println("REST register "+accountDto);
        accountService.register(accountDto);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("auth");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    public AccountDto getCurrentUser(){

        return accountService.getCurrentUser();
    }
}
