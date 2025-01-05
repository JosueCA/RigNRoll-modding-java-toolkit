/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;

public class SCRpersongroup {
    public long nativePointer;

    public static native SCRpersongroup create();

    public static native SCRpersongroup create(SCRuniperson var0);

    public static native void removeGroup(SCRpersongroup var0);

    public native void setLeader(SCRuniperson var1);

    public native void addPerson(SCRuniperson var1);

    public native void removePerson(SCRuniperson var1);
}

