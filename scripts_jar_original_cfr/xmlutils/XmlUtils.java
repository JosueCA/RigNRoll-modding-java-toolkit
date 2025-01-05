/*
 * Decompiled with CFR 0.151.
 */
package xmlutils;

import auxil.DInputStream2;
import auxil.XInputStreamCreate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import rnrcore.eng;
import xmlutils.Node;

public class XmlUtils {
    private static final XPath xpath = XPathFactory.newInstance().newXPath();

    public static XPath getXPath() {
        return xpath;
    }

    public static Node parse(String fileName) {
        Document doc;
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docbuild = fac.newDocumentBuilder();
            doc = docbuild.parse(new DInputStream2(XInputStreamCreate.open(fileName)));
        }
        catch (Exception e) {
            eng.err("parsing error: " + e.toString());
            return null;
        }
        NodeList topChildren = doc.getChildNodes();
        if (topChildren.getLength() != 1) {
            eng.err("parse XmlUtils Undefinite behaiboir.");
            return null;
        }
        return new Node(topChildren.item(0));
    }

    public static XPathExpression makeXpath(String expression) {
        try {
            return xpath.compile(expression);
        }
        catch (Exception e) {
            System.err.println(e.toString());
            return null;
        }
    }
}

