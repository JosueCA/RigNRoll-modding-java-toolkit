package rickroll.auxil;

//Decompiled with: CFR 0.152
//Class Version: 5
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XInputStreamCreate {
 static Pattern pattern = Pattern.compile("\\.[Xx][Mm][Ll]");

 public static InputStream open(String string) throws FileNotFoundException {
     FileInputStream fileInputStream;
     try {
         fileInputStream = new FileInputStream(string);
     }
     catch (FileNotFoundException fileNotFoundException) {
         fileInputStream = null;
     }
     Matcher matcher = pattern.matcher(string);
     String string2 = matcher.replaceAll(".pkqt");
     if (!string2.equals(string)) {
         FileInputStream fileInputStream2;
         try {
             fileInputStream2 = new FileInputStream(string2);
         }
         catch (FileNotFoundException fileNotFoundException) {
             fileInputStream2 = null;
         }
         if (fileInputStream2 == null && fileInputStream == null) {
             throw new FileNotFoundException();
         }
         if (fileInputStream2 != null && fileInputStream != null) {
             throw new FileNotFoundException();
         }
         if (fileInputStream2 != null) {
             return fileInputStream2;
         }
     }
     if (fileInputStream != null) {
         return fileInputStream;
     }
     throw new FileNotFoundException();
 }
}

