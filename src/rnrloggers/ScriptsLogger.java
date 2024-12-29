package rnrloggers;

import java.io.IOException;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import rickroll.log.RickRollLog;

public final class ScriptsLogger {
  private Logger log = null;
  
  private static final String LOGGER_NAME = "rnr.ScriptsLogger";
  
  private static final String FOLDER = PathHolder.getWritablePath() + "warnings\\";
  
  public static final int JOURNAL_LOG_MARK = 0;
  
  public static final int ORGANIZER_LOG_MARK = 1;
  
  public static final int MISSION_POINT_LOG_MARK = 2;
  
  public static final int SAVING_SYSTEM_LOG_MARK = 3;
  
  public static final int GENERAL_LOG_MARK = 4;
  
  private static final SimpleFormatter FORMATTER = new SimpleFormatter();
  
  private static final ScriptsLogger ourInstance = new ScriptsLogger();
  
  public static ScriptsLogger getInstance() {
    return ourInstance;
  }
  
  private ScriptsLogger() {
	  RickRollLog.log("ScriptsLogger silenced. ");
	  this.log = Logger.getLogger("rnr.ScriptsLogger");
	  this.log.setUseParentHandlers(false);
      // RICK
//    try {
//      TextFileHandler mainHandler = new TextFileHandler(FOLDER + "scriptsJavaLog.log", false, FORMATTER);
//      mainHandler.setFilter(new Filter() {
//            public boolean isLoggable(LogRecord record) {
//              return true;
//            }
//          });
//      TextFileHandler journalHandler = new TextFileHandler(FOLDER + "journalJava.log", false, FORMATTER);
//      journalHandler.setFilter(new IntegerFilter(0));
//      TextFileHandler organizerHandler = new TextFileHandler(FOLDER + "organizerJava.log", false, FORMATTER);
//      organizerHandler.setFilter(new IntegerFilter(1));
//      TextFileHandler mpHandler = new TextFileHandler(FOLDER + "mpJava.log", false, FORMATTER);
//      mpHandler.setFilter(new IntegerFilter(2));
//      TextFileHandler savingSystem = new TextFileHandler(FOLDER + "saving.log", false, FORMATTER);
//      savingSystem.setFilter(new IntegerFilter(3));
//      TextFileHandler generalHandler = new TextFileHandler(FOLDER + "general.log", false, FORMATTER);
//      generalHandler.setFilter(new IntegerFilter(4));
//      this.log.addHandler(mainHandler);
//      this.log.addHandler(journalHandler);
//      this.log.addHandler(organizerHandler);
//      this.log.addHandler(mpHandler);
//      this.log.addHandler(savingSystem);
//      this.log.addHandler(generalHandler);
//    } catch (IOException exception) {
//      System.err.println(exception.getLocalizedMessage());
//      exception.printStackTrace(System.err);
//    } 
  }
  
  public void log(Level level, int systemID, String message) {
	  RickRollLog.log("ScriptsLogger silenced. " + message );
//    if (null != level && null != message)
//      this.log.log(level, message, Integer.valueOf(systemID)); 
  }
  
  public Logger getLog() {
    return this.log;
  }
}
