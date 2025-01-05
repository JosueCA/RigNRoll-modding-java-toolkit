/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.JavaEvents;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.MenuAfterInitNarrator;
import menu.menucreation;
import menu.menues;
import players.IdentiteNames;
import rnrcore.eng;

public class BillOfLadingMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_documents.xml";
    private static final String GROUP = "DOCUMENT - BILL OF LADING";

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
    }

    public void AfterInitMenu(long _menu) {
        MENUText_field field;
        long control = menues.FindFieldInMenu(_menu, "DOCUMENT - Bill RIGHT - NAME VALUE");
        if (control != 0L && (field = menues.ConvertTextFields(control)) != null) {
            IdentiteNames info = new IdentiteNames("SC_FBI");
            if (!eng.noNative) {
                JavaEvents.SendEvent(57, 1, info);
            }
            if (info.firstName != null && info.lastName != null) {
                KeyPair[] key = new KeyPair[]{new KeyPair("STEVEN_CUNNING", info.firstName + " " + info.lastName)};
                MacroKit.ApplyToTextfield(field, key);
            }
        }
        MenuAfterInitNarrator.justShow(_menu);
    }

    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        return "scenarioBillOfLadingMENU";
    }

    public static long create() {
        return menues.createSimpleMenu((menucreation)new BillOfLadingMenu(), 1);
    }
}

