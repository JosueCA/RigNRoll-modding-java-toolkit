/*
 * Decompiled with CFR 0.151.
 */
package scenarioXml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class ObjectProperties {
    private static final int DEFAULT_CAPACITY = 8;
    private Properties params = null;
    private String name = null;
    private ObjectProperties childObject = null;

    public static LinkedList<ObjectProperties> extractList(Node root, String ... nodesToIgnore) {
        if (null == root) {
            throw new IllegalArgumentException("root must be non-null reference");
        }
        LinkedList<ObjectProperties> out = new LinkedList<ObjectProperties>();
        XmlFilter filter = new XmlFilter(root.getChildNodes());
        Node actionObjectNode = filter.nextElement();
        block0: while (null != actionObjectNode) {
            for (String nameToIgnore : nodesToIgnore) {
                if (0 != nameToIgnore.compareToIgnoreCase(actionObjectNode.getNodeName())) continue;
                actionObjectNode = filter.nextElement();
                continue block0;
            }
            ObjectProperties actionObject = new ObjectProperties(actionObjectNode.getNodeName());
            NamedNodeMap attributesMap = actionObjectNode.getAttributes();
            for (int i = attributesMap.getLength() - 1; 0 <= i; --i) {
                if (null == attributesMap.item(i).getTextContent() || 0 == attributesMap.item(i).getTextContent().length()) {
                    actionObject.setParam("name", attributesMap.item(i).getNodeName());
                    continue;
                }
                actionObject.setParam(attributesMap.item(i).getNodeName(), attributesMap.item(i).getTextContent());
            }
            out.add(actionObject);
            actionObjectNode = filter.nextElement();
        }
        return out;
    }

    public static ArrayList<ObjectProperties> extractListEx(XmlFilter filter, String nodesNames) {
        if (null == filter) {
            return new ArrayList<ObjectProperties>();
        }
        ArrayList<ObjectProperties> out = new ArrayList<ObjectProperties>(8);
        Node currentActionNode = filter.nodeNameNext(nodesNames);
        while (null != currentActionNode) {
            NamedNodeMap actAttrs = currentActionNode.getAttributes();
            if (0 < actAttrs.getLength()) {
                Properties params2 = new Properties();
                String className = XmlDocument.extractMainAttribAndParams(actAttrs, params2);
                ObjectProperties actionClassParams = new ObjectProperties(className, params2);
                XmlFilter actionDelayNodeDetector = new XmlFilter(currentActionNode.getChildNodes());
                Node delayNode = actionDelayNodeDetector.nodeNameNext("wait");
                if (null != delayNode) {
                    String subClassName = "wait";
                    Properties subClassParams = new Properties();
                    subClassParams.put("name", XmlDocument.getAttributeValue(delayNode.getAttributes(), "name", "unkonwn"));
                    subClassParams.put("days", XmlDocument.getAttributeValue(delayNode.getAttributes(), "days", "1"));
                    subClassParams.put("hours", XmlDocument.getAttributeValue(delayNode.getAttributes(), "hours", "0"));
                    actionClassParams.createChild(subClassName, subClassParams);
                }
                out.add(actionClassParams);
            }
            currentActionNode = filter.nodeNameNext(nodesNames);
        }
        return out;
    }

    public static ArrayList<ObjectProperties> extractListEx(XmlFilter filter) {
        return ObjectProperties.extractListEx(filter, "action");
    }

    ObjectProperties(String name, Properties params2) {
        if (null == name || null == params2) {
            throw new IllegalArgumentException("name and params must be non-null reference");
        }
        this.name = name;
        this.params = params2;
    }

    private ObjectProperties(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name must be non-null reference");
        }
        this.name = name;
        this.params = new Properties();
    }

    private void setParam(String paramName, String paramValue) {
        if (null == paramName || null == paramValue) {
            throw new IllegalArgumentException("paramName and paramValue must be non-null references");
        }
        if (null == this.params) {
            this.params = new Properties();
        }
        this.params.setProperty(paramName, paramValue);
    }

    public String getName() {
        return this.name;
    }

    public Properties getParams() {
        return this.params;
    }

    ObjectProperties getChildObject() {
        return this.childObject;
    }

    void createChild(String name, Properties params2) {
        if (null == name || null == params2) {
            throw new IllegalArgumentException("name and params must be non-null references");
        }
        this.childObject = new ObjectProperties(name, params2);
    }
}

