package players;

import java.util.Vector;
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

public class actorveh implements IScriptRef {
  private int ai_player;
  
  private int car;
  
  private ScriptRef uid = null;
  
  public static final int NOVEH = 0;
  
  public static final int ISDRIVER = 1;
  
  public static final int ISPASSANGER = 2;
  
  public static final int NOTUSESHIFT = 4;
  
  public static final int USEPREFIX = 8;
  
  public static final int ISPACK = 16;
  
  public actorveh() {
    this.ai_player = 0;
    this.car = 0;
    this.uid = new ScriptRef();
  }
  
  public static actorveh createScriptRef(int uId) {
    IScriptRef scriptRef = ScriptRefStorage.getRefference(uId);
    if (scriptRef == null)
      return new actorveh(uId); 
    if (scriptRef instanceof actorveh)
      return (actorveh)scriptRef; 
    return null;
  }
  
  private actorveh(int uId) {
    this.ai_player = 0;
    this.car = 0;
    this.uid = new ScriptRef();
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
    setAi_player(p);
  }
  
  public void UpdateCar() {
    this.car = (0 != this.ai_player) ? GetPlayerCar(this.ai_player) : 0;
  }
  
  public void deinitcar() {
    this.car = 0;
  }
  
  public boolean Exists() {
    return (this.ai_player != 0);
  }
  
  public void deactivate() {
    if (0 != this.ai_player) {
      delete_ai_player();
      this.ai_player = 0;
      this.car = 0;
    } 
  }
  
  public SCRcardriver takeDrivernative(aiplayer player) {
    SCRcardriver res_driver = (new loaddriver()).Init(player);
    UpdateCar();
    res_driver.attachToCar(this.car);
    setModelInCar(player.getModel());
    return res_driver;
  }
  
  public SCRcarpassanger takePassengernative(aiplayer player, int stateflag) {
    if ((stateflag & 0x2) != 0 && (stateflag & 0x4) == 0) {
      SCRcarpassanger res_passanger = (new loaddriver()).InitPassanger(player, this, stateflag);
      res_passanger.attachToCar(this.car);
      setModelInCar(player.getModel());
      return res_passanger;
    } 
    if ((stateflag & 0x2) != 0 && (stateflag & 0x4) != 0 && (stateflag & 0x8) == 0) {
      SCRcarpassanger res_passanger = (new loaddriver()).InitPassanger_NoShift(player, stateflag);
      res_passanger.attachToCar(this.car);
      setModelInCar(player.getModel());
      return res_passanger;
    } 
    eng.err("Errorious takePassenger");
    return null;
  }
  
  public SCRcarpassanger takePassengernative(aiplayer player, int stateflag, String noshiftPrefix) {
    if ((stateflag & 0x2) != 0 && (stateflag & 0x4) != 0 && (stateflag & 0x8) != 0) {
      SCRcarpassanger res_passanger = (new loaddriver()).InitPassanger_NoShift(player, noshiftPrefix, stateflag);
      res_passanger.attachToCar(this.car);
      setModelInCar(player.getModel());
      return res_passanger;
    } 
    eng.err("Errorious takePassenger with prefix");
    return null;
  }
  
  public void takeDriver(aiplayer player) {
    callTakePassanger(player, 1);
  }
  
  public void takePassanger(aiplayer player) {
    callTakePassanger(player, 2);
  }
  
  public void takePack(aiplayer player) {
    callTakePassanger(player, 18);
  }
  
  public void takePassanger_simple(aiplayer player) {
    callTakePassanger(player, 6);
  }
  
  public void takePassanger_simple_like(aiplayer player, String prefix) {
    callTakePassanger(player, 14, prefix);
  }
  
  public vectorJ gDir() {
    if (this.car != 0)
      return eng.GetVehicle_dir(this.car); 
    return defaultDirection();
  }
  
  public vectorJ defaultPosition() {
    return new vectorJ();
  }
  
  public vectorJ defaultDirection() {
    return new vectorJ(0.0D, 1.0D, 0.0D);
  }
  
  void setModelInCar(SCRuniperson charmodel) {
    long drv = eng.GetVehicleDriver(this.car);
    if (charmodel == null || this.car == 0 || 0L == drv)
      return; 
    vectorJ pos = eng.GetVehicle_steeringwheel_pos(drv);
    if (pos != null)
      charmodel.SetPosition(pos); 
    vectorJ dir = eng.GetVehicle_steeringwheel_dir(drv);
    if (dir != null)
      charmodel.SetDirection(dir); 
  }
  
