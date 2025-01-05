/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import players.actorveh;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class MappingCars {
    private HashMap<String, actorveh> mappedCars = new HashMap();

    public actorveh getMappedCar(String name) {
        actorveh car = this.mappedCars.get(name);
        return car;
    }

    public void addMappedCar(String name, actorveh car) {
        this.mappedCars.put(name, car);
    }

    public void removeMappedCar(String name) {
        this.mappedCars.remove(name);
    }

    public void removeMappedCar(int playerId) {
        Set<Map.Entry<String, actorveh>> set = this.mappedCars.entrySet();
        Iterator<Map.Entry<String, actorveh>> iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, actorveh> entry = iter.next();
            if (entry.getValue().getAi_player() != playerId) continue;
            iter.remove();
            return;
        }
    }

    public HashMap<String, actorveh> getMappedCars() {
        return this.mappedCars;
    }

    public void setMappedCars(HashMap<String, actorveh> mappedCars) {
        this.mappedCars = mappedCars;
    }
}

