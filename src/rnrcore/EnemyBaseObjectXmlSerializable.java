/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.IXMLSerializable;
import rnrcore.ObjectXmlSerailizatorId;
import rnrcore.ObjectXmlSerializable;
import rnrscenario.consistency.ScenarioClass;
import rnrscenario.controllers.EnemyBase;
import xmlserialization.EnemyBaseSerializator;

@ScenarioClass(scenarioStage=14)
public class EnemyBaseObjectXmlSerializable
extends ObjectXmlSerializable {
    IXMLSerializable serializator = null;

    public EnemyBaseObjectXmlSerializable(EnemyBase object) {
        this.serializator = new EnemyBaseSerializator(object);
    }

    public IXMLSerializable getXmlSerializator() {
        return this.serializator;
    }

    public ObjectXmlSerailizatorId getSerializationId() {
        return ObjectXmlSerailizatorId.ENEMY_BASE;
    }
}

