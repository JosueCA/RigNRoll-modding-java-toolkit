/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.List;
import rnrcore.MacroBody;
import rnrcore.Macros;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MacroBodyString
extends MacroBody {
    private String body;

    @Override
    public String getBody() {
        return this.body;
    }

    MacroBodyString(String body, List<Macros> macroces) {
        super(macroces);
        this.body = body;
    }
}

