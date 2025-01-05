/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Vector;
import menu.menues;
import menuscript.STOmenues;
import players.Crew;
import players.actorveh;
import rnrcore.IScriptTask;
import rnrcore.SCRcamera;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.Ithreadprocedure;
import rnrscenario.ThreadTask;
import rnrscr.AdvancedRandom;
import rnrscr.CarInOutTasks;
import rnrscr.Helper;
import rnrscr.SOscene;
import rnrscr.STOpresets;
import rnrscr.STOreaction;
import rnrscr.SyncMonitors;
import rnrscr.animation;
import rnrscr.cSpecObjects;
import rnrscr.camscripts;
import rnrscr.drvscripts;
import rnrscr.trackscripts;

public class stoscene
extends animation {
    static STOreaction reaction = new STOreaction();
    static int NOMMENONREPAIR = 1;
    private SCRcamera camera_aroundsto_inside = null;
    private SCRcamera camera_gofromsto_inside = null;
    private SCRcamera camera_back_of_vehicle = null;
    private SCRcamera menuCamera = null;
    matrixJ scene_matr_SHOP = null;
    vectorJ scene_posit = null;
    int scene_nomgate = -1;
    SCRuniperson repman = null;

    public void animateCamera2() {
        this.camera_aroundsto_inside.DeleteCamera();
        this.camera_aroundsto_inside = null;
        matrixJ Mrot_repaircam = new matrixJ();
        Mrot_repaircam.Set0(1.0, 0.0, 0.0);
        Mrot_repaircam.Set1(0.0, 0.0, 1.0);
        Mrot_repaircam.Set2(0.0, -1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        SCRcamera cam1 = SCRcamera.CreateCamera("anm");
        cam1.nCamera("anm");
        if (AdvancedRandom.probability(0.5)) {
            cam1.AddAnimation("repair_camera_1pos1", "space_MDL-camera_1pos1", Mrot_repaircam, mrotcam);
        } else {
            cam1.AddAnimation("repair_camera_1pos3", "space_MDL-camera_1pos3", Mrot_repaircam, mrotcam);
        }
        new camscripts().PlayCamera_bindSTO(cam1.nativePointer, this.scene_nomgate);
        this.camera_gofromsto_inside = cam1;
    }

    public void deleteCamera2() {
        this.camera_gofromsto_inside.DeleteCamera();
        this.camera_gofromsto_inside = null;
        Helper.restoreCameraToIgrokCar();
    }

    void createStoSceneTasked(STOPreset preset2, Vector pool) {
        SCRuniperson person = Crew.getIgrok().getModel();
        long task = eng.CreateTASK(person);
        long arrivedEvent = eng.AddEventTask(task);
        long startEvent = eng.AddEventTask(task);
        long endEvent = eng.AddEventTask(task);
        long proceedTask = eng.AddScriptTask(task, new ProceedFromSTO());
        long deleteTask = eng.AddScriptTask(task, new DeleteTask(task));
        long menuClosedEvent = eng.AddEventTask(task);
        long listenMenuClosed = eng.AddScriptTask(task, new ListenMenuClosed(menuClosedEvent));
        long sceneTask = eng.AddScriptTask(task, new createSTOSCene(task, listenMenuClosed, preset2, pool));
        long menuCameraTask = eng.AddScriptTask(task, new MenuCamera(preset2));
        long deleteMenuCameraTask = eng.AddScriptTask(task, new DeleteMenuCamera());
        SOscene SC = new SOscene();
        SC.task = task;
        SC.person = person;
        SC.actor = Crew.getIgrok();
        SC.vehicle = Crew.getIgrokCar();
        eng.OnBegTASK(arrivedEvent, "end", startEvent);
        eng.OnEndTASK(startEvent, "end", arrivedEvent);
        CarInOutTasks CAR_out = SC.makecaroutOnEnd(startEvent, false);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(endEvent);
        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", sceneTask);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", proceedTask);
        eng.OnBegTASK(proceedTask, "play", deleteTask);
        eng.OnBegTASK(menuClosedEvent, "end", endEvent);
        eng.OnBegTASK(menuClosedEvent, "play", deleteMenuCameraTask);
        eng.OnBegTASK(listenMenuClosed, "play", menuCameraTask);
        eng.playTASK(arrivedEvent);
    }

    public void proceedScene() {
        this.animateCamera2();
        eng.returnCameraToGameWorld();
        actorveh ourcar = Crew.getIgrokCar();
        this.Repair_RealeseCar(eng.GetVehicleDriver(ourcar.getCar()));
    }

    public int GetMoney() {
        if (null != this.currentOwner && null != this.currentOwner.Presets()) {
            return ((STOpresets)this.currentOwner.Presets()).getMoney();
        }
        return -1;
    }

    public void SetMoney(int value) {
        if (null != this.currentOwner && null != this.currentOwner.Presets()) {
            ((STOpresets)this.currentOwner.Presets()).setMoney(value);
        }
    }

    public void StartWorkWithSO(cSpecObjects s) {
        this.currentOwner = s;
        if (null == s.Presets()) {
            STOpresets ourPresets = new STOpresets();
            ourPresets.setMoney(0);
            s.SetPresets(ourPresets);
        }
        STOpresets stopr = (STOpresets)s.Presets();
        stopr.historycomming.visit();
    }

    public void Repair_RealeseCar(long addi) {
        event.Setevent(2002 + (int)addi);
        event.Setevent(25002);
    }

    public long RepairOffice(matrixJ matr_SHOP, vectorJ posit, int nomgate) {
        this.scene_matr_SHOP = matr_SHOP;
        this.scene_posit = posit;
        this.scene_nomgate = nomgate;
        this.activateSTOScene();
        this.createOperatorForPool();
        return this.repman.nativePointer;
    }

    public void createOperatorForPool() {
        this.LoadModels(NOMMENONREPAIR, 0);
        this.repman = SCRuniperson.createCutSceneAmbientPerson(this.getModelName(0, true), null);
        this.repman.SetInGameWorld();
        this.repman.play();
    }

    public void activateSTOScene() {
        actorveh ourcar = Crew.getIgrokCar();
        event.createScriptObject(17, new AnimateCamera1());
        event.createScriptObject(19, new PauseAnimateCamera1());
        event.createScriptObject(19, new LaunchScene(this));
        event.createScriptObject(18, new ExitScene());
        SCRcamera cam_back = SCRcamera.CreateCamera("anm");
        cam_back.nCamera("anm");
        camscripts camSCR = new camscripts();
        camSCR.AddCamera_BackOfVehicle(cam_back);
        camSCR.PlayCamera_bindSteerWheel(cam_back.nativePointer, ourcar.getCar());
        this.camera_back_of_vehicle = cam_back;
        eng.disableControl();
    }

    void fillPresetSoundStrings(STOPreset preset2) {
        STOpresets stopr = (STOpresets)this.currentOwner.Presets();
        reaction.createReaction(preset2, stopr.historycomming);
    }

    class CSTOSCene
    implements Ithreadprocedure {
        ThreadTask safe = null;
        STOPreset preset = null;
        Vector pool = new Vector();
        public volatile long scene;
        public volatile stoscene sc;
        SCRcamera cSHOP_menu = null;

        CSTOSCene() {
        }

        public void call() {
            actorveh ourcar = Crew.getIgrokCar();
            new drvscripts(SyncMonitors.getScenarioMonitor()).playOutOffCarThreaded(Crew.getIgrok(), ourcar, this.safe);
            STOmenues.createMenu();
            this.scene = trackscripts.initSceneXML(this.preset.sceneName, this.pool, this.preset);
            scenetrack.ReplaceSceneFlags(this.scene, 9);
            event.eventObject((int)this.scene, this.safe, "_resum");
            ThreadTask.create(new CStomenupanelevent());
            this.safe._susp();
            scenetrack.DeleteScene(this.scene);
            eng.lock();
            this.create_menucam();
            menues.showMenu(6000);
            eng.unlock();
            event.eventObject(2001, this.safe, "_resum");
            this.safe._susp();
            eng.lock();
            this.delete_menucam();
            eng.unlock();
            new drvscripts(SyncMonitors.getScenarioMonitor()).playInsideCarThreaded(Crew.getIgrok(), ourcar, this.safe);
            stoscene.this.proceedScene();
        }

        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        public void create_menucam() {
            matrixJ Mrot = new matrixJ();
            Mrot.Set0(-1.0, 0.0, 0.0);
            Mrot.Set1(0.0, 0.0, 1.0);
            Mrot.Set2(0.0, 1.0, 0.0);
            this.cSHOP_menu = SCRcamera.CreateCamera("anm_cam");
            this.cSHOP_menu.AddAnimation("Root_Camera_on_repair_5", "Camera_on_repair", Mrot, new matrixJ());
            new camscripts().PlayCamera_bindMatrix(this.cSHOP_menu.nativePointer, this.preset.M, this.preset.P);
        }

        public void delete_menucam() {
            camscripts.deleteCamera(this.cSHOP_menu).launch();
            this.sc.animateCamera2();
        }
    }

    class CStomenupanelevent
    implements Ithreadprocedure {
        ThreadTask safe = null;

        CStomenupanelevent() {
        }

        public void call() {
            this.simplewaitFor(13000);
            eng.lock();
            event.Setevent(2002);
            eng.unlock();
        }

        public void take(ThreadTask safe) {
            this.safe = safe;
        }

        void simplewaitFor(int timemillesec) {
            try {
                this.waitFor(timemillesec);
            }
            catch (InterruptedException e) {
                eng.writeLog("Script Error. CStomenupanelevent.");
            }
        }

        void waitFor(int timemillesec) throws InterruptedException {
            Thread.sleep(timemillesec);
        }
    }

    class STOPreset {
        vectorJ P = null;
        matrixJ M = null;
        String sceneName;
        String Nic1;
        String Nic2;
        String Rep1;
        String Rep2;

        STOPreset() {
        }
    }

    class ExitScene
    implements IScriptTask {
        ExitScene() {
        }

        public void launch() {
            stoscene.this.deleteCamera2();
            eng.enableControl();
        }
    }

    static class DevelopPanelAnimation
    extends TypicalAnm {
        private static final double TIME = 11.5;

        DevelopPanelAnimation() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (dt >= 11.5) {
                event.Setevent(2002);
                return true;
            }
            return false;
        }
    }

    class DeleteMenuCamera
    implements IScriptTask {
        DeleteMenuCamera() {
        }

        public void launch() {
            camscripts.deleteCamera(stoscene.this.menuCamera).launch();
        }
    }

    class MenuCamera
    implements IScriptTask {
        private STOPreset preset = null;

        MenuCamera(STOPreset preset2) {
            this.preset = preset2;
        }

        public void launch() {
            matrixJ Mrot = new matrixJ();
            Mrot.Set0(-1.0, 0.0, 0.0);
            Mrot.Set1(0.0, 0.0, 1.0);
            Mrot.Set2(0.0, 1.0, 0.0);
            stoscene.this.menuCamera = SCRcamera.CreateCamera("anm_cam");
            stoscene.this.menuCamera.AddAnimation("Root_Camera_on_repair_5", "Camera_on_repair", Mrot, new matrixJ());
            new camscripts().PlayCamera_bindMatrix(((stoscene)stoscene.this).menuCamera.nativePointer, this.preset.M, this.preset.P);
        }
    }

    class ListenMenuClosed
    implements IScriptTask {
        private static final String MENU_CLOSED = "menuClosed";
        private long menuClosedEvent;

        ListenMenuClosed(long task) {
            this.menuClosedEvent = task;
        }

        public void launch() {
            event.eventObject(2001, this, MENU_CLOSED);
            Helper.placePersonToCar(Crew.getIgrok().getModel(), Crew.getIgrokCar());
        }

        public void menuClosed() {
            eng.playTASK(this.menuClosedEvent);
        }
    }

    class createSTOSCene
    implements IScriptTask {
        private static final String METHOD_ENDSCENE = "endScene";
        private STOPreset preset = null;
        private Vector pool = new Vector();
        private long task;
        private long taskToLaunch;

        createSTOSCene(long task, long taskToLaunch, STOPreset preset2, Vector pool) {
            this.task = task;
            this.taskToLaunch = taskToLaunch;
            this.pool = pool;
            this.preset = preset2;
        }

        public void launch() {
            STOmenues.createMenu();
            new DevelopPanelAnimation();
            long scene = scenetrack.CreateSceneXMLPool(this.preset.sceneName, 17, this.pool, this.preset);
            event.eventObject((int)scene, this, METHOD_ENDSCENE);
            eng.AddSceneTrackToTask(this.task, scene);
        }

        public void endScene() {
            STOmenues.showMenu();
            eng.playTASK(this.taskToLaunch);
        }
    }

    class DeleteTask
    implements IScriptTask {
        private long task;

        DeleteTask(long task) {
            this.task = task;
        }

        public void launch() {
            eng.DeleteTASK(this.task);
        }
    }

    class ProceedFromSTO
    implements IScriptTask {
        ProceedFromSTO() {
        }

        public void launch() {
            stoscene.this.proceedScene();
        }
    }

    class LaunchScene
    implements IScriptTask {
        private stoscene sc;

        LaunchScene(stoscene sc) {
            this.sc = sc;
        }

        public void launch() {
            STOPreset preset2 = new STOPreset();
            preset2.M = stoscene.this.scene_matr_SHOP;
            preset2.P = stoscene.this.scene_posit;
            stoscene.this.fillPresetSoundStrings(preset2);
            SceneActorsPool poolman = new SceneActorsPool("repman", stoscene.this.repman);
            Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>();
            pool.add(poolman);
            stoscene.this.createStoSceneTasked(preset2, pool);
        }
    }

    class PauseAnimateCamera1
    implements IScriptTask {
        PauseAnimateCamera1() {
        }

        public void launch() {
            stoscene.this.camera_aroundsto_inside.StopCamera();
            camscripts.stopCamera(stoscene.this.camera_aroundsto_inside).launch();
        }
    }

    class AnimateCamera1
    implements IScriptTask {
        AnimateCamera1() {
        }

        public void launch() {
            stoscene.this.camera_back_of_vehicle.DeleteCamera();
            stoscene.this.camera_back_of_vehicle = null;
            matrixJ Mrot_repaircam = new matrixJ();
            Mrot_repaircam.Set0(1.0, 0.0, 0.0);
            Mrot_repaircam.Set1(0.0, 0.0, 1.0);
            Mrot_repaircam.Set2(0.0, -1.0, 0.0);
            matrixJ mrotcam = new matrixJ();
            SCRcamera cam1 = SCRcamera.CreateCamera("anm");
            cam1.nCamera("anm");
            cam1.SetPlayConsecutively(true);
            cam1.AddAnimation("repair_camera_1pos2", "space_MDL-camera_1pos2", Mrot_repaircam, mrotcam);
            new camscripts().PlayCamera_bindSTO(cam1.nativePointer, stoscene.this.scene_nomgate);
            stoscene.this.camera_aroundsto_inside = cam1;
        }
    }
}

