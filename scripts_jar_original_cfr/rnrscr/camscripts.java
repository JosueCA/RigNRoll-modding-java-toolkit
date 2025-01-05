/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.lang.reflect.Array;
import players.actorveh;
import rnrconfig.cabincam;
import rnrcore.IScriptTask;
import rnrcore.SCRcamera;
import rnrcore.cameratrack;
import rnrcore.eng;
import rnrcore.matrixJ;
import rnrcore.vectorJ;
import rnrscr.Helper;
import rnrscr.Param_CamScripts_SingleLong;

public class camscripts {
    public static camscripts helper = new camscripts();

    public static IScriptTask playCamera_bindMatrix(SCRcamera camera, matrixJ M, vectorJ P) {
        return new PlayCamera_bindMatrix(camera, M, P);
    }

    public static IScriptTask restore_Camera_to_igrok_car() {
        return new Restore_Camera_to_igrok_car();
    }

    public static IScriptTask buffercamturn(boolean value) {
        return new Buffercamturn(value);
    }

    public static IScriptTask playCamera_bindSteerWheel(SCRcamera cam, actorveh car) {
        return new PlayCamera_bindSteerWheel(cam, car);
    }

    public static IScriptTask deleteCamera(SCRcamera cam) {
        return new DeleteCam(cam);
    }

    public static IScriptTask stopCamera(SCRcamera cam) {
        return new StopCam(cam);
    }

    public static IScriptTask playCamera(SCRcamera cam) {
        return new PlayCam(cam);
    }

    public static IScriptTask runTrack(long track) {
        return new RunTrack(track);
    }

    public static IScriptTask deleteTrack(long track) {
        return new DeleteTrack(track);
    }

    public void RunTrack(Param_CamScripts_SingleLong param) {
        cameratrack.RunTrack(param.param);
    }

    public void StopTrack(Param_CamScripts_SingleLong param) {
        cameratrack.StopTrack(param.param);
    }

    public void DeleteTrack(Param_CamScripts_SingleLong param) {
        cameratrack.DeleteTrack(param.param);
    }

    public static void buffercamturnoff() {
        rnrcore.Helper.peekNativeMessage("turn off buffer camera");
    }

    public static void buffercamturnon() {
        rnrcore.Helper.peekNativeMessage("turn on buffer camera");
    }

