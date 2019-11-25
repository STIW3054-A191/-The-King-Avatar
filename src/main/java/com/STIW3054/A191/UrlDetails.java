package com.STIW3054.A191;

public class UrlDetails {

    static String getRepoName(String repoUrl) {
        String[] splitUrl = repoUrl.split("/");
        return splitUrl[splitUrl.length-1];
    }

}
