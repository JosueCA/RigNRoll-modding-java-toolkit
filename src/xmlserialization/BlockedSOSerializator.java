/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;
import rnrcore.CoreTime;
import rnrcore.IXMLSerializable;
import rnrscenario.SoBlock;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.ListElementSerializator;
import xmlserialization.Log;
import xmlutils.Node;
import xmlutils.NodeList;

public class BlockedSOSerializator
implements IXMLSerializable {
    private SoBlock m_blocker;

    public BlockedSOSerializator(SoBlock object) {
        this.m_blocker = object;
    }

    public BlockedSOSerializator() {
        this.m_blocker = null;
    }

    public static String getNodeName() {
        return "blockso";
    }

    public void serialize(PrintStream stream) {
        Helper.openNode(stream, BlockedSOSerializator.getNodeName());
        Vector<SoBlock.Blocked_SO> blockedItems = this.m_blocker.getBlocked();
        for (SoBlock.Blocked_SO item : blockedItems) {
            ListElementSerializator.serializeXMLListelementOpen(stream);
            ArrayList<Pair<String, String>> attributes = new ArrayList<Pair<String, String>>();
            if (item.getName() != null) {
                Helper.addAttribute("name", item.getName(), attributes);
            }
            Helper.addAttribute("type", item.getType(), attributes);
            Helper.printOpenNodeWithAttributes(stream, "blocker", attributes);
            SoBlock.TimeOutCondition finishCondition = item.getCondition();
            if (finishCondition != null) {
                CoreTime startTime;
                Helper.printOpenNodeWithAttributes(stream, "condition", Helper.createSingleAttribute("timecondition", ""));
                SoBlock.TimeOutCondition timeOutCondition = finishCondition;
                CoreTime deltaTime = timeOutCondition.getDeltatime();
                if (deltaTime != null) {
                    Helper.openNode(stream, "deltatime");
                    CoreTimeSerialization.serializeXML(deltaTime, stream);
                    Helper.closeNode(stream, "deltatime");
                }
                if ((startTime = timeOutCondition.getTimeStart()) != null) {
                    Helper.openNode(stream, "starttime");
                    CoreTimeSerialization.serializeXML(startTime, stream);
                    Helper.closeNode(stream, "starttime");
                }
                Helper.closeNode(stream, "condition");
            }
            Helper.closeNode(stream, "blocker");
            ListElementSerializator.serializeXMLListelementClose(stream);
        }
        Helper.closeNode(stream, BlockedSOSerializator.getNodeName());
    }

    public void deSerialize(Node node) {
        NodeList listFilterNode = node.getNamedChildren(ListElementSerializator.getNodeName());
        if (listFilterNode == null || listFilterNode.isEmpty()) {
            return;
        }
        Vector<SoBlock.Blocked_SO> blocked = new Vector<SoBlock.Blocked_SO>();
        for (Node listElementNode : listFilterNode) {
            Node blockSoElementNode = listElementNode.getNamedChild("blocker");
            String nameSoAttributeString = blockSoElementNode.getAttribute("name");
            String typeSoAttributeString = blockSoElementNode.getAttribute("type");
            String soName = nameSoAttributeString;
            int soType = Helper.ConvertToIntegerAndWarn(typeSoAttributeString, "type", "Node blocker has no attribute type");
            SoBlock.TimeOutCondition finishCondition = null;
            Node conditionNode = blockSoElementNode.getNamedChild("condition");
            if (conditionNode != null) {
                String attrTimeCondition = conditionNode.getAttribute("timecondition");
                if (attrTimeCondition != null) {
                    CoreTime deltaTime = this.getCoreTimeSerializationFromNode(conditionNode, "deltatime");
                    CoreTime startTime = this.getCoreTimeSerializationFromNode(conditionNode, "starttime");
                    finishCondition = new SoBlock.TimeOutCondition(startTime, deltaTime);
                } else {
                    Log.error("Node condition has non of attributes nocondition or timecondition");
                }
            }
            blocked.add(new SoBlock.Blocked_SO(soType, soName, finishCondition));
        }
        SoBlock.getInstance().setBlocked(blocked);
    }

    private CoreTime getCoreTimeSerializationFromNode(Node node, String childName) {
        Node timeContainerNode = node.getNamedChild(childName);
        if (timeContainerNode == null) {
            Log.error("getCoreTimeSerializationFromNode. Node " + node.getName() + " has no child node with name " + childName);
            return null;
        }
        Node timeNode = timeContainerNode.getNamedChild(CoreTimeSerialization.getNodeName());
        if (timeNode == null) {
            Log.error("getCoreTimeSerializationFromNode. Node " + childName + " has no child node with name " + CoreTimeSerialization.getNodeName());
            return null;
        }
        CoreTime result = CoreTimeSerialization.deserializeXML(timeNode);
        if (result == null) {
            Log.error("getCoreTimeSerializationFromNode. result time is null.");
            return null;
        }
        return result;
    }
}

