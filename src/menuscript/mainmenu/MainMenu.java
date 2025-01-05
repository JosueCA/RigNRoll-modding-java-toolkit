/*
 * Decompiled with CFR 0.151.
 */
package menuscript.mainmenu;

import java.util.HashMap;
import menu.menues;
import menuscript.mainmenu.IFadeOutFadeIn;

public class MainMenu {
    String XML_FILE = "..\\data\\config\\menu\\";
    protected final long animid_FADEOUT = 1L;
    protected final long animid_FADEIN = 2L;
    protected final int animid_SLIDER = 3;
    protected final int animid_FOLD = 4;
    protected final int animid_UNFOLD = 5;
    protected final long animid_FADEOUTHEAD = 6L;
    protected final long animid_FADEINHEAD = 7L;
    final long animid_FADEOUTDIALOG = 8L;
    final long animid_FADEINDIALOG = 9L;
    public float PERIODFADEOUT = 0.2f;
    public float PERIODFADEIN = 0.4f;
    public float PERIODFOLD = 0.2f;
    public float PERIODUNFOLD = 0.4f;
    protected long _menu = 0L;
    protected HashMap<String, String> panelDialogStates = new HashMap();

    MainMenu(String file) {
        this.XML_FILE = this.XML_FILE + file;
    }

    public long[] loadGroup(String groupname) {
        return menues.InitXml(this._menu, this.XML_FILE, groupname);
    }

    public long findField(String fieldName) {
        return menues.FindFieldInMenu(this._menu, fieldName);
    }

    public void OnDialogOpen(IFadeOutFadeIn cb) {
    }

    public void OnDialogOpenImmediate() {
    }

    public void OnDialogClose(IFadeOutFadeIn cb) {
    }

    void resetToDefaulScreen() {
    }

    public void rememberPanelState(String panelname, String dialogname) {
        this.panelDialogStates.put(panelname, dialogname);
    }

    public void forgetPanelState(String panelname) {
        this.panelDialogStates.remove(panelname);
    }
}

