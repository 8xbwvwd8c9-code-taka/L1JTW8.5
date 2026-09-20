package l1r.ai;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class IdFactory {
   private static final Logger a = Logger.getLogger(IdFactory.class.getName());
   private int b;
   private int c = 16;
   private final Object d = new Object();
   private static final int e = 268435456;
   private static IdFactory f = new IdFactory();
   private final AtomicInteger g = new AtomicInteger(251658240);

   private IdFactory() {
      this.e();
   }

   public static IdFactory a() {
      return f;
   }

   public int b() {
      this.g.compareAndSet(268435455, 251658240);
      return this.g.getAndIncrement();
   }

   public int c() {
      synchronized (this.d) {
         int var2 = this.c++;
         if (var2 == 268435455) {
            System.out.println("IdFactory for Npc out of range");
         }

         return var2++;
      }
   }

   public int d() {
      synchronized (this.d) {
         return this.b++;
      }
   }

   private void e() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement(
            "select max(id)+1 as nextid from (select id from character_items union all select id from character_teleport union all select id from character_warehouse union all select id from character_elf_warehouse union all select id from clan_warehouse union all select id from mail union all select objid as id from characters union all select clan_id as id from clan_data union all select objid as id from pets ) t"
         );
         var3 = var2.executeQuery();
         int var4 = 0;
         if (var3.next()) {
            var4 = var3.getInt("nextid");
         }

         if (var4 < 268435456) {
            var4 = 268435456;
         }

         this.b = var4;
         System.out.println("目前的物件ID: " + this.b);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }
}
