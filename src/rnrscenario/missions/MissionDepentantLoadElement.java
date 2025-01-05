/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import java.io.Serializable;
import rnrscenario.missions.MissionPlacement;

public class MissionDepentantLoadElement
implements Serializable {
    static final long serialVersionUID = 0L;
    MissionPlacement placement;
    String parentName;

    public MissionDepentantLoadElement(String parentName, MissionPlacement placement) {
        this.parentName = parentName;
        this.placement = placement;
    }
}

