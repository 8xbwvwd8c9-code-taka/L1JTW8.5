package l1r.am;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.LineageUtil;

public class BadNamesList {
   private static final Logger a = Logger.getLogger(BadNamesList.class.getName());
   private static BadNamesList b;
   private final ArrayList<String> c = new ArrayList<>();

   public static BadNamesList a() {
      if (b == null) {
         b = new BadNamesList();
      }

      return b;
   }

   private BadNamesList() {
      LineNumberReader var1 = null;

      try {
         File var2 = new File("./data/badnames.txt");
         var1 = new LineNumberReader(new BufferedReader(new FileReader(var2)));
         String var3 = null;

         while ((var3 = var1.readLine()) != null) {
            if (var3.trim().length() != 0 && !var3.startsWith("#")) {
               StringTokenizer var4 = new StringTokenizer(var3, ";");

               while (var4.hasMoreTokens()) {
                  this.c.add(var4.nextToken());
               }
            }
         }

         LineageUtil.a(var1);
      } catch (FileNotFoundException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         LineageUtil.a(var1);
      }
   }

   public boolean a(String var1) {
      for (String var2 : this.c) {
         if (var1.toLowerCase().contains(var2.toLowerCase())) {
            return true;
         }
      }

      return false;
   }

   public String[] b() {
      return this.c.toArray(new String[this.c.size()]);
   }
}
