package com.STIW3054.A191;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunJar {

    public static void runJar(String PomPath) {
        Runtime rt = Runtime.getRuntime();

        try {
            Process proc = rt.exec("java -jar "+JarPath.getPath(PomPath));


            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            // Read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // Read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }


            System.out.println(proc.exitValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
