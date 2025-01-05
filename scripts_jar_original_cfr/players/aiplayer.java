/*
 * Decompiled with CFR 0.151.
 */
package players;

import players.AmbientPlayer;
import players.CBContact;
import players.CrewNamesManager;
import players.CutSceneAmbientPersonCreator;
import players.ImodelCreate;
import players.MissionCutScenePlayer;
import players.NamedScenarioPersonPassanger;
import players.Passport;
import players.RefferensedCreator;
import players.ScenarioPersonPassanger;
import players.actorveh;
import rnrcore.IScriptRef;
import rnrcore.IXMLSerializable;
import rnrcore.SCRuniperson;
import rnrcore.ScriptRef;
import rnrcore.ScriptRefStorage;
import xmlserialization.AIPlayerSerializator;

public class aiplayer
implements IScriptRef {
    private Passport passport = null;
    private ImodelCreate modelCreator;
    private String idForModelCreator;
    private boolean modelbasedmodel = false;
    private boolean poolbasedmodel = false;
    private ScriptRef uid = null;
    private String poolref_name;

    public static aiplayer createScriptRef(String identitie, int uId) {
        IScriptRef scriptRef = ScriptRefStorage.getRefference(uId);
        if (scriptRef == null) {
            return new aiplayer(identitie, uId);
        }
        assert (scriptRef instanceof aiplayer);
        return (aiplayer)scriptRef;
    }

    public aiplayer(String identitie) {
        this.passport = new Passport(identitie);
        this.uid = new ScriptRef();
    }

    private aiplayer(String identitie, int uidValue) {
        this.passport = new Passport(identitie);
        this.uid = new ScriptRef();
        this.uid.register(uidValue, this);
    }

    public aiplayer() {
        this.passport = new Passport(null);
        this.uid = new ScriptRef();
    }

    public static aiplayer getScenarioAiplayer(String nameRef) {
        return CrewNamesManager.getInstance().gPlayer(nameRef);
    }

    public static aiplayer getRefferencedAiplayer(String identitie) {
        aiplayer res = new aiplayer(identitie);
        RefferensedCreator creator = new RefferensedCreator();
        res.setModelCreator(creator, null);
        return res;
    }

    public static aiplayer getNamedAiplayerHiPoly(String identitie, String name) {
        aiplayer res = new aiplayer(identitie);
        MissionCutScenePlayer creator = new MissionCutScenePlayer();
        res.setModelCreator(creator, null);
        res.sPoolBased(name);
        return res;
    }

    public static aiplayer getSimpleAiplayer(String identitie) {
        aiplayer res = new aiplayer(identitie);
        ScenarioPersonPassanger creator = new ScenarioPersonPassanger();
        res.setModelCreator(creator, identitie);
        return res;
    }

    public static aiplayer getAmbientAiplayer(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        AmbientPlayer creator = new AmbientPlayer();
        res.setModelCreator(creator, identitie);
        res.sPoolBased(poolrefName);
        return res;
    }

    public static aiplayer getNamedAiplayerNormal(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        NamedScenarioPersonPassanger creator = new NamedScenarioPersonPassanger();
        res.setModelCreator(creator, identitie);
        res.sPoolBased(poolrefName);
        return res;
    }

    public static aiplayer getCutSceneAmbientPerson(String identitie, String poolrefName) {
        aiplayer res = new aiplayer(identitie);
        CutSceneAmbientPersonCreator creator = new CutSceneAmbientPersonCreator();
        res.setModelCreator(creator, identitie);
        return res;
    }

    public void setModelCreator(ImodelCreate modelCreator, String id) {
        this.modelCreator = modelCreator;
        this.idForModelCreator = id;
    }

    public String getIdForModelCreator() {
        return this.idForModelCreator;
    }

    public ImodelCreate getModelCreator() {
        return this.modelCreator;
    }

    public void sModelBased(boolean val) {
        this.modelbasedmodel = val;
    }

    public boolean getModelBased() {
        return this.modelbasedmodel;
    }

    public void sPoolBased(String name) {
        this.poolbasedmodel = true;
        this.poolref_name = name;
    }

    public boolean getPoolBased() {
        return this.poolbasedmodel;
    }

    public String getPoolRefName() {
        return this.poolref_name;
    }

    public void setUid(int value) {
        this.uid.setUid(value);
    }

    public int getUid() {
        return this.uid.getUid(this);
    }

    public void removeRef() {
        this.uid.removeRef(this);
    }

    public void updateNative(int p) {
    }

    public String gModelname() {
        return this.passport.modelName;
    }

    public String gFirstName() {
        return this.passport.firstName;
    }

    public String gLastName() {
        return this.passport.lastName;
    }

    public String gNickName() {
        return this.passport.nickName;
    }

    public String gPoolRefName() {
        return this.modelbasedmodel ? this.passport.modelName : (this.poolbasedmodel ? this.poolref_name : this.gNickName());
    }

    public SCRuniperson getModel() {
        return this.modelCreator.create(this.passport.modelName, this.gPoolRefName(), this.idForModelCreator);
    }

    public String getIdentitie() {
        return this.passport.getM_identitie();
    }

    public CBContact createCBContacter() {
        return new CBContact(this.passport.modelName, this.passport.firstName, this.passport.lastName, this.passport.nickName);
    }

    public void beDriverOfCar(actorveh car) {
        car.takeDriver(this);
    }

    public void abondoneCar(actorveh car) {
        car.abandoneCar(this);
    }

    public void bePassangerOfCar(actorveh car) {
        car.takePassanger(this);
    }

    public void bePackOfCar(actorveh car) {
        car.takePack(this);
    }

    public void bePassangerOfCar_simple(actorveh car) {
        car.takePassanger_simple(this);
    }

    public void bePassangerOfCar_simple_like(actorveh car, String prefix) {
        car.takePassanger_simple_like(this, prefix);
    }

    public IXMLSerializable getXmlSerializator() {
        return new AIPlayerSerializator(this);
    }

    public void delete() {
        this.removeRef();
    }
}

