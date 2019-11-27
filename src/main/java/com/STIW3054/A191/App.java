package com.STIW3054.A191;

public class App {

    public void app() {
        try {
            ScrapeData save1 = new ScrapeData();
            save1.findAll();

            System.out.println("All Cloning Completed");

            Thread.sleep(500);// wait for 2 second
            System.out.println("Repo Written Successfully");
            System.out.println("END");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
