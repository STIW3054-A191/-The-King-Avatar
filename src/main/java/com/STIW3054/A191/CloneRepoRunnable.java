package com.STIW3054.A191;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.concurrent.CountDownLatch;

class CloneRepoRunnable implements Runnable {

    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;

    CloneRepoRunnable(String repoUrl, int totalRepo, CountDownLatch latch) {
        this.repoUrl = repoUrl;
        this.totalRepo = totalRepo;
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            Git.cloneRepository()
                    .setURI(repoUrl + ".git")
                    .setDirectory(new File(RepoFolderPath.getPath() + UrlDetails.getRepoName(repoUrl)))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        synchronized (CloneRepoRunnable.class) {
            latch.countDown();
            System.out.format("%-10s %-40s %-20s\n",
                    totalRepo - latch.getCount() + "/" + totalRepo,
                    UrlDetails.getRepoName(repoUrl),
                    "Cloning Completed !");
        }
    }
}
