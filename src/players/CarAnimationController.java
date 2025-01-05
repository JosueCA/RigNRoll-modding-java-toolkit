/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import players.DriversModelsPool;
import players.ICarCreationController;
import players.actorveh;
import players.aiplayer;
import rnrconfig.GetTruckersIdentities;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CarAnimationController
implements ICarCreationController {
    private ArrayList<TypeCarPair> nonloadedCars = new ArrayList();
    private ArrayList<TypeCarPair> loadedCars = new ArrayList();
    private HashMap<actorveh, String> assignedAnimations = new HashMap();
    private DriversModelsPool pool = null;

    private TypeCarPair removeFromList(int playerId, List<TypeCarPair> list) {
        Iterator<TypeCarPair> iter = list.iterator();
        while (iter.hasNext()) {
            TypeCarPair item = iter.next();
            if (item.car.getAi_player() != playerId) continue;
            iter.remove();
            return item;
        }
        assert (false);
        return null;
    }

    private actorveh findPlayerIdForNickName(String nickName) {
        Set<Map.Entry<actorveh, String>> set = this.assignedAnimations.entrySet();
        for (Map.Entry<actorveh, String> entry : set) {
            if (nickName.compareTo(entry.getValue()) != 0) continue;
            return entry.getKey();
        }
        return null;
    }

    private boolean createplayerForCar(actorveh car) {
        DriversModelsPool.NickName nick_name = this.getPoolInitialize().getNickName();
        if (nick_name == null) {
            return false;
        }
        aiplayer pl = aiplayer.getAmbientAiplayer(nick_name.getIdentitie(), nick_name.getNickName());
        pl.beDriverOfCar(car);
        this.assignedAnimations.put(car, nick_name.getNickName());
        return true;
    }

    private DriversModelsPool getPoolInitialize() {
        if (null == this.pool) {
            GetTruckersIdentities identities = new GetTruckersIdentities();
            this.pool = DriversModelsPool.createAndFill(identities.get());
        }
        return this.pool;
    }

    // @Override
    public actorveh onCarCreate(int playerType, int playerId) {
        actorveh car = new actorveh();
        car.setAi_player(playerId);
        if (!this.createplayerForCar(car)) {
            this.nonloadedCars.add(new TypeCarPair(playerType, car));
        } else {
            this.loadedCars.add(new TypeCarPair(playerType, car));
        }
        return car;
    }

    // @Override
    public void onCarDelete(int playerType, int playerId) {
        Set<Map.Entry<actorveh, String>> set = this.assignedAnimations.entrySet();
        for (Map.Entry<actorveh, String> entry : set) {
            if (entry.getKey().getAi_player() != playerId) continue;
            this.getPoolInitialize().removeNickName(entry.getValue());
            this.removeFromList(playerId, this.loadedCars);
            this.assignedAnimations.remove(entry.getKey());
            return;
        }
        this.removeFromList(playerId, this.nonloadedCars);
    }

    public void modelUnloaded(String nickName) {
        this.getPoolInitialize().nativecallModelUnloaded(nickName);
        actorveh car = this.findPlayerIdForNickName(nickName);
        if (null != car) {
            TypeCarPair friedPair = this.removeFromList(car.getAi_player(), this.loadedCars);
            this.nonloadedCars.add(friedPair);
        }
    }

    public void renewState() {
        int i = 0;
        while (i < this.nonloadedCars.size()) {
            TypeCarPair item = this.nonloadedCars.get(i);
            if (this.createplayerForCar(item.car)) {
                this.nonloadedCars.remove(i);
                this.loadedCars.add(item);
                continue;
            }
            ++i;
        }
    }

    public ArrayList<String> getPoolNames() {
        return this.getPoolInitialize().getPoolNames();
    }

    public HashMap<actorveh, String> getAssignedAnimations() {
        return this.assignedAnimations;
    }

    public void setAssignedAnimations(HashMap<actorveh, String> assignedAnimations) {
        this.assignedAnimations = assignedAnimations;
    }

    public ArrayList<TypeCarPair> getLoadedCars() {
        return this.loadedCars;
    }

    public void setLoadedCars(ArrayList<TypeCarPair> loadedCars) {
        this.loadedCars = loadedCars;
    }

    public ArrayList<TypeCarPair> getNonloadedCars() {
        return this.nonloadedCars;
    }

    public void setNonloadedCars(ArrayList<TypeCarPair> nonloadedCars) {
        this.nonloadedCars = nonloadedCars;
    }

    public void setPool(DriversModelsPool pool) {
        this.pool = pool;
    }

    public DriversModelsPool getPool() {
        return this.getPoolInitialize();
    }

    public void deinit() {
        if (this.pool != null) {
            this.pool.deinit();
        }
    }

    public static class TypeCarPair {
        actorveh car;
        int typePlayer;

        public TypeCarPair(int typePlayer, actorveh car) {
            this.car = car;
            this.typePlayer = typePlayer;
        }

        public actorveh getCar() {
            return this.car;
        }

        public int getTypePlayer() {
            return this.typePlayer;
        }
    }
}

