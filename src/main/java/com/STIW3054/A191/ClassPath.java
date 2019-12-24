package com.STIW3054.A191;

import java.io.File;
import java.util.ArrayList;

public class ClassPath {

    static ArrayList<String> getPath(File dir) {

        ArrayList<String> path = new ArrayList<>();

        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    File aChild = new File(dir, child);
                    if (child.endsWith(".class")) {
                        path.add(aChild.getPath());
                    } else if (aChild.isDirectory()) {
                         path.addAll(getPath(aChild));
                    }
                }
            }
        }
        return path;
    }
}

