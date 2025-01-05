/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.CursedHiWay;
import xmlserialization.CursedHiWaySerializator;

public class CursedHiWayObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public CursedHiWayObjectXmlSerializable(CursedHiWay object) {
        this.serializator = new CursedHiWaySerializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.CURSED_HI_WAY;
    }
}

