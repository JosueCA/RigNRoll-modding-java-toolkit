/*
 * Decompiled with CFR 0.151.
 */
package scenarioMachine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteState;
import scenarioMachine.FiniteStatesSet;
import scenarioUtils.Pair;
import scenarioXml.XmlFilter;
import scenarioXml.XmlNodeDataProcessor;
import scriptEvents.EventListener;
import scriptEvents.ScenarioBranchEndEvent;
import scriptEvents.ScriptEvent;
import xmlserialization.Helper;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FiniteStateMachine
implements EventListener,
XmlNodeDataProcessor,
XmlSerializable {
    private static final String STATE_NODE_NAME = "state";
    private static final String FSM_STATE_NODE_NAME = "fsmState";
    private static final String REACTED_NODE_NAME = "reacted";
    private static final String UID_ATTRIBUTE = "uid";
    private static final String NAME_ATTRIBUTE = "name";
    private static final int MAX_WALK_THROUGH_DEEP = 128;
    private static final int DEFAULT_CURRENT_STATES_STORAGE_CAPACITY = 8;
    private final LinkedHashMap<String, Pair<Boolean, FiniteState>> currentActiveStates = new LinkedHashMap(8);
    private FiniteStatesSet statesPool = null;
    private static final int ARRAY_CAPASITY = 32;
    private final List<FiniteState> recentlyEntered = new ArrayList<FiniteState>(32);
    private final List<FiniteState> recentlyLeaved = new ArrayList<FiniteState>(32);
    private final Set<String> toWalkThrough = new HashSet<String>();
    private final List<String[]> toActivate = new LinkedList<String[]>();
    private final Set<String> toDeactivate = new HashSet<String>();
    private ChangesContainer changesLog = null;
    private int eventsDeep = 0;

    public void clear() {
        this.currentActiveStates.clear();
        this.recentlyEntered.clear();
        this.recentlyLeaved.clear();
        this.toWalkThrough.clear();
        this.toActivate.clear();
        this.toDeactivate.clear();
        this.eventsDeep = 0;
        this.statesPool = null;
    }

    public FiniteStateMachine() {
        this(false);
    }

    public FiniteStateMachine(boolean recordChanges) {
        this.statesPool = new FiniteStatesSet();
        if (recordChanges) {
            this.changesLog = new ChangesContainer();
        }
    }

    private static void printErrorMessage(String error) {
        StackTraceElement[] stackTrace;
        ScenarioLogger.getInstance().machineWarning(error);
        ScenarioLogger.getInstance().machineLog(Level.FINEST, "STACK TRACE:");
        for (StackTraceElement callNode : stackTrace = Thread.currentThread().getStackTrace()) {
            ScenarioLogger.getInstance().machineLog(Level.FINEST, callNode.toString());
        }
    }

    private boolean activateState(FiniteState state) {
        if (null != state) {
            if (null != this.changesLog) {
                this.changesLog.addScenarioBranchStart(state.getName());
            }
            this.currentActiveStates.put(state.getName(), new Pair<Boolean, FiniteState>(false, state));
            state.entered();
            ScenarioLogger.getInstance().machineLog(Level.INFO, "activated state " + state.getName());
            return true;
        }
        return false;
    }

    private void walkThroughToEnd(String startOfWayName) {
        FiniteState startOfWay;
        Pair pair = (Pair)this.currentActiveStates.remove(startOfWayName);
        if (null != pair) {
            int iterationCount = 0;
            startOfWay = (FiniteState)pair.getSecond();
            startOfWay.leaved();
            while (null != startOfWay.getNextStates() && !startOfWay.getNextStates().isEmpty()) {
                startOfWay = startOfWay.getNextStates().getFirst();
                if (128 >= ++iterationCount) continue;
                ScenarioLogger.getInstance().machineLog(Level.SEVERE, "failed to find end of way == " + startOfWayName + ": way is too long, check FSM or increase max way lenght");
                this.currentActiveStates.put(((FiniteState)pair.getSecond()).getName(), pair);
                return;
            }
        } else {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "failed to find start of way == " + startOfWayName + ": walkThroghToEnd call failed");
            return;
        }
        this.currentActiveStates.put(startOfWay.getName(), new Pair<Boolean, FiniteState>(false, startOfWay));
        startOfWay.entered();
        ScenarioLogger.getInstance().machineLog(Level.INFO, startOfWayName + " walked through to the end");
        if (null != this.changesLog) {
            this.changesLog.addScenarioStateClosed(((FiniteState)pair.getSecond()).getName());
        }
    }

    private void deactivateAllStatesInQueue() {
        for (String stateName : this.toDeactivate) {
            Pair removed = (Pair)this.currentActiveStates.remove(stateName);
            if (null != removed) {
                ScenarioLogger.getInstance().machineLog(Level.INFO, stateName + " deactivated");
                if (null != this.changesLog) {
                    this.changesLog.addScenarioStateClosed(((FiniteState)removed.getSecond()).getName());
                }
                ((FiniteState)removed.getSecond()).leaved();
                continue;
            }
            FiniteStateMachine.printErrorMessage("ScenarioMachine internal error: trying to deactivate unexsisting state == " + stateName);
        }
        this.toDeactivate.clear();
    }

    private void activateAllStatesInQueue() {
        Iterator<String[]> posibleNamesIterator = this.toActivate.iterator();
        block0: while (posibleNamesIterator.hasNext()) {
            String[] posibleNames = posibleNamesIterator.next();
            posibleNamesIterator.remove();
            for (String stateName : posibleNames) {
                FiniteState found = this.statesPool.findState(stateName);
                if (null == found) continue;
                this.activateState(found);
                continue block0;
            }
        }
    }

    public final void setStatesPool(FiniteStatesSet statesPool) {
        if (null == statesPool) {
            throw new IllegalArgumentException("stateToPool must be non-null reference");
        }
        this.statesPool = statesPool;
    }

    public final void activateState(boolean immediately, String ... stateNames) {
        if (immediately) {
            for (String stateName : stateNames) {
                if (!this.activateState(this.statesPool.findState(stateName))) continue;
                return;
            }
        } else {
            this.toActivate.add(stateNames);
        }
    }

    public final void activateState(String ... stateNames) {
        this.activateState(0 == this.eventsDeep, stateNames);
    }

    public final String findEndNode(String startOfWayName) {
        if (null == startOfWayName) {
            return null;
        }
        FiniteState startOfWay = this.statesPool.getStates().get(startOfWayName);
        if (null != startOfWay) {
            while (null != startOfWay.getNextStates() && !startOfWay.getNextStates().isEmpty()) {
                startOfWay = startOfWay.getNextStates().getFirst();
            }
        } else {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "failed to find start of way == " + startOfWayName + ": findEndNode call failed");
            return null;
        }
        return startOfWay.getName();
    }

    public final void addNodeToWalkFromToEnd(String startOfWayName) {
        if (null != startOfWayName) {
            this.toWalkThrough.add(startOfWayName);
        }
    }

    public final void deactivateState(String stateName) {
        if (null != stateName) {
            this.toDeactivate.add(stateName);
        }
    }

    public final int getCurrentActiveStatesCount() {
        return this.currentActiveStates.size();
    }

    public final Collection<String> getCurrentActiveStates() {
        return Collections.unmodifiableSet(this.currentActiveStates.keySet());
    }

    public final Set<String> getStatesNames() {
        return Collections.unmodifiableSet(this.statesPool.getStates().keySet());
    }

    @Override
    public final void eventHappened(List<ScriptEvent> event2) {
        ScenarioLogger.getInstance().machineLog(Level.FINEST, "BEGINING PROCESSING OF EVENT TUPLE");
        ++this.eventsDeep;
        Iterator<Object> iter = event2.listIterator();
        while (iter.hasNext()) {
            ScriptEvent scriptEvent = iter.next();
            if (!(scriptEvent instanceof ScenarioBranchEndEvent)) continue;
            Pattern nodeToDeactivateNamePattern = ((ScenarioBranchEndEvent)scriptEvent).getNodeNamePattern();
            for (Map.Entry<String, Pair<Boolean, FiniteState>> entry : this.currentActiveStates.entrySet()) {
                if (!nodeToDeactivateNamePattern.matcher(entry.getKey()).matches()) continue;
                this.toWalkThrough.add(entry.getKey());
            }
        }
        for (Map.Entry<String, Pair<Boolean, FiniteState>> entry : this.currentActiveStates.entrySet()) {
            if (entry.getValue().getFirst().booleanValue()) continue;
            FiniteState currentState = entry.getValue().getSecond();
            ScenarioLogger.getInstance().machineLog(Level.FINEST, "processing event tuple on " + currentState.getName());
            FiniteState newState = currentState.process(event2);
            if (currentState == newState || this.toWalkThrough.contains(currentState.getName()) || this.toDeactivate.contains(currentState.getName())) continue;
            if (null != this.changesLog) {
                this.changesLog.addStep(currentState.getName(), newState.getName());
            }
            ScenarioLogger.getInstance().machineLog(Level.FINEST, "deleted node from active states; name == " + currentState.getName());
            entry.getValue().setFirst(true);
            this.recentlyEntered.add(newState);
            this.recentlyLeaved.add(currentState);
        }
        --this.eventsDeep;
        if (0 == this.eventsDeep) {
            iter = this.currentActiveStates.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry)iter.next();
                if (!((Boolean)((Pair)entry.getValue()).getFirst()).booleanValue()) continue;
                iter.remove();
            }
            for (FiniteState finiteState : this.recentlyEntered) {
                this.currentActiveStates.put(finiteState.getName(), new Pair<Boolean, FiniteState>(false, finiteState));
                ScenarioLogger.getInstance().machineLog(Level.FINEST, finiteState.getName() + " added to active states");
            }
            iter = this.recentlyEntered.listIterator();
            while (iter.hasNext()) {
                FiniteState finiteState = (FiniteState)iter.next();
                iter.remove();
                finiteState.entered();
            }
            iter = this.recentlyLeaved.listIterator();
            while (iter.hasNext()) {
                FiniteState finiteState = (FiniteState)iter.next();
                iter.remove();
                finiteState.leaved();
            }
            while (!this.toWalkThrough.isEmpty()) {
                String toRemoveThroughMoving = this.toWalkThrough.iterator().next();
                this.toWalkThrough.remove(toRemoveThroughMoving);
                this.walkThroughToEnd(toRemoveThroughMoving);
            }
            this.toWalkThrough.clear();
            this.deactivateAllStatesInQueue();
            this.activateAllStatesInQueue();
        }
        ScenarioLogger.getInstance().machineLog(Level.FINEST, "END OF PROCESSING OF EVENT TUPLE");
    }

    public final void clearActiveStates() {
        this.currentActiveStates.clear();
    }

    private void loadCurrentFSMStateFromStringArray(Collection<Pair<String, List<Integer>>> array) {
        if (null == array) {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "array is null");
            throw new IllegalArgumentException("'array' is null");
        }
        this.currentActiveStates.clear();
        for (Pair<String, List<Integer>> nodeToActivate : array) {
            FiniteState node = this.statesPool.getStates().get(nodeToActivate.getFirst());
            if (null != node) {
                this.currentActiveStates.put(nodeToActivate.getFirst(), new Pair<Boolean, FiniteState>(false, node));
                node.restoreState(nodeToActivate.getSecond());
                continue;
            }
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "node \"" + nodeToActivate + "\" wasn't found in pool: restored FSM can be uncorrect");
        }
    }

    @Override
    public void process(Node target, Object param) {
        if (null == target || null == param) {
            return;
        }
        try {
            Collection statesToRestore = (Collection)param;
            String recordFromXML = target.getAttributes().getNamedItem(NAME_ATTRIBUTE).getTextContent();
            LinkedList<Integer> doneReactions = new LinkedList<Integer>();
            XmlFilter searcher = new XmlFilter(target.getChildNodes());
            Node reacted = searcher.nodeNameNext(REACTED_NODE_NAME);
            while (null != reacted) {
                try {
                    doneReactions.add(new Integer(reacted.getAttributes().getNamedItem(UID_ATTRIBUTE).getTextContent()));
                }
                catch (NumberFormatException e) {
                    ScenarioLogger.getInstance().machineLog(Level.SEVERE, "invalid value came from save file: bad reaction uid; error message == " + e.getMessage());
                }
                reacted = searcher.nodeNameNext(REACTED_NODE_NAME);
            }
            statesToRestore.add(new Pair(recordFromXML, doneReactions));
        }
        catch (ClassCastException e) {
            ScenarioLogger.getInstance().machineLog(Level.SEVERE, "invalid param to \"process\" method");
        }
    }

    @Override
    public void loadFromNode(Node source) {
        if (null == source) {
            throw new IllegalArgumentException("source must be non-null reference");
        }
        LinkedList<Pair<String, List<Integer>>> statesToRestore = new LinkedList<Pair<String, List<Integer>>>();
        XmlFilter filter = new XmlFilter(source.getChildNodes());
        filter.visitAllNodes(STATE_NODE_NAME, this, statesToRestore);
        this.loadCurrentFSMStateFromStringArray(statesToRestore);
    }

    @Override
    public void yourNodeWasNotFound() {
    }

    @Override
    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        if (null == stream) {
            throw new IllegalArgumentException("stream must be non-null");
        }
        Helper.openNode(stream, FSM_STATE_NODE_NAME);
        for (Pair<Boolean, FiniteState> activeState : this.currentActiveStates.values()) {
            FiniteState state = activeState.getSecond();
            List<Integer> dataToSave = state.getDataToSave();
            if (!dataToSave.isEmpty()) {
                Helper.printOpenNodeWithAttributes(stream, STATE_NODE_NAME, Helper.createSingleAttribute(NAME_ATTRIBUTE, activeState.getSecond().getName()));
                for (Integer valueToSave : dataToSave) {
                    Helper.printClosedNodeWithAttributes(stream, REACTED_NODE_NAME, Helper.createSingleAttribute(UID_ATTRIBUTE, valueToSave.toString()));
                }
                Helper.closeNode(stream, STATE_NODE_NAME);
                continue;
            }
            Helper.printClosedNodeWithAttributes(stream, STATE_NODE_NAME, Helper.createSingleAttribute(NAME_ATTRIBUTE, activeState.getSecond().getName()));
        }
        Helper.closeNode(stream, FSM_STATE_NODE_NAME);
    }

    @Override
    public String getRootNodeName() {
        return FSM_STATE_NODE_NAME;
    }

    public List<List<ScriptEvent>> getExpectedEvents() {
        LinkedList<List<ScriptEvent>> out = new LinkedList<List<ScriptEvent>>();
        for (Map.Entry<String, Pair<Boolean, FiniteState>> entry : this.currentActiveStates.entrySet()) {
            List<List<ScriptEvent>> listOfExpected = entry.getValue().getSecond().getExpectedEvents();
            if (null == listOfExpected || listOfExpected.isEmpty()) continue;
            out.addAll(listOfExpected);
        }
        return out;
    }

    public List<String> getRecentlyStarted() {
        if (null == this.changesLog) {
            throw new UnsupportedOperationException("machine was started without enabling of log");
        }
        return Collections.unmodifiableList(this.changesLog.getStarted());
    }

    public List<String> getRecentlyClosed() {
        if (null == this.changesLog) {
            throw new UnsupportedOperationException("machine was started without enabling of log");
        }
        return Collections.unmodifiableList(this.changesLog.getClosed());
    }

    public List<Pair<String, String>> getRecentlySteps() {
        if (null == this.changesLog) {
            throw new UnsupportedOperationException("machine was started without enabling of log");
        }
        return Collections.unmodifiableList(this.changesLog.getSteps());
    }

    public void clearLog() {
        if (null == this.changesLog) {
            throw new UnsupportedOperationException("machine was started without enabling of log");
        }
        this.changesLog.clearAll();
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static final class ChangesContainer {
        private List<String> branchesStarted = new LinkedList<String>();
        private List<String> branchesClosed = new LinkedList<String>();
        private List<Pair<String, String>> steps = new LinkedList<Pair<String, String>>();

        private ChangesContainer() {
        }

        void addStep(String from, String to) {
            if (null == from || null == to) {
                throw new IllegalArgumentException("all parameters must be non-null");
            }
            this.steps.add(new Pair<String, String>(from, to));
        }

        void addScenarioBranchStart(String started) {
            if (null == started) {
                throw new IllegalArgumentException("started must be non-null reference");
            }
            this.branchesStarted.add(started);
        }

        void addScenarioStateClosed(String closed) {
            if (null == closed) {
                throw new IllegalArgumentException("started must be non-null reference");
            }
            this.branchesClosed.add(closed);
        }

        void clearAll() {
            this.branchesStarted.clear();
            this.branchesClosed.clear();
            this.steps.clear();
        }

        List<String> getStarted() {
            return Collections.unmodifiableList(this.branchesStarted);
        }

        List<String> getClosed() {
            return Collections.unmodifiableList(this.branchesClosed);
        }

        List<Pair<String, String>> getSteps() {
            return Collections.unmodifiableList(this.steps);
        }
    }
}

