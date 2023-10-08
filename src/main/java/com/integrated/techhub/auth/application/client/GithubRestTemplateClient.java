package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.request.GithubTokenRefreshRequest;
import com.integrated.techhub.auth.application.client.dto.request.GithubTokenRequest;
import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
                new GithubTokenRequest(clientId, clientSecret, code),
                OAuthTokensResponse.class
        );
    }

    @Override
    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        final String clientId = githubClientProperties.clientId();
        final String clientSecret = githubClientProperties.clientSecret();
        return restTemplate.postForObject(
                getNewAccessTokenUrl(clientId, clientSecret, refreshToken),
                new GithubTokenRefreshRequest(clientId, clientSecret, refreshToken),
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

    // TODO: Require Refactor
    @Override
    public List<GithubPrInfoResponse> getPrsByRepoName(final String accessToken, final String repo) {
        final List<GithubPrInfoResponse> responses = new ArrayList<>();
        final List<String> getPrsRequestUrls = new ArrayList<>();
//        int page = 1;
//        while (true) {
//            final List<GithubPrInfoResponse> prs = fetchPrs(accessToken, getListPullRequestUrl(repo, page));
//            if (prs.isEmpty()) break;
//            responses.addAll(prs);
//            page++;
//        }
        for (int page = 1; page <= 5; page++) {
            getPrsRequestUrls.add(getListPullRequestUrl(repo, page));
        }
        System.out.println("getPrsRequestUrls = " + getPrsRequestUrls);
        getPrsRequestUrls.parallelStream()
                .forEach(url -> {
                    System.out.println("실행");
                    List<GithubPrInfoResponse> githubPrInfoResponses = fetchPrs(accessToken, url);
                    responses.addAll(githubPrInfoResponses);
                });
        System.out.println("끝");
        return responses;
    }

    private List<GithubPrInfoResponse> fetchPrs(final String accessToken, final String url) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        final HttpEntity<Void> request = new HttpEntity<>(headers);
        return restTemplate.exchange(
                url,
                GET,
                request,
                new ParameterizedTypeReference<List<GithubPrInfoResponse>>() {}
        ).getBody();
    }

}
