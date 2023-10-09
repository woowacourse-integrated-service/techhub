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
public class RestTemplateGithubClient implements GithubClient {

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

    /*
     * 조회 속도가 느리긴 하지만 API 호출 횟수를 적게 사용
     * 인증된 유저 기준 시간당 5,000회
     * using: 사용자가 기다릴 필요가 없는 스케쥴러
     * */
    @Override
    @Deprecated // maintenance mode
    public List<GithubPrInfoResponse> getPrsByRepoName(final String accessToken, final String repo) {
        final List<GithubPrInfoResponse> responses = new ArrayList<>();
        int page = 1;
        while (true) {
            final List<GithubPrInfoResponse> prs = fetchPrs(accessToken, getListPullRequestUrl(repo, page, 100));
            if (prs.isEmpty()) break;
            responses.addAll(prs);
            page++;
        }
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
