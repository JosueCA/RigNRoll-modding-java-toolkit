package rnrcore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import auxil.DInputStream2;
import rickroll.RickRollConfig;
import rickroll.log.RickRollLog;
import rnrloggers.ScriptsLogger;
import rnrscenario.Sc_serial;

public final class NativeSerializationInterface {
  private static final boolean USE_SERIALIZABLES = false;
  
  private static GameXmlSerializator xmlSerializator = null;
  
  private static List<ISelfSerializable> binarySerializableTargets = new LinkedList<ISelfSerializable>();
  
  public static void setGameSerializator(GameXmlSerializator saver) {
    if (null != saver) {
      xmlSerializator = saver;
    } else {
      throw new IllegalArgumentException("saver must be non-null reference");
    } 
  }
  
  public static void addSelfSerializable(ISelfSerializable target) {
    if (null != target) {
      binarySerializableTargets.add(target);
    } else {
      throw new IllegalArgumentException("target must be non-null reference");
    } 
  }
  
  public static byte[] serialize(int saveVersion) {
    Sc_serial.getInstance().recieve();
    xmlSerializator.setSave_version(saveVersion);
    try {
      byte[] xmlData = xmlSerializator.saveToByteArray();
      ByteArrayOutputStream binarySerialized = new ByteArrayOutputStream();
      ObjectOutputStream mainStream = new ObjectOutputStream(binarySerialized);
      mainStream.writeObject(xmlData);
      mainStream.close();
      binarySerialized.close();
      return binarySerialized.toByteArray();
    } catch (IOException exception) {
    	RickRollLog.dumpStackTrace("Exception silenced. " + exception.getMessage());
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario serialization failed");
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
//      exception.printStackTrace(System.err);
//      System.err.flush();
      return new byte[0];
    } 
  }
  
  private static void closeObject(Closeable target) {
    try {
      if (null != target)
        target.close(); 
    } catch (IOException exception) {
    	RickRollLog.dumpStackTrace("Exception silenced. " + exception.getMessage());
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
//      exception.printStackTrace(System.err);
//      System.err.flush();
    } 
  }
  
  // Gets called AFTER rnrscenario.scenarioscript.loadGame()
  // There is no stack trace so it is called from native code??
  public static void deserialize(byte[] dataArray) {
    if (null == dataArray) {
      String errorMessgae = "NativeSerializationInterface.deserialize erorr: dataArray is null";
      ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
      eng.err(errorMessgae);
      return;
    } 
    if (0 == dataArray.length) {
      String errorMessgae = "NativeSerializationInterface.deserialize erorr: dataArray has 0 length";
      ScriptsLogger.getInstance().log(Level.SEVERE, 3, errorMessgae);
      eng.err(errorMessgae);
      return;
    } 
    
    
    ByteArrayInputStream byteStream = null;
    ObjectInputStream reader = null;
    try {
      File mySave = new File(RickRollConfig.RICK_LOGS_DIR + "GameSave_copy.xml");
      if(RickRollConfig.DEV && mySave.exists()) {
    	  RickRollLog.log("NativeSerializationInterface deserialize(dataArray); Loading my save");
    	  byte[] buffer = new byte[1024];
    	  ByteArrayOutputStream ous = null;
    	  FileInputStream ios = null;
    	  InputStream targetStream = null;
    	  try {
	    	  ous = new ByteArrayOutputStream();
		      ios = new FileInputStream(mySave);
		      
		      int read = 0;
		      while ((read = ios.read(buffer)) != -1) {
		    	  ous.write(buffer, 0, read);
		      }
		      
		      targetStream = new ByteArrayInputStream(ous.toByteArray());
	    	  RickRollLog.logInputStream("test2.xml", targetStream);

	    	  try {
	    		  
				// RickRollLog.logByteArray("test4.xml", ous.toByteArray());
				// Actual loading of save file
				xmlSerializator.loadFromByteArray(ous.toByteArray());
	          
	    	  } catch(Exception e) {
	    		  RickRollLog.dumpStackTrace("NativeSerializationInterface deserialize(dataArray) 2222;" + e.getMessage());
	    	  }
		      
    	  } catch(Exception e) {
    		  RickRollLog.dumpStackTrace("NativeSerializationInterface deserialize(dataArray) 1111;" + e.getMessage());
    	  } finally {
    		  closeObject(targetStream);
    		  closeObject(ios);
    		  closeObject(ous);
    	  }
          
          
      } else {
    	  if(RickRollConfig.DUMP_GAMESAVE_ONLOAD) {
	    	  InputStream targetStream = new ByteArrayInputStream(dataArray);
	    	  RickRollLog.logInputStream("current_loaded_gameSave.xml", targetStream);
	    	  targetStream.close();
    	  }
    	  
    	  byteStream = new ByteArrayInputStream(dataArray);
          reader = new ObjectInputStream(byteStream);
          byte[] xmlTextData = (byte[])reader.readObject();
          
          // Actual loading of save file
          xmlSerializator.loadFromByteArray(xmlTextData);
      }
    } catch (ClassNotFoundException exception) {
    	RickRollLog.dumpStackTrace("Exception silenced." + exception.getMessage());
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario deserialization failed");
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
//      exception.printStackTrace(System.err);
//      System.err.flush();
    } catch (IOException exception) {
    	RickRollLog.dumpStackTrace("Exception silenced." + exception.getMessage());
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, "scenario deserialization failed");
//      ScriptsLogger.getInstance().log(Level.SEVERE, 3, exception.getMessage());
//      exception.printStackTrace(System.err);
//      System.err.flush();
    } catch (Throwable e) {
    	RickRollLog.dumpStackTrace("Exception silenced." + e.getMessage());
//      e.printStackTrace(System.err);
//      if (!eng.noNative)
//        eng.exceptionDuringGameSerialization(); 
    } finally {
      closeObject(reader);
      closeObject(byteStream);
    } 
  }
}
