/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.w3c.dom.Node;
import scenarioXml.ObjectProperties;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;
import scriptEvents.EventChecker;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class IsoCondition {
    private ObjectProperties properties = null;
    private EventChecker readyCondition = null;
    private IsoCondition andCondition = null;
    private LinkedList<IsoCondition> orConditions = null;

    public IsoCondition() {
    }

    public IsoCondition(EventChecker readyCondition) {
        this.readyCondition = readyCondition;
    }

    public EventChecker getReadyCondition() {
        return this.readyCondition;
    }

    public boolean extractFromNode(Node root) {
        if (null == root) {
            return false;
        }
        if (0 != root.getNodeName().compareTo("cond")) {
            throw new IllegalArgumentException("root is not valid condition node");
        }
        Properties thisConditionParams = new Properties();
        String thisCondidionName = XmlDocument.extractMainAttribAndParams(root.getAttributes(), thisConditionParams);
        this.properties = new ObjectProperties(thisCondidionName, thisConditionParams);
        this.andCondition = new IsoCondition();
        if (!this.andCondition.extractFromNode(new XmlFilter(root.getChildNodes()).nodeNameNext("cond"))) {
            this.andCondition = null;
        }
        for (Node current = root.getNextSibling(); null != current; current = current.getNextSibling()) {
            if (0 != current.getNodeName().compareTo("cond")) continue;
            IsoCondition condition = new IsoCondition();
            Properties params2 = new Properties();
            String name = XmlDocument.extractMainAttribAndParams(current.getAttributes(), params2);
            condition.properties = new ObjectProperties(name, params2);
            IsoCondition childCondition = new IsoCondition();
            if (!childCondition.extractFromNode(new XmlFilter(current.getChildNodes()).nodeNameNext("cond"))) {
                childCondition = null;
            }
            condition.andCondition = childCondition;
            if (null == this.orConditions) {
                this.orConditions = new LinkedList();
            }
            this.orConditions.add(condition);
        }
        return true;
    }

    ObjectProperties getProperties() {
        return this.properties;
    }

    IsoCondition getAndCondition() {
        return this.andCondition;
    }

    List<IsoCondition> getOrList() {
        return this.orConditions;
    }
}

