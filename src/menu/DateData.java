/*
 * Decompiled with CFR 0.151.
 */
package menu;

public class DateData {
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int APR = 4;
    public static final int MAY = 5;
    public static final int JUN = 6;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int SEP = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;
    public int month;
    public int day;
    public int year;

    public DateData() {
    }

    public DateData(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }
}

