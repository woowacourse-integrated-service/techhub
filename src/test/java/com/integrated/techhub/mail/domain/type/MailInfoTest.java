package com.integrated.techhub.mail.domain.type;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailInfoTest {

    @Test
    void 생성자() {
        MailInfo mailInfo = MailInfo.DOMAIN;

        assertThat(mailInfo.getValue()).isEqualTo("tech.hub.kr");
    }

}