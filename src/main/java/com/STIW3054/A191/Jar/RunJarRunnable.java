package com.STIW3054.A191.Jar;

import com.STIW3054.A191.Output.OutputResult;
import com.STIW3054.A191.OutputFolder.OutputFolderPath;

import java.io.*;
import java.util.concurrent.*;

public class RunJarRunnable implements Runnable{
    private String pomPath,repoName,matricNo;
    private CountDownLatch latch;
    private int totalLatch;

    public RunJarRunnable(String PomPath, String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch) {
        this.pomPath = PomPath;
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
    }

    @Override
    public void run() {

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar "+ JarPath.getPath(pomPath), null, new File(OutputFolderPath.getRepoFolderPath()+repoName));

            FutureTask<String> futureTask = new FutureTask<>(new ReadStreamCallable(matricNo, repoName, proc.getInputStream (), proc.getErrorStream()));
            new Thread(futureTask).start();

            try {
                String result = futureTask.get(1, TimeUnit.MINUTES);
                if (result.equals("complete")) {
                    OutputResult.print(false, repoName, "Run Jar Completed !", latch, totalLatch);
                } else if (result.equals("error")) {
                    OutputResult.print(true, repoName, "Jar Error !", latch, totalLatch);
                }
            } catch (TimeoutException e) {
                proc.destroy();
                OutputResult.print(true, repoName, "Over waiting time !", latch, totalLatch);
            }

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


}
