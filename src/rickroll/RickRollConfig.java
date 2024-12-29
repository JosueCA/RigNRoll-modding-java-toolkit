package rickroll;

import java.util.ArrayList;
import java.util.Arrays;

public class RickRollConfig {

	public static boolean DEV = false;
	
	// GAME SAVES
	// dump file as 'current_loaded_gameSave.xml'
	// rename to 'GameSave_copy.xml' to load this XML instead, MUST SELECT THE SAME LOAD FROM GAME
	// THAT WAS USED TO DUMP THE FILE
	public static boolean DUMP_GAMESAVE_ONLOAD = false;
	
	// END GAME SAVES
	
	// KEEP IN SYNC
	public static boolean DECODE_FILES = false;
	public static String[] SOURCES_ENCODED_FILE_EXTENSIONS = {
		"pkqt",
		"cnf",
		"cfg",
		"CFG",
		// "ssn" // TESTED NOT DECODED
		//"wdb" // TESTED NOT DECODED
		// "xml" // TESTED NOT DECODED
		// "tech" // TESTED NOT DECODED
	};
	public static String[] SOURCES_DECODED_FILE_EXTENSIONS = {
		"xml",
		"cnf",
		"cfg",
		"CFG",
		// "ssn_decoded"
		// "wdb_decoded"
		// "xml"
		// "tech"
	};
	// KEEP IN SYNC
	
	public static String RICK_LOGS_DIR = "C:\\RICK\\";
    
	public static String SOURCE_DATA_DIR = "C:\\Program Files\\1C\\RigNRoll\\";
	
	public static String OUTPUT_DATA_DIR = "C:\\RICK\\";
	
	public static ArrayList<String> SOURCES_DATA_DIR_LIST = new ArrayList<String>(Arrays.asList(
			"C:\\Program Files\\1C\\RigNRoll\\Data", 
			"C:\\Program Files\\1C\\RigNRoll\\MonsterCup", 
			"C:\\Program Files\\1C\\RigNRoll\\Trucks",
			"C:\\Program Files\\1C\\RigNRoll\\GameWorld"
			));
	
	// Pedestrians
	// Pedestrian works but they have too many bugs
	// teleports, colissions...
	public static boolean ENABLE_PEDESTRIANS = false;
}
