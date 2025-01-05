/*
 * Decompiled with CFR 0.151.
 */
package menuscript.org;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.Cmenu_TTI;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menues;
import menuscript.Converts;
import menuscript.org.ChampionShipPane;
import menuscript.org.IOrgTab;
import menuscript.table.ISetupLine;
import menuscript.table.Table;
import rnrconfig.IconMappings;
import rnrcore.eng;
import rnrcore.loc;

public class ChampionShipCallendar
implements IOrgTab {
    private static final String TABLE = "TABLEGROUP - CHAMPIONSHIP - CALENDAR - 4 146";
    private static final String TABLE_LINE = "Tablegroup - ELEMENTS - CHAMPIONSHIP - CALENDAR";
    private static final String TABLE_RANGER = "Tableranger - CHAMPIONSHIP - CALENDAR - list";
    private static final String[] TABLE_ELEMENTS = new String[]{"THE RACE LEAGUE - BACK - 01", "THE RACE LEAGUE - BACK - 02", "THE RACE LEAGUE - BACK - 03", "THE RACE LEAGUE - BACK - 04", "THE RACE LEAGUE - BACK - 05", "THE RACE LEAGUE - SYMBOL - 01", "THE RACE LEAGUE - SYMBOL - 02", "THE RACE LEAGUE - SYMBOL - 03", "THE RACE LEAGUE - SYMBOL - 04", "THE RACE LEAGUE - SYMBOL - 05", "THE RACE LEAGUE - TEXT", "THE RACE LOGOTYPE", "THE RACE LOGOTYPE - TEXT", "RaceStage - SYMBOL", "RaceStage - TEXT", "THE RACE - START/FINISH", "Rating - League01 - VALUE", "Rating - League02 - VALUE", "Rating - League03 - VALUE", "Rating - League04 - VALUE", "Rating - League05 - VALUE"};
    public Vector<TableInfo> in_lines = new Vector();
    TableImplement table = null;
    private ChampionShipPane parent = null;
    private long _menu = 0L;

    public ChampionShipCallendar(long _menu, ChampionShipPane parent) {
        Object obj;
        this._menu = _menu;
        this.parent = parent;
        this.table = new TableImplement(_menu);
        long control = menues.FindFieldInMenu(_menu, "CALL COMMUNICATOR HELP - CHAMPIONSHIP - CALENDAR");
        if (control != 0L && (obj = menues.ConvertMenuFields(control)) != null) {
            menues.SetScriptOnControl(_menu, obj, this, "ShowHelp", 4L);
        }
    }

    public void ShowHelp(long _menu, MENUsimplebutton_field button) {
        if (this.parent != null) {
            this.parent.ShowTabHelp(1);
        }
    }

    public void afterInit() {
        this.table.afterInit();
    }

    public void exitMenu() {
        this.table.deinit();
    }

    public void enterFocus() {
        this.table.updateTable();
    }

    public void leaveFocus() {
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    class TableImplement
    implements ISetupLine {
        private best_table_data TABLE_DATA = new best_table_data();
        private long _menu = 0L;
        Table table;

        TableImplement(long menu) {
            this._menu = menu;
            this.table = new Table(this._menu, ChampionShipCallendar.TABLE, ChampionShipCallendar.TABLE_RANGER);
            this.table.setSelectionMode(0);
            this.table.fillWithLines("..\\data\\config\\menu\\menu_com.xml", ChampionShipCallendar.TABLE_LINE, TABLE_ELEMENTS);
            this.table.takeSetuperForAllLines(this);
            this.reciveTableData();
            this.build_tree_data();
        }

        private void reciveTableData() {
            this.TABLE_DATA.all_lines.clear();
            JavaEvents.SendEvent(71, 23, ChampionShipCallendar.this.in_lines);
            for (int i = 0; i < Math.min(ChampionShipCallendar.this.in_lines.size(), 10); ++i) {
                this.TABLE_DATA.all_lines.add(ChampionShipCallendar.this.in_lines.elementAt(i));
            }
            this.buildvoidcells();
        }

        @Override
        public void SetupLineInTable(MENUEditBox obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        }

        @Override
        public void SetupLineInTable(MENU_ranger obj, Cmenu_TTI table_node) {
        }

        private Cmenu_TTI convertTableData() {
            Cmenu_TTI root = new Cmenu_TTI();
            for (int i = 0; i < this.TABLE_DATA.all_lines.size(); ++i) {
                Cmenu_TTI ch = new Cmenu_TTI();
                ch.toshow = true;
                ch.ontop = i == 0;
                ch.item = this.TABLE_DATA.all_lines.get(i);
                root.children.add(ch);
            }
            return root;
        }

        private void build_tree_data() {
            this.table.reciveTreeData(this.convertTableData());
        }

        private void buildvoidcells() {
            block4: {
                block3: {
                    if (this.TABLE_DATA.all_lines.size() >= this.table.getNumRows()) break block3;
                    int dif = this.table.getNumRows() - this.TABLE_DATA.all_lines.size();
                    for (int i = 0; i < dif; ++i) {
                        TableInfo data = new TableInfo();
                        data.wheather_show = false;
                        this.TABLE_DATA.all_lines.add(data);
                    }
                    break block4;
                }
                int count_good_data = 0;
                Iterator<TableInfo> iter = this.TABLE_DATA.all_lines.iterator();
                while (iter.hasNext() && iter.next().wheather_show) {
                    ++count_good_data;
                }
                if (count_good_data < this.table.getNumRows() || count_good_data >= this.TABLE_DATA.all_lines.size()) break block4;
                for (int i = this.TABLE_DATA.all_lines.size() - 1; i >= count_good_data; --i) {
                    this.TABLE_DATA.all_lines.remove(i);
                }
            }
        }

        public void deinit() {
            this.table.deinit();
        }

        public void updateTable() {
            this.reciveTableData();
            this.build_tree_data();
            this.table.refresh();
        }

        public void selectLineEvent(Table table, int line) {
        }

        public void selectMultipleLinesEvent(Table table, ArrayList<Cmenu_TTI> lines) {
        }

        private void make_sync_group() {
            int i;
            Object contrls = null;
            contrls = new long[TABLE_ELEMENTS.length][];
            for (i = 0; i < ((long[][])contrls).length; ++i) {
                contrls[i] = this.table.getLineStatistics_controls(TABLE_ELEMENTS[i]);
            }
            for (i = 0; i < contrls[0].length; ++i) {
                for (int j = 0; j < ((long[][])contrls).length; ++j) {
                    menues.SetSyncControlActive(this._menu, i, contrls[j][i]);
                }
            }
        }

        public void afterInit() {
            this.table.afterInit();
            this.make_sync_group();
        }

        @Override
        public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
            TableInfo line = (TableInfo)table_node.item;
            if (!line.wheather_show) {
                menues.SetShowField(obj.nativePointer, false);
                return;
            }
            int control = this.table.getMarkedPosition(obj.nativePointer);
            switch (control) {
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    menues.SetShowField(obj.nativePointer, line.leagueId == control);
                    break;
                }
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: {
                    menues.SetShowField(obj.nativePointer, line.leagueId == control - 5);
                    break;
                }
                case 10: {
                    switch (line.leagueId) {
                        case 0: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - MONSTER CUP");
                            break;
                        }
                        case 1: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - CHAMPION LEAGUE");
                            break;
                        }
                        case 2: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - PREMIERE LEAGUE");
                            break;
                        }
                        case 3: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - FIRST LEAGUE");
                            break;
                        }
                        case 4: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - SECOND LEAGUE");
                            break;
                        }
                        default: {
                            obj.text = loc.getMENUString("common\\BigRacewLeague - SECOND LEAGUE");
                        }
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 11: {
                    IconMappings.remapRaceLogos(line.raceName, obj.nativePointer);
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 12: {
                    obj.text = loc.getBigraceShortName(line.raceName);
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 13: {
                    switch (line.stageId) {
                        case 0: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                            break;
                        }
                        case 1: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE II - SYMBOL");
                            break;
                        }
                        case 2: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE III - SYMBOL");
                            break;
                        }
                        default: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE I - SYMBOL");
                        }
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 14: {
                    switch (line.stageId) {
                        case 0: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT - CaseSmall");
                            break;
                        }
                        case 1: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE II - TEXT - CaseSmall");
                            break;
                        }
                        case 2: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE III - TEXT - CaseSmall");
                            break;
                        }
                        default: {
                            obj.text = loc.getMENUString("common\\BigRaceStage - STAGE I - TEXT - CaseSmall");
                        }
                    }
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 15: {
                    KeyPair[] macro1 = new KeyPair[]{new KeyPair("START_CITY", line.start), new KeyPair("FINISH_CITY", line.finish)};
                    MacroKit.ApplyToTextfield(obj, macro1);
                    menues.SetShowField(obj.nativePointer, true);
                    break;
                }
                case 16: 
                case 17: 
                case 18: 
                case 19: 
                case 20: {
                    obj.text = Converts.ConvertRating(eng.visualRating(line.rating_prize));
                    menues.SetShowField(obj.nativePointer, eng.visualLeague() == control - 16);
                }
            }
            menues.UpdateMenuField(obj);
            menues.SetBlindess(obj.nativePointer, false);
            menues.SetIgnoreEvents(obj.nativePointer, false);
        }
    }

    static class best_table_data {
        Vector<TableInfo> all_lines = new Vector();

        best_table_data() {
        }
    }

    public static class TableInfo {
        String raceName;
        int leagueId;
        int stageId;
        double rating_prize;
        String start;
        String finish;
        boolean wheather_show = true;
    }
}

