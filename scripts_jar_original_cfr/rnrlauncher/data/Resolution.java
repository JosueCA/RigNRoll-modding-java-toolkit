/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher.data;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class Resolution {
    private final Icon icon;
    private final ImageIcon baseImage;
    private final String description;

    public Resolution(Icon icon, ImageIcon baseGradient, String description) {
        assert (null != icon && null != description);
        this.icon = icon;
        this.description = description;
        this.baseImage = baseGradient;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public String getDescription() {
        return this.description;
    }

    public ImageIcon getBaseImage() {
        return this.baseImage;
    }
}

