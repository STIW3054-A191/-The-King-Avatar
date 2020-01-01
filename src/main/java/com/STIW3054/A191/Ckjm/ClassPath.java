package com.STIW3054.A191.Ckjm;

import com.STIW3054.A191.OutputFolder.OutputFolderPath;

import java.io.File;
import java.util.ArrayList;

class ClassPath {

    static ArrayList<String> getPath(String RepoName){
        return findClass(new File(OutputFolderPath.getRepoFolderPath()+RepoName));
    }

    private static ArrayList<String> findClass(File dir) {

        ArrayList<String> path = new ArrayList<>();

        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    File aChild = new File(dir, child);
                    if (child.endsWith(".class")) {
                        path.add(aChild.getPath());
                    } else if (aChild.isDirectory()) {
                         path.addAll(findClass(aChild));
                    }
                }
            }
        }
        return path;
    }
}

