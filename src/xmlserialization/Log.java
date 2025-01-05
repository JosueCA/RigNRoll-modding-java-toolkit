/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import rnrcore.eng;

public class Log {
    private static boolean wasLog = false;

    public static boolean wasLogRecord() {
        return wasLog;
    }

    public static void resetWasLog() {
        wasLog = false;
    }

    public static void warning(String message) {
        if (eng.noNative) {
            wasLog = true;
        }
        eng.log(message);
    }

    public static void error(String message) {
        if (eng.noNative) {
            wasLog = true;
        }
        eng.log(message);
    }
}

