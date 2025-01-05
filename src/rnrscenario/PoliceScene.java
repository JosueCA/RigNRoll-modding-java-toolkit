/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.Vector;
import menu.menues;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.traffic;
import rnrcore.vectorJ;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscr.Helper;
import rnrscr.SyncMonitors;
import rnrscr.trackscripts;
import scriptEvents.EventsControllerHelper;

public class PoliceScene {
    public static boolean reserved_for_scene = false;
    public static String MESSAGE_FOR_SCENE = "police_scene_end";
    static final String light = "PoliceSituation";
    static final String serious = "PoliceSituation2";
    static final String crime = "PoliceSituation2";
    static final String car_scene = "PoliceSituation2";
    private static CPlayPoliceScene scene = null;

    public static void playLight(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix, vectorJ position, matrixJ Police_matrix, vectorJ Police_position, matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = PoliceScene.play(false, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix, Quilt_position, false, onSpecObject);
    }

    public static void playSerious(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix, vectorJ position, matrixJ Police_matrix, vectorJ Police_position, matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = PoliceScene.play(true, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix, Quilt_position, false, onSpecObject);
    }

    public static void playCrime(boolean onSpecObject, actorveh guild, actorveh police, matrixJ matrix, vectorJ position, matrixJ Police_matrix, vectorJ Police_position, matrixJ Quilt_matrix, vectorJ Quilt_position) {
        police.setLockPlayer(true);
        scene = PoliceScene.play(true, guild, police, matrix, position, Police_matrix, Police_position, Quilt_matrix, Quilt_position, true, onSpecObject);
    }

    public static void playCreatedLight() {
        PoliceScene.playCreated(light);
    }

    public static void playCreatedSerious() {
        PoliceScene.playCreated("PoliceSituation2");
    }

    public static void playCreatedCrime() {
        PoliceScene.playCreated("PoliceSituation2");
    }

    public static void playCreated(String scenename) {
        if (null == scene) {
            eng.err("ERROR. Police scene wrong order of playing. info " + scenename);
            return;
        }
        PoliceScene.scene.preset.scenename = scenename;
        ThreadTask.create(scene);
        scene = null;
    }

    private static CPlayPoliceScene play(boolean car_scene, actorveh guild, actorveh police, matrixJ matrix, vectorJ position, matrixJ Police_matrix, vectorJ Police_position, matrixJ Quilt_matrix, vectorJ Quilt_position, boolean gameover, boolean onSpecObject) {
        CPlayPoliceScene scene = new CPlayPoliceScene(guild, police);
        scene.gameover = gameover;
        scene.onSpecObject = onSpecObject;
        if (car_scene) {
            vectorJ pos1 = police.gPosition();
            vectorJ pos2 = new vectorJ(pos1);
            pos1.z += 10.0;
            pos2.z -= 10.0;
            vectorJ scenepos = Collide.collidePoint(pos1, pos2);
            if (scenepos == null) {
                scenepos = pos1;
            }
            scene.preset = new SPoliceScenePreset(police.gMatrix(), scenepos);
            scene.preset.policecar = police;
            scene.preset.M_police = Police_matrix;
            scene.preset.P_police = Police_position;
            scene.preset.M_quilt = Quilt_matrix;
            scene.preset.P_quilt = Quilt_position;
        } else {
            vectorJ pos1 = new vectorJ(position);
            vectorJ pos2 = new vectorJ(pos1);
            pos1.z += 10.0;
            pos2.z -= 10.0;
            scene.preset = new SPoliceScenePreset(matrix, position);
            scene.preset.M_police = Police_matrix;
            scene.preset.P_police = Police_position;
            scene.preset.M_quilt = Quilt_matrix;
            scene.preset.P_quilt = Quilt_position;
        }
        return scene;
    }

    static class CPlayPoliceScene
    implements Ithreadprocedure {
        boolean gameover = false;
        boolean onSpecObject = false;
        ThreadTask safe = null;
        Vector pool = new Vector();
        SPoliceScenePreset preset;
        public volatile actorveh quiltPlayer;
        public volatile actorveh policePlayer;

        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        CPlayPoliceScene(actorveh quiltPlayer, actorveh policePlayer) {
            this.quiltPlayer = quiltPlayer;
            this.policePlayer = policePlayer;
        }

        public void call() {
            if (!Helper.isCarLive(this.quiltPlayer)) {
                eng.err("WARNING. Trying to judge non live player.");
                return;
            }
            this.quiltPlayer.sVeclocity(0.0);
            this.policePlayer.sVeclocity(0.0);
            this.quiltPlayer.setHandBreak(true);
            this.policePlayer.setHandBreak(true);
            eng.disableControl();
            eng.SwitchDriver_outside_cabin(this.quiltPlayer.getCar());
            new trackscripts(SyncMonitors.getScenarioMonitor()).PlaySceneXMLThreaded("justfade", false, this.safe);
            eng.lock();
            this.policePlayer.setLockPlayer(true);
            if (!this.onSpecObject) {
                traffic.setTrafficModeTemporary(1);
                traffic.cleanTrafficImmediatelyTemporary(500.0, this.preset.P);
                eng.pauseGameExceptPredefineAnimation(true);
                this.policePlayer.sPosition(this.preset.P_police, this.preset.M_police);
                this.quiltPlayer.sPosition(this.preset.P_quilt, this.preset.M_quilt);
            }
            eng.unlock();
            long scene = trackscripts.initSceneXML(this.preset.scenename, this.pool, this.preset);
            scenetrack.ReplaceSceneFlags(scene, 9);
            event.eventObject((int)scene, this.safe, "_resum");
            this.safe._susp();
            scenetrack.DeleteScene(scene);
            this.policePlayer.setLockPlayer(false);
            eng.lock();
            Helper.restoreCameraToIgrokCar();
            if (!this.onSpecObject) {
                eng.pauseGameExceptPredefineAnimation(false);
                traffic.setTrafficModeTemporary(0);
                traffic.restoreTrafficImmediatelyTemporary();
            }
            if (!reserved_for_scene) {
                this.eventMenu();
            } else {
                this.scene();
            }
            eng.unlock();
            eng.SwitchDriver_in_cabin(this.quiltPlayer.getCar());
            eng.enableControl();
            this.policePlayer.releasePedalBrake();
            this.quiltPlayer.setHandBreak(false);
            this.policePlayer.setHandBreak(false);
        }

        public void eventMenu() {
            if (!this.gameover) {
                event.Setevent(9801);
            } else {
                menues.gameoverMenuJail();
            }
        }

        public void scene() {
            EventsControllerHelper.messageEventHappened(MESSAGE_FOR_SCENE);
        }
    }

    static class SPoliceScenePreset {
        vectorJ P;
        matrixJ M;
        vectorJ P_police;
        matrixJ M_police;
        vectorJ P_quilt;
        matrixJ M_quilt;
        String scenename;
        actorveh policecar = null;

        SPoliceScenePreset(matrixJ matrix, vectorJ position) {
            this.P = position;
            this.M = matrix;
        }
    }
}

