/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import menu.menucreation;
import menu.menues;

public class GasBannerMenu
implements menucreation {
    private static final String XML = "..\\data\\config\\menu\\menu_gasstation.xml";
    private static final String GROUP = "GAS_PRICE_TABLO";
    private static final String PRICE_TEXT = "GAS_PRICE_TABLO - VALUE";
    private static long text_field = 0L;

    public void AfterInitMenu(long _menu) {
        menues.WindowSet_ShowCursor(_menu, false);
        text_field = menues.FindFieldInMenu(_menu, PRICE_TEXT);
        menues.setMenuID(_menu, 220);
        menues.setShowMenu(_menu, false);
    }

    public void InitMenu(long _menu) {
        menues.InitXml(_menu, XML, GROUP);
    }

    private static String FormatPriceShort(int _price) {
        int i_price = (int)Math.floor((double)_price / 100.0);
        int m_price = _price - i_price * 100;
        String i_ret = "" + i_price;
        String m_ret = m_price >= 10 ? "." + m_price : ".0" + m_price;
        return i_ret + m_ret;
    }

    public static void SetValue(int price) {
        if (text_field != 0L) {
            menues.SetFieldText(text_field, GasBannerMenu.FormatPriceShort(price));
            menues.UpdateMenuField(menues.ConvertMenuFields(text_field));
        }
    }

    public void exitMenu(long _menu) {
    }

    public void restartMenu(long _menu) {
    }

    public String getMenuId() {
        return "gasBannerMENU";
    }

    public static long CreateGasBannerMenu() {
        return menues.createSimpleMenu(new GasBannerMenu(), 1.0E8, "", 1600, 1200, 270, 270, 0, 0, "..\\Data\\Menu\\Cursors\\cursor_01.tga", 0, 0);
    }
}

