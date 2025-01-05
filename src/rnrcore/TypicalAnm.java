/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ScriptRef;
import rnrcore.anm;

public abstract class TypicalAnm
implements anm {
    private ScriptRef uid = new ScriptRef();

    public void updateNative(int nativePointer) {
    }

    public abstract boolean animaterun(double var1);

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public IXMLSerializable getXmlSerializator() {
        return null;
    }
}

