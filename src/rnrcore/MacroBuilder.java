/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.util.List;
import rnrcore.MacroBody;
import rnrcore.MacroBodyLocString;
import rnrcore.MacroBodyString;
import rnrcore.Macros;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MacroBuilder {
    public static MacroBody makeSimpleMacroBody(String namespace, String locref) {
        return new MacroBodyLocString(namespace, locref, null);
    }

    public static MacroBody makeSimpleMacroBody(String str) {
        return new MacroBodyString(str, null);
    }

    public static MacroBody makeMacroBody(String namespace, String locref, List<Macros> macroces) {
        return new MacroBodyLocString(namespace, locref, macroces);
    }

    public static MacroBody makeMacroBody(String str, List<Macros> macroces) {
        return new MacroBodyString(str, macroces);
    }
}

