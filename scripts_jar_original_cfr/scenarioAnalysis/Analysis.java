/*
 * Decompiled with CFR 0.151.
 */
package scenarioAnalysis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;
import rnrcore.NativeEventController;
import rnrcore.ScenarioSync;
import rnrcore.eng;
import rnrloggers.ScenarioAnalysisLogger;
import rnrscenario.config.ConfigManager;
import rnrscenario.scenarioscript;
import scenarioAnalysis.AdjacencyMatrix;
import scenarioAnalysis.AnalysisUI;
import scenarioAnalysis.DynamicScenarioAnalyzer;
import scenarioAnalysis.StaticScenarioAnalyzer;
import scenarioXml.CbvEventListenerBuilder;
import scenarioXml.InternalScenarioRepresentation;
import scenarioXml.XmlScenarioMachineBuilder;
import scriptEvents.EventsController;
import scriptEvents.EventsControllerHelper;

public final class Analysis {
    private static final int BUFFER_SIZE = 0x100000;
    private static final byte[] BUFFER = new byte[0x100000];
    private static final String CONFIG_FILE_PATH = ".\\Analysis.cfg";
    private static final String QUESTS_XML = "quests";
    private static final String CBV_XML = "cbv";
    private static final String MSG_XML = "msg";
    private static final String Q_XML = "q";
    private static final String SV_FOLDER_XML = "save_folder";
    private static final String SV_FILE_XML = "save_file";
    private static int folderIncrement = 0;

