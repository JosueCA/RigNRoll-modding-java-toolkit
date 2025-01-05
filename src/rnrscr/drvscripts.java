/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menu.menues;
import players.actorveh;
import players.aiplayer;
import rnrcore.IScriptTask;
import rnrcore.SCRcamera;
import rnrcore.SCRuniperson;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrscenario.ThreadTask;
import rnrscr.CarInOutTasks;
import rnrscr.Helper;
import rnrscr.SOscene;

public class drvscripts {
    private static Object synk_obj = new Object();
    public static drvscripts helper = new drvscripts(synk_obj);

    public static IScriptTask placePersonToCar(SCRuniperson person, actorveh car) {
        return new PlacePersonToCar(person, car);
    }

    public static IScriptTask resumeScript() {
        return new ResumeScript();
    }

    public static IScriptTask resumeScript(ThreadTask safe) {
        return new ResumeScriptSafe(safe);
    }

    public static IScriptTask outCabinState(actorveh car) {
        return new OutCabinState(car);
    }

    public static IScriptTask inCabinState(actorveh car) {
        return new InCabinState(car);
    }

    public static IScriptTask animatedDoorOut(SCRuniperson person, actorveh car) {
        return new AnimatedDoor(person, car, "_out");
    }

    public static IScriptTask animatedDoorIn(SCRuniperson person, actorveh car) {
        return new AnimatedDoor(person, car, "_in");
    }

    public static IScriptTask deleteAnimatedDoorOut(SCRuniperson person, actorveh car) {
        return new DeleteAnimatedDoor(person, car, "_out");
    }

    public static IScriptTask deleteAnimatedDoorIn(SCRuniperson person, actorveh car) {
        return new DeleteAnimatedDoor(person, car, "_in");
    }

    public drvscripts(Object syncObject) {
    }

    public void DeleteTaskNCam(long tsk, long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.DeleteCamera();
        eng.DeleteTASK(tsk);
    }

    public void playOutOffCarThreaded(aiplayer pers, actorveh veh) {
        ThreadTask safe = new ThreadTask(null);
        this.playOutOffCarThreaded(pers, veh, safe);
    }

    public void playOutOffCarThreaded(aiplayer pers, actorveh veh, ThreadTask safe) {
        SCRuniperson person = pers.getModel();
        if (veh.getCar() == 0 || person.nativePointer == 0L) {
            return;
        }
        Helper.placePersonToCar(person, veh);
        long od = eng.CreateTASK(pers.getModel());
        long resumesct = eng.AddScriptTask(od, drvscripts.resumeScript(safe));
        SOscene SC = new SOscene();
        SC.task = od;
        SC.person = person;
        SC.actor = pers;
        SC.vehicle = veh;
        long PARking = eng.AddEventTask(od);
        CarInOutTasks CAR_out = SC.makecaroutOnStart(PARking, true);
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", resumesct);
        eng.OnBegTASK(PARking, "end", PARking);
        eng.playTASK(PARking);
        safe._susp();
        eng.DeleteTASK(od);
    }

    public void playInsideCarThreaded(aiplayer pers, actorveh veh) {
        ThreadTask safe = new ThreadTask(null);
        this.playInsideCarThreaded(pers, veh, safe);
    }

    public void playInsideCarThreaded(aiplayer pers, actorveh veh, ThreadTask safe) {
        SCRuniperson person = pers.getModel();
        if (0 == veh.getCar() || 0L == person.nativePointer) {
            return;
        }
        Helper.placePersonToCar(person, veh);
        long od = eng.CreateTASK(pers.getModel());
        long resumesct = eng.AddScriptTask(od, drvscripts.resumeScript(safe));
        SOscene SC = new SOscene();
        SC.task = od;
        SC.person = person;
        SC.actor = pers;
        SC.vehicle = veh;
        long startScene = eng.AddEventTask(od);
        CarInOutTasks CAR_in = SC.makecarinOnStart(startScene);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", resumesct);
        eng.playTASK(startScene);
        safe._susp();
        eng.DeleteTASK(od);
    }

    public static void playInsideCarFast(aiplayer pers, actorveh car) {
        SCRuniperson person = pers.getModel();
        if (0 == car.getCar() || 0L == person.nativePointer) {
            return;
        }
        Helper.placePersonToCar(person, car);
        eng.SwitchDriver_in_cabin(car.getCar());
    }

    public void BlowCaritself(long car) {
        eng.BlowCar((int)car);
    }

    public static void BlowScene(aiplayer player, actorveh car) {
        eng.disableEscKeyScenesSkip();
        if (null == car || null == player) {
            return;
        }
        car.sVeclocity(0.0);
        long od = eng.CreateTASK(player.getModel());
        long blowprocess = eng.AddEventTask(od);
        long blow = eng.AddScriptTask(od, new BlowCarItSelf(car));
        long mune = eng.AddScriptTask(od, new BlowCarMenu());
        BlowCarScene blowCarScene = new BlowCarScene(car);
        long track = eng.AddScriptTask(od, blowCarScene);
        long end = eng.AddScriptTask(od, new EndBlowCar(od, blowCarScene));
        eng.OnBegTASK(blowprocess, "play", track);
        eng.OnMidTASK(blowprocess, 0.1, 0.1, "play", blow);
        eng.OnMidTASK(blowprocess, 15.0, 15.0, "play", mune);
        eng.OnEndTASK(mune, "play", end);
        eng.playTASK(blowprocess);
    }

