/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import rnrcore.CoreTime;
import rnrscenario.sctask;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class EndScenario
extends sctask {
    private final Object actionsLatch = new Object();
    private List<DelayedAction> delayedActions = new LinkedList<DelayedAction>();
    private static EndScenario instance = null;

    private EndScenario(int tip) {
        super(tip, false);
        this.start();
    }

    public static EndScenario getInstance() {
        if (null == instance) {
            instance = new EndScenario(3);
        }
        return instance;
    }

    public static void deinit() {
        if (null == instance) {
            return;
        }
        instance.finishImmediately();
        instance = null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        Object object = this.actionsLatch;
        synchronized (object) {
            ListIterator<DelayedAction> iter = this.delayedActions.listIterator();
            while (iter.hasNext()) {
                DelayedAction counter = iter.next();
                if (!counter.to_run()) continue;
                counter.execute();
                iter.remove();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void delayAction(String name, int daysCount, int hoursCount, ScriptEvent event_on_activate, ScriptEvent event_on_remove) {
        Object object = this.actionsLatch;
        synchronized (object) {
            this.delayedActions.add(new DelayedAction(name, daysCount, hoursCount, event_on_activate, event_on_remove));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeTimeQuest(String name) {
        Object object = this.actionsLatch;
        synchronized (object) {
            ListIterator<DelayedAction> iter = this.delayedActions.listIterator();
            while (iter.hasNext()) {
                DelayedAction counter = iter.next();
                if (0 != counter.getName().compareToIgnoreCase(name)) continue;
                counter.on_remove();
                iter.remove();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<DelayedAction> getDelayedActions() {
        Object object = this.actionsLatch;
        synchronized (object) {
            return new ArrayList<DelayedAction>(this.delayedActions);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setDelayedActions(List<DelayedAction> delayedActions) {
        Object object = this.actionsLatch;
        synchronized (object) {
            this.delayedActions = delayedActions;
        }
    }

    public static final class DelayedAction {
        private String name = "no name";
        private CoreTime timeStart = new CoreTime();
        private CoreTime deltatime = null;
        private ScriptEvent event_on_activate = null;
        private ScriptEvent event_on_remove = null;

        public DelayedAction(String name, int days, int hours, ScriptEvent event_on_activate, ScriptEvent event_on_remove) {
            this.event_on_activate = event_on_activate;
            this.event_on_remove = event_on_remove;
            this.name = name;
            this.deltatime = CoreTime.daysNhours(days, hours);
        }

        public boolean to_run() {
            return new CoreTime().moreThanOnTime(this.timeStart, this.deltatime) >= 0;
        }

        public void execute() {
            if (null != this.event_on_activate) {
                EventsController.getInstance().eventHappen(this.event_on_activate);
            }
        }

        public void on_remove() {
            if (null != this.event_on_remove) {
                EventsController.getInstance().eventHappen(this.event_on_remove);
            }
        }

        public CoreTime getTimeDelta() {
            return this.deltatime;
        }

        public void setDeltatime(CoreTime deltatime) {
            this.deltatime = deltatime;
        }

        public ScriptEvent getEvent_on_activate() {
            return this.event_on_activate;
        }

        public void setEvent_on_activate(ScriptEvent event_on_activate) {
            this.event_on_activate = event_on_activate;
        }

        public ScriptEvent getEvent_on_remove() {
            return this.event_on_remove;
        }

        public void setEvent_on_remove(ScriptEvent event_on_remove) {
            this.event_on_remove = event_on_remove;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CoreTime getTimeStart() {
            return this.timeStart;
        }

        public void setTimeStart(CoreTime timeStart) {
            this.timeStart = timeStart;
        }
    }
}

