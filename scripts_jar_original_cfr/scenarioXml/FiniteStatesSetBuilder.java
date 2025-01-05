/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;
import rnrcore.CoreTime;
import rnrloggers.ScenarioLogger;
import rnrorg.Organizers;
import rnrscenario.scenarioscript;
import scenarioMachine.FiniteState;
import scenarioMachine.FiniteStateMachine;
import scenarioMachine.FiniteStatesSet;
import scenarioMachine.FsmActionAdapter;
import scenarioUtils.AdvancedClass;
import scenarioXml.IsoCondition;
import scenarioXml.IsoQuest;
import scenarioXml.IsoQuestItemTask;
import scenarioXml.ObjectProperties;
import scenarioXml.PhasedQuest;
import scenarioXml.QuestPhase;
import scenarioXml.XmlStrings;
import scriptActions.MissionPointMarkAction;
import scriptActions.MissionPointUnmarkAction;
import scriptActions.ScriptAction;
import scriptActions.SingleStepScenarioAdvanceAction;
import scriptActions.StartOrgAction;
import scriptActions.StartScenarioBranchAction;
import scriptActions.StopScenarioBranchAction;
import scriptEvents.AndEventChecker;
import scriptEvents.ComplexEventReaction;
import scriptEvents.EventChecker;
import scriptEvents.EventReaction;
import scriptEvents.ExactEventChecker;
import scriptEvents.IsoQuestEmulationStateProcessor;
import scriptEvents.NativeMessageListener;
import scriptEvents.OrEventChecker;
import scriptEvents.PhasedQuestEmulationStateProcessor;
import scriptEvents.ScenarioStateMoveEventChecker;
import scriptEvents.ScriptEvent;
import scriptEvents.SimpleEventReaction;
import scriptEvents.SpecialObjectEvent;
import scriptEvents.SpecialObjectEventTypeChecker;
import scriptEvents.UniversalStateProcessor;
import scriptEvents.VoidEvent;
import scriptEvents.VoidEventChecker;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class FiniteStatesSetBuilder {
    private final LinkedList<FiniteState> states = new LinkedList();
    public static final String SCENARIO_STATE_NAME_SUFFIX = "_phase_";
    public static final String SCENARIO_VIRTUAL_STATE_SUFFIX = "_virtual_";

    private static void createSimpleAdvanceScheme(List<String> nextStateNames, UniversalStateProcessor stateProcessor) {
        if (null == nextStateNames || null == stateProcessor) {
            throw new IllegalArgumentException("nextStateNames and stateProcessor must be non-null references");
        }
        for (String avalibleState : nextStateNames) {
            SimpleEventReaction reactor = new SimpleEventReaction(null, new ScenarioStateMoveEventChecker(avalibleState), 0);
            stateProcessor.addReaction(reactor);
        }
    }

    static ScriptAction actionFromStringParams(String name, Properties params2, String substituteInsteadThis, FiniteStateMachine paramToConstructors) throws ActionConstructionException {
        if (null == name || null == params2 || null == substituteInsteadThis) {
            throw new IllegalArgumentException("actionFromStringParams: all parameters must be non-null references");
        }
        try {
            AdvancedClass scriptActionClass = new AdvancedClass(name, "scriptActions");
            Constructor[] constructorsArray = scriptActionClass.getAllConstructors();
            Object constructed = null;
            block17: for (Constructor creator : constructorsArray) {
                creator.setAccessible(true);
                switch (creator.getParameterTypes().length) {
                    case 0: {
                        constructed = creator.newInstance(new Object[0]);
                        break block17;
                    }
                    case 1: {
                        Class<?> constructorParameterType = creator.getParameterTypes()[0];
                        if (scenarioscript.class == constructorParameterType) {
                            constructed = creator.newInstance(scenarioscript.script);
                            break block17;
                        }
                        if (FiniteStateMachine.class == constructorParameterType) {
                            constructed = creator.newInstance(paramToConstructors);
                            break block17;
                        }
                    }
                    default: {
                        continue block17;
                    }
                }
            }
            if (null == constructed) {
                throw new ActionConstructionException("failed to create action. couldn't find valid constructor with no or one String parameter");
            }
            Set<Map.Entry<Object, Object>> entries = params2.entrySet();
            Iterator<Map.Entry<Object, Object>> i$ = entries.iterator();
            while (i$.hasNext()) {
                Map.Entry<Object, Object> newVar;
                Map.Entry<Object, Object> entry = newVar = i$.next();
                String fieldName = (String)entry.getKey();
                String fieldValue = (String)entry.getValue();
                Field field = scriptActionClass.findFieldInHierarchy(fieldName);
                try {
                    field.setAccessible(true);
                    if (Integer.TYPE == field.getType()) {
                        try {
                            field.setInt(constructed, Integer.parseInt(fieldValue));
                        }
                        catch (NumberFormatException exception) {
                            ScenarioLogger.getInstance().parserWarning("invalid int param " + fieldName + " == " + fieldValue);
                        }
                        continue;
                    }
                    if (Boolean.TYPE == field.getType()) {
                        field.setBoolean(constructed, Boolean.parseBoolean(fieldValue));
                        continue;
                    }
                    if (0 == "this".compareTo(fieldValue)) {
                        field.set(constructed, substituteInsteadThis);
                        continue;
                    }
                    field.set(constructed, fieldValue);
                }
                catch (IllegalArgumentException exception) {
                    ScenarioLogger.getInstance().parserWarning("invalid param " + fieldName + " == " + params2.getProperty(fieldName) + "; object field is not a String");
                }
                catch (IllegalAccessException exception) {
                    ScenarioLogger.getInstance().parserWarning("access denied to " + fieldName + " == " + params2.getProperty(fieldName));
                }
            }
            if (!((ScriptAction)constructed).validate()) {
                ScenarioLogger.getInstance().parserLog(Level.WARNING, "created instance of " + name + " is invalid");
            }
            return constructed;
        }
        catch (InstantiationException exception) {
            FiniteStatesSetBuilder.processException("failed to create action: trying to create abstract class", exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (IllegalAccessException exception) {
            FiniteStatesSetBuilder.processException("failed to create action " + name + ": illegal access error", exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (InvocationTargetException exception) {
            FiniteStatesSetBuilder.processException("failed to create action " + name + ": construction error", exception.getTargetException());
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (ClassNotFoundException exception) {
            FiniteStatesSetBuilder.processException("failed to create action " + name + ":  specified class wasn't found", exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (NoSuchFieldException exception) {
            FiniteStatesSetBuilder.processException("data came from xml for action " + name + ": invalid property name", exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (NullPointerException exception) {
            FiniteStatesSetBuilder.processException("something big and bad happened while creatring action " + name, exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
        catch (ClassCastException exception) {
            FiniteStatesSetBuilder.processException("illegal class of action " + name + "; not child of ScriptAction", exception);
            throw new ActionConstructionException("failed to construct " + name);
        }
    }

    public static ArrayList<ScriptAction> actionsFromActionList(List<ObjectProperties> actionList, String substituteInsteadThis, FiniteStateMachine paramToConstructors) {
        if (null == actionList || actionList.isEmpty()) {
            return new ArrayList<ScriptAction>(0);
        }
        ArrayList<ScriptAction> actionSet = new ArrayList<ScriptAction>(actionList.size());
        block4: for (ObjectProperties action : actionList) {
            ScenarioLogger.getInstance().parserLog(Level.INFO, "creating ScriptAction for action xml action \"" + action.getName() + '\"');
            try {
                if (null != action.getChildObject()) {
                    Field[] slaveFields;
                    String masterActionName = XmlStrings.resolveName(action.getName());
                    ScriptAction master = FiniteStatesSetBuilder.actionFromStringParams(masterActionName, action.getParams(), substituteInsteadThis, paramToConstructors);
                    String slaveActionName = XmlStrings.resolveName(action.getChildObject().getName());
                    ScriptAction slave = FiniteStatesSetBuilder.actionFromStringParams(slaveActionName, action.getChildObject().getParams(), substituteInsteadThis, paramToConstructors);
                    for (Field field : slaveFields = slave.getClass().getFields()) {
                        if (!field.getType().isInstance(master)) continue;
                        field.setAccessible(true);
                        field.set(slave, master);
                        actionSet.add(slave);
                        continue block4;
                    }
                    continue;
                }
                String resolved = XmlStrings.resolveName(action.getName());
                if (null != resolved) {
                    actionSet.add(FiniteStatesSetBuilder.actionFromStringParams(resolved, action.getParams(), substituteInsteadThis, paramToConstructors));
                    continue;
                }
                ScenarioLogger.getInstance().parserLog(Level.SEVERE, "failed to create realization of ScripAction class: can't find class for " + action.getName() + " action");
            }
            catch (ActionConstructionException exception) {
                ScenarioLogger.getInstance().parserLog(Level.SEVERE, exception.getMessage());
            }
            catch (IllegalAccessException exception) {
                System.err.println(exception.getLocalizedMessage());
                exception.printStackTrace(System.err);
                ScenarioLogger.getInstance().parserError("failed to instanciate hierarhical action: access to field denided");
            }
            catch (IllegalArgumentException exception) {
                ScenarioLogger.getInstance().parserError("probably unresolved action name came from XML");
                System.err.println(exception.getLocalizedMessage());
                exception.printStackTrace(System.err);
            }
        }
        return actionSet;
    }

    private static void processException(String message, Throwable exception) {
        if (null != message) {
            ScenarioLogger.getInstance().parserWarning(message);
        }
        if (null != exception && null != exception.getMessage()) {
            ScenarioLogger.getInstance().parserWarning(exception.getMessage());
        }
    }

    private static void addTransition(FiniteState transitionSource, List<FiniteState> transitionDestination) {
        if (null == transitionSource || null == transitionDestination) {
            throw new IllegalArgumentException("transitionSource and transitionDestination must be non-null references");
        }
        for (FiniteState destinationNode : transitionDestination) {
            transitionSource.addNextState(destinationNode);
        }
    }

    public static EventChecker buildEventChecker(IsoCondition condition, SpecialObjectEvent.EventType soEventTypeExternalParamToConstructor) {
        if (null == condition) {
            return null;
        }
        if (null != condition.getReadyCondition()) {
            return condition.getReadyCondition();
        }
        String checkerClassName = condition.getProperties().getName();
        ScenarioLogger.getInstance().parserLog(Level.INFO, "creating EventChecker for condition \"" + checkerClassName + '\"');
        try {
            String errorString;
            String resolvedClassName = XmlStrings.resolveName(checkerClassName);
            if (null == resolvedClassName) {
                ScenarioLogger.getInstance().parserWarning("unresolvet action class name " + checkerClassName);
                return null;
            }
            AdvancedClass checkerClass = new AdvancedClass(resolvedClassName, "scriptEvents");
            Constructor[] avalibleConstructors = checkerClass.getAllConstructors();
            EventChecker ware = null;
            block17: for (Constructor creator : avalibleConstructors) {
                switch (creator.getParameterTypes().length) {
                    case 0: {
                        creator.setAccessible(true);
                        ware = (EventChecker)creator.newInstance(new Object[0]);
                        break block17;
                    }
                    case 1: {
                        if (SpecialObjectEvent.EventType.class == creator.getParameterTypes()[0]) {
                            creator.setAccessible(true);
                            ware = (EventChecker)creator.newInstance(new Object[]{soEventTypeExternalParamToConstructor});
                            break block17;
                        }
                    }
                    default: {
                        continue block17;
                    }
                }
            }
            if (null == ware) {
                ScenarioLogger.getInstance().parserWarning("couldn't find appropriate constructor for" + resolvedClassName);
                return null;
            }
            Properties fields = condition.getProperties().getParams();
            for (Map.Entry<Object, Object> entry : fields.entrySet()) {
                String fieldName = (String)entry.getKey();
                String fieldValue = (String)entry.getValue();
                try {
                    Field field = checkerClass.findFieldInHierarchy(fieldName);
                    field.setAccessible(true);
                    if (Integer.TYPE == field.getType()) {
                        field.setInt(ware, Integer.parseInt(fieldValue));
                        continue;
                    }
                    if (CoreTime.class == field.getType()) {
                        field.set(ware, new CoreTime(0, 0, 0, Integer.parseInt(fieldValue), 0));
                        continue;
                    }
                    if (String.class == field.getType()) {
                        field.set(ware, fieldValue);
                        continue;
                    }
                    if (SpecialObjectEvent.EventType.class == field.getType()) {
                        try {
                            field.set(ware, (Object)soEventTypeExternalParamToConstructor);
                        }
                        catch (IllegalArgumentException exception) {
                            field.set(ware, (Object)SpecialObjectEvent.EventType.any);
                            ScenarioLogger.getInstance().parserWarning("failed to set field " + field.getName() + " of class instance " + checkerClass);
                            ScenarioLogger.getInstance().parserWarning("invalid enum value came, set field to default value == any");
                        }
                        continue;
                    }
                    ScenarioLogger.getInstance().parserWarning("field " + fieldName + " in class " + resolvedClassName + " has unsupported type");
                }
                catch (NoSuchFieldException exception) {
                    FiniteStatesSetBuilder.processException("couldn't find field named " + fieldName + " in class " + resolvedClassName, exception);
                }
                catch (NumberFormatException exception) {
                    FiniteStatesSetBuilder.processException("invalid value to field named " + fieldName + " while creating instance of class " + resolvedClassName, exception);
                }
                catch (NullPointerException exception) {
                    FiniteStatesSetBuilder.processException("NullPointerExcption has occured while creating instance of class " + resolvedClassName, exception);
                }
                catch (IllegalArgumentException exception) {
                    FiniteStatesSetBuilder.processException("IllegalArgumentException has occured while creating instance of class " + resolvedClassName + ": error in internal data structures", exception);
                }
            }
            if (ware instanceof NativeMessageListener) {
                ((NativeMessageListener)ware).prepare();
            }
            if (null != (errorString = ware.isValid())) {
                ScenarioLogger.getInstance().parserWarning("created instance of " + ware.getClass().getName() + " is invalid! error: " + errorString);
            }
            if (null != condition.getAndCondition()) {
                AndEventChecker andChecker = new AndEventChecker();
                EventChecker additionalChacker = FiniteStatesSetBuilder.buildEventChecker(condition.getAndCondition(), soEventTypeExternalParamToConstructor);
                if (null != additionalChacker) {
                    andChecker.addAndChecker(additionalChacker);
                    andChecker.addAndChecker(ware);
                    ware = andChecker;
                } else {
                    ScenarioLogger.getInstance().parserWarning("failed to create AND event checker: additional checker wasn't created");
                }
            }
            if (null != condition.getOrList() && 0 < condition.getOrList().size()) {
                OrEventChecker orChecker = new OrEventChecker();
                for (IsoCondition orCondition : condition.getOrList()) {
                    orChecker.addOrChecker(FiniteStatesSetBuilder.buildEventChecker(orCondition, soEventTypeExternalParamToConstructor));
                }
                orChecker.addOrChecker(ware);
                return orChecker;
            }
            return ware;
        }
        catch (ClassNotFoundException exception) {
            FiniteStatesSetBuilder.processException("couldn't find class with name " + checkerClassName + " and resolved name " + XmlStrings.resolveName(checkerClassName), exception);
        }
        catch (InstantiationException exception) {
            FiniteStatesSetBuilder.processException("couldn't instaciate copy of class " + checkerClassName, exception);
        }
        catch (IllegalAccessException exception) {
            FiniteStatesSetBuilder.processException("access denied to constructor of class" + checkerClassName, exception);
        }
        catch (InvocationTargetException exception) {
            FiniteStatesSetBuilder.processException("access denied to constructor of class" + checkerClassName, exception);
        }
        catch (NullPointerException exception) {
            FiniteStatesSetBuilder.processException("something big and bad happened", exception);
        }
        return null;
    }

    public void checkActionForVirtualNodes(List<ScriptAction> actions, VirtualNodeNumeration virtualNodeNamer, FiniteStateMachine machine) {
        ArrayList<StartScenarioBranchAction> added_actions = new ArrayList<StartScenarioBranchAction>();
        for (ScriptAction action : actions) {
            if (!action.actActionAsScenarioNode()) continue;
            IsoQuest quest = this.buildVirualSelfFinishConditionalState(action, virtualNodeNamer, machine);
            this.buildStateSet(quest, machine);
            String virtualNodeName = quest.getName() + SCENARIO_STATE_NAME_SUFFIX + 0;
            added_actions.add(new StartScenarioBranchAction(virtualNodeName, machine));
        }
        actions.addAll(added_actions);
    }

    public IsoQuest buildVirualSelfFinishConditionalState(ScriptAction action, VirtualNodeNumeration virtualNodeNamer, FiniteStateMachine machine) {
        EventChecker checker;
        ScriptEvent eventForChecker;
        IsoQuest quest = new IsoQuest();
        quest.setName(virtualNodeNamer.buildName(action.getClass().getName()));
        quest.setFinishOnLastPhase(true);
        IsoCondition condition = null;
        if (null != action.getExactEventForConditionOnActivate()) {
            eventForChecker = action.getExactEventForConditionOnActivate();
            checker = eventForChecker instanceof VoidEvent ? new VoidEventChecker((VoidEvent)eventForChecker, true) : new ExactEventChecker(eventForChecker, true);
            condition = new IsoCondition(checker);
        }
        ArrayList<ScriptAction> actions = new ArrayList<ScriptAction>();
        actions.add(action.getChildAction());
        actions.add(new StopScenarioBranchAction(quest.getName(), machine));
        IsoQuestItemTask task = new IsoQuestItemTask(actions, condition);
        quest.addTask(task);
        condition = null;
        if (null != action.getExactEventForConditionOnDeactivate()) {
            eventForChecker = action.getExactEventForConditionOnDeactivate();
            checker = eventForChecker instanceof VoidEvent ? new VoidEventChecker((VoidEvent)eventForChecker, false) : new ExactEventChecker(eventForChecker, false);
            condition = new IsoCondition(checker);
        }
        actions = new ArrayList();
        actions.add(new StopScenarioBranchAction(quest.getName(), machine));
        task = new IsoQuestItemTask(actions, condition);
        quest.addTask(task);
        return quest;
    }

    public void buildStateSet(IsoQuest isoQuest, final FiniteStateMachine machine) {
        if (null == isoQuest || null == machine) {
            throw new IllegalArgumentException("all parameters must be non-null references");
        }
        if (0 >= isoQuest.getTasks().length) {
            throw new IllegalArgumentException("isoQuest must have at least one phase");
        }
        VirtualNodeNumeration virtualNodeNamer = new VirtualNodeNumeration(isoQuest.getName());
        ScenarioLogger.getInstance().parserLog(Level.INFO, "creating scenario subgraph for isoQuest \"" + isoQuest.getName() + '\"');
        Object[] questPhases = isoQuest.getTasks();
        Arrays.sort(questPhases);
        ArrayList<FiniteState> recentlyCreatedStates = new ArrayList<FiniteState>(isoQuest.getTasks().length);
        LinkedList<String> namesAccumulator = new LinkedList<String>();
        String finalStateName = isoQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + (((IsoQuestItemTask)questPhases[questPhases.length - 1]).getPhase() + 1);
        FiniteState finalState = new FiniteState(null, finalStateName);
        if (isoQuest.isFinishOnLastPhase()) {
            finalState.addStateChangedListener(new FsmActionAdapter(null){

                public void stateEntered(FiniteState state) {
                    machine.deactivateState(state.getName());
                }
            });
        }
        namesAccumulator.add(finalStateName);
        recentlyCreatedStates.add(finalState);
        int currentPositionInPhaseArray = questPhases.length - 1;
        int currentPhase = ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getPhase();
        String currentNodeName = isoQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + currentPhase;
        IsoQuestEmulationStateProcessor isoQuestEmulation = new IsoQuestEmulationStateProcessor(finalStateName);
        LinkedList<ScriptAction> toActOnStast = new LinkedList<ScriptAction>();
        boolean isNodeWalkThrough = true;
        boolean finishExists = false;
        while (0 <= currentPositionInPhaseArray) {
            ScenarioLogger.getInstance().parserLog(Level.INFO, "processing phase #" + currentPhase + " task");
            ArrayList<ScriptAction> toAct = new ArrayList<ScriptAction>();
            ArrayList<ObjectProperties> actionsInProperties = new ArrayList<ObjectProperties>();
            ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getActionList(actionsInProperties, toAct);
            toAct.addAll(FiniteStatesSetBuilder.actionsFromActionList(actionsInProperties, isoQuest.getName(), machine));
            this.checkActionForVirtualNodes(toAct, virtualNodeNamer, machine);
            if (((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).isLastPhase()) {
                toAct.add(new StopScenarioBranchAction(currentNodeName, machine));
                finishExists = true;
            }
            EventChecker conditionChecker = null;
            if (null == ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getCondition()) {
                if (SpecialObjectEvent.EventType.none == ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getEventType()) {
                    toActOnStast.addAll(toAct);
                } else {
                    conditionChecker = new SpecialObjectEventTypeChecker(((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getEventType());
                    isNodeWalkThrough = false;
                }
            } else {
                conditionChecker = FiniteStatesSetBuilder.buildEventChecker(((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getCondition(), ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getEventType());
                if (null != conditionChecker) {
                    isNodeWalkThrough = false;
                } else {
                    ScenarioLogger.getInstance().parserWarning("EventChecker wasn't created from condition list, check XML for mistakes; scenario machine can work uncorrectly");
                }
            }
            if (null != conditionChecker) {
                EventReaction reaction;
                switch (toAct.size()) {
                    case 0: {
                        reaction = new SimpleEventReaction(null, conditionChecker, ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getUid());
                        break;
                    }
                    case 1: {
                        reaction = new SimpleEventReaction(toAct.get(0), conditionChecker, ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getUid());
                        break;
                    }
                    default: {
                        Collections.sort(toAct);
                        reaction = new ComplexEventReaction(conditionChecker, ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getUid());
                        ((ComplexEventReaction)reaction).addAction(toAct);
                    }
                }
                isoQuestEmulation.addReaction(reaction);
            }
            if (0 <= --currentPositionInPhaseArray && 0 >= currentPhase - ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getPhase()) continue;
            if (0 <= currentPositionInPhaseArray && 1 < ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getPhase() - currentPhase) {
                ScenarioLogger.getInstance().parserWarning("Scenario structure error: found illegal scenario step from phase " + currentPhase + " to " + ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getPhase());
                ScenarioLogger.getInstance().parserWarning("Quset name: " + isoQuest.getName());
                ScenarioLogger.getInstance().parserWarning("Scenario will work uncorrectly");
                return;
            }
            FiniteStatesSetBuilder.createSimpleAdvanceScheme(namesAccumulator, isoQuestEmulation);
            FiniteState state = new FiniteState(isoQuestEmulation, currentNodeName);
            if (isNodeWalkThrough) {
                toActOnStast.add(new SingleStepScenarioAdvanceAction(isoQuestEmulation.getImplicitNodeName(), machine));
            }
            if (finishExists) {
                isoQuestEmulation.setImplicitStepNextNodeName(null);
            }
            if (!toActOnStast.isEmpty()) {
                if (1 < toActOnStast.size()) {
                    Collections.sort(toActOnStast);
                }
                state.addStateChangedListener(new FsmActionAdapter(toActOnStast){

                    public void stateEntered(FiniteState state) {
                        this.actAllActions();
                    }
                });
            }
            FiniteStatesSetBuilder.addTransition(state, recentlyCreatedStates);
            recentlyCreatedStates.add(state);
            namesAccumulator.add(currentNodeName);
            if (0 > currentPositionInPhaseArray) continue;
            finishExists = false;
            currentPhase = ((IsoQuestItemTask)questPhases[currentPositionInPhaseArray]).getPhase();
            isoQuestEmulation = new IsoQuestEmulationStateProcessor(currentNodeName);
            currentNodeName = isoQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + currentPhase;
            isNodeWalkThrough = true;
            toActOnStast.clear();
        }
        this.states.addAll(recentlyCreatedStates);
    }

    public void buildStateSet(PhasedQuest phasedQuest, final FiniteStateMachine machine) {
        if (null == phasedQuest || null == machine) {
            throw new IllegalArgumentException("phasedQuest and machine must be non-null references");
        }
        ScenarioLogger.getInstance().parserLog(Level.INFO, "creating scenario subgraph for phasedQuest \"" + phasedQuest.getName() + '\"');
        if (null != phasedQuest.getOrganizerRef()) {
            Organizers.getInstance().add(phasedQuest.getOrganizerRef(), Pattern.compile(phasedQuest.getName() + "(" + SCENARIO_STATE_NAME_SUFFIX + "\\d+)?"));
        }
        ArrayList<FiniteState> recentlyCreatedStates = new ArrayList<FiniteState>(phasedQuest.getPhases().size() + 2);
        ArrayList<String> nodeChainNames = new ArrayList<String>(phasedQuest.getPhases().size() + 1);
        String previousNodeName = !phasedQuest.getPhases().isEmpty() ? phasedQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + (phasedQuest.getPhases().getLast().getNom() + 1) : phasedQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + 1;
        FiniteState finalState = new FiniteState(null, previousNodeName);
        ArrayList<ScriptAction> actionsOnExit = FiniteStatesSetBuilder.actionsFromActionList(phasedQuest.getActionsOnEnd(), previousNodeName, machine);
        if (null != phasedQuest.getMissionPoint() && 0 < phasedQuest.getMissionPoint().length()) {
            actionsOnExit.add(new MissionPointUnmarkAction(phasedQuest.getMissionPoint()));
        }
        Collections.sort(actionsOnExit);
        finalState.addStateChangedListener(new FsmActionAdapter(actionsOnExit){

            public void stateEntered(FiniteState state) {
                this.actAllActions();
                machine.deactivateState(state.getName());
            }
        });
        nodeChainNames.add(previousNodeName);
        recentlyCreatedStates.add(finalState);
        ListIterator<QuestPhase> iter = phasedQuest.getPhases().listIterator(phasedQuest.getPhases().size());
        while (iter.hasPrevious()) {
            String pointToUnmark;
            QuestPhase phase = iter.previous();
            ScenarioLogger.getInstance().parserLog(Level.INFO, "creating subgraph node for \"" + phasedQuest.getName() + "\" quest phase #" + phase.getNom());
            String phaseName = phasedQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + phase.getNom();
            PhasedQuestEmulationStateProcessor stateProcessor = new PhasedQuestEmulationStateProcessor();
            FiniteStatesSetBuilder.createSimpleAdvanceScheme(nodeChainNames, stateProcessor);
            FiniteState extractedState = new FiniteState(stateProcessor, phaseName);
            ArrayList<ScriptAction> onStart = FiniteStatesSetBuilder.actionsFromActionList(phase.getActionList(), phaseName, machine);
            if (null != phase.getMissionPoint()) {
                onStart.add(new MissionPointMarkAction(phase.getMissionPoint()));
                pointToUnmark = phase.getMissionPoint();
            } else {
                pointToUnmark = null;
            }
            Collections.sort(onStart);
            extractedState.addStateChangedListener(new FsmActionAdapter(onStart){

                public void stateEntered(FiniteState state) {
                    this.actAllActions();
                }

                public void stateLeaved(FiniteState state) {
                    if (null != pointToUnmark) {
                        MissionPointUnmarkAction toAct = new MissionPointUnmarkAction(pointToUnmark);
                        ((ScriptAction)toAct).act();
                    }
                }
            });
            nodeChainNames.add(phaseName);
            FiniteStatesSetBuilder.addTransition(extractedState, recentlyCreatedStates);
            recentlyCreatedStates.add(extractedState);
        }
        PhasedQuestEmulationStateProcessor stateProcessor = new PhasedQuestEmulationStateProcessor();
        FiniteStatesSetBuilder.createSimpleAdvanceScheme(nodeChainNames, stateProcessor);
        FiniteState firstState = new FiniteState(stateProcessor, phasedQuest.getName());
        ArrayList<ScriptAction> onEnterActionList = FiniteStatesSetBuilder.actionsFromActionList(phasedQuest.getActionsOnStart(), phasedQuest.getName(), machine);
        if (null != phasedQuest.getMissionPoint() && 0 < phasedQuest.getMissionPoint().length()) {
            onEnterActionList.add(new MissionPointMarkAction(phasedQuest.getMissionPoint()));
        }
        onEnterActionList.add(new StartOrgAction(phasedQuest.getName() + SCENARIO_STATE_NAME_SUFFIX + 1));
        Collections.sort(onEnterActionList);
        firstState.addStateChangedListener(new FsmActionAdapter(onEnterActionList){

            public void stateEntered(FiniteState state) {
                this.actAllActions();
            }
        });
        FiniteStatesSetBuilder.addTransition(firstState, recentlyCreatedStates);
        recentlyCreatedStates.add(firstState);
        this.states.addAll(recentlyCreatedStates);
    }

    void addStatesToSet(FiniteStatesSet set) {
        for (FiniteState scenarioNode : this.states) {
            set.addState(scenarioNode);
        }
    }

    void pourOut(FiniteStatesSet set) {
        this.addStatesToSet(set);
        this.states.clear();
    }

    private static class VirtualNodeNumeration {
        private String parentNodeName;
        private int iter = 0;

        VirtualNodeNumeration(String parentNodeName) {
            this.parentNodeName = parentNodeName;
        }

        String buildName(String prefix) {
            return prefix + FiniteStatesSetBuilder.SCENARIO_VIRTUAL_STATE_SUFFIX + this.parentNodeName + "_" + this.iter++;
        }
    }

    private static final class ActionConstructionException
    extends Exception {
        static final long serialVersionUID = 1L;

        ActionConstructionException() {
        }

        ActionConstructionException(String message) {
            super(message);
        }
    }
}

