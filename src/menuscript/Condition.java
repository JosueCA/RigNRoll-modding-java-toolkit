/*
 * Decompiled with CFR 0.151.
 */
package menuscript;

import gameobj.CarInfo;
import menu.Common;
import menu.CondTable;
import menuscript.PartsCameraTrigger;
import menuscript.VehiclePopup;

public class Condition
extends VehiclePopup {
    CondTable m_CondTable;
    PartsCameraTrigger m_PartsTrigger;

    public Condition(Common common, String windowname, String xmlfilename, String controlgroup) {
        super(common, windowname, xmlfilename, controlgroup);
    }

    public void AttachConditionTable(String controlgroup) {
        this.m_CondTable = new CondTable(this.common, "Condition Table");
        for (int i = 1; i <= 3; ++i) {
            this.m_CondTable.AddControl(0, i - 1, "VALUES - " + i + "level - BUTTONbrowse");
            this.m_CondTable.AddControl(2, i - 1, "VALUES - " + i + " level - Text Glow");
            this.m_CondTable.AddControl(1, i - 1, "VALUES - " + i + " level - Text");
            this.m_CondTable.AddControl(5, i - 1, "VALUES - " + i + "level - BUTTONconditionRED");
            this.m_CondTable.AddControl(4, i - 1, "VALUES - " + i + "level - BUTTONconditionYELLOW");
            this.m_CondTable.AddControl(3, i - 1, "VALUES - " + i + "level - BUTTONconditionGREEN");
        }
        this.m_PartsTrigger = new PartsCameraTrigger(null, this.m_truckview);
        this.m_CondTable.Setup(38, 7, this.m_xmlfilename, controlgroup, this.m_windowname, this.m_PartsTrigger);
        this.m_CondTable.AttachRanger(this.common.FindScrollerByParent("Tableranger - Condition", this.m_windowname));
    }

    protected void AfterInitMenu() {
        super.AfterInitMenu();
        this.m_truckview.BindRepairVehicle();
        this.m_truckview.InitMaterialSwitches();
        this.m_truckview.InitCameraSwitches();
    }

    void AttachCarInfo(CarInfo car) {
        boolean bl = this.m_bWorking = car != null;
        if (!this.m_bWorking) {
            return;
        }
        if (this.m_CondTable != null) {
            car.parts.FillCondTable(this.m_CondTable);
            this.m_CondTable.RefillTree();
        }
        this.m_truckview.AttachCarInfo(car.parts);
    }
}

