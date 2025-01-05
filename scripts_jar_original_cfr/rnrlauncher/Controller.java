/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import rnrcore.eng;
import rnrlauncher.data.ColumnHeader;
import rnrlauncher.data.InfoText;
import rnrlauncher.data.LocalizedText;
import rnrlauncher.data.Resolution;
import rnrlauncher.data.SystemInfoDataRecord;
import rnrlauncher.widgets.DataTable;
import rnrlauncher.widgets.MainWindow;

public final class Controller {
    private static final String FONT_NAME = "Tahoma";
    private static final String LOCALIZATION_NAMESPACE = "SYSTEM";
    private static final List<InfoText> textToDisplay = new ArrayList<InfoText>();
    private static final HashMap<Integer, ResolutionResources> resources = new HashMap();
    private static ImageIcon LOGO_ICON;
    private static ImageIcon WINDOW_ICON;
    private static final String ICONS_FOLDER = "..\\Data\\Menu\\Misc\\";
    private static final String LOGO_IMAGE_FILE = "..\\Data\\loc\\images\\rnr_launcher_logo.gif";
    private static final String WINDOW_ICON_IMAGE_FILE = "..\\Data\\Menu\\Misc\\rnr.png";
    private static String[] resolutionTextIndex;
    private static final Color COLOR_CRASH;
    private static final Color COLOR_BAD;
    private static final Color COLOR_GOOD;
    private static final String IMAGE_LIGHT_GREEN = "rnr_launcher_light_green.gif";
    private static final String IMAGE_LIGHT_YELLOW = "rnr_launcher_light_yellow.gif";
    private static final String IMAGE_LIGHT_RED = "rnr_launcher_light_red.gif";
    private static final String IMAGE_GRADIENT_GREEN = "rnr_launcher_back_green.gif";
    private static final String IMAGE_GRADIENT_YELLOW = "rnr_launcher_back_yellow.gif";
    private static final String IMAGE_GRADIENT_RED = "rnr_launcher_back_red.gif";

    static void checkIcon(ImageIcon icon, String source) {
        if (null == icon || 8 != icon.getImageLoadStatus()) {
            System.err.println("Warning: failed to load icon from: " + source);
        }
    }

