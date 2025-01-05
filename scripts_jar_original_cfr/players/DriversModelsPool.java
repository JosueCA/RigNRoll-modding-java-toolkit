/*
 * Decompiled with CFR 0.151.
 */
package players;

import java.util.ArrayList;
import java.util.Iterator;
import menu.JavaEvents;
import players.IdentiteNames;
import players.NickNamesUniqueName;
import rnrcore.SCRuniperson;
import rnrscr.PedestrianManager;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class DriversModelsPool {
    private static final int POOLSIZE = 5;
    private ArrayList<String> cycleListModelNames = new ArrayList();
    private ArrayList<String> pool = new ArrayList();
    private ArrayList<String> exposing = new ArrayList();
    private ArrayList<NickName> freeNickNames = new ArrayList();
    private ArrayList<NickName> bussyNickNames = new ArrayList();
    private NickNamesUniqueName uniqueName = new NickNamesUniqueName();

    private DriversModelsPool(ArrayList<String> model_names) {
        this.cycleListModelNames = (ArrayList)model_names.clone();
    }

    private void fillPool() {
        for (int i = 0; i < 5; ++i) {
            this.addNewModel();
        }
    }

    public ArrayList<String> getPoolNames() {
        return this.pool;
    }

    public void makePoolCycle() {
        int count = 0;
        int min_count = 1000000000;
        int min_index = -1;
        for (String model_name : this.pool) {
            int usaed = 0;
            for (NickName nn : this.bussyNickNames) {
                if (!nn.isIdentitie(model_name)) continue;
                ++usaed;
            }
            if (min_count > usaed) {
                min_count = usaed;
                min_index = count;
            }
            ++count;
        }
        if (min_index >= 0) {
            this.expose(this.pool.get(min_index));
        }
    }

    private void leaveNickName(String nickName) {
        this.uniqueName.leaveNickNameString(nickName);
    }

    private void checkExposed() {
        int i = 0;
        while (i < this.exposing.size()) {
            String exposeModel = this.exposing.get(i);
            int freei = 0;
            while (freei < this.freeNickNames.size()) {
                if (this.freeNickNames.get(freei).exposeByModel(exposeModel)) {
                    NickName leavedNickName = this.freeNickNames.remove(freei);
                    this.leaveNickName(leavedNickName.getNickName());
                    continue;
                }
                ++freei;
            }
            boolean has_exposed = false;
            for (NickName nick_name : this.bussyNickNames) {
                if (!nick_name.isIdentitie(exposeModel)) continue;
                has_exposed = true;
                break;
            }
            if (!has_exposed) {
                this.returnOldModel(exposeModel);
                this.addNewModel();
                this.exposing.remove(i);
                continue;
            }
            ++i;
        }
    }

    private void expose(String model_name) {
        for (int i = 0; i < this.pool.size(); ++i) {
            if (model_name.compareTo(this.pool.get(i)) != 0) continue;
            this.pool.remove(i);
            break;
        }
        this.exposing.add(model_name);
        this.checkExposed();
    }

    private NickName getNickName(String model_name) {
        if (null == model_name) {
            return null;
        }
        int count = 0;
        for (NickName nick_name : this.freeNickNames) {
            if (nick_name.isIdentitie(model_name)) {
                return this.freeNickNames.remove(count);
            }
            ++count;
        }
        return new NickName(this.uniqueName.getNickNameString(), model_name);
    }

    NickName getNickName() {
        NickName res = this.getNickName(this.getModelName());
        if (res != null) {
            this.bussyNickNames.add(res);
        }
        return res;
    }

    void removeNickName(String nick_name) {
        int count = 0;
        for (NickName nn : this.bussyNickNames) {
            if (nn.isNickName(nick_name)) {
                this.freeNickNames.add(this.bussyNickNames.remove(count));
                break;
            }
            ++count;
        }
        this.checkExposed();
    }

    private void addNewModel() {
        if (this.cycleListModelNames.isEmpty()) {
            return;
        }
        String new_model_name = this.cycleListModelNames.get(0);
        NickName try_nick_name = new NickName(this.uniqueName.getNickNameString(), new_model_name);
        if (try_nick_name.isBad()) {
            return;
        }
        this.cycleListModelNames.remove(0);
        this.pool.add(0, new_model_name);
        this.freeNickNames.add(try_nick_name);
    }

    public void nativecallModelUnloaded(String model_name) {
        Iterator<NickName> nick = this.bussyNickNames.iterator();
        while (nick.hasNext()) {
            NickName nick_name = nick.next();
            if (!nick_name.exposeByModel(model_name)) continue;
            nick.remove();
        }
        this.expose(model_name);
    }

    private void returnOldModel(String model_name) {
        this.cycleListModelNames.add(model_name);
        PedestrianManager.getInstance().removeNamedModel(model_name);
    }

    private String getModelName() {
        if (this.pool.isEmpty()) {
            return null;
        }
        String res = this.pool.remove(0);
        this.pool.add(res);
        return res;
    }

    public static DriversModelsPool createAndFill(ArrayList<String> models) {
        DriversModelsPool pool = new DriversModelsPool(models);
        pool.fillPool();
        return pool;
    }

    public static DriversModelsPool create(ArrayList<String> models) {
        return new DriversModelsPool(models);
    }

    private static boolean lock(String model_name, String nick_name) {
        IdentiteNames info = new IdentiteNames(model_name);
        JavaEvents.SendEvent(57, 1, info);
        SCRuniperson person = SCRuniperson.createNamedAmbientPerson(info.modelName, nick_name, info.modelName);
        if (person != null) {
            person.lockPerson();
            person.setAnimationsLoadConsequantly();
            return true;
        }
        return false;
    }

    private static void unlock(String nick_name) {
        SCRuniperson person = SCRuniperson.findModel(nick_name);
        if (person != null) {
            person.unlockPerson();
        }
    }

    public ArrayList<NickName> getBussyNickNames() {
        return this.bussyNickNames;
    }

    public void setBussyNickNames(ArrayList<NickName> value) {
        for (NickName nn : this.bussyNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }
        this.bussyNickNames = value;
    }

    public ArrayList<String> getCycleListModelNames() {
        return this.cycleListModelNames;
    }

    public void setCycleListModelNames(ArrayList<String> cycleListModelNames) {
        this.cycleListModelNames = cycleListModelNames;
    }

    public ArrayList<String> getExposing() {
        return this.exposing;
    }

    public void setExposing(ArrayList<String> exposing) {
        this.exposing = exposing;
    }

    public ArrayList<NickName> getFreeNickNames() {
        return this.freeNickNames;
    }

    public void setFreeNickNames(ArrayList<NickName> value) {
        for (NickName nn : this.freeNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }
        this.freeNickNames = value;
    }

    public ArrayList<String> getPool() {
        return this.pool;
    }

    public void setPool(ArrayList<String> pool) {
        this.pool = pool;
    }

    public NickNamesUniqueName getUniqueName() {
        return this.uniqueName;
    }

    public void setUniqueName(NickNamesUniqueName uniqueName) {
        this.uniqueName = uniqueName;
    }

    public void deinit() {
        for (NickName nn : this.freeNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }
        this.freeNickNames.clear();
        for (NickName nn : this.bussyNickNames) {
            nn.exposeByModel(nn.getIdentitie());
        }
        this.bussyNickNames.clear();
    }

    public static class NickName {
        private String identitie;
        private String name;
        private boolean badModel;

        public NickName(String name, String identitie) {
            this.name = name;
            this.badModel = !this.initwithModel(identitie);
        }

        private boolean initwithModel(String identitie) {
            this.exposeByModel(this.identitie);
            this.identitie = identitie;
            return DriversModelsPool.lock(identitie, this.name);
        }

        private boolean exposeByModel(String identitie) {
            if (this.isIdentitie(identitie)) {
                DriversModelsPool.unlock(this.name);
                this.identitie = null;
                return true;
            }
            return false;
        }

        private boolean isIdentitie(String model_name) {
            if (this.identitie == null) {
                return false;
            }
            return this.identitie.compareTo(model_name) == 0;
        }

        private boolean isNickName(String nick_name) {
            if (this.name == null) {
                return false;
            }
            return this.name.compareTo(nick_name) == 0;
        }

        public boolean isBad() {
            return this.badModel;
        }

        public String getIdentitie() {
            return this.identitie;
        }

        public String getNickName() {
            return this.name;
        }
    }
}

