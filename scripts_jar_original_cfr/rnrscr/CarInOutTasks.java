/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

public class CarInOutTasks {
    private long mTaskToStart;
    private long mTaskToWait;
    private long mTaskToDeleteAll;

    public long getMTaskToStart() {
        return this.mTaskToStart;
    }

    public long getMTaskToWait() {
        return this.mTaskToWait;
    }

    CarInOutTasks(long taskToStart, long taskToWait) {
        this.mTaskToStart = taskToStart;
        this.mTaskToWait = taskToWait;
    }

    CarInOutTasks(long taskToStart, long taskToWait, long taskToDeleteAll) {
        this.mTaskToStart = taskToStart;
        this.mTaskToWait = taskToWait;
        this.mTaskToDeleteAll = taskToDeleteAll;
    }

    public void setMTaskToWait(long taskToWait) {
        this.mTaskToWait = taskToWait;
    }

    public long getMTaskToDeleteAll() {
        return this.mTaskToDeleteAll;
    }

    public void setMTaskToDeleteAll(long taskToDeleteAll) {
        this.mTaskToDeleteAll = taskToDeleteAll;
    }
}

