package com.STIW3054.A191;

import org.apache.maven.shared.invoker.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    public static void main(String[] args) {

        //Get start time
        long startTime = System.currentTimeMillis();

        // Delete /target/repo/ folder
        System.out.println("Checking folder...\n/target/repo/");
        File file = new File(RepoPath.getPath());
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
        System.out.println("\nCloning...");
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

        // Set maven home for invoker used
        System.setProperty("maven.home", MavenHome.getPath());

        //Get all folder inside target/repo
        File repoDir = new File(RepoPath.getPath());
        String[] allRepo = repoDir.list();
        if (allRepo != null) {

            CountDownLatch latch2 = new CountDownLatch(allRepo.length);
            ExecutorService exec2 = Executors.newFixedThreadPool(Threads.availableHeavyThreads());

            for (String repo : allRepo) {

                File aRepoDir = new File(repoDir, repo);
                if (aRepoDir.isDirectory()) {

                    //Check pom.xml file location
                    String pomPath = PomPath.getPath(aRepoDir);
                    if (pomPath != null) {

                        Thread thread = new Thread(() -> {

                            InvocationRequest request = new DefaultInvocationRequest();
                            request.setPomFile(new File(pomPath));
                            request.setGoals(Collections.singletonList("clean install"));

                            Invoker invoker = new DefaultInvoker();
                            invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.ERROR));
                            invoker.setOutputHandler(null);

                            try {
                                if (invoker.execute(request).getExitCode() == 0) {
                                    System.out.println(repo + " BUILD SUCCESS");
                                } else {
                                    System.err.println(repo + " BUILD FAILURE");
                                }
                            } catch (MavenInvocationException e) {
                                e.printStackTrace();
                            }

                            latch2.countDown();

                        });

                        exec2.execute(thread);

                    } else {
                        //For no pom.xml file

                        //Save error to log
                        try {
                            FileHandler fileHandler = new FileHandler(repoDir.getPath() + "/" + repoNameDetails.getMatric(repo) + ".log", true);
                            fileHandler.setFormatter(new SimpleFormatter());
                            Logger logger = Logger.getLogger(repo);
                            logger.addHandler(fileHandler);
                            logger.setUseParentHandlers(false);
                            logger.warning(repo + " no pom.xml file.");
                            fileHandler.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Print error
                        System.err.println(repo + " no pom.xml file.");

                        latch2.countDown();
                    }
                } else {
                    latch2.countDown();
                }
            }

            exec2.shutdown();
            try {
                latch2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //Get end time and time elapsed
        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;

        long days = TimeUnit.MILLISECONDS.toDays(timeElapsed);
        long hours = TimeUnit.MILLISECONDS.toHours(timeElapsed) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeElapsed) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeElapsed) % 60;
        long milliseconds = timeElapsed % 1000;

        System.out.print("\nExecution time : ");

        if (days != 0) {
            System.out.print(days + " Days, " + hours + " Hours, " + minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (hours != 0) {
            System.out.print(hours + " Hours, " + minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (minutes != 0) {
            System.out.print(minutes + " Minutes, " + seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else if (seconds != 0) {
            System.out.print(seconds + " Seconds, " + milliseconds + " Milliseconds.");
        } else {
            System.out.print(milliseconds + " Milliseconds.");
        }

    }
}
