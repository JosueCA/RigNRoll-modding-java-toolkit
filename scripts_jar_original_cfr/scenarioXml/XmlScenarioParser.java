/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrloggers.ScenarioLogger;
import scenarioXml.CbVideoCallRawData;
import scenarioXml.IsoQuest;
import scenarioXml.IsoQuestItemTask;
import scenarioXml.ObjectProperties;
import scenarioXml.ParsingOut;
import scenarioXml.PhasedQuest;
import scenarioXml.QuestPhase;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class XmlScenarioParser {
    private XmlScenarioParser() {
    }

    static LinkedList<CbVideoCallRawData> parseCbVideoXml(String path2) {
        Document document;
        try {
            XmlDocument xml = new XmlDocument(path2);
            document = xml.getContent();
        }
        catch (IOException e) {
            System.err.println("Failure during loading CbVideoCalls config: " + e.getMessage());
            return null;
        }
        LinkedList<CbVideoCallRawData> out = new LinkedList<CbVideoCallRawData>();
        NodeList cbNodesList = document.getElementsByTagName("cbcall");
        XmlFilter filterCbCallList = new XmlFilter(cbNodesList);
        Node cbCallListNode = filterCbCallList.nextElement();
        while (null != cbCallListNode) {
            XmlFilter filterCbCallElements = new XmlFilter(cbCallListNode.getChildNodes());
            Node cbCallNode = filterCbCallElements.nodeNameNext("element");
            while (null != cbCallNode) {
                out.add(new CbVideoCallRawData(cbCallNode));
                cbCallNode = filterCbCallElements.nodeNameNext("element");
            }
            cbCallListNode = filterCbCallList.nextElement();
        }
        return out;
    }

    static ParsingOut parseQuests(String path2) throws IOException {
        XmlDocument xml = new XmlDocument(path2);
        Document document = xml.getContent();
        NodeList isoQuestsList = document.getElementsByTagName("isoquests");
        XmlFilter filterIsoQuestsList = new XmlFilter(isoQuestsList);
        ParsingOut parsingOut = new ParsingOut();
        Node isoQuestNode = filterIsoQuestsList.nextElement();
        while (null != isoQuestNode) {
            XmlFilter filterItemsList = new XmlFilter(isoQuestNode.getChildNodes());
            Node itemNode = filterItemsList.nodeNameNext("item");
            while (null != itemNode) {
                IsoQuest parsedQuest = new IsoQuest();
                NamedNodeMap attributes = itemNode.getAttributes();
                String name = XmlDocument.getAttributeValue(attributes, "name", "scenarioNode");
                boolean finishOnLastPhase = Boolean.parseBoolean(XmlDocument.getAttributeValue(attributes, "finishonlastphase", "true"));
                parsedQuest.setName(name);
                parsedQuest.setFinishOnLastPhase(finishOnLastPhase);
                XmlFilter itemTaskFilter = new XmlFilter(itemNode.getChildNodes());
                Node currentTaskNode = itemTaskFilter.nodeNameNext("task");
                while (null != currentTaskNode) {
                    IsoQuestItemTask task = new IsoQuestItemTask(currentTaskNode);
                    parsedQuest.addTask(task);
                    currentTaskNode = itemTaskFilter.nodeNameNext("task");
                }
                parsingOut.addQuest(parsedQuest);
                itemNode = filterItemsList.nodeNameNext("item");
            }
            isoQuestNode = filterIsoQuestsList.nextElement();
        }
        NodeList phasedQuestsList = document.getElementsByTagName("quests");
        XmlFilter filterPhasedQuestsList = new XmlFilter(phasedQuestsList);
        Node phasedQuestNode = filterPhasedQuestsList.nextElement();
        while (null != phasedQuestNode) {
            NodeList qList = phasedQuestNode.getChildNodes();
            XmlFilter filterItemsList = new XmlFilter(qList);
            Node qNode = filterItemsList.nodeNameNext("q");
            while (null != qNode) {
                PhasedQuest parsedQuest = new PhasedQuest();
                parsedQuest.setName(XmlDocument.getAttributeValue(qNode.getAttributes(), "name", "unkonwn"));
                parsedQuest.setOrganizerRef(XmlDocument.getAttributeValue(qNode.getAttributes(), "org", "unkonwn"));
                XmlFilter filter = new XmlFilter(qNode.getChildNodes());
                Node mpNode = filter.nodeNameNext("MP");
                if (null != mpNode) {
                    parsedQuest.setMissionPoint(XmlDocument.getAttributeValue(mpNode.getAttributes(), "name", "unkonwn"));
                } else {
                    parsedQuest.setMissionPoint(null);
                }
                filter.goOnStart();
                filter.goOnStart();
                Node phaseNode = filter.nodeNameNext("phase");
                while (null != phaseNode) {
                    int phaseNom;
                    try {
                        phaseNom = Integer.parseInt(XmlDocument.getAttributeValue(phaseNode.getAttributes(), "nom", "1"));
                    }
                    catch (NumberFormatException exception) {
                        ScenarioLogger.getInstance().parserWarning("error while parsing phasedQuest: invalid phase nom; set nom to default value");
                        ScenarioLogger.getInstance().parserWarning(exception.getLocalizedMessage());
                        exception.printStackTrace(System.err);
                        phaseNom = 1;
                    }
                    String missionPoint = XmlDocument.getSingleChildNodeAttribute(phaseNode, "MP", "name");
                    QuestPhase phase = new QuestPhase(phaseNom, missionPoint);
                    phase.setActionList(ObjectProperties.extractList(phaseNode, "MP"));
                    parsedQuest.addPhase(phase);
                    phaseNode = filter.nodeNameNext("phase");
                }
                Collections.sort(parsedQuest.getPhases());
                filter.goOnStart();
                Node questScriptNode = filter.nodeNameNext("start");
                if (null != questScriptNode) {
                    parsedQuest.setActionOnStart(ObjectProperties.extractList(questScriptNode, new String[0]));
                }
                filter.goOnStart();
                questScriptNode = filter.nodeNameNext("finish");
                if (null != questScriptNode) {
                    parsedQuest.setActionOnEnd(ObjectProperties.extractList(questScriptNode, new String[0]));
                }
                parsingOut.addQuest(parsedQuest);
                qNode = filterItemsList.nodeNameNext("q");
            }
            phasedQuestNode = filterPhasedQuestsList.nextElement();
        }
        return parsingOut;
    }

    public static void main(String[] args) {
        try {
            XmlScenarioParser.parseQuests("Quests.xml");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

