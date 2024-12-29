// Decompiled with: CFR 0.152
// Class Version: 5
package rnrscenario.missions;

import java.io.Serializable;

public abstract class QuestItem
implements Serializable {
    public abstract String getPlacement();

    public abstract void doPlace(String var1);

    public void doPlace(String mission_name, String place_name) {
    }

    public abstract boolean isSemitrailer();
}
