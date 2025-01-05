/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import java.util.ArrayList;
import java.util.Vector;
import menu.FocusManager;
import menu.Helper;
import menu.IFocusHolder;
import menu.KeyPair;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SelectCb;
import menu.menues;
import menuscript.RNRMapWrapper;
import menuscript.office.ApplicationTab;
import menuscript.office.ManageBranchesManager;
import menuscript.office.OfficeMenu;
import menuscript.table.Table;
import menuscript.tablewrapper.TableLine;
import menuscript.tablewrapper.TableWrapped;
import rnrconfig.WorldCoordinates;
import rnrcore.Log;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Branches
extends ApplicationTab {
    private boolean DEBUG = false;
    private int LIVE_WAREHOUSE = 4;
    private int OFFICE_WAREHOUSE = 5;
    private int LIVE_OFFICE = 11;
    private int SALES_OFFICE = 12;
    private static final String TAB_NAME = "BRANCHES";
    private static final String XML = "..\\data\\config\\menu\\menu_office.xml";
    private static final String COMPANY_TABLE = "MB - Company Branches - TABLEGROUP - 10 38";
    private static final String COMPANY_TABLE_RANGER = "MB - Company Branches - Tableranger";
    private static final String COMPANY_LINE = "Tablegroup - ELEMENTS - CompanyBranches";
    private static final String[] COMPANY_LINE_ELEMENTS = new String[]{"BUTTON - MB - Company Branches - Where VALUE", "BUTTON - MB - Company Branches - Where VALUE MARKED", "BUTTON - MB - Company Branches - Funds VALUE", "BUTTON - MB - Company Branches - Funds VALUE MARKED"};
    private static final int COMPANY_WHERE = 0;
    private static final int COMPANY_WHERE_GRAY = 1;
    private static final int COMPANY_VALUE = 2;
    private static final int COMPANY_VALUE_GRAY = 3;
    private static final String[] COMPANY_SORT = new String[]{"BUTTON - MB - Company Branches - WHERE", "BUTTON - MB - Company Branches - FUNDS"};
    private static final int COMPANY_SORT_WHERE = 0;
    private static final int COMPANY_SORT_FUNDS = 1;
    private static final String[] HEADQUATER = new String[]{"MB - Company Branches - OXNARD", "MB - Company Branches - HEADQUARTER"};
    private static final String HEADQUATER_SELECT = "onHeadquater";
    private static final String SALE_TABLE = "MB - Offices For Sale - TABLEGROUP - 11 38";
    private static final String SALE_TABLE_RANGER = "MB - Offices For Sale - Tableranger";
    private static final String SALE_LINE = "Tablegroup - ELEMENTS - Offices For Sale";
    private static final String[] SALE_LINE_ELEMENTS = new String[]{"BUTTON - MB - Offices For Sale - Where VALUE", "BUTTON - MB - Offices For Sale - Where VALUE MARKED", "BUTTON - MB - Offices For Sale - Funds VALUE", "BUTTON - MB - Offices For Sale - Funds VALUE MARKED"};
    private static final int SALE_WHERE = 0;
    private static final int SALE_WHERE_GRAY = 1;
    private static final int SALE_VALUE = 2;
    private static final int SALE_VALUE_GRAY = 3;
    private static final String[] SALE_SORT = new String[]{"BUTTON - MB - Offices For Sale - WHERE", "BUTTON - MB - Offices For Sale - INVESTMENT"};
    private static final String COMPANY_DESCRIPTION_TABLE = "MB - Company Branches - AREA - TABLEGROUP - 5 38";
    private static final String COMPANY_DESCRIPTION_TABLE_RANGER = "MB - Company Branches - AREA - Tableranger";
    private static final String SALES_DESCRIPTION_TABLE = "MB - Offices For Sale - AREA - TABLEGROUP - 5 38";
    private static final String SALES_DESCRIPTION_TABLE_RANGER = "MB - Offices For Sale - AREA - Tableranger";
    private static final String DESCRIPTION_LINE = "Tablegroup - ELEMENTS - CompanyBranches - AREA";
    private static final String[] DESCRIPTION_LINE_ELEMENTS = new String[]{"MB - Company Branches - AREA - Warehouse VALUE", "MB - Company Branches - AREA - Warehouse VALUE MARKED"};
    private static final int DESCRIPTION_NAME = 0;
    private static final int DESCRIPTION_NAME_MARKED = 1;
    private static final String[] SUMMARY_TEXTS = new String[]{"Established VALUE", "Closed Up VALUE", "Warehouses VALUE", "Invested VALUE", "Released VALUE", "Total VALUE"};
    private static final int SUMMARY_ESTABLISGED = 0;
    private static final int SUMMARY_CLOSED = 1;
    private static final int SUMMARY_ONCONTRACT = 2;
    private static final int SUMMARY_INVESTED = 3;
    private static final int SUMMARY_RELEASED = 4;
    private static final int SUMMARY_TOTAL = 5;
    private static final String MAP_NAME = "MAP - zooming picture BRANCES";
    private static final String MAP_ZOOM = "MB - MAP";
    private static final String MAP_SHIFT = "MB - MAP";
    private static final String MACRO_MONEY = "MONEY";
    private static final String MACRO_VALUE = "VALUE";
    private static final String MACRO_VALUE2 = "VALUE2";
    private static final String MACRO_SIGN = "SIGN";
    private static final String[] SUMMARY_MACRO = new String[]{"VALUE", "VALUE", "VALUE", "MONEY", "MONEY", "MONEY"};
    private static final String[] SUMMARY_MACRO2 = new String[]{"", "", "VALUE2", "SIGN", "SIGN", "SIGN"};
    private static final int[] SUMMARY_MACRO_NUM = new int[]{1, 1, 2, 2, 2, 2};
    private static final int SELECT_TYPE_COMPANY = 0;
    private static final int SELECT_TYPE_SALES = 1;
    private static final int SELECT_TYPE_HEADQUATER = 2;
    private static final String[] BUTTONS_ACTIONS = new String[]{"BUTTON - MB - Company Branches - CLOSE UP", "BUTTON - MB - Company Branches - CLOSE UP GRAY", "BUTTON - MB - Offices For Sale - ESTABLISH"};
    private static final String[] METHODS_ACTIONS = new String[]{"onCloseBranch", "", "onEstablishBranch"};
    private static final int ACTION_CLOSE = 0;
    private static final int ACTION_CLOSE_GRAY = 1;
    private CompanyBranches company = null;
    private SaleBranches sales = null;
    private CompanyWarehousesInArea company_warehouses = null;
    private SalesWarehousesInArea sales_warehouses = null;
    private RNRMapWrapper mapa;
    private String[] initialSummaryTexts = new String[SUMMARY_TEXTS.length];
    private long[] summaryTexts = new long[SUMMARY_TEXTS.length];
    private long[] action_controls;
    WorldCoordinates worldRectangle = null;
    HeadquaterLine headquater = null;
    SortedWarehouseDrawQuere warehousesQueue = new SortedWarehouseDrawQuere();

    public Branches(long _menu, OfficeMenu parent) {
        super(_menu, TAB_NAME, parent);
        int i;
        this.headquater = new HeadquaterLine(_menu);
        this.worldRectangle = WorldCoordinates.getCoordinates();
        this.company = new CompanyBranches(_menu);
        this.sales = new SaleBranches(_menu);
        this.company_warehouses = new CompanyWarehousesInArea(_menu);
        this.sales_warehouses = new SalesWarehousesInArea(_menu);
        for (i = 0; i < SUMMARY_TEXTS.length; ++i) {
            this.summaryTexts[i] = menues.FindFieldInMenu(_menu, SUMMARY_TEXTS[i]);
            this.initialSummaryTexts[i] = menues.GetFieldText(this.summaryTexts[i]);
        }
        this.mapa = new RNRMapWrapper(_menu, MAP_NAME, "MB - MAP", "MB - MAP", new SelectMapControl());
        this.action_controls = new long[BUTTONS_ACTIONS.length];
        for (i = 0; i < BUTTONS_ACTIONS.length; ++i) {
            this.action_controls[i] = menues.FindFieldInMenu(_menu, BUTTONS_ACTIONS[i]);
            if (i == 1) continue;
            menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.action_controls[i]), this, METHODS_ACTIONS[i], 4L);
        }
        long control = menues.FindFieldInMenu(_menu, "CALL OFFICE HELP - MANAGE BRANCHES");
        menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(control), this, "ShowHelp", 4L);
    }

    private void refresh_summary() {
        ManageBranchesManager.Summary summary = ManageBranchesManager.getManageBranchesManager().GetSummary();
        for (int i = 0; i < this.summaryTexts.length; ++i) {
            KeyPair[] keys = new KeyPair[SUMMARY_MACRO_NUM[i]];
            switch (i) {
                case 0: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.established);
                    break;
                }
                case 1: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.closed_up);
                    break;
                }
                case 2: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], "" + summary.warehouses_on_contract);
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], "" + summary.total_warehouses);
                    break;
                }
                case 3: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.invested));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.invested) >= 0 ? "" : "-");
                    break;
                }
                case 4: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.released));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.released) >= 0 ? "" : "-");
                    break;
                }
                case 5: {
                    keys[0] = new KeyPair(SUMMARY_MACRO[i], Helper.convertMoney((int)summary.total));
                    if (SUMMARY_MACRO_NUM[i] <= 1) break;
                    keys[1] = new KeyPair(SUMMARY_MACRO2[i], Math.abs((int)summary.total) >= 0 ? "" : "-");
                }
            }
            menues.SetFieldText(this.summaryTexts[i], MacroKit.Parse(this.initialSummaryTexts[i], keys));
            menues.UpdateMenuField(menues.ConvertMenuFields(this.summaryTexts[i]));
        }
    }

    private void refresh_mapa() {
        this.mapa.ClearData();
        this.warehousesQueue.clear();
        if (this.company.isInFocus()) {
            this.company.refresh_map_objects(true);
            this.headquater.refresh_map_objects(false);
            this.sales.refresh_map_objects(false);
        } else if (this.sales.isInFocus()) {
            this.company.refresh_map_objects(false);
            this.headquater.refresh_map_objects(false);
            this.sales.refresh_map_objects(true);
        } else if (this.headquater.isInFocus()) {
            this.company.refresh_map_objects(false);
            this.headquater.refresh_map_objects(true);
            this.sales.refresh_map_objects(false);
        }
        this.warehousesQueue.draw();
    }

    private void drawWarehousesOnMapa(CompanyLine dataline, boolean highlite) {
        ArrayList<CompanyLine> query = new ArrayList<CompanyLine>();
        query.add(dataline);
        ArrayList<DescriptionLine> data = this.reciveCompanyWarehouses(query);
        for (DescriptionLine line : data) {
            if (!line.companyAffected) continue;
            this.warehousesQueue.add(line, highlite, this.LIVE_WAREHOUSE, 0);
        }
    }

    private void drawWarehousesOnMapa(SalesLine dataline, boolean highlite) {
        ArrayList<SalesLine> query = new ArrayList<SalesLine>();
        query.add(dataline);
        ArrayList<DescriptionLine> data = this.reciveSalesWarehouses(query);
        for (DescriptionLine line : data) {
            if (line.companyAffected || !line.saleAffected) continue;
            this.warehousesQueue.add(line, highlite, this.OFFICE_WAREHOUSE, 1);
        }
    }

    public void onCloseBranch(long _menu, MENUsimplebutton_field button) {
        this.setDirty();
        this.closeBranch(this.company.getSelectedMultiple());
        this.update();
    }

    public void onEstablishBranch(long _menu, MENUsimplebutton_field button) {
        this.setDirty();
        this.establishBranch(this.sales.getSelectedMultiple());
        this.update();
    }

    private int getSortTypeCompany(int type) {
        switch (type) {
            case 0: {
                return 1;
            }
            case 1: {
                return 2;
            }
        }
        return -1;
    }

    private int getSortTypeSale(int type) {
        return this.getSortTypeCompany(type);
    }

    CompanyLine reciveCompanyHeadquater() {
        if (this.DEBUG) {
            CompanyLine item = new CompanyLine();
            item.id = new ManageBranchesManager.OfficeId();
            item.id.id = 4;
            item.name = "name44";
            item.funds = 1000000.0;
            item.x = 15000.0f;
            item.y = 0.0f;
            item.isGray = false;
            item.wheather_show = true;
            return item;
        }
        Vector<ManageBranchesManager.OfficeInfo> got = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(1, true);
        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = got.get(i);
            if (!info.isHeadquarter) continue;
            CompanyLine item = new CompanyLine();
            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;
            return item;
        }
        return null;
    }

    ArrayList<CompanyLine> reciveCompany() {
        if (this.DEBUG) {
            ArrayList<CompanyLine> res = new ArrayList<CompanyLine>();
            for (int i = 0; i < 3; ++i) {
                CompanyLine item = new CompanyLine();
                item.id = new ManageBranchesManager.OfficeId();
                item.id.id = i;
                item.name = "name" + i;
                item.funds = 12.4 * (double)i;
                item.x = -10000.0f * (float)i;
                item.y = -40000.0f + 10000.0f * (float)i;
                item.isGray = (i & 1) == 0;
                item.wheather_show = true;
                res.add(item);
            }
            return res;
        }
        Vector<ManageBranchesManager.OfficeInfo> got = ManageBranchesManager.getManageBranchesManager().GetCompanyBranches(this.company != null ? ((CompanyBranches)this.company).tablesort.type : 1, this.company != null ? ((CompanyBranches)this.company).tablesort.up : true);
        ArrayList<CompanyLine> res = new ArrayList<CompanyLine>();
        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = got.get(i);
            if (info.isHeadquarter) continue;
            CompanyLine item = new CompanyLine();
            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;
            res.add(item);
        }
        return res;
    }

    ArrayList<DescriptionLine> reciveCompanyWarehouses(ArrayList<CompanyLine> bunch) {
        if (this.DEBUG) {
            ArrayList<DescriptionLine> res = new ArrayList<DescriptionLine>();
            if (bunch == null) {
                return res;
            }
            for (CompanyLine data : bunch) {
                for (int i = 0; i < 3; ++i) {
                    DescriptionLine item = new DescriptionLine();
                    item.name = data.id.id + "name" + i;
                    item.x = -10000.0f + 12000.0f * (float)i;
                    item.y = (float)data.id.id * 10000.0f + -2000.0f * (float)i;
                    item.isGray = (i & 1) == 0;
                    item.companyAffected = (i & 1) == 1;
                    item.saleAffected = (i & 2) == 0;
                    item.wheather_show = true;
                    res.add(item);
                }
            }
            return res;
        }
        ArrayList<DescriptionLine> res = new ArrayList<DescriptionLine>();
        if (bunch == null) {
            return res;
        }
        Vector<ManageBranchesManager.OfficeId> list = new Vector<ManageBranchesManager.OfficeId>();
        for (CompanyLine line : bunch) {
            list.add(line.id);
        }
        Vector<ManageBranchesManager.WarehouseInfo> got = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(list);
        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.WarehouseInfo info = got.get(i);
            DescriptionLine item = new DescriptionLine();
            item.name = info.name;
            item.x = info.x;
            item.y = info.y;
            item.wheather_show = true;
            item.isGray = info.isGray;
            item.companyAffected = info.companyAffected;
            item.saleAffected = info.saleAffected;
            res.add(item);
        }
        return res;
    }

    void closeBranch(ArrayList<TableLine> data) {
        Vector<ManageBranchesManager.OfficeId> res = new Vector<ManageBranchesManager.OfficeId>(data.size());
        for (TableLine line : data) {
            CompanyLine cl = (CompanyLine)line;
            res.add(cl.id);
        }
        ManageBranchesManager.getManageBranchesManager().CloseUpBranch(res);
    }

    ArrayList<SalesLine> reciveSales() {
        if (this.DEBUG) {
            ArrayList<SalesLine> res = new ArrayList<SalesLine>();
            for (int i = 0; i < 3; ++i) {
                SalesLine item = new SalesLine();
                item.id = new ManageBranchesManager.OfficeId();
                item.id.id = i;
                item.name = "name" + i;
                item.funds = 12.4 * (double)i;
                item.x = -10000.0f + 10000.0f * (float)i;
                item.y = 20000.0f * (float)i;
                item.isGray = (i & 1) == 0;
                item.wheather_show = true;
                res.add(item);
            }
            return res;
        }
        Vector<ManageBranchesManager.OfficeInfo> got = ManageBranchesManager.getManageBranchesManager().GetOfficesForSale(this.sales != null ? this.sales.tablesort.type : 1, this.sales != null ? this.sales.tablesort.up : true);
        ArrayList<SalesLine> res = new ArrayList<SalesLine>();
        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.OfficeInfo info = got.get(i);
            SalesLine item = new SalesLine();
            item.id = info.id;
            item.name = info.where;
            item.funds = info.funds;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.wheather_show = true;
            res.add(item);
        }
        return res;
    }

    ArrayList<DescriptionLine> reciveSalesWarehouses(ArrayList<SalesLine> bunch) {
        if (this.DEBUG) {
            ArrayList<DescriptionLine> res = new ArrayList<DescriptionLine>();
            if (bunch == null) {
                return res;
            }
            for (SalesLine data : bunch) {
                for (int i = 0; i < 3; ++i) {
                    DescriptionLine item = new DescriptionLine();
                    item.name = data.id.id + "name" + i;
                    item.y = -10000.0f + 12000.0f * (float)i;
                    item.x = (float)data.id.id * 10000.0f + -2000.0f * (float)i;
                    item.isGray = (i & 1) == 0;
                    item.companyAffected = (i & 1) == 1;
                    item.saleAffected = (i & 2) == 0;
                    item.wheather_show = true;
                    res.add(item);
                }
            }
            return res;
        }
        ArrayList<DescriptionLine> res = new ArrayList<DescriptionLine>();
        if (bunch == null) {
            return res;
        }
        Vector<ManageBranchesManager.OfficeId> list = new Vector<ManageBranchesManager.OfficeId>();
        for (SalesLine line : bunch) {
            list.add(line.id);
        }
        Vector<ManageBranchesManager.WarehouseInfo> got = ManageBranchesManager.getManageBranchesManager().GetWarehousesInTheArea(list);
        for (int i = 0; i < got.size(); ++i) {
            ManageBranchesManager.WarehouseInfo info = got.get(i);
            DescriptionLine item = new DescriptionLine();
            item.name = info.name;
            item.x = info.x;
            item.y = info.y;
            item.isGray = info.isGray;
            item.companyAffected = info.companyAffected;
            item.saleAffected = info.saleAffected;
            item.wheather_show = true;
            res.add(item);
        }
        return res;
    }

    void establishBranch(ArrayList<TableLine> data) {
        Vector<ManageBranchesManager.OfficeId> res = new Vector<ManageBranchesManager.OfficeId>(data.size());
        for (TableLine line : data) {
            SalesLine sl = (SalesLine)line;
            res.add(sl.id);
        }
        ManageBranchesManager.getManageBranchesManager().EstablishNewBranch(res);
    }

    @Override
    public void afterInit() {
        this.mapa.afterInit();
        this.mapa.workWith(this.LIVE_OFFICE);
        this.mapa.workWith(this.SALES_OFFICE);
        this.company_warehouses.afterInit();
        this.sales_warehouses.afterInit();
        this.company.afterInit();
        this.sales.afterInit();
    }

    @Override
    public void apply() {
        this.setDirty();
        ManageBranchesManager.ErrorInfo err = ManageBranchesManager.getManageBranchesManager().Apply();
        switch (err.error_code) {
            case 1: {
                this.makeNotEnoughMoney();
                break;
            }
            case 2: {
                this.makeNotEnoughTurns(err.misc_info);
            }
        }
        this.update();
    }

    @Override
    public void deinit() {
        this.company.deinit();
        this.sales.deinit();
        this.company_warehouses.deinit();
        this.sales_warehouses.deinit();
        this.headquater.deinit();
    }

    @Override
    public void discard() {
        this.setDirty();
        ManageBranchesManager.getManageBranchesManager().Discard();
        this.update();
    }

    @Override
    public boolean update() {
        if (!super.update()) {
            return false;
        }
        this.sales.updateTable();
        this.company.updateTable();
        this.refresh_summary();
        this.refresh_mapa();
        this.makeUpdate();
        this.headquater.selectHeadQuater();
        return true;
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(3);
        }
    }

    class SortedWarehouseDrawQuere {
        private ArrayList<SortedWarehouseDrawQuereItem> queue = new ArrayList();

        SortedWarehouseDrawQuere() {
        }

        void clear() {
            this.queue.clear();
        }

        void add(DescriptionLine item, boolean hightlite, int type, int type_select) {
            for (SortedWarehouseDrawQuereItem line : this.queue) {
                if (line.line.name.compareTo(item.name) != 0) continue;
                if (hightlite && !line.hightlite) {
                    line.hightlite = hightlite;
                    line.type = type;
                    line.type_select = type_select;
                }
                return;
            }
            this.queue.add(new SortedWarehouseDrawQuereItem(item, hightlite, type, type_select));
        }

        void draw() {
            for (SortedWarehouseDrawQuereItem line : this.queue) {
                line.line.map_id = Branches.this.mapa.addObject(line.type, (float)Branches.this.worldRectangle.convertX(line.line.x), (float)Branches.this.worldRectangle.convertY(line.line.y), line.line.name, new SelectMapData(line.line, line.type_select));
                Branches.this.mapa.selectHighlight(line.line.map_id, line.hightlite);
            }
        }
    }

    static class SortedWarehouseDrawQuereItem {
        DescriptionLine line;
        boolean hightlite;
        int type;
        int type_select;

        SortedWarehouseDrawQuereItem(DescriptionLine line, boolean hightlite, int type, int type_select) {
            this.line = line;
            this.hightlite = hightlite;
            this.type = type;
            this.type_select = type_select;
        }
    }

    class SalesWarehousesInArea
    extends WarehousesInArea {
        SalesWarehousesInArea(long _menu) {
            super(_menu, Branches.SALES_DESCRIPTION_TABLE, Branches.SALES_DESCRIPTION_TABLE_RANGER);
        }

        protected void reciveTableData() {
            ArrayList<SalesLine> query = new ArrayList<SalesLine>();
            ArrayList<TableLine> selected = Branches.this.sales.getSelectedMultiple();
            for (TableLine line : selected) {
                if (!line.wheather_show) continue;
                query.add((SalesLine)line);
            }
            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveSalesWarehouses(query));
        }
    }

    class CompanyWarehousesInArea
    extends WarehousesInArea {
        CompanyWarehousesInArea(long _menu) {
            super(_menu, Branches.COMPANY_DESCRIPTION_TABLE, Branches.COMPANY_DESCRIPTION_TABLE_RANGER);
        }

        protected void reciveTableData() {
            if (Branches.this.headquater.isInFocus()) {
                ArrayList<CompanyLine> query = new ArrayList<CompanyLine>();
                query.add(Branches.this.headquater.headquaterInfo);
                this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompanyWarehouses(query));
            } else {
                ArrayList<CompanyLine> query = new ArrayList<CompanyLine>();
                ArrayList<TableLine> selected = Branches.this.company.getSelectedMultiple();
                for (TableLine line : selected) {
                    if (!line.wheather_show) continue;
                    query.add((CompanyLine)line);
                }
                this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompanyWarehouses(query));
            }
        }
    }

    abstract class WarehousesInArea
    extends TableWrapped {
        protected void deinit() {
            this.table.deinit();
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        WarehousesInArea(long _menu, String TABLE, String TABLE_RANGER) {
            super(_menu, 0, false, Branches.XML, TABLE, TABLE_RANGER, Branches.DESCRIPTION_LINE, DESCRIPTION_LINE_ELEMENTS, null, null);
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
        }

        public final void SetupLineInTable(long button, int position, TableLine table_node) {
            DescriptionLine line = (DescriptionLine)table_node;
            switch (position) {
                case 0: {
                    if (line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(button));
                    break;
                }
                case 1: {
                    if (!line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    menues.UpdateMenuField(menues.ConvertMenuFields(button));
                }
            }
        }

        public final void updateSelectedInfo(TableLine linedata) {
        }
    }

    static class DescriptionLine
    extends TableLine {
        String name;
        int map_id;
        float x;
        float y;
        boolean isGray;
        boolean companyAffected;
        boolean saleAffected;

        DescriptionLine() {
        }
    }

    class SaleBranches
    extends TableWrapped {
        sort tablesort;
        private String originalFunds;

        SaleBranches(long _menu) {
            super(_menu, 2, false, Branches.XML, Branches.SALE_TABLE, Branches.SALE_TABLE_RANGER, Branches.SALE_LINE, SALE_LINE_ELEMENTS, null, SALE_SORT);
            this.tablesort = new sort(Branches.this.getSortTypeSale(0), true);
            this.originalFunds = "";
            long[] texts = this.table.getLineStatistics_controls(SALE_LINE_ELEMENTS[2]);
            if (null == texts || texts.length == 0) {
                Log.menu("ERRORR. SaleBranches table does not includes " + SALE_LINE_ELEMENTS[2] + " control in line.");
            }
            this.originalFunds = menues.GetFieldText(texts[0]);
            this.deselectOnLooseFocus(true);
        }

        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveSales());
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            SalesLine line = (SalesLine)table_node;
            switch (position) {
                case 0: {
                    if (line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    break;
                }
                case 1: {
                    if (!line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    break;
                }
                case 2: {
                    if (line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    KeyPair[] keys = new KeyPair[]{new KeyPair(Branches.MACRO_MONEY, Helper.convertMoney((int)line.funds))};
                    menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                    break;
                }
                case 3: {
                    if (!line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    KeyPair[] keys = new KeyPair[]{new KeyPair(Branches.MACRO_MONEY, Helper.convertMoney((int)line.funds))};
                    menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
            Branches.this.sales_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            this.tablesort = new sort(Branches.this.getSortTypeSale(button.userid), this.tablesort.type == Branches.this.getSortTypeSale(button.userid) ? !this.tablesort.up : true);
            this.updateTable();
        }

        public void refresh_map_objects(boolean hightlite) {
            for (TableLine line : this.TABLE_DATA.all_lines) {
                if (!line.wheather_show) continue;
                SalesLine item = (SalesLine)line;
                String displayName = "";
                item.map_id = Branches.this.mapa.addObject(Branches.this.SALES_OFFICE, (float)Branches.this.worldRectangle.convertX(item.x), (float)Branches.this.worldRectangle.convertY(item.y), displayName, new SelectMapData(item, 1));
                Branches.this.mapa.selectHighlight(item.map_id, item.selected && hightlite);
                Branches.this.drawWarehousesOnMapa(item, item.selected && hightlite);
            }
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        protected void deinit() {
            this.table.deinit();
        }
    }

    static class SalesLine
    extends TableLine {
        ManageBranchesManager.OfficeId id;
        int map_id;
        String name;
        double funds;
        float x;
        float y;
        boolean isGray = false;

        SalesLine() {
        }
    }

    class CompanyBranches
    extends TableWrapped {
        private sort tablesort;
        private String originalFunds;

        CompanyBranches(long _menu) {
            super(_menu, 2, false, Branches.XML, Branches.COMPANY_TABLE, Branches.COMPANY_TABLE_RANGER, Branches.COMPANY_LINE, COMPANY_LINE_ELEMENTS, null, COMPANY_SORT);
            this.tablesort = new sort(Branches.this.getSortTypeCompany(0), true);
            this.originalFunds = "";
            long[] texts = this.table.getLineStatistics_controls(COMPANY_LINE_ELEMENTS[2]);
            if (null == texts || texts.length == 0) {
                Log.menu("ERRORR. CompanyBranches table does not includes " + COMPANY_LINE_ELEMENTS[2] + " control in line.");
            }
            this.originalFunds = menues.GetFieldText(texts[0]);
            this.deselectOnLooseFocus(true);
        }

        public void updateTable() {
            super.updateTable();
            if (!this.TABLE_DATA.all_lines.isEmpty() && !this.TABLE_DATA.all_lines.get((int)0).wheather_show) {
                Branches.this.headquater.selectHeadQuater();
            } else {
                Branches.this.headquater.deselectHeadQuater();
            }
        }

        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(Branches.this.reciveCompany());
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            CompanyLine line = (CompanyLine)table_node;
            switch (position) {
                case 0: {
                    if (line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    break;
                }
                case 1: {
                    if (!line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.name);
                    break;
                }
                case 2: {
                    if (line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    KeyPair[] keys = new KeyPair[]{new KeyPair(Branches.MACRO_MONEY, Helper.convertMoney((int)line.funds))};
                    menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                    break;
                }
                case 3: {
                    if (!line.isGray) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    KeyPair[] keys = new KeyPair[]{new KeyPair(Branches.MACRO_MONEY, Helper.convertMoney((int)line.funds))};
                    menues.SetFieldText(button, MacroKit.Parse(this.originalFunds, keys));
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
            Branches.this.company_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
            this.tablesort = new sort(Branches.this.getSortTypeCompany(button.userid), this.tablesort.type == Branches.this.getSortTypeCompany(button.userid) ? !this.tablesort.up : true);
            this.updateTable();
        }

        public void refresh_map_objects(boolean selectmode) {
            for (TableLine line : this.TABLE_DATA.all_lines) {
                if (!line.wheather_show) continue;
                CompanyLine item = (CompanyLine)line;
                String displayName = "";
                item.map_id = Branches.this.mapa.addObject(Branches.this.LIVE_OFFICE, (float)Branches.this.worldRectangle.convertX(item.x), (float)Branches.this.worldRectangle.convertY(item.y), displayName, new SelectMapData(item, 0));
                Branches.this.mapa.selectHighlight(item.map_id, item.selected && selectmode);
                Branches.this.drawWarehousesOnMapa(item, item.selected && selectmode);
            }
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        protected void deinit() {
            this.table.deinit();
        }
    }

    static class CompanyLine
    extends TableLine {
        ManageBranchesManager.OfficeId id;
        int map_id;
        String name;
        double funds;
        float x;
        float y;
        boolean isGray = false;

        CompanyLine() {
        }
    }

    static class sort {
        int type;
        boolean up;

        sort(int type, boolean up) {
            this.type = type;
            this.up = up;
        }
    }

    class HeadquaterLine
    implements IFocusHolder {
        private long[] headquater = new long[Branches.access$300().length];
        private CompanyLine headquaterInfo = null;

        public void cbEnterFocus() {
        }

        public void cbLeaveFocus() {
            this.deselectHeadQuater();
        }

        public void ControlsCtrlAPressed() {
        }

        public boolean isInFocus() {
            return FocusManager.isFocused(this);
        }

        HeadquaterLine(long _menu) {
            for (int i = 0; i < HEADQUATER.length; ++i) {
                this.headquater[i] = menues.FindFieldInMenu(_menu, HEADQUATER[i]);
                menues.SetScriptOnControl(_menu, menues.ConvertMenuFields(this.headquater[i]), this, Branches.HEADQUATER_SELECT, 2L);
            }
            menues.SetFieldText(this.headquater[0], ManageBranchesManager.getManageBranchesManager().GetHeadQuaterName());
            FocusManager.register(this);
            this.headquaterInfo = Branches.this.reciveCompanyHeadquater();
        }

        public void onHeadquater(long _menu, MENUbutton_field button) {
            this.selectHeadQuater();
        }

        private void selectHeadQuater() {
            FocusManager.enterFocus(this);
            for (int i = 0; i < this.headquater.length; ++i) {
                menues.SetFieldState(this.headquater[i], 1);
            }
            menues.SetShowField(Branches.this.action_controls[0], false);
            menues.SetShowField(Branches.this.action_controls[1], true);
            Branches.this.company_warehouses.updateTable();
            Branches.this.refresh_mapa();
        }

        private void deselectHeadQuater() {
            for (int i = 0; i < this.headquater.length; ++i) {
                menues.SetFieldState(this.headquater[i], 0);
            }
            menues.SetShowField(Branches.this.action_controls[0], true);
            menues.SetShowField(Branches.this.action_controls[1], false);
        }

        public void refresh_map_objects(boolean selectmode) {
            CompanyLine item = this.headquaterInfo;
            String display_name = "";
            item.map_id = Branches.this.mapa.addObject(Branches.this.LIVE_OFFICE, (float)Branches.this.worldRectangle.convertX(item.x), (float)Branches.this.worldRectangle.convertY(item.y), display_name, new SelectMapData(item, 2));
            Branches.this.mapa.selectHighlight(item.map_id, selectmode);
            Branches.this.drawWarehousesOnMapa(item, selectmode);
        }

        public void deinit() {
            FocusManager.unRegister(this);
        }
    }

    class SelectMapControl
    implements SelectCb {
        SelectMapControl() {
        }

        public void OnSelect(int state, Object sender) {
            SelectMapData data = (SelectMapData)sender;
            switch (data.type) {
                case 2: {
                    Branches.this.headquater.selectHeadQuater();
                    break;
                }
                case 0: {
                    Branches.this.company.EnterFocus();
                    Branches.this.company.selectLineByData(data.line);
                    break;
                }
                case 1: {
                    Branches.this.sales.EnterFocus();
                    Branches.this.sales.selectLineByData(data.line);
                }
            }
        }
    }

    static class SelectMapData {
        TableLine line;
        int type;

        SelectMapData(TableLine line, int type) {
            this.line = line;
            this.type = type;
        }
    }
}

