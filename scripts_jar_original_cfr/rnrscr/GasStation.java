/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menu.menues;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.IScriptTask;
import rnrcore.SCRcamera;
import rnrcore.SCRuniperson;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.animation;
import rnrscr.camscripts;
import rnrscr.drvscripts;

public class GasStation {
    private static final String[] ANIMS = new String[]{"IvanOnGas001", "IvanOnGas002", "IvanOnGas003"};
    private static final int SCENE1 = 0;
    private static final int SCENE2 = 1;
    private static final int SCENE3 = 2;
    private static final String ANM_SPACE = "space_GlobalSRT";
    private static final String MODEL_SPACE = "space_IVAN_NEWGlobalSRT";
    private static final String[] ANIM_ITEMS = new String[]{"GasKard001", "GasKard001"};
    private static final String[] ANIM_ITEMS_ANIMS = new String[]{"GasKard001Ivan", "GasKard001Ivan2"};
    private static final int ITEM_SCENE1 = 0;
    private static final int ITEM_SCENE2 = 1;
    private actorveh car;
    private SCRuniperson person;
    private long task;
    private SCRcamera cam1;
    private SCRcamera cam_anims;

    public void runGasStationScript(int nom_pump, vectorJ posit, vectorJ dirit) {
        camscripts.buffercamturnon();
        aiplayer actor = Crew.getIgrok();
        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        if (nom_pump >= 5) {
            nom_pump = 4;
        }
        String carprefix = eng.GetVehiclePrefix(this.car.getCar());
        String personprefix = eng.GetManPrefix(this.person.nativePointer);
        this.task = eng.CreateTASK(this.person);
        long mixtask = eng.AddMergeAnimPositioningTASK_CarWheel_start(this.task, this.car, this.person, posit, dirit, personprefix + "_out" + carprefix, ANIMS[0], ANM_SPACE, MODEL_SPACE, 0.5);
        long mixtaskin = eng.AddMergeAnimPositioningTASK_CarWheel_finish(this.task, posit, dirit, this.car, this.person, ANIMS[1], personprefix + "_in" + carprefix, ANM_SPACE, MODEL_SPACE, 0.5);
        long second = eng.AddAnimTASK(this.task, ANIMS[0], 0.0);
        long loopstay = eng.AddAnimTASKLoop(this.task, ANIMS[2], 0.0);
        long third = eng.AddEventTask(this.task);
        long forth = eng.AddAnimTASK(this.task, ANIMS[1], 0.0);
        long gasmenu = eng.AddScriptTask(this.task, new CreateMenu());
        long realesecar = eng.AddScriptTask(this.task, new ReleaseCar());
        long kard1 = eng.AddScriptTask(this.task, new CreateAnimatedItem(0));
        long kard2 = eng.AddScriptTask(this.task, new CreateAnimatedItem(1));
        long deletekard1 = eng.AddScriptTask(this.task, new DeleteAnimatedItem(0));
        long deletekard2 = eng.AddScriptTask(this.task, new DeleteAnimatedItem(1));
        long door1 = eng.AddScriptTask(this.task, drvscripts.animatedDoorOut(this.person, this.car));
        long deletedoor1 = eng.AddScriptTask(this.task, drvscripts.deleteAnimatedDoorOut(this.person, this.car));
        long door2 = eng.AddScriptTask(this.task, drvscripts.animatedDoorIn(this.person, this.car));
        long deletedoor2 = eng.AddScriptTask(this.task, drvscripts.deleteAnimatedDoorIn(this.person, this.car));
        this.cam1 = SCRcamera.CreateCamera("anm");
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(0.1, 0.0, 0.0);
        mrot.v1.Set(0.0, 0.0, 0.1);
        mrot.v2.Set(0.0, -0.1, 0.0);
        matrixJ mrotcam = new matrixJ();
        mrotcam.v0.Set(1.0, 0.0, 0.0);
        mrotcam.v1.Set(0.0, 1.0, 0.0);
        mrotcam.v2.Set(0.0, 0.0, 1.0);
        String camname1 = "camera_" + nom_pump + "pos1";
        String camname2 = "camera_" + nom_pump + "pos2";
        String camname3 = "camera_gen_pass1";
        String camname4 = "camera_gen_pass2";
        String camname5 = "camera_gen1";
        String camname6 = "camera_gen2";
        String camname7 = "camera_gen3";
        this.cam1.AddAnimation(camname1, "space_MDL-" + camname1, mrot, mrotcam);
        this.cam1.AddAnimation(camname2, "space_MDL-" + camname2, mrot, mrotcam);
        this.cam1.AddAnimation(camname3, "space_MDL-" + camname3, mrot, mrotcam);
        this.cam1.AddAnimation(camname4, "space_MDL-" + camname4, mrot, mrotcam);
        this.cam1.AddAnimation(camname5, "space_MDL-" + camname5, mrot, mrotcam);
        this.cam1.AddAnimation(camname6, "space_MDL-" + camname6, mrot, mrotcam);
        this.cam1.AddAnimation(camname7, "space_MDL-" + camname7, mrot, mrotcam);
        this.cam1.BindToGasStation(eng.GetCurrentGasStation());
        long camera1 = eng.AddScriptTask(this.task, camscripts.playCamera(this.cam1));
        long deleteall_andvehcam = eng.AddScriptTask(this.task, new FinishTask());
        this.cam_anims = SCRcamera.CreateCamera("anm");
        this.cam_anims.SetPlayConsecutively(true);
        this.cam_anims.SetPlayCycleAndConsecutively(true);
        String cam_animname = "";
        int choose = animation.RandomFromNom(4);
        choose = 4;
        switch (choose) {
            case 1: {
                cam_animname = "camera_RUL2";
                break;
            }
            case 2: {
                cam_animname = "camera_RUL3";
                break;
            }
            case 3: {
                cam_animname = "camera_RUL4";
                break;
            }
            case 4: {
                cam_animname = "camera_RUL5";
            }
        }
        matrixJ mrot_CA = new matrixJ();
        mrot_CA.v0.Set(0.0, 0.1, 0.0);
        mrot_CA.v1.Set(0.0, 0.0, 0.1);
        mrot_CA.v2.Set(0.1, 0.0, 0.0);
        matrixJ mrotcam_CA = new matrixJ();
        mrotcam_CA.v0.Set(0.0, 0.0, -1.0);
        mrotcam_CA.v1.Set(0.0, 1.0, 0.0);
        mrotcam_CA.v2.Set(1.0, 0.0, 0.0);
        this.cam_anims.AddAnimation(cam_animname, "space_MDL-" + cam_animname, mrot_CA, mrotcam_CA);
        long camera_ANIMATION = eng.AddScriptTask(this.task, camscripts.playCamera_bindSteerWheel(this.cam_anims, this.car));
        long camera_ANIMATION_play = eng.AddScriptTask(this.task, camscripts.playCamera(this.cam_anims));
        long stopcamera_ANIMATION = eng.AddScriptTask(this.task, camscripts.stopCamera(this.cam_anims));
        long endscene = eng.AddEventTask(this.task);
        long justcametopump = eng.AddEventTask(this.task);
        eng.EventTask_onGasStationLeave(endscene, false);
        eng.EventTask_onGasStationCame(justcametopump, false);
        eng.EventTask_onGasStationMessageClosed(third, false);
        eng.OnBegTASK(justcametopump, "play", door1);
        eng.OnEndTASK(justcametopump, "play", deletedoor1);
        eng.OnEndTASK(justcametopump, "play", mixtask);
        eng.OnEndTASK(justcametopump, "play", second);
        SCRcamera cOutCar = SCRcamera.CreateCamera("anm");
        String _cam_animname = "camera_RUL2";
        matrixJ _mrot_CA = new matrixJ();
        _mrot_CA.Set0(0.0, 0.1, 0.0);
        _mrot_CA.Set1(0.0, 0.0, 0.1);
        _mrot_CA.Set2(0.1, 0.0, 0.0);
        matrixJ _mrotcam_CA = new matrixJ();
        _mrotcam_CA.Set0(0.0, 0.0, -1.0);
        _mrotcam_CA.Set1(0.0, 1.0, 0.0);
        _mrotcam_CA.Set2(1.0, 0.0, 0.0);
        cOutCar.AddAnimation(_cam_animname, "space_MDL-" + _cam_animname, _mrot_CA, _mrotcam_CA);
        SCRcamera cInCar = SCRcamera.CreateCamera("anm");
        String _cam_animname2 = "camera_RUL2";
        matrixJ _mrot_CA2 = new matrixJ();
        _mrot_CA2.Set0(0.0, 0.1, 0.0);
        _mrot_CA2.Set1(0.0, 0.0, 0.1);
        _mrot_CA2.Set2(0.1, 0.0, 0.0);
        matrixJ _mrotcam_CA2 = new matrixJ();
        _mrotcam_CA2.Set0(0.0, 0.0, -1.0);
        _mrotcam_CA2.Set1(0.0, 1.0, 0.0);
        _mrotcam_CA2.Set2(1.0, 0.0, 0.0);
        cInCar.AddAnimation(_cam_animname2, "space_MDL-" + _cam_animname2, _mrot_CA2, _mrotcam_CA2);
        long COUTCA = eng.AddScriptTask(this.task, camscripts.playCamera_bindSteerWheel(cOutCar, this.car));
        long deleteCOUTCAR = eng.AddScriptTask(this.task, camscripts.deleteCamera(cOutCar));
        long CINCA = eng.AddScriptTask(this.task, camscripts.playCamera_bindSteerWheel(cInCar, this.car));
        long turnoffbuffercam = eng.AddScriptTask(this.task, camscripts.buffercamturn(false));
        long deleteCINCAR = eng.AddScriptTask(this.task, camscripts.deleteCamera(cInCar));
        eng.OnEndTASK(justcametopump, "play", stopcamera_ANIMATION);
        eng.OnEndTASK(justcametopump, "play", COUTCA);
        eng.OnEndTASK(mixtask, "play", kard1);
        eng.OnEndTASK(second, "play", deleteCOUTCAR);
        eng.OnEndTASK(second, "play", camera1);
        eng.OnEndTASK(second, "play", deletekard1);
        eng.OnEndTASK(second, "play", loopstay);
        eng.OnMidTASK(second, 2.0, 2.0, "play", gasmenu);
        eng.OnEndTASK(third, "end", second);
        eng.OnEndTASK(third, "play", camera_ANIMATION_play);
        eng.OnEndTASK(third, "play", forth);
        eng.OnEndTASK(third, "play", kard2);
        eng.OnBegTASK(forth, "end", loopstay);
        eng.OnEndTASK(forth, "play", CINCA);
        eng.OnEndTASK(forth, "play", deletekard2);
        eng.OnEndTASK(forth, "play", endscene);
        eng.OnEndTASK(forth, "play", mixtaskin);
        eng.OnEndTASK(forth, "play", realesecar);
        eng.OnBegTASK(forth, "play", door2);
        eng.OnEndTASK(forth, "play", deletedoor2);
        eng.OnEndTASK(forth, "play", turnoffbuffercam);
        eng.OnEndTASK(forth, "play", deleteCINCAR);
        eng.OnEndTASK(forth, "play", endscene);
        eng.OnEndTASK(endscene, "play", deleteall_andvehcam);
        eng.OnEndTASK(justcametopump, "play", second);
        eng.playTASK(camera_ANIMATION);
        eng.playTASK(justcametopump);
        eng.playTASK(third);
    }

