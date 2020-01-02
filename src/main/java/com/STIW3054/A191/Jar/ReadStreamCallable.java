package com.STIW3054.A191.Jar;

import com.STIW3054.A191.Output.OutputLogFile;
import com.STIW3054.A191.OutputFolder.OutputFolderPath;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

public class ReadStreamCallable implements Callable<String> {

    private String matricNo, repoName;
    private InputStream inputStream, errorStream;

    ReadStreamCallable(String MatricNo, String RepoName, InputStream InputStream, InputStream ErrorStream) {
        this.matricNo = MatricNo;
        this.repoName = RepoName;
        this.inputStream = InputStream;
        this.errorStream = ErrorStream;
    }

    @Override
    public String call() throws InterruptedException  {

        try {
            // Read the output
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(inputStream));
            String output;
            while ((output = stdInput.readLine()) != null) {
                try ( FileWriter writer = new FileWriter(OutputFolderPath.getOutFolderPath() + matricNo + ".out", true)) {
                    writer.write(output + "\n");
                }
            }

            // Read errors Message
            BufferedReader stdError = new BufferedReader(new InputStreamReader(errorStream));
            StringBuilder errorMessage = new StringBuilder();
            while (stdError.ready()) {
                errorMessage.append(stdError.readLine()).append("\n");
            }

            if (!errorMessage.toString().equals("")) {
                //Save error to log
                OutputLogFile.save(matricNo, repoName, errorMessage.toString());
                return "error";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "complete";
    }
}
