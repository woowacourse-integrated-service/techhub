package com.integrated.techhub.auth.presentation;

import com.integrated.techhub.auth.application.AuthService;
import com.integrated.techhub.auth.application.client.GithubRestTemplateClient;
import com.integrated.techhub.auth.dto.request.LoginRequest;
import com.integrated.techhub.auth.dto.request.SignUpRequest;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;
import com.integrated.techhub.auth.dto.response.TokenResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final GithubRestTemplateClient githubRestTemplateClient;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequest request) {
        final Long memberId = authService.registerMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid final LoginRequest request) {
        final TokenResponse tokens = authService.getTokens(request.email(), request.password());
        return ResponseEntity.ok().body(tokens);
    }

    @PutMapping("/login/oauth2/code/github")
    public ResponseEntity<Void> authorizeGithub(
            @Auth AuthProperties authProperties,
            @RequestParam final String code
    ) {
        final OAuthTokensResponse tokensResponse = githubRestTemplateClient.getGithubTokens(code);
        final Long memberId = authProperties.memberId();
        authService.saveGithubTokens(tokensResponse.toAccessToken(memberId), tokensResponse.toRefreshToken(memberId));
        return ResponseEntity.ok().build();
    }

}
