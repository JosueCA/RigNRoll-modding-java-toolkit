/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.Vector;
import menu.JavaEvents;
import rnrcore.eng;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class HireFireDriversManager {
    private boolean DEBUG_MODE = eng.noNative;
    private static HireFireDriversManager instance;
    private int apply_error_code = 0;
    public int in_int = 0;
    public Filter in_filter = new Filter();
    private Vector fields = new Vector();
    private Vector short_fields = new Vector();
    public DriverId in_id = new DriverId();
    private FullMyDriverInfo full_my_info = new FullMyDriverInfo();
    private FullDealerDriverInfo full_dealer_info = new FullDealerDriverInfo();
    private Summary summary = new Summary();
    public boolean in_bool = false;
    public Vector in_ids = new Vector();
    private Vector short_my_info = new Vector();
    private Vector short_dealer_info = new Vector();
    public static final int my_sort_by_name = 1;
    public static final int my_sort_by_rank = 2;
    public static final int my_sort_by_wage = 3;
    public static final int my_sort_by_loyalty = 4;
    public static final int my_sort_by_roi = 5;
    public static final int my_sort_by_wins_div_miss = 6;
    public static final int my_sort_by_forefit = 7;
    public static final int my_sort_by_maintenance = 8;
    public static final int my_sort_by_gas = 9;
    public static final int my_sort_by_vehicle = 10;
    public static final int dealer_sort_by_name = 1;
    public static final int dealer_sort_by_recruiter = 2;
    public static final int dealer_sort_by_rank = 3;
    public static final int dealer_sort_by_wage = 4;
    public static final int dealer_sort_by_gender = 5;
    public static final int dealer_sort_by_age = 6;
    public static final int dealer_sort_by_tickets = 7;
    public static final int dealer_sort_by_accidents = 8;
    public static final int dealer_sort_by_has_felony = 9;
    public static final int dealer_sort_by_experience = 10;
    public static final int apply_ok = 0;
    public static final int apply_not_enough_vehicles_for_drivers = 1;
    public static final int apply_not_enough_money = 2;
    public static final int apply_your_rank_is_not_sufficient = 3;
    public static final int field_gender = 0;
    public static final int field_age = 1;
    public static final int field_tickets = 2;
    public static final int field_accidents = 3;
    public static final int field_has_felony = 4;
    public static final int field_experience = 5;
    public static final int field_max = 6;

    public static HireFireDriversManager getHireFireDriversManager() {
        if (null == instance) {
            instance = new HireFireDriversManager();
        }
        return instance;
    }

    private HireFireDriversManager() {
        if (this.DEBUG_MODE) {
            FilterField field1 = new FilterField();
            FilterField field2 = new FilterField();
            FilterField field3 = new FilterField();
            FilterField field4 = new FilterField();
            field1.name = "Any";
            field1.show_me = false;
            field2.name = "18-25";
            field2.show_me = false;
            field3.name = "25-35";
            field3.show_me = true;
            field4.name = "35-45";
            field4.show_me = false;
            this.fields.add(field1);
            this.fields.add(field2);
            this.fields.add(field3);
            this.fields.add(field4);
            this.short_fields.add(field1);
            this.short_fields.add(field2);
            this.short_fields.add(field3);
            this.short_fields.add(field4);
            this.full_my_info.id = new DriverId(1);
            this.full_my_info.driver_name = "Driver1";
            this.full_my_info.age = 15;
            this.full_my_info.face_index = "Woman001";
            this.full_my_info.loyalty = 18.0f;
            this.full_my_info.roi = 15000.0f;
            this.full_my_info.forefit = 10000.0f;
            this.full_my_info.wins_missions = 100;
            this.full_my_info.maintenance = 30000.0f;
            this.full_my_info.gas = 4000.0f;
            this.full_my_info.short_vehicle_name = "Century";
            this.full_my_info.make = "Century";
            this.full_my_info.model = "Frrr";
            this.full_my_info.license_plate = "01234567";
            this.full_my_info.vehJustBought = true;
            this.full_dealer_info.id = new DriverId(2);
            this.full_dealer_info.driver_name = "Player112";
            this.full_dealer_info.age = 18;
            this.full_dealer_info.face_index = "Man_020";
            this.full_dealer_info.gender = "Female";
            this.full_dealer_info.tickets = 999;
            this.full_dealer_info.accidents = 1850;
            this.full_dealer_info.bHasFelony = true;
            this.full_dealer_info.experience = 7.0f;
            this.summary.vacant = 999;
            this.summary.to_fire = 888;
            this.summary.to_hire = 100;
            this.summary.advance = 8000000.0f;
            this.summary.commission = -2.25E7f;
            this.summary.total = 1000000.0f;
            for (int i = 0; i < 10; ++i) {
                ShotMyDriverInfo my = new ShotMyDriverInfo();
                my.id = new DriverId(2 * (i + 1));
                my.driver_name = "Driver " + i;
                my.rank = 100 * i;
                my.wage = 10000.0f + 10000.0f * (float)i;
                my.justHire = (i & 1) != 0;
                this.short_my_info.add(my);
                ShotDealerDriverInfo dealer = new ShotDealerDriverInfo();
                dealer.id = new DriverId(2 * (i + 1) + 1);
                dealer.driver_name = "Ghhasbnm" + i + "sdafgahk";
                dealer.dealer_name = "California Drivers " + i;
                dealer.rank = 200 * i;
                dealer.wage = 10000.0f + 20000.0f * (float)i;
                dealer.justFire = (i & 1) == 0;
                dealer.bCanHire = (i & 2) == 0;
                this.short_dealer_info.add(dealer);
            }
        }
    }

    Vector<FilterField> GetFilterFields(int filed) {
        if (!this.DEBUG_MODE) {
            this.in_int = filed;
            JavaEvents.SendEvent(35, 0, this);
        }
        Vector res = (Vector)this.fields.clone();
        return res;
    }

    Vector<FilterField> GetShortFilterFields(int filed) {
        if (!this.DEBUG_MODE) {
            this.in_int = filed;
            JavaEvents.SendEvent(35, 11, this);
        }
        Vector res = (Vector)this.short_fields.clone();
        return res;
    }

    void SetFilter(Filter filter) {
        if (!this.DEBUG_MODE) {
            this.in_filter = filter;
            JavaEvents.SendEvent(35, 1, this);
        }
    }

    FullMyDriverInfo GetMyDriverInfo(DriverId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(35, 2, this);
        }
        return this.full_my_info;
    }

    FullDealerDriverInfo GetDealerVehiclesInfo(DriverId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(35, 3, this);
        }
        return this.full_dealer_info;
    }

    Summary GetSummary() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(35, 4, this);
        }
        return this.summary;
    }

    Vector<ShotMyDriverInfo> GetMyDriverList(int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(35, 5, this);
        }
        Vector res = (Vector)this.short_my_info.clone();
        return res;
    }

    Vector<ShotDealerDriverInfo> GetDealerVehiclesList(Filter filter, int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            this.SetFilter(filter);
            JavaEvents.SendEvent(35, 6, this);
        }
        Vector res = (Vector)this.short_dealer_info.clone();
        return res;
    }

    void I_Fire(Vector<DriverId> ids) {
        if (!this.DEBUG_MODE) {
            this.in_ids = ids;
            JavaEvents.SendEvent(35, 7, this);
        }
    }

    int I_Hire(Vector<DriverId> ids) {
        if (!this.DEBUG_MODE) {
            this.in_ids = ids;
            JavaEvents.SendEvent(35, 8, this);
        }
        return this.apply_error_code;
    }

    void Discard() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(35, 9, this);
        }
    }

    int Apply() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(35, 10, this);
        }
        return this.apply_error_code;
    }

    static class Filter {
        int[] fileds = new int[6];

        Filter() {
        }
    }

    static class Summary {
        int vacant;
        int to_fire;
        int to_hire;
        float advance;
        float commission;
        float total;

        Summary() {
        }
    }

    static class FullDealerDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        int age;
        String face_index;
        String gender;
        int tickets;
        int accidents;
        boolean bHasFelony;
        float experience;

        FullDealerDriverInfo() {
        }
    }

    static class ShotDealerDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        String dealer_name;
        int rank;
        float wage;
        boolean justFire;
        boolean bCanHire;

        ShotDealerDriverInfo() {
        }
    }

    static class FullMyDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        int age;
        String face_index;
        float loyalty;
        float loyalty_temp;
        float roi;
        float roi_temp;
        float wins_temp;
        int wins_missions;
        float forefit;
        float forefit_temp;
        float maintenance;
        float maintenance_temp;
        float gas;
        float gas_temp;
        String short_vehicle_name;
        String make;
        String model;
        String license_plate;
        boolean vehJustBought;

        FullMyDriverInfo() {
        }
    }

    static class ShotMyDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        int rank;
        float wage;
        boolean justHire;

        ShotMyDriverInfo() {
        }
    }

    static class DriverId {
        int id;

        DriverId(int id) {
            this.id = id;
        }

        DriverId() {
        }
    }

    static class FilterField {
        String name;
        boolean show_me;

        FilterField() {
        }
    }
}

