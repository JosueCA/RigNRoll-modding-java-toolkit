/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import rnr.menu.MenuTools;
import rnr.menu.filling.MenuFieldFiller;
import rnr.tech.Code1;
import scenarioUtils.Pair;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FillerTextFieldMultipleSubstitution
extends MenuFieldFiller {
    private final KeyPair[] substitution;

    public FillerTextFieldMultipleSubstitution(String fieldName, Pair<String, String> ... substitutions) {
        super(fieldName);
        if (null == substitutions) {
            this.substitution = new KeyPair[0];
        } else {
            this.substitution = new KeyPair[substitutions.length];
            for (int i = 0; i < substitutions.length; ++i) {
                this.substitution[i] = new KeyPair(substitutions[i].getFirst(), substitutions[i].getSecond());
            }
        }
    }

    @Override
    public void fillFieldOfMenu(long menuReference) {
        MenuTools.findAndProcessFieldOfTypeInMenu(menuReference, this.getFieldName(), MENUText_field.class, new Code1<MENUText_field>(){

            @Override
            public void execute(MENUText_field argument) {
                MacroKit.ApplyToTextfield(argument, FillerTextFieldMultipleSubstitution.this.substitution);
            }
        });
    }
}

