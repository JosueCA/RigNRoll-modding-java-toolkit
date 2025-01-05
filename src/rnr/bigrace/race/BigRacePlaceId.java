/*
 * Decompiled with CFR 0.151.
 */
package rnr.bigrace.race;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum BigRacePlaceId {
    FIRST_PLACE(1),
    SECOND_PLACE(2),
    THIRD_PLACE(3),
    PARTAKING_PLACE(4),
    FAIL_PLACE(5);

    private int m_placeId;

    private BigRacePlaceId(int placeId) {
        this.m_placeId = placeId;
    }

    public int getId() {
        return this.m_placeId;
    }
}

