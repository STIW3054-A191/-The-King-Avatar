/*package com.STIW3054.A191;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;


class CloneRepoCallable implements Callable<String> {

    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;

    CloneRepoCallable(String repoUrl, int totalRepo, CountDownLatch latch){
        this.repoUrl = repoUrl;
        this.totalRepo = totalRepo;
        this.latch = latch;
    }
    
    @Override
    public String call() throws Exception {
       Thread.sleep(9000);
       //return Thread.currentThread().getName();
       ExecutorService executor = Executors.newFixedThreadPool(28);
       
       //Callable<String> callable = new CloneRepoCallable();
       
       
    try {
            Git.cloneRepository()
                    .setURI(repoUrl + ".git")
                    .setDirectory(new File(RepoPath.getPath() + UrlDetails.getRepoName(repoUrl)))
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        synchronized (CloneRepoRunnable.class) {
            latch.countDown();
            System.out.format("%-10s %-40s %-20s\n",
                    totalRepo-latch.getCount() + "/" + totalRepo,
                    UrlDetails.getRepoName(repoUrl),
                    "Cloning Completed !");
        }
        executor.shutdown();
        return null;
    }
    
}*/
