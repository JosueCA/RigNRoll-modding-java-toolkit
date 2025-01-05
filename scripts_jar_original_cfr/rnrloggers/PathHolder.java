/*
 * Decompiled with CFR 0.151.
 */
package rnrloggers;

import config.ApplicationFolders;

public final class PathHolder {
    private static String writablePath = ApplicationFolders.RCMDF();

    private PathHolder() {
    }

    public static void setWritablePath(String path2) {
        writablePath = path2;
    }

    public static String getWritablePath() {
        return writablePath;
    }
}

