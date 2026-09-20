package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Mail;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MailTable {
   private static final Logger a = Logger.getLogger(MailTable.class.getName());
   private static MailTable b;
   private static CopyOnWriteArrayList<L1Mail> c = new CopyOnWriteArrayList<>();

   public static MailTable a() {
      if (b == null) {
         b = new MailTable();
      }

      return b;
   }

   private MailTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM mail");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Mail var4 = new L1Mail();
            var4.a(var3.getInt("id"));
            var4.b(var3.getInt("type"));
            var4.a(var3.getString("sender"));
            var4.b(var3.getString("receiver"));
            var4.a(var3.getTimestamp("date"));
            var4.c(var3.getInt("read_status"));
            var4.a(var3.getBytes("subject"));
            var4.b(var3.getBytes("content"));
            var4.d(var3.getInt("inbox_id"));
            c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE mail SET read_status=1 WHERE id=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(L1Mail var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE mail SET type=? WHERE id=?");
         var3.setInt(1, var1.b());
         var3.setInt(2, var1.a());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM mail WHERE id=?");
         var3.setInt(1, var1);
         var3.execute();

         for (L1Mail var4 : c) {
            if (var4.a() == var1) {
               c.remove(var4);
               break;
            }
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public L1Mail a(int var1, L1PcInstance var2, L1PcInstance var3, byte[] var4, boolean var5) {
      int var6 = 0;
      int var7 = 0;

      for (int var8 = 0; var8 < var4.length; var8 += 2) {
         if (var4[var8] == 0 && var4[var8 + 1] == 0) {
            if (var6 == 0) {
               var6 = var8;
            } else if (var6 != 0 && var7 == 0) {
               var7 = var8;
               break;
            }
         }
      }

      int var21 = var6 + 2;
      int var9 = var7 - var6 + 1;
      if (var9 <= 0) {
         var9 = 1;
      }

      byte[] var10 = new byte[var21];
      byte[] var11 = new byte[var9];
      System.arraycopy(var4, 0, var10, 0, var21);
      System.arraycopy(var4, var21, var11, 0, var9);
      L1Mail var12 = new L1Mail();
      var12.a(IdFactory.a().d());
      var12.b(var1);
      var12.a(var3.et());
      var12.b(var2.et());
      var12.a(new Timestamp(System.currentTimeMillis()));
      var12.a(var10);
      var12.b(var11);
      var12.c(0);
      var12.d(var5 ? var3.fr() : var2.fr());
      Connection var13 = null;
      PreparedStatement var14 = null;

      try {
         var13 = DatabaseFactory.a().b();
         var14 = var13.prepareStatement("INSERT INTO mail SET id=?, type=?, sender=?, receiver=?, date=?, read_status=?, subject=?, content=?, inbox_id=?");
         var14.setInt(1, var12.a());
         var14.setInt(2, var12.b());
         var14.setString(3, var3.et());
         var14.setString(4, var2.et());
         var14.setTimestamp(5, var12.e());
         var14.setInt(6, var12.f());
         var14.setBytes(7, var12.g());
         var14.setBytes(8, var12.h());
         var14.setInt(9, var5 ? var3.fr() : var2.fr());
         var14.execute();
         c.add(var12);
      } catch (SQLException var19) {
         a.log(Level.SEVERE, var19.getLocalizedMessage(), var19);
      } finally {
         SQLUtil.a(var14);
         SQLUtil.a(var13);
      }

      return var12;
   }

   public ArrayList<L1Mail> a(int var1, int var2) {
      ArrayList var3 = new ArrayList<>();

      for (L1Mail var4 : c) {
         if (var4.i() == var1 && var4.b() == var2) {
            var3.add(var4);
         }
      }

      return var3;
   }

   public L1Mail c(int var1) {
      for (L1Mail var2 : c) {
         if (var2.a() == var1) {
            return var2;
         }
      }

      return null;
   }
}
