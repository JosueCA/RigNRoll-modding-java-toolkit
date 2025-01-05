/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.CoreTime;
import rnrscenario.EndScenario;
import scenarioUtils.Pair;
import scriptEvents.ScriptEvent;
import scriptEvents.VoidEvent;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.Node;

public class ActionTaskDaysSerialization {
    public static String getNodeName() {
        return "timeaction";
    }

    public static void serializeXML(EndScenario.DelayedAction value, PrintStream stream) {
        VoidEvent voidEvent;
        List<Pair<String, String>> attributes = Helper.createSingleAttribute("days", value.getTimeDelta().gDate());
        Helper.addAttribute("hours", value.getTimeDelta().gHour(), attributes);
        Helper.addAttribute("name", value.getName(), attributes);
        Helper.printOpenNodeWithAttributes(stream, ActionTaskDaysSerialization.getNodeName(), attributes);
        ScriptEvent event2 = value.getEvent_on_activate();
        if (event2 != null) {
            if (event2 instanceof VoidEvent) {
                voidEvent = (VoidEvent)event2;
                Helper.printClosedNodeWithAttributes(stream, "enterevent", Helper.createSingleAttribute("value", voidEvent.getInfo()));
            } else {
                Log.error("ActionTaskDaysSerialization on serializeXML has getEvent_on_activate unexpected event type " + event2.getClass().getName());
            }
        }
        if ((event2 = value.getEvent_on_remove()) != null) {
            if (event2 instanceof VoidEvent) {
                voidEvent = (VoidEvent)event2;
                Helper.printClosedNodeWithAttributes(stream, "exitevent", Helper.createSingleAttribute("value", voidEvent.getInfo()));
            } else {
                Log.error("ActionTaskDaysSerialization on serializeXML has getEvent_on_remove unexpected event type " + event2.getClass().getName());
            }
        }
        CoreTimeSerialization.serializeXML(value.getTimeStart(), stream);
        Helper.closeNode(stream, ActionTaskDaysSerialization.getNodeName());
    }

    public static EndScenario.DelayedAction deserializeXML(Node node) {
        String daysString = node.getAttribute("days");
        String hoursString = node.getAttribute("hours");
        int daysValue = Helper.ConvertToIntegerAndWarn(daysString, "days", "ActionTaskDaysSerialization on deserializeXML");
        int hoursValue = null == hoursString ? 0 : Helper.ConvertToIntegerAndWarn(hoursString, "hours", "ActionTaskDaysSerialization on deserializeXML");
        String name = node.getAttribute("name");
        VoidEvent onEnter = null;
        Node onEnterNode = node.getNamedChild("enterevent");
        if (null != onEnterNode) {
            String eventValue = onEnterNode.getAttribute("value");
            onEnter = new VoidEvent(eventValue);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named enterevent");
        }
        VoidEvent onLeave = null;
        Node onLeaveNode = node.getNamedChild("exitevent");
        if (null != onLeaveNode) {
            String eventValue = onLeaveNode.getAttribute("value");
            onLeave = new VoidEvent(eventValue);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named exitevent");
        }
        CoreTime startTime = null;
        Node startTimeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());
        if (null != startTimeNode) {
            startTime = CoreTimeSerialization.deserializeXML(startTimeNode);
        } else {
            Log.warning("ActionTaskDaysSerialization on deserializeXML has no node named " + CoreTimeSerialization.getNodeName());
        }
        EndScenario.DelayedAction result = new EndScenario.DelayedAction(name, daysValue, hoursValue, onEnter, onLeave);
        result.setTimeStart(startTime);
        return result;
    }
}

