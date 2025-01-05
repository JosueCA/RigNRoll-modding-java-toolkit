/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

public class Resumer {
    private final Object monitor;

    public Resumer(Object synchronizationMonitor) {
        if (null == synchronizationMonitor) {
            throw new IllegalArgumentException("synchronizationMonitor or notificationClient is null");
        }
        this.monitor = synchronizationMonitor;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resume() {
        Object object = this.monitor;
        synchronized (object) {
            this.monitor.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void resumeAll() {
        Object object = this.monitor;
        synchronized (object) {
            this.monitor.notifyAll();
        }
    }
}

