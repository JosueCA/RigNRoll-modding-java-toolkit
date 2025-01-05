/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrorg.MissionEventsMaker;
import rnrscenario.missions.IAddItem;

public class AddItemVehicle
implements IAddItem {
    static final long serialVersionUID = 0L;
    private String model;
    private String color = "0";

    public AddItemVehicle(String model, String color) {
        this.model = model;
        this.color = color;
    }

    public void place(String mission_id, String uid) {
        int i_color = 0;
        try {
            i_color = Integer.parseInt(this.color);
        }
        catch (NumberFormatException e) {
            i_color = 0;
        }
        MissionEventsMaker.createQuestItemAddItem(mission_id, this.model, i_color, uid);
    }
}

