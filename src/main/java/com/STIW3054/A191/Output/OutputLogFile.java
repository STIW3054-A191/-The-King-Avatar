package com.STIW3054.A191.Output;

import com.STIW3054.A191.OutputFolder.OutputFolderPath;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class OutputLogFile {

    public static void save(String MatricNo, String RepoName, String Message) {

        String logFilePath = OutputFolderPath.getLogFolderPath() + MatricNo + ".log";
        try {
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            Logger logger = Logger.getLogger(RepoName);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            logger.warning(Message);
            fileHandler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
