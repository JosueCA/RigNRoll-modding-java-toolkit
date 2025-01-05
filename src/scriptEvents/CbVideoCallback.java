/*
 * Decompiled with CFR 0.151.
 */
package scriptEvents;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import rnrloggers.ScenarioLogger;
import scriptActions.ScriptAction;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CbVideoCallback {
    private LinkedList<ScriptAction> onStartActions = new LinkedList();
    private LinkedList<ScriptAction> onFinishActions = new LinkedList();
    private HashMap<Integer, LinkedList<ScriptAction>> onAnswerActions = null;

    public CbVideoCallback(int answersCount) {
        if (0 <= answersCount) {
            this.onAnswerActions = new HashMap(answersCount);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.<init>: came invalid answersCount param == " + answersCount);
            this.onAnswerActions = new HashMap();
        }
    }

    public void addOnStartAction(ScriptAction action) {
        if (null != action) {
            this.onStartActions.add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnStartAction: invalid argument(s)");
        }
    }

    public void addOnFinishAction(ScriptAction action) {
        if (null != action) {
            this.onFinishActions.add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnFinishAction: invalid argument(s)");
        }
    }

    public void addOnAnswerAction(int answerNom, ScriptAction action) {
        if (null != action && 0 <= answerNom) {
            if (!this.onAnswerActions.containsKey(answerNom)) {
                this.onAnswerActions.put(answerNom, new LinkedList());
            }
            this.onAnswerActions.get(answerNom).add(action);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnAnswerAction: invalid argument(s)");
        }
    }

    public void addOnAnswerActions(int answerNom, Collection<ScriptAction> toAdd) {
        if (null != toAdd && 0 <= answerNom) {
            if (!this.onAnswerActions.containsKey(answerNom)) {
                this.onAnswerActions.put(answerNom, new LinkedList());
            }
            this.onAnswerActions.get(answerNom).addAll(toAdd);
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.addOnAnswerAction: invalid argument(s)");
        }
    }

    public void addOnStartActions(Collection<ScriptAction> toAdd) {
        if (null != toAdd && 0 < toAdd.size()) {
            this.onStartActions.addAll(toAdd);
        }
    }

    public void addOnFinishActions(Collection<ScriptAction> toAdd) {
        if (null != toAdd && 0 < toAdd.size()) {
            this.onFinishActions.addAll(toAdd);
        }
    }

    void onStart() {
        for (ScriptAction action : this.onStartActions) {
            action.act();
        }
    }

    void onFinish() {
        for (ScriptAction action : this.onFinishActions) {
            action.act();
        }
    }

    void onAnswer(int answerNomber) {
        if (0 <= answerNomber && this.onAnswerActions.containsKey(answerNomber)) {
            LinkedList<ScriptAction> onNthAnswerActions = this.onAnswerActions.get(answerNomber);
            for (ScriptAction action : onNthAnswerActions) {
                action.act();
            }
        } else {
            ScenarioLogger.getInstance().machineWarning("CbVideoCallback.onAnswer: invalid argument(s)");
        }
    }

    List<ScriptAction> getActionList() {
        LinkedList<ScriptAction> out = new LinkedList<ScriptAction>();
        out.addAll(this.onStartActions);
        out.addAll(this.onFinishActions);
        for (LinkedList<ScriptAction> toAddInOut : this.onAnswerActions.values()) {
            out.addAll(toAddInOut);
        }
        return out;
    }
}

