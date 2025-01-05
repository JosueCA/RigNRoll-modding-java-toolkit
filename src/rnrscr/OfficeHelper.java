/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import rnrcore.CoreTime;
import rnrcore.eng;
import rnrscr.cSpecObjects;
import rnrscr.specobjects;

class OfficeHelper {
    OfficeHelper() {
    }

    public static boolean hasOfficeSecretary() {
        cSpecObjects specobject = specobjects.getInstance().GetNearestLoadedOffice();
        if (null == specobject) {
            eng.log("ERRORR. hasOfficeSecretary has recieved null specobject on requesting loaded office.");
            return false;
        }
        if (specobject.name.compareTo("OxnardOffise") != 0) {
            return false;
        }
        CoreTime time = new CoreTime();
        return (double)time.gHour() >= 8.0 && (double)time.gHour() < 18.0;
    }
}

