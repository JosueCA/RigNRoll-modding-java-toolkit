/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPandoomedrace;
import rnrscenario.controllers.ScenarioHost;
import xmlserialization.PiterPanDoomedRaceSerializator;

@ScenarioClass(scenarioStage=5)
public class PiterPanDoomedRaceObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public PiterPanDoomedRaceObjectXmlSerializable(PiterPandoomedrace object, ScenarioHost host) {
        this.serializator = new PiterPanDoomedRaceSerializator(object, host);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.PITERPAN_DOOMED_RACE;
    }
}

