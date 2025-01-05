/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.ImodelCreate;
import rnrcore.SCRuniperson;

public class ScenarioPlayer
implements ImodelCreate {
    public SCRuniperson create(String modelname, String name, String id) {
        return SCRuniperson.createCutScenePerson(modelname, id);
    }
}

