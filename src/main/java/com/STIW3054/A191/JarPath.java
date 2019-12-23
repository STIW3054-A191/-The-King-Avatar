package com.STIW3054.A191;

import java.io.File;

class JarPath {

    static String getPath(File dir) {
        String path = null;
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    File aChild = new File(dir, child);
                    if (child.endsWith(".jar")) {
                        path = aChild.getPath();
                        break;
                    } else if (aChild.isDirectory()) {
                        path = getPath(aChild);
                        if (path != null) {
                            break;
                        }
                    }
                }
            }
        }
        return path;
    }
}
