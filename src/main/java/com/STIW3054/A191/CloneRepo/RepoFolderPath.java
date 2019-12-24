package com.STIW3054.A191.CloneRepo;

import java.net.URL;

public class RepoFolderPath {

    public static String getPath(){
        URL location = RepoFolderPath.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().replace("classes/","repo/");
    }

}