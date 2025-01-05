/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.MENUsimplebutton_field;
import rnr.menu.MenuTools;
import rnr.menu.filling.MenuFieldFiller;
import rnr.tech.Code2;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FillerButtonClickCallback
extends MenuFieldFiller {
    private final Code2<Long, MENUsimplebutton_field> callback;

    public FillerButtonClickCallback(String fieldName, Code2<Long, MENUsimplebutton_field> callback) {
        super(fieldName);
        this.callback = callback;
    }

    @Override
    public void fillFieldOfMenu(long menuHandle) {
        if (null != this.callback) {
            MenuTools.setButtonClickCallback(menuHandle, this.getFieldName(), this.callback);
        }
    }
}

