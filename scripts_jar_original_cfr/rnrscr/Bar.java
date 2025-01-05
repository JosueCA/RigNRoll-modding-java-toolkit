/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import players.CutSceneAuxPersonCreator;
import players.ImodelCreate;
import players.SOPersonModelCreator;
import rnrcore.CoreTime;
import rnrcore.SCRanimparts;
import rnrcore.SCRcamera;
import rnrcore.SCRperson;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscr.AdvancedRandom;
import rnrscr.BarPeopleScene;
import rnrscr.BarTimeZone;
import rnrscr.Chainanm;
import rnrscr.DialogsSet;
import rnrscr.MissionDialogs;
import rnrscr.ModelForBarScene;
import rnrscr.animation;
import rnrscr.cSpecObjects;
import rnrscr.smi.Newspapers;

public class Bar
extends animation {
    boolean useScene = false;
    public static final boolean TEST_BAR_HACK = true;
    private static Bar instance = null;
    private static boolean isCutSceneAmbientBar = false;
    static int NOMTABLEMAMANIMS = 4;
    static int NOMTABLEWOMAMANIMS = 4;
    static int NOMBARSTANDANIMS = 4;
    private static final int MAX_BARSTAND_MODELS = 3;
    static int NOMBARMEN_toShow = 1;
    public static int barType = 1;
    static int MAXPEOPLECOF = 128;
    int PeopleCof = 128;
    private DialogsSet questSet = null;
    private HashMap<String, Long> currentdialogs = new HashMap();
    private int totalModels = 0;
    private int total_Man_Models = 0;
    private int total_Woman_Models = 0;
    private int total_TableWoman_Models = 0;
    private int total_TableMan_Models = 0;
    private int total_BarStand_Models = 0;
    private int total_BarStand_Woman_Models = 0;
    private int total_BarStand_Man_Models = 0;
    private final int total_BarMen_Models = 1;
    private static List<SCRperson> m_pedPersons = new ArrayList<SCRperson>();
    private static List<Long> m_scenes = new ArrayList<Long>();
    static ArrayList AllTablePersons;
    static ArrayList AllBarStandPersons;
    static ArrayList AllBarmenPersons;

    public static Bar getInstance() {
        if (instance == null) {
            instance = new Bar();
        }
        return instance;
    }

    public static void setBarType(int type) {
        barType = type;
    }

    public int getQuestsCount() {
        return this.questSet.getQuestCount();
    }

    private Bar() {
        Newspapers.addBigraceFailure(1, "Simple Race", "race1", "Oxnard", 1, 1, 1, 1);
        cSpecObjects so = new cSpecObjects();
        so.sotip = 8;
        this.StartWorkWithSO(so);
    }

    public static final vectorJ getCurrentBarPosition() {
        return Bar.getInstance().currentOwner.position;
    }

    private void loadModels() {
        BarTimeZone zonner = new BarTimeZone();
        zonner.ProcessZone(eng.gHour(), this.PeopleCof / MAXPEOPLECOF, eng.gMonth() == 7 && eng.gDate() == 14);
        this.total_Man_Models = zonner.GetManCount();
        this.total_Woman_Models = zonner.GetWomanCount();
        if (this.total_Man_Models > 20) {
            this.total_Man_Models = 20;
        }
        if (this.total_Woman_Models > 20) {
            this.total_Woman_Models = 20;
        }
        this.totalModels = this.total_Man_Models + this.total_Woman_Models;
        this.LoadModels(this.total_Man_Models, this.total_Woman_Models);
        this.total_TableWoman_Models = 0;
        this.total_TableMan_Models = 0;
        this.total_BarStand_Woman_Models = 0;
        this.total_BarStand_Man_Models = 0;
        if (null != this.questSet && 0 != this.questSet.getQuestCount()) {
            for (int i = 0; i < this.questSet.getQuestCount(); ++i) {
                SCRuniperson uni_person = this.questSet.getQuest(i).getNpcModel();
                String modelname = eng.GetManPrefix(uni_person.nativePointer);
                boolean man_was_delted = false;
                if (this.removeManModelWithName(modelname)) {
                    man_was_delted = true;
                    --this.totalModels;
                    --this.total_Man_Models;
                }
                if (!this.removeWomanModelWithName(modelname)) continue;
                if (man_was_delted) {
                    eng.err("Dumb error on deleting model name from models. Bar.java. loadModels");
                }
                --this.totalModels;
                --this.total_Woman_Models;
            }
            this.total_TableWoman_Models = this.total_Woman_Models;
            this.total_TableMan_Models = this.total_Man_Models;
            this.total_BarStand_Models = this.questSet.getQuestCount();
        } else {
            this.total_TableWoman_Models = this.total_Woman_Models;
            this.total_TableMan_Models = this.total_Man_Models;
            int num_on_bar_stand = AdvancedRandom.RandFromInreval(0, Math.min(3, this.totalModels));
            if (0 != num_on_bar_stand) {
                this.total_BarStand_Woman_Models = AdvancedRandom.RandFromInreval(0, num_on_bar_stand);
                this.total_BarStand_Man_Models = num_on_bar_stand - this.total_BarStand_Woman_Models;
                this.total_TableWoman_Models -= this.total_BarStand_Woman_Models;
                this.total_TableMan_Models -= this.total_BarStand_Man_Models;
            }
            this.total_BarStand_Models = this.total_BarStand_Woman_Models + this.total_BarStand_Man_Models;
            if (this.total_BarStand_Models != num_on_bar_stand) {
                eng.err("load Models math errorr!!!!");
            }
        }
        this.LoadModelByTag(1, 1);
    }

    public void StartWorkWithSO(cSpecObjects s) {
        super.StartWorkWithSO(s);
        this.leaveBar();
        this.Init(MissionDialogs.queueDialogsForSO(s.sotip, s.position, new CoreTime()));
        this.loadModels();
    }

    public void SetPeopleCof(int Cof) {
        if (Cof >= MAXPEOPLECOF) {
            double deveance = (double)Cof / (double)MAXPEOPLECOF;
            int periodF = (int)Math.floor(deveance);
            this.PeopleCof = Cof - MAXPEOPLECOF * periodF;
        }
    }

    public void PlacePerson(SCRperson PERSONAGE1, vectorJ possit, vectorJ dirrit) {
        PERSONAGE1.SetPos(possit);
        PERSONAGE1.SetDir(dirrit);
    }

    public SCRperson createPersonForBar(String modelName, String animation2, vectorJ animationShift, double timecoef, double animationScale, vectorJ pos, vectorJ dir, boolean has_detail) {
        SCRperson PERSONAGE = has_detail ? SCRperson.CreateAnm(modelName, pos, dir, true) : SCRperson.CreateAnmNoDetailes(modelName, pos, dir, true);
        SCRanimparts ANIM = this.CreateAnmSingle(PERSONAGE, animation2);
        ANIM.PermanentShift(animationShift.x, animationShift.y, animationShift.z);
        ANIM.Tune(animationScale, false);
        ANIM.SetVelocity(0.0);
        ANIM.setTimeNScalePreventRandom(timecoef, animationScale);
        PERSONAGE.AddAnimGroup(ANIM, 700);
        eng.play(PERSONAGE);
        Eventsmain EVENTS = new Eventsmain();
        EVENTS.dotisthin(PERSONAGE);
        this.PlacePerson(PERSONAGE, pos, dir);
        PERSONAGE.ShiftPerson(ANIM.PermanentShift());
        Chainanm ManChain = this.CreateChain(PERSONAGE);
        ManChain.Add(5, 700, ANIM);
        PERSONAGE.StartChain(ManChain);
        PERSONAGE.SetInBarWorld();
        Bar.addPedPerson(PERSONAGE);
        return PERSONAGE;
    }

    public SCRperson createItemForBar(String modelName, String animation2, vectorJ animationShift, double timecoef, double animationScale, vectorJ pos, vectorJ dir) {
        SCRperson PERSONAGE = SCRperson.CreateAnmNoDetailes(modelName, pos, dir, true);
        PERSONAGE.setDependent();
        SCRanimparts ANIM = this.CreateSimpleAnm(PERSONAGE, animation2);
        ANIM.PermanentShift(animationShift.x, animationShift.y, animationShift.z);
        ANIM.Tune(animationScale, false);
        ANIM.SetVelocity(0.0);
        ANIM.setTimeNScalePreventRandom(timecoef, animationScale);
        PERSONAGE.AddAnimGroup(ANIM, 700);
        eng.play(PERSONAGE);
        Eventsmain EVENTS = new Eventsmain();
        EVENTS.dotisthin(PERSONAGE);
        this.PlacePerson(PERSONAGE, pos, dir);
        PERSONAGE.ShiftPerson(ANIM.PermanentShift());
        Chainanm ManChain = this.CreateChain(PERSONAGE);
        ManChain.Add(5, 700, ANIM);
        PERSONAGE.StartChain(ManChain);
        PERSONAGE.SetInBarWorld();
        Bar.addPedPerson(PERSONAGE);
        return PERSONAGE;
    }

    public void testTableScene(matrixJ M, vectorJ P) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();
        vec.add(new SceneActorsPool("model", SCRuniperson.createSOPerson("Man_001", "tableman")));
        vec.add(new SceneActorsPool("plate", SCRuniperson.createSOPerson("Plate1", "plate")));
        vec.add(new SceneActorsPool("fork", SCRuniperson.createSOPerson("Fork1", "fork")));
        scenetrack.CreateSceneXMLPool("table_m1_bar", 5, vec, new Data("", M, P));
    }

    public long createBarScene(vectorJ dir, vectorJ P, String[] ids, String[] modelNames, String scenename) {
        Vector<SceneActorsPool> vec = new Vector<SceneActorsPool>();
        if (ids == null || modelNames == null || ids.length != modelNames.length) {
            eng.err("ids==null || modelNames==null || ids.length!=modelNames.length");
        }
        for (int i = 0; i < ids.length; ++i) {
            vec.add(new SceneActorsPool(ids[i], SCRuniperson.createSOPerson(modelNames[i], "bart_so_model")));
        }
        return scenetrack.CreateSceneXMLPool(scenename, 5, vec, new Data("", new matrixJ(dir, new vectorJ(0.0, 0.0, 1.0)), P));
    }

    public long[] Table(vectorJ[] poss, vectorJ[] dirs) {
        int i;
        if (isCutSceneAmbientBar) {
            ArrayList<vectorJ> goodDirections = new ArrayList<vectorJ>();
            ArrayList<vectorJ> goodPositions = new ArrayList<vectorJ>();
            for (int i2 = 0; i2 < dirs.length; ++i2) {
                vectorJ dir = dirs[i2];
                if (!(dir.x < 0.0) && !(dir.y > 0.0)) continue;
                goodDirections.add(dirs[i2]);
                goodPositions.add(poss[i2]);
            }
            int size = goodDirections.size();
            vectorJ[] goodDirectionsArray = goodDirections.toArray(new vectorJ[goodDirections.size()]);
            vectorJ[] goodPositionsArray = goodPositions.toArray(new vectorJ[goodDirections.size()]);
            ArrayList<ModelForBarScene> models = new ArrayList<ModelForBarScene>();
            for (int i3 = 0; i3 < size; ++i3) {
                boolean maleModel = i3 % 2 == 0;
                models.add(new ModelForBarScene(this.getModelName(0, maleModel), maleModel));
            }
            long[] scenes = BarPeopleScene.createTableScenes(goodPositionsArray, goodDirectionsArray, models);
            Bar.addScenes(scenes);
            return new long[0];
        }
        ArrayList<ModelForBarScene> models = new ArrayList<ModelForBarScene>();
        for (i = 0; i < this.total_TableWoman_Models; ++i) {
            models.add(new ModelForBarScene(this.getModelName(0, false), false));
        }
        for (i = 0; i < this.total_TableMan_Models; ++i) {
            models.add(new ModelForBarScene(this.getModelName(0, true), true));
        }
        long[] scenes = BarPeopleScene.createTableScenes(poss, dirs, models);
        Bar.addScenes(scenes);
        return new long[0];
    }

    public long[] BarStand(vectorJ[] poss, vectorJ[] dirs) {
        boolean withDialogs;
        if (isCutSceneAmbientBar) {
            return new long[0];
        }
        ArrayList<ModelForBarScene> models = new ArrayList<ModelForBarScene>();
        boolean putInDialogs = false;
        String[] dialogNames = null;
        boolean bl = withDialogs = null != this.questSet && 0 != this.questSet.getQuestCount();
        if (!withDialogs) {
            int i;
            for (i = 0; i < this.total_BarStand_Woman_Models; ++i) {
                models.add(new ModelForBarScene(this.getModelName(0, false), false));
            }
            for (i = 0; i < this.total_BarStand_Man_Models; ++i) {
                models.add(new ModelForBarScene(this.getModelName(0, true), true));
            }
        } else {
            dialogNames = new String[this.questSet.getQuestCount()];
            for (int pers = 0; pers < this.questSet.getQuestCount(); ++pers) {
                String modelname;
                putInDialogs = true;
                SCRuniperson uni_person = this.questSet.getQuest(pers).getNpcModel();
                models.add(new ModelForBarScene(modelname, !(modelname = eng.GetManPrefix(uni_person.nativePointer)).contains("Woman")));
                dialogNames[pers] = this.questSet.getQuest(pers).getDescription();
            }
        }
        long[] scenes = BarPeopleScene.createBarStandScenes(poss, dirs, models);
        Bar.addScenes(scenes);
        if (putInDialogs) {
            assert (dialogNames != null && scenes.length == dialogNames.length);
            for (int i = 0; i < scenes.length; ++i) {
                this.currentdialogs.put(dialogNames[i], scenes[i]);
            }
        }
        return new long[0];
    }

    public long[] BarmanStand(vectorJ[] poss, vectorJ[] dirs) {
        ArrayList<ModelForBarScene> models = new ArrayList<ModelForBarScene>();
        for (int i = 0; i < NOMBARMEN_toShow; ++i) {
            models.add(new ModelForBarScene(this.getModelName(1, true), true));
        }
        long[] scenes = BarPeopleScene.createBarmanScenes(poss, dirs, models);
        Bar.addScenes(scenes);
        return new long[0];
    }

    public int[] Shuffle(int sz, int prohids) {
        int[] intArr = new int[sz];
        for (int i0 = 0; i0 < sz; ++i0) {
            intArr[i0] = i0;
        }
        Random rnd_ch = new Random();
        for (int pr = 0; pr < prohids; ++pr) {
            for (int i = 0; i < sz; ++i) {
                int replace = rnd_ch.nextInt(sz - i) + i;
                int prev = intArr[i];
                intArr[i] = intArr[replace];
                intArr[replace] = prev;
            }
        }
        return intArr;
    }

    private static void addPedPerson(SCRperson person) {
        m_pedPersons.add(person);
    }

    private static void addScenes(long[] scene) {
        for (long value : scene) {
            m_scenes.add(value);
        }
    }

    public static void deleteBar() {
        for (SCRperson person : m_pedPersons) {
            person.delete();
        }
        m_pedPersons.clear();
        Iterator<Object> i$ = m_scenes.iterator();
        while (i$.hasNext()) {
            long scene = (Long)i$.next();
            scenetrack.DeleteScene(scene);
            BarPeopleScene.forgetScenePersons(scene);
        }
        m_scenes.clear();
    }

    public void startDialog(String name) {
        if (!this.currentdialogs.containsKey(name)) {
            String message = "startint dialog in bar - has no person for dialog " + name;
            eng.log(message);
            return;
        }
        long scene = this.currentdialogs.get(name);
        scenetrack.UpdateSceneFlags(scene, 256);
        List<SCRuniperson> persons = BarPeopleScene.getScenePersons(scene);
        for (SCRuniperson person : persons) {
            person.stop();
        }
    }

    public void endDialog(String name) {
        if (!this.currentdialogs.containsKey(name)) {
            String message = "ending dialog in bar - has no person for dialog " + name;
            eng.log(message);
            return;
        }
        long scene = this.currentdialogs.get(name);
        scenetrack.UpdateSceneFlags(scene, 1);
    }

    public void leaveBar() {
        this.currentdialogs.clear();
        this.questSet = null;
    }

    public void Init(DialogsSet quests) {
        if (null != quests) {
            this.questSet = quests;
        }
    }

    public void BarCreateCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.BindToBar(eng.GetCurrentBar());
        cam.PlayCamera();
    }

    public void BarCreateCamera_Matrix(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(-1.0, 0.0, 0.0);
        mrot.v1.Set(0.0, -1.0, 0.0);
        mrot.v2.Set(0.0, 0.0, 1.0);
        vectorJ pos = new vectorJ(0.0, 0.0, 0.0);
        cam.BindToMatrix(mrot, pos);
        cam.PlayCamera();
    }

    public void BarDeleteCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.DeleteCamera();
    }

    public void BarStopCamera(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.StopCamera();
    }

    public void deletetask_pers(long tsk) {
        eng.DeleteTASK(tsk);
    }

    public void bar_realesecar(long drv) {
        event.Setevent(1002 + (int)drv);
    }

    public void createanimateddoor_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(1.0, 0.0, 0.0);
        mrot.v1.Set(0.0, 0.0, 1.0);
        mrot.v2.Set(0.0, -1.0, 0.0);
        person.CreateAnimatedSpace_timedependance("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()), "Bar_door_01", "space_MDL-Bar_door_01", "Ivan_In_Bar_001", 0.0, 0L, 0L, mrot, true, false);
    }

    public void createanimateddoor_fromperson_close(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(1.0, 0.0, 0.0);
        mrot.v1.Set(0.0, 0.0, 1.0);
        mrot.v2.Set(0.0, -1.0, 0.0);
        person.CreateAnimatedSpace_timedependance("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()), "Bar_door_01", "space_MDL-Bar_door_01", "", 0.0, 0L, 0L, mrot, true, false);
    }

    public void deleteanimateddoor_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        person.DeleteAnimatedSpace("Space_DoorToBar_" + eng.GetBarInnerName(eng.GetCurrentBar()), "Bar_door_01", 0L);
    }

    public void createanimateddoorinside_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        matrixJ mrot = new matrixJ();
        mrot.v0.Set(1.0, 0.0, 0.0);
        mrot.v1.Set(0.0, 0.0, 1.0);
        mrot.v2.Set(0.0, -1.0, 0.0);
        matrixJ mrot_newspaper = new matrixJ();
        mrot_newspaper.v0.Set(1.0, 0.0, 0.0);
        mrot_newspaper.v1.Set(0.0, 1.0, 0.0);
        mrot_newspaper.v2.Set(0.0, 0.0, 1.0);
        person.CreateAnimatedSpace_timedependance("Space_DoorBar01", "Bar_door_02", "space_MDL-Bar_door_01", "Ivan_In_Bar_002", 0.0, 0L, 0L, mrot, true, false);
        person.CreateAnimatedSpace_timedependance("Space_newspaper", "newspaper", "space_MDL-newspaper", "Ivan_In_Bar_002", 0.0, 0L, 0L, mrot_newspaper, true, true);
    }

    public void deleteanimateddoorinside_fromperson(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        person.DeleteAnimatedSpace("Space_DoorBar01", "Bar_door_02", 0L);
        person.DeleteAnimatedSpace("Space_newspaper", "newspaper", 0L);
    }

    public void PlacePersonInBar(long man) {
        SCRuniperson person = new SCRuniperson();
        person.nativePointer = man;
        person.SetInBarWorld();
    }

    public void PlaceCameraInBar(long camp) {
        SCRcamera cam = SCRcamera.CreateCamera_fake(camp);
        cam.SetInBarWorld();
    }

    public long playMenuCamera() {
        return scenetrack.CreateSceneXML("barin menu camera " + barType, 5, null);
    }

    void barfreecam() {
        SCRcamera freeCam = SCRcamera.CreateCamera("free");
        freeCam.PlayCamera();
        freeCam.SetInBarWorld();
        vectorJ possit = new vectorJ(2.0, 2.0, 1.0);
        freeCam.SetCameraPosition(possit);
    }

    public static void setCutSceneAmbientBar(boolean value) {
        isCutSceneAmbientBar = value;
    }

    public static ImodelCreate getModelCreator() {
        if (isCutSceneAmbientBar) {
            return new CutSceneAuxPersonCreator();
        }
        return new SOPersonModelCreator();
    }

    static class Data {
        String model;
        matrixJ M;
        vectorJ P;

        Data(String model, matrixJ M, vectorJ P) {
            this.model = model;
            this.M = M;
            this.P = P;
        }
    }

    public class Eventsmain {
        public void dotisthin(SCRperson PERSONAGE) {
            PERSONAGE.AttachToEvent(8);
        }
    }

    public class CreateAnimation {
        private String getBarmanModelName(int nom) {
            ArrayList<String> names = Bar.this.getModelNames(1);
            if (nom >= names.size()) {
                double deveance = (double)nom / (double)names.size();
                int periodF = (int)Math.floor(deveance);
                nom -= names.size() * periodF;
            }
            String modelname = names.get(nom);
            return modelname;
        }

        public void barmenScene(int nom, vectorJ pos, vectorJ dir) {
            String modelname = this.getBarmanModelName(nom);
            SCRuniperson _barmen = SCRuniperson.createNamedSOPerson(modelname, "barmen", "barmen");
            SCRuniperson barmenCloth = SCRuniperson.createNamedSOPerson("BarmenCloth", "barmenCloth", "barmenCloth");
            SCRuniperson barmenWine = SCRuniperson.createNamedSOPerson("Wine1", "Wine1", "Wine1");
            Vector<SceneActorsPool> v = new Vector<SceneActorsPool>(3);
            v.add(new SceneActorsPool("barmen", _barmen));
            v.add(new SceneActorsPool("barmenCloth", barmenCloth));
            v.add(new SceneActorsPool("barmenWine", barmenWine));
            matrixJ matr = new matrixJ(dir, new vectorJ(0.0, 0.0, 1.0));
            long scene = scenetrack.CreateSceneXMLPool("barmen", 5, v, new preset(matr, pos));
            scenetrack.moveSceneTime(scene, AdvancedRandom.getRandomDouble() * 15.0);
            Bar.addScenes(new long[]{scene});
        }

        public void barmen(int nom, vectorJ pos, vectorJ dir) {
            String modelname = this.getBarmanModelName(nom);
            SCRperson PERSONAGE = null;
            vectorJ[] shifts = new vectorJ[]{new vectorJ(0.0, 0.0, 0.0), new vectorJ(0.0, 0.0, 0.0)};
            double[] scale = new double[]{1.2, 1.2};
            String[] anmNames = new String[]{"barmen", "barmen_second_pers"};
            String[][] addItems = new String[][]{{"BarmenCloth", "Wine1"}, {"BarmenCloth", "Wine1"}};
            String[][] addAnims = new String[][]{{"BarmenCloth", "dishesBarman_M1"}, {"BarmenCloth", "dishesBarman_M1"}};
            double timecoef = AdvancedRandom.getRandomDouble();
            PERSONAGE = Bar.this.createPersonForBar(modelname, anmNames[nom], shifts[nom], timecoef, scale[nom], pos, dir, false);
            for (int i = 0; i < addAnims[nom].length; ++i) {
                SCRperson addItem = Bar.this.createItemForBar(addItems[nom][i], addAnims[nom][i], shifts[nom], timecoef, scale[nom], pos, dir);
                PERSONAGE.addDependent(addItem);
            }
        }

        public SCRperson barstand(int nom, boolean isMAN, String modelname, vectorJ pos, vectorJ dir) {
            SCRperson PERSONAGE = null;
            vectorJ[] shifts = new vectorJ[]{new vectorJ(0.0, 0.12, 0.0), new vectorJ(0.0, 0.12, 0.0), new vectorJ(0.0, 0.14, 0.0), new vectorJ(-0.03, 0.06, 0.0)};
            double[] scale = new double[]{1.2, 1.2, 1.2, 1.2};
            String[] anmNames = new String[]{"Man_inBar005", "Man_inBar006", "W_inbar005", "W_inbar006"};
            String[][] addItems = new String[][]{{"GlassSmall"}, {"GlassSmall"}, new String[0], new String[0]};
            String[][] addAnims = new String[][]{{"dishesBar_M05"}, {"dishesBar_M06"}, new String[0], new String[0]};
            if (nom >= 2) {
                double deveance = (double)nom / 2.0;
                int periodF = (int)Math.floor(deveance);
                nom -= 2 * periodF;
            }
            if (!isMAN) {
                nom += 2;
            }
            double timecoef = AdvancedRandom.getRandomDouble();
            PERSONAGE = Bar.this.createPersonForBar(modelname, anmNames[nom], shifts[nom], timecoef, scale[nom], pos, dir, true);
            for (int i = 0; i < addAnims[nom].length; ++i) {
                SCRperson addItem = Bar.this.createItemForBar(addItems[nom][i], addAnims[nom][i], shifts[nom], timecoef, scale[nom], pos, dir);
                PERSONAGE.addDependent(addItem);
            }
            return PERSONAGE;
        }

        class preset {
            public vectorJ P;
            public matrixJ M;

            preset(matrixJ M, vectorJ P) {
                this.M = M;
                this.P = P;
            }
        }
    }

    static class Presets {
        String newspaper;
        String bardoor;

        Presets() {
        }
    }
}

