/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import java.util.ArrayList;
import org.w3c.dom.Node;
import rnrcore.XmlSerializable;
import rnrorg.journable;
import rnrorg.journal;
import xmlserialization.Helper;
import xmlserialization.JournalElementSerialization;
import xmlutils.NodeList;

public class JournalSerialization
implements XmlSerializable {
    private static JournalSerialization instance = new JournalSerialization();

    public static JournalSerialization getInstance() {
        return instance;
    }

    public String getRootNodeName() {
        return JournalSerialization.getNodeName();
    }

    public void loadFromNode(Node node) {
        JournalSerialization.deserializeXML(new xmlutils.Node(node));
    }

    public void yourNodeWasNotFound() {
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        JournalSerialization.serializeXML(stream);
    }

    public static String getNodeName() {
        return "journal";
    }

    public static void serializeXML(PrintStream stream) {
        Helper.openNode(stream, JournalSerialization.getNodeName());
        ArrayList<journable> alljournalelements = journal.getInstance().getAlljournalelements();
        for (journable journalElement : alljournalelements) {
            JournalElementSerialization.serializeXML(journalElement, stream);
        }
        Helper.closeNode(stream, JournalSerialization.getNodeName());
    }

    public static void deserializeXML(xmlutils.Node node) {
        NodeList journalElements = node.getNamedChildren(JournalElementSerialization.getNodeName());
        ArrayList<journable> allElements = new ArrayList<journable>();
        if (null != journalElements && !journalElements.isEmpty()) {
            for (xmlutils.Node element : journalElements) {
                journable jou = JournalElementSerialization.deserializeXML(element);
                allElements.add(jou);
            }
        }
        journal.getInstance().setAlljournalelements(allElements);
    }
}

