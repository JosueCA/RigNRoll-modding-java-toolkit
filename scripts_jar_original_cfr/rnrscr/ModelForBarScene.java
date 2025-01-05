/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

public class ModelForBarScene {
    private String modelName;
    private boolean isMan;

    ModelForBarScene(String modelname, boolean isMan) {
        this.modelName = modelname;
        this.isMan = isMan;
    }

    public boolean isMan() {
        return this.isMan;
    }

    public String getModelName() {
        return this.modelName;
    }
}

