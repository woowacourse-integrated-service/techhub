package com.integrated.techhub.mail.application;

import com.integrated.techhub.mail.domain.AuthorityCode;
import com.integrated.techhub.mail.domain.AuthorityCodeRepository;
import com.integrated.techhub.mail.domain.ITemplateEngine;
import com.integrated.techhub.mail.dto.MailSendRequest;
import com.integrated.techhub.mail.dto.MailValidateRequest;
import com.integrated.techhub.mail.exception.AuthorityCodeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.concurrent.ThreadLocalRandom;

import static com.integrated.techhub.mail.domain.type.MailInfo.DOMAIN;
import static com.integrated.techhub.mail.domain.type.MailInfo.TITLE;
import static java.nio.charset.StandardCharsets.UTF_8;


@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private static final int MIN_BOUNDARY = 100000;
    private static final int MAX_BOUNDARY = 999999;
    private static final String TEMPLATE_NAME = "mail";
    private static final String TEMPLATE_CODE_NAME = "code";

    private final JavaMailSender mailSender;
    private final ITemplateEngine templateEngine;
    private final AuthorityCodeRepository authorityCodeRepository;

    @Async
    public void sendMail(final MailSendRequest request) {
        final int randomAuthCode = createRandomAuthCode();
        final String template = createTemplate(String.valueOf(randomAuthCode));
        final MimeMessagePreparator mimeMessagePreparator = createMimeMessagePreparator(request.email(), template);
        mailSender.send(mimeMessagePreparator);
        authorityCodeRepository.save(new AuthorityCode(request.email(), randomAuthCode));
    }

    private MimeMessagePreparator createMimeMessagePreparator(final String receiverEmail, final String template) {
        return mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, UTF_8.name());
            helper.setFrom(DOMAIN.getValue());
            helper.setTo(receiverEmail);
            helper.setSubject(TITLE.getValue());
            helper.setText(template, true);
        };
    }

    private int createRandomAuthCode() {
        return ThreadLocalRandom.current().nextInt(MIN_BOUNDARY, MAX_BOUNDARY + 1);
    }

    private String createTemplate(final String randomAuthCode) {
        final Context context = new Context();
        context.setVariable(TEMPLATE_CODE_NAME, randomAuthCode);
        return templateEngine.process(TEMPLATE_NAME, context);
    }

    public void validateAuthorityCode(final MailValidateRequest request) {
        final AuthorityCode authorityCode = authorityCodeRepository.findById(request.email())
                .orElseThrow(AuthorityCodeNotFoundException::new);
        authorityCode.validateAuthorityCode(request.authorityCode());
    }

}
