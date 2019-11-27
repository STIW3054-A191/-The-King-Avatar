package com.STIW3054.A191;

import java.net.URL;

class RepoPath {

    static String getPath(){
        URL location = RepoPath.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().replace("classes/","") + "repo/";
    }

}