/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import menu.BaseMenu;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.menucreation;
import menu.menues;
import menuscript.table.Table;
import menuscript.tablewrapper.TableLine;
import menuscript.tablewrapper.TableWrapped;
import rnrcore.loc;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class HelpMenu
extends BaseMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\specmenu.xml";
    private static final String GROUP = "HELP";
    private static final String HELP_TABLE = "TABLEGROUP HELP - 27 36";
    private static final String HELP_TABLE_RANGER = "Tableranger - HELP";
    private static final String HELP_LINE = "Tablegroup - HELP";
    private static final String[] HELP_LINE_ELEMENTS = new String[]{"Key Block TITLE", "Key - BACK PICTURE", "Key", "Key VALUE - BACK PICTURE", "Key VALUE"};
    private static final int HELP_TITLE = 0;
    private static final int HELP_KEY_PICTURE = 1;
    private static final int HELP_KEY = 2;
    private static final int HELP_KEY_VALUE_PICTURE = 3;
    private static final int HELP_KEY_VALUE = 4;
    ArrayList<HelpLine> helpLines = new ArrayList();
    Vector<KeyPair> macro = null;
    private static final String FILENAME = "..\\data\\config\\menu\\helpcontent.xml";
    private static final String ROOT = "group";
    private static final String NAME = "loc_name";
    private static final String LINE = "line";
    private static final String KEY = "key";
    private static final String KEY_VALUE = "loc_key_value";
    static long _menu = 0L;
    helpTable table = null;

    void parseLine(Node node) {
        HelpLine line = new HelpLine();
        line.isBlockTitle = false;
        line.key = node.getAttribute(KEY);
        line.key_value = node.getAttribute(KEY_VALUE);
        if (line.key == null) {
            line.key = "UNKNOWN";
        }
        if (line.key_value == null) {
            line.key_value = "UNKNOWN";
        }
        this.helpLines.add(line);
    }

    void parseBlock(Node node) {
        HelpLine group = new HelpLine();
        group.isBlockTitle = true;
        group.block_title = node.getAttribute(NAME);
        if (group.block_title == null) {
            group.block_title = "UNKNOWN";
        }
        this.helpLines.add(group);
        NodeList line = node.getNamedChildren(LINE);
        if (line != null) {
            for (int i = 0; i < line.size(); ++i) {
                this.parseLine((Node)line.get(i));
            }
        }
    }

    void FillHelp() {
        NodeList block;
        this.helpLines = new ArrayList();
        Node top = XmlUtils.parse(FILENAME);
        if (top != null && (block = top.getNamedChildren(ROOT)) != null) {
            for (int i = 0; i < block.size(); ++i) {
                this.parseBlock((Node)block.get(i));
            }
        }
        for (HelpLine line : this.helpLines) {
            if (line.isBlockTitle) {
                line.block_title = loc.getMENUString(line.block_title);
                continue;
            }
            KeyPair[] _macro = new KeyPair[this.macro.size()];
            int i = 0;
            Iterator<KeyPair> i$ = this.macro.iterator();
            while (i$.hasNext()) {
                KeyPair key;
                _macro[i] = key = i$.next();
                ++i;
            }
            line.key = MacroKit.Parse(loc.getMENUString(line.key), _macro);
            line.key_value = loc.getMENUString(line.key_value);
        }
    }

    ArrayList<HelpLine> reciveHelp() {
        return this.helpLines;
    }

    @Override
    public void restartMenu(long _menu) {
    }

    @Override
    public void InitMenu(long __menu) {
        _menu = __menu;
        menues.InitXml(_menu, XML, GROUP);
        JavaEvents.SendEvent(9, 0, this);
        this.FillHelp();
        this.table = new helpTable(_menu);
    }

    @Override
    public void AfterInitMenu(long _menu) {
        Object field;
        menues.WindowSet_ShowCursor(_menu, true);
        menues.SetStopWorld(_menu, true);
        long buttonOK = menues.FindFieldInMenu(_menu, "BUTTON - OK");
        if (buttonOK != 0L && (field = menues.ConvertMenuFields(buttonOK)) != null) {
            menues.SetScriptOnControl(_menu, field, this, "OnOk", 4L);
            menues.setfocuscontrolonmenu(_menu, buttonOK);
        }
        this.table.afterInit();
        menues.setShowMenu(_menu, true);
    }

    public void OnOk(long _menu, MENUsimplebutton_field button) {
        menues.CallMenuCallBack_ExitMenu(_menu);
    }

    @Override
    public void exitMenu(long __menu) {
        this.table.deinit();
        _menu = 0L;
    }

    public static long CreateMenu() {
        if (_menu == 0L) {
            return menues.createSimpleMenu(new HelpMenu(), 240000.0, "ESC", 1600, 1200, 1600, 1200, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
        }
        menues.CallMenuCallBack_ExitMenu(_menu);
        return 0L;
    }

    @Override
    public String getMenuId() {
        return "helpMENU";
    }

    class helpTable
    extends TableWrapped {
        helpTable(long _menu) {
            super(_menu, 0, false, HelpMenu.XML, HelpMenu.HELP_TABLE, HelpMenu.HELP_TABLE_RANGER, HelpMenu.HELP_LINE, HELP_LINE_ELEMENTS, null, null);
        }

        public void updateTable() {
            super.updateTable();
        }

        public void enterFocus(Table table) {
        }

        public void leaveFocus(Table table) {
        }

        protected void deinit() {
            this.table.deinit();
        }

        protected void reciveTableData() {
            this.TABLE_DATA.all_lines.addAll(HelpMenu.this.reciveHelp());
        }

        public void SetupLineInTable(long button, int position, TableLine table_node) {
            HelpLine line = (HelpLine)table_node;
            switch (position) {
                case 0: {
                    if (line.isBlockTitle) {
                        menues.SetShowField(button, true);
                        menues.SetFieldText(button, line.block_title);
                        break;
                    }
                    menues.SetShowField(button, false);
                    break;
                }
                case 1: {
                    if (line.isBlockTitle) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    break;
                }
                case 2: {
                    if (line.isBlockTitle) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.key);
                    break;
                }
                case 3: {
                    if (line.isBlockTitle) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    break;
                }
                case 4: {
                    if (line.isBlockTitle) {
                        menues.SetShowField(button, false);
                        break;
                    }
                    menues.SetShowField(button, true);
                    menues.SetFieldText(button, line.key_value);
                }
            }
            menues.UpdateMenuField(menues.ConvertMenuFields(button));
        }

        public void updateSelectedInfo(TableLine linedata) {
        }

        public void onSort(long _menu, MENUsimplebutton_field button) {
        }
    }

    static class HelpLine
    extends TableLine {
        String key;
        String key_value;
        String block_title;
        boolean isBlockTitle;

        HelpLine() {
        }
    }
}

