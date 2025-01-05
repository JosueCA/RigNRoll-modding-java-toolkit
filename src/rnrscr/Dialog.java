/*
 * Decompiled with CFR 0.151.
 */
package rnrscr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import menuscript.AnswerMenu;
import menuscript.BarMenu;
import players.Crew;
import players.actorveh;
import players.aiplayer;
import rickroll.log.RickRollLog;
import rnrcore.SCRuniperson;
import rnrcore.SceneActorsPool;
import rnrcore.TypicalAnm;
import rnrcore.eng;
import rnrcore.event;
import rnrcore.loc;
import rnrcore.matrixJ;
import rnrcore.scenetrack;
import rnrcore.vectorJ;
import rnrscenario.missions.MissionSystemInitializer;
import rnrscenario.missions.map.Place;
import rnrscr.Bar;
import rnrscr.Helper;
import rnrscr.IDialogListener;
import rnrscr.ILeaveDialog;
import rnrscr.MissionDialogs;
import rnrscr.SceneClipRandomizer;
import rnrscr.parkingplace;
import xmlutils.Node;
import xmlutils.NodeList;
import xmlutils.XmlUtils;

public class Dialog {
    private static final int YES_ANSWER = 0;
    private static final int NO_ANSWER = 1;
    private static final String[] BAR_ENVIRONMENT = new String[]{"XXXNicABar", "XXXManBar", "XXXWomanBar"};
    private static final String[] CAR_ENVIRONMENT = new String[]{"XXXNicACar", "pass_man", "pass_woman"};
    private static final SceneClipRandomizer[] SIMPLE_POINT_ENVIRONMENT = new SceneClipRandomizer[]{new SceneClipRandomizer("meet_client_MC_to_man", new SceneClipRandomizer.Clips[]{new SceneClipRandomizer.Clips("CamS_MeetClient", new String[]{"CamS_to_Ivan_near_Clip", "CamS_to_Ivan_far_Clip"}), new SceneClipRandomizer.Clips("IVAN_NEW", new String[]{"IVAN_MEW_MeetClient_listen_2_Clip", "IVAN_MEW_MeetClient_listen_hand_Clip", "IVAN_MEW_MeetClient_talk_Clip"})}), new SceneClipRandomizer("meet_client_MC_to_woman", new SceneClipRandomizer.Clips[]{new SceneClipRandomizer.Clips("CamS_MeetClient", new String[]{"CamS_to_Ivan_near_Clip", "CamS_to_Ivan_far_Clip"}), new SceneClipRandomizer.Clips("IVAN_NEW", new String[]{"IVAN_MEW_MeetClient_listen_2_Clip", "IVAN_MEW_MeetClient_listen_hand_Clip", "IVAN_MEW_MeetClient_talk_Clip"}), new SceneClipRandomizer.Clips("Woman", new String[]{"Woman_bsg_listen_idle_plot_Clip"})}), new SceneClipRandomizer("meet_client_man_to_MC", new SceneClipRandomizer.Clips[]{new SceneClipRandomizer.Clips("CamS_MeetClient", new String[]{"CamS_to_NPC_far_Clip", "CamS_to_NPC_near_Clip"}), new SceneClipRandomizer.Clips("IVAN_NEW", new String[]{"IVAN_MEW_MeetClient_listen_2_Clip", "IVAN_MEW_MeetClient_listen_hand_Clip"}), new SceneClipRandomizer.Clips("Man", new String[]{"MAN_MeetClient_talk_Clip", "MAN_MeetClient_talk_with_shlders_Clip", "MAN_MeetClient_listen_Clip"})}), new SceneClipRandomizer("meet_client_woman_to_MC", new SceneClipRandomizer.Clips[]{new SceneClipRandomizer.Clips("CamS_MeetClient", new String[]{"CamS_to_NPC_far_Clip", "CamS_to_NPC_near_Clip"}), new SceneClipRandomizer.Clips("IVAN_NEW", new String[]{"IVAN_MEW_MeetClient_listen_2_Clip", "IVAN_MEW_MeetClient_listen_hand_Clip"}), new SceneClipRandomizer.Clips("Woman", new String[]{"Woman_bsg_talk_plot_Clip", "Woman_bsg_listen_idle_plot_Clip"})})};
    private static final int SCENE_MC_MAN = 0;
    private static final int SCENE_MC_WOMAN = 1;
    private static final int SCENE_MAN_MC = 2;
    private static final int SCENE_WOMAN_MC = 3;
    private static final String FADE_BAR = "justfadebar";
    private static final String FADE_CAR = "justfade";
    private static final String EVENT_START_CARDIALOG = "start car dialog scene in MC car";
    private static final String EVENT_END_CARDIALOG = "end car dialog scene in MC car";
    private static final String EVENT_START_DIALOG = "start dialog scene in MC car";
    private static final String EVENT_END_DIALOG = "end dialog scene in MC car";
    private String[] ENVIRONMENT = null;
    private String FADE = null;
    private String identitie;
    private matrixJ M = null;
    private vectorJ P = null;
    private long cycleScene = 0L;
    private boolean simplePointScene = false;
    private static final double DISTANCE_TO_PARKING = 200.0;
    private static final double DISTANCE_TO_MISSION_POINT = 50.0;
    private String name = "";
    private BunchPhrases phrases = new BunchPhrases();
    private BunchPhrases current_flame = null;
    private SCRuniperson person_caller = null;
    private SCRuniperson person_callee = null;
    private static final String[] methNames = new String[]{"nextPhrase", "dialogended", "makeAnswerMenu"};
    private static final int scenefalg = 17;
    private static final int scenecyclefalg = 3;
    private static final int fadefalg = 17;
    public String nowarnings0;
    public actorveh nowarnings1;
    public matrixJ nowarnings2;
    public vectorJ nowarnings3;
    private actorveh car;
    private ILeaveDialog iLeave = null;
    private int index = 0;
    private static final String TOP_NODE = "dialogs";
    private static final String DIALOG_NODE = "dialog";
    private static final String PHRASE_NODE = "item";
    private static final String NAME_ATTR = "name";
    private static final String CALLER_ATTR = "caller";
    private static final String CALLEE_ATTR = "callee";
    private static final String WAV_ATTR = "wav";
    private static final String QUESTION_NODE = "question";
    private static final String QUESTION_ID_ATTR = "id";
    private static final String QUESTION_TEXT_ATTR = "text";
    private static final int LIVE = 0;
    private static final int MAN = 1;
    private static final int WOMAN = 2;
    private static HashMap<String, Dialog> storage = new HashMap();

