/*
 * Decompiled with CFR 0.151.
 */
package rnrlauncher;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import rnrlauncher.data.ColumnHeader;
import rnrlauncher.data.LocalizedText;
import rnrlauncher.data.Resolution;
import rnrlauncher.data.SystemInfoDataRecord;
import rnrlauncher.widgets.DataTable;
import rnrlauncher.widgets.MainWindow;

public final class LauncherTest {
    private static final String FONT_NAME = "Tahoma";
    private static final String ICONS_FOLDER = "..\\Data\\Menu\\Misc\\";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) {
            System.err.print(e.getMessage());
            e.printStackTrace(System.err);
        }
        ColumnHeader[] tableHeader = new ColumnHeader[]{new ColumnHeader("\u00ca\u00ee\u00ec\u00ef\u00ee\u00ed\u00e5\u00ed\u00f2\u00e0", 0.3), new ColumnHeader("\u00c2\u00e0\u00f8 \u00ea\u00ee\u00ec\u00ef\u00fc\u00fe\u00f2\u00e5\u00f0", 0.15), new ColumnHeader("\u00cc\u00e8\u00ed\u00e8\u00ec\u00e0\u00eb\u00fc\u00ed\u00fb\u00e5 \u00f2\u00f0\u00e5\u00e1\u00ee\u00e2\u00e0\u00ed\u00e8\u00ff", 0.25), new ColumnHeader("\u00d0\u00e5\u00e7\u00f3\u00eb\u00fc\u00f2\u00e0\u00f2 \u00e0\u00ed\u00e0\u00eb\u00e8\u00e7\u00e0", 0.3)};
        ImageIcon logoIcon = new ImageIcon("..\\Data\\loc\\images\\rnr_launcher_logo.gif");
        DataTable table = new DataTable(MainWindow.BACKGROUND_COLOR, FONT_NAME, tableHeader, logoIcon.getIconWidth() + MainWindow.getBordersWidth());
        ImageIcon iconGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_green.gif");
        ImageIcon iconBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_yellow.gif");
        ImageIcon iconCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_red.gif");
        ImageIcon gradientGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_green.gif");
        ImageIcon gradientBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");
        ImageIcon gradientCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_red.gif");
        Resolution res1 = new Resolution(iconGood, gradientGood, "Ok");
        Resolution res2 = new Resolution(iconCrash, gradientCrash, "Will crash! You PC SUCKS!!!! Go spend money");
        Resolution res3 = new Resolution(iconBad, gradientBad, "God only knows");
        SystemInfoDataRecord osDataRecord = new SystemInfoDataRecord();
        osDataRecord.putInfo(0, "Operating System");
        osDataRecord.putInfo(1, "Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, Windows Vista, ");
        osDataRecord.putInfo(2, "Windows XP, Windows 2000, Windows Vista");
        osDataRecord.putInfo(3, res1);
        SystemInfoDataRecord videoCardDataRecord = new SystemInfoDataRecord();
        videoCardDataRecord.putInfo(0, "Videocard");
        videoCardDataRecord.putInfo(1, "GeForce 8800 GTX ULTA");
        videoCardDataRecord.putInfo(2, "Voodoo 3, Voodoo 4, Voodoo 5");
        videoCardDataRecord.putInfo(3, res2);
        SystemInfoDataRecord videoMemoryDataRecord = new SystemInfoDataRecord();
        videoMemoryDataRecord.putInfo(0, "Video memory");
        videoMemoryDataRecord.putInfo(1, "128 Mb");
        videoMemoryDataRecord.putInfo(2, "2048 Mb");
        videoMemoryDataRecord.putInfo(3, res3);
        SystemInfoDataRecord directxVersionDataRecord = new SystemInfoDataRecord();
        directxVersionDataRecord.putInfo(0, "DirectX version");
        directxVersionDataRecord.putInfo(1, "8.0");
        directxVersionDataRecord.putInfo(2, "9.0c");
        directxVersionDataRecord.putInfo(3, res1);
        SystemInfoDataRecord audioCardDataRecord = new SystemInfoDataRecord();
        audioCardDataRecord.putInfo(0, "Audio Card");
        audioCardDataRecord.putInfo(1, "???");
        audioCardDataRecord.putInfo(2, "Sound Blaster");
        audioCardDataRecord.putInfo(3, res3);
        SystemInfoDataRecord memoryDataRecord = new SystemInfoDataRecord();
        memoryDataRecord.putInfo(0, "System RAM");
        memoryDataRecord.putInfo(1, "1024");
        memoryDataRecord.putInfo(2, "GeForce1, GeForce2, GeForce3, GeForce4, GeForce5, GeForce6, GeForce7, GeForce8, GeForce9");
        memoryDataRecord.putInfo(3, res2);
        SystemInfoDataRecord pagefileDataRecord = new SystemInfoDataRecord();
        pagefileDataRecord.putInfo(0, "Page file");
        pagefileDataRecord.putInfo(1, "1024");
        pagefileDataRecord.putInfo(2, "100 \u00d2\u00c5\u00d0\u00d0\u00c0 \u00c3\u00c5\u00ca\u00d2\u00c0\u00d0\u00c0!");
        pagefileDataRecord.putInfo(3, res2);
        SystemInfoDataRecord cpuDataRecord = new SystemInfoDataRecord();
        cpuDataRecord.putInfo(0, "CPU");
        cpuDataRecord.putInfo(1, "486");
        cpuDataRecord.putInfo(2, "\u00cf\u00c5\u00cd\u00d2\u00c8\u00d3\u00cc 100!");
        cpuDataRecord.putInfo(3, res2);
        table.addInfoRecord(osDataRecord);
        table.addInfoRecord(videoCardDataRecord);
        table.addInfoRecord(videoMemoryDataRecord);
        table.addInfoRecord(directxVersionDataRecord);
        table.addInfoRecord(audioCardDataRecord);
        table.addInfoRecord(memoryDataRecord);
        table.addInfoRecord(pagefileDataRecord);
        table.addInfoRecord(cpuDataRecord);
        LocalizedText text = new LocalizedText();
        text.setCancelButtonText("Exit");
        text.setRunButtonText("Run game");
        text.setRunAnywayButtonText("Run game anyway");
        text.setFooterText("QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH QQQ MSH ");
        text.setHeaderText("\u00c0\u00cd\u00c0\u00cb\u00c8\u00c7 \u00ca\u00ce\u00cd\u00d4\u00c8\u00c3\u00d3\u00d0\u00c0\u00d6\u00c8\u00c8 \u00ca\u00ce\u00cc\u00cf\u00dc\u00de\u00d2\u00c5\u00d0\u00c0");
        table.constructGui();
        MainWindow window = new MainWindow(logoIcon, new ImageIcon("..\\Data\\Menu\\Misc\\rnr.png"), table, FONT_NAME, text, 2);
        window.show();
    }
}

