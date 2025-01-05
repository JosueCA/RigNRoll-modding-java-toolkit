/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.matrixJ;
import rnrcore.vectorJ;

public class SCRuniperson {
    public long nativePointer = 0L;

    public static native SCRuniperson createLoadedObject(String var0);

    public static native SCRuniperson createMC(String var0, String var1);

    public static native SCRuniperson createAmbientPerson(String var0, String var1);

    public static native SCRuniperson createMissionVotingPerson(String var0, String var1);

    public static native SCRuniperson createMissionScenePerson(String var0, String var1);

    public static native SCRuniperson createCutScenePerson(String var0, String var1);

    public static native SCRuniperson createCutSceneAmbientPerson(String var0, String var1);

    public static native SCRuniperson createCBVideoPerson(String var0, String var1);

    public static native SCRuniperson createSOPerson(String var0, String var1);

    public static native SCRuniperson createSOMainPerson(String var0, String var1);

    public static native SCRuniperson createNamedAmbientPerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedMissionVotingPerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedMissionScenePerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedCutScenePerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedCutSceneAmbientPerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedCBVideoPerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedSOPerson(String var0, String var1, String var2);

    public static native SCRuniperson createNamedSOMainPerson(String var0, String var1, String var2);

    public native void CreateAnimatedItem(String var1, String var2, double var3);

    public native void AddAnimation(String var1);

    public native void SetPosition(vectorJ var1);

    public native void SetDirection(vectorJ var1);

    public native vectorJ GetPosition();

    public native matrixJ GetMatrix();

    public native void DeleteAnimatedItem(String var1, String var2);

    public native void CreateAnimatedSpace_timedependance(String var1, String var2, String var3, String var4, double var5, long var7, long var9, matrixJ var11, boolean var12, boolean var13);

    public native void DeleteAnimatedSpace(String var1, String var2, long var3);

    public native void SetInBarWorld();

    public native void SetInOfficeWorld();

    public native void SetInHotelWorld();

    public native void SetInGameWorld();

    public native void SetInWarehouseEnvironment();

    public native void SetInWorld(String var1);

    public native void play();

    public native void stop();

    public native long gNode();

    public native void stopbeingDriverOrPassanger();

    public static native SCRuniperson findModel(String var0);

    private native void lock();

    private native void unlock();

    public native void setAnimationsLoadConsequantly();

    public static SCRuniperson createCutSceneAmbientPerson(String model, String name, String id, vectorJ pos, vectorJ dir) {
        SCRuniperson ter = SCRuniperson.createNamedCutSceneAmbientPerson(model, name, id);
        ter.SetDirection(dir);
        ter.SetPosition(pos);
        return ter;
    }

    public void lockPerson() {
        this.lock();
    }

    public void unlockPerson() {
        this.unlock();
    }
}

