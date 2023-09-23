package com.integrated.techhub.mail.domain.type;

import lombok.Getter;

@Getter
public enum MailInfo {

    DOMAIN("tech.hub.kr"),
    TITLE("TechHub 인증번호 이메일입니다.");

    private final String value;

    MailInfo(final String value) {
        this.value = value;
    }

}
