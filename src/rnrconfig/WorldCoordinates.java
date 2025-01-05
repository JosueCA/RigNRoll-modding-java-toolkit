/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

import menu.JavaEvents;

public class WorldCoordinates {
    double left;
    double top;
    double right;
    double bottom;

    public static WorldCoordinates getCoordinates() {
        WorldCoordinates res = new WorldCoordinates();
        JavaEvents.SendEvent(36, 0, res);
        return res;
    }

    public double convertX(double x) {
        return 1.0 - 2.0 * (this.right - x) / (this.right - this.left);
    }

    public double convertY(double y) {
        return -1.0 + 2.0 * (this.top - y) / (this.top - this.bottom);
    }
}

