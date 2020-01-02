package com.STIW3054.A191.Ckjm;

import com.STIW3054.A191.Output.OutputLogFile;
import com.STIW3054.A191.ExcelFunction.SaveCkjmToExcel;
import com.STIW3054.A191.OutputFolder.OutputFolderPath;
import com.STIW3054.A191.Output.OutputResult;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class TestCkjmRunnable implements Runnable {

    private String repoName, matricNo;
    private CountDownLatch latch;
    private int totalLatch;
    private ArrayList<String> unknownMatricNo;

    public TestCkjmRunnable(String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch, ArrayList<String> UnknownMatricNo) {
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
        this.unknownMatricNo = UnknownMatricNo;
    }

    @Override
    public void run() {

        ArrayList<String> classPathArr = ClassPath.getPath(repoName);

        if (!classPathArr.isEmpty()) {

            try ( FileWriter writer = new FileWriter(OutputFolderPath.getTxtFolderPath() + matricNo + ".txt", true)) {

                int WMC = 0, DIT = 0, NOC = 0, CBO = 0, RFC = 0, LCOM = 0;

                for (String classPath : classPathArr) {
                    String result = testClass(classPath);

                    writer.write(result + "\n");

                    if (!result.split(" ")[1].equals("null")) {
                        WMC += Integer.parseInt(result.split(" ")[1]);
                        DIT += Integer.parseInt(result.split(" ")[2]);
                        NOC += Integer.parseInt(result.split(" ")[3]);
                        CBO += Integer.parseInt(result.split(" ")[4]);
                        RFC += Integer.parseInt(result.split(" ")[5]);
                        LCOM += Integer.parseInt(result.split(" ")[6]);
                    }
                }

                synchronized (TestCkjmRunnable.class) {
                    SaveCkjmToExcel.addData(matricNo, unknownMatricNo, WMC, DIT, NOC, CBO, RFC, LCOM);
                }
                writer.write("Total : " + WMC + " " + DIT + " " + NOC + " " + CBO + " " + RFC + " " + LCOM + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }

            OutputResult.print(false, repoName, "Test CKJM Completed !", latch, totalLatch);

        } else {
            OutputLogFile.save(matricNo, repoName, "No class file !");
            OutputResult.print(true, repoName, "No class file !", latch, totalLatch);
        }
    }

    private static String testClass(String ClassPath) {

        String result = null;

        Runtime rt = Runtime.getRuntime();

        try {
            Process proc = rt.exec("java -jar " + CkjmPath.getPath() + " " + ClassPath);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            result = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (result == null) {
            result = new File(ClassPath).getName().replaceAll(".class", "") + " null";
        }

        return result;
    }
}
