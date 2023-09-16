package com.integrated.techhub.mail.presentation;

import com.integrated.techhub.auth.application.AuthQueryService;
import com.integrated.techhub.mail.dto.MailSendRequest;
import com.integrated.techhub.mail.application.MailService;
import com.integrated.techhub.mail.dto.MailValidateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final AuthQueryService authQueryService;
    private final MailService mailService;

    @PostMapping("/authorization-code")
    public ResponseEntity<Void> sendMail(@RequestBody @Valid final MailSendRequest request) {
        authQueryService.validateExistedMember(request);
        mailService.sendMail(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validation")
    public ResponseEntity<Void> validateAuthorityCode(@RequestBody @Valid final MailValidateRequest request) {
        mailService.validateAuthorityCode(request);
        return ResponseEntity.ok().build();
    }

}
