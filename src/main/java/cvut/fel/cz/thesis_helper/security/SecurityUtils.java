package cvut.fel.cz.thesis_helper.security;

import cvut.fel.cz.thesis_helper.model.Account;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Account getCurrentUser() {
        final SecurityContext context = SecurityContextHolder.getContext();
        assert context != null;
        final UsrDetails userDetails = (UsrDetails) context.getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
}
