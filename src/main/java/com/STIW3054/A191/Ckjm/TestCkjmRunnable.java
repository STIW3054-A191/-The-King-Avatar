package com.STIW3054.A191.Ckjm;

import com.STIW3054.A191.OutputFolderPath.RepoFolderPath;
import com.STIW3054.A191.ExcelFunction.SaveCkjmToExcel;
import com.STIW3054.A191.OutputResult;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TestCkjmRunnable implements Runnable {
    private String repoName,matricNo;
    private CountDownLatch latch;
    private int totalLatch;

    public TestCkjmRunnable(String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch){
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
    }

    @Override
    public void run() {

        ArrayList<String> classPathArr = ClassPath.getPath(repoName);

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

                int WMC = 0, DIT = 0, NOC = 0, CBO = 0, RFC = 0, LCOM = 0;

                for (String classPath : classPathArr ) {
                    String result = testClass(classPath);

                    bw.write(result+"\n");

                    if(!result.split(" ")[1].equals("null")) {
                        WMC += Integer.parseInt(result.split(" ")[1]);
                        DIT += Integer.parseInt(result.split(" ")[2]);
                        NOC += Integer.parseInt(result.split(" ")[3]);
                        CBO += Integer.parseInt(result.split(" ")[4]);
                        RFC += Integer.parseInt(result.split(" ")[5]);
                        LCOM += Integer.parseInt(result.split(" ")[6]);
                    }

                }

                synchronized (TestCkjmRunnable.class) {
                    SaveCkjmToExcel.addData(matricNo, WMC, DIT, NOC, CBO, RFC, LCOM);
                }
                bw.write(WMC+" "+DIT+" "+NOC+" "+CBO+" "+RFC+" "+LCOM+"\n");

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
            result = new File(ClassPath).getName().replaceAll(".class","")+" null";
        }

        return result;
    }
}