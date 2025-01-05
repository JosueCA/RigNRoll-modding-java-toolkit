/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.PriorityTable;

public abstract class Requirement {
    private PriorityTable priorityTable = null;

    public abstract boolean check();

    public abstract int getPriorityIncrement();

    final void setPriorityTable(PriorityTable table) {
        this.priorityTable = table;
    }

    final PriorityTable getPriorityTable() {
        return this.priorityTable;
    }
}

