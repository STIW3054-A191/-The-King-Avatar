package com.STIW3054.A191.Ckjm;

import com.STIW3054.A191.CloneRepo.RepoFolderPath;
import com.STIW3054.A191.OutputResult;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestCkjmRunnable implements Runnable {
    private String pomPath,repoName,matricNo;
    private CountDownLatch latch;
    private int totalLatch;

    public TestCkjmRunnable(String PomPath, String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch){
        this.pomPath = PomPath;
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
    }

    @Override
    public void run() {

        ArrayList<String> classPathArr = ClassPath.getPath(pomPath);

        if(classPathArr.isEmpty()){

            String logFilePath = RepoFolderPath.getPath() + matricNo + ".log";

            //Save error to log
            try {
                FileHandler fileHandler = new FileHandler(logFilePath, true);
                fileHandler.setFormatter(new SimpleFormatter());
                Logger logger = Logger.getLogger(repoName);
                logger.addHandler(fileHandler);
                logger.setUseParentHandlers(false);
                logger.warning(repoName + " No class file !");
                fileHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputResult.print(true,repoName,"No class file !", latch, totalLatch);

        }else {

            String txtFilePath = RepoFolderPath.getPath() + matricNo + ".txt";

            try (FileWriter writer = new FileWriter(txtFilePath, true);
                 BufferedWriter bw = new BufferedWriter(writer)) {

                for (String classPath : classPathArr ) {
                    bw.write(testClass(classPath)+"\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputResult.print(false,repoName,"Test CKJM Completed !", latch, totalLatch);
        }

    }

    private static String testClass(String ClassPath){

        String result = null;

        Runtime rt = Runtime.getRuntime();

        try {
            Process proc = rt.exec("java -jar "+ CkjmPath.getPath() +" "+ClassPath);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            result = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(result == null) {
            result = ClassPath.split("classes")[1].replaceAll(".class","").substring(1).replace("\\",".")+" null";
        }

        return result;
    }
}