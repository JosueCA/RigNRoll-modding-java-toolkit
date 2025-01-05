/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import menuscript.MissionSuccessPicture;
import rnrcore.CoreTime;
import rnrorg.Album;
import rnrorg.organaiser;
import rnrscenario.missions.MissionEndUIController;

final class PostChannelsAfterMenu
extends MissionEndUIController {
    static final long serialVersionUID = 0L;
    private String text = null;
    private String material = null;

    public PostChannelsAfterMenu(String text, String material) {
        this.text = text;
        this.material = material;
    }

    public void endDialog() {
        String description = organaiser.getMissionDescriptionRef(this.missionName);
        CoreTime time = new CoreTime();
        Album.Item add = Album.getInstance().add(description, this.text, time, this.material);
        MissionSuccessPicture.create(add.locdesc, add.loctext, time, this.material);
    }
}

