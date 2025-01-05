/*
 * Decompiled with CFR 0.151.
 */
package menu;

import java.util.Vector;
import menu.KeyPair;
import menu.MENUText_field;
import menu.MacroKit;
import menu.menues;
import menuscript.Converts;
import rnrcore.EventsHolder;
import rnrcore.IEventListener;

public class BalanceUpdater
implements IEventListener {
    static int m_iPlayerBalance = 0;
    static int m_iCompanyBalance = 0;
    Vector<balanceControl> controls = new Vector();
    Vector<balanceControl> company_controls = new Vector();
    private static BalanceUpdater singleton = null;

    private boolean remove(long control) {
        if (control == 0L) {
            return false;
        }
        for (int i = 0; i < this.controls.size(); ++i) {
            if (this.controls.elementAt((int)i).control != control) continue;
            this.controls.remove(i);
            return true;
        }
        return false;
    }

    private boolean removeOffice(long control) {
        if (control == 0L) {
            return false;
        }
        for (int i = 0; i < this.company_controls.size(); ++i) {
            if (this.company_controls.elementAt((int)i).control != control) continue;
            this.company_controls.remove(i);
            return true;
        }
        return false;
    }

    private boolean add(long control) {
        if (control == 0L) {
            return false;
        }
        for (int i = 0; i < this.controls.size(); ++i) {
            if (this.controls.elementAt((int)i).control != control) continue;
            return false;
        }
        MENUText_field field = menues.ConvertTextFields(control);
        KeyPair[] keys = new KeyPair[]{new KeyPair("SIGN", m_iPlayerBalance >= 0 ? "" : "-"), new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)))};
        balanceControl new_control = new balanceControl();
        new_control.text = field.text;
        new_control.control = control;
        field.text = MacroKit.Parse(field.text, keys);
        menues.UpdateField(field);
        this.controls.add(new_control);
        return true;
    }

    private boolean addOffice(long control) {
        if (control == 0L) {
            return false;
        }
        for (int i = 0; i < this.company_controls.size(); ++i) {
            if (this.company_controls.elementAt((int)i).control != control) continue;
            return false;
        }
        MENUText_field field = menues.ConvertTextFields(control);
        KeyPair[] keys = new KeyPair[]{new KeyPair("SIGN", m_iPlayerBalance >= 0 ? "" : "-"), new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)))};
        balanceControl new_control = new balanceControl();
        new_control.text = field.text;
        new_control.control = control;
        field.text = MacroKit.Parse(field.text, keys);
        menues.UpdateField(field);
        this.company_controls.add(new_control);
        return true;
    }

    public void on_event(int value) {
        KeyPair[] keys;
        MENUText_field field;
        int i;
        for (i = 0; i < this.controls.size(); ++i) {
            field = menues.ConvertTextFields(this.controls.elementAt((int)i).control);
            keys = new KeyPair[]{new KeyPair("SIGN", m_iPlayerBalance >= 0 ? "" : "-"), new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iPlayerBalance)))};
            field.text = MacroKit.Parse(this.controls.elementAt((int)i).text, keys);
            menues.UpdateField(field);
        }
        for (i = 0; i < this.company_controls.size(); ++i) {
            field = menues.ConvertTextFields(this.company_controls.elementAt((int)i).control);
            keys = new KeyPair[]{new KeyPair("SIGN", m_iCompanyBalance >= 0 ? "" : "-"), new KeyPair("MONEY", "" + Converts.ConvertNumeric(Math.abs(m_iCompanyBalance)))};
            field.text = MacroKit.Parse(this.company_controls.elementAt((int)i).text, keys);
            menues.UpdateField(field);
        }
    }

    private BalanceUpdater() {
        EventsHolder.addEventListenet(81, this);
    }

    private int get() {
        return m_iPlayerBalance;
    }

    private static BalanceUpdater gHolder() {
        if (singleton == null) {
            singleton = new BalanceUpdater();
        }
        return singleton;
    }

    public static boolean AddBalanceControl(long control) {
        return BalanceUpdater.gHolder().add(control);
    }

    public static boolean RemoveBalanceControl(long control) {
        return BalanceUpdater.gHolder().remove(control);
    }

    public static boolean AddCompanyBalanceControl(long control) {
        return BalanceUpdater.gHolder().addOffice(control);
    }

    public static boolean RemoveCompanyBalanceControl(long control) {
        return BalanceUpdater.gHolder().removeOffice(control);
    }

    public static int GetBalance() {
        return BalanceUpdater.gHolder().get();
    }

    public static void SetBalance(int v) {
        m_iPlayerBalance = v;
    }

    public static void SetCompanyBalance(int v) {
        m_iCompanyBalance = v;
    }

    class balanceControl {
        long control = 0L;
        String text = null;

        balanceControl() {
        }
    }
}

