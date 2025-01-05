/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.SCRuniperson;

public class SceneActorsPool {
    String name = null;
    SCRuniperson person = null;

    public SceneActorsPool(String name, SCRuniperson person) {
        this.name = name;
        this.person = person;
    }

    public void lock() {
        if (this.person != null) {
            this.person.lockPerson();
        }
    }

    public void unlock() {
        if (this.person != null) {
            this.person.unlockPerson();
        }
    }
}

