/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum LightTypes {
    FRONT_LIGHT(1),
    REAR_LIGHT(2),
    PARK_LIGHT(3),
    LEFT_LIGHT(4),
    RIGHT_LIGHT(5),
    ALLERT_LIGHT(6),
    STOP_LIGHT(8),
    HAND_BREAK_LIGHT(9),
    ANTIFREESE_LOW(12),
    OIL_PREASURE_LOW(13),
    OIL_HYDRO_AMPL_LOW(14),
    ABS_DAMAGED(15),
    SUSPENSION_DAMAGED(16),
    CHARGING_DAMAGED(17),
    FRONT_FAR_LIGHT(18);

    private int _typeNumber;

    private LightTypes(int typeNumber) {
        this._typeNumber = typeNumber;
    }

    public int GetNumber() {
        return this._typeNumber;
    }

    public static LightTypes GetInstanceByNumber(int typeNumber) throws Exception {
        LightTypes[] a = LightTypes.values();
        for (int i = 0; i < a.length; ++i) {
            if (a[i].GetNumber() != typeNumber) continue;
            return a[i];
        }
        throw new Exception("LightTypes: there isn't constant with this typeNumber = " + typeNumber + ".");
    }
}

