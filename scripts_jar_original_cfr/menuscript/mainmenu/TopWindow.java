/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import menuscript.IQuitMenu;

public class TopWindow {
    private static IQuitMenu menuOnTop = null;

    public static final void Register(IQuitMenu menu) {
        menuOnTop = menu;
    }

    public static final void UnRegister(IQuitMenu menu) {
        if (menuOnTop == menu) {
            menuOnTop = null;
        }
    }

    public static final void quitTopMenu() {
        if (null != menuOnTop) {
            menuOnTop.quitMenu();
        }
    }
}

