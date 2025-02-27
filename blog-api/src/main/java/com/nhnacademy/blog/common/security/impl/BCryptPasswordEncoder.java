package com.nhnacademy.blog.common.security.impl;

import com.nhnacademy.blog.common.security.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

/**
 * Bcrypt password encoder
 * 참고 : https://github.com/djmdjm/jBCrypt
 */
@Component(BCryptPasswordEncoder.BEAN_NAME)
public class BCryptPasswordEncoder implements PasswordEncoder {
    public static final String BEAN_NAME="BCryptPasswordEncoder";
    @Override
    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }
}
