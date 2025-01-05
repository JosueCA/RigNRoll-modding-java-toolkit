/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import rnrorg.IStoreorgelement;
import rnrorg.Organizers;
import scriptActions.ScriptAction;

public abstract class OrgAction
extends ScriptAction {
    String name = null;

    IStoreorgelement getOrg() {
        return Organizers.getInstance().get(this.name);
    }

    public OrgAction(String name) {
        this.name = name;
    }

    public OrgAction() {
        this.name = "unknown";
    }

    public abstract void act();
}

