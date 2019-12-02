package com.STIW3054.A191;

public class repoNameDetails {
    static String getMatric(String repoName) {
        String[] splitRepoName = repoName.split("-");
        return splitRepoName[0];
    }
}
