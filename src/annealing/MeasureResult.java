/*
 * Decompiled with CFR 0.151.
 */
package annealing;

public class MeasureResult {
    public int weight;

    public MeasureResult(int weught) {
        this.weight = weught;
    }

    public static final boolean less(MeasureResult measure1, MeasureResult measure2) {
        return MeasureResult.difference(measure1, measure2) < 0;
    }

    public static final int difference(MeasureResult measure1, MeasureResult measure2) {
        return -measure1.weight - -measure2.weight;
    }
}

