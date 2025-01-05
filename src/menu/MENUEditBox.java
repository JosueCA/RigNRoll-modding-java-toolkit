/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;

public class MENUEditBox {
    public long nativePointer;
    public String nameID;
    public String text;
    public int ID;
    public int userid;
    int Xres;
    int Yres;
    public int poy;
    public int pox;
    public int leny;
    public int lenx;
    public Vector textures;
    public Vector materials;
    public Vector callbacks;
    public String parentName;
    public String parentType;

    public native void SetMode(int var1);

    public native int GetHour();

    public native int GetMin();

    public native void IncDecValue(int var1);

    public native boolean SetHour(int var1);

    public native boolean SetMin(int var1);
}

