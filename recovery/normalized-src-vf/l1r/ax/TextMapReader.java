package l1r.ax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.LineageUtil;

public class TextMapReader extends MapReader {
   private static final Logger a = Logger.getLogger(TextMapReader.class.getName());
   private static final String b = "./maps/";

   @Override
   public L1Map a(int var1) throws IOException {
      LineNumberReader var2 = new LineNumberReader(new BufferedReader(new FileReader("./maps/" + var1 + ".txt")));
      int var3 = Integer.parseInt(var2.readLine());
      int var4 = Integer.parseInt(var2.readLine());
      int var5 = Integer.parseInt(var2.readLine());
      int var6 = Integer.parseInt(var2.readLine());
      short[][] var7 = new short[var4 - var3 + 1][var6 - var5 + 1];
      int var8 = 0;

      String var9;
      while ((var9 = var2.readLine()) != null) {
         if (var9.trim().length() != 0 && !var9.startsWith("#")) {
            int var10 = 0;

            for (StringTokenizer var11 = new StringTokenizer(var9, ","); var11.hasMoreTokens(); var10++) {
               short var12 = Short.parseShort(var11.nextToken());
               var7[var10][var8] = var12;
            }

            var8++;
         }
      }

      var2.close();
      System.out.println("Load Map= " + var1 + ".txt ......OK");
      return new L1Map((short)var1, var7, var3, var5);
   }

   @Override
   public Map<Integer, L1Map> a() throws IOException {
      HashMap var1 = new HashMap<>();

      for (int var3 : c()) {
         try {
            L1Map var5 = this.a(var3);
            var1.put(var3, var5);
         } catch (IOException var6) {
            a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         }
      }

      return var1;
   }

   public static List<Integer> c() {
      ArrayList var0 = new ArrayList<>();
      File var1 = new File("./maps/");
      String[] var5;
      int var4 = (var5 = var1.list()).length;

      for (int var3 = 0; var3 < var4; var3++) {
         String var2 = var5[var3];
         File var6 = new File(var1, var2);
         if (var6.exists() && LineageUtil.a(var6).toLowerCase().equals("txt")) {
            int var7 = 0;

            try {
               String var8 = LineageUtil.b(var6);
               var7 = Integer.parseInt(var8);
            } catch (NumberFormatException var9) {
               continue;
            }

            var0.add(var7);
         }
      }

      return var0;
   }
}
