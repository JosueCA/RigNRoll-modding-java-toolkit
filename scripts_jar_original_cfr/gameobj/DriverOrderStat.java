/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

public class DriverOrderStat {
    public static final int UNABLE_ONTIME = -1;
    public static final int UNSUITABLE_VEHICLE = -2;
    public static final int UNQUALIFIED_DRIVER = -3;
    public static final int FREE = 0;
    public static final int OCCUPIED = 1;
    public static final int ASSIGNED = 2;
    public static final int MAX_STATE = 5;
    public int state;
    public int assignstate;
    public int price;
}

