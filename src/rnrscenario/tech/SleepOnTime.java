/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.tech;

import rnrcore.eng;

public class SleepOnTime {
    public SleepOnTime(int milleseconds) {
        this.simplewaitFor(milleseconds);
    }

    void simplewaitFor(int timemillesec) {
        try {
            this.waitFor(timemillesec);
        }
        catch (InterruptedException e) {
            eng.writeLog("Script Error. Stage sc00324 error.");
        }
    }

    void waitFor(int timemillesec) throws InterruptedException {
        Thread.sleep(timemillesec);
    }
}

