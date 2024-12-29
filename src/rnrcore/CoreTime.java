// Decompiled with: CFR 0.152
// Class Version: 5
package rnrcore;

import rnrcore.eng;
import rnrcore.gameDate;

public class CoreTime
implements gameDate {
    public static final int LEAPYEAR_DAYS = 366;
    public static final int NON_LEAPYEAR_DAYS = 365;
    int year;
    int month;
    int date;
    int hour;
    int minuten;
    private static final int INDEX_YEAR = 0;
    private static final int INDEX_MONTH = 1;
    private static final int INDEX_DATE = 2;
    private static final int INDEX_HOUR = 3;
    private static final int INDEX_MINUTE = 4;
    private static final int INDEX_MAX = 4;

    public int gDate() {
        return this.date;
    }

    public int gHour() {
        return this.hour;
    }

    public int gMinute() {
        return this.minuten;
    }

    public int gMonth() {
        return this.month;
    }

    public int gYear() {
        return this.year;
    }

    public void sDate(int value) {
        this.date = value;
    }

    public void sHour(int value) {
        this.hour = value;
    }

    public void sMinute(int value) {
        this.minuten = value;
    }

    public void sMonth(int value) {
        this.month = value;
    }

    public void sYear(int value) {
        this.year = value;
    }
    
    public String toString() {
        return String.format("%d %d %d %d %d", this.gYear(), this.gMonth(), this.gDate(), this.gHour(), this.gMinute());
    }
    
    public CoreTime(String data) {
        if (null != data) {
            String[] coreTimePacked = data.split(" ");
            if (4 < coreTimePacked.length) {
                try {
                    this.year = Integer.parseInt(coreTimePacked[0]);
                    this.month = Integer.parseInt(coreTimePacked[1]);
                    this.date = Integer.parseInt(coreTimePacked[2]);
                    this.hour = Integer.parseInt(coreTimePacked[3]);
                    this.minuten = Integer.parseInt(coreTimePacked[4]);
                }
                catch (NumberFormatException e) {
                    this.year = 0;
                    this.month = 0;
                    this.date = 0;
                    this.hour = 0;
                    this.minuten = 0;
                    this.update();
                }
            } else {
                this.update();
            }
        } else {
            this.update();
        }
    }

    public CoreTime() {
        this.update();
    }

    public CoreTime(CoreTime copy) {
        this.year = copy.year;
        this.month = copy.month;
        this.date = copy.date;
        this.hour = copy.hour;
        this.minuten = copy.minuten;
    }

    public void minusDays(int minus_days) {
        if (minus_days <= 0) {
            return;
        }
        if (this.date > minus_days) {
            this.date -= minus_days;
            return;
        }
        minus_days -= this.date;
        if (this.month == 1) {
            --this.year;
            this.month = 12;
        } else {
            --this.month;
        }
        boolean is_leap = CoreTime.isLeapYear(this.year);
        this.date = CoreTime.monthDays(this.month, is_leap);
        this.minusDays(minus_days);
    }

    public void plus_days(int days) {
        this.date += days;
        int datelimit = CoreTime.monthDays(this.month, CoreTime.isLeapYear(this.year));
        while (this.date > datelimit) {
            this.date -= datelimit;
            ++this.month;
            while (this.month > 12) {
                this.month -= 12;
                ++this.year;
            }
            datelimit = CoreTime.monthDays(this.month, CoreTime.isLeapYear(this.year));
        }
    }

    public void plus(CoreTime copy) {
        this.minuten += copy.minuten;
        while (this.minuten >= 60) {
            this.minuten -= 60;
            ++this.hour;
        }
        this.hour += copy.hour;
        while (this.hour >= 24) {
            this.hour -= 24;
            ++this.date;
        }
        this.date += copy.date;
        int datelimit = CoreTime.monthDays(this.month, CoreTime.isLeapYear(this.year));
        while (this.date > datelimit) {
            this.date -= datelimit;
            ++this.month;
            while (this.month > 12) {
                this.month -= 12;
                ++this.year;
            }
            datelimit = CoreTime.monthDays(this.month, CoreTime.isLeapYear(this.year));
        }
        this.month += copy.month;
        while (this.month > 12) {
            this.month -= 12;
            ++this.year;
        }
        this.year += copy.year;
        int datelimit_result_year = CoreTime.monthDays(this.month, CoreTime.isLeapYear(this.year));
        if (datelimit_result_year < this.date) {
            ++this.month;
            if (this.month >= 13) {
                this.month -= 12;
                ++this.year;
            }
            this.date = datelimit_result_year - this.date;
        }
    }

    public CoreTime(int year, int month, int date, int hour, int minuten) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minuten = minuten;
    }

    void update() {
        if (!eng.noNative) {
            this.year = eng.gYear();
            this.month = eng.gMonth();
            this.date = eng.gDate();
            this.hour = eng.gHour();
            this.minuten = eng.gMinuten();
        }
    }

    public int moreThan(CoreTime tm) {
        if (null == tm) {
            return 1;
        }
        if (this.year != tm.year) {
            return this.year - tm.year;
        }
        if (this.month != tm.month) {
            return this.month - tm.month;
        }
        if (this.date != tm.date) {
            return this.date - tm.date;
        }
        if (this.hour != tm.hour) {
            return this.hour - tm.hour;
        }
        if (this.minuten != tm.minuten) {
            return this.minuten - tm.minuten;
        }
        return 0;
    }

    public final int moreThanOnTime(CoreTime tm, CoreTime deltatime) {
        CoreTime ct = new CoreTime(tm);
        ct.plus(deltatime);
        return this.moreThan(ct);
    }

    public static CoreTime days(int _days) {
        return new CoreTime(0, 0, _days, 0, 0);
    }

    public static CoreTime daysNhours(int _days, int _hours) {
        return new CoreTime(0, 0, _days, _hours, 0);
    }

    public static CoreTime hours(int _hours) {
        return new CoreTime(0, 0, 0, _hours, 0);
    }

    public static CoreTime monthes(int _mon) {
        return new CoreTime(0, _mon, 0, 0, 0);
    }

    public static CoreTime monthesNDays(int _mon, int _days) {
        return new CoreTime(0, _mon, _days, 0, 0);
    }

    public static boolean isSameDate(CoreTime t1, CoreTime t2) {
        return t1.date == t2.date && t1.month == t2.month && t1.year == t2.year;
    }

    public static int CompareByDays(int year1, int month1, int date1, int year2, int month2, int date2) {
        return CoreTime.CompareByDays(new CoreTime(year1, month1, date1, 0, 0), new CoreTime(year2, month2, date2, 0, 0));
    }

    public static int CompareByDays(CoreTime time1incoming, CoreTime time2incoming) {
        int month;
        CoreTime time2;
        CoreTime time1;
        boolean order;
        boolean bl = order = time1incoming.moreThan(time2incoming) != 0;
        if (order) {
            time1 = time1incoming;
            time2 = time2incoming;
        } else {
            time1 = time2incoming;
            time2 = time1incoming;
        }
        int res = 0;
        boolean year1leap = CoreTime.isLeapYear(time1.year);
        boolean year2leap = CoreTime.isLeapYear(time2.year);
        if (time1.year > time2.year) {
            int year = time2.year;
            while (++year != time1.year) {
                if (CoreTime.isLeapYear(year)) {
                    res += 366;
                    continue;
                }
                res += 365;
            }
        }
        if (time1.month > time2.month) {
            if (time1.year > time2.year) {
                res = year2leap && time2.month < 3 || year1leap && time2.month >= 3 ? (res += 366) : (res += 365);
            }
            int day = time2.date;
            for (month = time2.month; month != time1.month; ++month) {
                res += CoreTime.count_days_tillend(month, day, year1leap);
                day = 1;
            }
            res += time1.date;
        } else if (time1.month <= time2.month) {
            if (time1.month == time2.month && time1.year == time2.year) {
                res += time1.date - time2.date;
            } else {
                int day = time2.date;
                for (month = time2.month; month != 13; ++month) {
                    res += CoreTime.count_days_tillend(month, day, year2leap);
                    day = 1;
                }
                day = 1;
                for (month = 1; month != time1.month; ++month) {
                    res += CoreTime.count_days_tillend(month, day, year1leap);
                }
                res += time1.date;
            }
        }
        return order ? res : -res;
    }

    public static boolean isLeapYear(int year) {
        boolean is_centennial;
        double remain_centennial = (double)year * 0.01;
        int year_centennial = 100 * (int)remain_centennial;
        boolean bl = is_centennial = year_centennial == year;
        if (is_centennial) {
            double remain = (double)year / 400.0;
            int year_400 = 400 * (int)remain;
            return year_400 == year;
        }
        double remain = (double)year / 4.0;
        int year_4 = 4 * (int)remain;
        return year_4 == year;
    }

    public static int monthDays(int month, boolean leap_year) {
        int datelimit = 30;
        switch (month) {
            case 1: 
            case 3: 
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 12: {
                datelimit = 31;
                break;
            }
            case 2: {
                datelimit = leap_year ? 29 : 28;
            }
        }
        return datelimit;
    }

    static int count_days_tillend(int frommonth, int fromday, boolean is_leap_year) {
        switch (frommonth) {
            case 1: 
            case 3: 
            case 5: 
            case 7: 
            case 8: 
            case 10: 
            case 12: {
                return 31 - fromday;
            }
            case 4: 
            case 6: 
            case 9: 
            case 11: {
                return 30 - fromday;
            }
            case 2: {
                return is_leap_year ? 29 - fromday : 28 - fromday;
            }
        }
        return 0;
    }
}
