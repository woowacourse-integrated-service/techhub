package com.integrated.techhub.mail.presentation;

import com.integrated.techhub.common.acceptance.AcceptanceTest;
import com.integrated.techhub.mail.domain.AuthorityCode;
import com.integrated.techhub.mail.domain.AuthorityCodeRepository;
import com.integrated.techhub.mail.dto.MailValidateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.integrated.techhub.mail.presentation.MailControllerSteps.인증코드_유효한지_요청;

@DisplayName("메일 인수 테스트")
class MailControllerTest extends AcceptanceTest {

    @Autowired
    private AuthorityCodeRepository authorityCodeRepository;

    @Test
    void validateAuthorityCode() {
        //given
        var email = "moomin@gmail.com";
        var code = 123456;
        authorityCodeRepository.save(new AuthorityCode(email, code));
        MailValidateRequest body = new MailValidateRequest(email, code);

        var 응답 = 인증코드_유효한지_요청(body);

        응답.response().then().statusCode(HttpStatus.OK.value());
    }

}