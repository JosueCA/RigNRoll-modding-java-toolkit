/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

import menu.TimeData;

public class WeatherPoint {
    public static final int SUNNY = 0;
    public static final int MOSTLY_CLEAR = 1;
    public static final int CLOUDY = 2;
    public static final int RAINY = 3;
    public static final int SNOWY = 5;
    public static final int STORMY = 4;
    public static final int MAX = 6;
    public double temp;
    public double rain;
    public double snow;
    public double weather;
    public double humidity;
    public double pressure;
    public double windspeed;
    public TimeData risetime;
    public TimeData settime;
    public double distance;

    public int GetState() {
        if (this.rain > 0.1) {
            if (this.snow < 0.1) {
                return this.rain > 0.7 ? 4 : 3;
            }
            return 5;
        }
        if (this.weather > 0.9) {
            return 0;
        }
        return this.weather < 0.5 ? 2 : 1;
    }
}

