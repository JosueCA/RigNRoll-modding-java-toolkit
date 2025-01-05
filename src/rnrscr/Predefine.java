/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.SCRperson;
import rnrcore.vectorJ;
import rnrscr.Bar;
import rnrscr.cSpecObjects;
import rnrscr.warehouse;

public class Predefine {
    public void PlacePerson(SCRperson PERSONAGE, vectorJ possit, vectorJ dirrit) {
        PERSONAGE.SetPos(possit);
        PERSONAGE.SetDir(dirrit);
    }

    public long[] Table(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().Table(positions, directions);
    }

    public long[] BarStand(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().BarStand(positions, directions);
    }

    public long[] BarmanStand(vectorJ[] positions, vectorJ[] directions) {
        return Bar.getInstance().BarmanStand(positions, directions);
    }

    public void InitiateBar() {
    }

    public long[] WHoperator(vectorJ[] positions, vectorJ[] directions, vectorJ[] positions_crane, vectorJ[] directions_crane, vectorJ[] positions_crane1, vectorJ[] directions_crane1) {
        return warehouse.getInstance().WHoperator(positions, directions, positions_crane, directions_crane, positions_crane1, directions_crane);
    }

    void startworkwithBar(cSpecObjects so) {
        Bar.getInstance().StartWorkWithSO(so);
    }

    void startworkwithWarehouse(cSpecObjects so) {
        warehouse.getInstance().StartWorkWithSO(so);
    }
}

