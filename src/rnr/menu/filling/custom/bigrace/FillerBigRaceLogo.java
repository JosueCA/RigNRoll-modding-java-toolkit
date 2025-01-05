/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.custom.bigrace;

import menu.menues;
import rnr.menu.filling.MenuFieldFiller;
import rnrconfig.IconMappings;

public final class FillerBigRaceLogo
extends MenuFieldFiller {
    public static final String UI_ELEMENT_NAME_RACE_LOGOTYPE = "THE RACE LOGOTYPE";
    private final String logoName;

    public FillerBigRaceLogo(String logoName) {
        this(UI_ELEMENT_NAME_RACE_LOGOTYPE, logoName);
    }

    public FillerBigRaceLogo(String controlName, String logoName) {
        super(controlName);
        this.logoName = logoName;
    }

    public void fillFieldOfMenu(long menuHandle) {
        if (0L == menuHandle) {
            return;
        }
        long raceLogoPictureHandle = menues.FindFieldInMenu(menuHandle, this.getFieldName());
        if (0L != raceLogoPictureHandle) {
            if (null != this.logoName) {
                menues.SetShowField(raceLogoPictureHandle, true);
                IconMappings.remapRaceLogos(this.logoName, raceLogoPictureHandle);
            } else {
                menues.SetShowField(raceLogoPictureHandle, false);
            }
        }
    }
}

