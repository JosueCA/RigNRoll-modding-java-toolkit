/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menuscript.ParentMenu;

public interface ISubMenu {
    public static final int OK = 0;
    public static final int NEEDCONFIRM = 1;
    public static final int NEEDCONFIRM_TEXT = 2;
    public static final int NEEDCONFIRM_CUSTOM = 3;
    public static final int YES = 0;
    public static final int NO = 1;
    public static final int CANCEL = 2;

    public int LeaveSubMenu();

    public int LeaveSubMenuSettingsTab();

    public void UserDecisionMenuSwitching(int var1);

    public void UserDecisionTabSwitching(int var1);

    public void Refresh();

    public void Activate();

    public void afterInit();

    public void exitMenu();

    public void InitMenu(long var1);

    public String GetCustomText();

    public void SetParent(ParentMenu var1);
}

