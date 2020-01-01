package com.STIW3054.A191.Jar;

import com.STIW3054.A191.Output.OutputLogFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ReadStreamRunnable implements Runnable {
    private String matricNo, repoName;
    private InputStream inputStream, errorStream;

    public ReadStreamRunnable(String MatricNo, String RepoName, InputStream InputStream, InputStream ErrorStream) {
        this.matricNo = MatricNo;
        this.repoName = RepoName;
        this.inputStream = InputStream;
        this.errorStream = ErrorStream;
    }

    public void run () {
        try {
            // Read the output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStream));
            String output;
            System.out.println(matricNo+"Here is the standard output of the command:");
            while ((output = stdInput.readLine()) != null) {
                System.out.println(output);
            }

            // Read errors Message
            BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder errorMessage = new StringBuilder();
            while (stdError.ready()) {
                errorMessage.append(stdError.readLine()).append("\n");
            }

            if(!errorMessage.toString().equals("")){
                //Save error to log
                OutputLogFile.save(matricNo, repoName, errorMessage.toString());

                System.out.println(matricNo+"Here is the standard error of the command (if any):");
                System.err.println(errorMessage.toString());
            }


        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }
}