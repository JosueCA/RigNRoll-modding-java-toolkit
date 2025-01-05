/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import rnrloggers.ScenarioLogger;
import scenarioXml.IsoCondition;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;
import scriptActions.ScriptAction;
import scriptEvents.SpecialObjectEvent;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
final class IsoQuestItemTask
implements Comparable {
    private static final int ACTION_LIST_DEFAULT_CAPACITY = 32;
    private static final int DEFAULT_PHASE = 1;
    private static final int TOKENS_IN_PHASE_STRING = 2;
    private SpecialObjectEvent.EventType eventType;
    private int phase;
    public static final int DEFAULT_UID = -1;
    private int uid;
    private boolean finish;
    private ArrayList<ObjectProperties> actionList;
    private ArrayList<ScriptAction> readyActionList;
    private IsoCondition condition;

    IsoQuestItemTask(Node target) {
        block10: {
            this.eventType = SpecialObjectEvent.EventType.any;
            this.phase = 0;
            this.uid = -1;
            this.finish = false;
            this.actionList = new ArrayList(32);
            this.readyActionList = new ArrayList();
            this.condition = null;
            if (null == target) {
                throw new IllegalArgumentException("target must be non-null reference");
            }
            NamedNodeMap attributes = target.getAttributes();
            try {
                String phaseAndUid = XmlDocument.getAttributeValue(attributes, "phase", Integer.toString(1) + '.' + Integer.toString(-1));
                StringTokenizer tokenizer = new StringTokenizer(phaseAndUid, ".");
                if (2 != tokenizer.countTokens()) {
                    ScenarioLogger.getInstance().parserWarning("Invalid isoquest.item.task.phase, set default phase nom == 1 default uid == -1");
                    ScenarioLogger.getInstance().parserWarning("Phase must match to pattern '\num.num' where num is possetive integer");
                }
                this.phase = tokenizer.hasMoreTokens() ? Integer.parseInt(tokenizer.nextToken()) : 1;
                this.uid = tokenizer.hasMoreTokens() ? Integer.parseInt(tokenizer.nextToken()) : -1;
            }
            catch (NumberFormatException exception) {
                this.phase = 1;
                this.uid = -1;
                ScenarioLogger.getInstance().parserWarning("Invalid isoquest.item.task.phase, set default phase nom == 1 default uid == -1");
                ScenarioLogger.getInstance().parserWarning(exception.getLocalizedMessage());
            }
            this.finish = Boolean.parseBoolean(XmlDocument.getAttributeValue(attributes, "finish", "false"));
            int actMainAttrIndex = XmlDocument.getIndexOfAttributeWithoutContent(attributes);
            if (-1 != actMainAttrIndex) {
                try {
                    Node eventTypeNode = attributes.item(actMainAttrIndex);
                    if (null != eventTypeNode) {
                        this.eventType = SpecialObjectEvent.EventType.valueOf(eventTypeNode.getNodeName());
                        break block10;
                    }
                    this.eventType = SpecialObjectEvent.EventType.none;
                }
                catch (IllegalArgumentException exception) {
                    this.eventType = SpecialObjectEvent.EventType.none;
                    System.err.println("illegal eventType came from XML, must be one of: any, f2, exit, enter");
                    System.err.println(exception.getLocalizedMessage());
                    exception.printStackTrace(System.err);
                }
            } else {
                this.eventType = SpecialObjectEvent.EventType.none;
            }
        }
        XmlFilter filter = new XmlFilter(target.getChildNodes());
        this.condition = new IsoCondition();
        if (!this.condition.extractFromNode(filter.nodeNameNext("cond"))) {
            this.condition = null;
        }
        filter.goOnStart();
        this.actionList = ObjectProperties.extractListEx(filter);
    }

    IsoQuestItemTask(List<ScriptAction> actions, IsoCondition condition) {
        this.eventType = SpecialObjectEvent.EventType.any;
        this.phase = 0;
        this.uid = -1;
        this.finish = false;
        this.actionList = new ArrayList(32);
        this.readyActionList = new ArrayList();
        this.condition = null;
        this.readyActionList.addAll(actions);
        this.condition = condition;
    }

    IsoCondition getCondition() {
        return this.condition;
    }

    SpecialObjectEvent.EventType getEventType() {
        return this.eventType;
    }

    int getPhase() {
        return this.phase;
    }

    int getUid() {
        return this.uid;
    }

    public boolean isLastPhase() {
        return this.finish;
    }

    void getActionList(List<ObjectProperties> properties, List<ScriptAction> actions) {
        properties.addAll(this.actionList);
        actions.addAll(this.readyActionList);
    }

    public int compareTo(Object o) {
        IsoQuestItemTask compareTarget = (IsoQuestItemTask)o;
        if (this.phase < compareTarget.phase) {
            return -1;
        }
        if (this.phase > compareTarget.phase) {
            return 1;
        }
        return 0;
    }
}

