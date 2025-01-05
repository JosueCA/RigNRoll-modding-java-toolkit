/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import menu.menues;
import menuscript.office.OfficeMenu;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rnrcore.IScriptTask;
import rnrcore.IXMLSerializable;
import rnrcore.SCRcamera;
import rnrcore.SCRuniperson;
import rnrcore.ScenarioSync;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.CarInOutTasks;
import rnrscr.Helper;
import rnrscr.OfficeHelper;
import rnrscr.SOscene;
import rnrscr.camscripts;
import rnrscr.drvscripts;
import scriptEvents.EventsControllerHelper;

public class Office {
    private static final String[] INSIDE_IVAN_ANIMATIONS = new String[]{"ivan_in_office_01", "ivan_in_office_02", "ivan_in_office_03", "ivan_in_office_04", "ivan_in_office_05", "ivan_in_office_06", "ivan_in_office_07"};
    private static final int SCENE1 = 0;
    private static final int SCENE2 = 1;
    private static final int SCENE3 = 2;
    private static final int SCENE4 = 3;
    private static final int SCENE5 = 4;
    private static final int SCENE6 = 5;
    private static final int SCENE7 = 6;
    private static final String[] SPACE_ = new String[]{"Space_Office_Door01", "Space_Office_Door02", "Space_Office_ChairPart01", "Space_Office_ChairPart02", "Space_Office_DoorHold01", "Space_Office_DoorHold02", "Space_Office_DoorHold03", "Space_Office_DoorHold04"};
    private static final String[] ANM_ = new String[]{"space_MDL-Space_Door01", "space_MDL-Space_Door02", "space_MDL-Space_ChairPart01", "space_MDL-Space_ChairPart02", "space_MDL-Space_DoorHold01", "space_MDL-Space_DoorHold02", "space_MDL-Space_DoorHold03", "space_MDL-Space_DoorHold04"};
    private static final boolean[] rotate = new boolean[]{true, true, true, true, true, true, true, true};
    private static final boolean[] move = new boolean[]{false, false, false, false, true, true, true, true};
    private static final double ADVERTICEMENT_PROBABILITY = 0.3;
    private actorveh car;
    private long task;
    private SCRuniperson person;
    private boolean _play_short = false;
    private boolean win_economy_contest = true;
    public SCRcamera cam_menu_teleportaion = null;

    void makeComingAnimation(long task, int startSceneIndex, long start_task, long end_task, long task_delete) {
        int i;
        int sizeAnimations = INSIDE_IVAN_ANIMATIONS.length - startSceneIndex;
        long[] cominginside = new long[sizeAnimations];
        long[] anim = new long[sizeAnimations];
        long[] delanim = new long[sizeAnimations];
        for (i = 0; i < sizeAnimations; ++i) {
            int sceneIndex = i + startSceneIndex;
            String sceneName = INSIDE_IVAN_ANIMATIONS[sceneIndex];
            cominginside[i] = eng.AddAnimTASK(task, sceneName, 0.0);
            anim[i] = eng.AddScriptTask(task, new Createanimatedoffice(sceneIndex));
            delanim[i] = eng.AddScriptTask(task, new DeleteAnimatedoffice(sceneIndex));
        }
        eng.OnBegTASK(start_task, "play", cominginside[0]);
        for (i = 0; i < sizeAnimations; ++i) {
            eng.OnBegTASK(cominginside[i], "play", anim[i]);
            eng.OnEndTASK(cominginside[i], "play", delanim[i]);
            if (i >= sizeAnimations - 1) continue;
            eng.OnEndTASK(cominginside[i], "play", cominginside[i + 1]);
        }
        eng.OnEndTASK(cominginside[sizeAnimations - 1], "play", end_task);
        eng.OnEndTASK(end_task, "unsuspend", cominginside[sizeAnimations - 1]);
        for (long delTask : delanim) {
            eng.OnEndTASK(task_delete, "play", delTask);
        }
    }

