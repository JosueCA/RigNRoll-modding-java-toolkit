/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import java.util.ArrayList;
import java.util.Iterator;
import menu.BaseMenu;
import menu.MENUbutton_field;
import menu.MENUsimplebutton_field;
import menu.SelectCb;
import menu.menues;
import menu.xmlcontrols;
import menuscript.ISubMenu;
import menuscript.mainmenu.PanelDialog;

public abstract class ParentMenu
extends BaseMenu
implements SelectCb,
ISubMenu {
    private static final String BUTTACTION = "OnButton";
    private static final String PREPAREBUTTSWITCH = "OnButtonSwitch";
    private static final String TABACTION = "OnTab";
    private static final String PREPARETABSWITCH = "OnTabSwitch";
    protected ArrayList<Long> tabs = new ArrayList();
    protected ArrayList<PanelDialog> settings = new ArrayList();
    protected ArrayList<Long> buttons = new ArrayList();
    protected ArrayList<ISubMenu> m_menus_buttons = new ArrayList();
    private int currentTab = 0;
    private int currentButton = -1;
    public boolean buttonPending = false;
    private boolean tabPending = false;
    private int pendingTab = 0;
    private int pendingButton = 0;
    private boolean f_menu_exited = false;
    private static int LASTSETTINGS = 0;
    private boolean f_saveBetweenTabs = false;
    private boolean f_afterInitPassed = false;
    int LASTSETTINGS_CONFIRMING = 0;

    public void setSaveBetweenTabs() {
        this.f_saveBetweenTabs = true;
    }

    abstract void NeedToConfirm(String var1);

    public void freezeTab(int ID) {
        if (ID >= this.tabs.size()) {
            return;
        }
        menues.SetBlindess(this.tabs.get(ID), true);
        menues.SetIgnoreEvents(this.tabs.get(ID), true);
    }

    public void unFreezeTab(int ID) {
        if (ID >= this.tabs.size()) {
            return;
        }
        menues.SetBlindess(this.tabs.get(ID), false);
        menues.SetIgnoreEvents(this.tabs.get(ID), false);
    }

    public void freezeButton(int ID) {
        if (ID >= this.buttons.size()) {
            return;
        }
        menues.SetBlindess(this.buttons.get(ID), true);
        menues.SetIgnoreEvents(this.buttons.get(ID), true);
    }

    public void unFreezeButton(int ID) {
        if (ID >= this.buttons.size()) {
            return;
        }
        menues.SetBlindess(this.buttons.get(ID), false);
        menues.SetIgnoreEvents(this.buttons.get(ID), false);
    }

    public int addButton(MENUbutton_field button, ISubMenu menu) {
        int size = this.buttons.size();
        this.buttons.add(button.nativePointer);
        this.m_menus_buttons.add(menu);
        if (menu != null) {
            menu.SetParent(this);
        }
        menues.SetScriptOnControl(this.uiTools.GetMenu(), button, this, "OnButton", 10L);
        menues.SetScriptOnControl(this.uiTools.GetMenu(), button, this, "OnButtonSwitch", 18L);
        button.userid = size;
        menues.UpdateMenuField(button);
        return size;
    }

    public int AddTab(long _menu, xmlcontrols.MENUCustomStuff tab) {
        int ID = this.tabs.size();
        this.tabs.add(tab.nativePointer);
        tab.userid = ID;
        menues.UpdateMenuField(tab);
        menues.SetScriptOnControl(_menu, tab, this, "OnTab", 10L);
        menues.SetScriptOnControl(_menu, tab, this, "OnTabSwitch", 18L);
        return ID;
    }

    protected void deselectCurrentButton() {
        for (int i = 0; i < this.buttons.size(); ++i) {
            long control = this.buttons.get(i);
            menues.setControlCanOperate(control, true);
            menues.SetFieldState(control, 0);
        }
        menues.setfocuscontrolonmenu(this.uiTools.GetMenu(), this.buttons.get(this.currentButton));
    }

    protected void selectButton(int nom) {
        for (int i = 0; i < this.buttons.size(); ++i) {
            long control = this.buttons.get(i);
            menues.setControlCanOperate(control, true);
            menues.SetFieldState(control, i == nom ? 1 : 0);
        }
    }

    protected void onSelectButton(int nom) {
        this.currentButton = nom;
        for (int i = 0; i < this.buttons.size(); ++i) {
            long control = this.buttons.get(i);
            menues.setControlCanOperate(control, true);
            if (i == nom) continue;
            menues.SetFieldState(control, 0);
        }
    }

    public void initSubMenues() {
        for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
            ISubMenu menu = this.m_menus_buttons.get(i);
            if (menu == null) continue;
            menu.InitMenu(this.uiTools.GetMenu());
        }
    }

    public void afterInitSubMenues() {
        this.f_afterInitPassed = true;
        for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
            ISubMenu menu = this.m_menus_buttons.get(i);
            if (menu == null) continue;
            menu.afterInit();
        }
    }

    public void RefreshTabs() {
        for (int i = 0; i < this.m_menus_buttons.size(); ++i) {
            ISubMenu menu = this.m_menus_buttons.get(i);
            if (menu == null) continue;
            menu.Refresh();
        }
    }

    public void OnButtonSwitch(long _menu, MENUbutton_field butt) {
        if (!this.f_afterInitPassed) {
            return;
        }
        if (butt.userid == this.currentButton) {
            menues.setControlCanOperate(butt.nativePointer, false);
            return;
        }
        if (this.currentButton != -1) {
            ISubMenu submenu = this.m_menus_buttons.get(this.currentButton);
            if (submenu != null) {
                int result = submenu.LeaveSubMenu();
                boolean bl = this.buttonPending = result != 0;
                if (this.buttonPending) {
                    this.pendingButton = butt.userid;
                }
                switch (result) {
                    case 1: {
                        this.NeedToConfirm(null);
                        break;
                    }
                    case 2: {
                        this.NeedToConfirm(submenu.GetCustomText());
                    }
                }
                if (result == 0) {
                    this.onSelectButton(butt.userid);
                } else {
                    menues.setControlCanOperate(butt.nativePointer, false);
                }
            } else {
                this.onSelectButton(butt.userid);
            }
        } else {
            this.onSelectButton(butt.userid);
        }
    }

    public void OnButton(long _menu, MENUbutton_field butt) {
        if (!this.f_afterInitPassed) {
            return;
        }
        if (this.currentButton == -1) {
            this.currentButton = butt.userid;
        }
        if (!this.buttonPending) {
            ISubMenu menu = this.m_menus_buttons.get(this.currentButton);
            if (menu != null) {
                menu.Activate();
            }
        } else {
            this.selectButton(this.currentButton);
        }
    }

    public void OnTabSwitch(long _menu, xmlcontrols.MENUCustomStuff tab) {
        if (!this.f_afterInitPassed) {
            return;
        }
        if (LASTSETTINGS == tab.userid) {
            return;
        }
        if (this.currentTab != -1) {
            if (this.f_saveBetweenTabs) {
                int result = this.LeaveSubMenuSettingsTab();
                boolean bl = this.tabPending = result != 0;
                if (this.tabPending) {
                    this.pendingTab = tab.userid;
                }
                switch (result) {
                    case 1: {
                        this.NeedToConfirm(null);
                        break;
                    }
                    case 2: {
                        this.NeedToConfirm(this.GetCustomText());
                    }
                }
                menues.setControlCanOperate(tab.nativePointer, result == 0);
            }
        } else {
            menues.setControlCanOperate(tab.nativePointer, true);
        }
    }

    public void OnTab(long _menu, xmlcontrols.MENUCustomStuff tab) {
        if (!this.f_afterInitPassed) {
            return;
        }
        if (!this.tabPending) {
            this.currentTab = tab.userid;
            LASTSETTINGS = tab.userid;
            if (this.currentTab < this.settings.size()) {
                this.settings.get(this.currentTab).update();
            }
        }
    }

    void PassUserDecision(int type) {
        if (this.buttonPending) {
            ISubMenu menu = this.m_menus_buttons.get(this.currentButton);
            menu.UserDecisionMenuSwitching(type);
            if (this.f_menu_exited) {
                return;
            }
            if (type == 2) {
                this.pendingButton = -1;
                this.buttonPending = false;
            } else {
                this.activatePendingButton();
            }
        } else if (this.tabPending) {
            this.UserDecisionTabSwitching(type);
            if (this.f_menu_exited) {
                return;
            }
            if (type == 2) {
                this.pendingTab = -1;
                this.tabPending = false;
            } else {
                this.activatePendingTab();
            }
        }
    }

    protected void activateTab(int tab) {
        menues.SetFieldState(this.tabs.get(tab), 1);
    }

    private void activatePendingButton() {
        this.buttonPending = false;
        this.currentButton = this.pendingButton;
        this.pendingButton = -1;
        this.selectButton(this.currentButton);
        ISubMenu menu = this.m_menus_buttons.get(this.currentButton);
        if (menu != null) {
            menu.Activate();
        }
    }

    private void activatePendingTab() {
        LASTSETTINGS = this.currentTab = this.pendingTab;
        menues.setControlCanOperate(this.tabs.get(this.currentTab), true);
        menues.SetFieldState(this.tabs.get(this.currentTab), 1);
        if (this.currentTab < this.settings.size()) {
            this.settings.get(this.currentTab).update();
        }
        this.pendingTab = -1;
        this.tabPending = false;
    }

    public void OnSelect(int state, Object sender) {
    }

    public void exitMenu() {
        this.f_menu_exited = true;
        for (ISubMenu menu : this.m_menus_buttons) {
            if (menu == null) continue;
            menu.exitMenu();
        }
        Iterator<PanelDialog> iter = this.settings.iterator();
        while (iter.hasNext()) {
            ((PanelDialog)iter.next()).exitMenu();
        }
        this.m_menus_buttons = null;
        this.settings = null;
    }

    void show() {
    }

    void hide() {
    }

    public void Activate() {
        this.show();
        this.Refresh();
    }

    public int LeaveSubMenu() {
        if (!this.settings.isEmpty() && this.settings.get(LASTSETTINGS).areValuesChanged()) {
            this.LASTSETTINGS_CONFIRMING = LASTSETTINGS;
            return 1;
        }
        this.hide();
        return 0;
    }

    public int LeaveSubMenuSettingsTab() {
        if (this.settings.get(LASTSETTINGS).areValuesChanged()) {
            this.LASTSETTINGS_CONFIRMING = LASTSETTINGS;
            return 1;
        }
        return 0;
    }

    public void UserDecisionMenuSwitching(int type) {
        if (LASTSETTINGS < this.settings.size()) {
            switch (type) {
                case 0: {
                    this.settings.get(this.LASTSETTINGS_CONFIRMING).OnOk(this.uiTools.GetMenu(), null);
                    this.hide();
                    break;
                }
                case 1: {
                    this.settings.get(this.LASTSETTINGS_CONFIRMING).OnExit(this.uiTools.GetMenu(), (MENUsimplebutton_field)null);
                    this.hide();
                }
            }
        }
    }

    public void UserDecisionTabSwitching(int type) {
        if (LASTSETTINGS < this.settings.size()) {
            switch (type) {
                case 0: {
                    this.settings.get(this.LASTSETTINGS_CONFIRMING).OnOk(this.uiTools.GetMenu(), null);
                    break;
                }
                case 1: {
                    this.settings.get(this.LASTSETTINGS_CONFIRMING).OnExit(this.uiTools.GetMenu(), (MENUsimplebutton_field)null);
                }
            }
        }
    }

    public void afterInit() {
        this.hide();
        this.afterInitSubMenues();
        Iterator<PanelDialog> iter = this.settings.iterator();
        while (iter.hasNext()) {
            iter.next().afterInit();
        }
    }

    public void Refresh() {
        Iterator<PanelDialog> iter = this.settings.iterator();
        while (iter.hasNext()) {
            iter.next().update();
        }
    }

    public String GetCustomText() {
        return null;
    }

    public void SetParent(ParentMenu parent) {
    }

    protected void makeNonActiveState() {
        if (-1 != this.currentButton) {
            this.deselectCurrentButton();
        }
        this.currentButton = -1;
    }
}

