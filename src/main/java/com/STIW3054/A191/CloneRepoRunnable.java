package com.STIW3054.A191;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.concurrent.CountDownLatch;

class CloneRepoRunnable implements Runnable {

    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;

    CloneRepoRunnable(String RepoUrl, int TotalRepo, CountDownLatch Latch) {
        this.repoUrl = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
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
            System.out.format("%-10s %-40s %-20s\n",
                    totalRepo - latch.getCount()+1 + "/" + totalRepo,
                    UrlDetails.getRepoName(repoUrl),
                    "Cloning Completed !");

            latch.countDown();
        }
    }
}
