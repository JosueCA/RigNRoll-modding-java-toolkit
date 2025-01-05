/*
 * Decompiled with CFR 0.151.
 */
package scenarioUtils;

import java.util.HashMap;
import java.util.logging.Level;
import rnrloggers.ScenarioLogger;

public class StringToSOTypeConverter {
    private static HashMap<String, Integer> conversionTable = new HashMap();
    private static final int DEFAULT_CONVERTED_VALUE = 1024;

    public static int convert(String type) {
        if (null != type) {
            Integer converted = conversionTable.get(type);
            if (null != converted) {
                return converted;
            }
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "StrintToSOTypeConverter: failed to convert " + type + " to sotip; returned default value");
        } else {
            ScenarioLogger.getInstance().machineLog(Level.WARNING, "StrintToSOTypeConverter: can't convert null reference; returned default value");
        }
        return 1024;
    }

    static {
        conversionTable.put("bar", 8);
        conversionTable.put("police", 64);
        conversionTable.put("johnhouse", 1024);
        conversionTable.put("scobject", 1024);
        conversionTable.put("wharehouse", 2);
        conversionTable.put("motel", 4);
        conversionTable.put("office", 1);
    }
}

