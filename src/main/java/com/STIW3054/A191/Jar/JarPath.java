package com.STIW3054.A191.Jar;

import java.io.File;

class JarPath {

    static String getPath(String PomPath) {

        String path = null;

        File dir = new File(PomPath.replaceAll("pom.xml", "target"));

        String[] children = dir.list();
        if (children != null) {
            for (String child : children) {
                File aChild = new File(dir, child);
                if (!child.startsWith("original") && child.endsWith(".jar")) {
                    path = aChild.getPath();
                }
            }
        }

        return path;
    }
}
