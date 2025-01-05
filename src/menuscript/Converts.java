// Decompiled with: CFR 0.152
// Class Version: 5
package menuscript;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENUbutton_field;
import menu.MacroKit;
import menu.menues;
import rnrcore.MacroBody;
import rnrcore.MacroBuilder;
import rnrcore.Macros;
import rnrcore.eng;
import rnrcore.loc;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Converts {
    public static int HeightToLines(int height, int baseline, int texth) {
        return (height - baseline) / texth;
    }

    public static String ConvertDouble(double value, int precision) {
        if (0 == precision) {
            return Integer.toString((int)value);
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < precision; ++i) {
            buf.append("#");
        }
        MessageFormat mf = new MessageFormat("{0,number,#." + buf.toString() + "}");
        return mf.format(new Object[]{value});
    }

    private static String ConvertMonthLoc(int month) {
        switch (month - 1) {
            case 0: {
                return loc.getDateString("JANUARY");
            }
            case 1: {
                return loc.getDateString("FEBRUARY");
            }
            case 2: {
                return loc.getDateString("MARCH");
            }
            case 3: {
                return loc.getDateString("APRIL");
            }
            case 4: {
                return loc.getDateString("MAY");
            }
            case 5: {
                return loc.getDateString("JUNE");
            }
            case 6: {
                return loc.getDateString("JULY");
            }
            case 7: {
                return loc.getDateString("AUGUST");
            }
            case 8: {
                return loc.getDateString("SEPTEMBER");
            }
            case 9: {
                return loc.getDateString("OCTOBER");
            }
            case 10: {
                return loc.getDateString("NOVEMBER");
            }
            case 11: {
                return loc.getDateString("DECEMBER");
            }
        }
        return loc.getDateString("JANUARY");
    }

    public static String makeClock(int hours) {
        String str_hours = "00";
        if (hours >= 10) {
            str_hours = "" + hours;
        } else if (hours >= 1 && hours < 10) {
            str_hours = "0" + hours;
        }
        return str_hours;
    }

    public static String makeClock00(int hours) {
        String str_hours = "000";
        if (hours >= 100) {
            str_hours = "" + hours;
        } else if (hours >= 10 && hours < 100) {
            str_hours = "0" + hours;
        } else if (hours >= 1 && hours < 10) {
            str_hours = "00" + hours;
        }
        return str_hours;
    }

    private static String GetPostfixForMotel(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            if (_hour < 12) {
                return "A";
            }
            return "P";
        }
        return "";
    }

    private static String GetPostfix(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            if (_hour < 12) {
                return " AM";
            }
            return " PM";
        }
        return "";
    }

    private static String makeClockHour(int _hour, int _min, int _sec) {
        if (eng.use_12HourTimeFormat()) {
            return Converts.makeClock(_hour % 12 == 0 ? 12 : _hour % 12);
        }
        return Converts.makeClock(_hour);
    }

    public static void ConverTimeAbsolute(MENUText_field text, int _hour, int _min, int _sec) {
        if (text != null && text.text != null) {
            String hour = Converts.makeClockHour(_hour, _min, _sec);
            String min = Converts.makeClock(_min) + (MacroKit.HasMacro(text.text, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
            String sec = Converts.makeClock(_sec) + (!MacroKit.HasMacro(text.text, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
            KeyPair[] key = new KeyPair[]{new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec)};
            MacroKit.ApplyToTextfield(text, key);
        }
    }

    public static String ConverTimeAbsolute(String macro_string, int _hour, int _min, int _sec) {
        String hour = Converts.makeClockHour(_hour, _min, _sec);
        String min = Converts.makeClock(_min) + (MacroKit.HasMacro(macro_string, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
        String sec = Converts.makeClock(_sec) + (!MacroKit.HasMacro(macro_string, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
        KeyPair[] key = new KeyPair[]{new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec)};
        String ret = MacroKit.Parse(macro_string, key);
        return ret;
    }

    public static void ConverTimeAbsolute(MENUText_field text, String macro_string, int _hour, int _min, int _sec) {
        String hour = Converts.makeClockHour(_hour, _min, _sec);
        String min = Converts.makeClock(_min) + (MacroKit.HasMacro(macro_string, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
        String sec = Converts.makeClock(_sec) + (!MacroKit.HasMacro(macro_string, "SECONDS") ? "" : Converts.GetPostfix(_hour, _min, _sec));
        KeyPair[] key = new KeyPair[]{new KeyPair("HOURS", hour), new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec)};
        text.text = MacroKit.Parse(macro_string, key);
        menues.UpdateField(text);
    }

    public static void ConvertTimeAllowed(MENUText_field text, int _hour, int _min, int _sec) {
        String min = Converts.makeClock00(_min + _hour * 60);
        String sec = Converts.makeClock(_sec);
        String string = min + ":" + sec;
        KeyPair[] macro = new KeyPair[]{new KeyPair("TIME_ALLOWED", string)};
        MacroKit.ApplyToTextfield(text, macro);
    }

    public static void ConvertTotalTime(MENUText_field text, int _hour, int _min, int _sec) {
        String min = Converts.makeClock00(_min + _hour * 60);
        String sec = Converts.makeClock(_sec);
        String string = min + ":" + sec;
        KeyPair[] macro = new KeyPair[]{new KeyPair("TOTAL_TIME", string)};
        MacroKit.ApplyToTextfield(text, macro);
    }

    public static void ConvertTimeLeft(MENUText_field text, int hour, int min, int sec) {
        String string = (hour >= 10 ? hour + "" : "0" + hour) + ":" + (min >= 10 ? min + "" : "0" + min) + ":" + (min >= 10 ? min + "" : "0" + sec);
        KeyPair[] macro = new KeyPair[]{new KeyPair("TIME_LEFT", string)};
        MacroKit.ApplyToTextfield(text, macro);
    }

    public static String ConverTimeForMotel(String macro_string, int _hour, int _min) {
        String min = Converts.makeClockHour(_hour, _min, 0);
        String sec = Converts.makeClock(_min) + Converts.GetPostfixForMotel(_hour, _min, 0);
        KeyPair[] key = new KeyPair[]{new KeyPair("HOURS", min), new KeyPair("MINUTES", sec)};
        String ret = MacroKit.Parse(macro_string, key);
        return ret;
    }

    public static String ConverTimeAbsolute(String macro_string, int _hour, int _min) {
        String min = Converts.makeClockHour(_hour, _min, 0);
        String sec = Converts.makeClock(_min) + Converts.GetPostfix(_hour, _min, 0);
        KeyPair[] key = new KeyPair[]{new KeyPair("HOURS", min), new KeyPair("MINUTES", sec)};
        String ret = MacroKit.Parse(macro_string, key);
        return ret;
    }

    public static String ConvertDate(String macro_string, int month, int year) {
        KeyPair[] key = new KeyPair[]{new KeyPair("MONTH", Converts.ConvertMonthLoc(month)), new KeyPair("YEAR", "" + year)};
        return MacroKit.Parse(macro_string, key);
    }

    public static void ConvertDate(MENUbutton_field text, int month, int date, int year) {
        KeyPair[] key = new KeyPair[]{new KeyPair("MONTH", Converts.ConvertMonthLoc(month)), new KeyPair("DATE", Converts.makeClock(date)), new KeyPair("YEAR", "" + year)};
        if (text != null) {
            if (text.origtext == null) {
                text.origtext = text.text;
            }
            text.text = MacroKit.Parse(text.origtext, key);
            menues.UpdateField(text);
        }
    }

    public static String ConvertDateLongAbsolute(String _source, int month, int date, int year, int _hour, int _min) {
        KeyPair[] key = new KeyPair[]{new KeyPair("MONTH", Converts.ConvertMonthLoc(month)), new KeyPair("DATE", Converts.makeClock(date)), new KeyPair("YEAR", "" + year)};
        return Converts.ConverTimeAbsolute(MacroKit.Parse(_source, key), _hour, _min);
    }

    public static String ConvertDateAbsolute(String _source, int month, int date, int year, int _hour, int _min) {
        KeyPair[] pair = new KeyPair[]{new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE"))};
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[]{new KeyPair("YEAR", "" + year), new KeyPair("MONTH", Converts.makeClock(month)), new KeyPair("DATE", Converts.makeClock(date))};
        return Converts.ConverTimeAbsolute(MacroKit.Parse(source, pairs), _hour, _min);
    }

    public static List<Macros> ConvertDateMacroAbsolute(int month, int date, int year, int _hour, int _min) {
        ArrayList<Macros> mocro_lst = new ArrayList<Macros>();
        mocro_lst.add(new Macros("YEAR", MacroBuilder.makeSimpleMacroBody("" + year)));
        mocro_lst.add(new Macros("MONTH", MacroBuilder.makeSimpleMacroBody(Converts.makeClock(month))));
        mocro_lst.add(new Macros("DATE", MacroBuilder.makeSimpleMacroBody(Converts.makeClock(date))));
        MacroBody body = MacroBuilder.makeMacroBody("DATE_TIME", "FULL_DATE", mocro_lst);
        Macros macros_full_date = new Macros("FULL_DATE", body);
        ArrayList<Macros> result = new ArrayList<Macros>();
        result.add(macros_full_date);
        result.add(new Macros("HOURS", MacroBuilder.makeSimpleMacroBody(Converts.makeClockHour(_hour, _min, 0))));
        result.add(new Macros("MINUTES", MacroBuilder.makeSimpleMacroBody(Converts.makeClock(_min) + Converts.GetPostfix(_hour, _min, 0))));
        return result;
    }

    public static String ConvertDateAbsolute(String _source, int month, int date, int year, int _hour, int _min, int _sec) {
        KeyPair[] pair = new KeyPair[]{new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE"))};
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[]{new KeyPair("YEAR", "" + year), new KeyPair("MONTH", Converts.makeClock(month)), new KeyPair("DATE", Converts.makeClock(date))};
        return Converts.ConverTimeAbsolute(MacroKit.Parse(source, pairs), _hour, _min, _sec);
    }

    public static String ConvertDate(String _source, int month, int date, int year) {
        KeyPair[] pair = new KeyPair[]{new KeyPair("FULL_DATE", loc.getDateString("FULL_DATE"))};
        String source = MacroKit.Parse(_source, pair);
        KeyPair[] pairs = new KeyPair[]{new KeyPair("YEAR", "" + year), new KeyPair("MONTH", Converts.makeClock(month)), new KeyPair("DATE", Converts.makeClock(date))};
        return MacroKit.Parse(source, pairs);
    }

    public static String ConverTime3Plus2(String macro_string, int _min, int _sec) {
        String min = Converts.makeClock00(_min);
        String sec = Converts.makeClock(_sec);
        KeyPair[] key = new KeyPair[]{new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec)};
        return MacroKit.Parse(macro_string, key);
    }

    public static String ConverTime3Plus2Total(String macro_string, int _hour, int _min, int _sec) {
        String min = Converts.makeClock00(_min + _hour * 60);
        String sec = Converts.makeClock(_sec);
        KeyPair[] key = new KeyPair[]{new KeyPair("TOTAL_TIME", min + ":" + sec)};
        return MacroKit.Parse(macro_string, key);
    }

    public static void ConverTime3Plus2(MENUText_field text, int _hour, int _min, int _sec) {
        String min = Converts.makeClock00(_min + _hour * 60);
        String sec = Converts.makeClock(_sec);
        KeyPair[] key = new KeyPair[]{new KeyPair("MINUTES", min), new KeyPair("SECONDS", sec)};
        MacroKit.ApplyToTextfield(text, key);
    }

    public static String ConvertNumeric(int value) {
        StringBuilder start = new StringBuilder(Integer.toString(value));
        int len = start.length();
        int i = 1;
        while (value / 1000 > 0) {
            start.insert(len - i * 3, ' ');
            value /= 1000;
            ++i;
        }
        return start.toString();
    }

    public static String ConvertSignedInit(int value) {
        return (value >= 0 ? "+" : "") + value;
    }

    public static double ConvertFahrenheit(double value) {
        return value * 9.0 / 5.0 + 32.0;
    }

    public static String newBigRaceSuffixes(int raceId) {
        return String.format("%02d", raceId + 1);
    }

    public static String ConvertRating(double _rating) {
        double rating = Math.abs(_rating);
        long frac = (long)(100.0 * (rating - Math.floor(rating)));
        long mean = (long)Math.floor(rating);
        return String.format("%d.%02d", mean, frac);
    }

    public static String bigRaceSuffixes(int raceId) {
        String groupSuffix = "";
        switch (raceId) {
            case 1: {
                groupSuffix = "04";
                break;
            }
            case 2: {
                groupSuffix = "03";
                break;
            }
            case 3: {
                groupSuffix = "02";
                break;
            }
            case 4: {
                groupSuffix = "01";
            }
        }
        return groupSuffix;
    }
}
