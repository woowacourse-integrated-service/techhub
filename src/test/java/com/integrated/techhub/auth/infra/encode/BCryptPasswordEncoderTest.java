package com.integrated.techhub.auth.infra.encode;

import com.integrated.techhub.common.auth.encode.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BCryptPasswordEncoderTest {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void encodeAndMatch() {
        //given
        int rawPassword = 1234;
        String encodedPassword = passwordEncoder.encode(String.valueOf(rawPassword));

        //when, then
        assertTrue(passwordEncoder.isMatch(String.valueOf(rawPassword), encodedPassword));
    }

}