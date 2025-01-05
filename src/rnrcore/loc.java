/*
 * Decompiled with CFR 0.151.
 */
package rnrcore;

import rnrcore.eng;

public class loc {
    public static final String JOURNAL = "journal";
    public static final String MPLONG = "MPLONG";
    public static final String MPSHORT = "MPSHORT";
    public static final String BIGRACE_SHORTNAME = "BIGRACE_SHORTNAME";
    public static final String MERCHNAME = "MERCHNAME";
    public static final String CITYNAME = "CITYNAME";
    public static final String CITYNAME_NATIVE = "CITYNAME_NATIVE";
    public static final String DATE_TIME = "DATE_TIME";

    public static String getNewspaperString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("newspaper", str);
        }
        return str;
    }

    public static String getRepairTableString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("carsystems", str);
        }
        return str;
    }

    public static String getMenuString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("menu", str);
        }
        return str;
    }

    public static String getMENUString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("MENU", str);
        }
        return str;
    }

    public static String getMissionPointLongName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(MPLONG, str);
        }
        return str;
    }

    public static String getMissionPointShortName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(MPSHORT, str);
        }
        return str;
    }

    public static String getJournalString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(JOURNAL, str);
        }
        return str;
    }

    public static String getOrgString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("organizer", str);
        }
        return str;
    }

    public static String getScenarioNamesString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("SCENARIONAMES", str);
        }
        return str;
    }

    public static String getnickNamesString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("NICKNAMES", str);
        }
        return str;
    }

    public static String getCustomerName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("customer", str);
        }
        return str;
    }

    public static String getDialogName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("DIALOG", str);
        }
        return str;
    }

    public static String getMissionSuccesPictureText(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("MISSION_PICTURE", str);
        }
        return str;
    }

    public static String getBigraceShortName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(BIGRACE_SHORTNAME, str);
        }
        return str;
    }

    public static String getBigraceFullName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("BIGRACE_FULLNAME", str);
        }
        return str;
    }

    public static String getBigraceDescription(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("BIGRACE_DESCRIPTION", str);
        }
        return str;
    }

    public static String getDateString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(DATE_TIME, str);
        }
        return str;
    }

    public static String getAiString(String str) {
        if (!eng.noNative) {
            return eng.getStringRef("AI", str);
        }
        return str;
    }

    public static String getCityName(String str) {
        if (!eng.noNative) {
            return eng.getStringRef(CITYNAME, str);
        }
        return str;
    }
}

