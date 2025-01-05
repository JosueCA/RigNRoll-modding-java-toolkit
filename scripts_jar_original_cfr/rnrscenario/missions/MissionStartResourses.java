/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions;

import rnrscenario.missions.MissionInfo;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.MissionsController;
import rnrscenario.missions.NpcResource;
import rnrscenario.missions.ResourcePlaser;

public class MissionStartResourses
implements MissionsController {
    static final long serialVersionUID = 0L;

    public void uploadMission(MissionInfo target) {
        boolean isLoading = MissionSystemInitializer.getMissionsManager().isLoading();
        NpcResource npcResource = null;
        String mission_name = target.getName();
        if (!isLoading) {
            npcResource = new NpcResource(mission_name);
            target.createItems();
        }
        ResourcePlaser.placeResources(target.getInfoStartChannels(), mission_name, target.isImportant(), false);
        ResourcePlaser.placeResources(target.getInfoEndChannels(), mission_name, target.isImportant(), true);
        if (!isLoading && null != npcResource) {
            npcResource.createNpcResource();
        }
        if (null != npcResource) {
            npcResource.finish();
        }
    }

    public void unloadMission(String target) {
    }
}

