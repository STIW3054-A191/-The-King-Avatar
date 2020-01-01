package com.STIW3054.A191;

import com.STIW3054.A191.Ckjm.TestCkjmRunnable;
import com.STIW3054.A191.CloneRepo.CloneRepoRunnable;
import com.STIW3054.A191.ExcelFunction.CreateExcel;
import com.STIW3054.A191.ExcelFunction.GetListOfStudents;
import com.STIW3054.A191.OutputFolderPath.CheckOutputFolder;
import com.STIW3054.A191.OutputFolderPath.RepoFolderPath;
import com.STIW3054.A191.CloneRepo.RepoLink;
import com.STIW3054.A191.Jar.RunJar;
import com.STIW3054.A191.MavenFunction.MavenCleanInstallRunnable;
import com.STIW3054.A191.MavenFunction.MavenHome;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static void main(String[] args) {

        //Get start time
        TimeElapsed.start();

        // Set maven home for invoker used
        System.out.println("Checking Maven Home...");
        MavenHome.setHome();

        //Create Excel file and get List Of Students
        System.out.println("\nCreate Excel file and get List Of Students...");
        CreateExcel.create();
        GetListOfStudents.get();

        // Delete /target/repo/ folder
        System.out.println("\nChecking folder...\n/target/output/");
        CheckOutputFolder.check();

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
        ArrayList<String[]> buildSuccessRepo = new ArrayList<>();
        CountDownLatch latchMavenCleanInstall = new CountDownLatch(totalRepo);
        ExecutorService execMavenCleanInstall = Executors.newFixedThreadPool(Threads.availableHeavyThreads());
        for (String link : arrLink) {
            Thread threadMavenCleanInstall = new Thread(new MavenCleanInstallRunnable(link, totalRepo, latchMavenCleanInstall, buildSuccessRepo));
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

        CountDownLatch latchRunJar = new CountDownLatch(buildSuccessRepo.size());
        for(String[] repoDetails : buildSuccessRepo){
            Thread test = new Thread(()->{
                RunJar.runJar(repoDetails[0], repoDetails[1], repoDetails[2]);
                latchRunJar.countDown();
            });
            test.start();
        }

        try {
            latchRunJar.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("\nTest CKJM...");
        CountDownLatch latchTestCkjm = new CountDownLatch(buildSuccessRepo.size());
        ExecutorService execTestCkjm = Executors.newFixedThreadPool(Threads.availableLightThreads());
        for(String[] repoDetails : buildSuccessRepo){
            Thread threadTestCkjm = new Thread(new TestCkjmRunnable(repoDetails[1], repoDetails[2], latchTestCkjm, buildSuccessRepo.size()));
            execTestCkjm.execute(threadTestCkjm);
        }
        execTestCkjm.shutdown();

        try {
            latchTestCkjm.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Test CKJM Completed !");

        //Get end time and time elapsed
        TimeElapsed.endAndOutput();
    }
}
