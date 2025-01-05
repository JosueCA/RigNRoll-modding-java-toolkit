/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;
import rnrscenario.sctask;

public class Scanimatedtask
extends sctask
implements anm {
    private ScriptRef uid = new ScriptRef();

    public Scanimatedtask(int tip, boolean stopOnGameDeinit) {
        super(tip, stopOnGameDeinit);
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void updateNative(int p) {
    }

    public boolean animaterun(double dt) {
        return false;
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }
}

