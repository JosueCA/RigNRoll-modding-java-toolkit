/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

import gameobj.CarInfo;

public class DriverInfo {
    public long handle;
    public String firstname;
    public String lastname;
    public double d_rating;
    public int faceindex;
    public double wagerate;
    public int gender;
    public int age;
    public int tickets;
    public int accidents;
    public boolean hasfelony;
    public double experience;
    public CarInfo car;

    public String GetFullName() {
        return this.firstname + " " + this.lastname;
    }
}

