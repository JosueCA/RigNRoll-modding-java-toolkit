/*
 * Decompiled with CFR 0.151.
 */
package menu;

public class TimeData {
    public int hours;
    public int minutes;
    public int seconds;

    public TimeData(int _hours, int _minutes) {
        this.hours = _hours;
        this.minutes = _minutes;
        this.seconds = 0;
    }

    public TimeData(int _hours, int _minutes, int _seconds) {
        this.hours = _hours;
        this.minutes = _minutes;
        this.seconds = _seconds;
    }

    public int AddSeconds(int secshift) {
        int outval = 0;
        this.seconds += secshift;
        if (this.seconds >= 60) {
            this.minutes += this.seconds / 60;
            this.seconds %= 60;
        }
        if (this.seconds < 0) {
            this.minutes -= this.seconds / 60 + 1;
            this.seconds = this.seconds % 60 + 60;
        }
        if (this.minutes >= 60) {
            this.hours += this.minutes / 60;
            this.minutes %= 60;
        }
        if (this.minutes < 0) {
            this.hours -= this.minutes / 60 + 1;
            this.minutes = this.minutes % 60 + 60;
        }
        if (this.hours >= 24) {
            outval += this.hours / 24;
            this.hours %= 24;
        }
        if (this.hours < 0) {
            outval -= this.hours / 24 + 1;
            this.hours = this.hours % 24 + 24;
        }
        return outval;
    }

    public TimeData Clone() {
        return new TimeData(this.hours, this.minutes, this.seconds);
    }

    public TimeData() {
    }
}

