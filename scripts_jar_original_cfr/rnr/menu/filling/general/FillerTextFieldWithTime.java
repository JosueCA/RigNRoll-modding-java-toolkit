/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.TimeData;
import menuscript.Converts;
import rnr.menu.filling.MenuFieldFiller;
import rnr.menu.filling.general.FillerTextFieldSubstitution;

public final class FillerTextFieldWithTime
extends MenuFieldFiller {
    private FillerTextFieldSubstitution innerFiller;

    public FillerTextFieldWithTime(String fieldName, String macrosName, TimeData time) {
        super(fieldName);
        String min = Converts.makeClock00(time.minutes + time.hours * 60);
        String sec = Converts.makeClock(time.seconds);
        String finalText = String.format("%s:%s", min, sec);
        this.innerFiller = new FillerTextFieldSubstitution(fieldName, macrosName, finalText);
    }

    public void fillFieldOfMenu(long menuHandle) {
        this.innerFiller.fillFieldOfMenu(menuHandle);
    }
}

