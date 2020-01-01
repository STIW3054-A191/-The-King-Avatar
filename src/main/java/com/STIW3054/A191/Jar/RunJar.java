package com.STIW3054.A191.Jar;

import com.STIW3054.A191.OutputFolderPath.RepoFolderPath;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class RunJar {

    public static void runJar(String PomPath, String RepoName, String MatricNo) {

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("java -jar "+ JarPath.getPath(PomPath), null, new File(RepoFolderPath.getPath()+RepoName));

            Thread s1 = new Thread(new ReadStreamRunnable(MatricNo, RepoName, proc.getInputStream (), proc.getErrorStream()));
            s1.start ();

            if(!proc.waitFor(1, TimeUnit.MINUTES)) {
                //timeout - kill the process.
                proc.destroy(); // consider using destroyForcibly instead
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
