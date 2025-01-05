/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

public interface scrun {
    public boolean couldSurviveDuringGameDeinit();

    public void run();

    public void finish();

    public void finishImmediately();

    public void start();

    public boolean finished();

    public boolean started();

    public void add(scrun var1);

    public boolean marked();

    public void mark(boolean var1);
}

