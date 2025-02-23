package dkhpweb.dkhp_backend.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static String getUserId(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new AccessDeniedException("Permission denied");

        String userId = authentication.getName();
        if(userId == null) throw new AccessDeniedException("Permission denied");
        return userId;
    }
}