    private static void moveResults() {
        File logDirectory = new File(".\\warnings\\");
        if (logDirectory.exists()) {
            File dir = new File("d:\\temp\\scenariologs\\res" + folderIncrement++);
            if (!dir.mkdirs()) {
                // empty if block
            }
            try {
                Analysis.copyFiles(logDirectory, dir);
            }
            catch (IOException c) {
                System.err.print(c.toString());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyFiles(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            String[] list;
            if (!dest.mkdirs()) {
                System.err.println("Failed to make destination directories");
            }
            for (String aList : list = src.list(new FilenameFilter(){

                public boolean accept(File dir, String name) {
                    return name.endsWith("log");
                }
            })) {
                String dest1 = dest.getPath() + "\\" + aList;
                String src1 = src.getPath() + "\\" + aList;
                Analysis.copyFiles(new File(src1), new File(dest1));
            }
        } else {
            FileInputStream in = null;
            FileOutputStream out = null;
            try {
                in = new FileInputStream(src);
                out = new FileOutputStream(dest);
                int bytesRead = in.read(BUFFER);
                while (0 < bytesRead) {
                    out.write(BUFFER, 0, bytesRead);
                    bytesRead = in.read(BUFFER);
                }
            }
            finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        }
    }

    public static boolean performStaticValidation(String configFileName) {
        assert (null != configFileName);
        AnalysisContext analysisContext = new AnalysisContext().invoke(configFileName);
        InternalScenarioRepresentation scenario = analysisContext.getScenario();
        CbvEventListenerBuilder cbvBuilder = analysisContext.getCbvBuilder();
        String[] defaultStartedQuests = analysisContext.getDefaultStartedQuest();
        return Analysis.runStaticAnalysis(scenario, cbvBuilder, defaultStartedQuests);
    }

    public static boolean performDynamicValidation(String configFileName) {
        return null != configFileName;
    }

    public static void main(String[] arguments) {
        AnalysisContext analysisContext = new AnalysisContext().invoke(CONFIG_FILE_PATH);
        InternalScenarioRepresentation scenario = analysisContext.getScenario();
        CbvEventListenerBuilder cbvBuilder = analysisContext.getCbvBuilder();
        String[] defaultStartedQuests = analysisContext.getDefaultStartedQuest();
        Analysis.runStaticAnalysis(scenario, cbvBuilder, defaultStartedQuests);
        Analysis.runDynamicAnalysis(scenario, cbvBuilder, defaultStartedQuests);
        Analysis.runGuiControllerAnalysis(scenario, defaultStartedQuests);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void runGuiControllerAnalysis(InternalScenarioRepresentation scenario, String[] defaultStartedQuests) {
        try {
            DynamicScenarioAnalyzer analyzerDynamic = new DynamicScenarioAnalyzer(scenario.getStatesMachine());
            AnalysisUI ui = new AnalysisUI();
            analyzerDynamic.uiSimulation(ui, defaultStartedQuests);
        }
        catch (Throwable e) {
            e.printStackTrace(System.err);
        }
        finally {
            ScenarioSync.interruptScriptRunningThread();
            Analysis.moveResults();
        }
    }

    private static void runDynamicAnalysis(InternalScenarioRepresentation scenario, CbvEventListenerBuilder cbvBuilder, String[] defaultStartedQuest) {
        EventsController.getInstance().addListener(cbvBuilder.getWare());
        EventsController.getInstance().addListener(scenario.getStatesMachine());
        DynamicScenarioAnalyzer analyzerDynamic = new DynamicScenarioAnalyzer(scenario.getStatesMachine());
        analyzerDynamic.randomSimulation(defaultStartedQuest);
    }

    private static boolean runStaticAnalysis(InternalScenarioRepresentation scenario, CbvEventListenerBuilder cbvBuilder, String[] defaultStartedQuest) {
        AdjacencyMatrix scenarioGraph = new AdjacencyMatrix(scenario.getStatesSet(), cbvBuilder.getWare().getActionList());
        StaticScenarioAnalyzer analyzerStatic = new StaticScenarioAnalyzer(scenarioGraph);
        return analyzerStatic.validate(defaultStartedQuest);
    }

    private static class AnalysisContext {
        private String[] defaultStartedQuests;
        private InternalScenarioRepresentation scenario;
        private CbvEventListenerBuilder cbvBuilder;

        private AnalysisContext() {
        }

        public String[] getDefaultStartedQuest() {
            return this.defaultStartedQuests;
        }

        public InternalScenarioRepresentation getScenario() {
            return this.scenario;
        }

        public CbvEventListenerBuilder getCbvBuilder() {
            return this.cbvBuilder;
        }

        public AnalysisContext invoke(String configFileName) {
            assert (null != configFileName);
            File logDirectory = new File(".\\warnings\\");
            if (!logDirectory.exists() && !logDirectory.mkdir()) {
                System.err.println("Failed to create directory for log files");
            }
            Properties defaultSettings = new Properties();
            defaultSettings.setProperty(Analysis.QUESTS_XML, ".\\quests.xml");
            defaultSettings.setProperty(Analysis.CBV_XML, ".\\cbvideocalls.xml");
            defaultSettings.setProperty(Analysis.MSG_XML, ".\\messageevents.xml");
            defaultSettings.setProperty(Analysis.Q_XML, "gamebegining");
            defaultSettings.setProperty(Analysis.SV_FOLDER_XML, ".\\saves");
            defaultSettings.setProperty(Analysis.SV_FILE_XML, "gamesave.xml");
            Properties settings = new Properties(defaultSettings);
            try {
                settings.load(new FileInputStream(configFileName));
            }
            catch (FileNotFoundException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(e.getMessage());
            }
            catch (IOException e) {
                ScenarioAnalysisLogger.getInstance().getLog().warning(e.getMessage());
            }
            String questXmlFileName = settings.getProperty(Analysis.QUESTS_XML);
            String cbvCallsXmlFileName = settings.getProperty(Analysis.CBV_XML);
            String msgEventsXmlFileName = settings.getProperty(Analysis.MSG_XML);
            this.defaultStartedQuests = settings.getProperty(Analysis.Q_XML).split(",");
            eng.noNative = true;
            ConfigManager.setConfigFilePath("");
            if (null == scenarioscript.script) {
                new scenarioscript();
            }
            NativeEventController.init();
            EventsControllerHelper.init();
            try {
                EventsControllerHelper.getInstance().uploadMessageEventsToRegister(msgEventsXmlFileName);
                this.scenario = XmlScenarioMachineBuilder.getScenarioMachine(questXmlFileName, true);
                this.cbvBuilder = new CbvEventListenerBuilder(cbvCallsXmlFileName, this.scenario.getStatesMachine());
            }
            catch (IOException exception) {
                System.err.println(exception.getMessage());
                exception.printStackTrace(System.err);
                return this;
            }
            return this;
        }
    }
}

