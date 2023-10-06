package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.dto.response.OAuthGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;

import java.util.List;

public interface OAuthClient {

    OAuthTokensResponse getGithubTokens(final String code);
    OAuthTokensResponse getNewAccessToken(final String refreshToken);

    OAuthGithubUsernameResponse getGithubUsername(final String accessToken);

    List<OAuthCrewGithubPrResponse> getPrsByRepoName(final String accessToken, final String repo);

}
