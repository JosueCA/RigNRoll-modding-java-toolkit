package rickroll;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import auxil.DInputStream2;
import auxil.XInputStreamCreate;
import rickroll.log.RickRollLog;

public class TransformPKQTToXML {

	public static Pattern pattern = Pattern.compile("\\." + RickRollConfig.SOURCES_ENCODED_FILE_EXTENSIONS[0]);
	public static String encodedExtension = RickRollConfig.SOURCES_ENCODED_FILE_EXTENSIONS[0];
	public static String decodedExtension = RickRollConfig.SOURCES_DECODED_FILE_EXTENSIONS[0];
	
	public static void transformPKQTToXML(String file) {
		if(!RickRollConfig.DEV) {
			return;
		}
		try {
			File fileFull = new File(file);
			if(!fileFull.exists() || !fileFull.getName().endsWith(encodedExtension)) {
				RickRollLog.log("##### TransformPKQTToXML invalid file for file: " + file);
				return;
			}
			
			String xmlFileOutput = file
					.replace("." + encodedExtension, "." +decodedExtension)
					.replace(RickRollConfig.SOURCE_DATA_DIR, RickRollConfig.OUTPUT_DATA_DIR);
			
			RickRollLog.log("##### TransformPKQTToXML Creating XMLFile at: " + xmlFileOutput);
			File xmlFile = new File(xmlFileOutput);
			
			File fileParent = xmlFile.getParentFile();
			if(!fileParent.exists()) {
				RickRollLog.log("##### TransformPKQTToXML Parent file for: " + file + " does not exists, creating full tree");
				fileParent.mkdirs();
			}
			
			DInputStream2 inputStream = new DInputStream2(
					XInputStreamCreate.open(fileFull.getAbsolutePath()));
			BufferedOutputStream os = new BufferedOutputStream(
					new FileOutputStream(xmlFile));
			
			byte[] buffer = new byte[1024];
			int len;

			while ((len = inputStream.read(buffer)) > -1) {
				os.write(buffer, 0, len);
			}

			os.flush();
			os.close();
			
			inputStream.close();
			RickRollLog.log("##### TransformPKQTToXML Created XMLFile at: " + xmlFile.getAbsolutePath());
		} catch (Exception e) {
			RickRollLog.log("##### TransformPKQTToXML exception: " +e.getMessage());
		}
		
		
	}
}
