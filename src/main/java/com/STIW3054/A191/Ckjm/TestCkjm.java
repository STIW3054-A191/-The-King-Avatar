package com.STIW3054.A191.Ckjm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TestCkjm {

    public static void test(String PomPath) {
        ArrayList<String> classPathArr = ClassPath.getPath(PomPath);

        if(classPathArr.isEmpty()){
            System.out.println("   No Class File");
        }else {
            for (String classPath : classPathArr ) {
                System.out.println("   " + testClass(classPath));
            }
        }
    }

    private static String testClass(String ClassPath){

        String result = null;

        Runtime rt = Runtime.getRuntime();

        try {
            Process proc = rt.exec("java -jar "+ CkjmPath.getPath() +" "+ClassPath);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            result = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(result == null) {
            result = ""+ClassPath.split("classes")[1].replaceAll(".class","").substring(1).replace("\\",".");
        }

        return result;
    }
}