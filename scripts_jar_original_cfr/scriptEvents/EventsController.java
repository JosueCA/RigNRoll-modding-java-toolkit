/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import scriptEvents.AfterEventsRun;
import scriptEvents.EventListener;
import scriptEvents.ScriptEvent;

public final class EventsController
implements StageChangedListener {
    private static EventsController thisInstance = new EventsController();
    private final List<EventListener> eventListeners = new ArrayList<EventListener>();
    private final List<EventListener> listenersToRemove = new ArrayList<EventListener>();
    private final List<EventListener> listenersToAdd = new ArrayList<EventListener>();
    private final Object synchronizationMonitor = new Object();
    private volatile boolean processingEvents = false;
    private int eventsChainDeep = 0;
    private ScenarioStage checkPointQuery = null;

    private EventsController() {
    }

    public static void deinit() {
        thisInstance = new EventsController();
    }

    public static EventsController getInstance() {
        return thisInstance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addListener(EventListener listener) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            if (null == listener) {
                System.err.println("EventsController.addListener - listener is null: ignored");
            } else if (!this.processingEvents) {
                this.eventListeners.add(listener);
            } else {
                this.listenersToAdd.add(listener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeListener(EventListener listener) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            if (!this.processingEvents) {
                this.eventListeners.remove(listener);
            } else {
                this.listenersToRemove.add(listener);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void eventHappen(ScriptEvent ... eventTuple) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            this.processingEvents = true;
            ++this.eventsChainDeep;
            if (0 < eventTuple.length) {
                List<ScriptEvent> tuple = Arrays.asList(eventTuple);
                for (EventListener listener : this.eventListeners) {
                    listener.eventHappened(tuple);
                }
                if (1 == this.eventsChainDeep) {
                    for (EventListener listener : this.listenersToAdd) {
                        this.eventListeners.add(listener);
                    }
                    this.listenersToAdd.clear();
                    for (EventListener listener : this.listenersToRemove) {
                        this.eventListeners.remove(listener);
                    }
                    this.listenersToRemove.clear();
                }
            }
            --this.eventsChainDeep;
            if (0 == this.eventsChainDeep) {
                this.processingEvents = false;
                if (null != this.checkPointQuery) {
                    ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.eventListeners, this.checkPointQuery);
                    this.checkPointQuery = null;
                }
            }
            if (!this.processingEvents) {
                AfterEventsRun.getInstance().run();
            }
        }
    }

    boolean isBussy() {
        return this.processingEvents;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        Object object = this.synchronizationMonitor;
        synchronized (object) {
            if (this.processingEvents) {
                this.checkPointQuery = scenarioStage;
            } else {
                ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.eventListeners, scenarioStage);
            }
        }
    }
}

