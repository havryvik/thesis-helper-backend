package cvut.fel.cz.thesis_helper.controller;

import cvut.fel.cz.thesis_helper.dto.AccountDto;
import cvut.fel.cz.thesis_helper.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/thesis_helper/account")
@CrossOrigin(allowedHeaders = {"authorization","content-type"}, origins = "http://localhost:3000")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PutMapping()
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'STUDENT')")
    @ResponseStatus(HttpStatus.OK)
    public AccountDto updateProfile(@RequestBody AccountDto accountDto){
        System.out.println("REST UPDATE ACCOUNT");
        return accountService.updateAccount(accountDto);
    }


}
