/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.RedRockCanyon;
import xmlserialization.RedRockCanyonSerializator;

@ScenarioClass(scenarioStage=13)
public class RedRockCanyonObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public RedRockCanyonObjectXmlSerializable(RedRockCanyon object) {
        this.serializator = new RedRockCanyonSerializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.RED_ROCK_CANYON;
    }
}

