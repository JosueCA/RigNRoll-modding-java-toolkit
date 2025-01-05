/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.chase00090;
import xmlserialization.Chase00090Serializator;

@ScenarioClass(scenarioStage=1)
public class ChaseObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public ChaseObjectXmlSerializable(chase00090 object) {
        this.serializator = new Chase00090Serializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.SAVE_DOROTHY;
    }
}

