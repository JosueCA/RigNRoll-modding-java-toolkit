/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.ListIterator;
import players.aiplayer;
import rnrcore.eng;
import rnrscr.CBVideocallelemnt;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class cbapparatus {
    private final ArrayList<CBVideocallelemnt> allcalls = new ArrayList();
    private static cbapparatus instance = null;

    private cbapparatus() {
    }

    public static cbapparatus getInstance() {
        if (instance == null) {
            instance = new cbapparatus();
        }
        return instance;
    }

    private void add(CBVideocallelemnt el) {
        this.allcalls.add(el);
    }

    public void clearFinishedCalls() {
        ListIterator<CBVideocallelemnt> i = this.allcalls.listIterator();
        while (i.hasNext()) {
            CBVideocallelemnt ell = i.next();
            if (!ell.gFinished()) continue;
            i.remove();
        }
    }

    public void clearAllCalls() {
        this.allcalls.clear();
    }

    public ArrayList<CBVideocallelemnt> gCallers() {
        ArrayList<CBVideocallelemnt> res = new ArrayList<CBVideocallelemnt>(this.allcalls.size());
        for (CBVideocallelemnt ell : this.allcalls) {
            if (ell.gFinished()) continue;
            res.add(ell);
        }
        return res;
    }

    public CBVideocallelemnt makecall(aiplayer pl, String dialogname) {
        CBVideocallelemnt el = new CBVideocallelemnt(pl, dialogname);
        el.setDialogName(dialogname);
        if (null == el.whocalls) {
            eng.err("ERROR. cbvideocall has null player. Dialog name " + dialogname);
        }
        el.initiate();
        if (0L == el.nativePointer) {
            eng.err("ERROR. cbvideocall has no reflection in native. Dialog name " + dialogname);
        }
        this.add(el);
        return el;
    }
}

