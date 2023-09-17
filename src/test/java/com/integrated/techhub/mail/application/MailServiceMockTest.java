package com.integrated.techhub.mail.application;

import com.integrated.techhub.mail.domain.AuthorityCodeRepository;
import com.integrated.techhub.mail.domain.ITemplateEngine;
import com.integrated.techhub.mail.dto.MailSendRequest;
import com.integrated.techhub.mail.infra.mail.TestTemplateEngineWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceMockTest {


    private final ITemplateEngine iTemplateEngine = new TestTemplateEngineWrapper();

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private AuthorityCodeRepository authorityCodeRepository;

    private MailService mailService;

    @BeforeEach
    void setUp() {
        mailService = new MailService(mailSender, iTemplateEngine, authorityCodeRepository);
    }

    @Test
    void sendMail() {
        //when
        mailService.sendMail(new MailSendRequest("moomin@gmail.com"));

        //then
        verify(mailSender, times(1)).send(any(MimeMessagePreparator.class));
        verify(authorityCodeRepository, times(1)).save(any());
    }

}
