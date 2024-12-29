package rnrscr;

import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.IScriptTask;
import rnrcore.SCRuniperson;
import rnrcore.SceneMacroPairs;
import rnrcore.eng;
import rnrcore.scenetrack;

public class SOscene {
  private static final int INSIDE_ANIMATION = 0;
  
  private static final int OUTSIDE_ANIMATION = 1;
  
  private static final int NUM_OUT_CAMERA_TRACKS = 9;
  
  private static final int NUM_IN_CAMERA_TRACKS = 14;
  
  private static final String CAMERA_OUT_SCENE_NAME = "driver_coming_out";
  
  private static final String CAMERA_IN_SCENE_NAME = "driver_coming_in";
  
  private static final String MACRO_NOM = "NOM";
  
  private static final String MACRO_CAR_MODEL_NAME = "CAR_PREFIX";
  
  private static final String MACRO_MODEL_NAME = "MODEL_NAME";
  
  public long task;
  
  public SCRuniperson person;
  
  public aiplayer actor;
  
  public actorveh vehicle;
  
  animation AA = new animation();
  
  static class SceneInOutPresets {
    String sceneName;
    
    String carPrefix;
    
    String modelName;
    
    int nomCameraTrack = 1;
    
    SceneInOutPresets(String sceneName, String modelName, String carPrefix, int nomCameraTrack) {
      this.sceneName = sceneName;
      this.carPrefix = carPrefix;
      this.modelName = modelName;
      this.nomCameraTrack = nomCameraTrack;
    }
  }
  
  static class PlacePersonInGame implements IScriptTask {
    private SCRuniperson person;
    
    PlacePersonInGame(SCRuniperson person) {
      this.person = person;
    }
    
    public void launch() {
      this.person.SetInGameWorld();
    }
  }
  
  private static boolean chooseOutSceneFromNumber = false;
  
  private static boolean chooseInSceneFromNumber = false;
  
  private static int outSceneNumber = 0;
  
  private static int inSceneNumber = 0;
  
  public static void setOutSceneNum(int value) {
    chooseOutSceneFromNumber = (value > 0);
    outSceneNumber = value;
  }
  
  public static void setInSceneNum(int value) {
    chooseInSceneFromNumber = (value > 0);
    inSceneNumber = value;
  }
  
  static SceneInOutPresets getOutSceneName(String modelName, String carNodeName) {
    int nomCameraScene = chooseOutSceneFromNumber ? outSceneNumber : AdvancedRandom.RandFromInreval(1, 9);
    SceneInOutPresets presets = new SceneInOutPresets("driver_coming_out", modelName, carNodeName, nomCameraScene);
    return presets;
  }
  
  static SceneInOutPresets getInSceneName(String modelName, String carNodeName) {
    int nomCameraScene = chooseInSceneFromNumber ? inSceneNumber : AdvancedRandom.RandFromInreval(1, 14);
    SceneInOutPresets presets = new SceneInOutPresets("driver_coming_in", modelName, carNodeName, nomCameraScene);
    return presets;
  }
  
  static class CarData {
    actorveh car;
  }
  
  private static long createCameraInOutTrack(SceneInOutPresets scenePresets, actorveh car) {
    CarData data = new CarData();
    data.car = car;
    car.registerCar("carinout");
    SceneMacroPairs macros = SceneMacroPairs.create();
    macros.addPair("NOM", "" + scenePresets.nomCameraTrack);
    macros.addPair("CAR_PREFIX", scenePresets.carPrefix);
    macros.addPair("MODEL_NAME", scenePresets.modelName);
    return scenetrack.CreateSceneXML(scenePresets.sceneName, 2, data, macros.getMacroPairs());
  }
  
  static class CreateAndPlayCameraScene implements IScriptTask {
    private long scene;
    
    SOscene.SceneInOutPresets scenePresets;
    
    private boolean useTask = false;
    
    private long task;
    
    CreateAndPlayCameraScene(SOscene.SceneInOutPresets scenePresets) {
      this.scenePresets = scenePresets;
    }
    
    public void makeTaskScene(long task) {
      this.task = task;
      this.useTask = true;
    }
    
    public void launch() {
      this.scene = SOscene.createCameraInOutTrack(this.scenePresets, Crew.getIgrokCar());
      scenetrack.UpdateSceneFlags(this.scene, 3);
      if (this.useTask)
        eng.AddSceneTrackToTask(this.task, this.scene); 
    }
    
    public long getScene() {
      return this.scene;
    }
  }
  
  static class DeleteCameraScene implements IScriptTask {
    private long scene;
    
    DeleteCameraScene(long scene) {
      this.scene = scene;
    }
    
    public void launch() {
      scenetrack.StopScene(this.scene);
      scenetrack.DeleteScene(this.scene);
    }
  }
  
  static class CameraSceneManager implements IScriptTask {
    private SOscene.CreateAndPlayCameraScene startScene = null;
    
    private SOscene.DeleteCameraScene endScene = null;
    
    CameraSceneManager(int typeanimation, String modelName, String carNodeName) {
      switch (typeanimation) {
        case 0:
          this.startScene = new SOscene.CreateAndPlayCameraScene(SOscene.getInSceneName(modelName, carNodeName));
          break;
        case 1:
          this.startScene = new SOscene.CreateAndPlayCameraScene(SOscene.getOutSceneName(modelName, carNodeName));
          break;
      } 
    }
    
    CameraSceneManager(int typeanimation, long task, String modelName, String carNodeName) {
      this(typeanimation, modelName, carNodeName);
      this.startScene.makeTaskScene(task);
    }
    
