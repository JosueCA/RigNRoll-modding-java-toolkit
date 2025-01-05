/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrscr.cSpecObjects;
import rnrscr.specobjects;

public class loadsobind {
    static final int BARBIND = 1;
    static final int STOBIND = 2;
    static final int MOTELBIND = 3;
    static final int OFICEBIND = 4;
    static final int PPBIND = 5;
    static final int POLICEBIND = 6;
    Object bind = null;
    int bindtype = 0;

    loadsobind() {
    }

    void BindBar(cSpecObjects bar) {
        this.bind = new cBindToSpecObject_Bar(bar);
        this.bindtype = 1;
    }

    void BindOffice(cSpecObjects office) {
        this.bind = new cBindToSpecObject_Office(office);
        this.bindtype = 4;
    }

    void BindMotel(cSpecObjects motel) {
        this.bind = new cBindToSpecObject_Motel(motel);
        this.bindtype = 3;
    }

    void BindPolice(cSpecObjects police) {
        this.bind = new cBindToSpecObject_Police(police);
        this.bindtype = 6;
    }

    void BindSTO(cSpecObjects police) {
        this.bind = new cBindToSpecObject_STO(police);
        this.bindtype = 2;
    }

    void BindPP(cSpecObjects police) {
        this.bind = new cBindToSpecObject_ParkingPlace(police);
        this.bindtype = 5;
    }

    boolean Unloaded(specobjects so) {
        switch (this.bindtype) {
            case 1: {
                return !((cBindToSpecObject_Bar)this.bind).Loaded(so);
            }
            case 2: {
                return !((cBindToSpecObject_STO)this.bind).Loaded(so);
            }
            case 3: {
                return !((cBindToSpecObject_Motel)this.bind).Loaded(so);
            }
            case 4: {
                return !((cBindToSpecObject_Office)this.bind).Loaded(so);
            }
            case 5: {
                return !((cBindToSpecObject_ParkingPlace)this.bind).Loaded(so);
            }
            case 6: {
                return !((cBindToSpecObject_Police)this.bind).Loaded(so);
            }
        }
        return false;
    }

    class cBindToSpecObject_ParkingPlace {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedParkingPlaces(this.object.name);
        }

        cBindToSpecObject_ParkingPlace(cSpecObjects ob) {
            this.object = ob;
        }
    }

    class cBindToSpecObject_Police {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedPolice(this.object.name);
        }

        cBindToSpecObject_Police(cSpecObjects ob) {
            this.object = ob;
        }
    }

    class cBindToSpecObject_STO {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedRepair(this.object.name);
        }

        cBindToSpecObject_STO(cSpecObjects ob) {
            this.object = ob;
        }
    }

    class cBindToSpecObject_Motel {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedMotel(this.object.name);
        }

        cBindToSpecObject_Motel(cSpecObjects ob) {
            this.object = ob;
        }
    }

    class cBindToSpecObject_Office {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedOffice(this.object.name);
        }

        cBindToSpecObject_Office(cSpecObjects ob) {
            this.object = ob;
        }
    }

    class cBindToSpecObject_Bar {
        cSpecObjects object;

        boolean Loaded(specobjects so) {
            return so.ifLoadedBar(this.object.name);
        }

        cBindToSpecObject_Bar(cSpecObjects ob) {
            this.object = ob;
        }
    }
}

