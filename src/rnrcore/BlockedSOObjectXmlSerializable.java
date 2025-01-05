/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.SoBlock;
import xmlserialization.BlockedSOSerializator;

public class BlockedSOObjectXmlSerializable
extends ObjectXmlSerializable {
    private IXMLSerializable serializator = null;

    public BlockedSOObjectXmlSerializable(SoBlock blocker) {
        this.serializator = new BlockedSOSerializator(blocker);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.BLOCK_SO;
    }
}

