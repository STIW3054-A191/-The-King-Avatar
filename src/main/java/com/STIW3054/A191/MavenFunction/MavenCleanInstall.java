package com.STIW3054.A191.MavenFunction;

import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.util.Collections;

public class MavenCleanInstall {
    public static int cleanInstall(String pomPath){
        int result = 1;
        MavenHome.setHome();
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomPath));
        request.setGoals(Collections.singletonList("clean install"));

        try {

            Invoker invoker = new DefaultInvoker();
            invoker.setLogger(new PrintStreamLogger(System.out, InvokerLogger.ERROR));

            invoker.setOutputHandler(System.out::println);
            invoker.setErrorHandler(System.err::println);

            final InvocationResult invocationResult = invoker.execute(request);

            result = invocationResult.getExitCode();

        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
