package com.integrated.techhub.common.auth.encode;

import com.integrated.techhub.auth.domain.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String rawPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawPassword, encryptedPassword);
    }

}