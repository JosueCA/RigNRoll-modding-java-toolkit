/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrscenario.CBCallsStorage;
import rnrscenario.CBVideoStroredCall;
import rnrscr.CBVideocallelemnt;
import rnrscr.cbapparatus;
import scenarioUtils.Pair;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlutils.NodeList;

public class CBVideoSerializator
implements XmlSerializable {
    private static CBVideoSerializator instance = null;

    private CBVideoSerializator() {
    }

    public static CBVideoSerializator getInstance() {
        if (instance == null) {
            instance = new CBVideoSerializator();
        }
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        CBVideoSerializator.serializeXML(stream);
    }

    public void loadFromNode(Node node) {
        CBVideoSerializator.deserialize(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public String getRootNodeName() {
        return CBVideoSerializator.getNodeName();
    }

    public static String getNodeName() {
        return "cbvideo";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, CBVideoSerializator.getNodeName());
        ArrayList<CBVideocallelemnt> callers = cbapparatus.getInstance().gCallers();
        for (CBVideocallelemnt element : callers) {
            if (element.gFinished()) continue;
            List<Pair<String, String>> attributes = Helper.createSingleAttribute("name", element.getResourceName());
            if (element.whocalls != null) {
                Helper.addAttribute("identitie", element.whocalls.getIdentitie(), attributes);
            }
            Helper.printClosedNodeWithAttributes(stream, "single_cbcall", attributes);
        }
        Helper.closeNode(stream, CBVideoSerializator.getNodeName());
    }

    public static void deserialize(xmlutils.Node node) {
        NodeList listElementsNodes = node.getNamedChildren("single_cbcall");
        if (listElementsNodes == null || listElementsNodes.isEmpty()) {
            return;
        }
        for (xmlutils.Node elementNode : listElementsNodes) {
            String dialogName = elementNode.getAttribute("name");
            if (dialogName == null) {
                Log.error("CBVideoSerializator has no attribute name on node single_cbcall");
            }
            String identitie = elementNode.getAttribute("identitie");
            CBVideoStroredCall call = CBCallsStorage.getInstance().getStoredCall(dialogName);
            if (identitie == null) {
                call.makecall();
                continue;
            }
            call.makecall(identitie, "from serialize");
        }
    }
}

