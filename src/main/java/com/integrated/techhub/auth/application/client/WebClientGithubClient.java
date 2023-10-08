package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.response.GithubPrInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static com.integrated.techhub.auth.util.GithubApiConstants.getListPullRequestUrl;

@Component
@RequiredArgsConstructor
public class WebClientGithubClient {

    private final WebClient webClient;

    public List<GithubPrInfoResponse> getPrsByRepoName(final String accessToken, final String repo) {
        final List<GithubPrInfoResponse> responses = new ArrayList<>();
        final List<String> prRequestUrls = createPrApiRequestUrls(repo, 80);

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
            prRequestUrls.add(getListPullRequestUrl(repo, page, 10));
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

}
