/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling.general;

import menu.menues;
import rnr.menu.filling.MenuFieldFiller;
import rnr.tech.Function0;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ControlsRenamer
extends MenuFieldFiller {
    private final String suffixToAddToNames;
    private final String controlGroupName;
    private Function0<long[]> controlsArraySource = null;

    public ControlsRenamer(String suffixToAddToNames, String controlGroupName, Function0<long[]> controlsArraySource) {
        super(null);
        this.suffixToAddToNames = suffixToAddToNames;
        this.controlGroupName = controlGroupName;
        this.controlsArraySource = controlsArraySource;
    }

    @Override
    public void fillFieldOfMenu(long menuHandle) {
        long[] uiControls = this.controlsArraySource.execute();
        if (null != uiControls) {
            for (int i = 0; i < uiControls.length; ++i) {
                String parentName = menues.GetFieldParentName(uiControls[i]);
                if (null == parentName) {
                    System.out.println("QQQ");
                } else if (0 != parentName.compareTo(this.controlGroupName)) {
                    menues.SetFieldParentName(uiControls[i], parentName + this.suffixToAddToNames);
                }
                menues.SetFieldName(menuHandle, uiControls[i], menues.GetFieldName(uiControls[i]) + this.suffixToAddToNames);
            }
        }
    }
}

