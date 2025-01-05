/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.CrewNamesManager;
import players.ICarCreationController;
import players.actorveh;
import rnrcore.eng;

public class LiveCarCreationController
implements ICarCreationController {
    private actorveh liveCar = null;

    public actorveh onCarCreate(int playerType, int playerId) {
        this.liveCar = new actorveh();
        this.liveCar.setAi_player(playerId);
        CrewNamesManager.createMainCharacter();
        CrewNamesManager.getMainCharacterPlayer().beDriverOfCar(this.liveCar);
        return this.liveCar;
    }

    public void onCarDelete(int playerType, int playerId) {
        if (null != this.liveCar) {
            if (playerId == this.liveCar.getAi_player()) {
                CrewNamesManager.deinitMainCharacter(this.liveCar);
                this.liveCar = null;
            } else {
                eng.log("ERRORR. onCarDelete for LiveCarCreationController fails.");
            }
        }
    }

    public actorveh getLiveCar() {
        return this.liveCar;
    }

    public void setLiveCar(actorveh liveCar) {
        this.liveCar = liveCar;
    }
}

