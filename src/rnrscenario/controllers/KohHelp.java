/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.controllers;

import players.CarName;
import players.Crew;
import players.actorveh;
import rnrcore.Collide;
import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.Location;
import scriptEvents.EventsControllerHelper;

@ScenarioClass(scenarioStage=0)
public class KohHelp
implements anm {
    public static final String ACCEPT_HELP = "Accept Help Koh";
    public static final String ESCAPE_HELP = "Escape Help Koh";
    private static final String SCENENAME = "00030cycle";
    private final int LAG = 200;
    private final double min_distance = 50.0;
    private final double max_distance = 1000.0;
    private boolean switchedOffFromOutside = false;
    private long scene = 0L;
    private boolean is_playing = false;
    private actorveh koh_car;
    private actorveh player_car;
    private final Object latch = new Object();
    private ScriptRef uid = new ScriptRef();

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

    static KohHelp prepare(Location where) {
        actorveh car = eng.CreateCarForScenario(CarName.CAR_COCH, where.getOrientation(), where.getPosition());
        Crew.addMappedCar("KOH", car);
        KohHelp help = new KohHelp();
        help.koh_car = car;
        help.player_car = Crew.getIgrokCar();
        ThreadTask.create(help.new Koh());
        eng.CreateInfinitScriptAnimation(help);
        return help;
    }

    static KohHelp prepareSerialize() {
        actorveh car = Crew.getMappedCar("KOH");
        KohHelp help = new KohHelp();
        help.koh_car = car;
        help.player_car = Crew.getIgrokCar();
        ThreadTask.create(help.new Koh());
        eng.CreateInfinitScriptAnimation(help);
        return help;
    }

    private presets makePresets(actorveh car) {
        presets pres = new presets();
        pres.M = car.gMatrix();
        vectorJ point1 = new vectorJ(car.gPosition());
        point1.z += 10.0;
        vectorJ point2 = new vectorJ(point1);
        point2.z -= 20.0;
        pres.P = Collide.collidePoint(point1, point2);
        return pres;
    }

    private void finish_scene() {
        scenetrack.DeleteScene(this.scene);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void switchOff() {
        Object object = this.latch;
        synchronized (object) {
            this.switchedOffFromOutside = true;
            this.deactivateCochCar();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean animaterun(double dt) {
        Object object = this.latch;
        synchronized (object) {
            if (!this.is_playing) {
                return false;
            }
            if (this.switchedOffFromOutside) {
                this.is_playing = false;
                return true;
            }
            double distance_2 = this.koh_car.gPosition().len2(this.player_car.gPosition());
            if (distance_2 < 2500.0) {
                KohHelpManage.accept();
                this.finish_scene();
                EventsControllerHelper.messageEventHappened(ACCEPT_HELP);
                this.is_playing = false;
                return true;
            }
            if (distance_2 > 1000000.0) {
                KohHelpManage.questFinished();
                this.finish_scene();
                EventsControllerHelper.messageEventHappened(ESCAPE_HELP);
                this.deactivateCochCar();
                this.is_playing = false;
                return true;
            }
            return false;
        }
    }

    private void deactivateCochCar() {
        if (null != this.koh_car) {
            this.koh_car.deactivate();
            this.koh_car = null;
        }
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }

    final class Koh
    implements Ithreadprocedure {
        ThreadTask safe = null;

        Koh() {
        }

        public void call() {
            while (KohHelp.this.koh_car.getCar() == 0) {
                this.safe.sleep(200L);
                KohHelp.this.koh_car.UpdateCar();
            }
            eng.lock();
            KohHelp.this.koh_car.sVeclocity(0.0);
            presets pre = KohHelp.this.makePresets(KohHelp.this.koh_car);
            eng.unlock();
            KohHelp.this.scene = scenetrack.CreateSceneXML(KohHelp.SCENENAME, 517, pre);
            KohHelp.this.is_playing = true;
        }

        public void take(ThreadTask safe) {
            this.safe = safe;
        }
    }

    static final class presets {
        matrixJ M;
        vectorJ P;

        presets() {
        }
    }
}

