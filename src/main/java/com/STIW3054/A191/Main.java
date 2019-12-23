package com.STIW3054.A191;

import com.STIW3054.A191.MavenFunction.MavenCleanInstallRunnable;
import com.STIW3054.A191.MavenFunction.MavenHome;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        //Get start time
        TimeElapsed.start();

        System.out.println(JarPath.getPath(new File(RepoFolderPath.getPath())));


        Runtime rt = Runtime.getRuntime();

        Process proc = rt.exec("java -jar "+JarPath.getPath(new File(RepoFolderPath.getPath())),null,new File(RepoFolderPath.getPath()));

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // Read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        // Read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }

        System.out.println(proc.exitValue());




/*
        // Set maven home for invoker used
        System.out.println("Checking Maven Home...");
        MavenHome.setHome();

        // Delete /target/repo/ folder
        System.out.println("\nChecking folder...\n/target/repo/");
        File file = new File(RepoFolderPath.getPath());
        FileManager.deleteDir(file);

        // Show total repositories
        System.out.println("\nCheck total Repositories...");
        ArrayList<String> arrLink = RepoLink.getLink();
        int totalRepo = arrLink.size();
        System.out.println("Total Repositories : " + totalRepo);

        // Show PC total threads and threads use for cloning
        System.out.println("\nCheck PC total threads...");
        System.out.format("%-35s: %-20s\n", "My PC total threads ", Threads.totalThreads());
        System.out.format("%-35s: %-20s\n", "Total threads use for cloning ", Threads.availableLightThreads());

        // Cloning all repositories with threads
        System.out.println("\nCloning Repositories...");
        // Use CountDownLatch to check when all threads completed.
        CountDownLatch latchCloneRepo = new CountDownLatch(totalRepo);
        // Use ExecutorService to set max threads can run in same time. By using 3/4 from My PC total threads.
        ExecutorService execCloneRepo = Executors.newFixedThreadPool(Threads.availableLightThreads());
        // Create threads to cloning
        for (String link : arrLink) {
            Thread threadCloneRepo = new Thread(new CloneRepoRunnable(link, totalRepo, latchCloneRepo));
            execCloneRepo.execute(threadCloneRepo);
        }
        execCloneRepo.shutdown();

        // Wait after all threads completed.
        try {
            latchCloneRepo.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Cloning Completed !");

        System.out.println("\nMaven Build Repositories...");

        CountDownLatch latchMavenCleanInstall = new CountDownLatch(totalRepo);
        ExecutorService execMavenCleanInstall = Executors.newFixedThreadPool(Threads.availableHeavyThreads());
        for (String link : arrLink) {
            Thread threadMavenCleanInstall = new Thread(new MavenCleanInstallRunnable(link, totalRepo, latchMavenCleanInstall));
            execMavenCleanInstall.execute(threadMavenCleanInstall);
        }
        execMavenCleanInstall.shutdown();

        // Wait after all threads completed.
        try {
            latchMavenCleanInstall.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Maven Build Completed !");
*/
        //Get end time and time elapsed
        TimeElapsed.endAndOutput();
    }
}
