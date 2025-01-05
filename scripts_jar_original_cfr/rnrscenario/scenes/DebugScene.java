/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import players.Crew;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscenario.cScriptFuncs;
import rnrscenario.stage;
import rnrscr.trackscripts;

public final class DebugScene
extends stage {
    private static String sceneName = "00030";

    public static void setSceneName(String value) {
        sceneName = value;
    }

    public DebugScene(Object monitor) {
        super(monitor, "DebugScene");
    }

    private Data makeNearCarData() {
        actorveh car = Crew.getIgrokCar();
        vectorJ pos = car.gPosition();
        vectorJ dir = car.gDir();
        dir.mult(10.0);
        pos.oPlus(dir);
        vectorJ posUp = new vectorJ(pos.x, pos.y, pos.z + 100.0);
        vectorJ posDown = new vectorJ(pos.x, pos.y, pos.z - 100.0);
        vectorJ resultPosition = Collide.collidePoint(posUp, posDown);
        if (resultPosition == null) {
            resultPosition = pos;
        }
        return new Data(new matrixJ(), resultPosition);
    }

    protected void playSceneBody(cScriptFuncs automat) {
        Data presets2 = this.makeNearCarData();
        trackscripts track = new trackscripts(this.getSyncMonitor());
        track.PlaySceneXMLThreaded(sceneName, false, presets2);
    }

    static class Data {
        matrixJ M;
        vectorJ P;
        matrixJ mFinish;
        vectorJ pFinish;
        matrixJ M_180;

        Data(matrixJ M, vectorJ P) {
            this.M = M;
            this.P = P;
            this.mFinish = M;
            this.pFinish = P;
            this.M_180 = new matrixJ(M);
            this.M_180.v0.mult(-1.0);
            this.M_180.v1.mult(-1.0);
        }
    }
}

