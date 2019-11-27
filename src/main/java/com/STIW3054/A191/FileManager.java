package com.STIW3054.A191;

import java.io.File;

class FileManager {
    static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // Delete all child
            if(children!=null) {
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
