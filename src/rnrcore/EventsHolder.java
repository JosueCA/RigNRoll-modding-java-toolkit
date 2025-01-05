/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import menu.JavaEventCb;
import menu.JavaEvents;
import rnrcore.IEventListener;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class EventsHolder
implements JavaEventCb {
    private HashMap<Integer, ArrayList<IEventListener>> events = new HashMap();
    private HashMap<Integer, ArrayList<IEventListener>> eventsToRemove = new HashMap();
    private HashMap<Integer, ArrayList<IEventListener>> eventsToAdd = new HashMap();
    private static EventsHolder singleton = null;
    private boolean m_onQueue = false;

    private void add(int id, IEventListener listener) {
        if (!this.m_onQueue) {
            if (!this.events.containsKey(id)) {
                ArrayList<IEventListener> obj = new ArrayList<IEventListener>();
                obj.add(listener);
                this.events.put(id, obj);
                JavaEvents.RegisterEvent(id, this);
            } else {
                this.events.get(id).add(listener);
            }
        } else {
            EventsHolder.addQueedAdd(this.eventsToAdd, id, listener);
        }
    }

    private static void addQueedAdd(HashMap<Integer, ArrayList<IEventListener>> mapToAddTo, int id, IEventListener listener) {
        if (!mapToAddTo.containsKey(id)) {
            ArrayList<IEventListener> addedListener = new ArrayList<IEventListener>();
            addedListener.add(listener);
            mapToAddTo.put(id, addedListener);
        } else {
            mapToAddTo.get(id).add(listener);
        }
    }

    private void remove(int id, IEventListener listener) {
        if (!this.m_onQueue) {
            if (!this.events.containsKey(id)) {
                return;
            }
            ArrayList<IEventListener> obj = this.events.get(id);
            obj.remove(listener);
        } else {
            EventsHolder.addQueedAdd(this.eventsToRemove, id, listener);
        }
    }

    private static EventsHolder gHolder() {
        if (singleton == null) {
            singleton = new EventsHolder();
        }
        return singleton;
    }

    // @Override
    public void OnEvent(int ID, int value, Object obj) {
        if (!this.events.containsKey(ID)) {
            return;
        }
        ArrayList<IEventListener> lsts = this.events.get(ID);
        this.m_onQueue = true;
        for (IEventListener listener : lsts) {
            listener.on_event(value);
        }
        this.m_onQueue = false;
        Set<Map.Entry<Integer, ArrayList<IEventListener>>> set = this.eventsToRemove.entrySet();
        for (Map.Entry<Integer, ArrayList<IEventListener>> entry : set) {
            for (IEventListener listener : entry.getValue()) {
                this.remove(entry.getKey(), listener);
            }
        }
        set = this.eventsToAdd.entrySet();
        for (Map.Entry<Integer, ArrayList<IEventListener>> entry : set) {
            for (IEventListener listener : entry.getValue()) {
                this.add(entry.getKey(), listener);
            }
        }
    }

    public static void addEventListenet(int id, IEventListener listener) {
        EventsHolder.gHolder().add(id, listener);
    }

    public static void removeEventListenet(int id, IEventListener listener) {
        EventsHolder.gHolder().remove(id, listener);
    }
}

