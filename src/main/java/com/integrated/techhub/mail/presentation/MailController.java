package com.integrated.techhub.mail.presentation;

import com.integrated.techhub.auth.application.AuthQueryService;
import com.integrated.techhub.dto.MailSendRequest;
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

    @PostMapping("/authorization-code")
    public ResponseEntity<Void> sendMail(@RequestBody @Valid final MailSendRequest request) {
        authQueryService.validateExistedMember(request);
        return ResponseEntity.ok().build();
    }

}
