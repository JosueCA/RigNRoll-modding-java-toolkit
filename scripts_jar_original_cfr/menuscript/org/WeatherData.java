/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import gameobj.WeatherPoint;
import java.util.Vector;

public class WeatherData {
    Vector m_warehouses;
    Vector m_dates;
    public Vector moonphase;
    public Vector moondates;
    private Vector m_weatherpoints;

    public void Init() {
        this.m_warehouses = new Vector();
        this.m_weatherpoints = new Vector();
        this.m_dates = new Vector();
        this.moonphase = new Vector();
        this.moondates = new Vector();
    }

    WeatherPoint GetWPoint(int day, boolean isnight, int whindex) {
        Vector whpoints = (Vector)this.m_weatherpoints.get(day * 2 + (isnight ? 1 : 0));
        if (whindex >= whpoints.size()) {
            return null;
        }
        return (WeatherPoint)whpoints.get(whindex);
    }
}

