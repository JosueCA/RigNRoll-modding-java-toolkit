/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;

public class SCRtalkingperson {
    public long nativePointer;
    public SCRuniperson person;

    public native void create(SCRuniperson var1);

    public SCRuniperson getPerson() {
        return this.person;
    }

    public native void stop();

    public native void say(String var1);

    public native void playAnimation(String var1, double var2);

    public long gNode() {
        return this.person.gNode();
    }

    public SCRtalkingperson(SCRuniperson per) {
        this.create(per);
        this.person = per;
    }
}

