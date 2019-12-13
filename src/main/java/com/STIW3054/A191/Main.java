package com.STIW3054.A191;

import com.STIW3054.A191.MavenFunction.MavenCleanInstallRunnable;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    public static void main(String[] args) {

        //Get start time
        TimeElapsed.start();

        // Set maven home for invoker used
        System.out.println("Checking Maven Home...");
        if(System.getProperty("maven.home")==null) {
            System.setProperty("maven.home", MavenHome.getPath());
        }

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
        CountDownLatch latch = new CountDownLatch(totalRepo);
        // Use ExecutorService to set max threads can run in same time. By using 3/4 from My PC total threads.
        ExecutorService exec = Executors.newFixedThreadPool(Threads.availableLightThreads());
        // Create threads to cloning
        for (String link : arrLink) {
            Thread thread = new Thread(new CloneRepoRunnable(link, totalRepo, latch));
            exec.execute(thread);
        }
        exec.shutdown();

        // Wait after all threads completed.
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Cloning Completed !");

        System.out.println("\nMaven Build Repositories...");

        CountDownLatch latchMavenCleanInstall = new CountDownLatch(totalRepo);
        ExecutorService execMavenCleanInstall = Executors.newFixedThreadPool(Threads.availableHeavyThreads());
        for (String link : arrLink) {
            Thread thread = new Thread(new MavenCleanInstallRunnable(link, totalRepo, latchMavenCleanInstall));
            execMavenCleanInstall.execute(thread);
        }
        execMavenCleanInstall.shutdown();

        // Wait after all threads completed.
        try {
            latchMavenCleanInstall.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Cloning Completed !");

        //Get end time and time elapsed
        TimeElapsed.endAndOutput();
    }
}
