/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.ImodelCreate;
import rnrcore.SCRuniperson;

public class MC
implements ImodelCreate {
    public SCRuniperson create(String modelname, String name, String id) {
        SCRuniperson result = SCRuniperson.createMC(modelname, id);
        result.lockPerson();
        return result;
    }
}