    public void launch() {
      this.endScene = new SOscene.DeleteCameraScene(this.startScene.getScene());
      this.endScene.launch();
    }
    
    public IScriptTask getCreateSceneScriptTask() {
      return this.startScene;
    }
  }
  
  public long waitParkingEvent() {
    long CARARRIVED = eng.AddEventTask(this.task);
    eng.EventTask_onParking(CARARRIVED, this.vehicle, false);
    return CARARRIVED;
  }
  
  public CarInOutTasks makecaroutOnEnd(long CARARRIVED, boolean usefadeonend) {
    usefadeonend = false;
    CarInOutTasks res = makecarout(false);
    eng.OnEndTASK(CARARRIVED, "play", res.getMTaskToStart());
    if (usefadeonend) {
      long fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);
      eng.OnEndTASK(res.getMTaskToWait(), "play", fadein);
      res.setMTaskToWait(fadein);
    } 
    return res;
  }
  
  public CarInOutTasks makecaroutOnStart(long CARARRIVED, boolean usefadeonend) {
    usefadeonend = false;
    CarInOutTasks res = makecarout(false);
    eng.OnBegTASK(CARARRIVED, "play", res.getMTaskToStart());
    if (usefadeonend) {
      long fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);
      eng.OnEndTASK(res.getMTaskToWait(), "play", fadein);
      res.setMTaskToWait(fadein);
    } 
    return res;
  }
  
  public CarInOutTasks makecarinOnEnd(long CARARRIVED) {
    CarInOutTasks res = makecarin();
    eng.OnEndTASK(CARARRIVED, "play", res.getMTaskToStart());
    return res;
  }
  
  public CarInOutTasks makecarinOnStart(long CARARRIVED) {
    CarInOutTasks res = makecarin();
    eng.OnBegTASK(CARARRIVED, "play", res.getMTaskToStart());
    return res;
  }
  
  public long restorecameratoCarOnEnd(long EVENTTASK) {
    long RestoreCameraToCar = eng.AddScriptTask(this.task, camscripts.restore_Camera_to_igrok_car());
    eng.OnEndTASK(EVENTTASK, "play", RestoreCameraToCar);
    return RestoreCameraToCar;
  }
  
  public long restorecameratoCarOnBegin(long EVENTTASK) {
    long RestoreCameraToCar = eng.AddScriptTask(this.task, camscripts.restore_Camera_to_igrok_car());
    eng.OnBegTASK(EVENTTASK, "play", RestoreCameraToCar);
    return RestoreCameraToCar;
  }
  
  private CarInOutTasks makecarout(boolean usefadeonend) {
    long endTask = eng.AddEventTask(this.task);
    long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(this.person));
    String carPrefix = eng.GetVehiclePrefix(this.vehicle.getCar());
    long out_cabin = eng.AddScriptTask(this.task, drvscripts.outCabinState(this.vehicle));
    long fadein = 0L;
    long animEvent = eng.AddEventTask(this.task);
    eng.EventTask_AnimationEventOut(animEvent, false);
    CameraSceneManager sceneCameraOut = new CameraSceneManager(1, this.task, this.actor.gModelname(), carPrefix);
    long COUTCA = eng.AddScriptTask(this.task, sceneCameraOut.getCreateSceneScriptTask());
    long deleteCOUTCAR = eng.AddScriptTask(this.task, sceneCameraOut);
    eng.OnBegTASK(COUTCA, "play", person_ingame);
    eng.OnBegTASK(COUTCA, "play", out_cabin);
    if (usefadeonend) {
      fadein = eng.AddAnimTASK(this.task, "fadein", 0.0D);
      eng.OnEndTASK(animEvent, "play", fadein);
      eng.OnEndTASK(fadein, "play", deleteCOUTCAR);
    } else {
      eng.OnEndTASK(animEvent, "play", deleteCOUTCAR);
      eng.OnEndTASK(animEvent, "end", endTask);
    } 
    return usefadeonend ? new CarInOutTasks(COUTCA, fadein) : new CarInOutTasks(COUTCA, endTask);
  }
  
  private CarInOutTasks makecarin() {
    long endTask = eng.AddEventTask(this.task);
    long person_ingame = eng.AddScriptTask(this.task, new PlacePersonInGame(this.person));
    String carPrefix = eng.GetVehiclePrefix(this.vehicle.getCar());
    long in_cabin = eng.AddScriptTask(this.task, drvscripts.inCabinState(this.vehicle));
    long turnoffbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(false));
    long turnonbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(true));
    long animEvent = eng.AddEventTask(this.task);
    eng.EventTask_AnimationEventIn(animEvent, false);
    CameraSceneManager sceneCameraIn = new CameraSceneManager(0, this.task, this.actor.gModelname(), carPrefix);
    long CINCA = eng.AddScriptTask(this.task, sceneCameraIn.getCreateSceneScriptTask());
    long deleteCINCAR = eng.AddScriptTask(this.task, sceneCameraIn);
    eng.OnBegTASK(CINCA, "play", person_ingame);
    eng.OnEndTASK(CINCA, "play", turnoffbuffercam);
    eng.OnEndTASK(CINCA, "play", in_cabin);
    eng.OnEndTASK(animEvent, "play", deleteCINCAR);
    eng.OnEndTASK(animEvent, "play", turnonbuffercam);
    eng.OnEndTASK(animEvent, "end", endTask);
    return new CarInOutTasks(CINCA, endTask);
  }
}
