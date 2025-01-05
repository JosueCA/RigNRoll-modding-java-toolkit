/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.Location;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="scenarioStage")
final class MeetingPlace {
    private final int scenarioStage;
    private final Location cochLocation;
    private final Location nickLocation;

    MeetingPlace(int scenarioStage, String cochPointName, String nickPointName) throws IllegalStateException {
        cSpecObjects cochPlacementPoint = specobjects.getInstance().GetLoadedNamedScenarioObject(cochPointName);
        if (null == cochPlacementPoint) {
            throw new IllegalStateException("illegal game data state: point coch point was not found");
        }
        Location cochLocation = new Location(scenarioStage, cochPlacementPoint.position, cochPlacementPoint.matrix);
        cSpecObjects nickPlacementPoint = specobjects.getInstance().GetLoadedNamedScenarioObject(nickPointName);
        if (null == nickPlacementPoint) {
            throw new IllegalStateException("illegal game data state: point nick point was not found");
        }
        Location nickLocation = new Location(scenarioStage, nickPlacementPoint.position, nickPlacementPoint.matrix);
        this.cochLocation = cochLocation;
        this.nickLocation = nickLocation;
        this.scenarioStage = scenarioStage;
    }

    Location getCochLocation() {
        return this.cochLocation;
    }

    Location getNickLocation() {
        return this.nickLocation;
    }
}

