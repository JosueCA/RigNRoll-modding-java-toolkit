// Decompiled with: CFR 0.152
// Class Version: 5
package rnrorg;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import rickroll.log.RickRollLog;
import rnrcore.Log;
import rnrcore.eng;
import rnrorg.INPC;
import rnrorg.IStoreorgelement;
import rnrorg.MissionTime;
import rnrorg.Organizers;
import rnrorg.QuestCustomer;
import rnrorg.QuestNPC;
import rnrorg.RewardForfeit;
import rnrorg.Scorgelement;
import rnrorg.journable;
import rnrorg.journalelement;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;
import xmlutils.NodeList;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class XmlInit {
    private static final String ERROR_DATA = "ERRORR";
    private static final String DEFAULT_IMPORTANCE = "false";
    private static final String DEFAULT_REWARD_FORFEIT = "";
    private static final String DATA_TIME_URGENT = "urgent";
    private final String TOP = "org";
    private final String TAG_ELEMENT = "element";
    private final String TAG_REWARD = "reward";
    private final String TAG_FORFEIT = "forfeit";
    private final String TAG_START = "start";
    private final String TAG_FINISH = "finish";
    public static final String[] TAG_FAIL = new String[]{"fail_timeout_pickup", "fail_timeout_complete", "fail_damaged", "decline"};
    private final String TAG_PERIODS = "periods";
    private final String ATTR_NAME = "name";
    private final String ATTR_DESCRIPTION = "description";
    private final String ATTR_TYPE = "type";
    private final String ATTR_CUSTOMERNPC = "customer_npc";
    private final String ATTR_CUSTOMER = "customer";
    private final String ATTR_IMPORTANT = "importance";
    private final String ATTR_MONEY = "money";
    private final String ATTR_RATING = "rating";
    private final String ATTR_RANK = "rank";
    private final String NODE_FACTION = "faction";
    private final String ATTR_FACTION_NAME = "name";
    private final String ATTR_FACTION_VALUE = "rating";
    private final String ATTR_OUTOFTIMEQUESTITEM = "outoftime_questitem";
    private final String ATTR_OUTOFTIMEMISSION = "outoftime_mission";
    private final String ATTR_TIMECOEF = "timecoef";
    private static boolean is_read = false;

    private XmlInit() {
    }

    public static void read() {
        if (is_read) {
            return;
        }
        is_read = true;
        XmlInit scaner = new XmlInit();
        Vector<String> filenames = new Vector();
        eng.getFilesAllyed(scaner.TOP, filenames);
        
        Vector<String> _names = filenames;
        for (String name : _names) {
            scaner.scan(name);
        }
    }

    public static void deinit() {
        is_read = false;
    }

    public void scan(String filename) {
        try {
            new XmlInit().read(filename);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private RewardForfeit buildRewardForfeit(Node node) {
        if (node == null) {
            return null;
        }
        NamedNodeMap reward_attributes = node.getAttributes();
        String money = this.getAttribute(reward_attributes, "money", DEFAULT_REWARD_FORFEIT);
        String rating = this.getAttribute(reward_attributes, "rating", DEFAULT_REWARD_FORFEIT);
        String rank = this.getAttribute(reward_attributes, "rank", DEFAULT_REWARD_FORFEIT);
        RewardForfeit rewardParams = new RewardForfeit(this.convertDouble(money, 1.0), this.convertDouble(rating, 1.0), this.convertDouble(rank, 1.0));
        xmlutils.Node utilNode = new xmlutils.Node(node);
        NodeList listFactionRewards = utilNode.getNamedChildren("faction");
        if (listFactionRewards != null && !listFactionRewards.isEmpty()) {
            for (xmlutils.Node factionRewardNode : listFactionRewards) {
                String factionName = factionRewardNode.getAttribute("name");
                if (factionName == null) {
                    Log.simpleMessage("Organizer XMLInit. Node faction has no attribute name");
                    continue;
                }
                String factionRewardValueString = factionRewardNode.getAttribute("rating");
                if (factionRewardValueString == null) {
                    Log.simpleMessage("Organizer XMLInit. Node faction has no attribute rating");
                    continue;
                }
                double factionRewardValue = this.convertDouble(factionRewardValueString, 0.0);
                rewardParams.addFactionRating(factionName, factionRewardValue);
            }
        }
        return rewardParams;
    }

    void read(String filename) throws IOException {
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        Node top = doc.getFirstChild();
        if (top.getNodeName().compareToIgnoreCase("org") != 0) {
            return;
        }
        org.w3c.dom.NodeList org_elements = doc.getElementsByTagName("element");
        XmlFilter filter = new XmlFilter(org_elements);
        Node node = filter.nextElement();
        while (null != node) {
            String journaldescription;
            NamedNodeMap attributes = node.getAttributes();
            String name = this.getAttribute("element", attributes, "name");
            String description = this.getAttribute("description", attributes, "description");
            String type = this.getAttribute("description", attributes, "type");
            String customer_npc = null;
            String customer = null;
            int reward_flag = 0;
            int forfeit_flag = 0;
            RewardForfeit forfeitParams = null;
            RewardForfeit rewardParams = null;
            INPC iCustomer = null;
            MissionTime quest_item_time = null;
            MissionTime complete_time = null;
            journalelement startNote = null;
            journalelement successNote = null;
            journable[] failNote = null;
            IStoreorgelement.Type typeQuest = IStoreorgelement.Type.notype;
            if (null != type) {
                try {
                    typeQuest = IStoreorgelement.Type.valueOf(type);
                }
                catch (Exception e) {
                    // empty catch block
                }
            }
            if (!this.hasAttribute(attributes, "customer_npc")) {
                customer = this.getAttribute("element", attributes, "customer");
            } else {
                customer_npc = this.getAttribute("element", attributes, "customer_npc");
            }
            if (null != customer) {
                iCustomer = new QuestCustomer(customer);
            } else if (null != customer_npc) {
                iCustomer = new QuestNPC(customer_npc);
            }
            String importance = this.getAttribute(attributes, "importance", DEFAULT_IMPORTANCE);
            XmlFilter filter_rewards_forfeits = new XmlFilter(node.getChildNodes());
            Node reward = filter_rewards_forfeits.nodeNameNext("reward");
            rewardParams = this.buildRewardForfeit(reward);
            if (rewardParams != null) {
                reward_flag = rewardParams.getFlag();
            }
            filter_rewards_forfeits.goOnStart();
            Node forfeit = filter_rewards_forfeits.nodeNameNext("forfeit");
            forfeitParams = this.buildRewardForfeit(forfeit);
            if (forfeitParams != null) {
                forfeit_flag = forfeitParams.getFlag();
            }
            filter_rewards_forfeits.goOnStart();
            Node start = filter_rewards_forfeits.nodeNameNext("start");
            if (null != start) {
                NamedNodeMap start_attributes = start.getAttributes();
                journaldescription = this.getAttribute("start", start_attributes, "description");
                startNote = new journalelement(journaldescription, null);
            }
            filter_rewards_forfeits.goOnStart();
            Node finish = filter_rewards_forfeits.nodeNameNext("finish");
            if (null != finish) {
                NamedNodeMap finish_attributes = finish.getAttributes();
                journaldescription = this.getAttribute("finish", finish_attributes, "description");
                successNote = new journalelement(journaldescription, null);
            }
            failNote = new journable[TAG_FAIL.length];
            for (int i = 0; i < TAG_FAIL.length; ++i) {
                filter_rewards_forfeits.goOnStart();
                Node fail = filter_rewards_forfeits.nodeNameNext(TAG_FAIL[i]);
                if (null == fail) continue;
                NamedNodeMap fail_attributes = fail.getAttributes();
                String journaldescription2 = this.getAttribute(TAG_FAIL[i], fail_attributes, "description");
                failNote[i] = new journalelement(journaldescription2, null);
            }
            filter_rewards_forfeits.goOnStart();
            Node periods = filter_rewards_forfeits.nodeNameNext("periods");
            while (null != periods) {
                double value;
                String time_coef;
                String mode;
                NamedNodeMap periods_attributes = periods.getAttributes();
                if (this.hasAttribute(periods_attributes, "outoftime_questitem")) {
                    quest_item_time = new MissionTime();
                    mode = this.getAttribute(periods_attributes, "outoftime_questitem", ERROR_DATA);
                    if (mode.compareTo(DATA_TIME_URGENT) == 0) {
                        quest_item_time.makeUrgent();
                    } else if (this.hasAttribute(periods_attributes, "timecoef")) {
                        time_coef = this.getAttribute(periods_attributes, "timecoef", ERROR_DATA);
                        value = this.convertDouble(time_coef, 1.0);
                        quest_item_time.setCoef(value);
                    }
                } else {
                    complete_time = new MissionTime();
                    mode = this.getAttribute("periods", periods_attributes, "outoftime_mission");
                    if (mode.compareTo(DATA_TIME_URGENT) == 0) {
                        complete_time.makeUrgent();
                    } else if (this.hasAttribute(periods_attributes, "timecoef")) {
                        time_coef = this.getAttribute(periods_attributes, "timecoef", ERROR_DATA);
                        value = this.convertDouble(time_coef, 1.0);
                        complete_time.setCoef(value);
                    }
                }
                periods = filter_rewards_forfeits.nodeNameNext("periods");
            }
            
            Scorgelement orgElement = new Scorgelement(name, typeQuest, this.convertToBoolean(importance), reward_flag, rewardParams, forfeit_flag, forfeitParams, description, iCustomer, quest_item_time, complete_time, startNote, successNote, failNote);
            Organizers.getInstance().add(name, orgElement);
            
            // RICK
            // RickRollLog.log("XmlInit read filename: " + filename + "; New Scorgelement with name: " + name + "; typeQuest: " + typeQuest + "; description: " + description);
            // END RICK
            
            
            node = filter.nextElement();
        }
    }

    private boolean hasAttribute(NamedNodeMap attributes, String name) {
        return null != attributes.getNamedItem(name);
    }

    private String getAttribute(NamedNodeMap attributes, String name, String DefaultValue) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            return DefaultValue;
        }
        return val.getTextContent();
    }

    private String getAttribute(String nodename, NamedNodeMap attributes, String name) {
        Node val = attributes.getNamedItem(name);
        if (null == val) {
            eng.log("Parsing organaser errorr. Node " + nodename + " has no attribute " + name);
            return null;
        }
        return val.getTextContent();
    }

    private boolean convertToBoolean(String value) {
        if (null == value) {
            return false;
        }
        return value.compareToIgnoreCase("on") == 0 || value.compareToIgnoreCase("true") == 0;
    }

    private double convertDouble(String value, double defaultValue) {
        double resvalue = defaultValue;
        try {
            resvalue = Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            eng.log("ERRORR orgamazer xml init. Attribute timecoef is not double. Mission initialising failes.");
        }
        return resvalue;
    }

    static org.w3c.dom.NodeList makeXpath(Object input, String expression) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        try {
            XPathExpression expr = xpath.compile(expression);
            Object result = expr.evaluate(input, XPathConstants.NODESET);
            return (org.w3c.dom.NodeList)result;
        }
        catch (Exception e) {
            System.err.print(e.toString());
            return null;
        }
    }

    static ArrayList<String> getMissionsBegunByChannel(Node node) {
        ArrayList<String> res = new ArrayList<String>();
        org.w3c.dom.NodeList actonslist = XmlInit.makeXpath(node, "descendant::action[@startmission]");
        if (actonslist.getLength() != 0) {
            XmlFilter filteractions = new XmlFilter(actonslist);
            Node nodeaction = filteractions.nextElement();
            while (null != nodeaction) {
                NamedNodeMap actionattr = nodeaction.getAttributes();
                Node actionname = actionattr.getNamedItem("name");
                String mission_started = actionname.getNodeValue();
                if (mission_started.compareTo("this") != 0) {
                    res.add(mission_started);
                }
                nodeaction = filteractions.nextElement();
            }
        }
        return res;
    }

    static void xpathtest(String filename) throws IOException {
        FileOutputStream fileout = new FileOutputStream(filename + ".log");
        ObjectOutputStream stream = new ObjectOutputStream(fileout);
        XmlDocument xml = new XmlDocument(filename);
        Document doc = xml.getContent();
        org.w3c.dom.NodeList infochannelslist = XmlInit.makeXpath(doc, "//infochannel");
        XmlFilter filter = new XmlFilter(infochannelslist);
        Node node = filter.nextElement();
        while (null != node) {
            org.w3c.dom.NodeList actonslist = XmlInit.makeXpath(node, "descendant::action[@startmission]");
            if (actonslist.getLength() != 0) {
                NamedNodeMap attrs = node.getAttributes();
                Node channelname = attrs.getNamedItem("name");
                Node channelresource = attrs.getNamedItem("resource");
                String message = "Information channel with name " + channelname.getNodeValue() + " and resource " + channelresource.getNodeValue() + " has startmission actions.\n";
                stream.writeObject(message);
                XmlFilter filteractions = new XmlFilter(actonslist);
                Node nodeaction = filteractions.nextElement();
                while (null != nodeaction) {
                    NamedNodeMap actionattr = nodeaction.getAttributes();
                    Node actionname = actionattr.getNamedItem("name");
                    stream.writeObject("\tStartmission named " + actionname.getNodeValue() + "\n");
                    nodeaction = filteractions.nextElement();
                }
            }
            node = filter.nextElement();
        }
        stream.flush();
        stream.close();
    }

    public static void main(String[] args) {
        ArrayList<Integer> testarr = new ArrayList<Integer>();
        testarr.add(5);
        testarr.add(1);
        testarr.add(6);
        testarr.add(2);
        testarr.add(7);
        testarr.add(3);
        testarr.add(8);
        testarr.add(4);
        Collections.sort(testarr, new ctest());
        try {
            XmlInit.xpathtest("E:\\BAZA\\Data\\Missions\\dnm_missions.xml");
        }
        catch (Exception e) {
            System.err.print(e.toString());
        }
    }

    static class ctest
    implements Comparator {
        ctest() {
        }

        public int compare(Object arg0, Object arg1) {
            return (Integer)arg0 - (Integer)arg1;
        }
    }
}
