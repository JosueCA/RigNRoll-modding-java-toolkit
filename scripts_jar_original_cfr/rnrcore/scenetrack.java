/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.Vector;
import rnrcore.SCRuniperson;

public class scenetrack {
    public static final int TRACK_RUN = 1;
    public static final int TRACK_SUSPENDONEND = 2;
    public static final int TRACK_CYCLE = 4;
    public static final int TRACK_ESCRECIEVE = 8;
    public static final int TRACK_SELFDELETE = 16;
    public static final int TRACK_STOPPED = 256;
    public static final int TRACK_AMBIENT = 512;

    public static native SCRuniperson[] GetSceneAll(long var0);

    public static native SCRuniperson GetSceneActor(long var0, String var2);

    public static native void InitScene(long var0);

    public static native long CreateScene();

    public static native void CreateTrackInSecene(long var0, String var2);

    public static native void SetCamerasInSecene(long var0, long var2);

    public static native void RunScene(long var0);

    public static native void StopScene(long var0);

    public static native void DeleteScene(long var0);

    public static native long CreateSceneXML(String var0, int var1, Object var2, Vector var3);

    public static native long CreateSceneXMLPool(String var0, int var1, Vector var2, Object var3, Vector var4);

    public static native void UpdateSceneFlags(long var0, int var2);

    public static native void ReplaceSceneFlags(long var0, int var2);

    public static native void moveSceneTime(long var0, double var2);

    public static native void setSceneTime(long var0, double var2);

    public static native void setSceneWeight(long var0, float var2);

    public static native void runFromStart(long var0);

    public static native long CreateItemTrackInScene(long var0, String var2);

    public static native void InitItems(long var0);

    public static native void Set_waittrackcreation(boolean var0);

    public static native void ChangeClipParam(long var0, String var2, String var3, Object var4, String var5);

    public static long CreateSceneXML(String Scenename, int flags, Object preset2) {
        return scenetrack.CreateSceneXML(Scenename, flags, preset2, null);
    }

    public static long CreateSceneXMLPool(String Scenename, int flags, Vector pool, Object preset2) {
        return scenetrack.CreateSceneXMLPool(Scenename, flags, pool, preset2, null);
    }
}

