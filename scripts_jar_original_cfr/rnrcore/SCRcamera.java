/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.matrixJ;
import rnrcore.vectorJ;

public class SCRcamera {
    public long nativePointer = 0L;

    public native void nCamera(String var1);

    public native void ScaleAnimation(double var1);

    public native void SetPlayConsecutively(boolean var1);

    public native void SetPlayCycleAndConsecutively(boolean var1);

    public native void PlayCamera();

    public native void StopCamera();

    public native void DeleteCamera();

    public native void AddAnimation(String var1, String var2, matrixJ var3, matrixJ var4);

    public native void BindToVehicle(long var1);

    public native void BindToWarehouse(long var1, int var3);

    public native void BindToGasStation(long var1);

    public native void BindToBar(long var1);

    public native void BindToRepair(long var1, int var3);

    public native void BindToVehicleSteerWheel(long var1);

    public native void BindToVehicleWheel(long var1, int var3);

    public native void BindToMatrix(matrixJ var1, vectorJ var2);

    public native void SetInBarWorld();

    public native void SetInBar2World();

    public native void SetInBar3World();

    public native void SetInOfficeWorld();

    public native void SetInHotelWorld();

    public native void SetInGameWorld();

    public native void SetInPoliceWorld();

    public native void IncludeCameraInCabinRender();

    public static native void jumpInCabin(boolean var0);

    public native void SetCameraPosition(vectorJ var1);

    public static SCRcamera CreateCamera(String typ) {
        SCRcamera cam = new SCRcamera();
        cam.nCamera(typ);
        return cam;
    }

    public static SCRcamera CreateCamera_fake(long str) {
        SCRcamera cam = new SCRcamera();
        cam.nativePointer = str;
        return cam;
    }
}

