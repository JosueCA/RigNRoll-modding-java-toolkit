/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.consistency.ScenarioClass;

@ScenarioClass(scenarioStage=-1, fieldWithDesiredStage="creationStage")
public final class Location {
    private final int creationStage;
    private final vectorJ position;
    private final matrixJ orientation;

    int getCreationStage() {
        return this.creationStage;
    }

    Location(int stage2, vectorJ position, matrixJ orientation) {
        this.position = position;
        this.orientation = orientation;
        this.creationStage = stage2;
    }

    public vectorJ getPosition() {
        return this.position;
    }

    public matrixJ getOrientation() {
        return this.orientation;
    }
}

