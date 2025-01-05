/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.io.Serializable;
import rnrscenario.demointro;

public class Tutorial
implements Serializable {
    public static final long serialVersionUID = 0L;
    private boolean tutorial_started = false;

    public void tutorial() {
        if (this.tutorial_started) {
            return;
        }
        this.tutorial_started = true;
        new demointro().start();
    }
}

