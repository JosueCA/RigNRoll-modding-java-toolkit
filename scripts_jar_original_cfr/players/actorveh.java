/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.Vector;
import players.Crew;
import players.aiplayer;
import players.semitrailer;
import players.vehicle;
import rnrconfig.loaddriver;
import rnrcore.IScriptRef;
import rnrcore.IXMLSerializable;
import rnrcore.SCRcardriver;
import rnrcore.SCRcarpassanger;
import rnrcore.SCRuniperson;
import rnrcore.ScriptRef;
import rnrcore.ScriptRefStorage;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.parkingplace;
import xmlserialization.ActorVehSerializator;

public class actorveh
implements IScriptRef {
    private int ai_player = 0;
    private int car = 0;
    private ScriptRef uid = new ScriptRef();
    public static final int NOVEH = 0;
    public static final int ISDRIVER = 1;
    public static final int ISPASSANGER = 2;
    public static final int NOTUSESHIFT = 4;
    public static final int USEPREFIX = 8;
    public static final int ISPACK = 16;

    public actorveh() {
    }

    public static actorveh createScriptRef(int uId) {
        IScriptRef scriptRef = ScriptRefStorage.getRefference(uId);
        if (scriptRef == null) {
            return new actorveh(uId);
        }
        if (scriptRef instanceof actorveh) {
            return (actorveh)scriptRef;
        }
        return null;
    }

    private actorveh(int uId) {
        this.uid.register(uId, this);
    }

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
        this.setAi_player(p);
    }

    public void UpdateCar() {
        this.car = 0 != this.ai_player ? this.GetPlayerCar(this.ai_player) : 0;
    }

    public void deinitcar() {
        this.car = 0;
    }

    public boolean Exists() {
        return this.ai_player != 0;
    }

    public void deactivate() {
        if (0 != this.ai_player) {
            this.delete_ai_player();
            this.ai_player = 0;
            this.car = 0;
        }
    }

    public SCRcardriver takeDrivernative(aiplayer player) {
        SCRcardriver res_driver = new loaddriver().Init(player);
        this.UpdateCar();
        res_driver.attachToCar(this.car);
        this.setModelInCar(player.getModel());
        return res_driver;
    }

    public SCRcarpassanger takePassengernative(aiplayer player, int stateflag) {
        if ((stateflag & 2) != 0 && (stateflag & 4) == 0) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger(player, this, stateflag);
            res_passanger.attachToCar(this.car);
            this.setModelInCar(player.getModel());
            return res_passanger;
        }
        if ((stateflag & 2) != 0 && (stateflag & 4) != 0 && (stateflag & 8) == 0) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger_NoShift(player, stateflag);
            res_passanger.attachToCar(this.car);
            this.setModelInCar(player.getModel());
            return res_passanger;
        }
        eng.err("Errorious takePassenger");
        return null;
    }

    public SCRcarpassanger takePassengernative(aiplayer player, int stateflag, String noshiftPrefix) {
        if ((stateflag & 2) != 0 && (stateflag & 4) != 0 && (stateflag & 8) != 0) {
            SCRcarpassanger res_passanger = new loaddriver().InitPassanger_NoShift(player, noshiftPrefix, stateflag);
            res_passanger.attachToCar(this.car);
            this.setModelInCar(player.getModel());
            return res_passanger;
        }
        eng.err("Errorious takePassenger with prefix");
        return null;
    }

    public void takeDriver(aiplayer player) {
        this.callTakePassanger(player, 1);
    }

    public void takePassanger(aiplayer player) {
        this.callTakePassanger(player, 2);
    }

    public void takePack(aiplayer player) {
        this.callTakePassanger(player, 18);
    }

    public void takePassanger_simple(aiplayer player) {
        this.callTakePassanger(player, 6);
    }

    public void takePassanger_simple_like(aiplayer player, String prefix) {
        this.callTakePassanger(player, 14, prefix);
    }

    public vectorJ gDir() {
        if (this.car != 0) {
            return eng.GetVehicle_dir(this.car);
        }
        return this.defaultDirection();
    }

    public vectorJ defaultPosition() {
        return new vectorJ();
    }

    public vectorJ defaultDirection() {
        return new vectorJ(0.0, 1.0, 0.0);
    }

    void setModelInCar(SCRuniperson charmodel) {
        vectorJ dir;
        long drv = eng.GetVehicleDriver(this.car);
        if (charmodel == null || this.car == 0 || 0L == drv) {
            return;
        }
        vectorJ pos = eng.GetVehicle_steeringwheel_pos(drv);
        if (pos != null) {
            charmodel.SetPosition(pos);
        }
        if ((dir = eng.GetVehicle_steeringwheel_dir(drv)) != null) {
            charmodel.SetDirection(dir);
        }
    }

    public void traceforward(double distance) {
        vectorJ dir = this.gDir();
        dir.mult(distance);
        this.autopilotTo(this.gPosition().oPlusN(dir));
    }

    public native void callTakePassanger(aiplayer var1, int var2);

    public native void callTakePassanger(aiplayer var1, int var2, String var3);

    public native void abandoneCar(aiplayer var1);

    public native int GetPlayerCar(int var1);

    private native void delete_ai_player();

    public native void setNeverUnloadFlag();

    public native void leave_target();

    public native void registerCar(String var1);

    public native void SetHidden(int var1);

    public native void autopilotTo(vectorJ var1);

    public native void stop_autopilot();

    public native boolean autopilotFinished();

    public native void teleport(vectorJ var1);

    public native void makeParking(parkingplace var1, int var2);

    public native void makeParkingAnimated(parkingplace var1, double var2, int var4);

    public native void leaveParking();

    public native parkingplace parked();

    public native boolean isparked();

    public native int lockParkingForMission(parkingplace var1);

    public native int hasParkingForMission(parkingplace var1);

    public native void freeParkingForMission(parkingplace var1, int var2);

    public native matrixJ gMatrix();

    public native matrixJ gMatrixOnRoad();

    public native vectorJ gPosition();

    public native vectorJ gPositionSteerWheel();

    public native vectorJ gPositionSaddle();

    public native vectorJ gVelocity();

    public native void sPosition(vectorJ var1, matrixJ var2);

    public native void sPosition(vectorJ var1);

    public native boolean gOvertaken(actorveh var1);

    public native void sOnTheRoadLaneAndStop(int var1);

    public static native void makerace(Vector var0, String var1);

    public native void stoprace();

    public static native void aligncars(Vector var0, vectorJ var1, double var2, double var4, int var6, int var7);

    public static native void aligncars_inTrajectoryStart(Vector var0, String var1, double var2, double var4, int var6, int var7);

    public static native void aligncars_inTrajectoryFinish(Vector var0, String var1, double var2, double var4, int var6, int var7);

    public static native void aligncars_inRaceStart(Vector var0, String var1, double var2, double var4, int var6, int var7);

    public static native void aligncars_inRaceFinish(Vector var0, String var1, double var2, double var4, int var6, int var7);

    public static native void autopilotOnTrajectory(Vector var0, String var1);

    public native void attach(semitrailer var1);

    public native void deattach(semitrailer var1);

    public native semitrailer querryTrailer();

    public native boolean isTrailerAttachedBySaddle(semitrailer var1);

    public native void sVeclocity(double var1);

    public native void sVeclocity(vectorJ var1);

    public native vehicle takeoff_currentcar();

    public native vehicle querryCurrentCar();

    public native double querryCarDamaged();

    public native void setLockPlayer(boolean var1);

    public native void setCollideMode(boolean var1);

    public native void makeUnloadable(boolean var1);

    public native void changeEngine(String var1);

    public native void loadImmediateForPredefineAnimation();

    public native void noNeedForPredefineAnimation();

    public native void switchOffEngine();

    public native void setHandBreak(boolean var1);

    public native void releasePedalBrake();

    public native void detachSemitrailer();

    public void deleteSemitrailerIfExists() {
        semitrailer semitrailer2 = this.querryTrailer();
        if (null != semitrailer2) {
            this.detachSemitrailer();
            semitrailer2.delete();
        }
    }

    public native void attachSemitrailer(semitrailer var1);

    public native void showOnMap(boolean var1);

    public native void setVisible();

    private native void droppOffAllButOnePassanger(int var1);

    public void dropOffPassangersButDriver() {
        this.droppOffAllButOnePassanger(Crew.getIgrok().getUid());
    }

    public int getAi_player() {
        return this.ai_player;
    }

    public void setAi_player(int ai_player) {
        this.ai_player = ai_player;
        this.UpdateCar();
    }

    public int getCar() {
        return this.car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public IXMLSerializable getXmlSerializator() {
        return new ActorVehSerializator(this);
    }
}

