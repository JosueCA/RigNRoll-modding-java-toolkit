/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import players.Crew;
import rnrscenario.scenarioscript;
import rnrscenario.sctask;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;

public final class Firstsecretarycall
extends sctask {
    String officename = "OxnardOffise";
    String dialogname = "demosecretary1";
    double lentooffice = 400.0;
    boolean test = false;
    boolean f_cbcalled = false;

    Firstsecretarycall() {
        super(3, false);
    }

    public void run() {
        this.initiateCBcallNearOffice();
    }

    void initiateCBcallNearOffice() {
        if (this.f_cbcalled) {
            return;
        }
        if (this.test) {
            this.f_cbcalled = true;
            this.startCBCall();
            return;
        }
        specobjects SO = specobjects.getInstance();
        cSpecObjects office = SO.GetNearestLoadedOffice();
        if (office != null && office.name.compareToIgnoreCase(this.officename) == 0 && office.position.len2(Crew.getIgrokCar().gPosition()) < this.lentooffice * this.lentooffice) {
            this.f_cbcalled = true;
            this.startCBCall();
        }
    }

    void startCBCall() {
        scenarioscript.script.launchCall(this.dialogname);
    }
}

