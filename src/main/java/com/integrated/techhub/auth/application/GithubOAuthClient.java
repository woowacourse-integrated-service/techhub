package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.request.OAuthAccessTokenRequest;
import com.integrated.techhub.auth.dto.request.OAuthTokenRefreshRequest;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;
import com.integrated.techhub.member.domain.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Component
public class GithubOAuthClient implements OAuthClient {

    private static final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String CREW_INFO_URL = "https://api.github.com/user";
    private static final String LIST_PULL_REQUEST_URL = "https://api.github.com/repos/%s/%s/pulls";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Override
    public OAuthTokensResponse getAccessToken(final String code) {
        return restTemplate.postForObject(
                ACCESS_TOKEN_URL,
                new OAuthAccessTokenRequest(clientId, clientSecret, code),
                OAuthTokensResponse.class
        );
    }

    @Override
    public OAuthCrewGithubUsernameResponse getGithubUsername(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                CREW_INFO_URL,
                GET,
                request,
                OAuthCrewGithubUsernameResponse.class
        ).getBody();
    }

    @Override
    public List<OAuthCrewGithubPrResponse> getCrewPrInfoList(final Member member, final String accessToken, final String repo) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                format(LIST_PULL_REQUEST_URL, member.getGithubUsername(), repo) + "?state=all&per_page=100",
                GET,
                request,
                new ParameterizedTypeReference<List<OAuthCrewGithubPrResponse>>() {
                }).getBody();
    }

    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        return restTemplate.postForObject(
                ACCESS_TOKEN_URL,
                new OAuthTokenRefreshRequest(clientId, clientSecret, refreshToken),
                OAuthTokensResponse.class
        );
    }

}
