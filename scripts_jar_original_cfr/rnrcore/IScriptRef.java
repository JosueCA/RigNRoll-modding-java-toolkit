/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;

public interface IScriptRef {
    public void setUid(int var1);

    public int getUid();

    public void updateNative(int var1);

    public IXMLSerializable getXmlSerializator();
}

