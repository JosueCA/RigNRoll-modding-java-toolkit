/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrscr.Presets;
import rnrscr.STOHistory;

public class STOpresets
extends Presets {
    private int money = 0;
    STOHistory historycomming = new STOHistory();

    public void setMoney(int value) {
        this.money = value;
    }

    public int getMoney() {
        return this.money;
    }

    public STOHistory getHistorycomming() {
        return this.historycomming;
    }

    public void setHistorycomming(STOHistory historycomming) {
        this.historycomming = historycomming;
    }
}

