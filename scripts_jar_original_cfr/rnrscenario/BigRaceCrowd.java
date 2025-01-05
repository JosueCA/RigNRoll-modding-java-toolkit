/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.Random;
import java.util.Stack;
import java.util.Vector;
import players.Crew;
import rnrcore.Collide;
import rnrcore.IXMLSerializable;
import rnrcore.SCRpersongroup;
import rnrcore.SCRtalkingperson;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscr.Helper;

public class BigRaceCrowd
implements anm {
    Vector groupLeaders = new Vector();
    Vector women_groupLeaders = new Vector();
    Stack models_for_groups = new Stack();
    Stack weemen_models_for_groups = new Stack();
    private boolean scene_ended = false;
    private ScriptRef uid = new ScriptRef();
    Random rnd = new Random();
    public static String unicidName = "bigracePerson";
    private static int unicidNameNumber = 0;
    static int id = 0;
    double row_wide = 1.1;
    double line_wide = 0.9;
    double row_hight = 0.0;
    int people_in_group = 1;
    int places = 7;
    Vector allGroups = new Vector();
    long winners_scene = 0L;
    String DWORDname = "DWORD_BigRace_WHFinish";
    Vector CrowdPersons = new Vector();
    Vector _Photografers = new Vector();

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public void updateNative(int p) {
    }

    int getRnd(int nm) {
        return this.getPrefix(nm);
    }

    int getPrefix(int nm) {
        if (nm <= 1) {
            return nm;
        }
        int res = (int)((double)nm * this.rnd.nextDouble() + 1.0);
        if (res > nm) {
            res = nm;
        }
        return res;
    }

    double getRandom(double nm) {
        if (nm <= 0.0) {
            return nm;
        }
        return nm * this.rnd.nextDouble();
    }

    double getRandom(double MIN, double MAX) {
        return MIN + this.getRandom(MAX - MIN);
    }

    void buildModelStack() {
        if (this.getRnd(2) == 1) {
            int pref = this.getPrefix(10);
            for (int i = 0; i < this.people_in_group; ++i) {
                String res = "";
                res = pref >= 10 ? "Man_0" + pref : "Man_00" + pref;
                this.models_for_groups.push(res);
            }
        } else {
            int pref = this.getPrefix(10);
            for (int i = 0; i < this.people_in_group; ++i) {
                String res = "";
                res = pref >= 10 ? "Woman0" + pref : "Woman00" + pref;
                this.weemen_models_for_groups.push(res);
            }
        }
    }

    public static String gunicidName() {
        String res = new String(unicidName);
        res = res + ++unicidNameNumber;
        return res;
    }

    public void delsome() {
        Object gr;
        int i;
        for (i = 0; i < this.allGroups.size(); ++i) {
            gr = (SCRpersongroup)this.allGroups.elementAt(i);
            SCRpersongroup.removeGroup((SCRpersongroup)gr);
        }
        for (i = 0; i < this.groupLeaders.size(); ++i) {
            gr = (SCRtalkingperson)this.groupLeaders.elementAt(i);
            ((SCRtalkingperson)gr).stop();
        }
        for (i = 0; i < this.women_groupLeaders.size(); ++i) {
            gr = (SCRtalkingperson)this.women_groupLeaders.elementAt(i);
            ((SCRtalkingperson)gr).stop();
        }
    }

    public void make_group(vectorJ base_pos, vectorJ base_dir) {
        SCRuniperson UP1;
        vectorJ shift;
        int i;
        vectorJ norm = new vectorJ(base_dir.y, -base_dir.x, 0.0);
        vectorJ zlevel = Collide.collidePoint(base_pos.oPlusN(new vectorJ(0.0, 0.0, 100.0)), base_pos.oPlusN(new vectorJ(0.0, 0.0, -100.0)));
        base_dir.z = zlevel.z;
        int sz = this.models_for_groups.size();
        int sz_2 = (int)Math.floor((double)sz * 0.5);
        int sz_remains = sz - sz_2;
        SCRuniperson UP = SCRuniperson.createCutSceneAmbientPerson((String)this.models_for_groups.pop(), BigRaceCrowd.gunicidName(), null, base_pos.oPlusN(norm).oPlusN(base_dir), base_dir);
        UP.SetInWarehouseEnvironment();
        SCRtalkingperson pers = new SCRtalkingperson(UP);
        UP.play();
        SCRpersongroup group = SCRpersongroup.create(UP);
        this.allGroups.add(group);
        this.groupLeaders.add(pers);
        if (sz_2 > 1) {
            for (i = 1; i < sz_2; ++i) {
                shift = new vectorJ(base_dir);
                shift.mult(i);
                UP1 = SCRuniperson.createCutSceneAmbientPerson((String)this.models_for_groups.pop(), BigRaceCrowd.gunicidName(), null, base_pos.oPlusN(norm).oPlusN(shift), base_dir);
                UP1.SetInWarehouseEnvironment();
                UP1.play();
                group.addPerson(UP1);
            }
        }
        if (sz_remains >= 1) {
            for (i = 0; i < sz_remains; ++i) {
                shift = new vectorJ(base_dir);
                shift.mult(i);
                UP1 = SCRuniperson.createCutSceneAmbientPerson((String)this.models_for_groups.pop(), BigRaceCrowd.gunicidName(), null, base_pos.oPlusN(shift), base_dir);
                UP1.SetInWarehouseEnvironment();
                UP1.play();
                group.addPerson(UP1);
            }
        }
    }

    void crowPlaceSomeWhere() {
        vectorJ place = new vectorJ(this.getPrefix(30) - 15, this.getPrefix(30) - 15, 0.0);
        for (int OUT = 0; OUT < 2; ++OUT) {
            for (int i = 0; i < 2; ++i) {
                vectorJ pos = new vectorJ(OUT * 2 + 2 * i, 2 * i - OUT * 2, 1.0);
                pos.oPlus(place);
                vectorJ dir = new vectorJ(Math.cos(i), Math.sin(i), 0.0);
                for (int j = 0; j < 4; ++j) {
                    int pref = this.getPrefix(20);
                    String res = "";
                    res = pref >= 10 ? "Man_0" + pref : "Man_00" + pref;
                    this.models_for_groups.push(res);
                }
                this.make_group(pos, dir);
            }
            eng.CreateInfinitCycleScriptAnimation(this, 5.0 + this.getRandom(5.0));
        }
    }

    public void nativePlaces(vectorJ dir, vectorJ pos1, vectorJ pos2) {
        vectorJ shift;
        int i;
        for (i = 0; i < 10; ++i) {
            shift = new vectorJ(dir);
            shift.mult(i * 4);
            this.buildModelStack();
            this.make_group(pos1.oPlusN(shift), dir);
        }
        for (i = 0; i < 10; ++i) {
            this.buildModelStack();
            shift = new vectorJ(dir);
            shift.mult(i * 4);
            this.make_group(pos2.oPlusN(shift), dir);
        }
        this.play1();
    }

    public void buildModelStackHuge() {
        for (int i = 0; i < 56 / this.people_in_group; ++i) {
            this.buildModelStack();
        }
    }

    double gRowWidth() {
        return this.row_wide * this.getRandom(0.6, 1.4);
    }

    double gLineWidth() {
        return this.line_wide * this.getRandom(0.8, 1.2);
    }

    vectorJ make2dPosition(vectorJ base_pos, vectorJ base_dir, int row, int place) {
        vectorJ shiftline = new vectorJ(-base_dir.y, base_dir.x, 0.0);
        vectorJ shiftrow = new vectorJ(-base_dir.x, -base_dir.y, -base_dir.z);
        shiftrow.mult(this.gRowWidth() + (double)(row - 1) * this.row_wide);
        vectorJ zshift = new vectorJ(0.0, 0.0, 1.0);
        zshift.mult((double)row * this.row_hight);
        shiftline.mult(this.gLineWidth() + (double)(place - 1) * this.line_wide + (21.6 - (double)(this.places - 1) * this.line_wide) * 0.5 - 4.5);
        vectorJ res = base_pos.oPlusN(shiftline).oPlusN(shiftrow).oPlusN(zshift);
        return res;
    }

    public void makeGroupHuge(vectorJ base_pos, vectorJ base_dir) {
        int sz = this.models_for_groups.size() + this.weemen_models_for_groups.size();
        int count_people_in_group = 0;
        int rows = (int)Math.ceil(sz / this.places) + 1;
        Vector<OnePlace> allPlaces = new Vector<OnePlace>();
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < this.places; ++j) {
                allPlaces.add(new OnePlace(i, j));
            }
        }
        for (int v = 0; v < allPlaces.size(); ++v) {
            OnePlace el = (OnePlace)allPlaces.elementAt(v);
            int r = this.getRnd(allPlaces.size() - 1);
            OnePlace repl_el = (OnePlace)allPlaces.elementAt(r);
            allPlaces.setElementAt(el, r);
            allPlaces.setElementAt(repl_el, v);
        }
        SCRpersongroup group = null;
        int man_lim = this.models_for_groups.size();
        for (int i = 0; i < sz; ++i) {
            SCRuniperson UP;
            if (count_people_in_group >= this.people_in_group) {
                count_people_in_group = 0;
            }
            OnePlace place = (OnePlace)allPlaces.elementAt(i);
            Stack chosen = this.models_for_groups;
            Vector chosen_leaders = this.groupLeaders;
            boolean steelman = true;
            if (i >= man_lim) {
                steelman = false;
                if (i == man_lim) {
                    count_people_in_group = 0;
                }
                chosen = this.weemen_models_for_groups;
                chosen_leaders = this.women_groupLeaders;
            }
            if (steelman && this.getRandom(1.0) < 0.2) {
                UP = SCRuniperson.createCutSceneAmbientPerson((String)chosen.pop(), BigRaceCrowd.gunicidName(), null, this.make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);
                UP.SetInWarehouseEnvironment();
                this.platPhotografAnimation(UP);
                ++count_people_in_group;
                continue;
            }
            if (0 == count_people_in_group) {
                UP = SCRuniperson.createCutSceneAmbientPerson((String)chosen.pop(), BigRaceCrowd.gunicidName(), null, this.make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);
                UP.SetInWarehouseEnvironment();
                SCRtalkingperson pers = new SCRtalkingperson(UP);
                UP.play();
                group = SCRpersongroup.create(UP);
                chosen_leaders.add(pers);
                this.allGroups.add(group);
                ++count_people_in_group;
                continue;
            }
            SCRuniperson UP1 = SCRuniperson.createCutSceneAmbientPerson((String)chosen.pop(), BigRaceCrowd.gunicidName(), null, this.make2dPosition(base_pos, base_dir, place.row, place.place), base_dir);
            UP1.SetInWarehouseEnvironment();
            UP1.play();
            group.addPerson(UP1);
            ++count_people_in_group;
        }
    }

    void clearGroups() {
        for (int i = 0; i < this.allGroups.size(); ++i) {
            SCRpersongroup gr = (SCRpersongroup)this.allGroups.elementAt(i);
            SCRpersongroup.removeGroup(gr);
        }
    }

    public void nativePlaces(vectorJ dir, vectorJ pos) {
        this.buildModelStackHuge();
        this.makeGroupHuge(pos, dir);
        this.play1();
    }

    public void winnersAnimation(int place, vectorJ pos, matrixJ matr) {
        SCRuniperson[] persons;
        eng.setdword(this.DWORDname, 1);
        PhotografPreset preset2 = new PhotografPreset();
        preset2.P = pos;
        preset2.M = matr;
        preset2.Mcam = new matrixJ(preset2.M);
        Crew.getIgrok().getModel().SetInWarehouseEnvironment();
        long sc = 0L;
        switch (place) {
            case 1: {
                sc = scenetrack.CreateSceneXML("bigrace_1place", 9, preset2);
                break;
            }
            case 2: {
                sc = scenetrack.CreateSceneXML("bigrace_2place", 9, preset2);
                break;
            }
            case 3: {
                sc = scenetrack.CreateSceneXML("bigrace_3place", 9, preset2);
            }
        }
        for (SCRuniperson singlePerson : persons = scenetrack.GetSceneAll(sc)) {
            singlePerson.SetInWarehouseEnvironment();
        }
        event.eventObject((int)sc, this, "endscene");
        this.winners_scene = sc;
    }

    public void winnersAnimationBackGround(vectorJ pos, matrixJ matr) {
        eng.setdword(this.DWORDname, 1);
        PhotografPreset preset2 = new PhotografPreset();
        preset2.P = pos;
        preset2.M = matr;
        preset2.Mcam = new matrixJ(preset2.M);
        scenetrack.CreateSceneXML("bigrace_background", 25, preset2);
    }

    public void endscene() {
        this.scene_ended = true;
        scenetrack.DeleteScene(this.winners_scene);
        this.clearPhoto();
        this.clearGroups();
        this.clearCrowd();
        this.delsome();
        Helper.restoreCameraToIgrokCar();
        event.Setevent(9001);
    }

    public void play1() {
        for (SCRtalkingperson p : this.groupLeaders) {
            p.playAnimation("m_in_crowd_0" + this.getPrefix(3), 0.5);
        }
        for (SCRtalkingperson p : this.women_groupLeaders) {
            p.playAnimation("w_in_crowd_0" + this.getPrefix(3), 0.5);
        }
    }

    public void play2() {
        for (SCRtalkingperson p : this.groupLeaders) {
            p.playAnimation("m_in_crowd_02", 0.5);
        }
    }

    public void play3() {
        for (SCRtalkingperson p : this.groupLeaders) {
            p.playAnimation("m_in_crowd_03", 0.5);
        }
    }

    public void platPhotografAnimation(SCRuniperson pers) {
        PhotografPreset preset2 = new PhotografPreset();
        preset2.P = pers.GetPosition();
        preset2.M = pers.GetMatrix();
        preset2.Mcam = new matrixJ(preset2.M);
        SCRuniperson camm = SCRuniperson.createCutSceneAmbientPerson("Model_Photocam_01", BigRaceCrowd.gunicidName(), null, preset2.P, preset2.M.v1);
        camm.SetInWarehouseEnvironment();
        this.CrowdPersons.add(camm);
        this.CrowdPersons.add(pers);
        SceneActorsPool poolman = new SceneActorsPool("man", pers);
        SceneActorsPool poolcam = new SceneActorsPool("cam", camm);
        poolman.lock();
        poolcam.lock();
        Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
        pool.add(poolman);
        pool.add(poolcam);
        cPhotografer photo = new cPhotografer();
        this._Photografers.add(photo);
        photo.pool = pool;
        photo.preset = preset2;
        photo.run();
    }

    void clearPhoto() {
        for (int i = 0; i < this._Photografers.size(); ++i) {
            cPhotografer ph = (cPhotografer)this._Photografers.elementAt(i);
            ph.clear();
        }
        this._Photografers.clear();
    }

    void clearCrowd() {
    }

    public boolean animaterun(double dt) {
        if (this.scene_ended) {
            return true;
        }
        this.play1();
        return false;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    class cPhotografer {
        Vector pool = null;
        PhotografPreset preset = null;
        volatile long scene;

        cPhotografer() {
        }

        public void run() {
            this.scene = scenetrack.CreateSceneXMLPool("bigrace_photoman", 256, this.pool, this.preset);
            scenetrack.moveSceneTime(this.scene, 10.0 * Math.random());
            scenetrack.ReplaceSceneFlags(this.scene, 5);
        }

        public void clear() {
            scenetrack.StopScene(this.scene);
            for (SceneActorsPool item : this.pool) {
                item.unlock();
            }
        }
    }

    class PhotografPreset {
        vectorJ P = null;
        matrixJ M = null;
        matrixJ Mcam = null;

        PhotografPreset() {
        }
    }

    class OnePlace {
        int row;
        int place;

        OnePlace(int row, int place) {
            this.row = row;
            this.place = place;
        }
    }
}

