package com.STIW3054.A191.Output;

import java.util.concurrent.CountDownLatch;

public class OutputResult {

    public static void print(boolean Error, String RepoName, String Comment, CountDownLatch Latch, int TotalLatch) {

        synchronized (OutputResult.class) {

            if (Error) {
                System.err.format("%-10s %-40s %-20s\n",
                        TotalLatch - Latch.getCount() + 1 + "/" + TotalLatch,
                        RepoName,
                        Comment);
            } else {
                System.out.format("%-10s %-40s %-20s\n",
                        TotalLatch - Latch.getCount() + 1 + "/" + TotalLatch,
                        RepoName,
                        Comment);

            }
            Latch.countDown();
        }
    }
}
