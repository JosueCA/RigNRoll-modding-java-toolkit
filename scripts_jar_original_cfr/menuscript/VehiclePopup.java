/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import gameobj.CarInfo;
import menu.Common;
import menu.ListenerManager;
import menu.MENUTruckview;
import menu.MENUsimplebutton_field;
import menu.SelectCb;
import menu.TruckView;
import menu.menues;

public class VehiclePopup
implements SelectCb {
    long m_windowp;
    TruckView m_truckview;
    boolean m_bWorking = false;
    CarInfo m_car;
    String m_windowname;
    String m_xmlfilename;
    Common common;

    public VehiclePopup(Common common, String windowname, String xmlfilename, String controlgroup) {
        this.common = common;
        this.m_windowname = windowname;
        this.m_xmlfilename = xmlfilename;
        this.m_windowp = menues.InitXmlControl(common.GetMenu(), xmlfilename, controlgroup, windowname);
        long control = menues.FindFieldInMenu_ByParent(common.GetMenu(), this.m_windowname, "FIELD - TruckView");
        this.m_truckview = new TruckView((MENUTruckview)menues.ConvertMenuFields(control));
        control = menues.FindFieldInMenu_ByParent(common.GetMenu(), this.m_windowname, "CLOSE Window");
        MENUsimplebutton_field b = menues.ConvertSimpleButton(control);
        menues.SetScriptOnControl(common.GetMenu(), b, this, "OnClose", 4L);
        ListenerManager.AddListener(104, this);
    }

    protected void AfterInitMenu() {
        menues.SetShowField(this.m_windowp, false);
    }

    public void SetCarInfo(CarInfo car) {
        this.m_car = car;
    }

    public void SetShowCallback(MENUsimplebutton_field button) {
        menues.SetScriptOnControl(this.common.GetMenu(), button, this, "ShowCallback", 4L);
    }

    public void Show() {
        this.AttachCarInfo(this.m_car);
        if (this.m_bWorking) {
            menues.SetShowField(this.m_windowp, true);
        }
    }

    public void ShowCallback(long _menu, MENUsimplebutton_field button) {
        this.Show();
    }

    void AttachCarInfo(CarInfo car) {
    }

    public void OnClose(long _menu, MENUsimplebutton_field button) {
        menues.SetShowField(this.m_windowp, false);
    }

    public void OnSelect(int state, Object sender) {
        if (state == 104) {
            this.AfterInitMenu();
        }
    }
}