    class FinishTask
    implements IScriptTask {
        FinishTask() {
        }

        public void launch() {
            camscripts.deleteCamera(GasStation.this.cam1).launch();
            camscripts.deleteCamera(GasStation.this.cam_anims).launch();
            eng.DeleteTASK(GasStation.this.task);
            camscripts.buffercamturnon();
        }
    }

    class DeleteAnimatedItem
    implements IScriptTask {
        private int scene;

        DeleteAnimatedItem(int scene) {
            this.scene = scene;
        }

        public void launch() {
            GasStation.this.person.DeleteAnimatedItem(ANIM_ITEMS[this.scene], ANIM_ITEMS_ANIMS[this.scene]);
        }
    }

    class CreateAnimatedItem
    implements IScriptTask {
        private int scene;

        CreateAnimatedItem(int scene) {
            this.scene = scene;
        }

        public void launch() {
            GasStation.this.person.CreateAnimatedItem(ANIM_ITEMS[this.scene], ANIM_ITEMS_ANIMS[this.scene], 0.0);
        }
    }

    class ReleaseCar
    implements IScriptTask {
        ReleaseCar() {
        }

        public void launch() {
            GasStation.this.car.leaveParking();
        }
    }

    static class CreateMenu
    implements IScriptTask {
        CreateMenu() {
        }

        public void launch() {
            menues.CreateGASSTATIONMENU();
        }
    }
}

