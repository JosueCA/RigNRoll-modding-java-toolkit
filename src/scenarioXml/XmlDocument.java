// Decompiled with: CFR 0.152
// Class Version: 5
package scenarioXml;

import auxil.DInputStream2;
import auxil.XInputStreamCreate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import rickroll.log.RickRollLog;
import rnrloggers.ScenarioLogger;
import scenarioXml.XmlFilter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class XmlDocument {
    private Document document;
    private boolean schemaValidationPassed = true;

    private void processException(String text, Exception exception) throws IOException {
        ScenarioLogger.getInstance().parserLog(Level.SEVERE, text);
        ScenarioLogger.getInstance().parserLog(Level.SEVERE, exception.getMessage());
        throw new IOException(exception.getMessage());
    }

    public XmlDocument(byte[] dataArray) throws IOException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.document = documentBuilder.parse(new DInputStream2(new ByteArrayInputStream(dataArray)));
        }
        catch (ParserConfigurationException exception) {
            this.processException("Failed to parse document from byte array", exception);
        }
        catch (IOException exception) {
            this.processException("IO error while loading dovument from byte array", exception);
        }
        catch (SAXException exception) {
            this.processException("SAXException while parsing document from byte array", exception);
        }
    }

    public XmlDocument(String path, String schemaPath) throws IOException {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            this.document = documentBuilder.parse(new DInputStream2(XInputStreamCreate.open(path)));
            
            if (null != schemaPath && 0 < schemaPath.length()) {
                SchemaFactory schemaBuilder = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                StreamSource schemaSource = new StreamSource(new File(schemaPath));
                Schema xmlSchema = schemaBuilder.newSchema(schemaSource);
                Validator xmlChecker = xmlSchema.newValidator();
                try {
                    xmlChecker.validate(new DOMSource(this.document));
                }
                catch (SAXException e) {
                    ScenarioLogger.getInstance().parserLog(Level.SEVERE, "Loaded XML file is invalid: " + e.getLocalizedMessage());
                    this.schemaValidationPassed = false;
                }
            }
        }
        catch (ParserConfigurationException exception) {
            this.processException("Failed to parse document " + path, exception);
        }
        catch (IOException exception) {
            this.processException("Failed to open document " + path, exception);
        }
        catch (SAXException exception) {
            this.processException("SAXException while parsing document " + path, exception);
        }
    }

    public XmlDocument(String path) throws IOException {
        this(path, null);
    }

    public Document getContent() {
        return this.document;
    }

    public boolean validationPassed() {
        return this.schemaValidationPassed;
    }

    static Properties extractAttribs(NamedNodeMap map) {
        if (null == map || 0 >= map.getLength()) {
            return null;
        }
        Properties out = new Properties();
        for (int i = 0; i < map.getLength(); ++i) {
            out.put(map.item(i).getNodeName(), map.item(i).getTextContent());
        }
        return out;
    }

    static String extractMainAttribAndParams(NamedNodeMap map, Properties params) {
        if (null == params) {
            throw new IllegalArgumentException("params must be non-null reference");
        }
        if (null == map || 0 >= map.getLength()) {
            return "unknown";
        }
        int actMainAttrIndex = XmlDocument.getIndexOfAttributeWithoutContent(map);
        String mainAttribute = -1 != actMainAttrIndex ? map.item(actMainAttrIndex).getNodeName() : "unknown";
        Properties extraxted = XmlDocument.extractAttribs(map);
        if (null != extraxted && 0 < extraxted.size()) {
            extraxted.remove(mainAttribute);
            params.putAll(extraxted);
        }
        return mainAttribute;
    }

    static String getAttributeValue(NamedNodeMap attributes, String name, String defaultValue) {
        Node value = attributes.getNamedItem(name);
        if (null != value) {
            return value.getTextContent();
        }
        return defaultValue;
    }

    static int getIndexOfAttributeWithoutContent(NamedNodeMap map) {
        for (int i = map.getLength() - 1; 0 <= i; --i) {
            if (null != map.item(i).getTextContent() && 0 < map.item(i).getTextContent().length()) continue;
            return i;
        }
        return -1;
    }

    static String getSingleChildNodeAttribute(Node rootNode, String childNodeName, String attributeName) {
        Node found = new XmlFilter(rootNode.getChildNodes()).nodeNameNext(childNodeName);
        if (null != found) {
            return XmlDocument.getAttributeValue(found.getAttributes(), attributeName, null);
        }
        return null;
    }

    private Collection<Node> extractTags(String rootNode, String tag) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }
        if (null == tag) {
            throw new IllegalArgumentException("tag must be non-null reference");
        }
        Document document = this.getContent();
        if (null == document) {
            throw new IOException("document wasn't loaded");
        }
        LinkedList<Node> out = new LinkedList<Node>();
        XmlFilter filter = new XmlFilter(document.getElementsByTagName(rootNode));
        Node xmlNode = filter.nextElement();
        while (null != xmlNode) {
            XmlFilter messagesFilter = new XmlFilter(xmlNode.getChildNodes());
            Node messageTextNode = messagesFilter.nodeNameNext(tag);
            while (null != messageTextNode) {
                out.add(messageTextNode);
                messageTextNode = messagesFilter.nodeNameNext(tag);
            }
            xmlNode = filter.nextElement();
        }
        return out;
    }

    public Collection<String> extractTagsTextContent(String rootNode, String tagName) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }
        if (null == tagName) {
            throw new IllegalArgumentException("tagName must be non-null reference");
        }
        Collection<Node> nodeList = this.extractTags(rootNode, tagName);
        LinkedList<String> out = new LinkedList<String>();
        for (Node tag : nodeList) {
            out.add(tag.getTextContent());
        }
        return out;
    }

    public Collection<Properties> extractPropertiesFromAttributes(String rootNode, String tagName) throws IOException {
        if (null == rootNode) {
            throw new IllegalArgumentException("rootNode must be non-null reference");
        }
        if (null == tagName) {
            throw new IllegalArgumentException("tagName must be non-null reference");
        }
        Collection<Node> nodeList = this.extractTags(rootNode, tagName);
        LinkedList<Properties> out = new LinkedList<Properties>();
        for (Node tag : nodeList) {
            Properties extracted = XmlDocument.extractAttribs(tag.getAttributes());
            if (null == extracted || 0 >= extracted.size()) continue;
            out.add(extracted);
        }
        return out;
    }
}
