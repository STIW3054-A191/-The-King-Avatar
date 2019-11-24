package com.STIW3054.A191;

public class App {
    public void app() {
        try {
            saveExcel save1 = new saveExcel();
            save1.saveData();

            System.out.println("\n\nSaving data to Excel...");
            Thread.sleep(2000);// wait for 2 second
            System.out.println("Excel Written Successfully");
            System.out.println("END");
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }
}