    public static void BlowSceneOtherPlayer(aiplayer player, actorveh car) {
        if (null == car || null == player) {
            return;
        }
        car.sVeclocity(0.0);
        long od = eng.CreateTASK(player.getModel());
        long blowprocess = eng.AddEventTask(od);
        long blow = eng.AddScriptTask(od, new BlowCarItSelf(car));
        long track = eng.AddScriptTask(od, new BlowCarSceneItSelf(car));
        long end = eng.AddScriptTask(od, new EndBlowCar(od, null));
        eng.OnBegTASK(blowprocess, "play", track);
        eng.OnMidTASK(blowprocess, 0.1, 0.1, "play", blow);
        eng.OnEndTASK(blow, "play", end);
        eng.playTASK(blowprocess);
    }

    static class EndBlowCar
    implements IScriptTask {
        long task;
        private IListenEndPredefAnimation m_listenerLaunch;

        EndBlowCar(long task, IListenEndPredefAnimation listenerLaunch) {
            this.task = task;
            this.m_listenerLaunch = listenerLaunch;
        }

        public void launch() {
            if (null != this.m_listenerLaunch) {
                this.m_listenerLaunch.animationFinished();
            }
            eng.DeleteTASK(this.task);
        }
    }

    static class BlowCarMenu
    implements IScriptTask {
        BlowCarMenu() {
        }

        public void launch() {
            eng.enableEscKeyScenesSkip();
            menues.gameoverMenu();
        }
    }

    static class BlowCarSceneItSelf
    implements IScriptTask {
        private actorveh car;

        BlowCarSceneItSelf(actorveh car) {
            this.car = car;
        }

        public void launch() {
            Preset blowpreset = new Preset();
            blowpreset.car = this.car;
            scenetrack.CreateSceneXML("blowcar2070", 17, blowpreset);
        }
    }

    static class BlowCarScene
    implements IScriptTask,
    IListenEndPredefAnimation {
        private actorveh car;
        private long scene;

        BlowCarScene(actorveh car) {
            this.car = car;
            this.scene = 0L;
        }

        public void launch() {
            Preset blowpreset = new Preset();
            blowpreset.car = this.car;
            this.scene = scenetrack.CreateSceneXML("blowcar", 1, blowpreset);
        }

        public void animationFinished() {
            if (this.scene != 0L) {
                scenetrack.DeleteScene(this.scene);
            }
        }
    }

    static interface IListenEndPredefAnimation {
        public void animationFinished();
    }

    static class Preset {
        actorveh car;

        Preset() {
        }
    }

    static class PlacePersonToCar
    implements IScriptTask {
        private SCRuniperson person;
        private actorveh car;

        PlacePersonToCar(SCRuniperson person, actorveh car) {
            this.person = person;
            this.car = car;
        }

        public void launch() {
            Helper.placePersonToCar(this.person, this.car);
            this.person.play();
        }
    }

    static class BlowCarItSelf
    implements IScriptTask {
        private actorveh car;

        BlowCarItSelf(actorveh car) {
            this.car = car;
        }

        public void launch() {
            eng.BlowCar(this.car.getCar());
        }
    }

    static class ResumeScriptSafe
    implements IScriptTask {
        private ThreadTask safe;

        ResumeScriptSafe(ThreadTask safe) {
            this.safe = safe;
        }

        public void launch() {
            this.safe._resum();
        }
    }

    static class ResumeScript
    implements IScriptTask {
        ResumeScript() {
            eng.log("ResumeScript");
        }

        public void launch() {
            ScenarioSync.resumeScene();
        }
    }

    static class DeleteAnimatedDoor
    implements IScriptTask {
        private SCRuniperson person;
        private actorveh car;
        private String anm_suffix;

        DeleteAnimatedDoor(SCRuniperson person, actorveh car, String anm_suffix) {
            this.person = person;
            this.car = car;
            this.anm_suffix = anm_suffix;
        }

        public void launch() {
            String modelprefix = eng.GetVehiclePrefix(this.car.getCar());
            this.person.DeleteAnimatedSpace(modelprefix + "door1Space", eng.GetManPrefix(this.person.nativePointer) + this.anm_suffix + modelprefix, eng.CarNode(this.car.getCar()));
        }
    }

    static class AnimatedDoor
    implements IScriptTask {
        private SCRuniperson person;
        private actorveh car;
        private String anm_suffix;

        AnimatedDoor(SCRuniperson person, actorveh car, String anm_suffix) {
            this.person = person;
            this.car = car;
            this.anm_suffix = anm_suffix;
        }

        public void launch() {
            String modelprefix = eng.GetVehiclePrefix(this.car.getCar());
            matrixJ mrot = new matrixJ();
            String prefix = eng.GetManPrefix(this.person.nativePointer);
            this.person.CreateAnimatedSpace_timedependance(modelprefix + "door1Space", prefix + this.anm_suffix + modelprefix, "space_door", prefix + this.anm_suffix + modelprefix, 0.0, eng.CarNode(this.car.getCar()), this.car.getCar(), mrot, true, false);
        }
    }

    static class InCabinState
    implements IScriptTask {
        private actorveh car;

        InCabinState(actorveh car) {
            this.car = car;
        }

        public void launch() {
            eng.SwitchDriver_in_cabin(this.car.getCar());
        }
    }

    static class OutCabinState
    implements IScriptTask {
        private actorveh car;

        OutCabinState(actorveh car) {
            this.car = car;
        }

        public void launch() {
            eng.SwitchDriver_outside_cabin(this.car.getCar());
        }
    }
}

