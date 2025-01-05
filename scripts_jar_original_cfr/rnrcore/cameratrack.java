/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRcamera;
import rnrcore.matrixJ;
import rnrcore.vectorJ;

public class cameratrack {
    public static native void ChangeClipParam(long var0, String var2, String var3, String var4, String var5);

    public static native void ChangeClipParam(long var0, String var2, String var3, String var4);

    public static native SCRcamera[] GetTrackCameras(long var0);

    public static native SCRcamera[] GetTrackActorCameras(long var0, String var2);

    public static native SCRcamera GetTrackActorClipCameras(long var0, String var2, String var3);

    public static native void InitCameraTrack(long var0, String var2, String var3);

    public static native void InitCameraTrackActor(long var0, String var2, String var3, String var4);

    public static native void InitCameraTrackActorClip(long var0, String var2, String var3, String var4, String var5);

    public static native void BindToMatrixCameraTrack(long var0, matrixJ var2, vectorJ var3);

    public static native void BindToVehicleSteerWheelCameraTrack(long var0, long var2);

    public static native long CreateCameraTrack(String var0);

    public static native void SetGlobalTime(long var0, double var2);

    public static native void SetGlobalTime(long var0, String var2, double var3);

    public static native void SetMaximumTime(long var0, double var2);

    public static native void SetMaximumTime(long var0, String var2, double var3);

    public static native void AddTime(long var0, double var2);

    public static native void AddTime(long var0, String var2, double var3);

    public static native void RunTrack(long var0);

    public static native void StopTrack(long var0);

    public static native void DeleteTrack(long var0);

    public static native void AttachCameraToCar(int var0);
}

