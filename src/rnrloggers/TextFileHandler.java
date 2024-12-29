package rnrloggers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

final class TextFileHandler extends Handler {
  private static final String SEPARATOR = "======================================================";
  
  private FileOutputStream output = null;
  
  private PrintWriter writer = null;
  
  public TextFileHandler(String path, boolean append, Formatter formatter) throws IOException {
    this.output = new FileOutputStream(path, append);
    this.writer = new PrintWriter(this.output);
    this.writer.println("======================================================");
    this.writer.flush();
    this.output.flush();
    setFormatter(formatter);
  }
  
  public void publish(LogRecord record) {
    if (null == record)
      return; 
    Formatter formatter = getFormatter();
    if (null == formatter || (null != getFilter() && !getFilter().isLoggable(record)))
      return; 
    try {
      this.writer.print(formatter.format(record) + '\n');
      this.writer.flush();
      this.output.flush();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace(System.err);
    } 
  }
  
  public void flush() {
    try {
      this.writer.flush();
      this.output.flush();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace(System.err);
    } 
  }
  
  public void close() throws SecurityException {
    try {
      this.writer.close();
      this.output.close();
    } catch (IOException e) {
      System.err.println(e.getMessage());
      e.printStackTrace(System.err);
    } 
  }
}
