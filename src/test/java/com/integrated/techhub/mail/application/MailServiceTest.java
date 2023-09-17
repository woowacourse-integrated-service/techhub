package com.integrated.techhub.mail.application;

import com.integrated.techhub.mail.domain.AuthorityCode;
import com.integrated.techhub.mail.domain.AuthorityCodeRepository;
import com.integrated.techhub.mail.dto.MailValidateRequest;
import com.integrated.techhub.mail.exception.AuthorityCodeNotFoundException;
import com.integrated.techhub.mail.exception.AuthorityCodeNotMatchException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MailServiceTest {

    @Autowired
    private AuthorityCodeRepository authorityCodeRepository;

    @Autowired
    private MailService mailService;


    @Nested
    class validateAuthorityCode {

        @Test
        void 정상_인증코드_검증() {
            //given
            String email = "moomin@gmail.com";
            int code = 123456;
            authorityCodeRepository.save(new AuthorityCode(email, code));
            MailValidateRequest request = new MailValidateRequest(email, code);

            //when, then
            assertDoesNotThrow(() -> mailService.validateAuthorityCode(request));
        }

        @Test
        void 존재하지_않는_이메일_인증코드_검증() {
            //given
            int code = 123456;
            authorityCodeRepository.save(new AuthorityCode("moomin@gmail.com", code));
            MailValidateRequest request = new MailValidateRequest("wrong@gmail.com", code);

            //when, then
            assertThrows(AuthorityCodeNotFoundException.class, () -> mailService.validateAuthorityCode(request));
        }

        @Test
        void 인증코드_검증_실패() {
            //given
            String email = "moomin@gmail.com";
            authorityCodeRepository.save(new AuthorityCode(email, 123456));
            MailValidateRequest request = new MailValidateRequest(email, 654321);

            //when, then
            assertThrows(AuthorityCodeNotMatchException.class, () -> mailService.validateAuthorityCode(request));
        }

    }

}