/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import rnrcore.SCRuniperson;

public class MENUTruckview {
    public long nativePointer;
    public String nameID;
    public String text;
    public int ID;
    public int userid;
    public int Xres;
    public int Yres;
    public int poy;
    public int pox;
    public int leny;
    public int lenx;
    public Vector textures;
    public Vector materials;
    public Vector callbacks;
    public String parentName;
    public String parentType;

    public native void Bind3DModel(String var1, int var2, int var3);

    public native void BindVehicle(long var1, int var3, int var4);

    public native void BindPerson(long var1, SCRuniperson var3, String var4, int var5, int var6);

    public native void SetState(int var1, int var2, int var3);

    public native void UnBind3DModel();

    public native int AddSwitch(String var1, int var2);

    public native int SetupViewer();
}

