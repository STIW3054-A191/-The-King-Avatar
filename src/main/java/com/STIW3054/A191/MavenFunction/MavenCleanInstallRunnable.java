package com.STIW3054.A191.MavenFunction;

import com.STIW3054.A191.Output.OutputLogFile;
import com.STIW3054.A191.OutputFolderPath.RepoFolderPath;
import com.STIW3054.A191.UrlDetails;
import com.STIW3054.A191.OutputResult;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;


public class MavenCleanInstallRunnable implements Runnable{
    private String repoUrl;
    private int totalRepo;
    private CountDownLatch latch;
    private ArrayList<String[]> buildSuccessRepo;

    public MavenCleanInstallRunnable(String RepoUrl, int TotalRepo, CountDownLatch Latch, ArrayList<String[]> BuildSuccessRepo) {
        this.repoUrl = RepoUrl;
        this.totalRepo = TotalRepo;
        this.latch = Latch;
        this.buildSuccessRepo = BuildSuccessRepo;
    }

    @Override
    public void run() {

        String repoPath = RepoFolderPath.getPath()+UrlDetails.getRepoName(repoUrl);
        String repoName = UrlDetails.getRepoName(repoUrl);

        String pomPath = PomPath.getPath(new File(repoPath));
        if(pomPath!=null) {

            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(pomPath));
            request.setGoals(Collections.singletonList("clean install"));

            try {

                Invoker invoker = new DefaultInvoker();
                invoker.setLogger(new PrintStreamLogger(System.out, InvokerLogger.ERROR));

                StringBuilder output = new StringBuilder();
                output.append("\n");

                invoker.setOutputHandler(new InvocationOutputHandler() {
                    @Override
                    public void consumeLine(String s) throws IOException {
                        output.append(s).append("\n");
                    }
                });

                invoker.setErrorHandler(new InvocationOutputHandler() {
                    @Override
                    public void consumeLine(String s) throws IOException {
                        output.append(s).append("\n");
                    }
                });

                final InvocationResult invocationResult = invoker.execute(request);

                if (invocationResult.getExitCode() == 0) {
                    buildSuccessRepo.add(new String[]{pomPath,repoName,UrlDetails.getMatric(repoUrl)});
                    OutputResult.print(false,repoName,"Build Success !", latch, totalRepo);
                } else {
                    //Save error to log
                    OutputLogFile.save(UrlDetails.getMatric(repoUrl),repoName,output.toString());
                    OutputResult.print(true,repoName,"Build Failure !", latch, totalRepo);
                }

            } catch (MavenInvocationException e) {
                e.printStackTrace();
            }

        }else {
            //Save error to log
            OutputLogFile.save(UrlDetails.getMatric(repoUrl),repoName,repoName + " No pom.xml file !");
            OutputResult.print(true,repoName,"No pom.xml file !", latch, totalRepo);
        }
    }
}
