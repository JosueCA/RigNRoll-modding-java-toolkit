/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

import gameobj.WarehouseInfo;
import java.util.Vector;
import menu.TimeData;

public class OrderInfo {
    public WarehouseInfo from;
    public WarehouseInfo to;
    public int maxfee;
    public int reliabilitypercent;
    public int pertruckfee;
    public int mintrucks;
    public int maxtrucks;
    public int assigned;
    public String load;
    public TimeData time;
    public int fragilitypercent;
    public Vector driverstats;

    public int GetPercent() {
        return this.assigned * 100 / this.maxtrucks;
    }
}

