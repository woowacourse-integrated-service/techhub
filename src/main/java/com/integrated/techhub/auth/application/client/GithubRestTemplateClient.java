package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.request.GithubOAuthTokenRequest;
import com.integrated.techhub.auth.application.client.dto.request.OAuthTokenRefreshRequest;
import com.integrated.techhub.auth.application.client.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.integrated.techhub.auth.util.GithubApiConstants.*;
import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
public class GithubRestTemplateClient implements GithubClient {

    private static final RestTemplate restTemplate = new RestTemplate();

    private final GithubClientProperties githubClientProperties;

    @Override
    public OAuthTokensResponse getGithubTokens(final String code) {
        final String clientId = githubClientProperties.clientId();
        final String clientSecret = githubClientProperties.clientSecret();
        return restTemplate.postForObject(
                getGithubTokenUrl(),
                new GithubOAuthTokenRequest(clientId, clientSecret, code),
                OAuthTokensResponse.class
        );
    }

    @Override
    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        final String clientId = githubClientProperties.clientId();
        final String clientSecret = githubClientProperties.clientSecret();
        return restTemplate.postForObject(
                getNewAccessTokenUrl(clientId, clientSecret, refreshToken),
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
                getMemberInfoUrl(),
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
            final List<OAuthCrewGithubPrResponse> prs = fetchPrs(accessToken, getListPullRequestUrl(repo, page));
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
