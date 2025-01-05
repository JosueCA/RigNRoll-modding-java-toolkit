/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

public class vectorJ {
    public double x;
    public double y;
    public double z;

    public void Set(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public void Set(vectorJ value) {
        this.x = value.x;
        this.y = value.y;
        this.z = value.z;
    }

    public vectorJ() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public vectorJ(double _x, double _y, double _z) {
        this.x = _x;
        this.y = _y;
        this.z = _z;
    }

    public vectorJ(vectorJ _copy) {
        this.x = _copy.x;
        this.y = _copy.y;
        this.z = _copy.z;
    }

    public double len2(vectorJ pos2) {
        double px = this.x - pos2.x;
        double py = this.y - pos2.y;
        double pz = this.z - pos2.z;
        return px * px + py * py + pz * pz;
    }

    public static vectorJ oMinus(vectorJ vec1, vectorJ vec2) {
        return new vectorJ(vec1.x - vec2.x, vec1.y - vec2.y, vec1.z - vec2.z);
    }

    public vectorJ oMinusN(vectorJ vec2) {
        return new vectorJ(this.x - vec2.x, this.y - vec2.y, this.z - vec2.z);
    }

    public void oMinus(vectorJ vec2) {
        this.x -= vec2.x;
        this.y -= vec2.y;
        this.z -= vec2.z;
    }

    public static vectorJ oPlus(vectorJ vec1, vectorJ vec2) {
        return new vectorJ(vec2.x + vec1.x, vec2.y + vec1.y, vec2.z + vec1.z);
    }

    public vectorJ oPlusN(vectorJ vec2) {
        return new vectorJ(vec2.x + this.x, vec2.y + this.y, vec2.z + this.z);
    }

    public void oPlus(vectorJ vec2) {
        this.x = vec2.x + this.x;
        this.y = vec2.y + this.y;
        this.z = vec2.z + this.z;
    }

    public static vectorJ oCross(vectorJ vec1, vectorJ vec2) {
        vectorJ res = new vectorJ();
        res.x = vec1.y * vec2.z - vec1.z * vec2.y;
        res.y = vec1.z * vec2.x - vec1.x * vec2.z;
        res.z = vec1.x * vec2.y - vec1.y * vec2.x;
        return res;
    }

    public static double dot(vectorJ vec1, vectorJ vec2) {
        return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
    }

    public vectorJ oCross(vectorJ vec2) {
        vectorJ res = new vectorJ();
        res.x = this.y * vec2.z - this.z * vec2.y;
        res.y = this.z * vec2.x - this.x * vec2.z;
        res.z = this.x * vec2.y - this.y * vec2.x;
        return res;
    }

    public double dot(vectorJ vec2) {
        return this.x * vec2.x + this.y * vec2.y + this.z * vec2.z;
    }

    public void norm() {
        double len_1 = this.length();
        if (len_1 != 0.0) {
            double len__1 = 1.0 / len_1;
            this.x *= len__1;
            this.y *= len__1;
            this.z *= len__1;
        } else {
            this.y = 1.0;
        }
    }

    public vectorJ normN() {
        vectorJ res = new vectorJ(this.x, this.y, this.z);
        double len_2 = this.dot(res);
        if (len_2 != 0.0) {
            double len__1 = 1.0 / Math.sqrt(len_2);
            res.x *= len__1;
            res.y *= len__1;
            res.z *= len__1;
        } else {
            res.y = 1.0;
        }
        return res;
    }

    public static vectorJ norm(vectorJ vec2) {
        return vec2.normN();
    }

    public static void normN(vectorJ vec2) {
        vec2.norm();
    }

    public double length() {
        return Math.sqrt(this.dot(this));
    }

    public void mult(double val) {
        this.x *= val;
        this.y *= val;
        this.z *= val;
    }

    public vectorJ getMultiplied(double scale) {
        return new vectorJ(this.x * scale, this.y * scale, this.z * scale);
    }
}

