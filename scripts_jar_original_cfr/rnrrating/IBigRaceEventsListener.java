/*
 * Decompiled with CFR 0.151.
 */
package rnrrating;

public interface IBigRaceEventsListener {
    public int getUid();

    public void raceStarted(boolean var1);

    public void raceFailed();

    public void raceFinished(int var1);
}

