/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher.data;

public final class ColumnHeader {
    private final String title;
    private final double weight;

    public ColumnHeader(String title, double weight) {
        this.title = title;
        this.weight = weight;
    }

    public String getTitle() {
        return this.title;
    }

    public double getWeight() {
        return this.weight;
    }
}

