package rnrlauncher;

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import javax.swing.ImageIcon;
import javax.swing.LookAndFeel;
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
      UIManager.setLookAndFeel((LookAndFeel)new WindowsClassicLookAndFeel());
    } catch (UnsupportedLookAndFeelException e) {
      System.err.print(e.getMessage());
      e.printStackTrace(System.err);
    } 
    ColumnHeader[] tableHeader = { new ColumnHeader(", 0.3D), new ColumnHeader(", 0.15D), new ColumnHeader(", 0.25D), new ColumnHeader(", 0.3D) };
    ImageIcon logoIcon = new ImageIcon("..\\Data\\loc\\images\\rnr_launcher_logo.gif");
    DataTable table = new DataTable(MainWindow.BACKGROUND_COLOR, "Tahoma", tableHeader, logoIcon.getIconWidth() + MainWindow.getBordersWidth());
    ImageIcon iconGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_green.gif");
    ImageIcon iconBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_yellow.gif");
    ImageIcon iconCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_light_red.gif");
    ImageIcon gradientGood = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_green.gif");
    ImageIcon gradientBad = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_yellow.gif");
    ImageIcon gradientCrash = new ImageIcon("..\\Data\\Menu\\Misc\\rnr_launcher_back_red.gif");
    Object res1 = new Resolution(iconGood, gradientGood, "Ok");
    Object res2 = new Resolution(iconCrash, gradientCrash, "Will crash! You PC SUCKS!!!! Go spend money");
    Object res3 = new Resolution(iconBad, gradientBad, "God only knows");
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
    pagefileDataRecord.putInfo(2, "100");
    pagefileDataRecord.putInfo(3, res2);
    SystemInfoDataRecord cpuDataRecord = new SystemInfoDataRecord();
    cpuDataRecord.putInfo(0, "CPU");
    cpuDataRecord.putInfo(1, "486");
    cpuDataRecord.putInfo(2, "100!");
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
    text.setHeaderText("");
    table.constructGui();
    MainWindow window = new MainWindow(logoIcon, new ImageIcon("..\\Data\\Menu\\Misc\\rnr.png"), table, "Tahoma", text, 2);
    window.show();
  }
}
