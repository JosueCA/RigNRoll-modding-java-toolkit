/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.List;
import rnrcore.CoreTime;
import rnrcore.MacroBody;
import rnrorg.JournalActiveListener;
import rnrorg.journable;
import rnrorg.journalelement;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlserialization.Log;
import xmlserialization.MacroSerialization;
import xmlutils.Node;
import xmlutils.NodeList;

public class JournalElementSerialization {
    private static int version = 1;

    public static String getNodeName() {
        return "journalElement";
    }

    public static void serializeXML(journable journalElement, PrintStream stream) {
        MacroBody macro;
        CoreTime expirationTime;
        Helper.openNode(stream, JournalElementSerialization.getNodeName(), version);
        CoreTime time = journalElement.getTime();
        if (null != time) {
            Helper.openNode(stream, "date");
            CoreTimeSerialization.serializeXML(time, stream);
            Helper.closeNode(stream, "date");
        }
        if (null != (expirationTime = journalElement.getDeactivationTime())) {
            Helper.openNode(stream, "journalElement");
            CoreTimeSerialization.serializeXML(expirationTime, stream);
            Helper.closeNode(stream, "journalElement");
        }
        if (null != (macro = journalElement.getMacroBody())) {
            MacroSerialization.serializeXML(macro, stream);
        }
        List<String> answerListeners = journalElement.getListenersResources();
        for (String resourceName : answerListeners) {
            Helper.printClosedNodeWithAttributes(stream, "answerlistener", Helper.createSingleAttribute("name", resourceName));
        }
        Helper.closeNode(stream, JournalElementSerialization.getNodeName());
    }

    public static journable deserializeXML(Node node) {
        NodeList listenersNodes;
        String versionString = node.getAttribute("version");
        int savedVersion = versionString == null ? 0 : Integer.parseInt(versionString);
        CoreTime time = null;
        CoreTime expirationTime = null;
        MacroBody macroBody = null;
        if (savedVersion < version) {
            Node timeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());
            if (null != timeNode) {
                time = CoreTimeSerialization.deserializeXML(timeNode);
            }
        } else {
            Node timeNode;
            Node expirationDateNode;
            Node timeNode2;
            Node dateNode = node.getNamedChild("date");
            if (dateNode != null && null != (timeNode2 = dateNode.getNamedChild(CoreTimeSerialization.getNodeName()))) {
                time = CoreTimeSerialization.deserializeXML(timeNode2);
            }
            if ((expirationDateNode = node.getNamedChild("journalElement")) != null && null != (timeNode = expirationDateNode.getNamedChild(CoreTimeSerialization.getNodeName()))) {
                expirationTime = CoreTimeSerialization.deserializeXML(timeNode);
            }
        }
        Node body = node.getNamedChild(MacroSerialization.getNodeName());
        if (null != body) {
            macroBody = MacroSerialization.deserializeXML(body);
        } else {
            Log.error("JournalElementSerialization in deserializeXML has no node named " + MacroSerialization.getNodeName());
        }
        journalelement jou = new journalelement(macroBody);
        if (null != time) {
            jou.setTime(time);
        }
        if (null != expirationTime) {
            jou.setDeactivationTime(expirationTime);
        }
        if ((listenersNodes = node.getNamedChildren("answerlistener")) != null) {
            for (Node listenerNode : listenersNodes) {
                String attributeName = listenerNode.getAttribute("name");
                if (attributeName == null) {
                    Log.error("Node answerlistener has no attribute name");
                    continue;
                }
                jou.makeQuestionFor(new JournalActiveListener(attributeName));
            }
        }
        jou.addToList();
        return jou;
    }
}

