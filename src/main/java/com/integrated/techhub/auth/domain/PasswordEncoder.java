package com.integrated.techhub.auth.domain;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean isMatch(String rawPassword, String encryptedPassword);

}