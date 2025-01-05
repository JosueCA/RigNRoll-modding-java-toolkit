/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrcore.eng;
import rnrorg.ScenarioMissionItem;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;

public class ScenarioMissions {
    public static final String TOP = "scenario_missions";
    public static final String TAG_ELEMENT = "element";
    public static final String MISSION_NAME = "missionname";
    public static final String ORG_NAME = "org";
    public static final String POINT_NAME = "point";
    public static final String NAME = "name";
    public static final String MOVE_TIME = "move_time";
    public static final String NEED_FINISH_ICON = "need_finish_icon";
    public static final String TIME_ON_MISSION = "time";
    private HashMap<String, ScenarioMissionItem> scenariomissions = new HashMap();
    private static ScenarioMissions instance = null;

    private ScenarioMissions() {
        this.read();
    }

    public static ScenarioMissions getInstance() {
        if (null == instance) {
            instance = new ScenarioMissions();
        }
        return instance;
    }

    public ScenarioMissionItem get(String name) {
        if (!this.scenariomissions.containsKey(name)) {
            return null;
        }
        return this.scenariomissions.get(name);
    }

    private void read() {
        Vector filenames = new Vector();
        if (!eng.noNative) {
            eng.getFilesAllyed(TOP, filenames);
        }
        Vector _names = filenames;
        for (String name : _names) {
            try {
                this.read(name);
            }
            catch (Exception e) {
                eng.err(e.toString());
            }
        }
    }

    private void read(String filename) throws IOException {
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        Node top = doc.getFirstChild();
        if (top.getNodeName().compareToIgnoreCase(TOP) != 0) {
            return;
        }
        NodeList org_elements = doc.getElementsByTagName(TAG_ELEMENT);
        XmlFilter filter = new XmlFilter(org_elements);
        Node node = filter.nextElement();
        while (null != node) {
            NamedNodeMap attributes = node.getAttributes();
            String mission_name = this.getAttribute(TAG_ELEMENT, attributes, MISSION_NAME);
            String org_name = this.getAttribute(TAG_ELEMENT, attributes, ORG_NAME);
            String point_name = this.getAttribute(TAG_ELEMENT, attributes, POINT_NAME);
            String name = this.getAttribute(TAG_ELEMENT, attributes, NAME);
            double time_on_mission = this.getDoubleAttribute(TAG_ELEMENT, attributes, TIME_ON_MISSION, false);
            boolean move_time = this.getBooleanAttribute(TAG_ELEMENT, attributes, MOVE_TIME, false);
            boolean need_finish_icon = this.getBooleanAttributeDefault(TAG_ELEMENT, attributes, NEED_FINISH_ICON, true);
            this.scenariomissions.put(name, new ScenarioMissionItem(mission_name, org_name, point_name, time_on_mission, move_time, need_finish_icon));
            node = filter.nextElement();
        }
    }

    private String getAttribute(String nodename, NamedNodeMap attributes, String name) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            return null;
        }
        return val.getTextContent();
    }

    private double getDoubleAttribute(String nodename, NamedNodeMap attributes, String name, boolean make_warning) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            if (make_warning) {
                eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            }
            return -1.0;
        }
        String textval = val.getTextContent();
        double value = -1.0;
        try {
            value = Double.parseDouble(textval);
        }
        catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value " + textval);
        }
        return value;
    }

    private boolean getBooleanAttribute(String nodename, NamedNodeMap attributes, String name, boolean make_warning) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            if (make_warning) {
                eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            }
            return false;
        }
        String textval = val.getTextContent();
        boolean value = false;
        try {
            value = Boolean.parseBoolean(textval);
        }
        catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value " + textval);
        }
        return value;
    }

    private boolean getBooleanAttributeDefault(String nodename, NamedNodeMap attributes, String name, boolean default_value) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            return default_value;
        }
        String textval = val.getTextContent();
        boolean value = default_value;
        try {
            value = Boolean.parseBoolean(textval);
        }
        catch (NumberFormatException e) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no bad attribute " + name + " with value " + textval);
        }
        return value;
    }
}

