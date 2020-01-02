package com.STIW3054.A191.Ckjm;

import com.STIW3054.A191.Output.OutputLogFile;
import com.STIW3054.A191.ExcelFunction.SaveCkjmToExcel;
import com.STIW3054.A191.OutputFolder.OutputFolderPath;
import com.STIW3054.A191.Output.OutputResult;
import gr.spinellis.ckjm.CkjmOutputHandler;
import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.MetricsFilter;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class TestCkjmRunnable implements Runnable {
    private String repoName,matricNo;
    private CountDownLatch latch;
    private int totalLatch;
    private ArrayList<String> unknownMatricNo;
    private PrintStream console;

    public TestCkjmRunnable(String RepoName, String MatricNo, CountDownLatch Latch, int TotalLatch, ArrayList<String> UnknownMatricNo,PrintStream Console){
        this.repoName = RepoName;
        this.matricNo = MatricNo;
        this.latch = Latch;
        this.totalLatch = TotalLatch;
        this.unknownMatricNo = UnknownMatricNo;
        this.console = Console;
    }

    @Override
    public void run() {

        final ArrayList<String> classPathArr = ClassPath.getPath(repoName);

        if(classPathArr.isEmpty()){
            synchronized (TestCkjmRunnable.class) {
                System.setErr(console);
                OutputLogFile.save(matricNo, repoName, "No class file !");
                OutputResult.print(true, repoName, "No class file !", latch, totalLatch);
            }
        }else{

                CkjmOutputHandler outputHandler = new CkjmOutputHandler() {

                    int i = 0, wmc = 0, dit = 0, noc = 0, cbo = 0, rfc = 0, lcom = 0;

                    @Override
                    public void handleClass(String name, ClassMetrics c) {

                        wmc += c.getWmc();
                        dit += c.getDit();
                        noc += c.getNoc();
                        cbo += c.getCbo();
                        rfc += c.getRfc();
                        lcom += c.getLcom();

                        i++;

                        try (FileWriter writer = new FileWriter(OutputFolderPath.getTxtFolderPath() + matricNo + ".txt", true)) {

                            writer.write(name +
                                    "\n WMC : " + c.getWmc() +
                                    ",  DIT : " + c.getDit() +
                                    ", NOC : " + c.getNoc() +
                                    ", CBO : " + c.getCbo() +
                                    ", RFC : " + c.getRfc() +
                                    ", LCOM : " + c.getLcom() + "\n");

                            if (i == classPathArr.size()) {
                                writer.write("Total :\n WMC : " + wmc + ", DIT : " + dit + ", NOC : " + noc + ", CBO : " + cbo + ", RFC : " + rfc + ", LCOM : " + lcom + "\n\n");

                                synchronized (TestCkjmRunnable.class) {
                                    SaveCkjmToExcel.addData(matricNo, unknownMatricNo, wmc, dit, noc, cbo, rfc, lcom);
                                }

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

            synchronized (TestCkjmRunnable.class) {
                // Create a stream to hold the output
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                System.setErr(ps);

                String[] stringArray = classPathArr.toArray(new String[0]);
                MetricsFilter.runMetrics(stringArray, outputHandler);

                System.setErr(console);
                if(!baos.toString().equals("")) {
                    // Show what happened
                    OutputLogFile.save(matricNo, repoName, baos.toString());
                }
            }
            OutputResult.print(false,repoName,"Test CKJM Completed !", latch, totalLatch);
        }
    }
}