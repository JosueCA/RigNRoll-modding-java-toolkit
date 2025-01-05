/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

public class SyncMonitors {
    private static final Object scenarioMonitor = new Object();
    private static boolean wasNonification = false;

    public static Object getScenarioMonitor() {
        return scenarioMonitor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setNotificationFlag(boolean value) {
        Object object = scenarioMonitor;
        synchronized (object) {
            wasNonification = value;
        }
    }

    public static boolean isNotificationFlag() {
        return wasNonification;
    }
}

