/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;
import scenarioMachine.FiniteStateMachine;
import scenarioXml.CbVideoCallRawData;
import scenarioXml.FiniteStatesSetBuilder;
import scenarioXml.XmlScenarioParser;
import scriptActions.ScriptAction;
import scriptEvents.CbVideoCallback;
import scriptEvents.CbVideoEventsListener;

public class CbvEventListenerBuilder {
    private CbVideoEventsListener ware = null;

    public CbvEventListenerBuilder(String pathToXmlFile, FiniteStateMachine scenarioMachine) throws IllegalArgumentException {
        if (null == pathToXmlFile || null == scenarioMachine) {
            throw new IllegalArgumentException("pathToXmlFile and scenarioMachine must be non-null references");
        }
        LinkedList<CbVideoCallRawData> external = XmlScenarioParser.parseCbVideoXml(pathToXmlFile);
        if (null == external) {
            throw new IllegalArgumentException("invalid data stored in " + pathToXmlFile);
        }
        ScenarioLogger.getInstance().parserLog(Level.FINEST, "BUILDING CBV EVENTS LISTENER");
        this.ware = new CbVideoEventsListener();
        for (CbVideoCallRawData rawCall : external) {
            ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing \"" + rawCall.getName() + "\" call");
            CbVideoCallback callBack = new CbVideoCallback(rawCall.getIndexesOfAnswerActions().size());
            if (null != rawCall.getStartActions()) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on start");
                ArrayList<ScriptAction> onStartActions = FiniteStatesSetBuilder.actionsFromActionList(rawCall.getStartActions(), "unknown", scenarioMachine);
                if (null != onStartActions && 0 < onStartActions.size()) {
                    Collections.sort(onStartActions);
                    callBack.addOnStartActions(onStartActions);
                }
            }
            if (null != rawCall.getFinishActions()) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on finish");
                ArrayList<ScriptAction> onFinishActions = FiniteStatesSetBuilder.actionsFromActionList(rawCall.getFinishActions(), "unknown", scenarioMachine);
                if (null != onFinishActions && 0 < onFinishActions.size()) {
                    Collections.sort(onFinishActions);
                    callBack.addOnFinishActions(onFinishActions);
                }
            }
            Set<Integer> answersActions = rawCall.getIndexesOfAnswerActions();
            for (Integer answerIndex : answersActions) {
                ScenarioLogger.getInstance().parserLog(Level.FINEST, "parsing actions on answer #" + answerIndex);
                ArrayList<ScriptAction> actions = FiniteStatesSetBuilder.actionsFromActionList(rawCall.getAnswerActions(answerIndex), "unknown", scenarioMachine);
                Collections.sort(actions);
                callBack.addOnAnswerActions(answerIndex, actions);
            }
            this.ware.addEventReaction(rawCall.getName(), callBack);
        }
        ScenarioLogger.getInstance().parserLog(Level.FINEST, "CBV EVENTS LISTENER WAS BUILT");
    }

    public CbVideoEventsListener getWare() {
        return this.ware;
    }
}

