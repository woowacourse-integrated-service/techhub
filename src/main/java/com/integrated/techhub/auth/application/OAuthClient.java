package com.integrated.techhub.auth.application;

import com.integrated.techhub.auth.dto.response.OAuthCrewGithubPrResponse;
import com.integrated.techhub.auth.dto.response.OAuthCrewGithubUsernameResponse;
import com.integrated.techhub.auth.dto.response.OAuthTokensResponse;
import com.integrated.techhub.member.domain.Member;

import java.util.List;

public interface OAuthClient {

    OAuthTokensResponse getAccessToken(final String code);

    OAuthCrewGithubUsernameResponse getGithubUsername(final String accessToken);

    List<OAuthCrewGithubPrResponse> getCrewPrInfoList(final Member member, final String accessToken, final String repo);

    OAuthTokensResponse getNewAccessToken(final String refreshToken);

}
