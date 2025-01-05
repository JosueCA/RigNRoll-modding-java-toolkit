/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.Vector;
import menu.JavaEvents;

public class ProfileManagement {
    private static ProfileManagement instance = null;
    private String profile_name;
    private int profile_logo;
    private String new_profile_name;
    private Vector exits_profile_names = new Vector();
    private boolean bRet = false;
    private String default_license_string;
    private String profile_license_string;

    public static ProfileManagement getProfileManager() {
        if (null == instance) {
            instance = new ProfileManagement();
        }
        return instance;
    }

    private ProfileManagement() {
    }

    Vector GetExistsProfiles() {
        JavaEvents.SendEvent(24, 0, this);
        Vector res = (Vector)this.exits_profile_names.clone();
        this.exits_profile_names.clear();
        return res;
    }

    void SetPrifile(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 1, this);
    }

    boolean DeleteProfile(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 2, this);
        return this.bRet;
    }

    boolean RenameProfile(String profile_name, String new_profile_name) {
        this.profile_name = profile_name;
        this.new_profile_name = new_profile_name;
        JavaEvents.SendEvent(24, 3, this);
        return this.bRet;
    }

    String GetCurrentProfileName() {
        JavaEvents.SendEvent(24, 4, this);
        return this.profile_name;
    }

    String getDefaultProfileName() {
        JavaEvents.SendEvent(24, 5, this);
        return this.profile_name;
    }

    void SetCurrentProfileLogo(int logo) {
        this.profile_logo = logo;
        JavaEvents.SendEvent(24, 6, this);
    }

    int GetCurrentProfileLogo() {
        JavaEvents.SendEvent(24, 7, this);
        return this.profile_logo;
    }

    void SetProfileLogo(String profile_name, int logo) {
        this.profile_logo = logo;
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 8, this);
    }

    int GetProfileLogo(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 9, this);
        return this.profile_logo;
    }

    boolean CreateProfile(String profile_name, int logo, String licese_string) {
        this.profile_name = profile_name;
        this.profile_logo = logo;
        this.profile_license_string = licese_string;
        JavaEvents.SendEvent(24, 11, this);
        return this.bRet;
    }

    boolean IsProfileExists(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 12, this);
        return this.bRet;
    }

    String GetCurrentProfileLicensePlateString() {
        JavaEvents.SendEvent(24, 13, this);
        return this.profile_license_string;
    }

    void SetCurrentProfileLicensePlateString(String name) {
        this.profile_license_string = name;
        JavaEvents.SendEvent(24, 14, this);
    }

    String GetProfileLicensePlateString(String profile_name) {
        this.profile_name = profile_name;
        JavaEvents.SendEvent(24, 15, this);
        return this.profile_license_string;
    }

    void SetProfileLicensePlateString(String profile_name, String licese_string) {
        this.profile_name = profile_name;
        this.profile_license_string = licese_string;
        JavaEvents.SendEvent(24, 16, this);
    }

    String GetDefaultLicensePlateString() {
        JavaEvents.SendEvent(24, 17, this);
        return this.default_license_string;
    }

    String GetDefaultLicensePlateSuffix() {
        JavaEvents.SendEvent(24, 18, this);
        return this.default_license_string;
    }
}

