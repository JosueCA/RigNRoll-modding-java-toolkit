/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu.DLCShopMenu;

import java.util.HashMap;
import java.util.Vector;
import menu.Common;
import menu.MENUEditBox;
import menu.MENUText_field;
import menu.MENU_ranger;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.Table;
import menu.TableCallbacks;
import menu.TableNode;
import menu.TextScroller;
import menu.menues;
import menuscript.Converts;
import menuscript.DLCShopManager;
import menuscript.IPoPUpMenuListener;
import menuscript.PoPUpMenu;
import menuscript.mainmenu.DLCShopMenu.DLCShopActivationManager;
import menuscript.mainmenu.DLCShopMenu.DLCShopItem;
import menuscript.mainmenu.DLCShopMenu.DLCShopMenuFilePath;
import menuscript.mainmenu.DLCShopMenu.DLCShopTableInterface;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public final class DLCShopTable
implements TableCallbacks,
Table.TableAnimation,
IPoPUpMenuListener,
DLCShopTableInterface {
    private static final String GO_TO_STORE_TABLEGROUP = "Tablegroup - DLC - Go to RNR GameStore";
    private static final String GO_TO_STORE_WINDOW = "DLC - Go to RNR GameStore";
    private PoPUpMenu m_goToStoreWindow;
    private Table m_table;
    TableNode m_selectedNode = null;
    private Common m_common;
    private DLCShopActivationManager m_activationManager;
    private ControlsDepository m_controls;
    private MENU_ranger m_ranger;
    private long m_purchaseButtonId;
    private long m_purchaseButtonGrayId;
    private long m_activateButtonId;
    private long m_activateButtonGrayId;
    private long m_serialNumberEditBoxId;
    private String m_serialNumber;
    private long m_descriptionItemNameEditBoxId;
    private long m_descriptionBodyEditBoxId;
    private long m_descriptionScrollerId;
    private TextScroller m_scroller = null;
    private int m_iSelectedLine = 0;
    private static double ROLL_UP_DURATION = 0.2;
    private CountNodeChildrenVisitor m_counting = new CountNodeChildrenVisitor();
    private DLCShopMenuFilePath.DLCShopMenuType m_menuType;
    private boolean m_isAnimating = false;

    public DLCShopTable(Common common, String name, DLCShopMenuFilePath.DLCShopMenuType menuType) {
        this.m_common = common;
        this.m_menuType = menuType;
        this.m_table = new Table(common.GetMenu(), name);
        this.m_table.AddEvent(4);
        this.m_table.AddEvent(2);
        this.m_activationManager = new DLCShopActivationManager(this.m_common.GetMenu(), this.m_menuType, this);
        this.m_controls = new ControlsDepository();
        this.initButtons();
    }

    public void deinit() {
        if (this.m_table != null) {
            this.m_table.DeInit();
        }
        if (this.m_scroller != null) {
            this.m_scroller.Deinit();
        }
    }

    public void hide() {
        this.m_goToStoreWindow.hide();
        this.m_activationManager.hide();
    }

    private void initButtons() {
        this.m_purchaseButtonId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - PURCHASE");
        this.m_purchaseButtonGrayId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - PURCHASE - GRAY");
        this.m_activateButtonId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - DLC ACTIVATE");
        this.m_activateButtonGrayId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - DLC ACTIVATE - GRAY");
        this.m_serialNumberEditBoxId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - AVAILABLE CONTENT VALUE");
        this.m_goToStoreWindow = new PoPUpMenu(this.m_common.GetMenu(), DLCShopMenuFilePath.getMenuPath(this.m_menuType), GO_TO_STORE_TABLEGROUP, GO_TO_STORE_WINDOW);
        this.m_descriptionBodyEditBoxId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - DESCRIPTION BODY");
        this.m_descriptionItemNameEditBoxId = menues.FindFieldInMenu(this.m_common.GetMenu(), "DLC - DESCRIPTION ITEM NAME");
        this.m_descriptionScrollerId = menues.FindFieldInMenu(this.m_common.GetMenu(), "Tableranger - DLC DESCRIPTION");
    }

    public void afterInitMenu() {
        this.m_table.Traverse(new RollUpLinesVisitor());
        this.m_table.RedrawTable();
        this.setScriptOnButtons();
        this.setStartVisibilityOfButtons();
        this.m_activationManager.afterInit();
    }

    public void setStartVisibilityOfButtons() {
        if (0L != this.m_purchaseButtonId) {
            menues.SetShowField(this.m_purchaseButtonId, false);
        }
        if (0L != this.m_purchaseButtonGrayId) {
            menues.SetShowField(this.m_purchaseButtonGrayId, true);
        }
        if (0L != this.m_activateButtonGrayId) {
            menues.SetShowField(this.m_activateButtonGrayId, false);
        }
        if (0L != this.m_activateButtonId) {
            menues.SetShowField(this.m_activateButtonId, true);
        }
        if (0L != this.m_serialNumberEditBoxId) {
            menues.SetShowField(this.m_serialNumberEditBoxId, true);
        }
        if (0L != this.m_descriptionBodyEditBoxId) {
            menues.SetShowField(this.m_descriptionBodyEditBoxId, true);
        }
        if (0L != this.m_descriptionItemNameEditBoxId) {
            menues.SetShowField(this.m_descriptionItemNameEditBoxId, true);
        }
        if (0L != this.m_descriptionScrollerId) {
            menues.SetShowField(this.m_descriptionScrollerId, true);
        }
    }

    private void setScriptOnButtons() {
        if (0L != this.m_purchaseButtonId) {
            menues.SetScriptOnControl(this.m_common.GetMenu(), menues.ConvertMenuFields(this.m_purchaseButtonId), this, "onPurchaseButtonPressed", 4L);
            this.onPurchaseButtonPressed(0L, null);
        }
        if (0L != this.m_activateButtonId) {
            menues.SetScriptOnControl(this.m_common.GetMenu(), menues.ConvertMenuFields(this.m_activateButtonId), this, "onActivateButtonPressed", 4L);
            this.onActivateButtonPressed(0L, null);
        }
        if (0L != this.m_serialNumberEditBoxId) {
            menues.SetScriptOnControl(this.m_common.GetMenu(), menues.ConvertMenuFields(this.m_serialNumberEditBoxId), this, "onDismissSerialNumber", 19L);
            menues.SetScriptOnControl(this.m_common.GetMenu(), menues.ConvertMenuFields(this.m_serialNumberEditBoxId), this, "onExecuteSerialNumber", 16L);
            this.onDismissSerialNumber(0L, null);
            this.onExecuteSerialNumber(0L, null);
        }
        if (null != this.m_goToStoreWindow) {
            this.m_goToStoreWindow.afterInit();
            this.m_goToStoreWindow.hide();
            this.m_goToStoreWindow.addListener(this);
        }
    }

    public void Setup(int vertsize, int linenum, String xmlfile, String controlgroup, String parent) {
        this.addControls();
        DLCShopManager.getDLCShopManager().UpdateStore();
        DLCShopNode root = this.convert(DLCShopManager.getDLCShopManager().GetStores());
        this.fillDLCStoreTable(root);
        this.m_table.Setup(vertsize, linenum, xmlfile, controlgroup, parent, this, 1);
    }

    private void addControls() {
        this.addControl(0, 0, "DLC - ITEM - NAME Combo");
        this.addControl(2, 0, "DLC - ITEM - BUTTONbrowse");
        this.addControl(3, 0, "DLC - ITEM - PRICE");
        this.addControl(4, 0, "DLC - ITEM - Installed - TRUE");
        this.addControl(5, 0, "DLC - ITEM - Installed - FALSE");
        this.addControl(6, 0, "DLC - ITEM - Activated - TRUE");
        this.addControl(7, 0, "DLC - ITEM - Activated - FALSE");
        this.addControl(1, 1, "DLC - ITEM - NAME Item");
        this.addControl(3, 1, "DLC - ITEM - PRICE");
        this.addControl(4, 1, "DLC - ITEM - Installed - TRUE");
        this.addControl(5, 1, "DLC - ITEM - Installed - FALSE");
        this.addControl(6, 1, "DLC - ITEM - Activated - TRUE");
        this.addControl(7, 1, "DLC - ITEM - Activated - FALSE");
    }

    public void onPurchaseButtonPressed(long _menu, MENUsimplebutton_field button) {
        if (_menu == this.m_common.GetMenu() && button.nativePointer == this.m_purchaseButtonId) {
            this.m_goToStoreWindow.show();
        }
    }

    public void onActivateButtonPressed(long _menu, MENUsimplebutton_field button) {
        if (_menu == this.m_common.GetMenu() && button.nativePointer == this.m_activateButtonId) {
            this.m_activationManager.activate(this.m_serialNumber);
            this.setStartVisibilityOfButtons();
        }
    }

    @Override
    public void updateTable() {
        if (null != this.m_selectedNode) {
            // empty if block
        }
        this.m_selectedNode = null;
        DLCShopNode root = this.convert(DLCShopManager.getDLCShopManager().GetStores());
        this.m_table.SetupRootNode();
        this.fillDLCStoreTable(root);
        this.m_table.RefillTree();
        this.m_table.RedrawTable();
    }

    public void onExecuteSerialNumber(long _menu, MENUEditBox obj) {
        if (_menu == this.m_common.GetMenu() && obj.nativePointer == this.m_serialNumberEditBoxId) {
            this.m_serialNumber = menues.GetFieldText(this.m_serialNumberEditBoxId);
            this.m_activationManager.activate(this.m_serialNumber);
            this.setStartVisibilityOfButtons();
        }
    }

    public void onDismissSerialNumber(long _menu, MENUEditBox obj) {
        if (_menu == this.m_common.GetMenu() && obj.nativePointer == this.m_serialNumberEditBoxId) {
            this.m_serialNumber = menues.GetFieldText(this.m_serialNumberEditBoxId);
        }
    }

    @Override
    public void onAgreeclose() {
        DLCShopItem selectedItem = (DLCShopItem)this.m_selectedNode.item;
        DLCShopManager.getDLCShopManager().GoToURL(selectedItem.id);
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onOpen() {
    }

    private void fillDLCStoreTable(TableNode pap, DLCShopNode node) {
        TableNode res = this.m_table.AddItem(pap, node.item, true);
        for (int i = 0; i < node.children.size(); ++i) {
            this.fillDLCStoreTable(res, node.children.get(i));
        }
    }

    public void fillDLCStoreTable(DLCShopNode root) {
        if (root != null) {
            for (int i = 0; i < root.children.size(); ++i) {
                this.fillDLCStoreTable(null, root.children.get(i));
            }
        }
    }

    public void attachRanger(MENU_ranger ranger) {
        this.m_table.AttachRanger(ranger);
        this.m_ranger = ranger;
    }

    public void addControl(int controlType, int controlDepth, String controlName) {
        this.m_controls.addName(controlType, controlDepth, controlName);
        switch (controlType) {
            case 0: 
            case 1: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: {
                this.m_table.AddRadioButton(controlName, this.m_controls.encode(controlType, controlDepth));
                break;
            }
            case 2: {
                this.m_table.AddSimpleButton(controlName, this.m_controls.encode(controlType, controlDepth));
                break;
            }
        }
    }

    @Override
    public void SetupLineInTable(TableNode node, MENUText_field text) {
    }

    @Override
    public void SetupLineInTable(TableNode node, MENUsimplebutton_field button) {
        int depth = this.m_controls.decodeDepth(button.userid);
        if (depth != node.depth || null == node.item) {
            menues.SetShowField(button.nativePointer, false);
            return;
        }
        int type = this.m_controls.decodeType(button.userid);
        switch (type) {
            case 2: {
                menues.SetShowField(button.nativePointer, true);
                break;
            }
        }
    }

    @Override
    public void SetupLineInTable(TableNode node, MENUbutton_field radio) {
        int depth = this.m_controls.decodeDepth(radio.userid);
        if (depth != node.depth || null == node.item) {
            menues.SetShowField(radio.nativePointer, false);
            return;
        }
        int type = this.m_controls.decodeType(radio.userid);
        DLCShopItem item = (DLCShopItem)node.item;
        switch (type) {
            case 0: {
                menues.SetShowField(radio.nativePointer, true);
                menues.SetFieldState(radio.nativePointer, this.m_iSelectedLine == node.line ? 1 : 0);
                radio.text = item.name;
                break;
            }
            case 1: {
                menues.SetShowField(radio.nativePointer, true);
                menues.SetFieldState(radio.nativePointer, this.m_iSelectedLine == node.line ? 1 : 0);
                radio.text = item.name;
                break;
            }
            case 3: {
                menues.SetShowField(radio.nativePointer, true);
                radio.text = Integer.toString(item.price);
                menues.SetBlindess(radio.nativePointer, true);
                menues.SetIgnoreEvents(radio.nativePointer, true);
                break;
            }
            case 4: {
                menues.SetShowField(radio.nativePointer, item.installed);
                menues.SetBlindess(radio.nativePointer, true);
                menues.SetIgnoreEvents(radio.nativePointer, true);
                break;
            }
            case 5: {
                menues.SetShowField(radio.nativePointer, !item.installed);
                menues.SetBlindess(radio.nativePointer, true);
                menues.SetIgnoreEvents(radio.nativePointer, true);
                break;
            }
            case 6: {
                menues.SetShowField(radio.nativePointer, item.activated);
                menues.SetBlindess(radio.nativePointer, true);
                menues.SetIgnoreEvents(radio.nativePointer, true);
                break;
            }
            case 7: {
                menues.SetShowField(radio.nativePointer, !item.activated);
                menues.SetBlindess(radio.nativePointer, true);
                menues.SetIgnoreEvents(radio.nativePointer, true);
                break;
            }
        }
    }

    @Override
    public void SetupLineInTable(int type, TableNode node, Object control) {
    }

    @Override
    public void OnEvent(long event2, TableNode node, long group, long _menu) {
        switch ((int)event2) {
            case 4: {
                this.onButtonPress(node);
                break;
            }
            case 2: {
                this.onButtonPress(node);
            }
        }
    }

    @Override
    public void OnNodeChange(TableNode node, boolean direction) {
    }

    private void onButtonPress(TableNode node) {
        this.selectNode(node);
        int type = this.m_controls.decodeType(this.m_table.GetLastID());
        if (type == 0 || type == 1 || type == 2) {
            this.m_iSelectedLine = node.line;
            this.showHideChildren(node);
        }
    }

    private void selectNode(TableNode node) {
        this.m_selectedNode = node;
        DLCShopItem item = (DLCShopItem)node.item;
        this.showHideButtons(item);
        int id = DLCShopMenuFilePath.getScreenShotId(this.m_menuType);
        String description = DLCShopManager.getDLCShopManager().UpdatePicture(item.id, id);
        this.SetDetailedString(description);
    }

    private void showHideButtons(DLCShopItem item) {
        if (0L != this.m_purchaseButtonId) {
            menues.SetShowField(this.m_purchaseButtonId, !item.activated);
        }
        if (0L != this.m_purchaseButtonGrayId) {
            menues.SetShowField(this.m_purchaseButtonGrayId, item.activated);
        }
        if (0L != this.m_activateButtonGrayId) {
            menues.SetShowField(this.m_activateButtonGrayId, false);
        }
        if (0L != this.m_activateButtonId) {
            menues.SetShowField(this.m_activateButtonId, true);
        }
        if (0L != this.m_descriptionItemNameEditBoxId) {
            menues.SetFieldText(this.m_descriptionItemNameEditBoxId, item.name);
            menues.UpdateField(menues.ConvertTextFields(this.m_descriptionItemNameEditBoxId));
        }
    }

    public void SetDetailedString(String description) {
        if (description != null) {
            MENU_ranger ranger = (MENU_ranger)menues.ConvertMenuFields(this.m_descriptionScrollerId);
            MENUText_field text = (MENUText_field)menues.ConvertMenuFields(this.m_descriptionBodyEditBoxId);
            if (null != ranger && null != text) {
                if (0L != this.m_descriptionBodyEditBoxId) {
                    menues.SetShowField(this.m_descriptionBodyEditBoxId, true);
                }
                text.text = description;
                menues.UpdateField(text);
                int textHeight = menues.GetTextLineHeight(text.nativePointer);
                int startBase = menues.GetBaseLine(text.nativePointer);
                int lineScreen = Converts.HeightToLines(text.leny, startBase, textHeight);
                int lineCounter = Converts.HeightToLines(menues.GetTextHeight(text.nativePointer, description), startBase, textHeight);
                if (null != this.m_scroller) {
                    this.m_scroller.Deinit();
                }
                this.m_scroller = new TextScroller(this.m_common, ranger, lineCounter, lineScreen, textHeight, startBase, false, "DLC - DESCRIPTION BODY");
                this.m_scroller.AddTextControl(text);
            }
        } else {
            if (this.m_descriptionBodyEditBoxId != 0L) {
                menues.SetShowField(this.m_descriptionBodyEditBoxId, false);
            }
            if (this.m_scroller != null) {
                this.m_scroller.Deinit();
            }
            this.m_scroller = null;
        }
    }

    private void showHideChildren(TableNode node) {
        if (!this.m_isAnimating) {
            this.m_isAnimating = true;
            this.m_counting.Clear();
            this.m_table.Traverse(node, this.m_counting, 0);
            int childrenNum = this.m_counting.GetCount();
            if (0 == childrenNum) {
                this.OnFinishAnimation();
            } else {
                menues.SetIgnoreEvents(this.m_ranger.nativePointer, true);
                this.rotateButtonTree(node, !node.self.showCH, 1.0 / ROLL_UP_DURATION);
                double duration = 1000.0 * ROLL_UP_DURATION / (double)(--childrenNum <= 0 ? 1 : childrenNum);
                this.m_table.ShowHideSubtree(node, duration, this);
            }
        }
        this.m_table.RedrawTable();
    }

    private void rotateButtonTree(TableNode node, boolean direction, double velocity) {
        menues.SetUVRotationOnGroupTree(this.m_common.GetMenu(), node.group, this.m_controls.getControlName(2, node.depth), false, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.m_common.GetMenu(), node.group, this.m_controls.getControlName(2, node.depth), true, 0, velocity, (direction ? 1 : -1) * 90);
        menues.SetUVRotationOnGroupTree(this.m_common.GetMenu(), node.group, this.m_controls.getControlName(2, node.depth), true, 1, velocity, (direction ? 1 : -1) * 90);
    }

    @Override
    public void OnFinishAnimation() {
        this.m_isAnimating = false;
        menues.SetIgnoreEvents(this.m_ranger.nativePointer, false);
    }

    private DLCShopNode convert(Vector<DLCShopManager.Store> stores) {
        DLCShopNode root = new DLCShopNode();
        for (DLCShopManager.Store store : stores) {
            root.children.add(new DLCShopNode(store));
        }
        return root;
    }

    private class ControlsDepository {
        private Vector<HashMap<Integer, String>> vectorMap = new Vector();

        public ControlsDepository() {
            for (int i = 0; i < 8; ++i) {
                this.vectorMap.add(new HashMap());
            }
        }

        private void addName(int controlType, int controlDepth, String controlName) {
            this.vectorMap.get(controlType).put(controlDepth, controlName);
        }

        public String getControlName(int controlType, int controlDepth) {
            return this.vectorMap.get(controlType).get(controlDepth);
        }

        private int encode(int controlType, int controlDepth) {
            return controlDepth * 100000 + controlType;
        }

        private int decodeDepth(int controlId) {
            return controlId / 100000;
        }

        private int decodeType(int controlId) {
            return controlId % 100000;
        }
    }

    class RollUpLinesVisitor
    implements Table.TableVisitor {
        RollUpLinesVisitor() {
        }

        public void VisitNode(TableNode node) {
            if (node.depth == 0) {
                node.self.showCH = false;
            }
            menues.UpdateData(node.self);
        }
    }

    private class CountNodeChildrenVisitor
    implements Table.TableVisitor {
        private int count = 0;

        private CountNodeChildrenVisitor() {
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

    private static class DLCShopNode {
        DLCShopItem item;
        Vector<DLCShopNode> children;

        DLCShopNode() {
            this.children = new Vector();
            this.item = new DLCShopItem();
        }

        DLCShopNode(DLCShopManager.Store store) {
            this.item = new DLCShopItem(store);
            this.children = new Vector();
            for (DLCShopManager.Item item : store.getItems()) {
                this.children.add(new DLCShopNode(item));
            }
        }

        DLCShopNode(DLCShopManager.Item item) {
            this.item = new DLCShopItem(item);
            this.children = new Vector();
        }
    }
}