    private void sayAppear() {
        MissionDialogs.sayAppear(this.name);
    }

    private void sayYes() {
        MissionDialogs.sayYes(this.name);
    }

    private void sayNo() {
        MissionDialogs.sayNo(this.name);
    }

    private void sayEndDialog() {
        MissionDialogs.sayEnd(this.name);
    }

    public boolean hasFinish() {
        return MissionDialogs.hasFinish(this.name);
    }

    public static final Dialog getDialog(String name) {
        boolean hasDialog = storage.containsKey(name);
        if (!hasDialog) {
            return null;
        }
        Dialog dialog = storage.get(name);
        assert (dialog != null);
        return new Dialog(dialog);
    }

    public final void start_car_leave_car_on_end(aiplayer npc, SCRuniperson callee, String identitie) {
        this.iLeave = new LeaveDialogAndLeaveCar(npc);
        this.car = Crew.getIgrokCar();
        npc.bePassangerOfCar(this.car);
        this.car.registerCar("IvanCar");
        this.sayAppear();
        this.person_callee = callee;
        this.person_caller = npc.getModel();
        this.ENVIRONMENT = CAR_ENVIRONMENT;
        this.FADE = FADE_CAR;
        this.identitie = identitie;
        this.current_flame = this.phrases;
        this.index = 0;
        this.fade();
    }

    public final void start_bar(SCRuniperson caller, SCRuniperson callee, String identitie) {
        this.iLeave = new SimpleLeave();
        this.sayAppear();
        this.person_callee = callee;
        this.person_caller = caller;
        this.ENVIRONMENT = new String[BAR_ENVIRONMENT.length];
        for (int i = 0; i < this.ENVIRONMENT.length; ++i) {
            this.ENVIRONMENT[i] = BAR_ENVIRONMENT[i] + Bar.barType;
        }
        this.FADE = FADE_BAR + Bar.barType;
        this.identitie = identitie;
        this.current_flame = this.phrases;
        this.index = 0;
        this.fade();
    }

    public final void start_simplePoint(SCRuniperson callee, String identitie, matrixJ M, vectorJ P) {
        this.iLeave = new SimplePointLeavedialog();
        this.sayAppear();
        this.simplePointScene = true;
        this.person_callee = callee;
        aiplayer callerAIPlayer = aiplayer.getCutSceneAmbientPerson(identitie, "meet_client");
        this.person_caller = callerAIPlayer.getModel();
        this.FADE = FADE_CAR;
        this.identitie = identitie;
        this.M = M;
        this.P = P;
        this.current_flame = this.phrases;
        this.index = 0;
        this.fade();
    }

