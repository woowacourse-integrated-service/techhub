package com.integrated.techhub.auth.presentation;

import com.integrated.techhub.auth.application.AuthService;
import com.integrated.techhub.auth.application.GithubOAuthClient;
import com.integrated.techhub.auth.application.GithubOAuthClientQueryService;
import com.integrated.techhub.auth.application.GithubOAuthService;
import com.integrated.techhub.auth.dto.request.SignUpRequest;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;
import com.integrated.techhub.common.auth.annotation.Auth;
import com.integrated.techhub.common.auth.resolver.AuthProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final GithubOAuthClient githubOAuthClient;
    private final GithubOAuthService githubOAuthService;
    private final GithubOAuthClientQueryService githubOAuthClientQueryService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody @Valid final SignUpRequest request) {
        final Long memberId = authService.registerMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @PutMapping("/login/oauth2/code/github")
    public ResponseEntity<Void> getCrewGithubInfo(
            @Auth AuthProperties authProperties,
            @RequestParam final String code
    ) {
        final OAuthTokensResponse tokensResponse = githubOAuthClient.getAccessToken(code);
        final OAuthCrewGithubUsernameResponse crewInfoResponse = githubOAuthClient.getGithubUsername(
                tokensResponse.accessToken());
        githubOAuthService.setGithubTokens(authProperties.memberId(), tokensResponse, crewInfoResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/prs")
    public ResponseEntity<List<OAuthCrewGithubPrResponse>> getCrewPrList(
            @Auth AuthProperties authProperties,
            @RequestParam final String repo
    ) {
        List<OAuthCrewGithubPrResponse> crewPrList = githubOAuthClientQueryService.getCrewPrList(authProperties.memberId(), repo);
        return ResponseEntity.ok().body(crewPrList);
    }

}
