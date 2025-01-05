/*
 * Decompiled with CFR 0.151.
 */
package players;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public enum CarName {
    CAR_COCH("PETERBILT_378", 13),
    CAR_BANDITS("FREIGHTLINER_ARGOSY", 13),
    CAR_DAKOTA("PETERBILT_379", 13),
    CAR_PITER_PAN("PETERBILT_387", 13),
    CAR_MATHEW("FREIGHTLINER_CORONADO", 13),
    CAR_MATHEW_DEAD("KENWORTH_T600W", 14),
    CAR_MONICA("Dodge_Intrepid", 16),
    CAR_DOROTHY("KENWORTH_T600W", 13),
    CAR_POLICE("Ford_CV_police", 0),
    CAR_GEPARD("GEPARD", 0),
    CAR_JOHN("GMC_TOPKICK4500_JOHN_TRAMPLIN", 0);

    private final String name;
    private final int color;

    private CarName(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public int getColor() {
        return this.color;
    }
}

