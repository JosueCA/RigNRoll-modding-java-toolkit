/*
 * Decompiled with CFR 0.151.
 */
package auxil;

import java.util.Properties;

public class DCProcess {
    public static final native byte[] process(byte[] var0, int var1);

    static {
        try {
            Properties prop = System.getProperties();
            System.err.println("java.library.path: " + prop.getProperty("java.library.path"));
            System.loadLibrary("rnr");
        }
        catch (UnsatisfiedLinkError e) {
            Properties prop = System.getProperties();
            System.err.println("java.library.path: " + prop.getProperty("java.library.path"));
            System.err.println("Cannot link rnr");
        }
    }
}