    void makeComingCameraFullAnimation(long task, long start_task, long end_task) {
        matrixJ mrot = new matrixJ();
        mrot.Set0(-1.0, 0.0, 0.0);
        mrot.Set1(0.0, 0.0, 1.0);
        mrot.Set2(0.0, 1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        SCRcamera cam_motel_comingin = SCRcamera.CreateCamera("anm_cam");
        cam_motel_comingin.SetInOfficeWorld();
        cam_motel_comingin.SetPlayCycleAndConsecutively(true);
        cam_motel_comingin.SetPlayConsecutively(true);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_1", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_2", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_3", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_4", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_5", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_6", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_7", "Camera_in_office", mrot, mrotcam);
        matrixJ mroot = new matrixJ();
        mroot.Set0(-1.0, 0.0, 0.0);
        mroot.Set1(0.0, -1.0, 0.0);
        mroot.Set2(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        long camera_comimgin = eng.AddScriptTask(task, new camscripts.PlayCamera_bindMatrix(cam_motel_comingin, mroot, pos));
        long stopcamera_comimgin = eng.AddScriptTask(task, new camscripts.StopCam(cam_motel_comingin));
        eng.OnBegTASK(start_task, "play", camera_comimgin);
        eng.OnBegTASK(end_task, "play", stopcamera_comimgin);
    }

    void makeComingCameraShortAnimation(long task, long start_task, long end_task) {
        matrixJ mrot = new matrixJ();
        mrot.Set0(-1.0, 0.0, 0.0);
        mrot.Set1(0.0, 0.0, 1.0);
        mrot.Set2(0.0, 1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        SCRcamera cam_motel_comingin = SCRcamera.CreateCamera("anm_cam");
        cam_motel_comingin.SetInOfficeWorld();
        cam_motel_comingin.SetPlayCycleAndConsecutively(true);
        cam_motel_comingin.SetPlayConsecutively(true);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_4", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_5", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_6", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_7", "Camera_in_office", mrot, mrotcam);
        matrixJ mroot = new matrixJ();
        mroot.Set0(-1.0, 0.0, 0.0);
        mroot.Set1(0.0, -1.0, 0.0);
        mroot.Set2(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        long camera_comimgin = eng.AddScriptTask(task, new camscripts.PlayCamera_bindMatrix(cam_motel_comingin, mroot, pos));
        long stopcamera_comimgin = eng.AddScriptTask(task, new camscripts.StopCam(cam_motel_comingin));
        eng.OnBegTASK(start_task, "play", camera_comimgin);
        eng.OnBegTASK(end_task, "play", stopcamera_comimgin);
    }

    void makeDevelopeAnimation(long task, long start_task, double time) {
        long start_develop_timer = eng.AddEventTask(task);
        long start_develop = eng.AddScriptTask(task, new DevelopMenu());
        eng.OnEndTASK(start_task, "play", start_develop_timer);
        eng.OnMidTASK(start_develop_timer, time, time, "play", start_develop);
    }

    void makeSecretaryAnimation(long start_task, long end_task) {
        SCRuniperson testSecr = SCRuniperson.createSOMainPerson("SECRETARY", null);
        testSecr.SetInOfficeWorld();
        testSecr.play();
        long ANIMSec = eng.CreateTASK(testSecr);
        long secretary1 = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_1", 0.0);
        long secretary2 = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_2", 0.0);
        long secretary3 = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_3", 0.0, true);
        long secretary1face = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_face_1", 0.0);
        long secretary2face = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_face_2", 0.0);
        long secretary3face = eng.AddAnimTASK(ANIMSec, "Secretary_in_office_face_3", 0.0);
        long endsecretary = eng.AddScriptTask(ANIMSec, new FinishTask(ANIMSec));
        eng.OnBegTASK(start_task, "play", secretary1);
        eng.OnEndTASK(secretary1, "play", secretary2);
        eng.OnEndTASK(secretary2, "play", secretary3);
        eng.OnBegTASK(secretary3, "play", secretary3face);
        eng.OnBegTASK(secretary1, "play", secretary1face);
        eng.OnBegTASK(secretary2, "play", secretary2face);
        eng.OnEndTASK(end_task, "play", endsecretary);
    }

    void makeNewsCamera(long task, long start_task, long end_task) {
        matrixJ mrot = new matrixJ();
        mrot.Set0(-1.0, 0.0, 0.0);
        mrot.Set1(0.0, 0.0, 1.0);
        mrot.Set2(0.0, 1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        matrixJ mroot = new matrixJ();
        mroot.Set0(-1.0, 0.0, 0.0);
        mroot.Set1(0.0, -1.0, 0.0);
        mroot.Set2(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        SCRcamera cam_newspaper = SCRcamera.CreateCamera("anm_cam");
        cam_newspaper.SetPlayConsecutively(false);
        cam_newspaper.SetPlayCycleAndConsecutively(false);
        cam_newspaper.SetInOfficeWorld();
        cam_newspaper.AddAnimation("Root_Camera_in_office_8", "Camera_in_office", mrot, mrotcam);
        long camera_newspaper = eng.AddScriptTask(task, new camscripts.PlayCamera_bindMatrix(cam_newspaper, mroot, pos));
        long deletecamera_newspaper = eng.AddScriptTask(task, camscripts.deleteCamera(cam_newspaper));
        eng.OnBegTASK(start_task, "play", camera_newspaper);
        eng.OnEndTASK(end_task, "play", deletecamera_newspaper);
    }

    void makeAdverticement(long start_part1, long start_part2, long end_task) {
        SCRuniperson Chipsi = SCRuniperson.createSOMainPerson("Model_Big_bone_bacon_taste", null);
        Chipsi.SetInOfficeWorld();
        Chipsi.play();
        Chipsi.SetDirection(new vectorJ(0.0, -1.0, 0.0));
        long ANIMChipsi = eng.CreateTASK(Chipsi);
        long chipsi1_1 = eng.AddAnimTASK(ANIMChipsi, "Big_bone_bacon_taste_Compreset_pose1", 0.0);
        long chipsi1_2 = eng.AddAnimTASK(ANIMChipsi, "Big_bone_bacon_taste_NicInOffice1", 0.0);
        long chipsi2_1 = eng.AddAnimTASK(ANIMChipsi, "Big_bone_bacon_taste_Compreset_pose2", 0.0, true);
        long chipsi2_2 = eng.AddAnimTASK(ANIMChipsi, "Big_bone_bacon_taste_NicInOffice2", 0.0, true);
        long endchipsi = eng.AddScriptTask(ANIMChipsi, new FinishTask(ANIMChipsi));
        eng.OnBegTASK(start_part1, "play", chipsi1_1);
        eng.OnBegTASK(start_part1, "play", chipsi1_2);
        eng.OnBegTASK(start_part2, "play", chipsi2_1);
        eng.OnBegTASK(start_part2, "play", chipsi2_2);
        eng.OnEndTASK(end_task, "play", endchipsi);
    }

    void OfficeInside() {
        this.win_economy_contest = rnrcore.Helper.winEconomy();
        boolean use_adv = Math.random() < 0.3;
        boolean use_secretary = OfficeHelper.hasOfficeSecretary();
        aiplayer actor = Crew.getIgrok();
        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        long task = eng.CreateTASK(this.person);
        long out_cabin = eng.AddScriptTask(task, drvscripts.outCabinState(this.car));
        long person_inofficeworld = eng.AddScriptTask(task, new PlacePersonInOffice());
        long place_in_Object_inside = eng.AddScriptTask(task, new PlaceInOfficePosition());
        long start_coming_task = eng.AddEventTask(task);
        long end_coming_task = eng.AddEventTask(task);
        long LOADNEWVEHICLE = eng.AddEventTask(task);
        long NEWSPAPEREND = eng.AddEventTask(task);
        long WAITMENUCLOSE = eng.AddEventTask(task);
        long initsecondpart = eng.AddScriptTask(task, new OfficeOut());
        long end = eng.AddScriptTask(task, new FinishTaskFinal(task, true));
        long endOnBankrupt = eng.AddScriptTask(task, new FinishTaskFinal(task, false));
        long office_world = eng.AddChangeWorldTask(task, "office", "so");
        long game_world = eng.AddChangeWorldTask(task, "game", "simple");
        long bankrupt2 = eng.AddEventTask(task);
        long playBankruptScene = eng.AddScriptTask(task, new BankruptScene());
        long endAuxilaryTasks = eng.AddEventTask(task);
        if (use_secretary) {
            this.makeComingAnimation(task, 0, start_coming_task, end_coming_task, bankrupt2);
            this.makeComingCameraFullAnimation(task, start_coming_task, end_coming_task);
            this.makeDevelopeAnimation(task, office_world, 15.5);
        } else {
            this.makeComingAnimation(task, 3, start_coming_task, end_coming_task, bankrupt2);
            this.makeComingCameraShortAnimation(task, start_coming_task, end_coming_task);
            this.makeDevelopeAnimation(task, office_world, 8.5);
        }
        this.makeNewsCamera(task, end_coming_task, NEWSPAPEREND);
        eng.OnEndTASK(NEWSPAPEREND, "end", end_coming_task);
        eng.OnEndTASK(LOADNEWVEHICLE, "end", endAuxilaryTasks);
        eng.OnBegTASK(endOnBankrupt, "end", endAuxilaryTasks);
        eng.EventTask_onOfficeBankrupt(bankrupt2, false);
        eng.EventTask_onOfficeMessageClosed(NEWSPAPEREND, false);
        eng.EventTask_onNewCarCreated(LOADNEWVEHICLE, false);
        long OFFICEMenu = eng.AddScriptTask(task, new CreateMenu());
        long SHOWMenu = eng.AddScriptTask(task, new ShowMenu());
        if (use_secretary) {
            this.makeSecretaryAnimation(start_coming_task, endAuxilaryTasks);
        }
        if (use_adv) {
            this.makeAdverticement(start_coming_task, start_coming_task, endAuxilaryTasks);
        }
        eng.OnEndTASK(NEWSPAPEREND, "play", WAITMENUCLOSE);
        eng.OnEndTASK(LOADNEWVEHICLE, "play", game_world);
        eng.OnEndTASK(game_world, "play", initsecondpart);
        eng.OnEndTASK(game_world, "play", end);
        eng.OnEndTASK(bankrupt2, "play", playBankruptScene);
        eng.OnEndTASK(bankrupt2, "play", endOnBankrupt);
        SOscene SC = new SOscene();
        SC.task = task;
        SC.person = this.person;
        SC.actor = actor;
        SC.vehicle = this.car;
        long PARking = SC.waitParkingEvent();
        CarInOutTasks CAR_out = this._play_short ? new CarInOutTasks(0L, PARking) : SC.makecaroutOnEnd(PARking, true);
        eng.playTASK(PARking);
        eng.playTASK(bankrupt2);
        eng.disableControl();
        if (this._play_short) {
            eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", out_cabin);
        }
        eng.OnEndTASK(CAR_out.getMTaskToWait(), "play", office_world);
        eng.OnEndTASK(office_world, "play", OFFICEMenu);
        eng.OnEndTASK(office_world, "play", place_in_Object_inside);
        eng.OnEndTASK(office_world, "play", person_inofficeworld);
        eng.OnEndTASK(office_world, "play", start_coming_task);
        eng.OnBegTASK(end_coming_task, "play", NEWSPAPEREND);
        eng.OnBegTASK(end_coming_task, "play", SHOWMenu);
    }

    void OfficeInside_short() {
        this._play_short = true;
        this.OfficeInside();
    }

    void deletetask(long task) {
        eng.DeleteTASK(task);
    }

    public static void teleport() {
        Office office = new Office();
        aiplayer actor = Crew.getIgrok();
        office.car = Crew.getIgrokCar();
        office.person = actor.getModel();
        SCRuniperson person = actor.getModel();
        long task = eng.CreateTASK(person);
        long office_world = eng.AddChangeWorldTask(task, "office", "so");
        long game_world = eng.AddChangeWorldTask(task, "game", "simple");
        long person_inofficeworld = eng.AddScriptTask(task, office.new PlacePersonInOffice());
        long place_in_Object_inside = eng.AddScriptTask(task, office.new PlaceInOfficePosition());
        long timer = eng.AddEventTask(task);
        long OFFICEMenu = eng.AddScriptTask(task, new CreateMenu());
        long SHOWMenu = eng.AddScriptTask(task, new ShowMenu());
        long NEWSPAPEREND = eng.AddEventTask(task);
        long LOADNEWVEHICLE = eng.AddEventTask(task);
        long WAITMENUCLOSE = eng.AddEventTask(task);
        long bankrupt2 = eng.AddEventTask(task);
        long playBankruptScene = eng.AddScriptTask(task, new BankruptScene());
        long end = eng.AddScriptTask(task, new FinishTaskFinal(task, true));
        long endOnBankrupt = eng.AddScriptTask(task, new FinishTaskFinal(task, false));
        long initsecondpart = eng.AddScriptTask(task, office.new OfficeOut());
        long endOfficeLoad = eng.AddEventTask(task);
        eng.EventTask_onOfficeBankrupt(bankrupt2, false);
        eng.EventTask_onOfficeMessageClosed(NEWSPAPEREND, false);
        eng.EventTask_onNewCarCreated(LOADNEWVEHICLE, false);
        office.makeNewsCamera(task, endOfficeLoad, NEWSPAPEREND);
        eng.OnEndTASK(office_world, "play", endOfficeLoad);
        eng.OnEndTASK(office_world, "play", OFFICEMenu);
        eng.OnEndTASK(office_world, "play", place_in_Object_inside);
        eng.OnEndTASK(office_world, "play", person_inofficeworld);
        eng.OnEndTASK(office_world, "play", timer);
        eng.OnMidTASK(timer, 2.0, 2.0, "play", SHOWMenu);
        eng.OnEndTASK(NEWSPAPEREND, "play", WAITMENUCLOSE);
        eng.OnEndTASK(LOADNEWVEHICLE, "play", game_world);
        eng.OnEndTASK(game_world, "play", initsecondpart);
        eng.OnEndTASK(game_world, "play", end);
        eng.OnEndTASK(bankrupt2, "play", playBankruptScene);
        eng.OnEndTASK(bankrupt2, "play", endOnBankrupt);
        eng.playTASK(office_world);
        eng.playTASK(bankrupt2);
    }

    public void endteleport() {
        this.cam_menu_teleportaion.DeleteCamera();
        actorveh vehicle2 = Crew.getIgrokCar();
        if (null != vehicle2) {
            vehicle2.UpdateCar();
        }
        if (vehicle2 == null || vehicle2.getCar() == 0) {
            event.eventObject(6002, this, "endteleport");
            return;
        }
        drvscripts.playInsideCarFast(Crew.getIgrok(), vehicle2);
        Helper.restoreCameraToIgrokCar();
    }

    public void endteleportNotFromMenu() {
        actorveh vehicle2 = Crew.getIgrokCar();
        if (null != vehicle2) {
            vehicle2.UpdateCar();
        }
        if (vehicle2 == null || vehicle2.getCar() == 0) {
            event.eventObject(6002, this, "endteleportNotFromMenu");
            return;
        }
        drvscripts.playInsideCarFast(Crew.getIgrok(), vehicle2);
        Helper.restoreCameraToIgrokCar();
    }

    public static void teleportPlayer() {
        actorveh car = Crew.getIgrokCar();
        car.teleport(eng.setCurrentOfficeNear(car.gPosition()));
        Office.teleport();
    }

    public void debug_scene() {
        aiplayer actor = Crew.getIgrok();
        this.car = Crew.getIgrokCar();
        this.person = actor.getModel();
        long task = eng.CreateTASK(this.person);
        long out_cabin = eng.AddScriptTask(task, drvscripts.outCabinState(this.car));
        long person_inofficeworld = eng.AddScriptTask(task, new PlacePersonInOffice());
        long place_in_Object_inside = eng.AddScriptTask(task, new PlaceInOfficePosition());
        long cominginside1 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[0], 0.0);
        long anim1 = eng.AddScriptTask(task, new Createanimatedoffice(0));
        long delanim1 = eng.AddScriptTask(task, new DeleteAnimatedoffice(0));
        long cominginside2 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[1], 0.0);
        long anim2 = eng.AddScriptTask(task, new Createanimatedoffice(1));
        long delanim2 = eng.AddScriptTask(task, new DeleteAnimatedoffice(1));
        long cominginside3 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[2], 0.0);
        long anim3 = eng.AddScriptTask(task, new Createanimatedoffice(2));
        long delanim3 = eng.AddScriptTask(task, new DeleteAnimatedoffice(2));
        long cominginside4 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[3], 0.0);
        long anim4 = eng.AddScriptTask(task, new Createanimatedoffice(3));
        long delanim4 = eng.AddScriptTask(task, new DeleteAnimatedoffice(3));
        long cominginside5 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[4], 0.0);
        long anim5 = eng.AddScriptTask(task, new Createanimatedoffice(4));
        long delanim5 = eng.AddScriptTask(task, new DeleteAnimatedoffice(4));
        long cominginside6 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[5], 0.0);
        long anim6 = eng.AddScriptTask(task, new Createanimatedoffice(5));
        long delanim6 = eng.AddScriptTask(task, new DeleteAnimatedoffice(5));
        long cominginside7 = eng.AddAnimTASK(task, INSIDE_IVAN_ANIMATIONS[6], 0.0);
        long anim7 = eng.AddScriptTask(task, new Createanimatedoffice(6));
        long delanim7 = eng.AddScriptTask(task, new DeleteAnimatedoffice(6));
        matrixJ mrot = new matrixJ();
        mrot.Set0(-1.0, 0.0, 0.0);
        mrot.Set1(0.0, 0.0, 1.0);
        mrot.Set2(0.0, 1.0, 0.0);
        matrixJ mrotcam = new matrixJ();
        SCRcamera cam_motel_comingin = SCRcamera.CreateCamera("anm_cam");
        cam_motel_comingin.SetInOfficeWorld();
        cam_motel_comingin.SetPlayCycleAndConsecutively(true);
        cam_motel_comingin.SetPlayConsecutively(true);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_1", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_2", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_3", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_4", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_5", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_6", "Camera_in_office", mrot, mrotcam);
        cam_motel_comingin.AddAnimation("Root_Camera_in_office_7", "Camera_in_office", mrot, mrotcam);
        matrixJ mroot = new matrixJ();
        mroot.Set0(-1.0, 0.0, 0.0);
        mroot.Set1(0.0, -1.0, 0.0);
        mroot.Set2(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        long camera_comimgin = eng.AddScriptTask(task, new camscripts.PlayCamera_bindMatrix(cam_motel_comingin, mroot, pos));
        long stopcamera_comimgin = eng.AddScriptTask(task, new camscripts.StopCam(cam_motel_comingin));
        long end = eng.AddScriptTask(task, new FinishTask(task));
        eng.AddEventTask(task);
        eng.AddScriptTask(task, new DevelopMenu());
        eng.playTASK(out_cabin);
        eng.OnBegTASK(out_cabin, "play", person_inofficeworld);
        eng.OnBegTASK(out_cabin, "play", place_in_Object_inside);
        eng.OnBegTASK(out_cabin, "play", cominginside1);
        eng.OnBegTASK(cominginside1, "play", camera_comimgin);
        eng.OnBegTASK(cominginside1, "play", anim1);
        eng.OnEndTASK(cominginside1, "play", delanim1);
        eng.OnEndTASK(cominginside1, "play", cominginside2);
        eng.OnBegTASK(cominginside2, "play", anim2);
        eng.OnEndTASK(cominginside2, "play", delanim2);
        eng.OnEndTASK(cominginside2, "play", cominginside3);
        eng.OnBegTASK(cominginside3, "play", anim3);
        eng.OnEndTASK(cominginside3, "play", delanim3);
        eng.OnEndTASK(cominginside3, "play", cominginside4);
        eng.OnBegTASK(cominginside4, "play", anim4);
        eng.OnEndTASK(cominginside4, "play", delanim4);
        eng.OnEndTASK(cominginside4, "play", cominginside5);
        eng.OnBegTASK(cominginside5, "play", anim5);
        eng.OnEndTASK(cominginside5, "play", delanim5);
        eng.OnEndTASK(cominginside5, "play", cominginside6);
        eng.OnBegTASK(cominginside6, "play", anim6);
        eng.OnEndTASK(cominginside6, "play", delanim6);
        eng.OnEndTASK(cominginside6, "play", cominginside7);
        eng.OnBegTASK(cominginside7, "play", anim7);
        eng.OnEndTASK(cominginside7, "play", delanim7);
        eng.OnEndTASK(cominginside7, "play", stopcamera_comimgin);
        eng.OnEndTASK(cominginside7, "play", end);
    }

    class ShowOfficeMenu
    implements anm {
        private ScriptRef uid = new ScriptRef();

        ShowOfficeMenu() {
        }

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

        public boolean animaterun(double dt) {
            if (dt > 1.0) {
                menues.showMenu(8000);
                return true;
            }
            return false;
        }

        public IXMLSerializable getXmlSerializator() {
            return null;
        }
    }

    class OfficeOut
    implements IScriptTask {
        OfficeOut() {
        }

        public void launch() {
            if (!Office.this.win_economy_contest && rnrcore.Helper.winEconomy()) {
                ScenarioSync.setPlayScene("victory_economy");
                return;
            }
            Office.this.car = Crew.getIgrokCar();
            Helper.placePersonToCar(Office.this.person, Office.this.car);
            if (Office.this.car == null) {
                eng.err("Breaked animation OfficeOut.");
                return;
            }
            aiplayer actor = Crew.getIgrok();
            Office.this.person = actor.getModel();
            eng.doWide(true);
            Office.this.task = eng.CreateTASK(Office.this.person);
            long secondpart = eng.AddEventTask(Office.this.task);
            long secondpartbegin = eng.AddEventTask(Office.this.task);
            long person_ingame = eng.AddScriptTask(Office.this.task, new PlacePersonInGame());
            long realesecar = eng.AddScriptTask(Office.this.task, new ReleaseFromParking());
            long end = eng.AddScriptTask(Office.this.task, new EndOfficeOut());
            long out_cabin = eng.AddScriptTask(Office.this.task, drvscripts.outCabinState(Office.this.car));
            SOscene SC = new SOscene();
            SC.task = Office.this.task;
            SC.person = Office.this.person;
            SC.actor = actor;
            SC.vehicle = Office.this.car;
            CarInOutTasks CAR_in = SC.makecarinOnEnd(secondpartbegin);
            eng.OnEndTASK(secondpartbegin, "play", person_ingame);
            SC.restorecameratoCarOnEnd(CAR_in.getMTaskToWait());
            eng.OnBegTASK(secondpart, "play", out_cabin);
            eng.OnBegTASK(secondpart, "end", secondpartbegin);
            eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", realesecar);
            eng.OnEndTASK(CAR_in.getMTaskToWait(), "play", end);
            eng.playTASK(secondpart);
        }
    }

    class EndOfficeOut
    implements IScriptTask {
        EndOfficeOut() {
        }

        public void launch() {
            Office.this.deletetask(Office.this.task);
            eng.doWide(false);
        }
    }

    static class BankruptScene
    implements IScriptTask {
        BankruptScene() {
        }

        public void launch() {
            EventsControllerHelper.messageEventHappened("bankrupt");
        }
    }

    static class DevelopMenu
    implements IScriptTask {
        DevelopMenu() {
        }

        public void launch() {
            rnrcore.Helper.peekNativeMessage("Develop office panel");
        }
    }

    static class ShowMenu
    implements IScriptTask {
        ShowMenu() {
        }

        public void launch() {
            OfficeMenu.showMenu();
        }
    }

    static class CreateMenu
    implements IScriptTask {
        CreateMenu() {
        }

        public void launch() {
            menues.CreateOfficeMENU();
        }
    }

    static class FinishTaskFinal
    implements IScriptTask {
        private long task;
        private boolean toEnableControl;

        FinishTaskFinal(long task, boolean toEnableControl) {
            this.task = task;
            this.toEnableControl = toEnableControl;
        }

        public void launch() {
            eng.DeleteTASK(this.task);
            if (this.toEnableControl) {
                eng.enableControl();
            }
        }
    }

    static class FinishTask
    implements IScriptTask {
        private long task;

        FinishTask(long task) {
            this.task = task;
        }

        public void launch() {
            eng.DeleteTASK(this.task);
        }
    }

    class ReleaseFromParking
    implements IScriptTask {
        ReleaseFromParking() {
        }

        public void launch() {
            Office.this.car.leaveParking();
        }
    }

    class PlaceCameraInOffice
    implements IScriptTask {
        private SCRcamera cam;

        PlaceCameraInOffice(SCRcamera cam) {
            this.cam = cam;
        }

        public void launch() {
            this.cam.SetInOfficeWorld();
        }
    }

    class PlacePersonInGame
    implements IScriptTask {
        PlacePersonInGame() {
        }

        public void launch() {
            Office.this.person.SetInGameWorld();
            Office.this.person.play();
        }
    }

    class PlacePersonInOffice
    implements IScriptTask {
        PlacePersonInOffice() {
        }

        public void launch() {
            Office.this.person.SetInOfficeWorld();
        }
    }

    class PlaceInOfficePosition
    implements IScriptTask {
        PlaceInOfficePosition() {
        }

        public void launch() {
            Office.this.person.SetPosition(new vectorJ(0.0, 0.0, 0.0));
            Office.this.person.SetDirection(new vectorJ(0.0, 1.0, 0.0));
            Office.this.person.play();
        }
    }

    class DeleteAnimatedoffice
    implements IScriptTask {
        private int scene = 0;

        DeleteAnimatedoffice(int scene) {
            this.scene = scene;
        }

        public void launch() {
            for (String aSPACE_ : SPACE_) {
                Office.this.person.DeleteAnimatedSpace(aSPACE_, INSIDE_IVAN_ANIMATIONS[this.scene], 0L);
            }
        }
    }

    class Createanimatedoffice
    implements IScriptTask {
        private int scene = 0;

        Createanimatedoffice(int scene) {
            this.scene = scene;
        }

        public void launch() {
            matrixJ M = new matrixJ();
            for (int i = 0; i < SPACE_.length; ++i) {
                Office.this.person.CreateAnimatedSpace_timedependance(SPACE_[i], INSIDE_IVAN_ANIMATIONS[this.scene], ANM_[i], INSIDE_IVAN_ANIMATIONS[this.scene], 0.0, 0L, 0L, M, rotate[i], move[i]);
            }
        }
    }
}

