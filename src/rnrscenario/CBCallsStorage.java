/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import java.util.ArrayList;
import java.util.Vector;
import players.Crew;
import rnrcore.eng;
import rnrscenario.CBVideoStroredCall;
import rnrscr.CBVideocallelemnt;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public final class CBCallsStorage {
    private static final String TOP = "cbcall";
    private static final String ELEMENT = "element";
    private static final String NAME = "name";
    private static final String DIALOG = "dialog";
    private static final String WHO = "who";
    private static final String TIMECALL = "timecall";
    private static final String TALKANYWAY = "talkanyway";
    private final ArrayList<CBVideoStroredCall> storedCBVideoCalls = new ArrayList();
    private boolean inited = false;
    private static CBCallsStorage instance = null;

    public static CBCallsStorage getInstance() {
        if (null == instance) {
            instance = new CBCallsStorage();
        }
        return instance;
    }

    public static void deinit() {
        if (instance != null) {
            CBCallsStorage.instance.storedCBVideoCalls.clear();
        }
        instance = null;
    }

    private CBCallsStorage() {
    }

    public CBVideoStroredCall getStoredCall(String name) {
        for (CBVideoStroredCall storedCBVideoCall : this.storedCBVideoCalls) {
            if (storedCBVideoCall.name.compareToIgnoreCase(name) != 0) continue;
            return storedCBVideoCall;
        }
        return null;
    }

    private void addStoredCBVideoCall(CBVideoStroredCall call) {
        this.storedCBVideoCalls.add(call);
    }

    public void init() {
        if (this.inited) {
            return;
        }
        this.inited = true;
        Vector filenames = new Vector();
        eng.getFilesAllyed(TOP, filenames);
        
        // RICK: manual castring of unknown object
        for (Object unknownObjectType : filenames) {
        	String name = (String) unknownObjectType;
            this.initwithfile(name);
        }
    }

    private void initwithfile(String filename) {
        Node node = XmlUtils.parse(filename);
        if (null == node) {
            return;
        }
        NodeList children = node.getChildren().findNamedNodes(ELEMENT);
        for (Node nextchild : children) {
            String name = this.getAttr(nextchild, NAME, "noname");
            String dialog = this.getAttr(nextchild, DIALOG, "noname");
            String who = this.getAttr(nextchild, WHO, null);
            String timecall = this.getAttr(nextchild, TIMECALL, "10.");
            String talkanyway = this.getAttr(nextchild, TALKANYWAY, "true");
            double time = 10.0;
            try {
                time = Double.parseDouble(timecall);
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
            }
            boolean f_talkanyway = true;
            try {
                f_talkanyway = Boolean.parseBoolean(talkanyway);
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
            }
            CBVideoStroredCall element = new CBVideoStroredCall(name, dialog, (float)time, f_talkanyway);
            if (who != null) {
                element.setIdentitie(who);
            }
            this.addStoredCBVideoCall(element);
        }
    }

    private String getAttr(Node node, String attr, String defaultvalue) {
        if (node.hasAttribute(attr)) {
            return node.getAttribute(attr);
        }
        return defaultvalue;
    }

    public static Vector printDialogsInfo() {
        Vector<Data> res = new Vector<Data>();
        for (CBVideoStroredCall element : CBCallsStorage.getInstance().storedCBVideoCalls) {
            CBVideocallelemnt call = element.makeImmediateCall();
            long pointer = call.nativePointer;
            res.add(new Data(pointer, call.getDialogName(), call.whocalls.getIdentitie(), Crew.getIgrok().getIdentitie()));
        }
        return res;
    }

    static class Data {
        String name;
        String caller;
        String callee;
        long pointer;

        Data() {
        }

        Data(long pointer, String name, String caler, String calee) {
            this.pointer = pointer;
            this.name = name;
            this.caller = caler;
            this.callee = calee;
        }
    }
}

