// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import rnrorg.IQuestCargo;

public class QuestCargoParams
implements IQuestCargo {
    private boolean has_fragility = false;
    private double fargility = 0.0;

    public void setCargoParams(boolean has_fargility, double fragility) {
        this.has_fragility = has_fargility;
        this.fargility = fragility;
    }

    public boolean hasFragility() {
        return this.has_fragility;
    }

    public double getFragility() {
        return this.fargility;
    }
}
