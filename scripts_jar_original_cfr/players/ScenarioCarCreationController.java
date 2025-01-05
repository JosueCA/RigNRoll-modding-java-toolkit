/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.ArrayList;
import java.util.Iterator;
import players.ICarCreationController;
import players.actorveh;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ScenarioCarCreationController
implements ICarCreationController {
    ArrayList<actorveh> cars = new ArrayList();

    @Override
    public actorveh onCarCreate(int playerType, int playerId) {
        actorveh car = new actorveh();
        car.setAi_player(playerId);
        this.cars.add(car);
        return car;
    }

    @Override
    public void onCarDelete(int playerType, int playerId) {
        Iterator<actorveh> iter = this.cars.iterator();
        while (iter.hasNext()) {
            actorveh car = iter.next();
            if (playerId != car.getAi_player()) continue;
            iter.remove();
            return;
        }
    }

    public ArrayList<actorveh> getCars() {
        return this.cars;
    }

    public void setCars(ArrayList<actorveh> cars) {
        this.cars = cars;
    }
}

