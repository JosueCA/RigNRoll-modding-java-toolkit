/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.vectorJ;

public final class Sphere {
    private static final double ERROR = 1.0E-4;
    private final vectorJ center = new vectorJ();
    private double sqrRadius;
    private double sqrCenterLength;

    vectorJ getCenter() {
        return this.center;
    }

    double getSqrRadius() {
        return this.sqrRadius;
    }

    double getSqrCenterLength() {
        return this.sqrCenterLength;
    }

    public Sphere(double centerX, double centerY, double centerZ, double radius) {
        this.sqrRadius = radius * radius;
        this.center.x = centerX;
        this.center.y = centerY;
        this.center.z = centerZ;
        this.sqrCenterLength = this.center.dot(this.center);
    }

    public void setRadius(double radius) {
        this.sqrRadius = radius * radius;
    }

    public void setCenter(double centerX, double centerY, double centerZ) {
        this.center.x = centerX;
        this.center.y = centerY;
        this.center.z = centerZ;
        this.sqrCenterLength = this.center.dot(this.center);
    }

    private static boolean isParameterOnSegment(double value) {
        return 0.0 <= value && 1.0 >= value;
    }

    public boolean intersecs(vectorJ start, vectorJ finish) {
        double finishDotFinish = finish.dot(finish);
        double finishDotCenter = finish.dot(this.center);
        double a = start.len2(finish);
        double b = 2.0 * (-finishDotFinish - start.dot(this.center) + finishDotCenter + start.dot(finish));
        double c = this.sqrCenterLength + finishDotFinish - 2.0 * finishDotCenter - this.sqrRadius;
        if (1.0E-4 > Math.abs(a)) {
            return this.sqrRadius >= start.len2(this.center);
        }
        double discriminant = b * b - 4.0 * a * c;
        if (0.0 > discriminant) {
            return false;
        }
        if (1.0E-4 > discriminant) {
            return Sphere.isParameterOnSegment(-b / (2.0 * a));
        }
        double denominator = 1.0 / (2.0 * a);
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double leftParameter = (-b + sqrtDiscriminant) * denominator;
        double rightParameter = (-b - sqrtDiscriminant) * denominator;
        return 0.0 <= leftParameter && 1.0 >= leftParameter || 0.0 <= rightParameter && 1.0 >= rightParameter || 0.0 >= leftParameter && 1.0 <= rightParameter || 0.0 >= rightParameter && 1.0 <= leftParameter;
    }
}

