/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import rnrorg.journal;
import rnrscenario.sctask;

public class RenewJourmalAfterLoad
extends sctask {
    public RenewJourmalAfterLoad() {
        super(3, false);
        this.start();
    }

    public void run() {
        journal.getInstance().resortactiveNotes();
        this.finish();
    }
}

