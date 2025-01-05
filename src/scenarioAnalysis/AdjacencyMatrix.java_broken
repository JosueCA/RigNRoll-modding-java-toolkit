/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import rnrloggers.ScenarioAnalysisLogger;
import scenarioMachine.FiniteState;
import scenarioMachine.FiniteStatesSet;
import scriptActions.ScenarioAnalysisMarkAction;
import scriptActions.ScriptAction;
import scriptActions.SingleStepScenarioAdvanceAction;
import scriptActions.StartScenarioBranchAction;
import scriptActions.StopScenarioBranchAction;
import scriptActions.TimeAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class AdjacencyMatrix
implements Cloneable {
    private static final int DEFAULT_STRING_BUILDER_SIZE = 256;
    private static final int DEFAULT_CAPACITY_SMALL = 8;
    private static final int DEFAULT_CAPACITY_BIG = 64;
    private static Map<String, TransitionAction> classesInterpretedAsTransitionAction = null;
    private static final int EDGE_EXISTS = 1;
    private static final int EDGE_ABSENCES = 0;
    private static final int GRAPH_ROOT_INDEX = 0;
    static final String GRAPH_ROOT_NAME = "ScenarioGraphRoot";
    private int[][] edges = null;
    private List<String> vertexesNames = null;
    private Map<String, Integer> vertexIndexesResolve = null;

    private AdjacencyMatrix(List<String> vertexNamesResolveTable, Map<String, Integer> vertexIndexesResolveTable, int[][] edgesMatrix) {
        if (null == vertexIndexesResolveTable || null == edgesMatrix || 0 >= edgesMatrix.length) {
            throw new IllegalArgumentException("vertexIndexesResolveTable and edgesMatrix must be non-null references and edgesMatrix must have at least one row");
        }
        this.edges = new int[edgesMatrix.length][edgesMatrix[0].length];
        for (int i = 0; i < edgesMatrix.length; ++i) {
            System.arraycopy(edgesMatrix[i], 0, this.edges[i], 0, edgesMatrix[i].length);
        }
        this.vertexIndexesResolve = new HashMap<String, Integer>(vertexIndexesResolveTable);
        this.vertexesNames = new ArrayList<String>(vertexNamesResolveTable);
    }

    private static TransitionAction findTransitionAction(Object action) {
        String name = action.getClass().getName();
        return classesInterpretedAsTransitionAction.get(name);
    }

    private void tryAddEdge(Object actionObjectToInterpret, int sourceIndex, String souceName) {
        if (null == actionObjectToInterpret) {
            throw new IllegalArgumentException("actionObjectToInterpret must be non-null reference");
        }
        if (0 > sourceIndex || sourceIndex >= this.edges.length) {
            throw new IllegalArgumentException("sourceIndex must be non-negative and less than edges.length");
        }
        if (null == souceName) {
            throw new IllegalArgumentException("souceName must be non-null reference");
        }
        TransitionAction pointerContainerToFieldWithDestinationNodeName = AdjacencyMatrix.findTransitionAction(actionObjectToInterpret);
        if (null != pointerContainerToFieldWithDestinationNodeName) {
            String destinationNodeName = pointerContainerToFieldWithDestinationNodeName.getTransitionDestination(actionObjectToInterpret);
            if (pointerContainerToFieldWithDestinationNodeName.hasDestination()) {
                Integer destIndex;
                if (null == destinationNodeName || 0 >= destinationNodeName.length()) {
                    ScenarioAnalysisLogger.getInstance().getLog().warning("can't find destination node: source == " + souceName + "; action == " + actionObjectToInterpret.toString());
                }
                if (null == (destIndex = this.vertexIndexesResolve.get(destinationNodeName))) {
                    destIndex = this.vertexIndexesResolve.get(destinationNodeName + "_phase_" + 1);
                }
                if (null == destIndex) {
                    StringBuilder builder = new StringBuilder(256);
                    builder.append("can't find index of destination node: source == ");
                    builder.append(souceName);
                    builder.append(" destination == ");
                    builder.append(destinationNodeName);
                    builder.append(" data source == ");
                    builder.append(actionObjectToInterpret.toString());
                    ScenarioAnalysisLogger.getInstance().getLog().warning(builder.toString());
                    return;
                }
                if (0 == this.edges[sourceIndex][destIndex]) {
                    this.edges[sourceIndex][destIndex.intValue()] = 1;
                } else {
                    ScenarioAnalysisLogger.getInstance().getLog().warning("multiedge found in scenario graph: source == " + souceName + "; destination == " + destinationNodeName);
                }
            }
        }
    }

    AdjacencyMatrix(FiniteStatesSet scenarioStates, List externalActions) {
        Map<String, FiniteState> statesMap = scenarioStates.getStates();
        Set<Map.Entry<String, FiniteState>> states = statesMap.entrySet();
        this.edges = new int[states.size() + 1][states.size() + 1];
        for (int i = 0; i < states.size() + 1; ++i) {
            Arrays.fill(this.edges[i], 0);
        }
        this.vertexIndexesResolve = new HashMap<String, Integer>(64);
        this.vertexesNames = new ArrayList<String>(64);
        String nodeName = GRAPH_ROOT_NAME;
        this.vertexIndexesResolve.put(nodeName, 0);
        this.vertexesNames.add(nodeName);
        int vertexIndex = 1;
        for (Map.Entry<String, FiniteState> entry : states) {
            nodeName = entry.getKey();
            this.vertexIndexesResolve.put(nodeName, vertexIndex);
            this.vertexesNames.add(nodeName);
            ++vertexIndex;
        }
        if (null != externalActions && !externalActions.isEmpty()) {
            for (Map.Entry<String, FiniteState> externalAction : externalActions) {
                if (null == externalAction) continue;
                this.tryAddEdge(externalAction, 0, "unknown");
            }
        }
        for (Map.Entry<String, FiniteState> entry : states) {
            FiniteState currentNode = entry.getValue();
            int index = this.vertexIndexesResolve.get(currentNode.getName());
            List<ScriptAction> actions = currentNode.getAllAvalibleActions();
            if (null == actions) continue;
            for (ScriptAction internalAction : actions) {
                if (null == internalAction) continue;
                this.tryAddEdge(internalAction, index, currentNode.getName());
            }
        }
    }

    boolean isEdgeExist(int from, int to) {
        if (0 > from || 0 > from || from >= this.edges.length || to >= this.edges[from].length) {
            throw new IllegalArgumentException("indexes are out of bounds");
        }
        return 0 != this.edges[from][to];
    }

    boolean isEdgeExist(String from, String to) {
        Integer fromIndex = this.vertexIndexesResolve.get(from);
        if (null == fromIndex) {
            throw new IllegalArgumentException("vertex with name " + from + "doesn't exist");
        }
        Integer toIndex = this.vertexIndexesResolve.get(to);
        if (null == toIndex) {
            throw new IllegalArgumentException("vertex with name " + to + "doesn't exist");
        }
        return 0 != this.edges[fromIndex][toIndex];
    }

    Collection<String> getGraphVertexesNames() {
        return Collections.unmodifiableSet(this.vertexIndexesResolve.keySet());
    }

    Collection<String> getOutputEdges(String from) {
        Integer fromIndex = this.vertexIndexesResolve.get(from);
        if (null == fromIndex) {
            throw new IllegalArgumentException("vertex with name " + from + "doesn't exist");
        }
        LinkedList<String> out = new LinkedList<String>();
        for (int i = 0; i < this.edges[fromIndex].length; ++i) {
            if (1 != this.edges[fromIndex][i]) continue;
            out.add(this.vertexesNames.get(i));
        }
        return out;
    }

    static {
        try {
            classesInterpretedAsTransitionAction = new HashMap<String, TransitionAction>(8);
            String className = StartScenarioBranchAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getBranchName"));
            className = SingleStepScenarioAdvanceAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = StopScenarioBranchAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = ScenarioAnalysisMarkAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionAction(className, "getDestination"));
            className = TimeAction.class.getName();
            classesInterpretedAsTransitionAction.put(className, new TransitionActionOfAction(className, "getChildAction"));
        }
        catch (ClassNotFoundException e) {
            ScenarioAnalysisLogger.getInstance().getLog().severe(e.getMessage());
        }
        catch (NoSuchMethodException e) {
            ScenarioAnalysisLogger.getInstance().getLog().severe(e.getMessage());
        }
    }

    private static final class TransitionActionOfAction
    extends TransitionAction {
        private boolean m_hasDestination = true;

        TransitionActionOfAction(String className, String getterName) throws ClassNotFoundException, NoSuchMethodException {
            super(className, getterName);
        }

        public boolean hasDestination() {
            return this.m_hasDestination;
        }

        String getTransitionDestination(Object action) {
            try {
                TransitionAction next_transition;
                Object nextaction = this.nameOfNextNodeGetter.invoke(action, new Object[0]);
                if (null != nextaction && (next_transition = AdjacencyMatrix.findTransitionAction(nextaction)) != null) {
                    this.m_hasDestination = next_transition.hasDestination();
                    return next_transition.getTransitionDestination(nextaction);
                }
                this.m_hasDestination = false;
                return null;
            }
            catch (IllegalAccessException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("illegal access: can't extract transition action from " + action.toString());
                return null;
            }
            catch (ClassCastException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("illegal cast to String: can't extract transition action from " + action.toString());
                return null;
            }
            catch (InvocationTargetException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("invocation exception: can't getter call failed on object " + action.toString());
                return null;
            }
        }
    }

    private static class TransitionAction {
        protected Method nameOfNextNodeGetter = null;

        public boolean hasDestination() {
            return true;
        }

        TransitionAction(String className, String getterName) throws ClassNotFoundException, NoSuchMethodException {
            for (Class<?> instanceClass = Class.forName(className); Object.class != instanceClass; instanceClass = instanceClass.getSuperclass()) {
                try {
                    this.nameOfNextNodeGetter = instanceClass.getDeclaredMethod(getterName, new Class[0]);
                    this.nameOfNextNodeGetter.setAccessible(true);
                    return;
                }
                catch (NoSuchMethodException e) {
                    continue;
                }
            }
            throw new NoSuchMethodException("method " + getterName + " wasn't found in " + className + " and all it's superclasses");
        }

        String getTransitionDestination(Object action) {
            try {
                return (String)this.nameOfNextNodeGetter.invoke(action, new Object[0]);
            }
            catch (IllegalAccessException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("illegal access: can't extract transition action from " + action.toString());
                return null;
            }
            catch (ClassCastException exception) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("illegal cast to String: can't extract transition action from " + action.toString());
                return null;
            }
            catch (InvocationTargetException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning("invocation exception: can't getter call failed on object " + action.toString());
                return null;
            }
        }
    }
}

