/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscenario.missions.MissionManager;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.starters.ConditionChecker;
import scenarioUtils.Pair;
import scenarioXml.XmlFilter;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlserialization.MissionsPrioritySerialization;
import xmlserialization.SimpleTypeSerializator;
import xmlserialization.nxs.SerializatorOfAnnotated;
import xmlutils.NodeList;

public class MissionsSerialization
implements XmlSerializable {
    private static MissionsSerialization instance = new MissionsSerialization();

    public static MissionsSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return MissionsSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        MissionsSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        MissionsSerialization.serializeXML(MissionSystemInitializer.getMissionsManager(), stream);
    }

    public static String getNodeName() {
        return "missions";
    }

    public static void serializeXML(MissionManager value, PrintStream stream) {
        List<String> finishedMissions;
        Helper.openNode(stream, MissionsSerialization.getNodeName());
        Helper.openNode(stream, "sf_finish_conditions");
        List<ConditionChecker> allCheckers = ConditionChecker.getAllCheckers();
        for (ConditionChecker checker : allCheckers) {
            SerializatorOfAnnotated.getInstance().saveState(stream, checker);
        }
        Helper.closeNode(stream, "sf_finish_conditions");
        List<Pair<String, String>> activatedMissions = value.getActivatedMissions();
        if (null != activatedMissions && !activatedMissions.isEmpty()) {
            Helper.openNode(stream, "activemissions");
            for (Pair<String, String> name : activatedMissions) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString(name.getFirst(), stream);
                if (name.getSecond() != null) {
                    SimpleTypeSerializator.serializeXMLString(name.getSecond(), stream);
                }
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
            Helper.closeNode(stream, "activemissions");
        }
        if (null != (finishedMissions = value.getFinisheddMissions()) && !finishedMissions.isEmpty()) {
            Helper.openNode(stream, "finishedmissions");
            for (String name : finishedMissions) {
                ListElementSerializator.serializeXMLListelementOpen(stream);
                SimpleTypeSerializator.serializeXMLString(name, stream);
                ListElementSerializator.serializeXMLListelementClose(stream);
            }
            Helper.closeNode(stream, "finishedmissions");
        }
        MissionsPrioritySerialization.serializeXML(value.getMissionsPriorities(), stream);
        Helper.closeNode(stream, MissionsSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        xmlutils.Node statefullFinishConditions;
        xmlutils.Node priorityNode;
        NodeList listFinishedMissions;
        xmlutils.Node finishedMissions;
        NodeList listActiveMissions;
        MissionManager manager = MissionSystemInitializer.getMissionsManager();
        xmlutils.Node activeMissions = node.getNamedChild("activemissions");
        if (null != activeMissions && null != (listActiveMissions = activeMissions.getNamedChildren(ListElementSerializator.getNodeName())) && !listActiveMissions.isEmpty()) {
            ArrayList<Pair<String, String>> missionsNames = new ArrayList<Pair<String, String>>();
            for (xmlutils.Node activeMissionNode : listActiveMissions) {
                NodeList nameNode = activeMissionNode.getNamedChildren(SimpleTypeSerializator.getNodeNameString());
                if (null == nameNode) {
                    Log.error("MissionsSerialization in deserializeXML has no named nodes " + SimpleTypeSerializator.getNodeNameString() + " in node named " + ListElementSerializator.getNodeName() + " in node named " + "activemissions");
                    continue;
                }
                Pair<String, String> pairMissionNamePointName = new Pair<String, String>(null, null);
                int counStrings = 0;
                for (xmlutils.Node nodeString : nameNode) {
                    if (counStrings == 0) {
                        String missionName = SimpleTypeSerializator.deserializeXMLString(nodeString);
                        pairMissionNamePointName.setFirst(missionName);
                    } else if (counStrings == 1) {
                        String pointName = SimpleTypeSerializator.deserializeXMLString(nodeString);
                        pairMissionNamePointName.setSecond(pointName);
                    }
                    ++counStrings;
                }
                missionsNames.add(pairMissionNamePointName);
            }
            manager.setActivatedMissions(missionsNames);
        }
        if (null != (finishedMissions = node.getNamedChild("finishedmissions")) && null != (listFinishedMissions = finishedMissions.getNamedChildren(ListElementSerializator.getNodeName())) && !listFinishedMissions.isEmpty()) {
            ArrayList<String> miossionsNames = new ArrayList<String>();
            for (xmlutils.Node activeMissionNode : listFinishedMissions) {
                xmlutils.Node nameNode = activeMissionNode.getNamedChild(SimpleTypeSerializator.getNodeNameString());
                if (null == nameNode) {
                    Log.error("MissionsSerialization in deserializeXML has no named node " + SimpleTypeSerializator.getNodeNameString() + " in node named " + ListElementSerializator.getNodeName() + " in node named " + "finishedmissions");
                    continue;
                }
                String missionName = SimpleTypeSerializator.deserializeXMLString(nameNode);
                miossionsNames.add(missionName);
            }
            manager.setFinishedMissions(miossionsNames);
        }
        if (null != (priorityNode = node.getNamedChild(MissionsPrioritySerialization.getNodeName()))) {
            MissionsPrioritySerialization.deserializeXML(manager.getMissionsPriorities(), priorityNode);
        }
        if (null != (statefullFinishConditions = node.getNamedChild("sf_finish_conditions"))) {
            XmlFilter nodesFilter = new XmlFilter(statefullFinishConditions.getNode().getChildNodes());
            Node finishCheckerNode = nodesFilter.nextElement();
            while (null != finishCheckerNode) {
                if (null == SerializatorOfAnnotated.getInstance().loadStateOrNull(new xmlutils.Node(finishCheckerNode))) {
                    Log.error("Finish condition was not loaded from mission node with name " + finishCheckerNode.getNodeName());
                }
                finishCheckerNode = nodesFilter.nextElement();
            }
        }
    }
}

