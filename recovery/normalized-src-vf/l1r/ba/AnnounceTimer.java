package l1r.ba;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.AnnounceTable;
import l1r.aq.L1World;
import l1r.bi.GeneralThreadPool;
import l1r.l1j.server.Config;

public class AnnounceTimer {
   private static AnnounceTimer a;
   private final CopyOnWriteArrayList<String> b = new CopyOnWriteArrayList<>();
   private int c = 0;

   public static AnnounceTimer a() {
      if (a == null) {
         a = new AnnounceTimer();
      }

      return a;
   }

   private AnnounceTimer() {
      GeneralThreadPool.a().a(new AnnounceTimer.L1R_a(null), 60000L, 60000 * Config.v);
   }

   private class L1R_a implements Runnable {
      private L1R_a() {
      }

      @Override
      public void run() {
         AnnounceTable.a().a(AnnounceTimer.this.b);
         if (Config.w) {
            SimpleDateFormat var1 = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            String var2 = var1.format(new Date());
            L1World.a().d("(" + var2 + ")");
         }

         Iterator var3 = AnnounceTimer.this.b.listIterator();
         if (var3.hasNext()) {
            AnnounceTimer.this.c = AnnounceTimer.this.c % AnnounceTimer.this.b.size();
            L1World.a().d(AnnounceTimer.this.b.get(AnnounceTimer.this.c));
            AnnounceTimer.this.c = AnnounceTimer.this.c + 1;
         }
      }

      // $VF: synthetic method
      L1R_a(AnnounceTimer.L1R_a var2) {
         this();
      }
   }
}
