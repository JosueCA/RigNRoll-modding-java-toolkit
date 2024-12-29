package rnrcore;

import java.util.Vector;

public class scenetrack {
  public static final int TRACK_RUN = 1;
  
  public static final int TRACK_SUSPENDONEND = 2;
  
  public static final int TRACK_CYCLE = 4;
  
  public static final int TRACK_ESCRECIEVE = 8;
  
  public static final int TRACK_SELFDELETE = 16;
  
  public static final int TRACK_STOPPED = 256;
  
  public static final int TRACK_AMBIENT = 512;
  
  public static native SCRuniperson[] GetSceneAll(long paramLong);
  
  public static native SCRuniperson GetSceneActor(long paramLong, String paramString);
  
  public static native void InitScene(long paramLong);
  
  public static native long CreateScene();
  
  public static native void CreateTrackInSecene(long paramLong, String paramString);
  
  public static native void SetCamerasInSecene(long paramLong1, long paramLong2);
  
  public static native void RunScene(long paramLong);
  
  public static native void StopScene(long paramLong);
  
  public static native void DeleteScene(long paramLong);
  
  public static native long CreateSceneXML(String paramString, int paramInt, Object paramObject, Vector paramVector);
  
  public static native long CreateSceneXMLPool(String paramString, int paramInt, Vector paramVector1, Object paramObject, Vector paramVector2);
  
  public static native void UpdateSceneFlags(long paramLong, int paramInt);
  
  public static native void ReplaceSceneFlags(long paramLong, int paramInt);
  
  public static native void moveSceneTime(long paramLong, double paramDouble);
  
  public static native void setSceneTime(long paramLong, double paramDouble);
  
  public static native void setSceneWeight(long paramLong, float paramFloat);
  
  public static native void runFromStart(long paramLong);
  
  public static native long CreateItemTrackInScene(long paramLong, String paramString);
  
  public static native void InitItems(long paramLong);
  
  public static native void Set_waittrackcreation(boolean paramBoolean);
  
  public static native void ChangeClipParam(long paramLong, String paramString1, String paramString2, Object paramObject, String paramString3);
  
  public static long CreateSceneXML(String Scenename, int flags, Object preset) {
    return CreateSceneXML(Scenename, flags, preset, null);
  }
  
  public static long CreateSceneXMLPool(String Scenename, int flags, Vector pool, Object preset) {
    return CreateSceneXMLPool(Scenename, flags, pool, preset, null);
  }
}
