package rnrloggers;

import config.ApplicationFolders;

public final class PathHolder {
  private static String writablePath = ApplicationFolders.RCMDF();
  
  public static void setWritablePath(String path) {
    writablePath = path;
  }
  
  public static String getWritablePath() {
    return writablePath;
  }
}
