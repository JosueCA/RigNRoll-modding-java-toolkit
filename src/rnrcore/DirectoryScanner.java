/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.File;
import java.io.FileFilter;
import rnrcore.IScanFileListener;

public class DirectoryScanner {
    private String path;

    public DirectoryScanner(String path2) {
        this.path = path2;
    }

    private void scanSubdir(String extention, File path2, IScanFileListener listener) {
        if (path2.isDirectory()) {
            File[] files;
            for (File file : files = path2.listFiles(new Filter(extention))) {
                if (file.isFile()) {
                    listener.scan(file.getPath());
                    continue;
                }
                this.scanSubdir(extention, file, listener);
            }
        } else {
            listener.scan(path2.getPath());
        }
    }

    public void scanFiles(String extention, IScanFileListener listener, Type type) {
        File dir = new File(this.path);
        if (dir.isFile()) {
            return;
        }
        File[] files = dir.listFiles(new Filter(extention));
        if (null == files) {
            return;
        }
        switch (type) {
            case subdirs: {
                for (File file : files) {
                    this.scanSubdir(extention, file, listener);
                }
                break;
            }
            case nosubdirs: {
                for (File file : files) {
                    if (!file.isFile()) continue;
                    listener.scan(file.getPath());
                }
                break;
            }
        }
    }

    static class Filter
    implements FileFilter {
        private String extention;

        Filter(String extention) {
            this.extention = extention;
        }

        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }
            return pathname.getName().endsWith(this.extention);
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum Type {
        subdirs,
        nosubdirs;

    }
}

