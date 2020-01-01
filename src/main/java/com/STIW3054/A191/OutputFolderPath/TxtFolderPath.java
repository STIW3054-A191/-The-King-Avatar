package com.STIW3054.A191.OutputFolderPath;

import java.net.URL;

public class TxtFolderPath {
    public static String getPath(){
        URL location = OutFolderPath.class.getProtectionDomain().getCodeSource().getLocation();
        return location.getFile().substring(1).replace("classes/","output/txt/");
    }
}
