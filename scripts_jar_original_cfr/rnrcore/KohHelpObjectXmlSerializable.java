/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.KohHelpManage;
import rnrscenario.controllers.ScenarioHost;
import xmlserialization.KohHelpSerializator;

@ScenarioClass(scenarioStage=0)
public class KohHelpObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public KohHelpObjectXmlSerializable(KohHelpManage object, ScenarioHost host) {
        this.serializator = new KohHelpSerializator(object, host);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.KOH_HELP;
    }
}

