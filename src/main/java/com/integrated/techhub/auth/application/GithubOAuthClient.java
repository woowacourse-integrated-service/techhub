package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.request.GithubOAuthTokenRequest;
import com.integrated.techhub.auth.dto.request.OAuthTokenRefreshRequest;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;
import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Component
public class GithubOAuthClient implements OAuthClient {

    private static final String MEMBER_INFO_URL = "https://api.github.com/user";
    private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String GET_NEW_ACCESS_TOKEN = "https://api.github.com/login/oauth/access_token?clientId=%s&client_secret=%s&grant_type=refresh_token&refresh_token=%s";
    private static final String LIST_PULL_REQUEST_URL = "https://api.github.com/repos/woowacourse/%s/pulls";
    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Override
    public OAuthTokensResponse getGithubTokens(final String code) {
        return restTemplate.postForObject(
                GITHUB_TOKEN_URL,
                new GithubOAuthTokenRequest(clientId, clientSecret, code),
                OAuthTokensResponse.class
        );
    }

    @Override
    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        return restTemplate.postForObject(
                GET_NEW_ACCESS_TOKEN,
                new OAuthTokenRefreshRequest(clientId, clientSecret, refreshToken),
                OAuthTokensResponse.class
        );
    }

    @Override
    @Deprecated
    public OAuthGithubUsernameResponse getGithubUsername(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                MEMBER_INFO_URL,
                GET,
                request,
                OAuthGithubUsernameResponse.class
        ).getBody();
    }

    @Override
    public List<OAuthCrewGithubPrResponse> getPrsByRepoName(final String accessToken, final String repo) {
        final List<OAuthCrewGithubPrResponse> responses = new ArrayList<>();
        int page = 1;
        while (true) {
            List<OAuthCrewGithubPrResponse> prs = fetchPrs(accessToken, format(LIST_PULL_REQUEST_URL + "?state=all&page=" + page, repo));
            if (prs.isEmpty()) break;
            responses.addAll(prs);
            page++;
        }
        return responses;
    }

    private List<OAuthCrewGithubPrResponse> fetchPrs(final String accessToken, final String url) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<Void> request = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, request, new ParameterizedTypeReference<List<OAuthCrewGithubPrResponse>>() {}).getBody();
    }

}
