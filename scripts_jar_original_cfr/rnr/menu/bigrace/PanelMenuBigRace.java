/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.bigrace;

import java.util.ArrayList;
import java.util.List;
import menu.BaseMenu;
import menu.ListenerManager;
import menu.NotifyInformation;
import menu.menucreation;
import menu.menues;
import rnr.menu.MenuCreationListener;
import rnr.menu.bigrace.MenuBigRace;
import rnr.menu.filling.MenuFilling;
import rnrcore.event;

public class PanelMenuBigRace
extends BaseMenu
implements menucreation {
    private long rootUiElementHandle = 0L;
    private MenuBigRace menuImplementation = null;
    private List<MenuCreationListener> creationListeners = new ArrayList<MenuCreationListener>();

    public PanelMenuBigRace(int raceId, MenuBigRace.ControlGroupTemplate controlGroupTemplate, MenuBigRace.MenuIdTemplate menuIdTemplate, MenuFilling dataForMenu) {
        assert (null != controlGroupTemplate) : "'controlGroupTemplate' must be non-null reference";
        assert (null != menuIdTemplate) : "'menuIdTemplate' must be non-null reference";
        assert (null != dataForMenu) : "'dataForMenu' must be non-null reference";
        this.menuImplementation = new MenuBigRace(raceId, controlGroupTemplate, menuIdTemplate, dataForMenu);
        this.rootUiElementHandle = menues.createSimpleMenu(this, 1.0E9, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }

    public void addCreationListener(MenuCreationListener listener) {
        if (null != listener) {
            this.creationListeners.add(listener);
        }
    }

    long getHandle() {
        return this.rootUiElementHandle;
    }

    public void InitMenu(long menuHandle) {
        ListenerManager.TriggerEvent(103);
        this.menuImplementation.create(menuHandle);
        for (MenuCreationListener creationListener : this.creationListeners) {
            creationListener.menuCreationStarted(menuHandle);
        }
    }

    public void AfterInitMenu(long menuHandle) {
        ListenerManager.TriggerEvent(104);
        this.menuImplementation.finalizeCreation(menuHandle);
        NotifyInformation.notifyAfterInit(menuHandle);
        for (MenuCreationListener creationListener : this.creationListeners) {
            creationListener.menuCreationFinished(menuHandle);
        }
    }

    public void exitMenu(long menuHandle) {
        ListenerManager.TriggerEvent(105);
        this.menuImplementation.exit();
        event.Setevent(9001);
        for (MenuCreationListener creationListener : this.creationListeners) {
            creationListener.menuClosed(menuHandle);
        }
    }

    public String getControlGroupName() {
        return this.menuImplementation.getControlGroupName();
    }

    public String getMenuId() {
        return this.menuImplementation.getMenuId();
    }

    public void restartMenu(long menuHandle) {
    }

    public void hide() {
        this.menuImplementation.changeControlsVisibility(false);
    }

    public void show() {
        this.menuImplementation.changeControlsVisibility(true);
    }

    public long[] getControlsHandles() {
        return this.menuImplementation.getAllControlsHandles();
    }
}

