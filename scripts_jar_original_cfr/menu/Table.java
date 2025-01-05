/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import menu.CInteger;
import menu.Cmenu_TTI;
import menu.Common;
import menu.IWheelEnabled;
import menu.MENUText_field;
import menu.MENUTruckview;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.TableCallbacks;
import menu.TableCmp;
import menu.TableNode;
import menu.TableSelect;
import menu.menues;
import rnrcore.Log;

public class Table
extends IWheelEnabled {
    public static final int TABLE_GETLASTID = 1;
    public static final int TABLE_SINGLECHECK = 2;
    public static final int TABLE_NONPERMSINGLECHECK = 4;
    public static final int TABLE_AUTOHIDEEMPTYLINES = 8;
    public static final int TABLE_HORIZONTAL = 16;
    public static final int TABLE_NOKEYSCROLL = 32;
    public static final int TRAVERSE_SHOWCH_ENABLE = 1;
    public static final int TRAVERSE_FAKE_ENABLE = 2;
    Vector m_Events;
    MENU_ranger m_ranger;
    Vector m_AnimList;
    boolean m_isopening;
    int m_iCounter;
    double m_delay;
    TableAnimation m_animcb;
    int m_iAnimationID = Common.GetID();
    TableNode m_animnode;
    TableCmp m_lastcomp;
    Vector m_items;
    public static final int CONTROL_TEXTFIELD = 0;
    public static final int CONTROL_SIMPLEBUTTON = 1;
    public static final int CONTROL_RADIOBUTTON = 2;
    public static final int CONTROL_TRUCKVIEW = 3;
    TableTree m_rootnode;
    long[] m_groups;
    int m_iLinenum;
    int m_iVisSize;
    int m_iFakeNum = 0;
    Vector m_FakeGroups = new Vector();
    Cmenu_TTI m_root;
    Cmenu_TTI m_top;
    private boolean buzzy_OnRangerScroll = false;
    int m_iLastUserID = -1;
    TableCallbacks m_callbacks;
    TableNode m_singlechecked;
    boolean m_bAutoHideEmpty;
    boolean m_bSingleChecked;
    boolean m_bPermSingleChecked;
    boolean m_bGetLastID;
    boolean m_bSelect;
    boolean m_bHorizontal;
    boolean m_bKeyscroll;
    long m_iLastControl;
    TableSelect m_SelectCbs;
    TableNode m_SelectNode;
    long m_iSelectedControl;
    int m_iSelectedID;
    static HashMap s_GlobalEntries;
    static int s_maxid;
    int m_iCurrentSortType;
    int m_iCurrentTopLine;
    boolean m_bCurrentOrder;
    long m_menu;
    long m_table;

    public void cbEnterFocus() {
    }

    public void cbLeaveFocus() {
    }

    public void ControlsCtrlAPressed() {
    }

    public Table(long menu, String name) {
        this.m_menu = menu;
        this.m_items = new Vector();
        this.m_Events = new Vector();
        this.SetupRootNode();
    }

    public void DeInit() {
        this.wheelDeinit();
        this.m_rootnode = null;
        this.m_root = null;
        this.m_top = null;
    }

    public void SetupRootNode() {
        this.m_rootnode = new TableTree();
        this.m_rootnode.showch = true;
        this.m_rootnode.item = new TableNode();
        this.m_rootnode.item.buildtime = this.m_rootnode;
        this.m_rootnode.item.depth = -1;
        this.m_rootnode.item.item = null;
    }

    public void Setup(long vertsize, int linenum, String xmlfile, String xmlgroup, String parentname, TableCallbacks cbs, int flags) {
        Cmenu_TTI child;
        this.m_iCurrentSortType = -1;
        this.m_bCurrentOrder = false;
        this.m_iCurrentTopLine = 0;
        this.m_bSingleChecked = (flags & 2) != 0;
        this.m_bPermSingleChecked = (flags & 4) == 0;
        this.m_bGetLastID = (flags & 1) != 0;
        this.m_bAutoHideEmpty = (flags & 8) != 0;
        this.m_bHorizontal = (flags & 0x10) != 0;
        boolean bl = this.m_bKeyscroll = (flags & 0x20) == 0;
        if (this.m_bKeyscroll) {
            long parent = menues.FindFieldInMenu(this.m_menu, parentname);
            Object parento = menues.ConvertMenuFields(parent);
            menues.SetScriptOnControl(this.m_menu, parento, this, "OnKeyOutUp", 15L);
            menues.SetScriptOnControl(this.m_menu, parento, this, "OnKeyOutDown", 14L);
        }
        this.wheelInit(this.m_menu, parentname);
        this.m_callbacks = cbs;
        this.m_iLinenum = linenum;
        this.m_groups = new long[linenum];
        this.m_table = menues.CreateTable(this.m_menu);
        for (int line = 0; line < linenum; ++line) {
            long group;
            this.m_groups[line] = group = menues.CreateGroup(this.m_menu);
            menues.AddGroupInTable(this.m_menu, this.m_table, line, group);
            menues.ScriptObjSyncGroup(this.m_menu, group, this, "SetupLineInTable");
            this.SetupCallbacks(group);
        }
        for (int i = 0; i < this.m_items.size(); ++i) {
            for (int line = 0; line < linenum; ++line) {
                long group = this.m_groups[line];
                ItemInfo item = (ItemInfo)this.m_items.get(i);
                long control = menues.InitXmlControl(this.m_menu, xmlfile, xmlgroup, item.name);
                item.realname = item.uniquenaming ? item.name + item.userid : item.name;
                Object obj = null;
                switch (item.type) {
                    case 0: {
                        MENUText_field field = menues.ConvertTextFields(control);
                        if (!this.m_bHorizontal) {
                            field.poy = (int)((long)field.poy + (long)line * vertsize);
                            field.pox += item.xshift;
                        } else {
                            field.pox = (int)((long)field.pox + (long)line * vertsize);
                            field.poy += item.xshift;
                        }
                        field.userid = item.userid;
                        field.nameID = item.realname;
                        field.parentName = parentname;
                        menues.UpdateField(field);
                        obj = field;
                        break;
                    }
                    case 1: {
                        MENUsimplebutton_field button = menues.ConvertSimpleButton(control);
                        if (!this.m_bHorizontal) {
                            button.poy = (int)((long)button.poy + (long)line * vertsize);
                            button.pox += item.xshift;
                        } else {
                            button.pox = (int)((long)button.pox + (long)line * vertsize);
                            button.poy += item.xshift;
                        }
                        button.userid = item.userid;
                        button.nameID = item.realname;
                        button.parentName = parentname;
                        menues.UpdateField(button);
                        obj = button;
                        break;
                    }
                    case 2: {
                        MENUbutton_field radio = menues.ConvertButton(control);
                        if (!this.m_bHorizontal) {
                            radio.poy = (int)((long)radio.poy + (long)line * vertsize);
                            radio.pox += item.xshift;
                        } else {
                            radio.pox = (int)((long)radio.pox + (long)line * vertsize);
                            radio.poy += item.xshift;
                        }
                        radio.userid = item.userid;
                        radio.nameID = item.realname;
                        radio.parentName = parentname;
                        menues.UpdateField(radio);
                        obj = radio;
                        break;
                    }
                    case 3: {
                        MENUTruckview truckview = (MENUTruckview)menues.ConvertMenuFields(control);
                        if (!this.m_bHorizontal) {
                            truckview.poy = (int)((long)truckview.poy + (long)line * vertsize);
                            truckview.pox += item.xshift;
                        } else {
                            truckview.pox = (int)((long)truckview.pox + (long)line * vertsize);
                            truckview.poy += item.xshift;
                        }
                        truckview.userid = item.userid;
                        truckview.nameID = item.realname;
                        truckview.parentName = parentname;
                        menues.UpdateMenuField(truckview);
                        obj = truckview;
                    }
                }
                menues.AddControlInGroup(this.m_menu, group, obj);
                menues.LinkGroupAndControl(this.m_menu, obj);
                menues.ChangableFieldOnGroup(this.m_menu, obj);
                if (item.type == 0) continue;
                menues.StoreControlState(this.m_menu, obj);
            }
        }
        menues.FillMajorDataTable_ScriptObject(this.m_menu, this.m_table, this, "FillTable");
        this.RecalcGroups();
        this.SetupRootNode();
        if (this.m_bSingleChecked && this.m_root.children.size() > 0 && this.m_bPermSingleChecked && (child = (Cmenu_TTI)this.m_root.children.get(0)) != null) {
            this.m_singlechecked = (TableNode)child.item;
            this.m_singlechecked.checked = true;
        }
    }

    public void RedrawItemLine(TableNode node) {
        menues.RedrawGroup(this.m_menu, node.group);
    }

    public void AttachSelectCbs(TableSelect cbs) {
        if (cbs == null) {
            this.m_bSelect = false;
            return;
        }
        this.m_bSelect = true;
        this.m_SelectCbs = cbs;
        this.m_iSelectedControl = 0L;
        this.m_SelectNode = null;
    }

    public void Deselect() {
        this.CheckDeselect();
        this.m_SelectNode = null;
    }

    public TableNode GetNodeByLine(int line) {
        if (line >= this.m_root.children.size() - this.m_iLinenum) {
            return null;
        }
        return (TableNode)((Cmenu_TTI)this.m_root.children.get((int)line)).item;
    }

    public void SyncLineStates() {
        for (int line = 0; line < this.m_iLinenum; ++line) {
            int groupid = Common.GetID();
            for (int i = 0; i < this.m_items.size(); ++i) {
                ItemInfo item = (ItemInfo)this.m_items.get(i);
                if (item.type == 0) continue;
                long control = menues.FindFieldInGroup(this.m_menu, this.GetGroupByLine(line), item.name);
                menues.SetSyncControlActive(this.m_menu, groupid, control);
                menues.SetSyncControlState(this.m_menu, groupid, control);
            }
        }
    }

    public void SyncLineStates(int start, int finish) {
        for (int line = 0; line < this.m_iLinenum; ++line) {
            int groupid = Common.GetID();
            for (int i = start; i < Math.min(finish, this.m_items.size()); ++i) {
                ItemInfo item = (ItemInfo)this.m_items.get(i);
                long control = menues.FindFieldInGroup(this.m_menu, this.GetGroupByLine(line), item.name);
                menues.SetSyncControlActive(this.m_menu, groupid, control);
                menues.SetSyncControlState(this.m_menu, groupid, control);
            }
        }
    }

    public void SetupCallbacks(long group) {
        block7: for (int i = 0; i < this.m_Events.size(); ++i) {
            int event2 = (Integer)this.m_Events.get(i);
            switch (event2) {
                case 4: {
                    menues.SetScriptOnGroup(this.m_menu, group, this, "OnButtonPress", 4L);
                    continue block7;
                }
                case 2: {
                    menues.SetScriptOnGroup(this.m_menu, group, this, "OnRadioPress", 2L);
                    continue block7;
                }
                case 3: {
                    menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseOver", 3L);
                    continue block7;
                }
                case 6: {
                    menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseInside", 6L);
                    continue block7;
                }
                case 5: {
                    menues.SetScriptOnGroup(this.m_menu, group, this, "OnMouseOutside", 5L);
                }
            }
        }
    }

    public TableNode GetSingleChecked() {
        return this.m_singlechecked;
    }

    public void RedrawTable() {
        this.RecalcGroups();
        menues.RedrawTable(this.m_menu, this.m_table);
    }

    public void AddTextField(String name, int userid) {
        this.AddTextField(name, userid, 0, false);
    }

    public void AddTextField(String name, int userid, int xshift) {
        this.AddTextField(name, userid, xshift, true);
    }

    public void AddUniqueTextField(String name, int userid) {
        this.AddTextField(name, userid, 0, true);
    }

    public void AddTruckView(String name, int userid) {
        this.AddTruckView(name, userid, 0, false);
    }

    public void AddTruckView(String name, int userid, int xshift) {
        this.AddTruckView(name, userid, xshift, true);
    }

    public void AddTruckView(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(3, name, userid, xshift, uniquename));
    }

    public void AddTextField(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(0, name, userid, xshift, uniquename));
    }

    public void AddSimpleButton(String name, int userid) {
        this.AddSimpleButton(name, userid, 0, false);
    }

    public void AddSimpleButton(String name, int userid, int xshift) {
        this.AddSimpleButton(name, userid, xshift, true);
    }

    public void AddUniqueSimpleButton(String name, int userid) {
        this.AddSimpleButton(name, userid, 0, true);
    }

    public void AddSimpleButton(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(1, name, userid, xshift, uniquename));
    }

    public void AddUniqueRadioButton(String name, int userid) {
        this.AddRadioButton(name, userid, 0, true);
    }

    public void AddRadioButton(String name, int userid) {
        this.AddRadioButton(name, userid, 0, false);
    }

    public void AddRadioButton(String name, int userid, int xshift) {
        this.AddRadioButton(name, userid, xshift, true);
    }

    public void AddRadioButton(String name, int userid, int xshift, boolean uniquename) {
        this.m_items.add(new ItemInfo(2, name, userid, xshift, uniquename));
    }

    public void AddEvent(int event2) {
        this.m_Events.add(new Integer(event2));
    }

    void UpdateRanger() {
        this.m_ranger.max_value = this.m_iVisSize > this.m_iLinenum ? this.m_iVisSize - this.m_iLinenum : 0;
        this.m_ranger.current_value = ((TableNode)this.m_top.item).line;
        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }
        menues.UpdateField(this.m_ranger);
    }

    public void AttachRanger(MENU_ranger ranger) {
        if (null == ranger) {
            Log.menu("Table. AttachRanger. Trying to attach null ranger.");
            return;
        }
        this.m_ranger = ranger;
        this.m_ranger.userid = s_maxid;
        this.m_ranger.page = this.m_iLinenum;
        this.m_ranger.current_value = 0;
        menues.SetScriptOnControl(this.m_menu, ranger, this, "OnRangerScroll", 1L);
        this.UpdateRanger();
    }

    Cmenu_TTI ComputeFirst() {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)this.m_root.children.get(i);
            if (!child.toshow) continue;
            return child;
        }
        return null;
    }

    int ComputeSize(Cmenu_TTI node) {
        int n;
        if (!node.toshow) {
            return 0;
        }
        int n2 = n = node == this.m_root ? 0 : 1;
        if (node.showCH) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
                n += this.ComputeSize(child);
            }
        }
        return n;
    }

    public void Traverse(TableVisitor visitor) {
        this.Traverse(visitor, 0);
    }

    public void Traverse(TableVisitor visitor, int flags) {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)this.m_root.children.get(i);
            this.CallTraverse(child, visitor, flags);
        }
    }

    public void Traverse(TableNode node, TableVisitor visitor, int flags) {
        for (int i = 0; i < node.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)node.self.children.get(i);
            this.CallTraverse(child, visitor, flags);
        }
    }

    boolean IsFlag(int value, int flag) {
        return (value & flag) != 0;
    }

    void CallTraverse(Cmenu_TTI node, TableVisitor visitor, int flags) {
        if (node != this.m_root) {
            TableNode tablenode = (TableNode)node.item;
            if (tablenode.item != null || this.IsFlag(flags, 2)) {
                visitor.VisitNode(tablenode);
            }
        }
        if (!this.IsFlag(flags, 1) || node.showCH) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
                this.CallTraverse(child, visitor, flags);
            }
        }
    }

    public void TraverseWithParent(TableParentVisitor visitor) {
        for (int i = 0; i < this.m_root.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)this.m_root.children.get(i);
            TableNode node = (TableNode)child.item;
            if (node.item == null) continue;
            this.TraverseParent(visitor, node, null);
        }
    }

    public void TraverseWithParent(TableParentVisitor visitor, TableNode start) {
        for (int i = 0; i < start.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)this.m_root.children.get(i);
            TableNode node = (TableNode)child.item;
            if (node.item == null) continue;
            this.TraverseParent(visitor, node, (TableNode)start.parent.item);
        }
    }

    public void TraverseUp(TableVisitor visitor, TableNode start) {
        Cmenu_TTI node = start.parent;
        while (node != this.m_root) {
            visitor.VisitNode((TableNode)node.item);
            node = ((TableNode)node.item).parent;
        }
    }

    private void TraverseParent(TableParentVisitor visitor, TableNode node, TableNode parent) {
        visitor.VisitPreChildren(node);
        for (int i = 0; i < node.self.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)node.self.children.get(i);
            this.TraverseParent(visitor, (TableNode)child.item, node);
        }
        visitor.VisitNodeWithParent(node, parent);
    }

    public void Check(TableNode node) {
        if (this.m_bSingleChecked) {
            if (node == this.m_singlechecked) {
                if (!this.m_bPermSingleChecked) {
                    this.m_singlechecked.checked = false;
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                    this.m_singlechecked = null;
                } else {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                }
                return;
            }
            if (this.m_singlechecked != null) {
                this.m_singlechecked.checked = false;
                if (this.m_singlechecked.group != -1L) {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                }
            }
            this.m_singlechecked = node;
            if (node != null) {
                this.m_singlechecked.checked = true;
                if (this.m_singlechecked.group != -1L) {
                    menues.RedrawGroup(this.m_menu, this.m_singlechecked.group);
                } else {
                    int newline = 0;
                    int topline = ((TableNode)this.m_top.item).line;
                    if (this.m_singlechecked.line - topline >= this.m_iLinenum || topline > this.m_singlechecked.line) {
                        newline = this.m_singlechecked.line;
                    }
                    this.ChangeTop(newline);
                    if (this.m_ranger != null) {
                        this.UpdateRanger();
                    }
                    this.RedrawTable();
                }
            }
            return;
        }
        boolean bl = node.checked = !node.checked;
        if (node.group != -1L) {
            menues.RedrawGroup(this.m_menu, node.group);
        }
    }

    public void ResetSingleChecked() {
        if (this.m_singlechecked != null) {
            this.m_singlechecked.checked = false;
        }
        this.m_singlechecked = (TableNode)this.FindItemByLine((Cmenu_TTI)this.m_root, (int)0).item;
        this.m_singlechecked.checked = true;
        this.RedrawTable();
    }

    public void ClearSingleChecked() {
        if (this.m_singlechecked != null) {
            this.Check(this.m_singlechecked);
        }
    }

    public void ShowHideNode(TableNode node, boolean toredraw) {
        node.self.toshow = !node.self.toshow;
        menues.UpdateData(node.self);
        if (toredraw) {
            this.RecalcGroups();
            this.RedrawTable();
        }
    }

    public void ShowHideSubtree(TableNode node, double delay, TableAnimation animation2) {
        if (delay == 0.0) {
            node.self.showCH = !node.self.showCH;
            menues.UpdateData(node.self);
            return;
        }
        this.m_delay = delay / 1000.0;
        this.m_animcb = animation2;
        if (this.m_AnimList == null) {
            this.m_AnimList = new Vector();
        } else {
            this.m_AnimList.clear();
        }
        this.m_animnode = node;
        this.m_iCounter = -1;
        this.m_isopening = !node.self.showCH;
        this.Traverse(node, new MakeAnimList(), 1);
        if (this.m_isopening) {
            node.self.showCH = true;
            menues.UpdateData(node.self);
        }
        menues.SetScriptObjectAnimation(this.m_menu, this.m_iAnimationID, this, "ShowHideAnimation");
    }

    void ShowHideAnimation(long stuff, double time) {
        int count = (int)(time / this.m_delay);
        if (count > this.m_AnimList.size() - 1) {
            count = this.m_AnimList.size() - 1;
        }
        if (count > this.m_iCounter) {
            TableNode node;
            int index;
            int i;
            for (i = this.m_iCounter + 1; i <= count; ++i) {
                index = this.m_isopening ? i : this.m_AnimList.size() - i - 1;
                node = (TableNode)this.m_AnimList.get(index);
                node.self.toshow = this.m_isopening;
                menues.UpdateData(node.self);
            }
            this.RedrawTable();
            if (this.m_animcb != null) {
                for (i = this.m_iCounter + 1; i <= count; ++i) {
                    index = this.m_isopening ? i : this.m_AnimList.size() - i - 1;
                    node = (TableNode)this.m_AnimList.get(index);
                    this.m_animcb.OnNodeChange(node, this.m_isopening);
                }
            }
            this.m_iCounter = count;
            if (count == this.m_AnimList.size() - 1) {
                this.StopAnimation();
                this.m_animcb.OnFinishAnimation();
            }
        }
    }

    private void StopAnimation() {
        menues.StopScriptAnimation(this.m_iAnimationID);
        this.m_AnimList.clear();
        if (!this.m_isopening) {
            this.m_animnode.self.showCH = false;
            menues.UpdateData(this.m_animnode.self);
        }
        this.m_animnode = null;
        this.RedrawTable();
    }

    public void SortTable(int type, TableCmp comparator) {
        if (type == this.m_iCurrentSortType) {
            this.m_bCurrentOrder = !this.m_bCurrentOrder;
        } else {
            this.m_iCurrentSortType = type;
            this.m_bCurrentOrder = true;
        }
        comparator.SetOrder(this.m_bCurrentOrder);
        this.m_lastcomp = comparator;
        PrivateComparator comp = new PrivateComparator(comparator);
        this.SortNode(this.m_root, comp);
        this.ChangeTop(0);
        menues.UpdateDataWithChildren(this.m_root);
        if (this.m_bSingleChecked) {
            this.ResetSingleChecked();
        } else {
            this.RedrawTable();
        }
    }

    public void RestoreLastSort() {
        if (this.m_lastcomp == null) {
            return;
        }
        PrivateComparator comp = new PrivateComparator(this.m_lastcomp);
        this.SortNode(this.m_root, comp);
        this.ChangeTop(0);
        menues.UpdateDataWithChildren(this.m_root);
        this.RedrawTable();
    }

    void ChangeTop(int line) {
        this.RecalcGroups();
        if (((TableNode)this.m_top.item).line == line) {
            return;
        }
        if (this.m_bSelect) {
            this.CheckDeselect();
        }
        this.m_top.ontop = false;
        menues.UpdateData(this.m_top);
        this.m_top = this.FindItemByLine(this.m_root, line);
        this.m_top.ontop = true;
        menues.UpdateData(this.m_top);
        menues.ConnectTableAndData(this.m_menu, this.m_table);
        this.RecalcGroups();
        if (this.m_bSelect && this.m_SelectNode != null) {
            this.CheckSelect(this.m_SelectNode, this.m_iSelectedID, this.FindControlInGroup(this.m_SelectNode.group, this.m_iSelectedID));
        }
    }

    long FindControlInGroup(long group, int id) {
        if (group == -1L) {
            return 0L;
        }
        for (int i = 0; i < this.m_items.size(); ++i) {
            ItemInfo item = (ItemInfo)this.m_items.get(i);
            if (item.userid != id) continue;
            return menues.FindFieldInGroup(this.m_menu, group, item.name);
        }
        return 0L;
    }

    public TableNode AddItem(TableNode node, Object item, boolean showch) {
        TableTree parent = node == null ? this.m_rootnode : node.buildtime;
        TableTree newitem = new TableTree();
        newitem.item = new TableNode();
        newitem.showch = showch;
        newitem.item.buildtime = newitem;
        newitem.item.checked = false;
        newitem.item.depth = parent.item == null ? 0 : parent.item.depth + 1;
        newitem.item.item = item;
        parent.children.add(newitem);
        return newitem.item;
    }

    public void EraseTraverse(EraseVisitor v) {
        if (this.m_bSingleChecked) {
            if (this.m_singlechecked != null) {
                this.m_singlechecked.checked = false;
            }
            this.m_singlechecked = null;
        }
        this.VisitEraseTraverse(this.m_root, v);
        this.ActualEreaseTraverse(this.m_root);
        menues.SetXMLDataOnTable(this.m_menu, this.m_table, this.m_root);
        if (this.m_bSingleChecked) {
            this.ResetSingleChecked();
        } else {
            this.RedrawTable();
        }
    }

    private void VisitEraseTraverse(Cmenu_TTI node, EraseVisitor v) {
        TableNode n = (TableNode)node.item;
        if (n.item != null && v.VisitNode(n)) {
            n.tokill = true;
            return;
        }
        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
            this.VisitEraseTraverse(child, v);
        }
    }

    private void ActualEreaseTraverse(Cmenu_TTI node) {
        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
            TableNode n = (TableNode)child.item;
            if (n.tokill) {
                node.children.remove(i);
                --i;
                continue;
            }
            this.ActualEreaseTraverse(child);
        }
    }

    public int GetLineNum() {
        return this.m_iLinenum;
    }

    public long GetGroupByLine(int line) {
        return this.m_groups[line];
    }

    public int GetScreenLineByLogical(int line) {
        if (this.m_top == null) {
            return line;
        }
        return line - ((TableNode)this.m_top.item).line;
    }

    public Vector GetFakeGroups() {
        return this.m_FakeGroups;
    }

    public void FillTable(Cmenu_TTI table) {
        int i;
        this.m_root = table;
        this.m_iCurrentTopLine = 0;
        int nodenum = this.SyncTrees(table, this.m_rootnode, null);
        if (nodenum - this.m_iLinenum > this.m_iFakeNum) {
            for (i = this.m_iFakeNum; i < nodenum - this.m_iLinenum; ++i) {
                long group = menues.CreateGroup(this.m_menu);
                menues.AddGroupInTable(this.m_menu, this.m_table, i + this.m_iLinenum, group);
                this.m_FakeGroups.add(new Long(group));
            }
            this.m_iFakeNum = nodenum - this.m_iLinenum;
        }
        this.ClearSetupTree(this.m_rootnode);
        for (i = 0; i < this.m_iLinenum; ++i) {
            Cmenu_TTI fakenode = new Cmenu_TTI();
            TableNode faketablenode = new TableNode();
            faketablenode.self = fakenode;
            faketablenode.parent = this.m_root;
            fakenode.item = faketablenode;
            fakenode.children = new Vector();
            fakenode.ontop = false;
            fakenode.showCH = false;
            fakenode.toshow = true;
            this.m_root.children.add(fakenode);
        }
        this.m_top = this.ComputeFirst();
        if (this.m_top != null) {
            this.m_top.ontop = true;
        }
        menues.UpdateDataWithChildren(table);
    }

    public void RefillTree() {
        if (this.m_root == null) {
            return;
        }
        this.FillTable(this.m_root);
        menues.SetXMLDataOnTable(this.m_menu, this.m_table, this.m_root);
        this.RecalcGroups();
        this.SetupRootNode();
        if (this.m_bSingleChecked) {
            this.ResetSingleChecked();
        } else {
            this.RedrawTable();
        }
    }

    void ClearSetupTree(TableTree setupnode) {
        setupnode.item.buildtime = null;
        setupnode.item = null;
        for (int i = 0; i < setupnode.children.size(); ++i) {
            this.ClearSetupTree((TableTree)setupnode.children.get(i));
        }
        setupnode.children.clear();
    }

    public void ResetTop() {
        this.RecalcGroups();
        this.ChangeTop(0);
        menues.RedrawTable(this.m_menu, this.m_table);
    }

    public void SetTop(TableNode newtop) {
        if (newtop == null || newtop.self == this.m_top) {
            return;
        }
        this.m_top.ontop = false;
        menues.UpdateData(this.m_top);
        this.m_top = newtop.self;
        this.m_top.ontop = true;
        menues.UpdateData(this.m_top);
        this.RedrawTable();
    }

    public TableNode GetTop() {
        return (TableNode)this.m_top.item;
    }

    void RecalcGroups() {
        CInteger h = new CInteger(-1);
        CInteger rh = new CInteger(0);
        this.CalcGroups(this.m_root, h, rh);
        if (this.m_top != null && !this.m_top.toshow) {
            this.m_top.ontop = false;
            menues.UpdateData(this.m_top);
            this.m_top = this.FindItemByLine(this.m_root, 0);
            this.m_top.ontop = true;
            menues.UpdateData(this.m_top);
            this.RecalcGroups();
        }
        this.m_iVisSize = h.value - this.m_iLinenum;
        if (this.m_bSingleChecked && this.m_singlechecked != null && !this.m_singlechecked.self.toshow) {
            this.m_singlechecked.checked = false;
            this.m_singlechecked = (TableNode)this.FindItemByLine((Cmenu_TTI)this.m_root, (int)0).item;
            this.m_singlechecked.checked = true;
        }
        menues.ConnectTableAndData(this.m_menu, this.m_table);
        if (this.m_ranger != null) {
            this.UpdateRanger();
        }
    }

    void CalcGroups(Cmenu_TTI tablenode, CInteger linenum, CInteger real_line_num) {
        TableNode localitem = (TableNode)tablenode.item;
        localitem.real_line = real_line_num.value;
        if (linenum == null || !tablenode.toshow) {
            localitem.line = -1;
            localitem.group = -1L;
        } else {
            localitem.line = linenum.value;
            int screenline = this.GetScreenLineByLogical(linenum.value);
            localitem.group = screenline < 0 || screenline >= this.m_iLinenum ? -1L : this.m_groups[screenline];
            ++linenum.value;
        }
        ++real_line_num.value;
        for (int i = 0; i < tablenode.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)tablenode.children.get(i);
            this.CalcGroups(child, tablenode.showCH && tablenode.toshow ? linenum : null, real_line_num);
        }
    }

    int SyncTrees(Cmenu_TTI tablenode, TableTree setupnode, Cmenu_TTI parent) {
        int sum = 1;
        tablenode.item = setupnode.item;
        tablenode.showCH = setupnode.showch;
        tablenode.ontop = false;
        tablenode.toshow = true;
        tablenode.children = new Vector();
        setupnode.item.self = tablenode;
        if (setupnode.item != null) {
            setupnode.item.parent = parent;
        }
        for (int i = 0; i < setupnode.children.size(); ++i) {
            tablenode.children.add(new Cmenu_TTI());
            sum += this.SyncTrees((Cmenu_TTI)tablenode.children.get(i), (TableTree)setupnode.children.get(i), tablenode);
        }
        return sum;
    }

    public void SetupLineInTable(MENUbutton_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode)table_node.item;
        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);
            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    public void SetupLineInTable(MENUsimplebutton_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode)table_node.item;
        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);
            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    public void SetupLineInTable(MENUText_field obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode)table_node.item;
        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);
            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(node, obj);
        }
    }

    public void SetupLineInTable(MENUTruckview obj, Cmenu_TTI table_node) {
        TableNode node = (TableNode)table_node.item;
        if (this.m_bAutoHideEmpty) {
            menues.SetShowField(obj.nativePointer, node.item != null);
            if (node.item != null) {
                this.m_callbacks.SetupLineInTable(3, node, obj);
            }
        } else {
            this.m_callbacks.SetupLineInTable(3, node, obj);
        }
    }

    void OnRangerScroll(long _menu, MENU_ranger scroller) {
        if (this.buzzy_OnRangerScroll) {
            return;
        }
        if (scroller.current_value == ((TableNode)this.m_top.item).line) {
            return;
        }
        this.buzzy_OnRangerScroll = true;
        this.m_iCurrentTopLine = scroller.current_value;
        this.ChangeTop(scroller.current_value);
        menues.RedrawTable(this.m_menu, this.m_table);
        this.buzzy_OnRangerScroll = false;
    }

    Cmenu_TTI FindItemByLine(Cmenu_TTI node, int line) {
        TableNode localitem = (TableNode)node.item;
        if (localitem.line == line) {
            return node;
        }
        if (node.showCH) {
            for (int i = 0; i < node.children.size(); ++i) {
                Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
                Cmenu_TTI result = this.FindItemByLine(child, line);
                if (result == null) continue;
                return result;
            }
        }
        return null;
    }

    public int GetLastID() {
        return this.m_iLastUserID;
    }

    void CheckLastID(long _group) {
        if (this.m_bGetLastID || this.m_bSelect) {
            for (int i = 0; i < this.m_items.size(); ++i) {
                ItemInfo iteminfo = (ItemInfo)this.m_items.get(i);
                long control = menues.FindFieldInGroup(this.m_menu, _group, iteminfo.realname);
                if (menues.GetStoredState(this.m_menu, control) == 0L) continue;
                this.m_iLastUserID = iteminfo.userid;
                this.m_iLastControl = control;
                break;
            }
        }
    }

    void CheckDeselect() {
        if (!this.m_bSelect) {
            return;
        }
        if (this.m_SelectNode != null && this.m_iSelectedControl != 0L) {
            this.m_SelectCbs.Deselect(this.m_SelectNode, this.m_iSelectedID, this.m_iSelectedControl);
        }
    }

    void CheckSelect(TableNode node, int id, long control) {
        this.wheelControlSelected();
        if (!this.m_bSelect) {
            return;
        }
        if (node == null || node.line == -1) {
            return;
        }
        this.m_SelectNode = node;
        this.m_iSelectedID = id;
        this.m_iSelectedControl = control;
        if (control != 0L) {
            this.m_SelectCbs.Select(this.m_SelectNode, this.m_iSelectedID, this.m_iSelectedControl);
        }
    }

    void OnKeyOutUp(long _menu, MENUText_field parent) {
        this.OnKeyOutUp();
    }

    void OnKeyOutUp(long _menu, MENUsimplebutton_field parent) {
        this.OnKeyOutUp();
    }

    void OnKeyOutUp(long _menu, MENUbutton_field parent) {
        this.OnKeyOutUp();
    }

    void OnKeyOutUp() {
        if (this.m_ranger == null) {
            return;
        }
        if (this.m_ranger.current_value > this.m_ranger.min_value) {
            --this.m_ranger.current_value;
            this.m_iCurrentTopLine = this.m_ranger.current_value;
            this.ChangeTop(this.m_ranger.current_value);
            menues.UpdateField(this.m_ranger);
            this.RedrawTable();
        }
    }

    public void ControlsMouseWheel(int value) {
        if (this.m_ranger == null) {
            return;
        }
        this.m_ranger.current_value -= value;
        if (this.m_ranger.current_value > this.m_ranger.max_value) {
            this.m_ranger.current_value = this.m_ranger.max_value;
        }
        if (this.m_ranger.current_value < this.m_ranger.min_value) {
            this.m_ranger.current_value = this.m_ranger.min_value;
        }
        menues.UpdateField(this.m_ranger);
        this.RedrawTable();
    }

    void OnKeyOutDown(long _menu, MENUText_field parent) {
        this.OnKeyOutDown();
    }

    void OnKeyOutDown(long _menu, MENUsimplebutton_field parent) {
        this.OnKeyOutDown();
    }

    void OnKeyOutDown(long _menu, MENUbutton_field parent) {
        this.OnKeyOutDown();
    }

    void OnKeyOutDown() {
        if (this.m_ranger == null) {
            return;
        }
        if (this.m_ranger.current_value < this.m_ranger.max_value) {
            ++this.m_ranger.current_value;
            this.m_iCurrentTopLine = this.m_ranger.current_value;
            this.ChangeTop(this.m_ranger.current_value);
            menues.UpdateField(this.m_ranger);
            this.RedrawTable();
        }
    }

    public int sliderPosition() {
        return this.m_ranger.current_value;
    }

    public void OnRadioPress(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);
        this.CheckDeselect();
        this.CheckLastID(_group);
        this.CheckSelect((TableNode)tablenode.item, this.m_iLastUserID, this.m_iLastControl);
        this.m_callbacks.OnEvent(2L, (TableNode)tablenode.item, _group, _menu);
    }

    public void OnButtonPress(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);
        this.CheckDeselect();
        this.CheckLastID(_group);
        this.CheckSelect((TableNode)tablenode.item, this.m_iLastUserID, this.m_iLastControl);
        this.m_callbacks.OnEvent(4L, (TableNode)tablenode.item, _group, _menu);
    }

    public void OnMouseOver(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);
        this.CheckLastID(_group);
        this.m_callbacks.OnEvent(3L, (TableNode)tablenode.item, _group, _menu);
    }

    public void OnMouseInside(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);
        this.CheckLastID(_group);
        this.m_callbacks.OnEvent(6L, (TableNode)tablenode.item, _group, _menu);
    }

    public void OnMouseOutside(long _menu, long _group) {
        Cmenu_TTI tablenode = menues.GetXMLDataOnGroup(_menu, _group);
        this.CheckLastID(_group);
        this.m_callbacks.OnEvent(5L, (TableNode)tablenode.item, _group, _menu);
    }

    void SortNode(Cmenu_TTI node, PrivateComparator comparator) {
        Collections.sort(node.children, comparator);
        for (int i = 0; i < node.children.size(); ++i) {
            Cmenu_TTI child = (Cmenu_TTI)node.children.get(i);
            this.SortNode(child, comparator);
        }
    }

    static {
        s_maxid = 0;
    }

    static class PrivateComparator
    implements Comparator {
        Comparator cmp;

        public PrivateComparator(Comparator _cmp) {
            this.cmp = _cmp;
        }

        public int compare(Object o1, Object o2) {
            Cmenu_TTI m1 = (Cmenu_TTI)o1;
            Cmenu_TTI m2 = (Cmenu_TTI)o2;
            TableNode n1 = (TableNode)m1.item;
            TableNode n2 = (TableNode)m2.item;
            if (n1.item == null && n2.item == null) {
                return 0;
            }
            if (n1.item == null) {
                return 1;
            }
            if (n2.item == null) {
                return -1;
            }
            return this.cmp.compare(n1.item, n2.item);
        }
    }

    static class TableTree {
        TableNode item;
        Vector children = new Vector();
        boolean showch;
    }

    static class ItemInfo {
        int xshift;
        int type;
        int userid;
        boolean uniquenaming;
        String name;
        String realname;

        ItemInfo(int _type, String _name, int _userid, int _xshift, boolean _uniquenaming) {
            this.type = _type;
            this.name = _name;
            this.userid = _userid;
            this.xshift = _xshift;
            this.uniquenaming = _uniquenaming;
        }
    }

    class MakeAnimList
    implements TableVisitor {
        MakeAnimList() {
        }

        public void VisitNode(TableNode node) {
            if (node.line != -1 ^ Table.this.m_isopening) {
                Table.this.m_AnimList.add(node);
            }
            if (node.self.toshow == Table.this.m_isopening) {
                node.self.toshow = !node.self.toshow;
                menues.UpdateData(node.self);
            }
        }
    }

    public static interface TableAnimation {
        public void OnNodeChange(TableNode var1, boolean var2);

        public void OnFinishAnimation();
    }

    public static interface TableParentVisitor {
        public void VisitPreChildren(TableNode var1);

        public void VisitNodeWithParent(TableNode var1, TableNode var2);
    }

    public static interface EraseVisitor {
        public boolean VisitNode(TableNode var1);
    }

    public static interface TableVisitor {
        public void VisitNode(TableNode var1);
    }
}

