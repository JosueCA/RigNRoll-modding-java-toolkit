/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.scenes;

import java.util.Vector;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.scenetrack;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscenario.cScriptFuncs;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.stage;
import rnrscr.animation;
import rnrscr.camscripts;
import rnrscr.trackscripts;

@ScenarioClass(scenarioStage=1)
public final class sc00090
extends stage {
    public static int tipshoot;
    public static double coef1;
    public static double coef2;
    public volatile double coef1_true;
    public volatile double coef2_true;
    private ThreadTask proc = null;
    cScriptFuncs automat = null;
    int tochoose = 1;

    public static void SetCoef(double c) {
        tipshoot = c > 1.0 ? 2 : 1;
        coef1 = c = c > 1.0 ? 1.0 : c;
        coef2 = 1.0 - c;
    }

    void SetCoef1(camscripts.trackclipparams pars) {
        pars.Weight = this.coef1_true;
    }

    void SetCoef2(camscripts.trackclipparams pars) {
        pars.Weight = this.coef2_true;
    }

    public sc00090(Ithreadprocedure proc) {
        super(proc, "sc00090");
        this.proc = new ThreadTask(proc);
    }

    protected void playSceneBody(cScriptFuncs automat) {
        this.automat = automat;
        this.ChaseShooting();
    }

    void ChaseShooting() {
        eng.lock();
        trackscripts trs = new trackscripts(this.getSyncMonitor());
        int rbd = tipshoot;
        rbd = rbd == 1 ? 1 : animation.RandomFromNom(4) + 1;
        this.coef1_true = coef1;
        this.coef2_true = coef2;
        Presets data = new Presets();
        data.banditcar = Crew.getMappedCar("ARGOSY BANDIT");
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        aiplayer Bandit = aiplayer.getSimpleAiplayer("SC_BANDITJOELOW");
        aiplayer GUN = aiplayer.getSimpleAiplayer("SC_BANDITGUN");
        SCRuniperson person1 = Bandit.getModel();
        person1.lockPerson();
        SCRuniperson person2 = GUN.getModel();
        person2.lockPerson();
        pool.add(new SceneActorsPool("bandit", Bandit.getModel()));
        pool.add(new SceneActorsPool("gun", GUN.getModel()));
        eng.unlock();
        switch (rbd) {
            case 1: {
                eng.lock();
                long sc1 = trs.PlaySceneXML_nGet("00060shootingbegin_side", false);
                scenetrack.ChangeClipParam(sc1, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_bgn_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc1, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_bgn_Clip", this, "SetCoef2");
                scenetrack.ChangeClipParam(sc1, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_bgn_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc1, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_bgn_Clip", this, "SetCoef2");
                event.eventObject((int)sc1, this.proc, "_resum");
                eng.unlock();
                this.proc._susp();
                eng.lock();
                scenetrack.DeleteScene(sc1);
                eng.unlock();
                eng.lock();
                long sc2 = trs.PlaySceneXML_nGet("00060shooting_side", false);
                scenetrack.ChangeClipParam(sc2, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_cyc_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc2, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_cyc_Clip", this, "SetCoef2");
                scenetrack.ChangeClipParam(sc2, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_cyc_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc2, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_cyc_Clip", this, "SetCoef2");
                scenetrack.ChangeClipParam(sc2, "Model_shoot", "Model_shoot_BANDIT_JOE_side_shoot_cyc_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc2, "Model_shoot", "Model_shoot_BANDIT_JOE_back_shoot_cyc_Clip", this, "SetCoef2");
                event.eventObject((int)sc2, this.proc, "_resum");
                eng.unlock();
                this.proc._susp();
                eng.lock();
                scenetrack.DeleteScene(sc2);
                eng.unlock();
                eng.lock();
                long sc3 = trs.PlaySceneXML_nGet("00060shootingend_side", false);
                scenetrack.ChangeClipParam(sc3, "BANDIT_JOE", "BANDIT_JOE_pas01_side_shoot_fin_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc3, "BANDIT_JOE", "BANDIT_JOE_pas01_back_shoot_fin_Clip", this, "SetCoef2");
                scenetrack.ChangeClipParam(sc3, "Model_gun", "Model_gun_BANDIT_JOE_side_shoot_fin_Clip", this, "SetCoef1");
                scenetrack.ChangeClipParam(sc3, "Model_gun", "Model_gun_BANDIT_JOE_back_shoot_fin_Clip", this, "SetCoef2");
                event.eventObject((int)sc3, this.proc, "_resum");
                eng.unlock();
                this.proc._susp();
                eng.lock();
                scenetrack.DeleteScene(sc3);
                eng.unlock();
                break;
            }
            case 2: {
                trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);
                break;
            }
            case 3: {
                trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);
                break;
            }
            case 4: {
                trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);
                break;
            }
            case 5: {
                trs.PlaySceneXMLThreaded("00060shootingbegin_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shooting_frnt", false, pool, data, this.proc);
                trs.PlaySceneXMLThreaded("00060shootingend_frnt", false, pool, data, this.proc);
            }
        }
        eng.lock();
        person1.unlockPerson();
        person2.unlockPerson();
        eng.unlock();
    }

    static class Presets {
        actorveh banditcar;

        Presets() {
        }
    }
}

