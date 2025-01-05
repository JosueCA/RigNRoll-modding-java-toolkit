/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrscr.SyncMonitors;

public class Suspender {
    private final Object monitor;

    public Suspender(Object synchronizationMonitor) {
        if (null == synchronizationMonitor) {
            throw new IllegalArgumentException("synchronizationMonitor is null");
        }
        this.monitor = synchronizationMonitor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void suspend() {
        SyncMonitors.setNotificationFlag(false);
        try {
            Object object = this.monitor;
            synchronized (object) {
                while (!SyncMonitors.isNotificationFlag()) {
                    this.monitor.wait();
                }
            }
        }
        catch (InterruptedException exception) {
            System.out.print(exception.getMessage());
            exception.printStackTrace();
        }
    }
}