    private final void fade() {
        event.eventObject((int)scenetrack.CreateSceneXML(this.FADE, 17, null), this, methNames[0]);
    }

    private String[] constructMessage(Phrase mess) {
        if (mess.requests.isEmpty()) {
            return new String[0];
        }
        String[] res = new String[mess.requests.size()];
        int iter = 0;
        for (Phrase.request req : mess.requests) {
            res[iter++] = loc.getDialogName(req.requestmessage);
        }
        return res;
    }

    public void makeAnswerMenu() {
        Phrase firstPhrase = (Phrase)this.current_flame.Phrases.get(this.index);
        if (firstPhrase.isRequest()) {
            AnswerMenu.createAnswerMenu(this.constructMessage(firstPhrase), new WaitQnswer());
        } else {
            eng.err("ERRORR. Dialog has error in dialog process. makeAnswerMenu reached firstPhrase.isRequest()==false");
        }
    }

    public void nextPhrase() {
        if (this.current_flame.Phrases.isEmpty() || this.current_flame.Phrases.size() < this.index) {
            this.dialogended();
            return;
        }
        Phrase firstPhrase = (Phrase)this.current_flame.Phrases.get(this.index);
        int flag = 17;
        if (firstPhrase.isRequest()) {
            eng.err("ERRORR. Dialog has error in dialog process. nextPhrase reached firstPhrase.isRequest()==true");
        } else {
            class Preset {
                public String Phrase;
                public String model;
                public String identity;
                public actorveh car;
                public matrixJ M;
                public vectorJ P;

                Preset(String Phrase2, String model, actorveh car, String identity) {
                    this.Phrase = Phrase2;
                    this.model = model;
                    Dialog.this.nowarnings0 = this.Phrase;
                    Dialog.this.nowarnings0 = this.model;
                    this.car = car;
                    Dialog.this.nowarnings0 = this.identity = identity;
                    Dialog.this.nowarnings1 = this.car;
                }

                Preset(String Phrase2, String model, String identity, matrixJ M, vectorJ P) {
                    this.M = M;
                    this.P = P;
                    this.Phrase = Phrase2;
                    this.model = model;
                    Dialog.this.nowarnings0 = this.Phrase;
                    Dialog.this.nowarnings0 = this.model;
                    Dialog.this.nowarnings0 = this.identity = identity;
                    Dialog.this.nowarnings1 = this.car;
                    Dialog.this.nowarnings2 = this.M;
                    Dialog.this.nowarnings3 = this.P;
                }
            }
            Phrase nextPhrase;
            boolean talkWithMan;
            ++this.index;
            Vector<SceneActorsPool> v = new Vector<SceneActorsPool>(1);
            if (firstPhrase.is_caller) {
                v.add(new SceneActorsPool("man", firstPhrase.is_caller ? this.person_caller : this.person_callee));
            }
            int tip_track_name = 0;
            String npc_model_name = eng.GetManPrefix(this.person_caller.nativePointer);
            boolean bl = talkWithMan = !npc_model_name.contains("Woman");
            tip_track_name = firstPhrase.is_caller ? (talkWithMan ? 1 : 2) : 0;
            if (this.current_flame.Phrases.size() > this.index && (nextPhrase = (Phrase)this.current_flame.Phrases.get(this.index)).isRequest()) {
                flag = 3;
            }
            long scene = 0L;
            if (this.simplePointScene) {
            	
            	//
            	// RICK
                // int indexSceneCreator;
            	int indexSceneCreator = 0;
            	// END
            	//
            	
                Vector<SceneActorsPool> pool = new Vector<SceneActorsPool>(1);
                pool.add(new SceneActorsPool("man", this.person_caller));
                int n = talkWithMan ? (firstPhrase.is_caller ? 2 : 0) : (indexSceneCreator = firstPhrase.is_caller ? 3 : 1);
                assert (indexSceneCreator < SIMPLE_POINT_ENVIRONMENT.length);
                assert (this.M != null);
                assert (this.P != null);
                scene = SIMPLE_POINT_ENVIRONMENT[indexSceneCreator].createScene(flag, pool, new Preset(firstPhrase.phrase, "", this.identitie, this.M, this.P));
            } else {
                scene = scenetrack.CreateSceneXMLPool(this.ENVIRONMENT[tip_track_name], flag, v, new Preset(firstPhrase.phrase, "", this.car, this.identitie));
            }
            if (flag == 3) {
                this.cycleScene = scene;
                event.eventObject((int)scene + 1, this, methNames[2]);
            } else {
                event.eventObject((int)scene, this, this.current_flame.Phrases.size() == this.index ? methNames[1] : methNames[0]);
            }
        }
    }

