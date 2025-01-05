/*
 * Decompiled with CFR 0.151.
 */
package menu;

import gameobj.CarInfo;
import gameobj.CarParts;
import java.util.HashMap;
import java.util.Vector;
import menu.MENUTruckview;
import rnrcore.SCRuniperson;

public class TruckView {
    MENUTruckview m_truckview;
    public static final int VIEW_CONDITION = 10;
    public static final int VIEW_UNLOADED = -1;
    int state = -1;
    private Vector m_Switches;
    private Vector m_Cameras;

    public TruckView(MENUTruckview control) {
        this.m_truckview = control;
        this.m_Switches = new Vector();
        this.m_Cameras = new Vector();
    }

    public void AttachCarInfo(CarParts parts) {
        for (int i = 0; i < this.m_Switches.size(); ++i) {
            SwitchInfo info = (SwitchInfo)this.m_Switches.get(i);
            int state = parts.GetItem((int)info.partsid).condition;
            this.m_truckview.SetState(1, info.switchid, state);
        }
    }

    public void BindRepairVehicle() {
        if (this.state == 10) {
            return;
        }
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }
        this.m_truckview.Bind3DModel("model_Damage_Truck", 1, 2);
        this.state = 10;
    }

    public void BindNodePerson(SCRuniperson person, String name, int type, int flags) {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }
        this.m_truckview.BindPerson(0L, person, name, type, flags);
        this.state = type;
    }

    public void UnbindVehicle() {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }
        this.state = -1;
    }

    public void BindVehicle(CarInfo car, int type, int flags) {
        if (this.state != -1) {
            this.m_truckview.UnBind3DModel();
        }
        this.m_truckview.BindVehicle(car.GetVehiclePointer(), type, flags);
        this.state = type;
    }

    public void InitMaterialSwitches() {
        int i;
        HashMap<Integer, Integer> switches = new HashMap<Integer, Integer>();
        switches.put(97, this.m_truckview.AddSwitch("mat_Engine_110", 1));
        switches.put(98, this.m_truckview.AddSwitch("mat_CoolingSystem_110", 1));
        switches.put(99, this.m_truckview.AddSwitch("mat_SteeringSystem_110", 1));
        switches.put(100, this.m_truckview.AddSwitch("mat_BrakingSystem_110", 1));
        switches.put(105, this.m_truckview.AddSwitch("mat_FuelSystem_110", 1));
        switches.put(104, this.m_truckview.AddSwitch("mat_TiresRims_110", 1));
        switches.put(101, this.m_truckview.AddSwitch("mat_DriveTrain_110", 1));
        switches.put(108, this.m_truckview.AddSwitch("mat_BodyFrame_110", 1));
        switches.put(106, this.m_truckview.AddSwitch("mat_ExhaustSystem_110", 1));
        switches.put(103, this.m_truckview.AddSwitch("mat_Suspension_110", 1));
        switches.put(102, this.m_truckview.AddSwitch("mat_ElectricalSystem_110", 1));
        switches.put(107, this.m_truckview.AddSwitch("mat_CouplingSystem_110", 1));
        switches.put(107, this.m_truckview.AddSwitch("mat_CouplingSystem_110", 1));
        if (switches.containsKey(new Integer(97))) {
            for (i = 0; i <= 9; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(97)));
            }
            this.m_Switches.add(new SwitchInfo(97, (Integer)switches.get(97)));
        }
        if (switches.containsKey(new Integer(98))) {
            for (i = 10; i <= 15; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(98)));
            }
            this.m_Switches.add(new SwitchInfo(98, (Integer)switches.get(98)));
        }
        if (switches.containsKey(new Integer(99))) {
            for (i = 16; i <= 21; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(99)));
            }
            this.m_Switches.add(new SwitchInfo(99, (Integer)switches.get(99)));
        }
        if (switches.containsKey(new Integer(100))) {
            for (i = 22; i <= 31; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(100)));
            }
            this.m_Switches.add(new SwitchInfo(100, (Integer)switches.get(100)));
        }
        if (switches.containsKey(new Integer(101))) {
            for (i = 32; i <= 38; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(101)));
            }
            this.m_Switches.add(new SwitchInfo(101, (Integer)switches.get(101)));
        }
        if (switches.containsKey(new Integer(102))) {
            for (i = 39; i <= 45; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(102)));
            }
            this.m_Switches.add(new SwitchInfo(102, (Integer)switches.get(102)));
        }
        if (switches.containsKey(new Integer(103))) {
            for (i = 46; i <= 52; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(103)));
            }
            this.m_Switches.add(new SwitchInfo(103, (Integer)switches.get(103)));
        }
        if (switches.containsKey(new Integer(104))) {
            for (i = 53; i <= 68; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(104)));
            }
            this.m_Switches.add(new SwitchInfo(104, (Integer)switches.get(104)));
        }
        if (switches.containsKey(new Integer(105))) {
            for (i = 69; i <= 76; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(105)));
            }
            this.m_Switches.add(new SwitchInfo(105, (Integer)switches.get(105)));
        }
        if (switches.containsKey(new Integer(106))) {
            for (i = 77; i <= 80; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(106)));
            }
            this.m_Switches.add(new SwitchInfo(106, (Integer)switches.get(106)));
        }
        if (switches.containsKey(new Integer(107))) {
            for (i = 81; i <= 83; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(107)));
            }
            this.m_Switches.add(new SwitchInfo(107, (Integer)switches.get(107)));
        }
        if (switches.containsKey(new Integer(107))) {
            for (i = 81; i <= 83; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(107)));
            }
            this.m_Switches.add(new SwitchInfo(107, (Integer)switches.get(107)));
        }
        if (switches.containsKey(new Integer(108))) {
            for (i = 84; i <= 86; ++i) {
                this.m_Switches.add(new SwitchInfo(i, (Integer)switches.get(108)));
            }
            this.m_Switches.add(new SwitchInfo(108, (Integer)switches.get(108)));
        }
    }

    public void InitCameraSwitches() {
        int i;
        HashMap<Integer, Integer> cameras = new HashMap<Integer, Integer>();
        cameras.put(96, this.m_truckview.AddSwitch("Camera_Vehicle", 2));
        cameras.put(97, this.m_truckview.AddSwitch("CameraEngine", 2));
        cameras.put(98, this.m_truckview.AddSwitch("CameraCoolingSystem", 2));
        cameras.put(107, this.m_truckview.AddSwitch("CameraCouplingSystem", 2));
        cameras.put(102, this.m_truckview.AddSwitch("CameraElectricalSystem", 2));
        cameras.put(106, this.m_truckview.AddSwitch("CameraExhaustSystem", 2));
        cameras.put(103, this.m_truckview.AddSwitch("CameraSuspension", 2));
        cameras.put(104, this.m_truckview.AddSwitch("CameraTiresRims", 2));
        cameras.put(101, this.m_truckview.AddSwitch("CameraDriveTrain", 2));
        cameras.put(108, this.m_truckview.AddSwitch("CameraBodyFrame", 2));
        cameras.put(99, this.m_truckview.AddSwitch("CameraSteeringSystem", 2));
        cameras.put(100, this.m_truckview.AddSwitch("CameraBrakingSystem", 2));
        cameras.put(105, this.m_truckview.AddSwitch("CameraFuelSystem", 2));
        this.m_Cameras.add(new SwitchInfo(96, (Integer)cameras.get(96)));
        if (cameras.containsKey(new Integer(97))) {
            this.m_Cameras.add(new SwitchInfo(97, (Integer)cameras.get(97)));
            for (i = 0; i <= 9; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(97)));
            }
        }
        if (cameras.containsKey(new Integer(98))) {
            this.m_Cameras.add(new SwitchInfo(98, (Integer)cameras.get(98)));
            for (i = 10; i <= 15; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(98)));
            }
        }
        if (cameras.containsKey(new Integer(99))) {
            this.m_Cameras.add(new SwitchInfo(99, (Integer)cameras.get(99)));
            for (i = 16; i <= 21; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(99)));
            }
        }
        if (cameras.containsKey(new Integer(100))) {
            this.m_Cameras.add(new SwitchInfo(100, (Integer)cameras.get(100)));
            for (i = 22; i <= 31; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(100)));
            }
        }
        if (cameras.containsKey(new Integer(101))) {
            this.m_Cameras.add(new SwitchInfo(101, (Integer)cameras.get(101)));
            for (i = 32; i <= 38; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(101)));
            }
        }
        if (cameras.containsKey(new Integer(102))) {
            this.m_Cameras.add(new SwitchInfo(102, (Integer)cameras.get(102)));
            for (i = 39; i <= 45; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(102)));
            }
        }
        if (cameras.containsKey(new Integer(103))) {
            this.m_Cameras.add(new SwitchInfo(103, (Integer)cameras.get(103)));
            for (i = 46; i <= 52; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(103)));
            }
        }
        if (cameras.containsKey(new Integer(104))) {
            this.m_Cameras.add(new SwitchInfo(104, (Integer)cameras.get(104)));
            for (i = 53; i <= 68; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(104)));
            }
        }
        if (cameras.containsKey(new Integer(105))) {
            this.m_Cameras.add(new SwitchInfo(105, (Integer)cameras.get(105)));
            for (i = 69; i <= 76; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(105)));
            }
        }
        if (cameras.containsKey(new Integer(106))) {
            this.m_Cameras.add(new SwitchInfo(106, (Integer)cameras.get(106)));
            for (i = 77; i <= 80; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(106)));
            }
        }
        if (cameras.containsKey(new Integer(107))) {
            this.m_Cameras.add(new SwitchInfo(107, (Integer)cameras.get(107)));
            for (i = 81; i <= 83; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(107)));
            }
        }
        if (cameras.containsKey(new Integer(107))) {
            this.m_Cameras.add(new SwitchInfo(107, (Integer)cameras.get(107)));
            for (i = 81; i <= 83; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(107)));
            }
        }
        if (cameras.containsKey(new Integer(108))) {
            this.m_Cameras.add(new SwitchInfo(108, (Integer)cameras.get(108)));
            for (i = 84; i <= 86; ++i) {
                this.m_Cameras.add(new SwitchInfo(i, (Integer)cameras.get(108)));
            }
        }
    }

    public void SetCameraSwitch(int partsid) {
        for (int i = 0; i < this.m_Cameras.size(); ++i) {
            SwitchInfo info = (SwitchInfo)this.m_Cameras.get(i);
            if (info.partsid != partsid) continue;
            this.m_truckview.SetState(2, info.switchid, 0);
            break;
        }
        boolean bFoundMaterial = false;
        for (int i = 0; i < this.m_Switches.size(); ++i) {
            SwitchInfo info = (SwitchInfo)this.m_Switches.get(i);
            if (info.partsid != partsid) continue;
            this.m_truckview.SetState(1, info.switchid, 3);
            bFoundMaterial = true;
            break;
        }
        if (!bFoundMaterial) {
            this.m_truckview.SetState(1, -1, 3);
        }
    }

    private class SwitchInfo {
        int partsid;
        int switchid;

        SwitchInfo(int _partsid, int _switchid) {
            this.partsid = _partsid;
            this.switchid = _switchid;
        }
    }
}

