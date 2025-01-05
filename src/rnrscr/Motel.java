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
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.CarInOutTasks;
import rnrscr.Helper;
import rnrscr.SOscene;
import rnrscr.camscripts;
import rnrscr.drvscripts;

public class Motel {
    private static final String INSIDE_ANIMATION = "ivan_in_motel";
    private actorveh car;
    private long task;
    private SCRuniperson person;
    private boolean _play_short = false;

    public void MotelInside() {
        aiplayer actor = Crew.getIgrok();
        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        this.task = eng.CreateTASK(this.person);
        Helper.placePersonToCar(this.person, this.car);
        long movetocar = eng.AddScriptTask(this.task, drvscripts.placePersonToCar(this.person, this.car));
        long place_in_Object_inside = eng.AddScriptTask(this.task, new PlaceInModelPosition());
        long cominginside = eng.AddAnimTASK(this.task, INSIDE_ANIMATION, 0.0);
        long door2 = eng.AddScriptTask(this.task, new createanimatedmotel());
        long deletedoor2 = eng.AddScriptTask(this.task, new deleteanimateddoorinside_fromperson());
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(-1.0, 0.0, 0.0);
        mrot.v1.Set(0.0, 0.0, 1.0);
        mrot.v2.Set(0.0, 1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        SCRcamera cam_motel_comingin = SCRcamera.CreateCamera("anm_cam");
        cam_motel_comingin.SetInHotelWorld();
        cam_motel_comingin.SetPlayCycleAndConsecutively(false);
        cam_motel_comingin.SetPlayConsecutively(true);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel1", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel2", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel3", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel4", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel5", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel6", "Camera_in_motel", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_motel7", "Camera_in_motel", mrot, mrotcam);
        SCRcamera cam_newspaper = SCRcamera.CreateCamera("anm_cam");
        cam_newspaper.SetPlayConsecutively(false);
        cam_newspaper.SetPlayCycleAndConsecutively(false);
        cam_newspaper.SetInHotelWorld();
        cam_newspaper.AddAnimation("Root_Camera_in_motel8", "Camera_in_motel", mrot, mrotcam);
        matrixJ mroot = new matrixJ();
        mroot.Set0(-1.0, 0.0, 0.0);
        mroot.Set1(0.0, -1.0, 0.0);
        mroot.Set2(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        long camera_newspaper = eng.AddScriptTask(this.task, camscripts.playCamera_bindMatrix(cam_newspaper, mroot, pos));
        long deletecamera_newspaper = eng.AddScriptTask(this.task, camscripts.deleteCamera(cam_newspaper));
        long NEWSPAPEREND = eng.AddEventTask(this.task);
        eng.EventTask_onMOTELMessageClosed(NEWSPAPEREND, false);
        long realesecar = eng.AddScriptTask(this.task, new ReleaseFromParking());
        long deletetask = eng.AddScriptTask(this.task, new FinishTask());
        long cr_newspaper = eng.AddScriptTask(this.task, new CreateMenu());
        long event_show_menu = eng.AddEventTask(this.task);
        long show_menu = eng.AddScriptTask(this.task, new ShowMenu());
        long develop_menu = eng.AddScriptTask(this.task, new DevelopMenu());
        eng.EventTask_onMOTELDevelopFinish(event_show_menu, false);
        long motel_world = eng.AddChangeWorldTask(this.task, "motel", "so");
        long game_world = eng.AddChangeWorldTask(this.task, "game", "simple");
        long camera_comimgin = eng.AddScriptTask(this.task, camscripts.playCamera_bindMatrix(cam_motel_comingin, mroot, pos));
        long stopcamera_comimgin = eng.AddScriptTask(this.task, camscripts.stopCamera(cam_motel_comingin));
        long person_inhotelworld = eng.AddScriptTask(this.task, new PlaceInModel());
        long person_ingame = eng.AddScriptTask(this.task, new PlaceInGame());
        long out_cabin = eng.AddScriptTask(this.task, drvscripts.outCabinState(this.car));
        long set_anm_camera_dependence_task = eng.AddScriptTask(this.task, new Set_anm_camera_dependence(cominginside, cam_motel_comingin));
        eng.OnEndTASK(NEWSPAPEREND, "play", game_world);
        eng.OnEndTASK(NEWSPAPEREND, "play", deletecamera_newspaper);
        eng.OnEndTASK(game_world, "play", person_ingame);
        eng.OnEndTASK(game_world, "play", movetocar);
        SOscene SC = new SOscene();
        SC.task = this.task;
        SC.person = this.person;
        SC.actor = actor;
        SC.vehicle = this.car;
        long PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_out = this._play_short ? new CarInOutTasks(0L, PARking) : SC.makecaroutOnEnd(PARking, true);
        CarInOutTasks CAR_in = SC.makecarinOnEnd(game_world);
        SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
        eng.playTASK(PARking);
        eng.disableControl();
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
        eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", deletetask);
        if (this._play_short) {
            eng.OnEndTASK(PARking, "play", out_cabin);
        }
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", motel_world);
        eng.OnEndTASK(motel_world, "play", place_in_Object_inside);
        eng.OnEndTASK(motel_world, "play", cominginside);
        eng.OnEndTASK(motel_world, "play", person_inhotelworld);
        eng.OnBegTASK(cominginside, "play", cr_newspaper);
        eng.OnBegTASK(cominginside, "play", door2);
        eng.OnMidTASK(cominginside, 13.5, 13.5, "play", develop_menu);
        eng.OnEndTASK(cominginside, "play", deletedoor2);
        eng.OnEndTASK(event_show_menu, "play", show_menu);
        eng.OnBegTASK(cominginside, "play", set_anm_camera_dependence_task);
        eng.OnBegTASK(cominginside, "play", camera_comimgin);
        eng.OnEndTASK(cominginside, "play", deletedoor2);
        eng.OnEndTASK(cominginside, "play", stopcamera_comimgin);
        eng.OnEndTASK(cominginside, "play", camera_newspaper);
        eng.OnEndTASK(cominginside, "play", NEWSPAPEREND);
    }

    public void MotelInside_short() {
        this._play_short = true;
        this.MotelInside();
    }

    static class Set_anm_camera_dependence
    implements IScriptTask {
        private SCRcamera cam;
        private long task;

        Set_anm_camera_dependence(long task, SCRcamera cam) {
            this.cam = cam;
            this.task = task;
        }

        public void launch() {
            eng.Anim_SetCamera(this.task, this.cam.nativePointer);
        }
    }

    class PlaceInGame
    implements IScriptTask {
        PlaceInGame() {
        }

        public void launch() {
            Motel.this.person.SetInGameWorld();
            eng.returnCameraToGameWorld();
        }
    }

    class PlaceInModel
    implements IScriptTask {
        PlaceInModel() {
        }

        public void launch() {
            Motel.this.person.SetInHotelWorld();
        }
    }

    class PlaceInModelPosition
    implements IScriptTask {
        PlaceInModelPosition() {
        }

        public void launch() {
            Motel.this.person.SetPosition(new vectorJ(0.0, 0.0, 0.0));
            Motel.this.person.SetDirection(new vectorJ(0.0, 1.0, 0.0));
            Motel.this.person.play();
        }
    }

    static class DevelopMenu
    implements IScriptTask {
        DevelopMenu() {
        }

        public void launch() {
            event.Setevent(7002);
        }
    }

    static class ShowMenu
    implements IScriptTask {
        ShowMenu() {
        }

        public void launch() {
            menues.showMenu(7000);
        }
    }

    static class CreateMenu
    implements IScriptTask {
        CreateMenu() {
        }

        public void launch() {
            menues.CreateMotelMENU();
        }
    }

    class FinishTask
    implements IScriptTask {
        FinishTask() {
        }

        public void launch() {
            eng.DeleteTASK(Motel.this.task);
            eng.enableControl();
        }
    }

    class ReleaseFromParking
    implements IScriptTask {
        ReleaseFromParking() {
        }

        public void launch() {
            Motel.this.car.leaveParking();
        }
    }

    class deleteanimateddoorinside_fromperson
    implements IScriptTask {
        deleteanimateddoorinside_fromperson() {
        }

        public void launch() {
            Motel.this.person.DeleteAnimatedSpace("SpaceDoor", "SpaceDoor_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_RC", "Space_RC_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_Doorhold01", "Space_Doorhold_in_motel", 0L);
            Motel.this.person.DeleteAnimatedSpace("Space_Doorhold02", "Space_Doorhold_in_motel", 0L);
        }
    }

    class createanimatedmotel
    implements IScriptTask {
        createanimatedmotel() {
        }

        public void launch() {
            matrixJ mrot = new matrixJ();
            mrot.v0.Set(1.0, 0.0, 0.0);
            mrot.v1.Set(0.0, 0.0, 1.0);
            mrot.v2.Set(0.0, -1.0, 0.0);
            matrixJ mrot_newspaper = new matrixJ();
            Motel.this.person.CreateAnimatedSpace_timedependance("SpaceDoor", "SpaceDoor_in_motel", "space_MDL-SpaceDoor", Motel.INSIDE_ANIMATION, 0.0, 0L, 0L, mrot_newspaper, true, false);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_RC", "Space_RC_in_motel", "space_MDL-Space_RC", Motel.INSIDE_ANIMATION, 0.0, 0L, 0L, mrot_newspaper, true, true);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_Doorhold01", "Space_Doorhold_in_motel", "space_MDL-Space_Doorhold01", Motel.INSIDE_ANIMATION, 0.0, 0L, 0L, mrot_newspaper, true, true);
            Motel.this.person.CreateAnimatedSpace_timedependance("Space_Doorhold02", "Space_Doorhold_in_motel", "space_MDL-Space_Doorhold02", Motel.INSIDE_ANIMATION, 0.0, 0L, 0L, mrot_newspaper, true, true);
        }
    }
}

