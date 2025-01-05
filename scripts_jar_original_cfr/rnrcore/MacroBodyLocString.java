/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.List;
import rnrcore.MacroBody;
import rnrcore.Macros;
import rnrcore.eng;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MacroBodyLocString
extends MacroBody {
    private String namespace;
    private String locref;

    @Override
    protected String getBody() {
        return eng.noNative ? this.namespace + ':' + this.locref : eng.getStringRef(this.namespace, this.locref);
    }

    MacroBodyLocString(String namespace, String locref, List<Macros> macroces) {
        super(macroces);
        this.namespace = namespace;
        this.locref = locref;
    }

    public String getLocref() {
        return this.locref;
    }

    public String getNamespace() {
        return this.namespace;
    }
}

