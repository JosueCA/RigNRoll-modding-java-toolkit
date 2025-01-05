/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.ArrayList;
import java.util.List;
import scriptEvents.EventChecker;
import scriptEvents.EventsControllerHelper;
import scriptEvents.MessageEvent;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MessageEventChecker
implements EventChecker {
    static final long serialVersionUID = 0L;
    private String text = null;
    private ScriptEvent lastPossetiveChecked = null;

    public MessageEventChecker(String messageToWait) {
        this.text = messageToWait;
    }

    public MessageEventChecker() {
    }

    // @Override
    public void deactivateChecker() {
    }

    // @Override
    public boolean checkEvent(List<ScriptEvent> eventTuple) {
        if (null == this.text) {
            System.err.println("MessageEventChecker wasn't correctly inited: text field is null");
            return false;
        }
        for (ScriptEvent event2 : eventTuple) {
            if (!(event2 instanceof MessageEvent) || 0 != ((MessageEvent)event2).getMessage().compareToIgnoreCase(this.text)) continue;
            this.lastPossetiveChecked = event2;
            return true;
        }
        return false;
    }

    // @Override
    public String isValid() {
        if (null == this.text) {
            return "\"text\" field is null";
        }
        if (!EventsControllerHelper.getInstance().isRegisteredMessageEvent(this.text)) {
            return this.text + " is not a valid message";
        }
        return null;
    }

    // @Override
    public ScriptEvent lastPossetiveChecked() {
        return this.lastPossetiveChecked;
    }

    // @Override
    public List<ScriptEvent> getExpectantEvent() {
        ArrayList<ScriptEvent> out = new ArrayList<ScriptEvent>(1);
        out.add(new MessageEvent(this.text));
        return out;
    }
}

