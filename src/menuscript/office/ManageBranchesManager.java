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
public class ManageBranchesManager {
    private boolean DEBUG_MODE = eng.noNative;
    private static ManageBranchesManager instance;
    private ErrorInfo apply_error_code = new ErrorInfo();
    public int in_int = 0;
    public boolean in_bool = false;
    public Vector in_ids = new Vector();
    private Vector offices_info = new Vector();
    private Vector warehouses_info = new Vector();
    private Summary summary = new Summary();
    public String out_string = "Oxnard";
    public static final int sort_by_name = 1;
    public static final int sort_by_funds = 2;
    public static final int apply_ok = 0;
    public static final int apply_not_enough_money = 1;
    public static final int apply_not_enough_turnover = 2;

    public static ManageBranchesManager getManageBranchesManager() {
        if (null == instance) {
            instance = new ManageBranchesManager();
        }
        return instance;
    }

    private ManageBranchesManager() {
        this.apply_error_code.error_code = 0;
        this.apply_error_code.misc_info = 0;
        if (this.DEBUG_MODE) {
            this.summary.established = 2;
            this.summary.closed_up = 1;
            this.summary.warehouses_on_contract = 24;
            this.summary.total_warehouses = 42;
            this.summary.invested = -780000.0f;
            this.summary.released = 360000.0f;
            this.summary.total = 420000.0f;
            OfficeInfo info0 = new OfficeInfo();
            info0.id = new OfficeId(0);
            info0.where = "Reno";
            info0.funds = 450000.0f;
            info0.isGray = false;
            info0.isHeadquarter = false;
            info0.x = 0.0f;
            info0.y = 0.0f;
            this.offices_info.add(info0);
            OfficeInfo info1 = new OfficeInfo();
            info1.id = new OfficeId(1);
            info1.where = "San Diego";
            info1.funds = 570000.0f;
            info1.isGray = true;
            info1.isHeadquarter = false;
            info1.x = 0.5f;
            info1.y = 0.5f;
            this.offices_info.add(info1);
            OfficeInfo info2 = new OfficeInfo();
            info2.id = new OfficeId(2);
            info2.where = "Oxnard";
            info2.funds = 570000.0f;
            info2.isGray = false;
            info2.isHeadquarter = true;
            info2.x = 0.0f;
            info2.y = 0.0f;
            this.offices_info.add(info2);
            WarehouseInfo w0 = new WarehouseInfo();
            w0.name = "Los Angeles";
            w0.isGray = false;
            w0.x = -0.5f;
            w0.y = -0.5f;
            w0.companyAffected = true;
            w0.saleAffected = false;
            this.warehouses_info.add(w0);
            WarehouseInfo w1 = new WarehouseInfo();
            w1.name = "Santa Barbara";
            w1.isGray = true;
            w1.x = -0.25f;
            w1.y = -0.25f;
            w1.companyAffected = true;
            w1.saleAffected = true;
            WarehouseInfo w2 = new WarehouseInfo();
            w2.name = "Santa Barbara";
            w2.isGray = true;
            w2.x = -0.25f;
            w2.y = -0.25f;
            w2.companyAffected = false;
            w2.saleAffected = true;
            this.warehouses_info.add(w2);
        }
    }

    Summary GetSummary() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(37, 0, this);
        }
        return this.summary;
    }

    Vector<OfficeInfo> GetCompanyBranches(int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(37, 1, this);
        }
        Vector res = (Vector)this.offices_info.clone();
        return res;
    }

    Vector<OfficeInfo> GetOfficesForSale(int sort_mode, boolean bUp) {
        if (!this.DEBUG_MODE) {
            this.in_int = sort_mode;
            this.in_bool = bUp;
            JavaEvents.SendEvent(37, 2, this);
        }
        Vector res = (Vector)this.offices_info.clone();
        return res;
    }

    Vector<WarehouseInfo> GetWarehousesInTheArea(Vector<OfficeId> list) {
        if (!this.DEBUG_MODE) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 3, this);
        }
        Vector res = (Vector)this.warehouses_info.clone();
        return res;
    }

    void CloseUpBranch(Vector<OfficeId> list) {
        if (!this.DEBUG_MODE) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 4, this);
        }
    }

    void EstablishNewBranch(Vector<OfficeId> list) {
        if (!this.DEBUG_MODE) {
            this.in_ids = list;
            JavaEvents.SendEvent(37, 5, this);
        }
    }

    ErrorInfo Apply() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(37, 6, this);
        }
        return this.apply_error_code;
    }

    void Discard() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(37, 7, this);
        }
    }

    String GetHeadQuaterName() {
        if (!this.DEBUG_MODE) {
            JavaEvents.SendEvent(37, 8, this);
        }
        return this.out_string;
    }

    static class ErrorInfo {
        int error_code;
        int misc_info;

        ErrorInfo() {
        }
    }

    static class Summary {
        int established;
        int closed_up;
        int warehouses_on_contract;
        int total_warehouses;
        float invested;
        float released;
        float total;

        Summary() {
        }
    }

    static class WarehouseInfo {
        String name;
        boolean isGray;
        float x;
        float y;
        boolean companyAffected;
        boolean saleAffected;

        WarehouseInfo() {
        }
    }

    static class OfficeInfo {
        OfficeId id = new OfficeId();
        String where;
        float funds;
        boolean isGray;
        boolean isHeadquarter;
        float x;
        float y;

        OfficeInfo() {
        }
    }

    static class OfficeId {
        int id;

        OfficeId(int id) {
            this.id = id;
        }

        OfficeId() {
        }
    }
}

