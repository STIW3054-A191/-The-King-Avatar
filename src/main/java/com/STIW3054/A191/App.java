package com.STIW3054.A191;

public class App {
    public void app() {
        try {
            ScrapeData save1 = new ScrapeData();
            save1.findAll();

           System.out.println("\n\nSaving data to Excel...");
           Thread.sleep(1000);// wait for 2 second
           System.out.println("Excel Written Successfully");
           System.out.println("END");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