    public void PlayCam(Param_CamScripts_SingleLong param) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(param.param);
        cam.PlayCamera();
    }

    public void DeleteCam(Param_CamScripts_SingleLong param) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(param.param);
        cam.DeleteCamera();
    }

    public void PlayCamera_bindVehicle(long camp, long car) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToVehicle(car);
        cam.PlayCamera();
    }

    public void PlayCamera_bindSTO(long camp, long nomgate) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToRepair(eng.GetCurrentRepairShop(), (int)nomgate);
        cam.PlayCamera();
    }

    public void PlayCamera_bindWarehouse(long camp, long nomgate) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToWarehouse(eng.GetCurrentWarehouse(), (int)nomgate);
        cam.PlayCamera();
    }

    public void PlayCamera_bindMatrix(long camp, matrixJ matr, vectorJ posr) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToMatrix(matr, posr);
        cam.PlayCamera();
    }

    public void PlayCamera_bindSteerWheel(long camp, long veh) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToVehicleSteerWheel(veh);
        cam.PlayCamera();
    }

    public void PlayCamera_bindWheel(long camp, long veh, int nom_wh) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToVehicleWheel(veh, nom_wh);
        cam.PlayCamera();
    }

    public void PlayCamera_bindWheel0(long camp, long veh) {
        this.PlayCamera_bindWheel(camp, veh, 0);
    }

    public void PlayCamera_bindWheel1(long camp, long veh) {
        this.PlayCamera_bindWheel(camp, veh, 1);
    }

    public void PlayCamera_bindWheel2(long camp, long veh) {
        this.PlayCamera_bindWheel(camp, veh, 2);
    }

    public void PlayCamera_bindWheel3(long camp, long veh) {
        this.PlayCamera_bindWheel(camp, veh, 3);
    }

    public SCRcamera CreateCamera_FrontOfVehicle() {
        SCRcamera cam_veh1 = SCRcamera.CreateCamera("anm");
        cam_veh1.nCamera("anm");
        String CAM_VEHname2 = "camera_RUL11";
        matrixJ mrotVEH = new matrixJ();
        mrotVEH.v0.Set(0.0, 1.0, 0.0);
        mrotVEH.v1.Set(0.0, 0.0, 1.0);
        mrotVEH.v2.Set(1.0, 0.0, 0.0);
        matrixJ mrotcamVEH = new matrixJ();
        mrotcamVEH.v0.Set(0.0, 0.0, -1.0);
        mrotcamVEH.v1.Set(0.0, 1.0, 0.0);
        mrotcamVEH.v2.Set(1.0, 0.0, 0.0);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
        return cam_veh1;
    }

    public void AddCamera_FrontOfVehicle(SCRcamera cam_veh1) {
        String CAM_VEHname2 = "camera_RUL11";
        matrixJ mrotVEH = new matrixJ();
        mrotVEH.v0.Set(0.0, 1.0, 0.0);
        mrotVEH.v1.Set(0.0, 0.0, 1.0);
        mrotVEH.v2.Set(1.0, 0.0, 0.0);
        matrixJ mrotcamVEH = new matrixJ();
        mrotcamVEH.v0.Set(0.0, 0.0, -1.0);
        mrotcamVEH.v1.Set(0.0, 1.0, 0.0);
        mrotcamVEH.v2.Set(1.0, 0.0, 0.0);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
    }

    public void AddCamera_BackOfVehicle(SCRcamera cam_veh1) {
        String CAM_VEHname2 = "camera_RUL10";
        matrixJ mrotVEH = new matrixJ();
        mrotVEH.v0.Set(0.0, 1.0, 0.0);
        mrotVEH.v1.Set(0.0, 0.0, 1.0);
        mrotVEH.v2.Set(1.0, 0.0, 0.0);
        matrixJ mrotcamVEH = new matrixJ();
        mrotcamVEH.v0.Set(0.0, 0.0, -1.0);
        mrotcamVEH.v1.Set(0.0, 1.0, 0.0);
        mrotcamVEH.v2.Set(1.0, 0.0, 0.0);
        cam_veh1.AddAnimation(CAM_VEHname2, "space_MDL-" + CAM_VEHname2, mrotVEH, mrotcamVEH);
    }

    public SCRcamera CreateCameraBackSit(String carprefix) {
        SCRcamera cam_back = SCRcamera.CreateCamera("car");
        cam_back.nCamera("car");
        cabincam.Set_0_cam_Angles(cam_back.nativePointer, 0.0, 0.0, 0.0);
        int carnom = -1;
        if (0 == carprefix.compareTo("")) {
            carnom = 1;
        }
        if (0 == carprefix.compareTo("FREIGHTLINER_CLASSIC_XL_car_4")) {
            carnom = 2;
        }
        if (0 == carprefix.compareTo("Freight_Cor_")) {
            carnom = 3;
        }
        switch (carnom) {
            case 1: {
                cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0, -2.0, 1.55, 0.0, 0.0, 0.0, 0.0);
                break;
            }
            case 2: {
                cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0, -1.5, 2.0, 0.0, 0.0, 0.0, 0.0);
                break;
            }
            case 3: {
                cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0, -0.1499999999999999, 2.589, -100.0, 100.0, -89.0, 89.0);
                break;
            }
            default: {
                eng.writeLog("FromScript. Cannot find description of car " + carprefix + " in CreateCameraBackSit.");
                cabincam.Add_camera_cabin_point(cam_back.nativePointer, 0.0, -1.5, 1.55, 0.0, 0.0, 0.0, 0.0);
            }
        }
        return cam_back;
    }

    public class trackclipparams {
        String anmName = "";
        String name = "";
        public boolean Active;
        double In;
        double Out;
        double StartOffset;
        double Scale;
        double antiScale;
        double BefType;
        double BefHold;
        double BefCycl;
        double BefBoun;
        double AftType;
        double AftHold;
        double AftCycl;
        double AftBoun;
        public double Weight;
        String track_name = "";
        boolean track_mono;
        public boolean track_mute;
    }

    static class DeleteTrack
    implements IScriptTask {
        private long track;

        DeleteTrack(long track) {
            this.track = track;
        }

        public void launch() {
            SCRcamera[] allcameras = cameratrack.GetTrackCameras(this.track);
            for (int i = 0; i < Array.getLength(allcameras); ++i) {
                allcameras[i].StopCamera();
            }
            cameratrack.DeleteTrack(this.track);
        }
    }

    static class RunTrack
    implements IScriptTask {
        private long track;

        RunTrack(long track) {
            this.track = track;
        }

        public void launch() {
            cameratrack.RunTrack(this.track);
        }
    }

    static class PlayCam
    implements IScriptTask {
        private SCRcamera cam;

        PlayCam(SCRcamera cam) {
            this.cam = cam;
        }

        public void launch() {
            this.cam.PlayCamera();
        }
    }

    static class StopCam
    implements IScriptTask {
        private SCRcamera cam;

        StopCam(SCRcamera cam) {
            this.cam = cam;
        }

        public void launch() {
            this.cam.StopCamera();
        }
    }

    static class PlayCamera_bindMatrix
    implements IScriptTask {
        private SCRcamera cam;
        private matrixJ M;
        private vectorJ P;

        PlayCamera_bindMatrix(SCRcamera cam, matrixJ M, vectorJ P) {
            this.cam = cam;
            this.M = M;
            this.P = P;
        }

        public void launch() {
            this.cam.BindToMatrix(this.M, this.P);
            this.cam.PlayCamera();
        }
    }

    static class DeleteCam
    implements IScriptTask {
        private SCRcamera cam;

        DeleteCam(SCRcamera cam) {
            this.cam = cam;
        }

        public void launch() {
            this.cam.DeleteCamera();
        }
    }

    static class PlayCamera_bindSteerWheel
    implements IScriptTask {
        private SCRcamera cam;
        private actorveh car;

        PlayCamera_bindSteerWheel(SCRcamera cam, actorveh car) {
            this.cam = cam;
            this.car = car;
        }

        public void launch() {
            this.cam.BindToVehicleSteerWheel(this.car.getCar());
            this.cam.PlayCamera();
        }
    }

    static class Buffercamturn
    implements IScriptTask {
        private boolean value;

        Buffercamturn(boolean value) {
            this.value = value;
        }

        public void launch() {
            if (this.value) {
                camscripts.buffercamturnon();
            } else {
                camscripts.buffercamturnoff();
            }
        }
    }

    static class Restore_Camera_to_igrok_car
    implements IScriptTask {
        Restore_Camera_to_igrok_car() {
        }

        public void launch() {
            Helper.restoreCameraToIgrokCar();
        }
    }
}

