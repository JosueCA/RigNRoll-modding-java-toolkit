/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import menu.Helper;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SelectCb;
import menu.menues;
import menuscript.RNRMapWrapper;
import menuscript.office.ApplicationTab;
import menuscript.office.ManageBranchesManager;
import menuscript.office.OfficeMenu;
import rnrconfig.WorldCoordinates;
import rnrcore.eng;

public class CompanyStatistics
extends ApplicationTab {
    private static final String TAB_NAME = "NO";
    private static final String[] DATA_FIELDS = new String[]{"CS - Totals - Coverage VALUE", "CS - Totals - Warehouses VALUE", "CS - Totals - Turnover VALUE", "CS - Totals - Total assets VALUE", "CS - Totals - Branches VALUE", "CS - Totals - Total branches value VALUE", "CS - Totals - Trucks VALUE", "CS - Totals - Total trucks value VALUE", "CS - Totals - Drivers VALUE", "CS - Totals - Total return on investments VALUE", "CS - Totals - Total wage rate VALUE", "CS - Totals - About to quit VALUE", "CS - Drivers - Rank VALUE MIN", "CS - Drivers - Rank VALUE AVERAGE", "CS - Drivers - Rank VALUE MAX", "CS - Drivers - Wage rate VALUE MIN", "CS - Drivers - Wage rate VALUE AVERAGE", "CS - Drivers - Wage rate VALUE MAX", "CS - Drivers - ROI VALUE MIN", "CS - Drivers - ROI VALUE AVERAGE", "CS - Drivers - ROI VALUE MAX", "CS - Drivers - Loyalty VALUE MIN", "CS - Drivers - Loyalty VALUE AVERAGE", "CS - Drivers - Loyalty VALUE MAX", "CS - Trucks - Price VALUE MIN", "CS - Trucks - Price VALUE AVERAGE", "CS - Trucks - Price VALUE MAX", "CS - Trucks - Wear VALUE MIN", "CS - Trucks - Wear VALUE AVERAGE", "CS - Trucks - Wear VALUE MAX", "CS - Trucks - Speed VALUE MIN", "CS - Trucks - Speed VALUE AVERAGE", "CS - Trucks - Speed VALUE MAX", "CS - Trucks - Load Safety VALUE MIN", "CS - Trucks - Load Safety VALUE AVERAGE", "CS - Trucks - Load Safety VALUE MAX"};
    private final int DATA_COVERAGE = 0;
    private final int DATA_WAREHOUSES = 1;
    private final int DATA_TURNOVER = 2;
    private final int DATA_ASSETS = 3;
    private final int DATA_BRANCHES = 4;
    private final int DATA_BRANCHES_VALUE = 5;
    private final int DATA_TRUCKS = 6;
    private final int DATA_TRUCKS_VALUE = 7;
    private final int DATA_DRIVERS = 8;
    private final int DATA_RETURNS = 9;
    private final int DATA_WAGE = 10;
    private final int DATA_QUIT = 11;
    private final int DATA_RANK_MIN = 12;
    private final int DATA_RANK_AVE = 13;
    private final int DATA_RANK_MAX = 14;
    private final int DATA_WAGE_MIN = 15;
    private final int DATA_WAGE_AVE = 16;
    private final int DATA_WAGE_MAX = 17;
    private final int DATA_ROI_MIN = 18;
    private final int DATA_ROI_AVE = 19;
    private final int DATA_ROI_MAX = 20;
    private final int DATA_LOYAL_MIN = 21;
    private final int DATA_LOYAL_AVE = 22;
    private final int DATA_LOYAL_MAX = 23;
    private final int DATA_PRICE_MIN = 24;
    private final int DATA_PRICE_AVE = 25;
    private final int DATA_PRICE_MAX = 26;
    private final int DATA_WEAR_MIN = 27;
    private final int DATA_WEAR_AVE = 28;
    private final int DATA_WEAR_MAX = 29;
    private final int DATA_SPEED_MIN = 30;
    private final int DATA_SPEED_AVE = 31;
    private final int DATA_SPEED_MAX = 32;
    private final int DATA_LOAD_MIN = 33;
    private final int DATA_LOAD_AVE = 34;
    private final int DATA_LOAD_MAX = 35;
    private static final String[] MACRO = new String[]{"VALUE", "VALUE1", "MONEY", "SIGN"};
    private static final int MV = 0;
    private static final int MV1 = 1;
    private static final int MM = 2;
    private static final int SI = 3;
    private static final int[][] DATA_MACRO = new int[][]{{0}, {0, 1}, {2, 3}, {2, 3}, new int[0], {2, 3}, new int[0], {2, 3}, new int[0], {2, 3}, {2, 3}, new int[0], new int[0], new int[0], new int[0], {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {2, 3}, {0}, {0}, {0}, {2, 3}, {2, 3}, {2, 3}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}};
    private static final String[] DEPT_FIELDS = new String[]{"CS - Totals - Unsettled debts", "CS - Totals - Unsettled debts VALUE"};
    private static final int DEPT_NAME = 0;
    private static final int DEPT_VALUE = 1;
    private static final String DEPT_MACRO = MACRO[2];
    private int LIVE_WAREHOUSE = 4;
    private int SALES_WAREHOUSE = 3;
    private static final String MAP_NAME = "MAP - zooming picture CS";
    private static final String MAP_ZOOM = "CS - MAP";
    private static final String MAP_SHIFT = "CS - MAP";
    private long[] data_fields = new long[DATA_FIELDS.length];
    private String[] data_str = new String[DATA_FIELDS.length];
    private long[] debt_fields = new long[DEPT_FIELDS.length];
    private String[] debt_str = new String[DEPT_FIELDS.length];
    private RNRMapWrapper mapa;
    WorldCoordinates worldRectangle = null;

    public CompanyStatistics(long _menu, OfficeMenu parent) {
        super(_menu, TAB_NAME, parent);
        int i;
        for (i = 0; i < DATA_FIELDS.length; ++i) {
            this.data_fields[i] = menues.FindFieldInMenu(_menu, DATA_FIELDS[i]);
            this.data_str[i] = menues.GetFieldText(this.data_fields[i]);
        }
        for (i = 0; i < DEPT_FIELDS.length; ++i) {
            this.debt_fields[i] = menues.FindFieldInMenu(_menu, DEPT_FIELDS[i]);
            this.debt_str[i] = menues.GetFieldText(this.debt_fields[i]);
        }
        this.mapa = new RNRMapWrapper(_menu, MAP_NAME, "CS - MAP", "CS - MAP", new SelectMapControl());
        this.worldRectangle = WorldCoordinates.getCoordinates();
        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - COMPANY STATISTICS");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    public void afterInit() {
        for (int i = 0; i < this.debt_fields.length; ++i) {
            menues.SetBlindess(this.debt_fields[i], true);
            menues.SetIgnoreEvents(this.debt_fields[i], true);
        }
        this.mapa.afterInit();
        this.mapa.doNotWorkWith(this.LIVE_WAREHOUSE);
        this.mapa.doNotWorkWith(this.SALES_WAREHOUSE);
    }

    public boolean update() {
        if (!super.update()) {
            return false;
        }
        this.updateData();
        this.updateMapa();
        return true;
    }

    private void updateDept(int dept) {
        KeyPair[] macro = new KeyPair[]{new KeyPair(DEPT_MACRO, Helper.convertMoney(dept))};
        if (dept == 0) {
            menues.SetFieldState(this.debt_fields[0], 0);
            menues.SetFieldState(this.debt_fields[1], 0);
        } else {
            menues.SetFieldState(this.debt_fields[0], 1);
            menues.SetFieldState(this.debt_fields[1], 1);
        }
        menues.SetFieldText(this.debt_fields[1], MacroKit.Parse(this.debt_str[1], macro));
        menues.UpdateMenuField(menues.ConvertMenuFields(this.debt_fields[1]));
    }

    private CompanyInfo getInfo() {
        CompanyInfo info = new CompanyInfo();
        if (!eng.noNative) {
            JavaEvents.SendEvent(44, 6, info);
        }
        return info;
    }

    private void updateData() {
        CompanyInfo info = this.getInfo();
        this.updateDept(info.dept);
        for (int i = 0; i < this.data_fields.length; ++i) {
            KeyPair[] macro = DATA_MACRO[i].length != 0 ? new KeyPair[DATA_MACRO[i].length] : null;
            String[] values = DATA_MACRO[i].length != 0 ? new String[DATA_MACRO[i].length] : null;
            String value_single = "";
            switch (i) {
                case 0: {
                    values[0] = "" + (int)(100.0 * info.coverage);
                    break;
                }
                case 1: {
                    values[0] = "" + info.warehouses_have;
                    values[1] = "" + info.warehouses_from;
                    break;
                }
                case 2: {
                    values[0] = info.turnover >= 0 ? Helper.convertMoney(info.turnover) : "-" + Helper.convertMoney(info.turnover);
                    values[1] = info.turnover >= 0 ? "" : "-";
                    break;
                }
                case 3: {
                    values[0] = Helper.convertMoney(info.assets);
                    values[1] = info.assets >= 0 ? "" : "-";
                    break;
                }
                case 4: {
                    value_single = "" + info.branches;
                    break;
                }
                case 5: {
                    values[0] = Helper.convertMoney(info.branches_cost);
                    values[1] = info.branches_cost >= 0 ? "" : "-";
                    break;
                }
                case 6: {
                    value_single = "" + info.trucks;
                    break;
                }
                case 7: {
                    values[0] = Helper.convertMoney(info.trucks_cost);
                    values[1] = info.trucks_cost >= 0 ? "" : "-";
                    break;
                }
                case 8: {
                    value_single = "" + info.drivers;
                    break;
                }
                case 9: {
                    values[0] = Helper.convertMoney(info.roi);
                    values[1] = info.roi >= 0 ? "" : "-";
                    break;
                }
                case 10: {
                    values[0] = Helper.convertMoney(info.wage);
                    values[1] = info.wage >= 0 ? "" : "-";
                    break;
                }
                case 11: {
                    value_single = "" + info.want_quit;
                    break;
                }
                case 12: {
                    value_single = "" + info.rank_min;
                    break;
                }
                case 13: {
                    value_single = "" + info.rank_ave;
                    break;
                }
                case 14: {
                    value_single = "" + info.rank_max;
                    break;
                }
                case 15: {
                    values[0] = Helper.convertMoney(info.wage_min);
                    values[1] = info.wage_min >= 0 ? "" : "-";
                    break;
                }
                case 16: {
                    values[0] = Helper.convertMoney(info.wage_ave);
                    values[1] = info.wage_ave >= 0 ? "" : "-";
                    break;
                }
                case 17: {
                    values[0] = Helper.convertMoney(info.wage_max);
                    values[1] = info.wage_max >= 0 ? "" : "-";
                    break;
                }
                case 18: {
                    values[0] = Helper.convertMoney(info.roi_min);
                    values[1] = info.roi_min >= 0 ? "" : "-";
                    break;
                }
                case 19: {
                    values[0] = Helper.convertMoney(info.roi_ave);
                    values[1] = info.roi_ave >= 0 ? "" : "-";
                    break;
                }
                case 20: {
                    values[0] = Helper.convertMoney(info.roi_max);
                    values[1] = info.roi_max >= 0 ? "" : "-";
                    break;
                }
                case 21: {
                    values[0] = "" + info.loyal_min;
                    break;
                }
                case 22: {
                    values[0] = "" + info.loyal_ave;
                    break;
                }
                case 23: {
                    values[0] = "" + info.loyal_max;
                    break;
                }
                case 24: {
                    values[0] = Helper.convertMoney(info.truckprice_min);
                    values[1] = info.truckprice_min >= 0 ? "" : "-";
                    break;
                }
                case 25: {
                    values[0] = Helper.convertMoney(info.truckprice_ave);
                    values[1] = info.truckprice_ave >= 0 ? "" : "-";
                    break;
                }
                case 26: {
                    values[0] = Helper.convertMoney(info.truckprice_max);
                    values[1] = info.truckprice_max >= 0 ? "" : "-";
                    break;
                }
                case 27: {
                    values[0] = "" + info.truckwear_min;
                    break;
                }
                case 28: {
                    values[0] = "" + info.truckwear_ave;
                    break;
                }
                case 29: {
                    values[0] = "" + info.truckwear_max;
                    break;
                }
                case 30: {
                    values[0] = "" + info.truckspeed_min;
                    break;
                }
                case 31: {
                    values[0] = "" + info.truckspeed_ave;
                    break;
                }
                case 32: {
                    values[0] = "" + info.truckspeed_max;
                    break;
                }
                case 33: {
                    values[0] = "" + info.safe_min;
                    break;
                }
                case 34: {
                    values[0] = "" + info.safe_ave;
                    break;
                }
                case 35: {
                    values[0] = "" + info.safe_max;
                }
            }
            if (null != macro) {
                for (int j = 0; j < macro.length; ++j) {
                    macro[j] = new KeyPair(MACRO[DATA_MACRO[i][j]], values[j]);
                }
                menues.SetFieldText(this.data_fields[i], MacroKit.Parse(this.data_str[i], macro));
            } else {
                menues.SetFieldText(this.data_fields[i], value_single);
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(this.data_fields[i]));
        }
    }

    private void updateMapa() {
        this.mapa.ClearData();
        HashMap<String, ManageBranchesManager.WarehouseInfo> warehouses = new HashMap<String, ManageBranchesManager.WarehouseInfo>();
        Vector<ManageBranchesManager.OfficeInfo> our_offices = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(1, true);
        Vector<ManageBranchesManager.OfficeInfo> sales_offices = ManageBranchesManager.getManageBranchesManager().GetOfficesForSale(1, true);
        Vector<ManageBranchesManager.OfficeId> offices = new Vector<ManageBranchesManager.OfficeId>();
        for (ManageBranchesManager.OfficeInfo info : our_offices) {
            offices.add(info.id);
        }
        for (ManageBranchesManager.OfficeInfo info : sales_offices) {
            offices.add(info.id);
        }
        Vector<ManageBranchesManager.WarehouseInfo> warehouse_collection = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(offices);
        for (ManageBranchesManager.WarehouseInfo ware : warehouse_collection) {
            ManageBranchesManager.WarehouseInfo inf = (ManageBranchesManager.WarehouseInfo)warehouses.get(ware.name);
            if (null == inf) {
                warehouses.put(ware.name, ware);
                continue;
            }
            if (inf.companyAffected || !ware.companyAffected) continue;
            warehouses.put(ware.name, ware);
        }
        Collection warecoll = warehouses.values();
        // RICK: unknown object type
        for (Object unknownObjectType: warecoll) {
        	ManageBranchesManager.WarehouseInfo info = (ManageBranchesManager.WarehouseInfo) unknownObjectType;
            int type = info.companyAffected ? this.LIVE_WAREHOUSE : this.SALES_WAREHOUSE;
            this.mapa.addObject(type, (float)this.worldRectangle.convertX(info.x), (float)this.worldRectangle.convertY(info.y), info.name, new Object());
        }
    }

    public void deinit() {
    }

    public void apply() {
    }

    public void discard() {
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(5);
        }
    }

    static class CompanyInfo {
        double coverage;
        int warehouses_have;
        int warehouses_from;
        int turnover;
        int assets;
        int branches;
        int branches_cost;
        int trucks;
        int trucks_cost;
        int drivers;
        int roi;
        int wage;
        int want_quit;
        int dept;
        int rank_min;
        int rank_ave;
        int rank_max;
        int wage_min;
        int wage_ave;
        int wage_max;
        int roi_min;
        int roi_ave;
        int roi_max;
        int loyal_min;
        int loyal_ave;
        int loyal_max;
        int truckprice_min;
        int truckprice_ave;
        int truckprice_max;
        int truckwear_min;
        int truckwear_ave;
        int truckwear_max;
        int truckspeed_min;
        int truckspeed_ave;
        int truckspeed_max;
        int safe_min;
        int safe_ave;
        int safe_max;

        CompanyInfo() {
        }
    }

    class SelectMapControl
    implements SelectCb {
        SelectMapControl() {
        }

        public void OnSelect(int state, Object sender) {
        }
    }
}