  public void traceforward(double distance) {
    vectorJ dir = gDir();
    dir.mult(distance);
    autopilotTo(gPosition().oPlusN(dir));
  }
  
  public native void callTakePassanger(aiplayer paramaiplayer, int paramInt);
  
  public native void callTakePassanger(aiplayer paramaiplayer, int paramInt, String paramString);
  
  public native void abandoneCar(aiplayer paramaiplayer);
  
  public native int GetPlayerCar(int paramInt);
  
  private native void delete_ai_player();
  
  public native void setNeverUnloadFlag();
  
  public native void leave_target();
  
  public native void registerCar(String paramString);
  
  public native void SetHidden(int paramInt);
  
  public native void autopilotTo(vectorJ paramvectorJ);
  
  public native void stop_autopilot();
  
  public native boolean autopilotFinished();
  
  public native void teleport(vectorJ paramvectorJ);
  
  public native void makeParking(parkingplace paramparkingplace, int paramInt);
  
  public native void makeParkingAnimated(parkingplace paramparkingplace, double paramDouble, int paramInt);
  
  public native void leaveParking();
  
  public native parkingplace parked();
  
  public native boolean isparked();
  
  public native int lockParkingForMission(parkingplace paramparkingplace);
  
  public native int hasParkingForMission(parkingplace paramparkingplace);
  
  public native void freeParkingForMission(parkingplace paramparkingplace, int paramInt);
  
  public native matrixJ gMatrix();
  
  public native matrixJ gMatrixOnRoad();
  
  public native vectorJ gPosition();
  
  public native vectorJ gPositionSteerWheel();
  
  public native vectorJ gPositionSaddle();
  
  public native vectorJ gVelocity();
  
  public native void sPosition(vectorJ paramvectorJ, matrixJ parammatrixJ);
  
  public native void sPosition(vectorJ paramvectorJ);
  
  public native boolean gOvertaken(actorveh paramactorveh);
  
  public native void sOnTheRoadLaneAndStop(int paramInt);
  
  public static native void makerace(Vector paramVector, String paramString);
  
  public native void stoprace();
  
  public static native void aligncars(Vector paramVector, vectorJ paramvectorJ, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public static native void aligncars_inTrajectoryStart(Vector paramVector, String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public static native void aligncars_inTrajectoryFinish(Vector paramVector, String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public static native void aligncars_inRaceStart(Vector paramVector, String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public static native void aligncars_inRaceFinish(Vector paramVector, String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public static native void autopilotOnTrajectory(Vector paramVector, String paramString);
  
  public native void attach(semitrailer paramsemitrailer);
  
  public native void deattach(semitrailer paramsemitrailer);
  
  public native semitrailer querryTrailer();
  
  public native boolean isTrailerAttachedBySaddle(semitrailer paramsemitrailer);
  
  public native void sVeclocity(double paramDouble);
  
  public native void sVeclocity(vectorJ paramvectorJ);
  
  public native vehicle takeoff_currentcar();
  
  public native vehicle querryCurrentCar();
  
  public native double querryCarDamaged();
  
  public native void setLockPlayer(boolean paramBoolean);
  
  public native void setCollideMode(boolean paramBoolean);
  
  public native void makeUnloadable(boolean paramBoolean);
  
  public native void changeEngine(String paramString);
  
  public native void loadImmediateForPredefineAnimation();
  
  public native void noNeedForPredefineAnimation();
  
  public native void switchOffEngine();
  
  public native void setHandBreak(boolean paramBoolean);
  
  public native void releasePedalBrake();
  
  public native void detachSemitrailer();
  
  public void deleteSemitrailerIfExists() {
    semitrailer semitrailer = querryTrailer();
    if (null != semitrailer) {
      detachSemitrailer();
      semitrailer.delete();
    } 
  }
  
  public native void attachSemitrailer(semitrailer paramsemitrailer);
  
  public native void showOnMap(boolean paramBoolean);
  
  public native void setVisible();
  
  private native void droppOffAllButOnePassanger(int paramInt);
  
  public void dropOffPassangersButDriver() {
    droppOffAllButOnePassanger(Crew.getIgrok().getUid());
  }
  
  public int getAi_player() {
    return this.ai_player;
  }
  
  public void setAi_player(int ai_player) {
    this.ai_player = ai_player;
    UpdateCar();
  }
  
  public int getCar() {
    return this.car;
  }
  
  public void setCar(int car) {
    this.car = car;
  }
  
  public IXMLSerializable getXmlSerializator() {
    return (IXMLSerializable)new ActorVehSerializator(this);
  }
}
