/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.MENUText_field;
import menu.menues;
import rnr.menu.MenuTools;
import rnr.menu.filling.MenuFieldFiller;
import rnr.tech.Code1;

public final class FillerHideTextField
extends MenuFieldFiller {
    public FillerHideTextField(String fieldName) {
        super(fieldName);
    }

    public void fillFieldOfMenu(long menuHandle) {
        if (null == this.getFieldName() || 0L == menuHandle) {
            return;
        }
        MenuTools.findAndProcessFieldOfTypeInMenu(menuHandle, this.getFieldName(), MENUText_field.class, new Code1<MENUText_field>(){

            @Override
            public void execute(MENUText_field argument) {
                menues.SetShowField(argument.nativePointer, false);
            }
        });
    }
}

