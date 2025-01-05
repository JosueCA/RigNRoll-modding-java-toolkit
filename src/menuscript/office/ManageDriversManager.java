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
public class ManageDriversManager {
    private static ManageDriversManager instance;
    private Vector exist_short_driver_info = new Vector();
    public Vector ids = new Vector();
    public DriverId id = new DriverId();
    public float in_value = 0.0f;
    private float out_value = 0.0f;
    private float out_value0 = 0.0f;
    private float out_value1 = 0.0f;
    private FullDriverInfo full_driver_info = new FullDriverInfo();
    private Summary summary = new Summary();
    private boolean bRet = false;
    public int sort_mode = 1;
    public boolean bSortUp = false;
    public boolean in_b_value = false;
    private boolean DEBUG_MODE = eng.noNative;
    public static final int sort_by_name = 1;
    public static final int sort_by_rank = 2;
    public static final int sort_by_wage = 3;
    public static final int sort_by_loyality = 4;
    public static final int sort_by_roi = 5;
    public static final int sort_by_wins_div_missions = 6;
    public static final int sort_by_forefit = 7;
    public static final int sort_by_maintenance = 8;
    public static final int sort_by_gas = 9;
    public static final int sort_by_vehicle = 10;

    public static ManageDriversManager getManageDriversManager() {
        if (null == instance) {
            instance = new ManageDriversManager();
        }
        return instance;
    }

    private ManageDriversManager() {
        if (this.DEBUG_MODE) {
            int NOM = 2;
            int i = 0;
            while (i < NOM) {
                ShotDriverInfo inf = new ShotDriverInfo();
                inf.id = new DriverId(i);
                inf.driver_name = "name " + i;
                inf.isGray = (i & 1) != 0;
                inf.wage = 100.0f * (float)i;
                inf.x = (float)((double)i / (double)NOM);
                inf.y = (float)((double)i / (double)NOM);
                inf.rank = i++;
                this.exist_short_driver_info.add(inf);
            }
            this.full_driver_info.id = new DriverId(1);
            this.full_driver_info.driver_name = "some";
            this.full_driver_info.age = 75;
            this.full_driver_info.face_index = "Woman001";
            this.full_driver_info.loyalty = 0.1f;
            this.full_driver_info.roi = 0.1f;
            this.full_driver_info.wins_missions = 100;
            this.full_driver_info.forefit = 0.1f;
            this.full_driver_info.maintenance = 0.1f;
            this.full_driver_info.gas = 0.1f;
            this.full_driver_info.short_vehicle_name = "some vn";
            this.full_driver_info.make = "MAKE";
            this.full_driver_info.model = "MODEL";
            this.full_driver_info.license_plate = "LICENSE";
            this.full_driver_info.vehJustBought = true;
        }
    }

    Vector<ShotDriverInfo> GetShortDriverInfoList(int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.sort_mode = sort_mode;
            this.bSortUp = bUp;
            JavaEvents.SendEvent(29, 0, this);
            Vector res = (Vector)this.exist_short_driver_info.clone();
            this.exist_short_driver_info.clear();
            return res;
        }
        Vector res = (Vector)this.exist_short_driver_info.clone();
        return res;
    }

    FullDriverInfo GetFullDriverInfo(DriverId id) {
        this.id = id;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 1, this);
        }
        return this.full_driver_info;
    }

    void SetOrderType(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 2, this);
        }
    }

    float GetOrderType(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 3, this);
        }
        return this.out_value;
    }

    void SetLoadFargylity(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 4, this);
        }
    }

    float GetLoadFragylity(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 5, this);
        }
        return this.out_value;
    }

    void SetDistances(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 6, this);
        }
    }

    float GetDistances(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 7, this);
        }
        return this.out_value;
    }

    void SetWear(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 8, this);
        }
    }

    float GetWear(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 9, this);
        }
        return this.out_value;
    }

    void SetSpeed(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 10, this);
        }
    }

    float GetSpeed(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 11, this);
        }
        return this.out_value;
    }

    void SetLoadSafety(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 12, this);
        }
    }

    float GetLoadSafety(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return 0.0f;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 13, this);
        }
        return this.out_value;
    }

    void SetWage(Vector<DriverId> ids, float value) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_value = value;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 14, this);
        }
    }

    int[] GetWage(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return new int[]{0, 100, 0};
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 26, this);
            return new int[]{Math.round(this.out_value), Math.round(this.out_value0), Math.round(this.out_value1)};
        }
        return new int[]{0, 100, 0};
    }

    boolean Apply() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 16, this);
        }
        return this.bRet;
    }

    void Discard() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 17, this);
        }
    }

    Summary GetSummary() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 18, this);
        }
        return this.summary;
    }

    void SetOrderSelectionCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_b_value = bSetAuto;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 19, this);
        }
    }

    boolean GetOrderSelectionCriteria(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 20, this);
        }
        return this.bRet;
    }

    void SetTruckSelectionCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_b_value = bSetAuto;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 21, this);
        }
    }

    boolean GetTruckSelectionCriteria(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 22, this);
        }
        return this.bRet;
    }

    void SetWageChangeCriteria(Vector<DriverId> ids, boolean bSetAuto) {
        if (ids == null || ids.size() == 0) {
            return;
        }
        this.ids = ids;
        this.in_b_value = bSetAuto;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 23, this);
        }
    }

    boolean GetWageChangeCriteria(Vector<DriverId> ids) {
        if (ids == null || ids.size() == 0) {
            return true;
        }
        this.ids = ids;
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 24, this);
        }
        return this.bRet;
    }

    void OnEnterOffice() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(29, 25, this);
        }
    }

    void OnLeaveOffice() {
    }

    static class Summary {
        int drivers_involved;
        int order_criteria_changed;
        int truck_criteria_changed;
        int wages_changed_drivers;
        float wages_changed_day;

        Summary() {
        }
    }

    static class FullDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        int age;
        String face_index;
        float loyalty;
        float loyalty_temp;
        float roi;
        float roi_temp;
        int wins_missions;
        float wins_temp;
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

        FullDriverInfo() {
        }
    }

    static class ShotDriverInfo {
        DriverId id = new DriverId();
        String driver_name;
        int rank;
        float wage;
        boolean isGray;
        float x;
        float y;

        ShotDriverInfo() {
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
}

