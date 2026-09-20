package l1r.aq;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import l1r.ap.L1PcInstance;

public class L1Buddy {
   private final int a;
   private final LinkedHashMap<Integer, String> b = new LinkedHashMap<>();

   public L1Buddy(int var1) {
      this.a = var1;
   }

   public int a() {
      return this.a;
   }

   public boolean a(int var1, String var2) {
      if (this.b.containsKey(var1)) {
         return false;
      }

      this.b.put(var1, var2);
      return true;
   }

   public boolean a(String var1) {
      int var2 = 0;

      for (Entry<Integer, String> var3 : this.b.entrySet()) {
         if (var1.equalsIgnoreCase(var3.getValue())) {
            var2 = var3.getKey();
            break;
         }
      }

      if (var2 == 0) {
         return false;
      }

      this.b.remove(var2);
      return true;
   }

   public String b() {
      String var1 = new String("");

      for (L1PcInstance var2 : L1World.a().c()) {
         if (this.b.containsKey(var2.fr())) {
            var1 = var1 + var2.et() + " ";
         }
      }

      return var1;
   }

   public String[] c() {
      return this.b.values().toArray(new String[0]);
   }

   public boolean b(String var1) {
      for (String var2 : this.b.values()) {
         if (var1.equalsIgnoreCase(var2)) {
            return true;
         }
      }

      return false;
   }
}
