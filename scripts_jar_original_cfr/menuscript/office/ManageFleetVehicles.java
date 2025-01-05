/*
 * Decompiled with CFR 0.151.
 */
package menuscript.office;

import gameobj.CarInfo;
import java.util.HashMap;
import menu.JavaEvents;
import menuscript.office.ManageFlitManager;

public class ManageFleetVehicles {
    private HashMap<ManageFlitManager.VehicleId, CarInfo> cars = new HashMap();
    private ManageFlitManager.VehicleId examained = null;

    public ManageFlitManager.VehicleId getExamained() {
        return this.examained;
    }

    public void add(ManageFlitManager.VehicleId id) {
        this.examained = id;
        if (id.id != 0) {
            JavaEvents.SendEvent(38, 0, this);
        }
    }

    public void add(CarInfo info) {
        this.cars.put(this.examained, info);
    }

    public CarInfo get(ManageFlitManager.VehicleId id) {
        return this.cars.get(id);
    }

    public void clear() {
        this.cars.clear();
    }
}

