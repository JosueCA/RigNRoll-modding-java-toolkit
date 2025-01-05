/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import rnrcore.IXMLSerializable;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrloggers.MissionsLogger;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscr.Helper;
import rnrscr.camscripts;

public class MissionVoter
implements anm {
    private static final String[] SCENE_NAME = new String[]{"voter_man", "voter_woman"};
    private static final String[] SCENE__CALM_NAME = new String[]{"voter_man_calm", "voter_woman_calm"};
    private static final String[] ACTOR_NAME = new String[]{"XXXMan", "XXXWoman"};
    private static final String[][] CLIP_NAMES = new String[][]{{"SC_voter_man_01_Clip", "SC_voter_man_02_Clip", "SC_voter_man_03_Clip"}, {"SC_voter_woman_01_Clip", "SC_voter_woman_02_Clip", "SC_voter_woman_03_Clip"}};
    private static final int MAN = 0;
    private static final int WOMAN = 1;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int CALM = 0;
    private static final double left_angle = -59.0;
    private static final double right_angle = 53.0;
    private static final double time_mix = 1.0;
    private static final double DISTANCE = 100.0;
    private static final double DISTANCE_IMMOBILE = 2.0;
    private static final double TIME_IMMOBILE = 3.0;
    private static final double TIME_MIN_CALM = 5.0;
    private static final double TIME_MIN_ACTIVE = 5.0;
    private static final double TIME_MAX_ACTIVE = 30.0;
    private ScriptRef uid = new ScriptRef();
    private long scene;
    private long scene_calm;
    private int modelIndex = 0;
    private double left_coef = 0.0;
    private double right_coef = 1.0;
    private double calm_coef = 0.0;
    private double calm_state_time = 0.0;
    private boolean calm_state = false;
    private boolean finished = false;
    private vectorJ scenePos = null;
    private matrixJ sceneMat = null;
    private vectorJ lastPos = null;
    private vectorJ lastPosImmobile = null;
    private vectorJ currentPos = null;
    private double countUnmoved = 0.0;
    private boolean immobilised = false;
    private double count_calmstate_time = 0.0;
    private double count_active_time = 0.0;
    private boolean is_calm = true;
    private boolean need_voting = false;
    private boolean not_active = false;
    private boolean first_calm = true;
    private SCRuniperson person = null;
    private static HashMap<String, MissionVoter> anmObjects = new HashMap();

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void updateNative(int p) {
    }

    MissionVoter() {
    }

    MissionVoter(long scene, long scene_calm, int modelIndex, matrixJ M, vectorJ P, SCRuniperson person) {
        this.scene = scene;
        this.modelIndex = modelIndex;
        this.scene_calm = scene_calm;
        this.sceneMat = M;
        this.scenePos = P;
        this.person = person;
    }

    public static void start(String id, SCRuniperson person, matrixJ M, vectorJ P) {
        long scene_calm;
        int index = MissionVoter.defineModelIndex(person);
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(new SceneActorsPool("man", person));
        long scene = scenetrack.CreateSceneXMLPool(SCENE_NAME[index], 517, pool, new preset(M, P));
        if (scene == 0L) {
            MissionsLogger.getInstance().doLog("MissionVoter scene == NULL" + SCENE_NAME[index], Level.INFO);
        }
        if ((scene_calm = scenetrack.CreateSceneXMLPool(SCENE__CALM_NAME[index], 517, pool, new preset(M, P))) == 0L) {
            MissionsLogger.getInstance().doLog("MissionVoter scene_calm == NULL" + SCENE__CALM_NAME[index], Level.INFO);
        }
        MissionVoter obj = new MissionVoter(scene, scene_calm, index, M, P, person);
        eng.CreateInfinitScriptAnimation(obj);
        anmObjects.put(id, obj);
    }

    public static void stop(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).finish();
            anmObjects.remove(id);
        }
    }

    public static void freeVoting(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).voting(false);
        }
    }

    public static void resumeVoting(String id) {
        if (anmObjects.containsKey(id)) {
            anmObjects.get(id).voting(true);
        }
    }

    private static int defineModelIndex(SCRuniperson person) {
        return eng.GetManPrefix(person.nativePointer).contains("Woman") ? 1 : 0;
    }

    public void finish() {
        this.finished = true;
        scenetrack.DeleteScene(this.scene);
        scenetrack.DeleteScene(this.scene_calm);
    }

    public void voting(boolean to_vote) {
        if (!to_vote) {
            scenetrack.StopScene(this.scene);
            scenetrack.StopScene(this.scene_calm);
            this.person.stop();
        } else {
            scenetrack.RunScene(this.scene);
            scenetrack.RunScene(this.scene_calm);
            this.person.play();
            this.not_active = true;
        }
    }

    public boolean real_voting() {
        return this.inActivateRadius() && this.inActivateSemiSphere() && this.need_voting && MissionSystemInitializer.getMissionsManager().getMissionSystemEnable();
    }

    public static void setVoting(String id, boolean value) {
        if (anmObjects.containsKey(id)) {
            MissionVoter missionVoter = anmObjects.get(id);
            missionVoter.need_voting = value;
        }
    }

    public boolean animaterun(double t) {
        if (this.finished) {
            return true;
        }
        this.update();
        this.countImmobile(t);
        this.calCoefs();
        if (this.is_calm) {
            if (t - this.count_calmstate_time > 5.0 && this.real_voting() && !this.immobilised) {
                this.is_calm = false;
                this.count_active_time = t;
            }
        } else if (t - this.count_active_time > 5.0) {
            if (this.real_voting()) {
                if (this.immobilised || t - this.count_active_time > 30.0) {
                    this.is_calm = true;
                    this.count_calmstate_time = t;
                }
            } else {
                this.is_calm = true;
                this.count_calmstate_time = t;
            }
        }
        if (this.not_active) {
            this.is_calm = true;
        }
        if (!this.is_calm) {
            if (this.calm_state) {
                this.calm_state_time = t;
            }
            this.calm_state = false;
            double coef_calm_mix = t - this.calm_state_time > 1.0 ? 0.0 : (1.0 - t + this.calm_state_time) / 1.0;
            this.left_coef *= 1.0 - coef_calm_mix;
            this.right_coef *= 1.0 - coef_calm_mix;
            this.calm_coef = coef_calm_mix;
            this.first_calm = false;
        } else {
            if (!this.calm_state) {
                this.calm_state_time = t;
            }
            this.calm_state = true;
            double tmx = this.first_calm ? 0.0 : 1.0;
            double coef_calm_mix = t - this.calm_state_time >= tmx ? 1.0 : (t - this.calm_state_time) / 1.0;
            this.left_coef *= 1.0 - coef_calm_mix;
            this.right_coef *= 1.0 - coef_calm_mix;
            this.calm_coef = coef_calm_mix;
        }
        scenetrack.ChangeClipParam(this.scene, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][1], this, "setLeftCoef");
        scenetrack.ChangeClipParam(this.scene, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][2], this, "setRightCoef");
        scenetrack.ChangeClipParam(this.scene_calm, ACTOR_NAME[this.modelIndex], CLIP_NAMES[this.modelIndex][0], this, "setCalmCoef");
        return false;
    }

    public void setLeftCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.left_coef;
    }

    public void setRightCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.right_coef;
    }

    public void setCalmCoef(camscripts.trackclipparams pars) {
        pars.Weight = this.calm_coef;
    }

    private void update() {
        this.lastPos = this.currentPos;
        this.currentPos = Helper.getCurrentPosition();
    }

    private void countImmobile(double t) {
        if (null == this.lastPos || this.currentPos == null) {
            return;
        }
        if (null == this.lastPosImmobile) {
            this.lastPosImmobile = this.lastPos;
        }
        if (t - this.countUnmoved > 3.0) {
            if (this.currentPos.len2(this.lastPosImmobile) > 4.0) {
                this.lastPosImmobile = this.currentPos;
                this.countUnmoved = t;
                this.immobilised = false;
            } else {
                this.immobilised = true;
            }
        }
    }

    private void calCoefs() {
        if (null == this.currentPos || null == this.scenePos) {
            return;
        }
        vectorJ R = vectorJ.oMinus(this.currentPos, this.scenePos);
        double cos_x = vectorJ.dot(R.normN(), this.sceneMat.v0.normN());
        double angle = Math.acos(cos_x);
        if (angle > 0.0 && angle < Math.PI) {
            if (angle > 2.600540585471551) {
                this.left_coef = 1.0;
                this.right_coef = 1.0 - this.left_coef;
            } else if (angle < 0.6457718232379019) {
                this.right_coef = 1.0;
                this.left_coef = 1.0 - this.right_coef;
            } else {
                this.left_coef = (180.0 * angle / Math.PI - 90.0 + 53.0) / 112.0;
                this.right_coef = 1.0 - this.left_coef;
            }
        }
    }

    private boolean inActivateRadius() {
        return null != this.currentPos && this.currentPos.len2(this.scenePos) < 10000.0;
    }

    private boolean inActivateSemiSphere() {
        return null != this.currentPos && this.sceneMat.v1.dot(vectorJ.oMinus(this.currentPos, this.scenePos)) > 0.0;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    static class preset {
        public matrixJ M;
        public vectorJ P;

        preset(matrixJ M, vectorJ P) {
            this.P = P;
            this.M = M;
        }
    }
}

