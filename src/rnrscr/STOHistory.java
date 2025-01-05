/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import players.Crew;
import players.actorveh;
import rnrcore.eng;

public class STOHistory {
    private int count_meetings = 0;
    private String lastcarmodel = "";
    private boolean cameWithNewCar = false;
    private double damagelevel = 0.0;

    public void visit() {
        String model;
        ++this.count_meetings;
        actorveh ourcar = Crew.getIgrokCar();
        if (ourcar == null || ourcar.getCar() == 0) {
            eng.err("Visiting STO with realy bad car.");
        }
        this.cameWithNewCar = (model = eng.GetVehiclePrefix(ourcar.getCar())).compareToIgnoreCase(this.lastcarmodel) != 0;
        this.lastcarmodel = model;
        this.damagelevel = ourcar.querryCarDamaged();
    }

    public boolean isFirstTimeHere() {
        return this.count_meetings == 1;
    }

    public boolean isSecondOrMoreTimeHere() {
        return this.count_meetings >= 2;
    }

    public boolean newTruck() {
        return this.cameWithNewCar;
    }

    public boolean carIsDamaged() {
        return this.damagelevel > 0.5;
    }

    public boolean isCameWithNewCar() {
        return this.cameWithNewCar;
    }

    public void setCameWithNewCar(boolean cameWithNewCar) {
        this.cameWithNewCar = cameWithNewCar;
    }

    public int getCount_meetings() {
        return this.count_meetings;
    }

    public void setCount_meetings(int count_meetings) {
        this.count_meetings = count_meetings;
    }

    public double getDamagelevel() {
        return this.damagelevel;
    }

    public void setDamagelevel(double damagelevel) {
        this.damagelevel = damagelevel;
    }

    public String getLastcarmodel() {
        return this.lastcarmodel;
    }

    public void setLastcarmodel(String lastcarmodel) {
        this.lastcarmodel = lastcarmodel;
    }
}

