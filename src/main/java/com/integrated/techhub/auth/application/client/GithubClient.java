package com.integrated.techhub.auth.application.client;

import com.integrated.techhub.auth.application.client.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.application.client.dto.response.OAuthTokensResponse;

import java.util.List;

public interface GithubClient {

    OAuthTokensResponse getGithubTokens(final String code);
    OAuthTokensResponse getNewAccessToken(final String refreshToken);

    OAuthGithubUsernameResponse getGithubUsername(final String accessToken);

    List<OAuthCrewGithubPrResponse> getPrsByRepoName(final String accessToken, final String repo);

}
