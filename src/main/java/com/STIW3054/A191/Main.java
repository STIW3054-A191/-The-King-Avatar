package com.STIW3054.A191;

import com.STIW3054.A191.Ckjm.TestCkjmRunnable;
import com.STIW3054.A191.CloneRepo.CloneRepoRunnable;
import com.STIW3054.A191.CloneRepo.RepoLink;
import com.STIW3054.A191.ExcelFunction.CreateExcel;
import com.STIW3054.A191.ExcelFunction.GetListOfStudents;
import com.STIW3054.A191.ExcelFunction.StackedBarChart;
import com.STIW3054.A191.Jar.RunJarRunnable;
import com.STIW3054.A191.MavenFunction.MavenCleanInstallRunnable;
import com.STIW3054.A191.MavenFunction.MavenHome;
import com.STIW3054.A191.OutputFolder.CheckOutputFolder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        //Get start time
        TimeElapsed.start();

        // Set maven home for invoker used
        System.out.println("Checking Maven Home...");
        MavenHome.setHome();

        // Delete /target/repo/ folder
        System.out.println("\nChecking folder...\n/target/output/");
        CheckOutputFolder.check();

        //Create Excel file and get List Of Students
        System.out.println("\nCreate Excel file and get List Of Students...");
        CreateExcel.create();
        GetListOfStudents.get();

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
        latchCloneRepo.await();
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
        latchMavenCleanInstall.await();
        System.out.println("Maven Build Completed !");


        System.out.println("\nRun Jar...");
        CountDownLatch latchRunJar = new CountDownLatch(buildSuccessRepo.size());
        ExecutorService execRunJar = Executors.newFixedThreadPool(Threads.availableLightThreads());
        for(String[] repoDetails : buildSuccessRepo){
            Thread threadRunJar = new Thread(new RunJarRunnable(repoDetails[0], repoDetails[1], repoDetails[2],latchRunJar,buildSuccessRepo.size()));
            execRunJar.execute(threadRunJar);
        }
        execRunJar.shutdown();
        latchRunJar.await();
        System.out.println("Run Jar Completed !");


        System.out.println("\nTest CKJM...");
        PrintStream console = System.err;
        ArrayList<String> unknownMatricNo = new ArrayList<>();
        CountDownLatch latchTestCkjm = new CountDownLatch(buildSuccessRepo.size());
        ExecutorService execTestCkjm = Executors.newFixedThreadPool(Threads.availableLightThreads());
        for(String[] repoDetails : buildSuccessRepo){
            Thread threadTestCkjm = new Thread(new TestCkjmRunnable(repoDetails[1], repoDetails[2], latchTestCkjm, buildSuccessRepo.size(), unknownMatricNo, console));
            execTestCkjm.execute(threadTestCkjm);
        }
        execTestCkjm.shutdown();
        latchTestCkjm.await();
        System.out.println("Test CKJM Completed !");

        if(unknownMatricNo.size()>0){
            for(String matric : unknownMatricNo){
                System.err.println("\nMatric No "+matric+" not found in list of students!");
            }
        }

        System.out.println("\nCreate Bar Chart...");
        StackedBarChart.create();

        //Get end time and time elapsed
        TimeElapsed.endAndOutput();
    }
}
