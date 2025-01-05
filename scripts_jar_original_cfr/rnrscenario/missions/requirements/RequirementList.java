/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario.missions.requirements;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import rnrscenario.missions.requirements.Requirement;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class RequirementList
extends Requirement {
    private List<Requirement> requirements = new LinkedList<Requirement>();

    public void addRequirement(Requirement target) {
        if (null != target) {
            this.requirements.add(target);
        }
    }

    Collection<Requirement> getList() {
        return Collections.unmodifiableList(this.requirements);
    }
}

