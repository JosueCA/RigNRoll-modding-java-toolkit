/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.Cmenu_TTI;
import menu.Common;
import menu.Item;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.MacroKit;
import menu.SelectCb;
import menu.Table;
import menu.TableCallbacks;
import menu.TableNode;
import menu.menues;
import menuscript.Converts;

public class CondTable
implements TableCallbacks,
Table.TableAnimation {
    public static final int OPENGROUP = 0;
    public static final int TEXT = 1;
    public static final int TEXTGLOW = 2;
    public static final int CONDITION_GREEN = 3;
    public static final int CONDITION_YELLOW = 4;
    public static final int CONDITION_RED = 5;
    public static final int STUB = 6;
    public static final int PRICE = 7;
    public static final int AUTH = 8;
    public static final int CHECK = 9;
    public static final int SIZE = 10;
    public static double OPEN_DUR = 0.5;
    public static final int EVENT_CHECK = 100;
    public static final int EVENT_UNCHECK = 101;
    public static final int EVENT_SELECT = 102;
    Common common;
    boolean m_bAnimating = false;
    boolean[][] m_aArrowStates;
    int m_iAnimLine;
    int m_iSelectedLine = 0;
    int m_iWasSelectedLine = 0;
    static final boolean OPEN = true;
    static final boolean CLOSED = false;
    MENU_ranger m_Ranger;
    SelectCb m_cbs;
    boolean m_bHasChecked;
    FixupTraverse m_FixupTraverse = new FixupTraverse();
    FixupChecked m_FixupChecked = new FixupChecked();
    IsTypeFullCheck m_istypefullcheck = new IsTypeFullCheck();
    TypeCheck m_typecheck = new TypeCheck();
    Table m_Table;
    String auth_text = null;
    String price_text = null;
    CountingTraverse m_Counting = new CountingTraverse();
    IsSubTreeFullCheck m_fullcheck = new IsSubTreeFullCheck();
    SubTreeCheck m_SubTreeChecker = new SubTreeCheck();
    UpPass m_UpPass = new UpPass();
    boolean m_bGlowEnable = false;
    menues m_this;
    Vector[] m_aNames;

    public void Traverse(Table.TableVisitor visitor) {
        this.m_Table.Traverse(visitor);
    }

    static boolean HasLevel(int ID) {
        return ID <= 6;
    }

    public CondTable(Common common, String name) {
        this.m_Table = new Table(common.GetMenu(), name);
        this.m_aNames = new Vector[10];
        for (int i = 0; i < 10; ++i) {
            this.m_aNames[i] = new Vector(3);
        }
        this.common = common;
    }

    public void DeInit() {
        if (this.m_Table != null) {
            this.m_Table.DeInit();
        }
    }

    int Encode(int type, int level) {
        return level * 100 + type;
    }

    int DecodeLevel(int id) {
        return id / 100;
    }

    int DecodeType(int id) {
        return id % 100;
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
            case 7: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 8: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 9: {
                this.m_Table.AddRadioButton(name, this.Encode(type, level));
                this.m_bHasChecked = true;
                break;
            }
            case 3: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 4: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
                break;
            }
            case 5: {
                this.m_Table.AddTextField(name, this.Encode(type, level));
            }
        }
    }

    public void Fixup() {
        this.m_Table.TraverseWithParent(new FixupTraverse());
    }

    public TableNode AddItem(TableNode node, Item item) {
        return this.m_Table.AddItem(node, item, node == null);
    }

    public void Setup(int vertsize, int linenum, String xmlfile, String controlgroup, String parent, SelectCb cb) {
        this.m_cbs = cb;
        this.m_Table.AddEvent(4);
        this.m_Table.AddEvent(2);
        this.m_Table.AddEvent(3);
        this.m_Table.Setup(vertsize, linenum, xmlfile, controlgroup, parent, this, 1);
        this.Fixup();
        this.m_aArrowStates = new boolean[linenum][];
        for (int i = 0; i < this.m_aArrowStates.length; ++i) {
            this.m_aArrowStates[i] = new boolean[3];
            for (int i1 = 0; i1 < 3; ++i1) {
                this.m_aArrowStates[i][i1] = true;
            }
        }
    }

    public void RefillTree() {
        this.m_Table.RefillTree();
        this.Fixup();
    }

    public void StartGlow() {
        this.m_Table.Traverse(new StartGlowTraverse(), 0);
    }

    public void AttachRanger(MENU_ranger ranger) {
        this.m_Table.AttachRanger(ranger);
        this.m_Ranger = ranger;
    }

    public void SetupLineInTable(TableNode node, MENUText_field text) {
        int level = this.DecodeLevel(text.userid);
        int type = this.DecodeType(text.userid);
        if (CondTable.HasLevel(type) && level != node.depth || node.item == null) {
            menues.SetShowField(text.nativePointer, false);
            return;
        }
        menues.SetShowField(text.nativePointer, true);
        Item item = (Item)node.item;
        switch (type) {
            case 2: {
                text.text = "" + item.name;
                break;
            }
            case 7: {
                if (this.price_text == null) {
                    this.price_text = text.text;
                }
                if (item.price > 0) {
                    KeyPair[] macro = new KeyPair[]{new KeyPair("VALUE", Converts.ConvertNumeric(item.price))};
                    text.text = MacroKit.Parse(this.price_text, macro);
                    break;
                }
                text.text = "-";
                break;
            }
            case 8: {
                if (this.auth_text == null) {
                    this.auth_text = text.text;
                }
                if (item.auth == 0) {
                    text.text = "";
                    break;
                }
                KeyPair[] macro = new KeyPair[]{new KeyPair("VALUE", Converts.ConvertNumeric(item.auth))};
                text.text = MacroKit.Parse(this.auth_text, macro);
                break;
            }
            case 3: {
                if (item.condition == 0) break;
                menues.SetShowField(text.nativePointer, false);
                break;
            }
            case 4: {
                if (item.condition == 1) break;
                menues.SetShowField(text.nativePointer, false);
                break;
            }
            case 5: {
                if (item.condition == 2) break;
                menues.SetShowField(text.nativePointer, false);
            }
        }
    }

    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
        int level = this.DecodeLevel(button.userid);
        int type = this.DecodeType(button.userid);
        if (CondTable.HasLevel(type) && level != node.depth || node.item == null) {
            menues.SetShowField(button.nativePointer, false);
            return;
        }
        menues.SetShowField(button.nativePointer, true);
        switch (type) {
            case 0: {
                if (node.self.children.size() != 0) break;
                menues.SetShowField(button.nativePointer, false);
            }
        }
    }

    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        int level = this.DecodeLevel(radio.userid);
        int type = this.DecodeType(radio.userid);
        if (CondTable.HasLevel(type) && level != node.depth || node.item == null) {
            menues.SetShowField(radio.nativePointer, false);
            return;
        }
        menues.SetShowField(radio.nativePointer, true);
        Item item = (Item)node.item;
        if (type == 1) {
            radio.text = "" + item.name;
            menues.SetFieldState(radio.nativePointer, this.m_iSelectedLine == node.line ? 1 : 0);
        }
        if (type == 9) {
            menues.SetShowField(radio.nativePointer, item.condition != 0);
            menues.SetFieldState(radio.nativePointer, node.checked ? 1 : 0);
        }
    }

    void RotateButtonTree(TableNode node, boolean direction, double velocity) {
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), false, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), true, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[0].get(node.depth), true, 1, velocity, (direction ? 1 : -1) * 90);
    }

    void RotateButtonGroup(TableNode node, boolean direction, double velocity) {
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
                if (this.m_cbs != null && this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    this.m_cbs.OnSelect(102, node);
                }
            } else {
                if (this.m_cbs != null && this.m_iWasSelectedLine != this.m_iSelectedLine) {
                    this.m_cbs.OnSelect(102, node);
                }
                this.m_Table.RedrawTable();
            }
        }
    }

    void OnRadioPress(TableNode node, long group) {
        int type = this.DecodeType(this.m_Table.GetLastID());
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
                if (this.m_cbs != null) {
                    this.m_cbs.OnSelect(102, node);
                }
            } else {
                this.m_Table.RedrawTable();
            }
        }
        if (type == 9) {
            this.FullCheck(node);
            if (this.m_cbs != null) {
                this.m_cbs.OnSelect(node.checked ? 100 : 101, node);
            }
        }
    }

    private void FullCheck(TableNode node) {
        if (node.checked) {
            this.m_fullcheck.Setup();
            this.m_Table.Traverse(node, this.m_fullcheck, 0);
            if (!this.m_fullcheck.GetResult()) {
                node.checked = false;
            }
        }
        this.m_Table.Check(node);
        this.m_SubTreeChecker.Setup(node.checked);
        this.m_Table.Traverse(node, this.m_SubTreeChecker, 0);
        this.m_Table.TraverseUp(this.m_UpPass, node);
        this.m_Table.TraverseWithParent(this.m_FixupTraverse);
        this.m_Table.RedrawTable();
    }

    public void FixUp() {
        this.m_Table.TraverseWithParent(this.m_FixupTraverse);
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

    public void RedrawTable(boolean fixup) {
        if (fixup) {
            this.Fixup();
        }
        this.m_Table.RedrawTable();
    }

    public void OnNodeChange(TableNode node, boolean direction) {
        if (direction) {
            int level = node.depth;
            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[1].get(level), 0.5, 1.0, "type1");
            menues.SetAlfaAnimationOnGroupTree(this.common.GetMenu(), node.group, (String)this.m_aNames[2].get(level), 0.5, 1.0, "type2");
        }
    }

    public void VisitNode(TableNode node) {
    }

    public void OnFinishAnimation() {
        this.m_bAnimating = false;
        this.m_iAnimLine = -1;
        menues.SetIgnoreEvents(this.m_Ranger.nativePointer, false);
    }

    public void AfterInitMenu() {
        int depth;
        for (depth = 0; depth < 3; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), false, 0, 100.0, 0.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 0, 100.0, 0.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 1, 100.0, 0.0);
        }
        this.m_Table.Traverse(new InitAnimTraverse());
        if (this.m_bGlowEnable) {
            this.StartGlow();
        }
        for (depth = 0; depth < 3; ++depth) {
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), false, 0, 1000.0, 90.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 0, 1000.0, 90.0);
            menues.SetUVRotationOnGroupTree(this.common.GetMenu(), this.m_Table.GetGroupByLine(0), (String)this.m_aNames[0].get(depth), true, 1, 1000.0, 90.0);
        }
    }

    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    private class UpPass
    implements Table.TableVisitor {
        private UpPass() {
        }

        public void VisitNode(TableNode node) {
            boolean checkIt = true;
            for (int i = 0; i < node.self.children.size(); ++i) {
                TableNode child = (TableNode)((Cmenu_TTI)node.self.children.get((int)i)).item;
                Item item = (Item)child.item;
                if (item.condition == 0 || child.checked) continue;
                checkIt = false;
                break;
            }
            if (checkIt) {
                if (!node.checked) {
                    CondTable.this.m_Table.Check(node);
                }
            } else if (node.checked) {
                CondTable.this.m_Table.Check(node);
            }
        }
    }

    private class SubTreeCheck
    implements Table.TableVisitor {
        boolean check;

        private SubTreeCheck() {
        }

        void Setup(boolean _check) {
            this.check = _check;
        }

        public void VisitNode(TableNode node) {
            if (node.checked != this.check) {
                CondTable.this.m_Table.Check(node);
            }
        }
    }

    private class IsSubTreeFullCheck
    implements Table.TableVisitor {
        boolean isfull;

        private IsSubTreeFullCheck() {
        }

        void Setup() {
            this.isfull = true;
        }

        public void VisitNode(TableNode node) {
            if (node.checked != this.isfull) {
                this.isfull = false;
            }
        }

        boolean GetResult() {
            return this.isfull;
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
            String textname = (String)CondTable.this.m_aNames[1].get(node.depth);
            String glowname = (String)CondTable.this.m_aNames[2].get(node.depth);
            menues.SetAlfaAnimationOnGroupTree(CondTable.this.common.GetMenu(), node.group, textname, 0.5, 1.0, "type1");
            menues.SetAlfaAnimationOnGroupTree(CondTable.this.common.GetMenu(), node.group, glowname, 0.5, 1.0, "type2");
        }
    }

    private class TypeCheck
    implements Table.TableVisitor {
        boolean tocheck;
        int cond;

        private TypeCheck() {
        }

        void Setup(int condition, boolean _tocheck) {
            this.tocheck = _tocheck;
            this.cond = condition;
        }

        public void VisitNode(TableNode node) {
            if (node.self.children.size() != 0) {
                return;
            }
            Item item = (Item)node.item;
            if (item.condition == this.cond) {
                node.checked = this.tocheck;
            }
        }
    }

    private class IsTypeFullCheck
    implements Table.TableVisitor {
        boolean isfull;
        int cond;

        private IsTypeFullCheck() {
        }

        void Setup(int condition) {
            this.isfull = true;
            this.cond = condition;
        }

        public void VisitNode(TableNode node) {
            if (node.self.children.size() != 0 || !this.isfull) {
                return;
            }
            Item item = (Item)node.item;
            if (item.condition == this.cond && !node.checked) {
                this.isfull = false;
            }
        }

        boolean GetResult() {
            return this.isfull;
        }
    }

    class InitAnimTraverse
    implements Table.TableVisitor {
        int start = 0;

        InitAnimTraverse() {
        }

        public void VisitNode(TableNode node) {
            long gr;
            if (node.line == 0 || node.line == -1) {
                return;
            }
            if (node.line >= CondTable.this.m_Table.GetLineNum()) {
                Long group = (Long)CondTable.this.m_Table.GetFakeGroups().get(node.line - CondTable.this.m_Table.GetLineNum());
                gr = group;
            } else {
                gr = CondTable.this.m_Table.GetGroupByLine(node.line);
            }
            for (int depth = 0; depth < 3; ++depth) {
                String name = (String)CondTable.this.m_aNames[0].get(depth);
                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(), CondTable.this.m_Table.GetGroupByLine(0), name, false, 0, gr, false, 0);
                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(), CondTable.this.m_Table.GetGroupByLine(0), name, true, 0, gr, true, 0);
                menues.CopyUVRotationOnGroupTree(CondTable.this.common.GetMenu(), CondTable.this.m_Table.GetGroupByLine(0), name, true, 1, gr, true, 1);
            }
        }
    }

    class FixupChecked
    implements Table.TableParentVisitor {
        FixupChecked() {
        }

        public void VisitPreChildren(TableNode node) {
            if (node.self.children.size() == 0) {
                return;
            }
            node.checked = false;
        }

        public void VisitNodeWithParent(TableNode node, TableNode parent) {
            if (parent == null) {
                return;
            }
            if (node.checked) {
                parent.checked = true;
            }
        }
    }

    class FixupTraverse
    implements Table.TableParentVisitor {
        FixupTraverse() {
        }

        public void VisitPreChildren(TableNode node) {
            Item item = (Item)node.item;
            if (node.self.children.size() == 0) {
                item.auth = node.checked ? item.price : 0;
                return;
            }
            item.condition = 0;
            item.price = 0;
            item.auth = 0;
        }

        public void VisitNodeWithParent(TableNode node, TableNode parent) {
            if (parent == null) {
                return;
            }
            Item parentitem = (Item)parent.item;
            Item item = (Item)node.item;
            if (parentitem.condition < item.condition) {
                parentitem.condition = item.condition;
            }
            parentitem.price += item.price;
            parentitem.auth += item.auth;
        }
    }
}

