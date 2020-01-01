package com.STIW3054.A191.CloneRepo;

public class UrlDetails {

    public static String getRepoName(String repoUrl) {
        String[] splitUrl = repoUrl.split("/");
        return splitUrl[splitUrl.length - 1];
    }

    public static String getMatric(String repoUrl) {
        String repoName = getRepoName(repoUrl);
        String[] splitRepoName = repoName.split("-");
        return splitRepoName[0];
    }
}
