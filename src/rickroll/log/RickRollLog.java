package rickroll.log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import auxil.DInputStream2;
import auxil.XInputStreamCreate;
import rickroll.RickRollConfig;
import rnrcore.CoreTime;

public class RickRollLog {

	public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void log(String str) {
		if (!RickRollConfig.DEV) {
			return;
		}
		Date now = new Date();
		PrintWriter out = null;
		try {

			out = new PrintWriter(new FileOutputStream(new File(
					RickRollConfig.RICK_LOGS_DIR + "_RickRollLog_log.txt"),
					true));

			out.println(dateFormat.format(now) + " " + str);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static void logInputStream(String filename, InputStream inputStream) {
		BufferedOutputStream os;
		try {
			os = new BufferedOutputStream(
					new FileOutputStream(RickRollConfig.RICK_LOGS_DIR + filename));
		
			byte[] buffer = new byte[1024];
			int len;
		
			while ((len = inputStream.read(buffer)) > -1) {
				os.write(buffer, 0, len);
			}
		
			os.flush();
			os.close();
		} catch (Exception e) {
			RickRollLog.log("##### logInputStream(): " + e.getMessage());
		}
	}
	
	public static void logByteArray(String filename, byte[] dataArray) {
		if (!RickRollConfig.DEV) {
			return;
		}
		
		ByteArrayOutputStream ous = null;
  	  	FileInputStream ios = null;
		try {
			File mySave = new File(RickRollConfig.RICK_LOGS_DIR + filename);
			ous = new ByteArrayOutputStream();
		    ios = new FileInputStream(mySave);

			byte[] buffer = new byte[1024];
			int len;
		
			while ((len = ios.read(buffer)) > -1) {
				ous.write(buffer, 0, len);
			}
		
			ous.flush();
			
			ios.close();
			ous.close();
		} catch (Exception e) {
			RickRollLog.log("##### logByteArray(): " + e.getMessage());
		}
	}
	
	public static void writeDCProcessBytes(String filename, byte[] dataArray) {
		if (!RickRollConfig.DEV) {
			return;
		}
		FileOutputStream fos = null;
		try {
			RickRollLog.log("##### writeDCProcessBytes filename: " + filename);
			
			// Build absolute path
			String name = RickRollConfig.RICK_LOGS_DIR + filename;
			File myFile = new File(name);
			fos = new FileOutputStream(myFile);
			fos.write(dataArray);
			fos.close();
		} catch (Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}
	}
	
	public static void writeInputStream(byte[] dataArray) {
		if (!RickRollConfig.DEV) {
			return;
		}
		try {
			RickRollLog.log("##### writeInputStream for unkown byte stream");
			RickRollLog.writeInputStream("byteStream", new DInputStream2(
					new ByteArrayInputStream(dataArray)));
		} catch (Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}
	}

	public static void writeInputStream(String filename) {
		if (!RickRollConfig.DEV) {
			return;
		}
		try {
			RickRollLog.log("##### writeInputStream for param: " + filename);
			RickRollLog.writeInputStream(filename, new DInputStream2(
					XInputStreamCreate.open(filename)));
		} catch (Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}
	}

	public static void writeInputStream(String filename,
			DInputStream2 inputStream) {
		if (!RickRollConfig.DEV) {
			return;
		}
		try {
			// Get the name of the file
			// Relative path is expected ( ..\Data\config\scenario_values.xml )
			// File f = new File(filename);
			String name = null;

			if (filename.contains("..\\")) {
				int index = filename.lastIndexOf("..\\");
				name = filename.substring(index + 3, filename.length());
			} else {
				name = filename.substring(filename.lastIndexOf("\\") + 1,
						filename.length());
			}

			if (name == null) {
				throw new NullPointerException(
						"##### writeInputStream file name cannot be null");
			}

			// Build absolute path
			name = RickRollConfig.RICK_LOGS_DIR + name;

			File fileFull = new File(name);
			File fileParent = fileFull.getParentFile();
			if (!fileParent.exists()) {
				RickRollLog.log("##### writeInputStream Parent file for: "
						+ name + " does not exists, creating full tree");
				fileParent.mkdirs();
			}

			// RickRollLog.log("##### writeInputStream with inputStream for param: "
			// + filename + " . On: " + name);

			BufferedOutputStream os = new BufferedOutputStream(
					new FileOutputStream(name));
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;

			while ((len = inputStream.read(buffer)) > -1) {
				// baos.write(buffer, 0, len);
				os.write(buffer, 0, len);
			}

			os.flush();
			os.close();
			// baos.flush();
		} catch (Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}

	}

	public static String getVMFlags() {
		StringBuilder buf = new StringBuilder();
		try {
			RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
			List<String> arguments = runtimeMxBean.getInputArguments();

			for (String s : arguments) {
				buf.append(s + "\n");
			}
		} catch (Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}

		return buf.toString();
	}

	public static void dumpStackTrace(String traceOrigin) {
		if (!RickRollConfig.DEV) {
			return;
		}
		if (traceOrigin == null || traceOrigin.equals("")) {
			return;
		}
		
		Date now = new Date();
		PrintWriter out = null;
		try {

			out = new PrintWriter(new FileOutputStream(new File(
					RickRollConfig.RICK_LOGS_DIR + "_RickRollLog_log.txt"),
					true));

			out.println("---------------------------------------- " + dateFormat.format(now));
			Exception aux = new Exception(traceOrigin);
			aux.printStackTrace(out);
			out.println("----------------------------------------");
		} catch (FileNotFoundException e) {
			RickRollLog.log("##### " + e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	
	public static String coreTimeToString(CoreTime coreTime) {
		try {
			if(coreTime != null) {
				return String.format("%d/%d/%d %d::%d", coreTime.gYear(), coreTime.gMonth(), coreTime.gDate(), coreTime.gHour(), coreTime.gMinute());
			}
		} catch(Exception e) {
			RickRollLog.log("##### " + e.getMessage());
		}
		return null;
	}
}
