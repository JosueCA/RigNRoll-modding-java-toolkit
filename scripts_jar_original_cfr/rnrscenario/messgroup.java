/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import menu.menues;
import menuscript.MessageWindow;
import rnrscenario.sctask;

public class messgroup
extends sctask {
    String message;
    boolean hasok;
    boolean istutorial;
    double lifetime;
    String exitkey;
    boolean stopworld;
    boolean polosy;

    messgroup(String message, boolean hasok, boolean istutorial, double lifetime, String exitkey, boolean stopworld, boolean polosy) {
        super(3, true);
        this.message = message;
        this.hasok = hasok;
        this.istutorial = istutorial;
        this.lifetime = lifetime;
        this.exitkey = exitkey;
        this.stopworld = stopworld;
        this.polosy = polosy;
        if (this.makecreation()) {
            this.finish();
        }
    }

    public void run() {
        if (this.makecreation()) {
            this.finish();
        }
    }

    boolean makecreation() {
        if (!menues.cancreate_somenu()) {
            return false;
        }
        this.create();
        return true;
    }

    void create() {
        MessageWindow.CreateMessageWindow(this.message, this.hasok, this.istutorial, this.lifetime, this.exitkey, this.stopworld, this.polosy);
    }
}

