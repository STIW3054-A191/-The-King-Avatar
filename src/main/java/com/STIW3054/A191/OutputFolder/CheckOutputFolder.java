package com.STIW3054.A191.OutputFolder;

import java.io.File;

public class CheckOutputFolder {

    public static void check(){

        File repoFolder = new File(OutputFolderPath.getRepoFolderPath());
        if(repoFolder.exists()){
            deleteDir(repoFolder);
        }

        makeDir(repoFolder);
        makeDir(new File(OutputFolderPath.getLogFolderPath()));
        makeDir(new File(OutputFolderPath.getOutFolderPath()));
        makeDir(new File(OutputFolderPath.getTxtFolderPath()));

    }

    private static void makeDir(File Directory){
        if (!Directory.exists()){
            Directory.mkdirs();
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // Delete all child
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // Delete directory and return result
        return dir.delete();
    }


}
