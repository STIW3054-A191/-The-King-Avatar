package com.STIW3054.A191.OutputFolder;

import java.net.URL;

public class OutputFolderPath {

    public static String getOutputFolderPath(){
        URL location = OutputFolderPath.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().substring(1).split("target")[0]+"target/output/";
    }

    public static String getRepoFolderPath(){
        return getOutputFolderPath()+"repo/";
    }

    public static String getLogFolderPath(){
        return getOutputFolderPath()+"log/";
    }

    public static String getOutFolderPath(){
        return getOutputFolderPath()+"out/";
    }

    public static String getTxtFolderPath(){
        return getOutputFolderPath()+"txt/";
    }
}
