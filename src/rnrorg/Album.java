/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import rnrcore.CoreTime;
import rnrcore.loc;
import rnrorg.AlbumFinishWarehouse;
import scenarioUtils.Pair;
import xmlserialization.CoreTimeSerialization;
import xmlserialization.Helper;
import xmlutils.Node;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Album {
    private static Album instance = null;
    private ArrayList<Item> items = new ArrayList();

    private Album() {
    }

    public static Album getInstance() {
        if (null == instance) {
            instance = new Album();
        }
        return instance;
    }

    public static String getNodeName() {
        return "albumElement";
    }

    public Item add(String description, String text, CoreTime date, String material) {
        Item ret = new Item(description, text, date, material, false);
        this.items.add(ret);
        return ret;
    }

    public void addBigRaceShot(String description, String text, CoreTime date, String material) {
        this.items.add(new Item(description, text, date, material, true));
    }

    public ArrayList<Item> getAll() {
        return this.items;
    }

    public void setAll(ArrayList<Item> i) {
        this.items = i;
    }

    public static class Item {
        public String description;
        public String locdesc;
        public String text;
        public String loctext;
        public CoreTime date;
        public String material;
        public boolean is_bigrace_item;

        Item(String description, String text, CoreTime date, String material, boolean is_bigrace_item) {
            this.date = date;
            this.material = material;
            this.is_bigrace_item = is_bigrace_item;
            this.description = description;
            this.text = text;
            if (is_bigrace_item) {
                this.locdesc = loc.getBigraceShortName(description);
                this.loctext = AlbumFinishWarehouse.locText(description, text);
            } else {
                this.locdesc = loc.getOrgString(description);
                this.loctext = loc.getMissionSuccesPictureText(text);
            }
        }

        public void serializeXML(PrintStream stream) {
            Helper.openNode(stream, Album.getNodeName());
            CoreTimeSerialization.serializeXML(this.date, stream);
            List<Pair<String, String>> attributes = Helper.createSingleAttribute("albumElementDescr", this.description);
            Helper.addAttribute("albumElementText", this.text, attributes);
            Helper.addAttribute("albumElementMaterial", this.material, attributes);
            Helper.addAttribute("albumElement", this.is_bigrace_item, attributes);
            Helper.printClosedNodeWithAttributes(stream, "albumElementValue", attributes);
            Helper.closeNode(stream, Album.getNodeName());
        }

        public static Item deserializeXML(Node node) {
            Node timeNode = node.getNamedChild(CoreTimeSerialization.getNodeName());
            CoreTime d = new CoreTime();
            if (null != timeNode) {
                d = CoreTimeSerialization.deserializeXML(timeNode);
            }
            String des = null;
            String t = null;
            String mat = null;
            boolean isbgr = false;
            Node body = node.getNamedChild("albumElementValue");
            if (null != body) {
                des = body.getAttribute("albumElementDescr");
                t = body.getAttribute("albumElementText");
                mat = body.getAttribute("albumElementMaterial");
                String str = body.getAttribute("albumElement");
                isbgr = Helper.ConvertToBooleanAndWarn(str, "albumElement", "Album on deserializeXML");
            }
            Item item = new Item(des, t, d, mat, isbgr);
            return item;
        }
    }
}

