/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.MENUText_field;
import menu.menues;
import rnr.menu.MenuTools;
import rnr.menu.filling.MenuFieldFiller;
import rnr.tech.Code1;

public final class FillerTextField
extends MenuFieldFiller {
    private final String fieldValue;

    public FillerTextField(String fieldName, String fieldValue) {
        super(fieldName);
        this.fieldValue = fieldValue;
    }

    public void fillFieldOfMenu(long menuReference) {
        if (0L == menuReference || null == this.fieldValue || null == this.getFieldName()) {
            return;
        }
        MenuTools.findAndProcessFieldOfTypeInMenu(menuReference, this.getFieldName(), MENUText_field.class, new Code1<MENUText_field>(){

            @Override
            public void execute(MENUText_field argument) {
                argument.text = FillerTextField.this.fieldValue;
                menues.UpdateField(argument);
            }
        });
    }
}

