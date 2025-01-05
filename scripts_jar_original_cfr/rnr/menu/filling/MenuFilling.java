/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling;

import java.util.ArrayList;
import java.util.List;
import rnr.menu.filling.MenuFieldFiller;

public final class MenuFilling {
    private static final int EXPECTED_FILLERS_COUNT = 8;
    private final List<MenuFieldFiller> fillersOnMenuInit = new ArrayList<MenuFieldFiller>(8);
    private final List<MenuFieldFiller> filersOnMenuInitCompletion = new ArrayList<MenuFieldFiller>(8);

    public void addFillerToBeCalledOnMenuInit(MenuFieldFiller filler) {
        if (null != filler) {
            this.fillersOnMenuInit.add(filler);
        }
    }

    public void addFillerToBeCalledOnMenuInitCompletion(MenuFieldFiller filler) {
        if (null != filler) {
            this.filersOnMenuInitCompletion.add(filler);
        }
    }

    public void menuInitializing(long menuHandle) {
        for (MenuFieldFiller filler : this.fillersOnMenuInit) {
            filler.fillFieldOfMenu(menuHandle);
        }
    }

    public void menuInitialized(long menuHandle) {
        for (MenuFieldFiller filler : this.filersOnMenuInitCompletion) {
            filler.fillFieldOfMenu(menuHandle);
        }
    }

    public void menuClosed() {
        for (MenuFieldFiller filler : this.filersOnMenuInitCompletion) {
            filler.freeResources();
        }
        for (MenuFieldFiller filler : this.fillersOnMenuInit) {
            filler.freeResources();
        }
    }
}

