/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.Vector;
import rnrcore.eng;

public class RandomAccesString {
    private Vector bunchStrings = new Vector();

    private void resort() {
        int j;
        int sz = this.bunchStrings.size();
        if (sz == 0 || sz == 1) {
            return;
        }
        int[] i = new int[sz];
        for (j = 0; j < sz; ++j) {
            i[j] = j;
        }
        for (j = 0; j < sz; ++j) {
            for (int k = 0; k < sz; ++k) {
                if (j == k || !(Math.random() >= 0.9)) continue;
                int n = j;
                i[n] = i[n] ^ i[k];
                int n2 = k;
                i[n2] = i[n2] ^ i[j];
                int n3 = j;
                i[n3] = i[n3] ^ i[k];
            }
        }
        for (j = 0; j < sz; ++j) {
            Object ob1;
            if (i[j] == j) continue;
            Object ob = this.bunchStrings.elementAt(i[j]);
            if (ob.equals(ob1 = this.bunchStrings.elementAt(j))) {
                eng.err("resort error");
            }
            this.bunchStrings.setElementAt(ob1, i[j]);
            this.bunchStrings.setElementAt(ob, j);
        }
    }

    private String sortfirst() {
        if (this.bunchStrings.size() == 0) {
            return "ERROR. RandomAccesString is empty.";
        }
        String res = (String)this.bunchStrings.firstElement();
        this.bunchStrings.removeElementAt(0);
        this.add(res);
        return res;
    }

    void add(String str) {
        this.bunchStrings.add(str);
        this.resort();
    }

    String get() {
        return this.sortfirst();
    }
}

