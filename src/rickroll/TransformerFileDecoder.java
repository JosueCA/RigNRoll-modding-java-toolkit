package rickroll;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import auxil.DInputStream2;
import auxil.XInputStreamCreate;
import rickroll.log.RickRollLog;

public class TransformerFileDecoder {

	public Pattern pattern = null;
	public String encodedFileExtension = null;
	public String decodedFileExtension = null;
	
	public TransformerFileDecoder(String encodedFileExtension, String decodedFileExtension) {
		this.encodedFileExtension = encodedFileExtension;
		this.decodedFileExtension = decodedFileExtension;
		this.pattern = Pattern.compile("\\." + encodedFileExtension);
	}
	
	public void transform(String file) {
		if(!RickRollConfig.DECODE_FILES) {
			return;
		}
		if(this.decodedFileExtension == null || this.encodedFileExtension == null || this.pattern == null) {
			return;
		}
		InputStream fileStream = null;
		DInputStream2 inputStream = null;
		BufferedOutputStream os = null;
		try {
			File fileFull = new File(file);
			if(!fileFull.exists() || !fileFull.getName().endsWith(encodedFileExtension)) {
				RickRollLog.log("##### TransformerFileDecoder invalid file for file: " + file);
				return;
			}
			
			String fileOutput = file
					.replace("." + encodedFileExtension, "." +decodedFileExtension)
					.replace(RickRollConfig.SOURCE_DATA_DIR, RickRollConfig.OUTPUT_DATA_DIR);
			
			RickRollLog.log("##### TransformerFileDecoder Creating at: " + fileOutput);
			File myFile = new File(fileOutput);
			
			File fileParent = myFile.getParentFile();
			if(!fileParent.exists()) {
				RickRollLog.log("##### TransformerFileDecoder Parent file for: " + file + " does not exists, creating full tree");
				fileParent.mkdirs();
			}
			
			fileStream = XInputStreamCreate.open(fileFull.getAbsolutePath());
			inputStream = new DInputStream2(fileStream);
			os = new BufferedOutputStream(
					new FileOutputStream(myFile));
			
			byte[] buffer = new byte[1024];
			int len;

			while ((len = inputStream.read(buffer)) > -1) {
				os.write(buffer, 0, len);
			}

			os.flush();
			os.close();
			
			RickRollLog.log("##### TransformerFileDecoder Created file at: " + myFile.getAbsolutePath());
		} catch (Exception e) {
			RickRollLog.log("##### TransformerFileDecoder exception: " +e.getMessage());
		} finally {
			try {
			if(os != null) {
				os.close();
			}
			if(fileStream != null) {
				fileStream.close();
			}
			if(inputStream != null) {
				inputStream.close();
			}
			} catch(Exception e) {
				RickRollLog.log("##### TransformerFileDecoder exception: " +e.getMessage());
			}
		}
		
		
	}
}
