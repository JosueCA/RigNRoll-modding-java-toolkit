// Decompiled with: CFR 0.152
// Class Version: 5
package rickroll.core;

import rickroll.auxil.DInputStream;
import rickroll.auxil.XInputStreamCreate;
import rickroll.core.XmlFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Application {
    // private static final String DEFAULT_FOLDER = ".\\";
	private static final String DEFAULT_FOLDER = ".\\";
    
    
    private static final int FOLDER_ARGUMENT_INDEX = 0;
    private static final String NAMERESOLVE_TABLE_NODE_PREFIX = "ptcl_";
    private static final String NAMERESOLVE_TABLE_FILE_NAME = "nameresolve.xml";
    private static final String NAMERESOLVE_TABLE_RECORD = "item";
    private static final String TABLE_KEY = "ID";
    private static final String TABLE_VALUE = "value";
    private static final String PARTICLE_NODE_NAME = "particle";
    private static final String PARTICLE_MATERIAL_TYPE_NODE_NAME = "type";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] stringArray) {
        Object object;
        Node node;
        Node node2;
        NodeList object2;
        File file;
        String string = DEFAULT_FOLDER;
        if (stringArray.length > 0) {
            file = new File(stringArray[0]);
            if (file.exists() && file.isDirectory()) {
                string = file.getAbsolutePath();
            } else {
                System.out.println(stringArray[0] + "doesn't exists or it is not a folder");
            }
        }
        file = new File(string);
        File file2 = new File(string + '\\' + NAMERESOLVE_TABLE_FILE_NAME);
        if (!file2.exists()) {
            System.out.println("nameresolve.xml wasn't found; execution can not be continued");
        }
        File[] fileArray = file.listFiles(new FileFilter(){

            public boolean accept(File file) {
                 return file.getName().endsWith(".xml");
            	//return file.getName().endsWith(".pkqt");
            }
        });
        TreeSet<String> treeSet = new TreeSet<String>();
        for (File iterator2 : fileArray) {
            try {
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                
                DInputStream fileEncoded = new DInputStream(XInputStreamCreate.open(iterator2.getPath()));
                
                InputStreamReader streamReader = new InputStreamReader(fileEncoded, "UTF-8");
                BufferedReader reader = new BufferedReader(streamReader);
                for( String line; (line = reader.readLine()) != null;) {
                	System.out.println("Line: " + line);
                }
                
                Document document = documentBuilder.parse(fileEncoded);
                
                // RICK: changed from Object to NodeList
                object2 = document.getElementsByTagName(PARTICLE_NODE_NAME);
                for (int i = 0; i < object2.getLength(); ++i) {
                    node2 = object2.item(i);
                    node = node2.getAttributes().getNamedItem(PARTICLE_MATERIAL_TYPE_NODE_NAME);
                    if (!XmlFilter.textContentExists(node)) continue;
                    treeSet.add(node.getTextContent());
                }
            }
            catch (Exception exception) {
                System.err.println(exception.getLocalizedMessage());
            }
        }
        treeSet.add("type27");
        treeSet.add("type28");
        treeSet.add("type29");
        treeSet.add("type30");
        treeSet.add("type31");
        treeSet.add("type32");
        treeSet.add("type39");
        treeSet.add("type40");
        TreeSet treeSet2 = new TreeSet();
        TreeSet<String> treeSet3 = new TreeSet<String>();
        try {
            object = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document fileNotFoundException = ((DocumentBuilder)object).parse(new DInputStream(XInputStreamCreate.open(file2.getPath())));
            NodeList nodeList = fileNotFoundException.getElementsByTagName(NAMERESOLVE_TABLE_RECORD);
            for (int i = 0; i < nodeList.getLength(); ++i) {
            	
            	// RICK: changed from Object to Node
                Node object2Node = nodeList.item(i);
                XmlFilter xmlFilter = new XmlFilter(object2Node.getChildNodes());
                node2 = xmlFilter.nodeNameNext(TABLE_KEY);
                if (!XmlFilter.textContentExists(node2) || !node2.getTextContent().startsWith(NAMERESOLVE_TABLE_NODE_PREFIX) || !XmlFilter.textContentExists(node = xmlFilter.goOnStart().nodeNameNext(TABLE_VALUE))) continue;
                treeSet3.add(node.getTextContent());
                if (!treeSet.contains(node2.getTextContent().substring(NAMERESOLVE_TABLE_NODE_PREFIX.length()))) continue;
                treeSet2.add(node.getTextContent());
            }
        }
        catch (ParserConfigurationException parserConfigurationException) {
            System.err.println(parserConfigurationException.getLocalizedMessage());
        }
        catch (IOException iOException) {
            System.err.println(iOException.getLocalizedMessage());
        }
        catch (SAXException sAXException) {
            System.err.println(sAXException.getLocalizedMessage());
        }
        object = null;
        try {
            object = new PrintStream(new FileOutputStream("materials_from_configs.txt"));
            Iterator iterator = treeSet2.iterator();
            while (iterator.hasNext()) {
                String string2 = (String)iterator.next();
                ((PrintStream)object).println(string2);
            }
            ((PrintStream)object).close();
            object = new PrintStream(new FileOutputStream("materials_from_nameresolve_table.txt"));
            for (String string3 : treeSet3) {
                ((PrintStream)object).println(string3);
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            System.err.println(fileNotFoundException.getLocalizedMessage());
        }
        finally {
            if (null != object) {
                ((PrintStream)object).close();
            }
        }
    }
}
