package rnrcore;

public class SCRcamera {
  public long nativePointer;
  
  public native void nCamera(String paramString);
  
  public native void ScaleAnimation(double paramDouble);
  
  public native void SetPlayConsecutively(boolean paramBoolean);
  
  public native void SetPlayCycleAndConsecutively(boolean paramBoolean);
  
  public native void PlayCamera();
  
  public native void StopCamera();
  
  public native void DeleteCamera();
  
  public native void AddAnimation(String paramString1, String paramString2, matrixJ parammatrixJ1, matrixJ parammatrixJ2);
  
  public native void BindToVehicle(long paramLong);
  
  public native void BindToWarehouse(long paramLong, int paramInt);
  
  public native void BindToGasStation(long paramLong);
  
  public native void BindToBar(long paramLong);
  
  public native void BindToRepair(long paramLong, int paramInt);
  
  public native void BindToVehicleSteerWheel(long paramLong);
  
  public native void BindToVehicleWheel(long paramLong, int paramInt);
  
  public native void BindToMatrix(matrixJ parammatrixJ, vectorJ paramvectorJ);
  
  public native void SetInBarWorld();
  
  public native void SetInBar2World();
  
  public native void SetInBar3World();
  
  public native void SetInOfficeWorld();
  
  public native void SetInHotelWorld();
  
  public native void SetInGameWorld();
  
  public native void SetInPoliceWorld();
  
  public native void IncludeCameraInCabinRender();
  
  public static native void jumpInCabin(boolean paramBoolean);
  
  public native void SetCameraPosition(vectorJ paramvectorJ);
  
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
  
  public SCRcamera() {
    this.nativePointer = 0L;
  }
}
