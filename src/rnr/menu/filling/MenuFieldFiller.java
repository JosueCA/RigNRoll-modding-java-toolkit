/*
 * Decompiled with CFR 0.151.
 */
package rnr.menu.filling;

public abstract class MenuFieldFiller {
    private final String fieldName;

    protected MenuFieldFiller(String fieldName) {
        this.fieldName = fieldName;
    }

    public final String getFieldName() {
        return this.fieldName;
    }

    public void freeResources() {
    }

    public abstract void fillFieldOfMenu(long var1);
}

