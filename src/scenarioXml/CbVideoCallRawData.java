/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import rnrloggers.ScenarioLogger;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class CbVideoCallRawData {
    private String name = null;
    private ArrayList<ObjectProperties> onStartActionList = null;
    private ArrayList<ObjectProperties> onFinishActionList = null;
    private HashMap<Integer, ArrayList<ObjectProperties>> onAnswerActionList = new HashMap();

    CbVideoCallRawData(Node xmlDataRoot) {
        if (null == xmlDataRoot || 0 != xmlDataRoot.getNodeName().compareToIgnoreCase("element")) {
            throw new IllegalArgumentException("xmlDataRoot must be non-null and must have name element");
        }
        NamedNodeMap attributes = xmlDataRoot.getAttributes();
        this.name = XmlDocument.getAttributeValue(attributes, "name", "unkownName");
        XmlFilter filter = new XmlFilter(xmlDataRoot.getChildNodes());
        Node startNode = filter.nodeNameNext("start");
        if (null != startNode) {
            this.onStartActionList = ObjectProperties.extractListEx(new XmlFilter(startNode.getChildNodes()));
        }
        filter.goOnStart();
        Node finishNode = filter.nodeNameNext("finish");
        if (null != finishNode) {
            this.onFinishActionList = ObjectProperties.extractListEx(new XmlFilter(finishNode.getChildNodes()));
        }
        filter.goOnStart();
        Node answerNode = filter.nodeNameNext("answer");
        while (null != answerNode) {
            Integer answerNom;
            try {
                answerNom = Integer.parseInt(XmlDocument.getAttributeValue(answerNode.getAttributes(), "value", "noninteger"));
            }
            catch (NumberFormatException exception) {
                ScenarioLogger.getInstance().parserWarning("CbVideoCallRawData.<init>: invalid value came from XML to answer nom: answer ignored;");
                ScenarioLogger.getInstance().parserWarning(exception.getLocalizedMessage());
                answerNode = filter.nodeNameNext("answer");
                continue;
            }
            ArrayList<ObjectProperties> incomindData = ObjectProperties.extractListEx(new XmlFilter(answerNode.getChildNodes()));
            if (!this.onAnswerActionList.containsKey(answerNom)) {
                this.onAnswerActionList.put(answerNom, incomindData);
            } else {
                ScenarioLogger.getInstance().parserWarning("CbVideoCallRawData.<init>: answer with nom == " + answerNom + "already exists");
            }
            answerNode = filter.nodeNameNext("answer");
        }
    }

    String getName() {
        return this.name;
    }

    Set<Integer> getIndexesOfAnswerActions() {
        return this.onAnswerActionList.keySet();
    }

    ArrayList<ObjectProperties> getAnswerActions(Integer index) {
        return this.onAnswerActionList.get(index);
    }

    ArrayList<ObjectProperties> getStartActions() {
        return this.onStartActionList;
    }

    ArrayList<ObjectProperties> getFinishActions() {
        return this.onFinishActionList;
    }

    private static final class TagStrings {
        static final String NODE_NAME = "element";
        static final String ANSWER_NODE = "answer";
        static final String FINISH_NODE = "finish";
        static final String START_NODE = "start";
        static final String NAME_ATTRIBUTE_STRING = "name";
        static final String DIALOG_ATTRIBUTE_STRING = "dialog";
        static final String WHO_ATTRIBUTE_STRING = "who";
        static final String TIMECALL_ATTRIBUTE_STRING = "timecall";
        static final String TALK_ATTRIBUTE_STRING = "talkanyway";
        static final String VALUE_ATTRIBUTE_STRING = "value";

        private TagStrings() {
        }
    }
}

