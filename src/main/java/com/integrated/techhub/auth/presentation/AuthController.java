package com.integrated.techhub.auth.presentation;

import com.integrated.techhub.auth.application.AuthService;
import com.integrated.techhub.auth.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequest request) {
        final Long memberId = authService.registerMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

}
