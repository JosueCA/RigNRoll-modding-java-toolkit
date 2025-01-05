/*
 * Decompiled with CFR 0.151.
 */
package config;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class ApplicationFolders {
    private static String _GCMDFW = ".\\";
    private static String _APPNAMEW = "RigNRoll";

    private static void close(Closeable target) {
        if (null != target) {
            try {
                target.close();
            }
            catch (IOException e) {
                System.err.print(e.getMessage());
                e.printStackTrace(System.err);
            }
        }
    }

    public static String GCMDFW() {
        return _GCMDFW;
    }

    public static String GCCFW() {
        return "..\\Data\\config\\";
    }

    public static String GCBFW() {
        return "..\\Bin\\";
    }

    public static String RCMDFW() {
        return ApplicationFolders.GCMDFW();
    }

    public static String RCCFW() {
        return ApplicationFolders.GCCFW();
    }

    public static String RCBFW() {
        return ApplicationFolders.GCBFW();
    }

    public static String GCMDF() {
        return ApplicationFolders.GCMDFW();
    }

    public static String GCCF() {
        return ApplicationFolders.GCCFW();
    }

    public static String GCBF() {
        return ApplicationFolders.GCBFW();
    }

    public static String RCMDF() {
        return ApplicationFolders.GCMDFW();
    }

    public static String RCCF() {
        return ApplicationFolders.GCCFW();
    }

    public static String RCBF() {
        return ApplicationFolders.GCBFW();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    static {
        BufferedReader bufferedReaderFromFile = null;
        FileInputStream streamFromFile = null;
        InputStreamReader inputStreamReader = null;
        try {
            streamFromFile = new FileInputStream("APPNAMEW.txt");
            inputStreamReader = new InputStreamReader((InputStream)streamFromFile, "UTF-16LE");
            bufferedReaderFromFile = new BufferedReader(inputStreamReader);
            _APPNAMEW = bufferedReaderFromFile.readLine();
            _APPNAMEW = _APPNAMEW.trim();
        }
        catch (FileNotFoundException e) {
            ApplicationFolders.close(bufferedReaderFromFile);
            ApplicationFolders.close(streamFromFile);
            ApplicationFolders.close(inputStreamReader);
        }
        catch (IOException e2) {
            e2.printStackTrace(System.err);
            {
                catch (Throwable throwable) {
                    ApplicationFolders.close(bufferedReaderFromFile);
                    ApplicationFolders.close(streamFromFile);
                    ApplicationFolders.close(inputStreamReader);
                    throw throwable;
                }
            }
            ApplicationFolders.close(bufferedReaderFromFile);
            ApplicationFolders.close(streamFromFile);
            ApplicationFolders.close(inputStreamReader);
        }
        ApplicationFolders.close(bufferedReaderFromFile);
        ApplicationFolders.close(streamFromFile);
        ApplicationFolders.close(inputStreamReader);
        _GCMDFW = System.getProperty("user.home") + "\\" + _APPNAMEW + "\\GameWorld\\";
    }
}

