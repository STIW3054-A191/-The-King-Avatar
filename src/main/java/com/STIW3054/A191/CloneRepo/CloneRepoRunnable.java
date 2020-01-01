package com.STIW3054.A191.CloneRepo;

import com.STIW3054.A191.OutputFolder.OutputFolderPath;
import com.STIW3054.A191.Output.OutputResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class CloneRepoRunnable implements Runnable {

    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;

    public CloneRepoRunnable(String RepoUrl, int TotalRepo, CountDownLatch Latch) {
        this.repoUrl = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
    }

    @Override
    public void run() {

        try {
            Git.cloneRepository()
                    .setURI(repoUrl + ".git")
                    .setDirectory(new File(OutputFolderPath.getRepoFolderPath() + UrlDetails.getRepoName(repoUrl)))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        OutputResult.print(false,UrlDetails.getRepoName(repoUrl),"Cloning Completed !", latch, totalRepo);
    }
}
