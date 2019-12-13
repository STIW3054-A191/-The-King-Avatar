package com.STIW3054.A191.MavenFunction;

import com.STIW3054.A191.RepoFolderPath;
import com.STIW3054.A191.UrlDetails;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

public class MavenCleanInstallRunnable implements Runnable{
    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;

    public MavenCleanInstallRunnable(String RepoUrl, int TotalRepo, CountDownLatch Latch) {
        this.repoUrl = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
    }

    @Override
    public void run() {


        String repoPath = RepoFolderPath.getPath()+UrlDetails.getRepoName(repoUrl);
        String logFilePath = RepoFolderPath.getPath() + UrlDetails.getMatric(repoUrl) + ".log";
        String repoName = UrlDetails.getRepoName(repoUrl);


        String pomPath = PomPath.getPath(new File(repoPath));
        if(pomPath!=null) {

            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(pomPath));
            request.setGoals(Collections.singletonList("clean install"));

            try {
                // Store current System.out before assigning a new value
                PrintStream console = System.out;
                // Assign log to output stream
                PrintStream log = new PrintStream(new File(logFilePath));
                System.setOut(log);

                Invoker invoker = new DefaultInvoker();
                invoker.setLogger(new PrintStreamLogger(System.out, InvokerLogger.ERROR));
                invoker.setOutputHandler(System.out::println);
                invoker.setErrorHandler(System.out::println);

                final InvocationResult invocationResult = invoker.execute(request);

                // Assign log to output stream
                System.setOut(console);
                // Close log
                log.close();

                if (invocationResult.getExitCode() == 0) {
                    File logFile = new File(logFilePath);
                    boolean success = logFile.delete();
                    if (success) {
                        System.out.println(repoName + " BUILD SUCCESS");
                    }
                } else {
                    System.err.println(repoName + " BUILD FAILURE");
                }
                latch.countDown();

            } catch (FileNotFoundException | MavenInvocationException e) {
                e.printStackTrace();
            }
        }else {

            latch.countDown();

            //Print error
            System.err.println(" no pom.xml file.");
        }
    }
}
