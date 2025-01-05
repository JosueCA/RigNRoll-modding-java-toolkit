/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.chaseTopo;
import xmlserialization.ChaseTopoSerializator;

@ScenarioClass(scenarioStage=9)
public class ChaseTopoObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public ChaseTopoObjectXmlSerializable(chaseTopo object) {
        this.serializator = new ChaseTopoSerializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.CHASE_TOPO;
    }
}

