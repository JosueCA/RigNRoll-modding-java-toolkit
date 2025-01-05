/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import rnrcore.Helper;
import rnrcore.INativeMessageEvent;
import scriptEvents.EventChecker;
import scriptEvents.EventsControllerHelper;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class NativeMessageListener
implements EventChecker,
INativeMessageEvent {
    static final long serialVersionUID = 0L;
    private boolean to_remove_event = true;
    private ScriptEvent lastSuccessfullyChecked = null;
    public String text = "";

    public void prepare() {
        Helper.addNativeEventListener(this);
    }

    @Override
    public void deactivateChecker() {
        if (this.to_remove_event) {
            Helper.removeNativeEventListener(this);
        }
    }

    @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.text) {
            System.err.println("NativeMessageListener wasn't correctly inited: text field is null");
            return false;
        }
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof Event) || 0 != ((Event)event2).text.compareToIgnoreCase(this.text)) continue;
            this.lastSuccessfullyChecked = event2;
            return true;
        }
        return false;
    }

    @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastSuccessfullyChecked;
    }

    @Override
    public List<ScriptEvent> getExpectantEvent() {
        ArrayList<ScriptEvent> lst = new ArrayList<ScriptEvent>();
        lst.add(new Event(this.text));
        return lst;
    }

    @Override
    public String isValid() {
        return null;
    }

    @Override
    public String getMessage() {
        return this.text;
    }

    @Override
    public void onEvent(String message) {
        this.to_remove_event = false;
        EventsControllerHelper.eventHappened(new Event(this.text));
        EventsControllerHelper.messageEventHappened(this.text);
    }

    @Override
    public boolean removeOnEvent() {
        return true;
    }

    static class Event
    implements ScriptEvent {
        static final long serialVersionUID = 0L;
        String text;

        Event(String text) {
            this.text = text;
        }
    }
}

