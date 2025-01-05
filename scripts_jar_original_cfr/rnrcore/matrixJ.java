/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import java.io.Serializable;
import rnrcore.vectorJ;

public class matrixJ
implements Serializable {
    static final long serialVersionUID = 0L;
    public vectorJ v0;
    public vectorJ v1;
    public vectorJ v2;

    public matrixJ() {
        this.v0 = new vectorJ(1.0, 0.0, 0.0);
        this.v1 = new vectorJ(0.0, 1.0, 0.0);
        this.v2 = new vectorJ(0.0, 0.0, 1.0);
    }

    public matrixJ(matrixJ m) {
        this.v0 = new vectorJ(m.v0);
        this.v1 = new vectorJ(m.v1);
        this.v2 = new vectorJ(m.v2);
    }

    public matrixJ(vectorJ dir, vectorJ nz) {
        this.v2 = new vectorJ(nz);
        this.v1 = new vectorJ(dir);
        this.v0 = dir.oCross(nz);
    }

    public void Set0(double _x, double _y, double _z) {
        this.v0.x = _x;
        this.v0.y = _y;
        this.v0.z = _z;
    }

    public void Set1(double _x, double _y, double _z) {
        this.v1.x = _x;
        this.v1.y = _y;
        this.v1.z = _z;
    }

    public void Set2(double _x, double _y, double _z) {
        this.v2.x = _x;
        this.v2.y = _y;
        this.v2.z = _z;
    }

    public static matrixJ Mz(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        res.Set0(c, s, 0.0);
        res.Set1(-s, c, 0.0);
        return res;
    }

    public static matrixJ Mx(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        res.Set1(0.0, c, s);
        res.Set2(0.0, -s, c);
        return res;
    }

    public static matrixJ My(double angle) {
        matrixJ res = new matrixJ();
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        res.Set0(c, 0.0, -s);
        res.Set2(s, 0.0, c);
        return res;
    }

    public void mult(matrixJ M) {
        vectorJ Mc0 = new vectorJ(M.v0.x, M.v1.x, M.v2.x);
        vectorJ Mc1 = new vectorJ(M.v0.y, M.v1.y, M.v2.y);
        vectorJ Mc2 = new vectorJ(M.v0.z, M.v1.z, M.v2.z);
        this.Set0(this.v0.dot(Mc0), this.v0.dot(Mc1), this.v0.dot(Mc2));
        this.Set1(this.v1.dot(Mc0), this.v1.dot(Mc1), this.v1.dot(Mc2));
        this.Set2(this.v2.dot(Mc0), this.v2.dot(Mc1), this.v2.dot(Mc2));
    }

    public static matrixJ mult(matrixJ Mleft, matrixJ M) {
        matrixJ res = new matrixJ(Mleft);
        res.mult(M);
        return res;
    }

    public static vectorJ mult(matrixJ M, vectorJ v) {
        vectorJ Mc0 = new vectorJ(M.v0.x, M.v1.x, M.v2.x);
        vectorJ Mc1 = new vectorJ(M.v0.y, M.v1.y, M.v2.y);
        vectorJ Mc2 = new vectorJ(M.v0.z, M.v1.z, M.v2.z);
        return new vectorJ(Mc0.dot(v), Mc1.dot(v), Mc2.dot(v));
    }
}

