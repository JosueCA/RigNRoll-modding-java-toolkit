/*
 * Decompiled with CFR 0.151.
 */
package gameobj;

import gameobj.CarParts;
import menu.JavaEvents;

public class CarInfo {
    public long handle = 0L;
    public boolean vehicleWasActualized = false;
    public boolean cabineLoad = false;
    public String gamemodelname;
    public CarParts parts = new CarParts();

    public native void LoadVehicleModel();

    public native boolean IsVehicleLoaded();

    public native void UnloadVehicle();

    public native long GetVehiclePointer();

    public void LoadVehicleCabine() {
        if (!this.cabineLoad) {
            JavaEvents.SendEvent(71, 3, this);
        }
    }
}

