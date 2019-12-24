package com.STIW3054.A191.MavenFunction;

import com.STIW3054.A191.CloneRepo.RepoFolderPath;
import com.STIW3054.A191.UrlDetails;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MavenCleanInstallRunnable implements Runnable{
    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;
    private ArrayList<String> successRepoPomPath;

    public MavenCleanInstallRunnable(String RepoUrl, int TotalRepo, CountDownLatch Latch, ArrayList<String> SuccessRepoPomPath) {
        this.repoUrl = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
        this.successRepoPomPath = SuccessRepoPomPath;
    }

    @Override
    public void run() {

        String repoPath = RepoFolderPath.getPath()+UrlDetails.getRepoName(repoUrl);
        String logFilePath = RepoFolderPath.getPath().substring(1) + UrlDetails.getMatric(repoUrl) + ".log";
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
                        successRepoPomPath.add(pomPath);
                        printResult(false,repoName,"Build Success !");
                    }
                } else {

                    printResult(true,repoName,"Build Failure !");
                }

            } catch (FileNotFoundException | MavenInvocationException e) {
                e.printStackTrace();
            }
        }else {

            //Save error to log
            try {
                FileHandler fileHandler = new FileHandler(logFilePath, true);
                fileHandler.setFormatter(new SimpleFormatter());
                Logger logger = Logger.getLogger(repoName);
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
                logger.warning(repoName + " no pom.xml file.");
                fileHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            printResult(true,repoName,"No pom.xml file !");
        }
    }

    private void printResult(boolean Error, String RepoName,String Comment){

        synchronized (MavenCleanInstallRunnable.class) {

            if(Error){
                System.err.format("%-10s %-40s %-20s\n",
                        totalRepo - latch.getCount()+1 + "/" + totalRepo,
                        RepoName,
                        Comment);
            }
            else {
                System.out.format("%-10s %-40s %-20s\n",
                        totalRepo - latch.getCount()+1 + "/" + totalRepo,
                        RepoName,
                        Comment);

            }
            latch.countDown();
        }
    }
}
