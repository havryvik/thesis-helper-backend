package cvut.fel.cz.thesis_helper.service;

import cvut.fel.cz.thesis_helper.dto.AccountDto;
import cvut.fel.cz.thesis_helper.model.Account;

public interface AccountService {
    Account findByEmail(String email);
    Account register(AccountDto accountDto);
    AccountDto updateAccount(AccountDto accountDto);
    AccountDto getCurrentUser();
}
