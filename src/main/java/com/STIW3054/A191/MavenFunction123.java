package com.STIW3054.A191;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;

class MavenFunction123 {

    static void cleanInstall(String pomPath,String matricNo){
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomPath));
        request.setGoals(Collections.singletonList("clean install"));

        Invoker invoker = new DefaultInvoker();
        invoker.setLogger(new PrintStreamLogger(System.out ,InvokerLogger.ERROR));

        // Store current System.out before assigning a new value
        PrintStream console = System.out;
        PrintStream o = null;
        try {
            o = new PrintStream(new File(RepoFolderPath.getPath()+matricNo+".log"));
            // Assign o to output stream
            System.setOut(o);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        invoker.setOutputHandler(System.out::println);
        invoker.setErrorHandler(System.err::println);



        try {
            final InvocationResult invocationResult = invoker.execute(request);

            System.setOut(console);
            assert o != null;
            o.close();

            synchronized (MavenFunction123.class) {

                if (invocationResult.getExitCode() == 0) {
                    File logFile = new File(RepoFolderPath.getPath() + matricNo + ".log");
                    boolean success = logFile.delete();
                    if (success) {
                        System.out.println(pomPath + " BUILD SUCCESS");
                    }

                } else {
                    System.err.println(pomPath + " BUILD FAILURE");
                }
            }

        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
    }
}
