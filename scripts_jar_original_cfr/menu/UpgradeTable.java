/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.Common;
import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.Table;
import menu.TableCallbacks;
import menu.TableNode;
import menu.TextScroller;
import menu.UpgradeItem;
import menu.menues;
import menuscript.Converts;
import menuscript.STOmenues;
import rnrcore.loc;

public class UpgradeTable
implements TableCallbacks,
Table.TableAnimation {
    public static final int OPENGROUP = 0;
    public static final int TEXT = 1;
    public static final int TEXTGLOW = 2;
    public static final int UPGRADE_PRICE = 4;
    public static final int UPGRADE_ALL = 5;
    public static final int UPGRADE_ALL_GRAY = 6;
    public static final int DOWNGRADE_ALL = 7;
    public static final int DOWNGRADE_ALL_GRAY = 8;
    public static final int DOWNGRADE_PRICE = 9;
    public static final int CHECK = 10;
    public static final int CHECK_GRAY = 11;
    public static final int INDICATOR_RED = 12;
    public static final int INDICATOR_GREEN = 13;
    public static final int INDICATOR_GRAY = 14;
    public static final int BORDER = 15;
    public static final int SIZE = 19;
    public static double OPEN_DUR = 0.5;
    static final boolean OPEN = true;
    static final boolean CLOSED = false;
    Table m_Table;
    MENU_ranger m_Ranger;
    Vector[] m_aNames;
    Common common;
    UpgradeNode root = null;
    boolean m_bAnimating = false;
    int m_iAnimLine = -1;
    boolean[][] m_aArrowStates;
    boolean m_bGlowEnable = false;
    STOmenues father = null;
    int m_iSelectedLine = 0;
    int m_iWasSelectedLine = 0;
    boolean bIsGreenIndicatorSizeStored = false;
    boolean bIsGrayIndicatorSizeStored = false;
    boolean bIsRedIndicatorSizeStored = false;
    float indicator_red_original_size = 0.0f;
    float indicator_green_original_size = 0.0f;
    float indicator_gray_original_size = 0.0f;
    CountingTraverse m_Counting = new CountingTraverse();
    int out_id = 0;
    public String detailed_name = null;
    public long id_details_picture = 0L;
    TextScroller scroller = null;
    public long id_details_text = 0L;
    public long id_details_text_scroller = 0L;
    String store_marked_for_purchase = null;
    String store_marked_for_return = null;
    String store_total = null;
    long id_marked_for_purchase = 0L;
    long id_marked_for_return = 0L;
    long id_total = 0L;
    long id_total_title = 0L;
    public int total_upgrade_price = 0;

    public UpgradeTable(Common common, String name, STOmenues _father) {
        this.m_Table = new Table(common.GetMenu(), name);
        this.m_aNames = new Vector[19];
        for (int i = 0; i < 19; ++i) {
            this.m_aNames[i] = new Vector(4);
        }
        this.common = common;
        this.father = _father;
    }

    int Encode(int type, int level) {
        return level * 1000 + type;
    }

    int DecodeLevel(int id) {
        return id / 1000;
    }

    int DecodeType(int id) {
        return id % 1000;
    }

    void AddName(int type, int level, String name) {
        Vector v = this.m_aNames[type];
        if (v.size() <= level) {
            v.setSize(level + 1);
        }
        v.set(level, name);
    }

    public void AddControl(int type, int level, String name) {
        this.AddName(type, level, name);
        switch (type) {
            case 0: {
                this.m_Table.AddSimpleButton(name, this.Encode(type, level));
                break;
            }
            case 1: {
                this.m_Table.AddRadioButton(name, this.Encode(type, level));
                break;
            }
            case 2: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                this.m_bGlowEnable = true;
                break;
            }
            case 4: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 5: {
                this.m_Table.AddSimpleButton(name, this.Encode(type, level));
                break;
            }
            case 6: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 7: {
                this.m_Table.AddSimpleButton(name, this.Encode(type, level));
                break;
            }
            case 8: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 9: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 10: {
                this.m_Table.AddRadioButton(name, this.Encode(type, level));
                break;
            }
            case 11: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 13: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 12: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 14: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 15: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
            }
        }
    }

    public void AttachRanger(MENU_ranger ranger) {
        this.m_Table.AttachRanger(ranger);
        this.m_Ranger = ranger;
    }

    boolean HasLevels(int level, int type) {
        switch (type) {
            case 0: {
                return true;
            }
            case 1: {
                return true;
            }
            case 2: {
                return true;
            }
            case 4: {
                return false;
            }
            case 5: {
                return false;
            }
            case 6: {
                return false;
            }
            case 7: {
                return false;
            }
            case 8: {
                return false;
            }
            case 9: {
                return false;
            }
            case 10: {
                return false;
            }
            case 11: {
                return false;
            }
            case 13: {
                return true;
            }
            case 12: {
                return true;
            }
            case 14: {
                return true;
            }
            case 15: {
                return true;
            }
        }
        return false;
    }

    public void SetupLineInTable(TableNode node, MENUText_field text) {
        int level = this.DecodeLevel(text.userid);
        int type = this.DecodeType(text.userid);
        if (level != node.depth && this.HasLevels(level, type) || node.item == null) {
            menues.SetShowField(text.nativePointer, false);
            return;
        }
        menues.SetShowField(text.nativePointer, true);
        UpgradeItem item = (UpgradeItem)node.item;
        switch (type) {
            case 2: {
                text.text = "" + item.name;
                break;
            }
            case 4: {
                text.text = "$" + item.upgrade_price;
                break;
            }
            case 9: {
                text.text = "$" + item.downgrade_price;
                break;
            }
            case 8: {
                if (!item.can_downgrade_all && node.self.children.size() != 0) break;
                menues.SetShowField(text.nativePointer, false);
                break;
            }
            case 6: {
                if (!item.can_upgrade_all && node.self.children.size() != 0) break;
                menues.SetShowField(text.nativePointer, false);
                break;
            }
            case 11: {
                if (item.checked && item.need_one && node.self.children.size() == 0) {
                    menues.SetShowField(text.nativePointer, true);
                } else {
                    menues.SetShowField(text.nativePointer, false);
                }
                menues.SetIgnoreEvents(text.nativePointer, true);
                menues.SetBlindess(text.nativePointer, true);
                break;
            }
            case 13: {
                if (!this.bIsGreenIndicatorSizeStored) {
                    this.indicator_green_original_size = text.lenx;
                    this.bIsGreenIndicatorSizeStored = true;
                }
                if (item.farshness > item.original_farshness) {
                    menues.SetShowField(text.nativePointer, true);
                } else {
                    menues.SetShowField(text.nativePointer, false);
                }
                text.lenx = (int)(this.indicator_green_original_size * item.farshness);
                text.lenx = text.lenx <= 0 ? 1 : text.lenx;
                menues.UpdateField(text);
                break;
            }
            case 12: {
                if (!this.bIsRedIndicatorSizeStored) {
                    this.indicator_red_original_size = text.lenx;
                    this.bIsRedIndicatorSizeStored = true;
                }
                if (item.farshness < item.original_farshness) {
                    menues.SetShowField(text.nativePointer, true);
                } else {
                    menues.SetShowField(text.nativePointer, false);
                }
                text.lenx = (int)(this.indicator_red_original_size * item.original_farshness);
                text.lenx = text.lenx <= 0 ? 1 : text.lenx;
                menues.UpdateField(text);
                break;
            }
            case 14: {
                if (!this.bIsGrayIndicatorSizeStored) {
                    this.indicator_gray_original_size = text.lenx;
                    this.bIsGrayIndicatorSizeStored = true;
                }
                text.lenx = item.farshness < item.original_farshness ? (int)(this.indicator_gray_original_size * item.farshness) : (int)(this.indicator_gray_original_size * item.original_farshness);
                text.lenx = text.lenx <= 0 ? 1 : text.lenx;
                menues.UpdateField(text);
            }
        }
    }

    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
        int level = this.DecodeLevel(button.userid);
        int type = this.DecodeType(button.userid);
        if (level != node.depth && this.HasLevels(level, type) || node.item == null) {
            menues.SetShowField(button.nativePointer, false);
            return;
        }
        menues.SetShowField(button.nativePointer, true);
        UpgradeItem item = (UpgradeItem)node.item;
        switch (type) {
            case 0: {
                if (node.self.children.size() != 0) break;
                menues.SetShowField(button.nativePointer, false);
                break;
            }
            case 7: {
                if (item.can_downgrade_all && node.self.children.size() != 0) break;
                menues.SetShowField(button.nativePointer, false);
                break;
            }
            case 5: {
                if (item.can_upgrade_all && node.self.children.size() != 0) break;
                menues.SetShowField(button.nativePointer, false);
            }
        }
    }

    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        int level = this.DecodeLevel(radio.userid);
        int type = this.DecodeType(radio.userid);
        if (level != node.depth && this.HasLevels(level, type) || node.item == null) {
            menues.SetShowField(radio.nativePointer, false);
            return;
        }
        menues.SetShowField(radio.nativePointer, true);
        UpgradeItem item = (UpgradeItem)node.item;
        switch (type) {
            case 10: {
                if (item.checked && item.need_one && node.self.children.size() == 0 || node.self.children.size() != 0) {
                    menues.SetShowField(radio.nativePointer, false);
                    break;
                }
                menues.SetFieldState(radio.nativePointer, item.checked ? 1 : 0);
                break;
            }
            case 1: {
                radio.text = "" + item.name;
                menues.SetFieldState(radio.nativePointer, this.m_iSelectedLine == node.line ? 1 : 0);
            }
        }
    }

    public void StartGlow() {
        this.m_Table.Traverse(new StartGlowTraverse(), 0);
    }

    void OnMouseOver(TableNode node, long group) {
        if (!this.m_bGlowEnable) {
            return;
        }
        int type = this.DecodeType(this.m_Table.GetLastID());
        int level = this.DecodeLevel(this.m_Table.GetLastID());
        if (level != node.depth || type != 1) {
            return;
        }
        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), group, (String)this.m_aNames[1].get(level), 0.5, 1.0, "type1");
        menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), group, (String)this.m_aNames[2].get(level), 0.5, 1.0, "type2");
    }

    void OnButtonPress(TableNode node, long group) {
        int type = this.DecodeType(this.m_Table.GetLastID());
        if (type == 0) {
            this.m_iWasSelectedLine = this.m_iSelectedLine;
            this.m_iSelectedLine = node.line;
            if (!this.m_bAnimating) {
                this.m_bAnimating = true;
                this.m_iAnimLine = node.line;
                menues.SetIgnoreEvents(this.m_Ranger.nativePointer, true);
                this.RotateButtonTree(node, !node.self.showCH, 1.0 / OPEN_DUR);
                this.m_aArrowStates[this.m_Table.GetScreenLineByLogical((int)node.line)][node.depth] = !node.self.showCH;
                this.m_Counting.Clear();
                this.m_Table.Traverse(node, this.m_Counting, 0);
                int numch = this.m_Counting.GetCount() - 1;
                this.m_Table.ShowHideSubtree(node, 1000.0 * OPEN_DUR / (double)numch, this.m_bGlowEnable ? this : null);
                if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    UpgradeItem item = (UpgradeItem)node.item;
                    this.out_id = item.id;
                    JavaEvents.SendEvent(67, 4, this);
                }
            } else {
                if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    UpgradeItem item = (UpgradeItem)node.item;
                    this.out_id = item.id;
                    JavaEvents.SendEvent(67, 4, this);
                }
                this.m_Table.RedrawTable();
            }
        }
        if (type == 5) {
            UpgradeItem item = (UpgradeItem)node.item;
            this.out_id = item.id;
            JavaEvents.SendEvent(67, 2, this);
            this.m_Table.RedrawTable();
        }
        if (type == 7) {
            UpgradeItem item = (UpgradeItem)node.item;
            this.out_id = item.id;
            JavaEvents.SendEvent(67, 3, this);
            this.m_Table.RedrawTable();
        }
    }

    void OnRadioPress(TableNode node, long group) {
        int type = this.DecodeType(this.m_Table.GetLastID());
        if (type == 10) {
            UpgradeItem item = (UpgradeItem)node.item;
            this.out_id = item.id;
            JavaEvents.SendEvent(67, 1, this);
            this.m_Table.RedrawTable();
        }
        if (type == 1) {
            this.m_iWasSelectedLine = this.m_iSelectedLine;
            this.m_iSelectedLine = node.line;
            if (this.m_iWasSelectedLine != this.m_iSelectedLine) {
                if (!this.m_bAnimating && node.self.children.size() != 0 && !node.self.showCH) {
                    this.m_bAnimating = true;
                    this.m_iAnimLine = node.line;
                    menues.SetIgnoreEvents(this.m_Ranger.nativePointer, true);
                    this.RotateButtonTree(node, !node.self.showCH, 1.0 / OPEN_DUR);
                    this.m_aArrowStates[this.m_Table.GetScreenLineByLogical((int)node.line)][node.depth] = !node.self.showCH;
                    this.m_Counting.Clear();
                    this.m_Table.Traverse(node, this.m_Counting, 0);
                    int numch = this.m_Counting.GetCount() - 1;
                    this.m_Table.ShowHideSubtree(node, 1000.0 * OPEN_DUR / (double)numch, this.m_bGlowEnable ? this : null);
                } else {
                    this.m_Table.RedrawTable();
                }
                UpgradeItem item = (UpgradeItem)node.item;
                this.out_id = item.id;
                JavaEvents.SendEvent(67, 4, this);
            } else {
                this.m_Table.RedrawTable();
            }
        }
    }

    public void OnEvent(long event2, TableNode node, long group, long _menu) {
        switch ((int)event2) {
            case 4: {
                this.OnButtonPress(node, group);
                break;
            }
            case 2: {
                this.OnRadioPress(node, group);
                break;
            }
            case 3: {
                this.OnMouseOver(node, group);
            }
        }
    }

    void RotateButtonTree(TableNode node, boolean direction, double velocity) {
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), false, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), true, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), true, 1, velocity, (direction ? 1 : -1) * 90);
    }

    public void OnNodeChange(TableNode node, boolean direction) {
        if (direction) {
            int level = node.depth;
            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[1].get(level), 0.5, 1.0, "type1");
            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[2].get(level), 0.5, 1.0, "type2");
        }
    }

    public void OnFinishAnimation() {
        this.m_bAnimating = false;
        this.m_iAnimLine = -1;
        menues.SetIgnoreEvents(this.m_Ranger.nativePointer, false);
    }

    public void Setup(int vertsize, int linenum, String xmlfile, String controlgroup, String parent) {
        this.root = new UpgradeNode();
        JavaEvents.SendEvent(67, 0, this);
        this.FillUpgradeTable();
        this.out_id = this.root.item.id;
        JavaEvents.SendEvent(67, 4, this);
        this.m_Table.AddEvent(4);
        this.m_Table.AddEvent(2);
        this.m_Table.AddEvent(3);
        this.m_Table.Setup(vertsize, linenum, xmlfile, controlgroup, parent, this, 1);
        this.m_aArrowStates = new boolean[linenum][];
        for (int i = 0; i < this.m_aArrowStates.length; ++i) {
            this.m_aArrowStates[i] = new boolean[4];
            for (int i1 = 0; i1 < 4; ++i1) {
                this.m_aArrowStates[i][i1] = true;
            }
        }
    }

    public void DeInit() {
        if (this.m_Table != null) {
            this.m_Table.DeInit();
        }
        if (this.scroller != null) {
            this.scroller.Deinit();
        }
    }

    public void AfteInitMenu() {
        int depth;
        for (depth = 0; depth < 4; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), false, 0, 100.0, 0.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 0, 100.0, 0.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 1, 100.0, 0.0);
        }
        this.m_Table.Traverse(new InitAnimTraverse());
        if (this.m_bGlowEnable) {
            this.StartGlow();
        }
        this.m_Table.RedrawTable();
        this.PrepareSummary(this.common.GetMenu());
        this.PrepareDetailedText(this.common.GetMenu());
        for (depth = 0; depth < 4; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), false, 0, 1000.0, 90.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 0, 1000.0, 90.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 1, 1000.0, 90.0);
        }
    }

    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    private void fillUpgradeTable(TableNode pap, UpgradeNode node) {
        TableNode res = this.m_Table.AddItem(pap, node.item, true);
        for (int i = 0; i < node.childs.size(); ++i) {
            this.fillUpgradeTable(res, (UpgradeNode)node.childs.get(i));
        }
    }

    public void FillUpgradeTable() {
        if (this.root != null) {
            this.fillUpgradeTable(null, this.root);
        }
    }

    void PrepareDetailedText(long _menu) {
        this.id_details_text = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - DETAILS TEXT");
        this.id_details_picture = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - UpgradePIC");
        this.id_details_text_scroller = menues.FindFieldInMenu(_menu, "UA - VehicleUpgradeMONITOR - tableranger");
        this.SetDetailedString();
        this.ShowDetailedPicture(false);
    }

    public void ShowDetailedPicture(boolean bShow) {
        menues.SetShowField(this.id_details_picture, bShow);
    }

    public void SetDetailedString() {
        if (this.detailed_name != null) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.id_details_text_scroller);
            MENUText_field text = (MENUText_field)menues.ConvertMenuFields(this.id_details_text);
            if (ranger != null && text != null) {
                if (this.id_details_text != 0L) {
                    menues.SetShowField(this.id_details_text, true);
                }
                text.text = this.detailed_name;
                menues.UpdateField(text);
                int texh = menues.GetTextLineHeight(text.nativePointer);
                int startbase = menues.GetBaseLine(text.nativePointer);
                int linescreen = Converts.HeightToLines(text.leny, startbase, texh);
                int linecounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, this.detailed_name), startbase, texh);
                if (this.scroller != null) {
                    this.scroller.Deinit();
                }
                this.scroller = new TextScroller(this.common, ranger, linecounter, linescreen, texh, startbase, false, "UA - VehicleUpgradeMONITOR - DETAILS TEXT");
                this.scroller.AddTextControl(text);
            }
        } else {
            if (this.id_details_text != 0L) {
                menues.SetShowField(this.id_details_text, false);
            }
            if (this.scroller != null) {
                this.scroller.Deinit();
            }
            this.scroller = null;
        }
    }

    public void PrepareSummary(long _menu) {
        this.id_marked_for_purchase = menues.FindFieldInMenu(_menu, "Marked for purchase - VALUE");
        this.id_marked_for_return = menues.FindFieldInMenu(_menu, "Marked for return - VALUE");
        this.id_total = menues.FindFieldInMenu(_menu, "Total/Return - VALUE");
        this.id_total_title = menues.FindFieldInMenu(_menu, "Total/Return - TITLE");
        if (this.id_marked_for_purchase != 0L) {
            this.store_marked_for_purchase = menues.GetFieldText(this.id_marked_for_purchase);
        }
        if (this.id_marked_for_return != 0L) {
            this.store_marked_for_return = menues.GetFieldText(this.id_marked_for_return);
        }
        if (this.id_total != 0L) {
            this.store_total = menues.GetFieldText(this.id_total);
        }
        this.SetSummary(0, 0, 0);
    }

    public void SetSummary(int to_install, int to_uninstall, int summary) {
        KeyPair[] pairs;
        if (this.id_marked_for_purchase != 0L) {
            pairs = new KeyPair[]{new KeyPair("VALUE", "" + to_install)};
            menues.SetFieldText(this.id_marked_for_purchase, MacroKit.Parse(this.store_marked_for_purchase, pairs));
            if (menues.ConvertMenuFields(this.id_marked_for_purchase) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_marked_for_purchase));
            }
        }
        if (this.id_marked_for_return != 0L) {
            pairs = new KeyPair[]{new KeyPair("VALUE", "" + to_uninstall)};
            menues.SetFieldText(this.id_marked_for_return, MacroKit.Parse(this.store_marked_for_return, pairs));
            if (menues.ConvertMenuFields(this.id_marked_for_return) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_marked_for_return));
            }
        }
        if (this.id_total != 0L) {
            pairs = new KeyPair[]{new KeyPair("MONEY", Converts.ConvertNumeric(Math.abs(summary)))};
            menues.SetFieldText(this.id_total, MacroKit.Parse(this.store_total, pairs));
            if (menues.ConvertMenuFields(this.id_total) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_total));
            }
        }
        if (this.id_total_title != 0L) {
            menues.SetFieldText(this.id_total_title, summary >= 0 ? loc.getMENUString("common\\Total") : loc.getMENUString("menu_repair.xml\\MenuRepair UpgradeVEHICLE\\BACK ALL - UpgradeVEHICLE\\Total/Return - TITLE"));
            if (menues.ConvertMenuFields(this.id_total_title) != null) {
                menues.UpdateMenuField(menues.ConvertMenuFields(this.id_total_title));
            }
        }
        this.total_upgrade_price = summary;
        if (this.father != null) {
            this.father.UpdateAuth();
        }
    }

    public void OnApply() {
        JavaEvents.SendEvent(67, 5, this);
        this.m_Table.RedrawTable();
    }

    public void OnDiscard() {
        JavaEvents.SendEvent(67, 6, this);
        this.m_Table.RedrawTable();
    }

    class InitAnimTraverse
    implements Table.TableVisitor {
        InitAnimTraverse() {
        }

        public void VisitNode(TableNode node) {
            long gr;
            if (node.real_line == 0) {
                return;
            }
            if (node.real_line >= UpgradeTable.this.m_Table.GetLineNum()) {
                Long group = (Long)UpgradeTable.this.m_Table.GetFakeGroups().get(node.real_line - UpgradeTable.this.m_Table.GetLineNum());
                gr = group;
            } else {
                gr = UpgradeTable.this.m_Table.GetGroupByLine(node.real_line);
            }
            for (int depth = 0; depth < 4; ++depth) {
                String name = (String)UpgradeTable.this.m_aNames[0].get(depth);
                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(), UpgradeTable.this.m_Table.GetGroupByLine(0), name, false, 0, gr, false, 0);
                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(), UpgradeTable.this.m_Table.GetGroupByLine(0), name, true, 0, gr, true, 0);
                menues.CopyUVRotationOnGroupTree(UpgradeTable.this.common.GetMenu(), UpgradeTable.this.m_Table.GetGroupByLine(0), name, true, 1, gr, true, 1);
            }
            if (node.depth >= 1) {
                node.self.showCH = false;
            }
            menues.UpdateData(node.self);
        }
    }

    class CountingTraverse
    implements Table.TableVisitor {
        int count = 0;

        CountingTraverse() {
        }

        public void VisitNode(TableNode node) {
            ++this.count;
        }

        public int GetCount() {
            return this.count;
        }

        public void Clear() {
            this.count = 0;
        }
    }

    class StartGlowTraverse
    implements Table.TableVisitor {
        StartGlowTraverse() {
        }

        public void VisitNode(TableNode node) {
            if (node.line == -1) {
                return;
            }
            String textname = (String)UpgradeTable.this.m_aNames[1].get(node.depth);
            String glowname = (String)UpgradeTable.this.m_aNames[2].get(node.depth);
            menues.SetAlfaAnimationOnGroupTree(UpgradeTable.this.common.GetMenu(), node.group, textname, 0.5, 1.0, "type1");
            menues.SetAlfaAnimationOnGroupTree(UpgradeTable.this.common.GetMenu(), node.group, glowname, 0.5, 1.0, "type2");
        }
    }

    public static class UpgradeNode {
        UpgradeItem item;
        Vector childs = new Vector();

        public UpgradeNode(UpgradeItem _item) {
            this.item = _item;
        }

        public UpgradeNode() {
            this.item = new UpgradeItem();
        }

        public UpgradeNode AddChild(UpgradeItem _item) {
            UpgradeNode child = new UpgradeNode(_item);
            this.childs.add(child);
            return child;
        }

        public UpgradeNode GetChild(int i) {
            return (UpgradeNode)this.childs.elementAt(i);
        }

        public void AddChild(UpgradeNode ch) {
            this.childs.add(ch);
        }
    }
}

