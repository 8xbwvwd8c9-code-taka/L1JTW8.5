package l1r.bc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1World;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class FishingTimer implements Runnable {
   private static final Logger a = Logger.getLogger(FishingTimer.class.getName());
   private final int b = 120;
   private final L1PcInstance c;
   private int d = 120;
   private boolean e = false;
   private final L1ItemInstance f;
   private final L1Location g;
   private static ArrayList<FishingTimer.a> h = new ArrayList<>();
   private static int i = 0;

   public FishingTimer(L1PcInstance var1, L1ItemInstance var2) {
      this.c = var1;
      this.g = var1.fu();
      this.c.a(this);
      this.f = var2;
      if (var2.N() == 640282) {
         this.d = 40;
         this.e = true;
      }
   }

   @Override
   public void run() {
      try {
         int var1 = 1;
         this.c.a(new S_ProtoBuffers(63, this.d, this.e ? 2 : 1));

         while (this.c != null && this.c.cq()) {
            if (!this.c.fu().equals(this.g)) {
               this.a();
               break;
            }

            if (var1 >= this.d) {
               var1 = 1;
               this.a(this.c);
            }

            var1++;
            Thread.sleep(1000L);
         }
      } catch (Exception var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
      }
   }

   private void a(L1PcInstance var1) {
      boolean var2 = false;
      int var3 = Random.a(i + i / 20);

      for (FishingTimer.a var4 : h) {
         if (var4.c <= var3 && var4.d >= var3) {
            L1ItemInstance var6 = ItemTable.a().b(var4.a);
            var6.e(var4.b);
            this.a(var6);
            var2 = true;
            break;
         }
      }

      if (!var2) {
         var1.a(new S_ServerMessage(1517));
      }

      if (!var1.j().b(640270, 1)) {
         var1.a(new S_ServerMessage(1137));
         this.a();
      } else {
         this.c();
         var1.a(new S_ProtoBuffers(63, this.d, this.e ? 2 : 1));
      }
   }

   private void a(L1ItemInstance var1) {
      this.c.a(new S_ServerMessage(403, var1.a().h()));
      this.c.x((int)(2.0 * Config.B));
      this.c.a(new S_OwnCharStatus(this.c));
      if (this.c.j().a(var1, 1) == 0) {
         this.c.j().d(var1);
         this.c.a(new S_PacketBox(55, var1.m()));
      } else {
         var1.d(this.c);
         L1World.a().a(this.c.fs(), this.c.ft(), this.c.fp()).d(var1);
         this.a();
      }
   }

   public void a() {
      this.c.r(false);
      this.c.a((FishingTimer)null);
      this.c.a(new S_CharVisualUpdate(this.c));
      this.c.b(new S_CharVisualUpdate(this.c));
   }

   private void c() {
      if (this.f.N() == 640282) {
         if (this.f.I() > 1) {
            this.f.g(this.f.I() - 1);
            this.c.j().b(this.f);
         } else {
            this.c.j().b(this.f, 1);
            ItemTable.a(this.c, 640269, 1);
            this.c.a(new S_ServerMessage(1163));
            this.a();
         }
      }
   }

   public static void b() {
      Connection var0 = null;
      PreparedStatement var1 = null;
      ResultSet var2 = null;

      try {
         var0 = DatabaseFactory.a().b();
         var1 = var0.prepareStatement("SELECT *FROM fishing");
         var2 = var1.executeQuery();

         while (var2.next()) {
            int var3 = var2.getInt("itemid");
            int var4 = var2.getInt("count");
            int var5 = var2.getInt("prab_value");
            FishingTimer.a var6 = new FishingTimer.a(var3, var4);
            var6.c = i + 1;
            i += var5;
            var6.d = i;
            h.add(var6);
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var2, var1, var0);
      }
   }

   private static class a {
      public int a;
      public int b;
      public int c = 0;
      public int d = 0;

      public a(int var1, int var2) {
         this.a = var1;
         this.b = var2;
      }
   }
}
