package com.STIW3054.A191;

import java.io.File;

public class Main {
    public static void main (String[] args){

        System.out.println("Checking folder...\n/target/repo/");

        // Delete /target/repo/ folder
        File file = new File(RepoPath.getPath());
        FileManager.deleteDir(file);

        //App c_app = new App();
        //c_app.app();
    }

}