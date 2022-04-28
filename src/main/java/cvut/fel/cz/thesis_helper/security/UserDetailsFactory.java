package cvut.fel.cz.thesis_helper.security;

import cvut.fel.cz.thesis_helper.model.Account;

public final class UserDetailsFactory {

    public UserDetailsFactory() {
    }
    public static UsrDetails create(Account user){
        return new UsrDetails(user);
    }


}
