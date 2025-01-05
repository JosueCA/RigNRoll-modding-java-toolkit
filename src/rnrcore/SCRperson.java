/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRanimparts;
import rnrcore.vectorJ;
import rnrscr.Chainanm;
import rnrscr.Eventanm;

public class SCRperson {
    public long nativePointer = 0L;

    public native void Type(int var1);

    public native void AttachToEvent(Eventanm var1);

    public native void AttachToEvent(int var1);

    public native void SetPos(vectorJ var1);

    public native void SetDir(vectorJ var1);

    public native void SetRandomTimeOnFive();

    public native void SetFive(int var1, SCRanimparts var2);

    public native void AddAnimGroup(SCRanimparts var1, int var2, int var3, int var4);

    public native String GetName();

    public void AddAnimGroup(SCRanimparts _five, int type) {
        this.AddAnimGroup(_five, type, 0, 0);
    }

    public native void nPerson(String var1, vectorJ var2, vectorJ var3);

    public native void nPersonNoDetailes(String var1, vectorJ var2, vectorJ var3);

    public native void nPersonSO(String var1, vectorJ var2, vectorJ var3);

    public native void nPersonNoDetailesSO(String var1, vectorJ var2, vectorJ var3);

    public native void setDependent();

    public native void addDependent(SCRperson var1);

    public native void delete();

    public native void StartChain(Chainanm var1);

    public native void CreateAnimatedItem(String var1, String var2, double var3);

    public native void ShiftPerson(vectorJ var1);

    public native void SetInBarWorld();

    public native void SetInOfficeWorld();

    public native void SetInHotelWorld();

    public native void SetInGameWorld();

    public static SCRperson CreateAnm(String strr, vectorJ begin, vectorJ direction, boolean on_specobject) {
        SCRperson ter = new SCRperson();
        if (on_specobject) {
            ter.nPersonSO(strr, begin, direction);
        } else {
            ter.nPerson(strr, begin, direction);
        }
        return ter;
    }

    public static SCRperson CreateAnmNoDetailes(String strr, vectorJ begin, vectorJ direction, boolean on_specobject) {
        SCRperson ter = new SCRperson();
        if (on_specobject) {
            ter.nPersonNoDetailesSO(strr, begin, direction);
        } else {
            ter.nPersonNoDetailes(strr, begin, direction);
        }
        return ter;
    }
}

