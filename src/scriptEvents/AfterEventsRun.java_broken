/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import scriptEvents.EventsController;
import scriptEvents.IAfterEventsRun;

public class AfterEventsRun {
    private HashMap<Integer, List<IAfterEventsRun>> runs = new HashMap();
    private int session = 0;
    private static AfterEventsRun instance = null;

    public static AfterEventsRun getInstance() {
        if (null == instance) {
            instance = new AfterEventsRun();
        }
        return instance;
    }

    private AfterEventsRun() {
    }

    public static void deinit() {
        instance = null;
    }

    public void addListener(IAfterEventsRun listener) {
        if (EventsController.getInstance().isBussy()) {
            List<Object> currentSessionList = null;
            if (this.runs.containsKey(this.session)) {
                currentSessionList = this.runs.get(this.session);
            } else {
                currentSessionList = new LinkedList();
                this.runs.put(this.session, currentSessionList);
            }
            currentSessionList.add(listener);
        } else {
            listener.run();
        }
    }

    public void run() {
        ++this.session;
        this.runSession(this.session - 1);
        --this.session;
    }

    private void runSession(int current_session) {
        if (this.runs.containsKey(current_session)) {
            List<IAfterEventsRun> items = this.runs.get(current_session);
            for (IAfterEventsRun listener : items) {
                listener.run();
            }
            items.clear();
        }
    }
}

