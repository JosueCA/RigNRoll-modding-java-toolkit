/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.List;
import rnrorg.IPickUpEventListener;

public class PickUpEventManager {
    private List<IPickUpEventListener> m_listeners = new ArrayList<IPickUpEventListener>();
    private List<IPickUpEventListener> m_listenersToRemove = new ArrayList<IPickUpEventListener>();
    private List<IPickUpEventListener> m_listenersToAdd = new ArrayList<IPickUpEventListener>();
    private boolean m_onEventProcess = false;
    private static PickUpEventManager instance = null;

    public static void addListener(IPickUpEventListener listener) {
        if (instance == null) {
            return;
        }
        if (!PickUpEventManager.instance.m_onEventProcess) {
            PickUpEventManager.instance.m_listeners.add(listener);
        } else {
            PickUpEventManager.instance.m_listenersToAdd.add(listener);
        }
    }

    public static void removeListener(IPickUpEventListener listener) {
        if (instance == null) {
            return;
        }
        if (!PickUpEventManager.instance.m_onEventProcess) {
            PickUpEventManager.instance.m_listeners.remove(listener);
        } else {
            PickUpEventManager.instance.m_listenersToRemove.add(listener);
        }
    }

    private void makePickUpEventOnMission(String missionName) {
        this.m_onEventProcess = true;
        for (IPickUpEventListener listener : this.m_listeners) {
            listener.onPickUpevent(missionName);
        }
        this.m_onEventProcess = false;
        this.m_listeners.addAll(this.m_listenersToAdd);
        this.m_listenersToAdd.clear();
        this.m_listeners.removeAll(this.m_listenersToRemove);
        this.m_listenersToRemove.clear();
    }

    public static void pickUpEventOnMission(String missionName) {
        if (instance == null) {
            return;
        }
        instance.makePickUpEventOnMission(missionName);
    }

    public static void init() {
        instance = new PickUpEventManager();
    }

    public static void deinit() {
        instance = null;
    }
}

