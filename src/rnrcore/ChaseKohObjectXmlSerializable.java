/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ChaseKoh;
import xmlserialization.ChaseKohSerializator;

@ScenarioClass(scenarioStage=15)
public class ChaseKohObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public ChaseKohObjectXmlSerializable(ChaseKoh object) {
        this.serializator = new ChaseKohSerializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.CHASE_KOH;
    }
}

