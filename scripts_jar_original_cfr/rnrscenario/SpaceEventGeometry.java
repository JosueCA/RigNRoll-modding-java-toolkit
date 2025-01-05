/*
 * Decompiled with CFR 0.151.
 */
package rnrscenario;

import rnrcore.vectorJ;

public class SpaceEventGeometry {
    private vectorJ m_centr;
    private double m_radius2;

    public SpaceEventGeometry(vectorJ position, double radius) {
        this.m_centr = position;
        this.m_radius2 = radius * radius;
    }

    final boolean inEvent(vectorJ position) {
        double difX = position.x - this.m_centr.x;
        double difY = position.y - this.m_centr.y;
        double distanceXY = difX * difX + difY * difY;
        return position.z > this.m_centr.z && distanceXY < this.m_radius2;
    }
}

