package l1r.ax;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class L1WorldMap {
   private static final Logger a = Logger.getLogger(L1WorldMap.class.getName());
   private static L1WorldMap b;
   private Map<Integer, L1Map> c;

   public Map<Integer, L1Map> a() {
      return this.c;
   }

   public static L1WorldMap b() {
      if (b == null) {
         b = new L1WorldMap();
      }

      return b;
   }

   private L1WorldMap() {
      long var1 = System.currentTimeMillis();
      System.out.print("loading map...");

      try {
         this.c = MapReader.b().a();
         if (this.c == null) {
            throw new RuntimeException("地圖檔案讀取失敗...");
         }
      } catch (FileNotFoundException var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      } catch (Exception var5) {
         a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
         System.exit(0);
      }

      System.out.println("OK! " + (System.currentTimeMillis() - var1) + "ms");
   }

   public L1Map a(int var1) {
      L1Map var2 = this.c.get(var1);
      if (var2 == null) {
         System.out.println("[Error] get null map:" + var1);
         var2 = new L1Map();
      }

      return var2;
   }
}
