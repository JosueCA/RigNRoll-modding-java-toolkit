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
public class ManageFlitManager {
    private boolean DEBUG_MODE = eng.noNative;
    private static ManageFlitManager instance;
    private Vector fields = new Vector();
    private Vector short_fields = new Vector();
    private FullMyVehicleInfo full_my_info = new FullMyVehicleInfo();
    private FullDealerVehicleInfo full_dealer_info = new FullDealerVehicleInfo();
    private Summary summary = new Summary();
    private Vector short_my_info = new Vector();
    private Vector short_dealer_info = new Vector();
    private ShotMyVehicleInfo short_my_vehicle_info = new ShotMyVehicleInfo();
    private int apply_error_code = 0;
    public int in_int = 0;
    public Filter in_filter = new Filter();
    public VehicleId in_id = new VehicleId();
    public boolean in_bool = false;
    public Vector in_ids = new Vector();
    public static final int my_sort_by_name = 1;
    public static final int my_sort_by_price = 2;
    public static final int my_sort_by_license = 3;
    public static final int my_sort_by_type = 4;
    public static final int my_sort_by_condition = 5;
    public static final int my_sort_by_wear = 6;
    public static final int my_sort_by_speed = 7;
    public static final int my_sort_by_load_safety = 8;
    public static final int my_sort_by_horse_power = 9;
    public static final int my_sort_by_color = 10;
    public static final int dealer_sort_by_name = 1;
    public static final int dealer_sort_by_dealer = 2;
    public static final int dealer_sort_by_descount = 3;
    public static final int dealer_sort_by_price = 4;
    public static final int dealer_sort_by_type = 5;
    public static final int dealer_sort_by_make = 6;
    public static final int dealer_sort_by_model = 7;
    public static final int dealer_sort_by_milleage = 8;
    public static final int dealer_sort_by_suspension = 9;
    public static final int dealer_sort_by_horsepower = 10;
    public static final int dealer_sort_by_color = 11;
    public static final int apply_ok = 0;
    public static final int apply_not_enough_vehicles_for_drivers = 1;
    public static final int apply_not_enough_money = 2;
    public static final int field_price = 0;
    public static final int field_type = 1;
    public static final int field_make = 2;
    public static final int field_model = 3;
    public static final int field_milleage = 4;
    public static final int field_horse_power = 5;
    public static final int field_suspension = 6;
    public static final int field_color = 7;
    public static final int field_max = 8;

    public static ManageFlitManager getManageFlitManager() {
        if (null == instance) {
            instance = new ManageFlitManager();
        }
        return instance;
    }

    private ManageFlitManager() {
        if (this.DEBUG_MODE) {
            FilterField field1 = new FilterField();
            FilterField field2 = new FilterField();
            FilterField field3 = new FilterField();
            FilterField field4 = new FilterField();
            field1.name = "Any";
            field1.show_me = false;
            field2.name = "1";
            field2.show_me = false;
            field3.name = "2";
            field3.show_me = true;
            field4.name = "...";
            field4.show_me = false;
            this.fields.add(field1);
            this.fields.add(field2);
            this.fields.add(field3);
            this.fields.add(field4);
            this.short_fields.add(field1);
            this.short_fields.add(field2);
            this.short_fields.add(field3);
            this.short_fields.add(field4);
            this.full_my_info.id = new VehicleId(1);
            this.full_my_info.vehicle_name = "Kenworth t200";
            this.full_my_info.license_plate = "VODILA1";
            this.full_my_info.type = "tractor";
            this.full_my_info.condition = 0.5f;
            this.full_my_info.wear = 0.2f;
            this.full_my_info.speed = 0.1f;
            this.full_my_info.load_safety = 0.6f;
            this.full_my_info.horse_power = 300.0f;
            this.full_my_info.color = "Red";
            this.full_my_info.vehicle_picture = "FREIGHTLINER_CENTURY";
            this.full_my_info.color_picture = "Unknown";
            this.full_dealer_info.id = new VehicleId(2);
            this.full_dealer_info.vehicle_name = "Freightliner Century";
            this.full_dealer_info.price = 52000.0f;
            this.full_dealer_info.type = "tractor";
            this.full_dealer_info.make = "Freightliner";
            this.full_dealer_info.model = "Century";
            this.full_dealer_info.mileage = 560.0f;
            this.full_dealer_info.suspension = "Air Ride 100";
            this.full_dealer_info.horsepower = 420.0f;
            this.full_dealer_info.color = "Black";
            this.full_dealer_info.vehicle_picture = "FREIGHTLINER_ARGOSY";
            this.full_dealer_info.color_picture = "Unknown";
            this.summary.vehiles_to_sell = 8;
            this.summary.vehiles_to_purchase = 4;
            this.summary.proceeds = 80000.0f;
            this.summary.expenses = -225000.0f;
            this.summary.overhead = -20000.0f;
            this.summary.total = 165000.0f;
            for (int i = 0; i < 10; ++i) {
                ShotMyVehicleInfo my = new ShotMyVehicleInfo();
                my.id = new VehicleId(2 * (i + 1));
                my.vehicle_name = "Mack Vision " + i;
                my.price = 52000.0f + 5000.0f * (float)i;
                my.isGray = (i & 1) != 0;
                this.short_my_info.add(my);
                ShotDealerVehicleInfo dealer = new ShotDealerVehicleInfo();
                dealer.id = new VehicleId(2 * (i + 1) + 1);
                dealer.vehicle_name = "Freightliner Century " + i;
                dealer.dealer_name = "Reno Automarket " + i;
                dealer.discount = 0.05f * (float)i;
                dealer.price = 44000.0f + 10000.0f * (float)i;
                dealer.isGray = (i & 1) == 0;
                this.short_dealer_info.add(dealer);
            }
            this.short_my_vehicle_info.id = new VehicleId(99);
            this.short_my_vehicle_info.vehicle_name = "Peterbilt 378";
            this.short_my_vehicle_info.price = 58000.0f;
            this.short_my_vehicle_info.isGray = false;
        }
    }

