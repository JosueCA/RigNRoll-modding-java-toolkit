package menuscript.mainmenu;

import java.util.Vector;
import menu.JavaEvents;

public class SaveLoadCommonManagement {
  public static SaveLoadCommonManagement getSaveLoadCommonManager() {
    if (null == instance)
      instance = new SaveLoadCommonManagement(); 
    return instance;
  }
  
  private static SaveLoadCommonManagement instance = null;
  
  private Vector ret_exits_files;
  
  public String in_name0;
  
  public String in_name1;
  
  public String out_string;
  
  public MediaTime out_time;
  
  public int in_game_type;
  
  public int in_media_type;
  
  public int in_sort_type;
  
  public boolean in_sort_dir;
  
  public boolean bRet;
  
  public static final int type_Single = 1;
  
  public static final int type_Multi = 2;
  
  public static final int type_QRace = 4;
  
  public static final int type_QuickSave = 1;
  
  public static final int type_AutoSave = 2;
  
  public static final int type_Save = 4;
  
  public static final int type_Clip = 8;
  
  private SaveLoadCommonManagement() {
    this.ret_exits_files = new Vector();
    this.out_time = new MediaTime();
    this.in_game_type = 0;
    this.in_media_type = 0;
    this.in_sort_type = 0;
    this.in_sort_dir = true;
    this.bRet = false;
  }
  
  static class MediaTime {
    int year = 0;
    
    int month = 0;
    
    int day = 0;
    
    int hour = 0;
    
    int min = 0;
    
    int sec = 0;
  }
  
  static class Media {
    String media_name;
    
    int game_type;
    
    int media_type;
    
    int year;
    
    int month;
    
    SaveLoadCommonManagement.MediaTime media_time;
  }
  
  Vector<Media> GetExistsMedia(int GameType, int mediaType, int sort_type, boolean sortUp) {
    this.in_game_type = GameType;
    this.in_media_type = mediaType;
    this.in_sort_type = sort_type;
    this.in_sort_dir = sortUp;
    JavaEvents.SendEvent(25, 0, this);
    Vector<Media> res = (Vector)this.ret_exits_files.clone();
    this.ret_exits_files.clear();
    return res;
  }
  
  void DeleteExistsMedia(String name, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_media_type = mediaType;
    this.in_name0 = name;
    JavaEvents.SendEvent(25, 1, this);
  }
  
  boolean RenameExistsMedia(String name, int GameType, int mediaType, String new_name) {
    this.in_game_type = GameType;
    this.in_media_type = mediaType;
    this.in_name0 = name;
    this.in_name1 = new_name;
    JavaEvents.SendEvent(25, 2, this);
    return this.bRet;
  }
  
  void SetStartNewGameFlag(int GameType) {
    this.in_game_type = GameType;
    JavaEvents.SendEvent(25, 3, this);
  }
  
  void SetLoadGameFlagFromESCMenu(String exists_media, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_name0 = exists_media;
    this.in_media_type = mediaType;
    JavaEvents.SendEvent(25, 4, this);
  }
  
  void SetLoadGameFlagFromMainMenu(String exists_media, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_name0 = exists_media;
    this.in_media_type = mediaType;
    JavaEvents.SendEvent(25, 5, this);
  }
  
  void SetRestartGameFlag() {
    JavaEvents.SendEvent(25, 6, this);
  }
  
  boolean SetSaveGameFlag(String exists_media, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_name0 = exists_media;
    this.in_media_type = mediaType;
    JavaEvents.SendEvent(25, 7, this);
    return this.bRet;
  }
  
  void SetQuitToMainMenuFlag() {
    JavaEvents.SendEvent(25, 8, this);
  }
  
  void UpdateShot(String exists_media, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_name0 = exists_media;
    this.in_media_type = mediaType;
    JavaEvents.SendEvent(25, 9, this);
  }
  
  void UpdateShotByCurrent() {
    JavaEvents.SendEvent(25, 10, this);
  }
  
  MediaTime GetCurrentMediaTime() {
    JavaEvents.SendEvent(25, 11, this);
    return this.out_time;
  }
  
  public void OnEnterESCmenu() {
    JavaEvents.SendEvent(25, 13, this);
  }
  
  public boolean IsTheCurrentGameSaved() {
    this.bRet = false;
    JavaEvents.SendEvent(25, 12, this);
    return this.bRet;
  }
  
  public boolean IsCompatibleGame(String exists_media, int GameType, int mediaType) {
    this.in_game_type = GameType;
    this.in_name0 = exists_media;
    this.in_media_type = mediaType;
    JavaEvents.SendEvent(25, 14, this);
    return this.bRet;
  }
}
