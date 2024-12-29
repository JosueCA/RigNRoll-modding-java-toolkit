package rnrcore;

public class cameratrack {
  public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2, String paramString3);
  
  public static native SCRcamera[] GetTrackCameras(long paramLong);
  
  public static native SCRcamera[] GetTrackActorCameras(long paramLong, String paramString);
  
  public static native SCRcamera GetTrackActorClipCameras(long paramLong, String paramString1, String paramString2);
  
  public static native void InitCameraTrack(long paramLong, String paramString1, String paramString2);
  
  public static native void InitCameraTrackActor(long paramLong, String paramString1, String paramString2, String paramString3);
  
  public static native void InitCameraTrackActorClip(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public static native void BindToMatrixCameraTrack(long paramLong, matrixJ parammatrixJ, vectorJ paramvectorJ);
  
  public static native void BindToVehicleSteerWheelCameraTrack(long paramLong1, long paramLong2);
  
  public static native long CreateCameraTrack(String paramString);
  
  public static native void SetGlobalTime(long paramLong, double paramDouble);
  
  public static native void SetGlobalTime(long paramLong, String paramString, double paramDouble);
  
  public static native void SetMaximumTime(long paramLong, double paramDouble);
  
  public static native void SetMaximumTime(long paramLong, String paramString, double paramDouble);
  
  public static native void AddTime(long paramLong, double paramDouble);
  
  public static native void AddTime(long paramLong, String paramString, double paramDouble);
  
  public static native void RunTrack(long paramLong);
  
  public static native void StopTrack(long paramLong);
  
  public static native void DeleteTrack(long paramLong);
  
  public static native void AttachCameraToCar(int paramInt);
}
