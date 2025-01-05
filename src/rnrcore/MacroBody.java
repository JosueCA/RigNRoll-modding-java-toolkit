/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.ArrayList;
import java.util.List;
import menu.KeyPair;
import menu.MacroKit;
import rnrcore.Macros;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public abstract class MacroBody {
    private List<Macros> macroces = new ArrayList<Macros>();

    protected KeyPair[] getMacroces() {
        if (this.macroces == null) {
            return new KeyPair[0];
        }
        KeyPair[] pairs = new KeyPair[this.macroces.size()];
        int i = 0;
        for (Macros macro : this.macroces) {
            pairs[i++] = macro.getKeyPair();
        }
        return pairs;
    }

    protected abstract String getBody();

    public String makeString() {
        return MacroKit.Parse(this.getBody(), this.getMacroces());
    }

    protected MacroBody(List<Macros> macroces) {
        if (null != macroces) {
            this.macroces.addAll(macroces);
        }
    }

    public List<Macros> getMacrosList() {
        return this.macroces;
    }
}

