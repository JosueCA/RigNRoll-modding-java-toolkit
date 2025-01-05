/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import rnrscenario.missions.PriorityTable;
import rnrscenario.missions.requirements.Requirement;
import rnrscenario.missions.requirements.RequirementList;

public final class AndRequirement
extends RequirementList {
    static final long serialVersionUID = 0L;

    AndRequirement(PriorityTable table) {
        this.setPriorityTable(table);
    }

    public boolean check() {
        for (Requirement requirement : this.getList()) {
            if (requirement.check()) continue;
            return false;
        }
        return true;
    }

    public int getPriorityIncrement() {
        int maxPriorityIncrement = 0;
        for (Requirement requirement : this.getList()) {
            maxPriorityIncrement = Math.max(maxPriorityIncrement, requirement.getPriorityIncrement());
        }
        return maxPriorityIncrement;
    }
}

