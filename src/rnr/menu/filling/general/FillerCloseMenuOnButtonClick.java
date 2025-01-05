/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.menues;
import rnr.menu.filling.MenuFieldFiller;

public final class FillerCloseMenuOnButtonClick
extends MenuFieldFiller {
    public FillerCloseMenuOnButtonClick(String fieldName) {
        super(fieldName);
    }

    public void fillFieldOfMenu(long menuHandle) {
        if (0L != menuHandle) {
            menues.SetMenuCallBack_ExitMenu(menuHandle, menues.FindFieldInMenu(menuHandle, this.getFieldName()), 4L);
        }
    }
}

