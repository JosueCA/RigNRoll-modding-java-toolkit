/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import xmlserialization.ObjectXmlSerializator;

public abstract class ObjectXmlSerializable {
    public void registerObjectXmlSerializable() {
        ObjectXmlSerializator.getInstance().registerObject(this);
    }

    public void unRegisterObjectXmlSerializable() {
        ObjectXmlSerializator.getInstance().unRegisterObject(this);
    }

    public abstract IXMLSerializable getXmlSerializator();

    public abstract ObjectXmlSerailizatorId getSerializationId();
}

