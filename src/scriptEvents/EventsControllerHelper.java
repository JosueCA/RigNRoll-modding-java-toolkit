/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import rnrcore.eng;
import rnrloggers.ScenarioLogger;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import scenarioXml.XmlDocument;
import scriptEvents.EventListener;
import scriptEvents.EventsController;
import scriptEvents.MessageEvent;
import scriptEvents.ScenarioBranchEndEvent;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class EventsControllerHelper
implements EventListener,
StageChangedListener {
    private final Object latch = new Object();
    private boolean processingEvent = false;
    private ScenarioStage stageChangedEventHost = null;
    private final ArrayList<Object> allListenersBuffer = new ArrayList();
    private static final String ROOT_NODE_NAME = "messages";
    private static final String MESSAGE_NODE_TAG_NAME = "msg";
    private static final String IMPLICIT_FILE_TO_LOAD = "..\\Data\\config\\messageEvents.xml";
    private static EventsControllerHelper ourInstance = null;
    private final HashMap<String, ArrayList<MessageEventListener>> listnersTable;
    private final HashSet<String> registeredMessagesForEvents = new HashSet();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    // @Override
    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        if (this.processingEvent) {
            this.stageChangedEventHost = scenarioStage;
        } else {
            Object object = this.latch;
            synchronized (object) {
                for (ArrayList<MessageEventListener> messageEventListeners : this.listnersTable.values()) {
                    for (MessageEventListener listener : messageEventListeners) {
                        this.allListenersBuffer.add(listener.listener);
                    }
                }
                List<Object> garbage = ScenarioGarbageFinder.getOutOfDateScenarioObjects(this.getClass().getName(), this.allListenersBuffer, scenarioStage);
                for (ArrayList<MessageEventListener> messageEventListeners : this.listnersTable.values()) {
                    Iterator<MessageEventListener> listenerIterator = messageEventListeners.iterator();
                    while (listenerIterator.hasNext()) {
                        MessageEventListener messageEventListener = listenerIterator.next();
                        if (!garbage.contains(messageEventListener.listener)) continue;
                        listenerIterator.remove();
                    }
                }
                this.allListenersBuffer.clear();
            }
        }
    }

    private void registerMessageEvent(String messageText) {
        if (null == messageText) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "EventsController.registerMessageEvent - messageText is null: ignored");
            return;
        }
        if (!this.registeredMessagesForEvents.add(messageText)) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "message with text " + messageText + " already exists");
        } else {
            ScenarioLogger.getInstance().machineLog(Level.INFO, "registered messageEvent; text == " + messageText);
        }
    }

    boolean isRegisteredMessageEvent(String messageText) {
        if (null == messageText) {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "EventsController.isRegisteredMessageEvent - messageText is null: ignored");
            return false;
        }
        return this.registeredMessagesForEvents.contains(messageText);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void loadMessageEventsStrings(String path2) {
        Object object = this.latch;
        synchronized (object) {
            try {
                XmlDocument xml = new XmlDocument(path2);
                Collection<String> extracted = xml.extractTagsTextContent(ROOT_NODE_NAME, MESSAGE_NODE_TAG_NAME);
                for (String incomingMsg : extracted) {
                    if (null == incomingMsg || 0 >= incomingMsg.length()) continue;
                    this.registerMessageEvent(incomingMsg);
                }
            }
            catch (IOException e) {
                ScenarioLogger.getInstance().machineLog(Level.WARNING, "failed to load message events from " + path2 + ": " + e.getMessage());
            }
        }
    }

    public static void init() {
        ourInstance = new EventsControllerHelper();
    }

    public static void deinit() {
        ourInstance = null;
    }

    public static EventsControllerHelper getInstance() {
        return ourInstance;
    }

    private EventsControllerHelper() {
        this.listnersTable = new HashMap();
        EventsController.getInstance().addListener(this);
        if (!eng.noNative) {
            this.loadMessageEventsStrings(IMPLICIT_FILE_TO_LOAD);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    // @Override
    public void eventHappened(List<ScriptEvent> eventTuple) {
        this.processingEvent = true;
        try {
            for (ScriptEvent event2 : eventTuple) {
                if (!(event2 instanceof MessageEvent)) continue;
                Object object = this.latch;
                synchronized (object) {
                    ArrayList<MessageEventListener> listToCopy = this.listnersTable.get(((MessageEvent)event2).getMessage());
                    if (null != listToCopy) {
                        LinkedList<MessageEventListener> listenersOfArgumentMessage = new LinkedList<MessageEventListener>(listToCopy);
                        for (MessageEventListener listener : listenersOfArgumentMessage) {
                            try {
                                listener.react();
                            }
                            catch (IllegalAccessException exception) {
                                ScenarioLogger.getInstance().machineWarning(exception.getMessage());
                            }
                            catch (InvocationTargetException exception) {
                                ScenarioLogger.getInstance().machineWarning(exception.getMessage());
                            }
                        }
                    }
                }
            }
        }
        finally {
            this.processingEvent = false;
        }
        if (null != this.stageChangedEventHost) {
            ScenarioGarbageFinder.deleteOutOfDateScenarioObjects(this.getClass().getName(), this.listnersTable.entrySet(), this.stageChangedEventHost);
            this.stageChangedEventHost = null;
        }
    }

    public static void messageEventHappened(String what) {
        EventsController.getInstance().eventHappen(new MessageEvent(what));
    }

    public static void eventHappened(ScriptEvent event2) {
        EventsController.getInstance().eventHappen(event2);
    }

    public static void endScenarioBranch(String name) {
        EventsController.getInstance().eventHappen(new ScenarioBranchEndEvent(name + "(" + "_phase_" + "\\d+)?"));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addMessageListener(Object listener, String methodToInvoke, String messageToWait) {
        Object object = this.latch;
        synchronized (object) {
            ArrayList<MessageEventListener> listenersOfArgumentMessage;
            if (!this.isRegisteredMessageEvent(messageToWait)) {
                ScenarioLogger.getInstance().machineLog(Level.WARNING, "trying to add listener on unregistered message == " + messageToWait);
            }
            if (null == (listenersOfArgumentMessage = this.listnersTable.get(messageToWait))) {
                listenersOfArgumentMessage = new ArrayList();
                this.listnersTable.put(messageToWait, listenersOfArgumentMessage);
            }
            try {
                listenersOfArgumentMessage.add(new MessageEventListener(listener, methodToInvoke));
                ScenarioLogger.getInstance().machineLog(Level.INFO, "added text messages listener: " + listener.getClass().toString() + "; method to invoke: " + methodToInvoke + "; message to wait: " + messageToWait);
            }
            catch (NoSuchMethodException exception) {
                ScenarioLogger.getInstance().machineWarning(exception.getMessage());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeMessageListener(Object listener, String methodToInvoke, String messageToWait) {
        Object object = this.latch;
        synchronized (object) {
            ArrayList<MessageEventListener> listenersOfArgumentMessage = this.listnersTable.get(messageToWait);
            if (null != listenersOfArgumentMessage) {
                Iterator<MessageEventListener> eventListenerIterator = listenersOfArgumentMessage.iterator();
                while (eventListenerIterator.hasNext()) {
                    MessageEventListener eventListener = eventListenerIterator.next();
                    if (!eventListener.isSameEvent(listener, methodToInvoke)) continue;
                    eventListenerIterator.remove();
                    return;
                }
            }
        }
    }

    public void uploadMessageEventsToRegister(String filePath) {
        if (null == filePath) {
            throw new IllegalArgumentException("filePath must be non-null reference");
        }
        this.loadMessageEventsStrings(filePath);
    }

    public Set<String> getAvalibleMessageEvents() {
        return Collections.unmodifiableSet(this.registeredMessagesForEvents);
    }

    private static final class MessageEventListener {
        private Object listener = null;
        private Method methodToCall = null;

        MessageEventListener(Object listener, String methodToCallName) throws NoSuchMethodException {
            if (null == listener || null == methodToCallName || 0 >= methodToCallName.length()) {
                return;
            }
            this.listener = listener;
            this.methodToCall = listener.getClass().getDeclaredMethod(methodToCallName, new Class[0]);
            this.methodToCall.setAccessible(true);
        }

        boolean isSameEvent(Object listener, String methodToCallName) {
            return this.listener.equals(listener) && this.methodToCall.getName().compareTo(methodToCallName) == 0;
        }

        void react() throws IllegalAccessException, InvocationTargetException {
            this.methodToCall.invoke(this.listener, new Object[0]);
        }
    }
}

