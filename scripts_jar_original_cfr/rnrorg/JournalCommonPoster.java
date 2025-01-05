/*
 * Decompiled with CFR 0.151.
 */
package rnrorg;

import java.util.ArrayList;
import java.util.List;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrorg.journalelement;

public class JournalCommonPoster {
    private String description;
    private List<Macros> macroPairs = new ArrayList<Macros>();

    public void setDescription(String description) {
        this.description = description;
    }

    public void addMacroPair(String key, String value) {
        this.macroPairs.add(new Macros(key, MacroBuilder.makeSimpleMacroBody(value)));
    }

    public void addMacroPair(String key, String namespace, String locref) {
        this.macroPairs.add(new Macros(key, MacroBuilder.makeSimpleMacroBody(namespace, locref)));
    }

    public void start() {
        new journalelement(this.description, this.macroPairs).start();
    }
}