    public static void loadResourses() {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.err.print("Warning: " + e.getMessage());
        }
        ImageIcon iconGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_green.gif");
        Controller.checkIcon(iconGood, IMAGE_LIGHT_GREEN);
        ImageIcon iconBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_yellow.gif");
        Controller.checkIcon(iconBad, IMAGE_LIGHT_YELLOW);
        ImageIcon iconCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_red.gif");
        Controller.checkIcon(iconCrash, IMAGE_LIGHT_RED);
        ImageIcon gradientGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_green.gif");
        Controller.checkIcon(gradientGood, IMAGE_GRADIENT_GREEN);
        ImageIcon gradientBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");
        Controller.checkIcon(gradientBad, IMAGE_GRADIENT_YELLOW);
        ImageIcon gradientCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_red.gif");
        Controller.checkIcon(gradientCrash, IMAGE_GRADIENT_RED);
        resources.put(0, new ResolutionResources(COLOR_CRASH, iconCrash, gradientCrash, "LAUNCHER_COMPONENT_RESUME_CANT_RUN"));
        resources.put(1, new ResolutionResources(COLOR_CRASH, iconCrash, gradientCrash, "LAUNCHER_COMPONENT_RESUME_UNDEFINED_COMPONENT"));
        resources.put(2, new ResolutionResources(COLOR_BAD, iconCrash, gradientCrash, "LAUNCHER_COMPONENT_RESUME_WILL_RUN_WITH_ARTIFACTS"));
        resources.put(3, new ResolutionResources(COLOR_BAD, iconBad, gradientBad, "LAUNCHER_COMPONENT_RESUME_FUNCTIONALITY_NOT_GOURANTED"));
        resources.put(4, new ResolutionResources(COLOR_GOOD, iconGood, gradientGood, "LAUNCHER_COMPONENT_RESUME_MUST_RUN"));
        Controller.resolutionTextIndex[4] = "LAUNCHER_RESUME_MUST_RUN";
        Controller.resolutionTextIndex[3] = "LAUNCHER_RESUME_FUNCTIONALITY_NOT_GOURANTED";
        Controller.resolutionTextIndex[2] = "LAUNCHER_RESUME_WILL_RUN_WITH_ARTIFACTS";
        Controller.resolutionTextIndex[1] = "LAUNCHER_RESUME_UNDEFINED_COMPONENT";
        Controller.resolutionTextIndex[0] = "LAUNCHER_RESUME_CANT_RUN";
        LOGO_ICON = new ImageIcon(LOGO_IMAGE_FILE);
        Controller.checkIcon(LOGO_ICON, LOGO_IMAGE_FILE);
        WINDOW_ICON = new ImageIcon(WINDOW_ICON_IMAGE_FILE);
        Controller.checkIcon(WINDOW_ICON, WINDOW_ICON_IMAGE_FILE);
    }

    public static void prepare() {
        textToDisplay.clear();
    }

    public static void consumeData(String systemName, String userSystem, String requiredSystem, int status) {
        System.err.println("Info: data came -> " + systemName + '|' + userSystem + '|' + requiredSystem + '|' + status);
        textToDisplay.add(new InfoText(userSystem, requiredSystem, null, status, eng.getStringRef(LOCALIZATION_NAMESPACE, systemName)));
    }

    public static boolean showLauncherWindow() {
        String componentNameString = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_TABLE_COLUMN_COMPONENT");
        String userSystemInfoHeaderString = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_TABLE_COLUMN_USER_WARE");
        String requiredSystemInfoHeaderString = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_TABLE_COLUMN_MINIMAL_WARE");
        String analysisResultHeaderString = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_TABLE_COLUMN_STATUS");
        ColumnHeader[] tableHeader = new ColumnHeader[]{new ColumnHeader(componentNameString, 0.3), new ColumnHeader(userSystemInfoHeaderString, 0.15), new ColumnHeader(requiredSystemInfoHeaderString, 0.25), new ColumnHeader(analysisResultHeaderString, 0.3)};
        int logoWidth = 0 < LOGO_ICON.getIconWidth() ? LOGO_ICON.getIconWidth() : 0;
        DataTable table = new DataTable(MainWindow.BACKGROUND_COLOR, FONT_NAME, tableHeader, logoWidth + MainWindow.getBordersWidth());
        boolean unknownComponentExists = false;
        boolean undefinedComponentExists = false;
        boolean unsupportedComponentExists = false;
        boolean incompatibleComponentExists = false;
        for (InfoText infoText : textToDisplay) {
            SystemInfoDataRecord dataRecord = new SystemInfoDataRecord();
            ResolutionResources res = resources.get(infoText.getStatus());
            String resolutionText = eng.getStringRef(LOCALIZATION_NAMESPACE, res.getResolutionTextId());
            Resolution resulution = new Resolution(res.getImage(), res.getGradient(), resolutionText);
            switch (infoText.getStatus()) {
                case 0: {
                    incompatibleComponentExists = true;
                    break;
                }
                case 3: {
                    unknownComponentExists = true;
                    break;
                }
                case 2: {
                    unsupportedComponentExists = true;
                    break;
                }
                case 1: {
                    undefinedComponentExists = true;
                    break;
                }
                case 4: {
                    break;
                }
                default: {
                    System.err.println("Warning: unknown component status: " + infoText.getStatus());
                }
            }
            dataRecord.putInfo(0, infoText.getName());
            dataRecord.putInfo(1, infoText.getUserSystemInfo());
            dataRecord.putInfo(2, infoText.getRequiredSystemInfo());
            dataRecord.putInfo(3, resulution);
            table.addInfoRecord(dataRecord);
        }
        LocalizedText text = new LocalizedText();
        String cancelButtonText = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_BUTTON_CANCEL");
        String runAnywayButtonText = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_BUTTON_RUN_ANYWAY");
        String headerText = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_HEADER");
        String runButtonText = eng.getStringRef(LOCALIZATION_NAMESPACE, "LAUNCHER_BUTTON_OK");
        text.setCancelButtonText(cancelButtonText);
        text.setRunButtonText(runButtonText);
        text.setRunAnywayButtonText(runAnywayButtonText);
        text.setHeaderText(headerText);
        int status = 4;
        if (incompatibleComponentExists) {
            status = 0;
        } else if (undefinedComponentExists) {
            status = 1;
        } else if (unknownComponentExists) {
            status = 3;
        } else if (unsupportedComponentExists) {
            status = 2;
        }
        text.setFooterText(eng.getStringRef(LOCALIZATION_NAMESPACE, resolutionTextIndex[status]));
        table.constructGui();
        MainWindow window = new MainWindow(LOGO_ICON, WINDOW_ICON, table, FONT_NAME, text, status);
        return window.show();
    }

    public static void redirectSystemErr(String rootPath) {
        try {
            System.setErr(new PrintStream(new FileOutputStream(rootPath + "warnings\\err.log", true)));
            System.err.println("INFO: redirected err from JVM");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static {
        resolutionTextIndex = new String[5];
        COLOR_CRASH = new Color(255, 170, 153, 255);
        COLOR_BAD = new Color(230, 207, 161, 255);
        COLOR_GOOD = new Color(161, 179, 168, 255);
    }

    private static final class ResolutionResources {
        private final Color color;
        private final ImageIcon image;
        private final ImageIcon gradient;
        private final String resolutionTextId;

        ResolutionResources(Color color, ImageIcon image, ImageIcon gradient, String resolutionTextId) {
            this.resolutionTextId = resolutionTextId;
            assert (null != color);
            assert (null != image);
            assert (null != gradient);
            this.color = color;
            this.image = image;
            this.gradient = gradient;
        }

        Color getColor() {
            return this.color;
        }

        ImageIcon getImage() {
            return this.image;
        }

        ImageIcon getGradient() {
            return this.gradient;
        }

        String getResolutionTextId() {
            return this.resolutionTextId;
        }
    }
}

