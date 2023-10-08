package com.integrated.techhub.auth.util;

public class GithubApiConstants {

    public static class Auth {
        /*
         * [GITHUB_TOKEN_URL, GET_NEW_ACCESS_TOKEN]
         * https://docs.github.com/en/apps/creating-github-apps/authenticating-with-a-github-app/generating-a-user-access-token-for-a-github-app#generating-a-user-access-token-when-a-user-installs-your-app
         * Exchange the code from the previous step for a user access token by making a POST request to this URL,
         * along with the following query parameters: https://github.com/login/oauth/access_token
         * */
        private static final String GITHUB_TOKEN_URL = "https://github.com/login/oauth/access_token";
        private static final String NEW_ACCESS_TOKEN_URL = "https://api.github.com/login/oauth/access_token?clientId=%s&client_secret=%s&grant_type=refresh_token&refresh_token=%s";
    }

    public static String getGithubTokenUrl() {
        return Auth.GITHUB_TOKEN_URL;
    }

    public static String getNewAccessTokenUrl(final String clientId, final String clientSecret, final String githubRefreshToken) {
        return String.format(Auth.NEW_ACCESS_TOKEN_URL, clientId, clientSecret, githubRefreshToken);
    }

    public static class Member {
        /*
         * [LIST_PULL_REQUEST_URL]
         * https://docs.github.com/ko/rest/pulls/pulls?apiVersion=2022-11-28#list-pull-requests
         * Draft pull requests are available in public repositories with GitHub Free and GitHub Free for organizations,
         * GitHub Pro, and legacy per-repository billing plans, and in public and private repositories
         * with GitHub Team and GitHub Enterprise Cloud. For more information,
         * see GitHub's products in the GitHub Help documentation.
         * */
        @Deprecated
        public static final String MEMBER_INFO_URL = "https://api.github.com/user";
        public static final String LIST_PULL_REQUEST_URL = "https://api.github.com/repos/woowacourse/%s/pulls?state=all&page=%d&per_page=100";
    }

    public static String getMemberInfoUrl() {
        return Member.MEMBER_INFO_URL;
    }

    public static String getListPullRequestUrl(final String repoName, final int page) {
        return String.format(Member.LIST_PULL_REQUEST_URL, repoName, page);
    }

}
