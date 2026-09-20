package l1r.aq;

import java.util.concurrent.CopyOnWriteArrayList;

public class L1ExcludingList {
   private final CopyOnWriteArrayList<String> a = new CopyOnWriteArrayList<>();

   public String[] a() {
      return this.a.toArray(new String[this.a.size()]);
   }

   public void a(String var1) {
      this.a.add(var1);
   }

   public String b(String var1) {
      for (String var2 : this.a) {
         if (var2.equalsIgnoreCase(var1)) {
            this.a.remove(var2);
            return var2;
         }
      }

      return null;
   }

   public boolean c(String var1) {
      for (String var2 : this.a) {
         if (var2.equalsIgnoreCase(var1)) {
            return true;
         }
      }

      return false;
   }

   public boolean b() {
      return this.a.size() > 50;
   }
}