    Vector<FilterField> GetFilterFields(int filed) {
        if (!this.DEBUG_MODE) {
            this.in_int = filed;
            JavaEvents.SendEvent(33, 0, this);
        }
        Vector res = (Vector)this.fields.clone();
        return res;
    }

    Vector<FilterField> GetShortFilterFields(int filed) {
        if (!this.DEBUG_MODE) {
            this.in_int = filed;
            JavaEvents.SendEvent(33, 13, this);
        }
        Vector res = (Vector)this.short_fields.clone();
        return res;
    }

    void SetFilter(Filter filter) {
        if (!this.DEBUG_MODE) {
            this.in_filter = filter;
            JavaEvents.SendEvent(33, 1, this);
        }
    }

    FullMyVehicleInfo GetMyVehiclesInfo(VehicleId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 2, this);
        }
        return this.full_my_info;
    }

    FullMyVehicleInfo GetMyVehiclesInfoWithUpgrades(VehicleId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 17, this);
        }
        return this.full_my_info;
    }

    FullDealerVehicleInfo GetDealerVehiclesInfo(VehicleId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 3, this);
        }
        return this.full_dealer_info;
    }

    FullDealerVehicleInfo GetDealerVehiclesInfoWithUpgrades(VehicleId selected_id) {
        if (!this.DEBUG_MODE) {
            this.in_id = selected_id;
            JavaEvents.SendEvent(33, 16, this);
        }
        return this.full_dealer_info;
    }

    Summary GetSummary() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 4, this);
        }
        return this.summary;
    }

    ShotMyVehicleInfo GetMyVehicleInfo() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 5, this);
        }
        return this.short_my_vehicle_info;
    }

    Vector<ShotMyVehicleInfo> GetMyVehiclesList(int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(33, 6, this);
        }
        Vector res = (Vector)this.short_my_info.clone();
        return res;
    }

    Vector<ShotDealerVehicleInfo> GetDealerVehiclesList(Filter filter, int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            this.in_filter = filter;
            JavaEvents.SendEvent(33, 7, this);
        }
        Vector res = (Vector)this.short_dealer_info.clone();
        return res;
    }

    boolean Can_I_Take() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 18, this);
            return this.in_bool;
        }
        return false;
    }

    void I_Take(VehicleId id) {
        if (!this.DEBUG_MODE) {
            this.in_id = id;
            JavaEvents.SendEvent(33, 8, this);
        }
    }

    void I_Sell(Vector<VehicleId> ids) {
        if (!this.DEBUG_MODE) {
            this.in_ids = ids;
            JavaEvents.SendEvent(33, 9, this);
        }
    }

    void I_Purchase(Vector<VehicleId> ids) {
        if (!this.DEBUG_MODE) {
            this.in_ids = ids;
            JavaEvents.SendEvent(33, 10, this);
        }
    }

    void Discard() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 11, this);
        }
    }

    int Apply() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 12, this);
        }
        return this.apply_error_code;
    }

    void OnEnterMenu() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 14, this);
        }
    }

    void OnLeaveMenu() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(33, 15, this);
        }
    }

    static class Filter {
        int[] fileds = new int[8];
        String model_user;

        Filter() {
        }
    }

    static class Summary {
        int vehiles_to_sell;
        int vehiles_to_purchase;
        float proceeds;
        float expenses;
        float overhead;
        float total;

        Summary() {
        }
    }

    static class FullDealerVehicleInfo {
        VehicleId id = new VehicleId();
        String vehicle_name;
        float price;
        String type;
        String make;
        String model;
        float mileage;
        float condition;
        String suspension;
        float horsepower;
        String color;
        String vehicle_picture;
        String color_picture;
        String upgrades_info_string;

        FullDealerVehicleInfo() {
        }
    }

    static class ShotDealerVehicleInfo {
        VehicleId id = new VehicleId();
        String vehicle_name;
        String dealer_name;
        float discount;
        float price;
        boolean isGray;

        ShotDealerVehicleInfo() {
        }
    }

    static class FullMyVehicleInfo {
        VehicleId id = new VehicleId();
        String vehicle_name;
        String license_plate;
        String type;
        float condition;
        float wear;
        float speed;
        float load_safety;
        float horse_power;
        float mileage;
        String color;
        String vehicle_picture;
        String color_picture;
        String upgrades_info_string;
        String suspension;

        FullMyVehicleInfo() {
        }
    }

    static class ShotMyVehicleInfo {
        VehicleId id = new VehicleId();
        String vehicle_name;
        float price;
        boolean isGray;

        ShotMyVehicleInfo() {
        }
    }

    static class VehicleId {
        int id;

        VehicleId(int id) {
            this.id = id;
        }

        VehicleId() {
        }
    }

    static class FilterField {
        String name;
        boolean show_me;

        FilterField() {
        }
    }
}

