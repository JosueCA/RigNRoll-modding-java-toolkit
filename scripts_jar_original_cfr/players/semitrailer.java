/*
 * Decompiled with CFR 0.151.
 */
package players;

import rnrcore.matrixJ;
import rnrcore.vectorJ;

public class semitrailer {
    final int p_triler;

    public final boolean equal(semitrailer trailer) {
        return this.p_triler == trailer.p_triler;
    }

    public semitrailer() {
        this.p_triler = 0;
    }

    public static native semitrailer create(String var0, matrixJ var1, vectorJ var2);

    public native void delete();

    public native matrixJ getMatrix();

    public native vectorJ getPosition();

    public native void registerCar(String var1);

    public native String querryModelName();

    public native void setMatrix(matrixJ var1);

    public native void setPosition(vectorJ var1);

    public native void setVelocityModule(double var1);

    public static native semitrailer querryMissionSemitrailer(String var0);
}

