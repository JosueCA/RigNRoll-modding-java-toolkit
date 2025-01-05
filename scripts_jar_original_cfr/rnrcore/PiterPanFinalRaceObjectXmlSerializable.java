/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.PiterPanFinalrace;
import rnrscenario.controllers.ScenarioHost;
import xmlserialization.PiterPanFinalRaceSerializator;

@ScenarioClass(scenarioStage=7)
public class PiterPanFinalRaceObjectXmlSerializable
extends ObjectXmlSerializable {
    private final IXMLSerializable serializator;

    public PiterPanFinalRaceObjectXmlSerializable(PiterPanFinalrace object, ScenarioHost host) {
        this.serializator = new PiterPanFinalRaceSerializator(object, host);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.PITERPAN_FINAL_RACE;
    }
}

