/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.menues;
import rnr.menu.filling.MenuFieldFiller;

public final class FillerSetFocusOnButton
extends MenuFieldFiller {
    public FillerSetFocusOnButton(String fieldName) {
        super(fieldName);
    }

    public void fillFieldOfMenu(long menuHandle) {
        menues.setfocuscontrolonmenu(menuHandle, menues.FindFieldInMenu(menuHandle, this.getFieldName()));
    }
}

