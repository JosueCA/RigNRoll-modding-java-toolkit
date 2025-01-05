/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import rnrcore.XmlSerializable;
import rnrcore.eng;
import rnrloggers.ScriptsLogger;
import rnrscenario.consistency.ScenarioGarbageFinder;
import rnrscenario.consistency.ScenarioStage;
import rnrscenario.consistency.StageChangedListener;
import scenarioXml.XmlDocument;
import scenarioXml.XmlFilter;

public class GameXmlSerializator
implements StageChangedListener {
    public static final int NO_VERSIONING = -1;
    private static final int BYTE_STREAM_INITIAL_CAPACITY = 1024;
    private static final String GAME_SAVE_ROOT_NODE_NAME = "RnRSave";
    private static final String GAME_SAVE_ROOT_VERSION_ATTR = "version";
    private List<XmlSerializable> serializableXmlObjects = null;
    private String path = null;
    private String file = null;
    private byte[] stream = null;
    private boolean loadFromArray = false;
    private int save_version = -1;
    private int load_version = -1;

    public GameXmlSerializator(String pathToFolder, String fileName) {
        if (null == pathToFolder) {
            throw new IllegalArgumentException("pathToFolder must be non-null reference");
        }
        if (null == fileName) {
            throw new IllegalArgumentException("fileName must be non-null reference");
        }
        this.path = pathToFolder;
        if ('\\' != this.path.charAt(this.path.length() - 1)) {
            this.path = this.path + '\\';
        }
        this.file = fileName;
        this.serializableXmlObjects = new LinkedList<XmlSerializable>();
    }

    public GameXmlSerializator(byte[] toLoadFrom) {
        if (null == toLoadFrom) {
            throw new IllegalArgumentException("toLoadFrom must be non-null reference");
        }
        this.stream = toLoadFrom;
        this.loadFromArray = true;
    }

    public GameXmlSerializator() {
        this.loadFromArray = false;
    }

    public void addSerializationTarget(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException("GameXmlSerializator.addSerializationTarget: target must be non-null");
        }
        this.serializableXmlObjects.add(target);
    }

    public void removeSerializationTarget(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException("GameXmlSerializator.removeSerializationTarget: target must be non-null");
        }
        this.serializableXmlObjects.remove(target);
    }

    public void addSerializationTargetExclusively(XmlSerializable target) {
        if (null == target) {
            throw new IllegalArgumentException("GameXmlSerializator.addSerializationTargetExclusively: target must be non-null");
        }
        this.serializableXmlObjects.add(0, target);
    }

    public void save(PrintStream destination) {
        if (null == destination) {
            throw new IllegalArgumentException("destination must be non-null reference");
        }
        destination.println("<?xml version=\"1.0\" encoding=\"Cp1252\" ?>");
        destination.println("<RnRSave version=\"" + this.getSave_version() + "\"" + '>');
        for (XmlSerializable serializationTarget : this.serializableXmlObjects) {
            serializationTarget.saveToStreamAsSetOfXmlNodes(destination);
        }
        destination.println("</RnRSave>");
        destination.flush();
    }

    public void load(XmlDocument source) {
        if (null == source) {
            throw new IllegalArgumentException("source must be non-null reference");
        }
        Document document = source.getContent();
        NodeList list = document.getElementsByTagName(GAME_SAVE_ROOT_NODE_NAME);
        if (1 != list.getLength()) {
            String error = "failed to load game: RnRSave node wasn't found";
            ScriptsLogger.getInstance().log(Level.SEVERE, 4, error);
            eng.err(error);
            return;
        }
        Node root = list.item(0);
        xmlutils.Node rootNodeUtil = new xmlutils.Node(root);
        String versionLoadString = rootNodeUtil.getAttribute(GAME_SAVE_ROOT_VERSION_ATTR);
        if (versionLoadString != null) {
            int version = Integer.decode(versionLoadString);
            this.setLoad_version(version);
        } else {
            this.setLoad_version(-1);
        }
        XmlFilter filter = new XmlFilter(root.getChildNodes());
        for (XmlSerializable serializationTarget : this.serializableXmlObjects) {
            Node objectSourceNode = filter.nodeNameNext(serializationTarget.getRootNodeName());
            if (null != objectSourceNode) {
                serializationTarget.loadFromNode(objectSourceNode);
            } else {
                serializationTarget.yourNodeWasNotFound();
            }
            filter.goOnStart();
        }
    }

    public byte[] saveToByteArray() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
        PrintStream out = new PrintStream(byteStream);
        this.save(out);
        out.close();
        try {
            byteStream.close();
        }
        catch (IOException e) {
            e.printStackTrace(System.err);
            String error = "failed to save game to byte array: " + e.getMessage();
            eng.err(error);
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, error);
        }
        byte[] saveByteArray = byteStream.toByteArray();
        try {
            new XmlDocument(saveByteArray);
        }
        catch (IOException e) {
            String errorMessage = "failed to save game to byte array: " + e.getMessage();
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
            eng.fatal(errorMessage);
        }
        return saveByteArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void saveToFile() {
        File savingDirectory = new File(this.path);
        if (!savingDirectory.exists() && !savingDirectory.mkdir()) {
            System.err.println("failed to creade dir: " + savingDirectory.getPath());
        }
        PrintStream out = null;
        try {
            out = new PrintStream(this.path + this.file);
            this.save(out);
        }
        catch (FileNotFoundException e) {
            String errorMessgae = "failed to save game: " + e.getMessage();
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
            eng.err(errorMessgae);
        }
        finally {
            if (null != out) {
                out.close();
            }
        }
    }

    public void loadFromDefaultSource() {
        if (!this.loadFromArray && null == this.file || this.loadFromArray && null == this.stream) {
            throw new IllegalStateException("source was not specified");
        }
        try {
            XmlDocument xml = this.loadFromArray ? new XmlDocument(this.stream) : new XmlDocument(this.path + this.file);
            this.load(xml);
        }
        catch (IOException e) {
            String errorMessage = "failed to load game from file: " + e.getMessage();
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
            eng.fatal(errorMessage);
        }
    }

    public void loadFromByteArray(byte[] array) {
        if (null == array) {
            throw new IllegalArgumentException("stream must be non-null reference");
        }
        try {
            XmlDocument xml = new XmlDocument(array);
            this.load(xml);
        }
        catch (IOException e) {
            String errorMessage = "failed to load game from byte array: " + e.getMessage();
            ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessage);
            eng.err(errorMessage);
        }
    }

    public int getLoad_version() {
        return this.load_version;
    }

    public void setLoad_version(int load_version) {
        this.load_version = load_version;
    }

    public int getSave_version() {
        return this.save_version;
    }

    public void setSave_version(int save_version) {
        this.save_version = save_version;
    }

    public void scenarioCheckPointReached(ScenarioStage scenarioStage) {
        ScenarioGarbageFinder.deleteOutOfDateScenarioObjects("GameXmlSerializetor", this.serializableXmlObjects, scenarioStage);
    }
}

