package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.request.GithubOAuthTokenRequest;
import com.integrated.techhub.auth.application.client.dto.request.OAuthTokenRefreshRequest;
import com.integrated.techhub.auth.application.client.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;
import com.integrated.techhub.auth.util.GithubApiConstants.Auth;
import com.integrated.techhub.auth.util.GithubApiConstants.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

@Component
public class GithubRestTemplateClient implements GithubClient {

    private static final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Override
    public OAuthTokensResponse getGithubTokens(final String code) {
        return restTemplate.postForObject(
                Auth.GITHUB_TOKEN_URL,
                new GithubOAuthTokenRequest(clientId, clientSecret, code),
                OAuthTokensResponse.class
        );
    }

    @Override
    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        return restTemplate.postForObject(
                Auth.GET_NEW_ACCESS_TOKEN_URL,
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
                Member.MEMBER_INFO_URL,
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
            List<OAuthCrewGithubPrResponse> prs = fetchPrs(accessToken, format(Member.LIST_PULL_REQUEST_URL + "?state=all&page=" + page, repo));
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
