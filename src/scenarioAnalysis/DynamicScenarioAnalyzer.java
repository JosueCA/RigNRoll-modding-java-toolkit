/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import rnrloggers.ScenarioAnalysisLogger;
import scenarioAnalysis.AnalysisUI;
import scenarioMachine.FiniteStateMachine;
import scenarioUtils.Pair;
import scriptEvents.EventsController;
import scriptEvents.ScriptEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DynamicScenarioAnalyzer {
    private static final int MAX_EMULATION_ITERATIONS_COUNT = 2048;
    private static final String INFO_RECORD_PREFIX = "\t++++ ";
    private static final String ARROW = " --> ";
    private FiniteStateMachine scenarioAutomat = null;
    private boolean chooseMade = false;
    private int choose = 0;
    private final Object mutex = new Object();
    private boolean warningClosed = false;

    DynamicScenarioAnalyzer(FiniteStateMachine machine) {
        if (null == machine) {
            throw new IllegalArgumentException("machine must be non-null reference");
        }
        this.scenarioAutomat = machine;
    }

    private void printSimulationResults(ScriptEvent ... eventTuple) {
        try {
            Collection<String> activeStates = this.scenarioAutomat.getCurrentActiveStates();
            List<String> statedStates = this.scenarioAutomat.getRecentlyStarted();
            List<String> closedStates = this.scenarioAutomat.getRecentlyClosed();
            List<Pair<String, String>> stepsMade = this.scenarioAutomat.getRecentlySteps();
            if (statedStates.isEmpty() && closedStates.isEmpty() && stepsMade.isEmpty()) {
                return;
            }
            ScenarioAnalysisLogger.getInstance().getLog().info("simulated tuple:");
            for (ScriptEvent event2 : eventTuple) {
                ScenarioAnalysisLogger.getInstance().getLog().finest('\t' + event2.toString());
            }
            ScenarioAnalysisLogger.getInstance().getLog().info("simulation result:");
            ScenarioAnalysisLogger.getInstance().getLog().info("\tactive:");
            for (String string : activeStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + string);
            }
            ScenarioAnalysisLogger.getInstance().getLog().info("\tstarted:");
            for (String string : statedStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + string);
            }
            ScenarioAnalysisLogger.getInstance().getLog().info("\tclosed:");
            for (String string : closedStates) {
                ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + string);
            }
            ScenarioAnalysisLogger.getInstance().getLog().info("\tsteps made:");
            for (Pair pair : stepsMade) {
                ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + pair.getFirst() + ARROW + pair.getSecond());
            }
        }
        catch (UnsupportedOperationException exception) {
            ScenarioAnalysisLogger.getInstance().getLog().severe("fsm was created without recordChanges flag!");
        }
    }

    private void emulateEventTuple(ScriptEvent ... eventTuple) {
        EventsController.getInstance().eventHappen(eventTuple);
        this.printSimulationResults(eventTuple);
        this.scenarioAutomat.clearLog();
    }

    public void randomSimulation(String ... defaultStartedQuests) {
        List<List<ScriptEvent>> expectedEvents;
        ScenarioAnalysisLogger.getInstance().getLog().finest("RANDOM SIMULATION STARTED:\n");
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVATING DEFAULT QUESTS:\n");
        for (String questToActivate : defaultStartedQuests) {
            this.scenarioAutomat.activateState(true, questToActivate + "_phase_" + 1);
        }
        this.printSimulationResults(new ScriptEvent[0]);
        this.scenarioAutomat.clearLog();
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nGENERATING RANDOM EVENTS:\n");
        Random randomGenerator = new Random(System.nanoTime());
        ScriptEvent[] emptyArrayTuple = new ScriptEvent[]{};
        for (int iterationNom = 0; !this.scenarioAutomat.getCurrentActiveStates().isEmpty() && 2048 > iterationNom && null != (expectedEvents = this.scenarioAutomat.getExpectedEvents()) && !expectedEvents.isEmpty(); ++iterationNom) {
            List<ScriptEvent> eventTuple = expectedEvents.get(randomGenerator.nextInt(expectedEvents.size()));
            this.emulateEventTuple(eventTuple.toArray(emptyArrayTuple));
        }
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVE SATES:\n");
        Collection<String> activeStates = this.scenarioAutomat.getCurrentActiveStates();
        for (String infoStr : activeStates) {
            ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + infoStr);
        }
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nRANDOM SIMULATION CLOSED");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void uiSimulation(AnalysisUI ui, String ... defaultStartedQuests) {
        List<List<ScriptEvent>> expectedEvents;
        ui.show();
        ScenarioAnalysisLogger.getInstance().getLog().finest("RANDOM SIMULATION STARTED:\n");
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVATING DEFAULT QUESTS:\n");
        for (String questToActivate : defaultStartedQuests) {
            this.scenarioAutomat.activateState(true, questToActivate + "_phase_" + 1);
        }
        this.printSimulationResults(new ScriptEvent[0]);
        this.scenarioAutomat.clearLog();
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nGENERATING RANDOM EVENTS:\n");
        ScriptEvent[] emptyArrayTuple = new ScriptEvent[]{};
        for (int iterationNom = 0; !this.scenarioAutomat.getCurrentActiveStates().isEmpty() && 2048 > iterationNom && null != (expectedEvents = this.scenarioAutomat.getExpectedEvents()) && !expectedEvents.isEmpty(); ++iterationNom) {
            String[] stringTupleEvents = this.convertForUi(expectedEvents);
            boolean check_results = true;
            int choose_made = 0;
            try {
                ui.recieve(stringTupleEvents, new UiListener());
                while (check_results) {
                    Object object = this.mutex;
                    synchronized (object) {
                        check_results = !this.chooseMade;
                        choose_made = this.choose;
                    }
                }
            }
            catch (AnalysisUI.ExceptionBadData e) {
                boolean warningclosed = false;
                e.showEvent(new WarnListener());
                while (!warningclosed) {
                    Object object = this.mutex;
                    synchronized (object) {
                        warningclosed = this.warningClosed;
                    }
                }
            }
            List<ScriptEvent> eventTuple = expectedEvents.get(choose_made);
            this.emulateEventTuple(eventTuple.toArray(emptyArrayTuple));
        }
        ScenarioAnalysisLogger.getInstance().getLog().finest("\nACTIVE SATES:\n");
        Collection<String> activeStates = this.scenarioAutomat.getCurrentActiveStates();
        Iterator<String> i$ = activeStates.iterator();
        while (true) {
            if (!i$.hasNext()) {
                ScenarioAnalysisLogger.getInstance().getLog().finest("\nRANDOM SIMULATION CLOSED");
                ui.close();
                return;
            }
            String infoStr = i$.next();
            ScenarioAnalysisLogger.getInstance().getLog().info(INFO_RECORD_PREFIX + infoStr);
        }
    }

    private String[] convertForUi(List<List<ScriptEvent>> eventsTuple) {
        String[] res = new String[eventsTuple.size()];
        for (int i = 0; i < eventsTuple.size(); ++i) {
            String events_string = "";
            for (ScriptEvent event2 : eventsTuple.get(i)) {
                events_string = event2.toString() + "\n";
            }
            res[i] = events_string;
        }
        return res;
    }

    class WarnListener
    implements AnalysisUI.WarnMessageClosed {
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        WarnListener() {
            Object object = DynamicScenarioAnalyzer.this.mutex;
            synchronized (object) {
                DynamicScenarioAnalyzer.this.warningClosed = false;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void closed() {
            Object object = DynamicScenarioAnalyzer.this.mutex;
            synchronized (object) {
                DynamicScenarioAnalyzer.this.warningClosed = true;
            }
        }
    }

    class UiListener
    implements AnalysisUI.IUpdateChoose {
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        UiListener() {
            Object object = DynamicScenarioAnalyzer.this.mutex;
            synchronized (object) {
                DynamicScenarioAnalyzer.this.chooseMade = false;
                DynamicScenarioAnalyzer.this.choose = 0;
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void choose(int value) {
            Object object = DynamicScenarioAnalyzer.this.mutex;
            synchronized (object) {
                DynamicScenarioAnalyzer.this.chooseMade = true;
                DynamicScenarioAnalyzer.this.choose = value;
            }
        }
    }
}

