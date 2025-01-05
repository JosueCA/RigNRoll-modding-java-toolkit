/*
 * Decompiled with CFR 0.151.
 */
package xmlserialization;

import java.io.PrintStream;
import org.w3c.dom.Node;
import players.Crew;
import rnrcore.XmlSerializable;
import xmlserialization.CrewSerialization;

public class CrewXmlSerialization
implements XmlSerializable {
    private static CrewXmlSerialization instance = new CrewXmlSerialization();

    public static CrewXmlSerialization getInstance() {
        return instance;
    }

    public void saveToStreamAsSetOfXmlNodes(PrintStream stream) {
        CrewSerialization.serializeXML(Crew.getInstance(), stream);
    }

    public void loadFromNode(Node node) {
        xmlutils.Node utilNode = new xmlutils.Node(node);
        Crew crew = CrewSerialization.deserializeXML(utilNode);
        Crew.setInstance(crew);
    }

    public String getRootNodeName() {
        return "crew";
    }

    public void yourNodeWasNotFound() {
    }
}

