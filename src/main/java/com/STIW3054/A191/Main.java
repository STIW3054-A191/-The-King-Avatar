package com.STIW3054.A191;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main (String[] args){

        System.out.println("Checking folder...\n/target/repo/");
        // Delete /target/repo/ folder
        File file = new File(RepoPath.getPath());
        FileManager.deleteDir(file);

        System.out.println("\nCheck total Repositories...");
        ArrayList<String> arrLink = RepoLink.getLink();
        int totalRepo = arrLink.size();
        System.out.println("Total Repositories : "+totalRepo);

        System.out.println("\nCheck PC total threads...");
        System.out.format("%-35s: %-20s\n","My PC total threads ", Threads.totalThreads());
        System.out.format("%-35s: %-20s\n","Total threads use for cloning ", Threads.availableThreads());


        //App c_app = new App();
        //c_app.app();
    }

}