/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.eng;

public final class Log {
    private Log() {
    }

    public static void rating(String value) {
        eng.writeLog("rating.log " + value);
    }

    public static void menu(String value) {
        eng.writeLog("menu.log " + value);
    }

    public static void printClassInfo(Object ob) {
        Class<?> cls = ob.getClass();
        eng.writeLog("class.log " + cls.getName());
    }

    public static void simpleMessage(String value) {
        eng.writeLog("MESSAGE.\t" + value);
    }
}

