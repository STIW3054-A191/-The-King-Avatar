package com.STIW3054.A191.Jar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadStreamRunnable implements Runnable{

    private InputStream inputStream, errorStream;

    public ReadStreamRunnable(InputStream InputStream, InputStream ErrorStream) {
        this.inputStream = InputStream;
        this.errorStream = ErrorStream;
    }

    @Override
    public void run() {

        try {
            // Read the output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStream));
            String output;
            while ((output = stdInput.readLine()) != null) {
                System.out.println(output + "\n");
            }

            // Read errors Message
            BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStream));
            while (stdError.ready()) {
                System.err.println(stdError.readLine() +"\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
