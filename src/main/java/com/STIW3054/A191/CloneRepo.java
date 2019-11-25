package com.STIW3054.A191;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.File;

class CloneRepo {

    static void clone(String repoUrl) {
        try {
            Git.cloneRepository()
                    .setURI(repoUrl + ".git")
                    .setDirectory(new File("/Repo/" + UrlDetails.getRepoName(repoUrl)))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }
}
