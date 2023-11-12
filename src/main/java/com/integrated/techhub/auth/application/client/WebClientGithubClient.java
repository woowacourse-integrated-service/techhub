package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.request.GithubTokenRefreshRequest;
import com.integrated.techhub.auth.application.client.dto.request.GithubTokenRequest;
import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;
import com.integrated.techhub.resilience4j.circuitbreaker.exception.CircuitBreakerInvalidException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static com.integrated.techhub.auth.util.GithubApiConstants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientGithubClient implements GithubClient {

    private static final int LAST_PAGE = 40;
    private static final int PER_PAGE = 10;

    private final WebClient webClient;
    private final GithubClientProperties githubClientProperties;

    @Override
    @CircuitBreaker(name = "webClientGithubClient", fallbackMethod = "throwBadGateway")
    public OAuthTokensResponse getGithubTokens(final String code) {
        final String clientId = githubClientProperties.clientId();
        final String clientSecret = githubClientProperties.clientSecret();

        return webClient.post()
                .uri(getGithubTokenUrl())
                .body(BodyInserters.fromValue(new GithubTokenRequest(clientId, clientSecret, code)))
                .retrieve()
                .bodyToMono(OAuthTokensResponse.class).block();
    }

    @Override
    @CircuitBreaker(name = "webClientGithubClient", fallbackMethod = "throwBadGateway")
    public OAuthTokensResponse getNewAccessToken(final String refreshToken) {
        final String clientId = githubClientProperties.clientId();
        final String clientSecret = githubClientProperties.clientSecret();

        return webClient.post()
                .uri(getNewAccessTokenUrl(clientId, clientSecret, refreshToken))
                .body(BodyInserters.fromValue(new GithubTokenRefreshRequest(clientId, clientSecret, refreshToken)))
                .retrieve()
                .bodyToMono(OAuthTokensResponse.class).block();
    }

    @Override
    @Deprecated
    @CircuitBreaker(name = "webClientGithubClient", fallbackMethod = "throwBadGateway")
    public OAuthGithubUsernameResponse getGithubUsername(final String accessToken) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        return webClient.get()
                .uri(getMemberInfoUrl())
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(OAuthGithubUsernameResponse.class).block();
    }

    /*
     * 조회 속도가 빠르긴 하지만 API 호출 횟수를 많이 사용
     * 인증된 유저 기준 시간당 5,000회
     * using: 사용자가 직접 요청하는 동기화 API
     * */

    @Override
    @CircuitBreaker(name = "webClientGithubClient", fallbackMethod = "fallback")
    public List<GithubPrInfoResponse> getPrsByRepoName(final String accessToken, final String repo) {
        final List<GithubPrInfoResponse> responses = new ArrayList<>();
        final List<String> prRequestUrls = createPrApiRequestUrls(repo, LAST_PAGE);
        Flux.fromIterable(prRequestUrls)
                .flatMap(url -> fetchPrs(accessToken, url))
                .collectList()
                .block()
                .forEach(githubPrInfoResponses -> responses.addAll(githubPrInfoResponses));
        return responses;
    }

    private List<String> createPrApiRequestUrls(final String repo, final int lastPage) {
        List<String> prRequestUrls = new ArrayList<>();
        for (int page = 1; page <= lastPage; page++) {
            prRequestUrls.add(getListPullRequestUrl(repo, page, PER_PAGE));
        }
        return prRequestUrls;
    }

    private Flux<List<GithubPrInfoResponse>> fetchPrs(final String accessToken, final String requestUrl) {
        return webClient.get()
                .uri(requestUrl)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToFlux(GithubPrInfoResponse.class)
                .collectList()
                .flux();
    }

    private List<GithubPrInfoResponse> fallback(Throwable t) {
        throw new CircuitBreakerInvalidException(t);
    }

}
