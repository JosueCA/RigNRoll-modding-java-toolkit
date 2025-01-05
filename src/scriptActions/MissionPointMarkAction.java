/*
 * Decompiled with CFR 0.151.
 */
package scriptActions;

import scriptActions.ScriptAction;

public class MissionPointMarkAction
extends ScriptAction {
    private String name = null;

    public MissionPointMarkAction() {
    }

    public MissionPointMarkAction(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }
        this.name = name;
    }

    public void act() {
    }

    public boolean validate() {
        return null != this.name;
    }
}

