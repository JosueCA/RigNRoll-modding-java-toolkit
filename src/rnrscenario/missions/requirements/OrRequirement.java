/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.PriorityTable;
import rnrscenario.missions.requirements.Requirement;
import rnrscenario.missions.requirements.RequirementList;

public final class OrRequirement
extends RequirementList {
    static final long serialVersionUID = 0L;

    OrRequirement(PriorityTable table) {
        this.setPriorityTable(table);
    }

    public boolean check() {
        for (Requirement requirement : this.getList()) {
            if (!requirement.check()) continue;
            return true;
        }
        return false;
    }

    Requirement optimize() {
        if (1 == this.getList().size()) {
            return this.getList().iterator().next();
        }
        return this;
    }

    public int getPriorityIncrement() {
        int maxPriorityIncrement = 0;
        for (Requirement requirement : this.getList()) {
            if (!requirement.check()) continue;
            maxPriorityIncrement = Math.max(maxPriorityIncrement, requirement.getPriorityIncrement());
        }
        return maxPriorityIncrement;
    }
}