    public void dialogended() {
        if (this.iLeave != null) {
            this.iLeave.leaveDialog();
            this.iLeave = null;
        }
        this.index = 0;
        this.current_flame = null;
        this.person_callee.play();
        this.person_caller.stop();
        this.sayEndDialog();
        BarMenu.dialog_ended = true;
    }

    private Dialog(String name) {
        this.name = name;
    }

    private Dialog(Dialog value) {
        this.name = value.name;
        this.phrases = new BunchPhrases(value.phrases);
    }

    public static void deInit() {
        storage.clear();
    }

    public static void init() {
        Vector filenames = new Vector();
        eng.getFilesAllyed(TOP_NODE, filenames);
        Vector _names = filenames;
        
        //
        // A manual casting of 'cbdialogmessage.request' was added
        //
        for (Object unknownObjectType : _names) {
        	try {
        		String name = (String) unknownObjectType;
	            Node top = XmlUtils.parse(name);
	            if (null == top || top.getName().compareToIgnoreCase(TOP_NODE) != 0) {
	                return;
	            }
	            NodeList children = top.getNamedChildren(DIALOG_NODE);
	            for (int i = 0; i < children.size(); ++i) {
	                Node node = (Node)children.get(i);
	                String dialog_name = node.getAttribute(NAME_ATTR);
	                Dialog dialog = Dialog.readDialog(node, dialog_name);
	                storage.put(dialog_name, dialog);
	            }
        	} catch (Exception e) {
				RickRollLog.dumpStackTrace("Modified decompiled java exception. A manual casting of 'name' was added", e);
			}
        }
        //
        // END
        //
    }

    private static final Dialog readDialog(Node node, String name) {
        Dialog res = new Dialog(name);
        res.phrases = Dialog.readPharases(node);
        return res;
    }

    private static final BunchPhrases readPharases(Node node) {
        NodeList children = node.getNamedChildren(PHRASE_NODE);
        BunchPhrases bunch = new BunchPhrases();
        for (int i = 0; i < children.size(); ++i) {
            Node phrase = (Node)children.get(i);
            bunch.Phrases.add(Dialog.readPhrase(phrase));
        }
        return bunch;
    }

    private static final Phrase readPhrase(Node node) {
        Node phrase = node;
        boolean is_callee = false;
        if (phrase.hasAttribute(CALLEE_ATTR)) {
            is_callee = true;
        } else if (phrase.hasAttribute(CALLER_ATTR)) {
            is_callee = false;
        }
        String message = "";
        if (phrase.hasAttribute(WAV_ATTR)) {
            message = phrase.getAttribute(WAV_ATTR);
        }
        Phrase single_pharse = new Phrase(!is_callee, message);
        NodeList questions = node.getNamedChildren(QUESTION_NODE);
        for (Node single_question : questions) {
            String id = single_question.getAttribute(QUESTION_ID_ATTR);
            String text = single_question.getAttribute(QUESTION_TEXT_ATTR);
            Phrase.request q = new Phrase.request();
            q.id = Integer.parseInt(id);
            q.requestmessage = text;
            NodeList dialogs = single_question.getNamedChildren(DIALOG_NODE);
            if (1 == dialogs.size()) {
                q.dialog_continue = Dialog.readPharases((Node)dialogs.get(0));
            }
            single_pharse.requests.add(q);
        }
        return single_pharse;
    }

    class SimplePointLeavedialog
    implements ILeaveDialog {
        SimplePointLeavedialog() {
            vectorJ carPos;
            actorveh liveCar = Crew.getIgrokCar();
            liveCar.UpdateCar();
            eng.SwitchDriver_outside_cabin(liveCar.getCar());
            actorveh car = Crew.getIgrokCar();
            car.sVeclocity(0.0);
            Place nearestPlace = MissionSystemInitializer.getMissionsMap().getNearestPlace();
            vectorJ goodPosition = carPos = car.gPosition();
            if (nearestPlace != null) {
                switch (nearestPlace.getTip()) {
                    case 0: 
                    case 2: 
                    case 3: {
                        break;
                    }
                    case 1: {
                        goodPosition = nearestPlace.getCoords();
                        break;
                    }
                }
            }
            eng.disableControl();
        }

        public void leaveDialog() {
            Crew.getIgrokCar().UpdateCar();
            eng.SwitchDriver_in_cabin(Crew.getIgrokCar().getCar());
            Helper.restoreCameraToIgrokCar();
            eng.enableControl();
        }
    }

