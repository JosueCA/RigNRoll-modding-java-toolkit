/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace;

import rnr.menu.MenuCreationListener;
import rnr.menu.bigrace.MenuBigRace;
import rnr.menu.filling.MenuFilling;

final class PopupMenuBigRace
implements MenuCreationListener {
    private MenuBigRace menuImplementation = null;

    PopupMenuBigRace(int raceId, MenuBigRace.ControlGroupTemplate controlGroupTemplate, MenuBigRace.MenuIdTemplate menuIdTemplate, MenuFilling dataForMenu) {
        assert (null != controlGroupTemplate) : "'controlGroupTemplate' must be non-null reference";
        assert (null != menuIdTemplate) : "'menuIdTemplate' must be non-null reference";
        assert (null != dataForMenu) : "'dataForMenu' must be non-null reference";
        this.menuImplementation = new MenuBigRace(raceId, controlGroupTemplate, menuIdTemplate, dataForMenu);
    }

    void show() {
        this.menuImplementation.changeControlsVisibility(true);
    }

    void hide() {
        this.menuImplementation.changeControlsVisibility(false);
    }

    public void menuCreationFinished(long menuHandle) {
        this.menuImplementation.finalizeCreation(menuHandle);
    }

    public void menuClosed(long menuHandle) {
        this.menuImplementation.exit();
    }

    public void menuCreationStarted(long menuHandle) {
        this.menuImplementation.create(menuHandle);
    }

    String getXmlFileWithMenu() {
        return this.menuImplementation.getXmlFileWithMenu();
    }
}

