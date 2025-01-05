/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.ScenarioHost;
import rnrscenario.controllers.preparesc00060;
import xmlserialization.Preparesc00060Serializator;

@ScenarioClass(scenarioStage=1)
public class Preparesc00060ObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public Preparesc00060ObjectXmlSerializable(preparesc00060 object, ScenarioHost host) {
        this.serializator = new Preparesc00060Serializator(object, host);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.PREPARE_SAVE_DOROTHY;
    }
}