    class LeaveDialogAndLeaveCar
    implements ILeaveDialog {
        aiplayer npc;

        LeaveDialogAndLeaveCar(aiplayer npc) {
            rnrcore.Helper.peekNativeMessage(Dialog.EVENT_START_CARDIALOG);
            this.npc = npc;
            npc.getModel().lockPerson();
            actorveh car = Crew.getIgrokCar();
            car.sVeclocity(0.0);
            Place nearestPlace = MissionSystemInitializer.getMissionsMap().getNearestPlace();
            vectorJ carPos = car.gPosition();
            if (nearestPlace != null) {
                double distance_2 = carPos.len2(nearestPlace.getCoords());
                switch (nearestPlace.getTip()) {
                    case 0: 
                    case 2: 
                    case 3: {
                        parkingplace parking;
                        if (!(distance_2 < 40000.0) || (parking = parkingplace.findNearestParking(nearestPlace.getCoords())) == null) break;
                        car.makeParking(parking, 0);
                        car.leaveParking();
                        break;
                    }
                }
            }
            car.setHandBreak(true);
            eng.disableControl();
        }

        public void leaveDialog() {
            this.npc.getModel().unlockPerson();
            rnrcore.Helper.peekNativeMessage(Dialog.EVENT_END_CARDIALOG);
            this.npc.abondoneCar(Dialog.this.car);
            Helper.restoreCameraToIgrokCar();
            eng.enableControl();
            Dialog.this.car.setHandBreak(false);
        }
    }

    class SimpleLeave
    implements ILeaveDialog {
        SimpleLeave() {
            rnrcore.Helper.peekNativeMessage(Dialog.EVENT_START_DIALOG);
        }

        public void leaveDialog() {
            rnrcore.Helper.peekNativeMessage(Dialog.EVENT_END_DIALOG);
        }
    }

    class WaitQnswer
    extends TypicalAnm
    implements IDialogListener {
        private boolean callBackRecieved = false;
        private boolean yesRecieved = false;

        WaitQnswer() {
            eng.CreateInfinitScriptAnimation(this);
        }

        public boolean animaterun(double dt) {
            if (this.callBackRecieved) {
                if (this.yesRecieved) {
                    Dialog.this.sayYes();
                    this.makeAnswer(0);
                } else {
                    Dialog.this.sayNo();
                    this.makeAnswer(1);
                }
                return true;
            }
            return false;
        }

        public void onAppear(String dialog_name) {
        }

        public void onNo(String dialog_name) {
            this.callBackRecieved = true;
            this.yesRecieved = false;
        }

        public void onYes(String dialog_name) {
            this.callBackRecieved = true;
            this.yesRecieved = true;
        }

        private void makeAnswer(int value) {
            if (0L != Dialog.this.cycleScene) {
                scenetrack.DeleteScene(Dialog.this.cycleScene);
                Dialog.this.cycleScene = 0L;
            }
            Phrase phr = (Phrase)Dialog.this.current_flame.Phrases.get(Dialog.this.index);
            for (Phrase.request req : phr.requests) {
                if (req.id != value) continue;
                Dialog.this.current_flame = req.dialog_continue;
                Dialog.this.index = 0;
                Dialog.this.nextPhrase();
            }
        }
    }

    private static class Phrase {
        private boolean is_caller;
        private String phrase;
        Vector<request> requests = new Vector();

        Phrase() {
        }

        Phrase(Phrase value) {
            this.is_caller = value.is_caller;
            this.phrase = new String(value.phrase);
            this.requests = new Vector();
            for (request singleRequest : value.requests) {
                this.requests.add(new request(singleRequest));
            }
        }

        boolean isRequest() {
            return !this.requests.isEmpty();
        }

        Phrase(boolean is_caller, String phrase) {
            this.is_caller = is_caller;
            this.phrase = phrase;
        }

        public static class request {
            private String requestmessage;
            private int id;
            private BunchPhrases dialog_continue = new BunchPhrases();

            request() {
                this.requestmessage = "empty request";
                this.id = 0;
            }

            request(request value) {
                this.requestmessage = new String(value.requestmessage);
                this.id = value.id;
                this.dialog_continue = new BunchPhrases(value.dialog_continue);
            }
        }
    }

    private static class BunchPhrases {
        private ArrayList<Phrase> Phrases = new ArrayList();

        BunchPhrases() {
        }

        BunchPhrases(BunchPhrases value) {
            this.Phrases = new ArrayList();
            for (Phrase singlePhrase : value.Phrases) {
                this.Phrases.add(new Phrase(singlePhrase));
            }
        }
    }
}

