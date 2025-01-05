/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menu.ListOfAlternatives;
import menu.RadioGroupSmartSwitch;
import menu.SliderGroupRadioButtons;
import menu.menues;

public class CA {
    static String FILENAME = "..\\data\\config\\menu\\menu_MAIN.xml";
    private static final String LISTOFALTERANTIVESGROUP = "Tablegroup - ELEMENTS - List";
    private static final String RADIOGROUPSMARTSWICTHGROUP = "Tablegroup - ELEMENTS - Switch";
    private static final String RADIOGROUPSMARTSWICTHGROUPGRAY = "Tablegroup - ELEMENTS - Switch - GRAY";
    private static final String SLIDERRADIOBUTTONSGROUP = "Tablegroup - ELEMENTS - Slider";

    static final void freezControl(long field) {
        menues.SetBlindess(field, true);
        menues.SetIgnoreEvents(field, true);
    }

    static final void unfreezControl(long field) {
        menues.SetBlindess(field, false);
        menues.SetIgnoreEvents(field, false);
    }

    static final void hideControl(long field) {
        menues.SetShowField(field, false);
    }

    static final void showControl(long field) {
        menues.SetShowField(field, true);
    }

    static final ListOfAlternatives createListOfAlternatives(String title, String[] alternarives, boolean bCanChange) {
        return new ListOfAlternatives(FILENAME, LISTOFALTERANTIVESGROUP, title, alternarives, !bCanChange);
    }

    static final RadioGroupSmartSwitch createRadioGroupSmartSwitch(String title, boolean value, boolean bCanChange) {
        return new RadioGroupSmartSwitch(FILENAME, RADIOGROUPSMARTSWICTHGROUP, title, value, !bCanChange);
    }

    static final RadioGroupSmartSwitch createRadioGroupSmartSwitchGray(String title, boolean value, boolean bCanChange) {
        return new RadioGroupSmartSwitch(FILENAME, RADIOGROUPSMARTSWICTHGROUPGRAY, title, value, !bCanChange);
    }

    static final SliderGroupRadioButtons createSliderRadioButtons(String title, int min_value, int max_value, int cur_value, boolean bCanChange) {
        return new SliderGroupRadioButtons(FILENAME, SLIDERRADIOBUTTONSGROUP, title, min_value, max_value, cur_value, !bCanChange);
    }
}

