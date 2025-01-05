/*
 * Decompiled with CFR 0.151.
 */
package rnrconfig;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum GaugeTypes {
    VOID_GAUGE(0),
    SPEDOMETR_GAUGE(1),
    TACHMETR_GAUGE(2),
    FUEL_GAUGE(3),
    OILTEMPER_GAUGE(4),
    WATERTEMP_GAUGE(5),
    OILPREASU_GAUGE(6),
    AIRPREASU_GAUGE(7),
    AIRAPPLIE_GAUGE(8),
    WIPERS_GAUGE(9),
    AIRPESASUSECONDARY_GAUGE(10),
    OILBOXTEMP_GAUGE(11),
    AXLEFRONTTEMP_GAUGE(12),
    AXLEREARTEMP_GAUGE(13),
    VOLT_GAUGE(14);

    private int _typeNumber;

    private GaugeTypes(int typeNumber) {
        this._typeNumber = typeNumber;
    }

    public int GetNumber() {
        return this._typeNumber;
    }

    public static GaugeTypes GetInstanceByNumber(int typeNumber) throws Exception {
        GaugeTypes[] a = GaugeTypes.values();
        for (int i = 0; i < a.length; ++i) {
            if (a[i].GetNumber() != typeNumber) continue;
            return a[i];
        }
        throw new Exception("GaugeTypes: there isn't constant with this typeNumber.");
    }
}

