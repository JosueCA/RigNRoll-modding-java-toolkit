/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import rnr.menu.filling.MenuFieldFiller;
import rnr.menu.filling.general.FillerTextFieldMultipleSubstitution;
import scenarioUtils.Pair;

public final class FillerTextFieldSubstitution
extends MenuFieldFiller {
    private final FillerTextFieldMultipleSubstitution innerFiller;

    public FillerTextFieldSubstitution(String fieldName, String textToReplace, String valueToSubstitute) {
        super(fieldName);
        this.innerFiller = new FillerTextFieldMultipleSubstitution(fieldName, new Pair<String, String>(textToReplace, valueToSubstitute));
    }

    public void fillFieldOfMenu(long menuReference) {
        this.innerFiller.fillFieldOfMenu(menuReference);
    }
}

