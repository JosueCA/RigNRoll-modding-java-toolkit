/*
 * Decompiled with CFR 0.151.
 */
package auxil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XInputStreamCreate {
    static Pattern pattern = Pattern.compile("\\.[Xx][Mm][Ll]");

    public static InputStream open(String path2) throws FileNotFoundException {
        FileInputStream f = null;
        try {
            f = new FileInputStream(path2);
        }
        catch (FileNotFoundException e) {
            f = null;
        }
        Matcher m = pattern.matcher(path2);
        String rpath = m.replaceAll(".pkqt");
        if (!rpath.equals(path2)) {
            FileInputStream fs = null;
            try {
                fs = new FileInputStream(rpath);
            }
            catch (FileNotFoundException e) {
                fs = null;
            }
            if (fs == null && f == null) {
                throw new FileNotFoundException();
            }
            if (fs != null && f != null) {
                throw new FileNotFoundException();
            }
            if (fs != null) {
                return fs;
            }
        }
        if (f != null) {
            return f;
        }
        throw new FileNotFoundException();
    }
}

