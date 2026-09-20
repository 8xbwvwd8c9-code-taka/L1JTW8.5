package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class HistoryTable {
   private static final Logger a = Logger.getLogger(HistoryTable.class.getName());
   private static HistoryTable b;

   public static HistoryTable a() {
      if (b == null) {
         b = new HistoryTable();
      }

      return b;
   }

   private HistoryTable() {
      long var1 = System.currentTimeMillis();
      System.out.print("cleaning History Tables...");
      this.a("history_chat", 3);
      this.a("history_enchant", 7);
      this.a("history_trade", 7);
      this.a("history_pickup", 7);
      this.a("history_resolvent", 7);
      this.a("history_sell", 3);
      this.a("history_warehouse", 3);
      this.a("history_warehouse_clan", 7);
      this.a("history_warehouse_elf", 7);
      this.a("history_world_shop", 15);
      System.out.println("OK! " + (System.currentTimeMillis() - var1) + " ms");
   }

   public void a(L1PcInstance var1, String var2, L1ItemInstance var3) {
      this.a("history_enchant", var1, var2, var3, 1);
   }

   public void a(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_give", var1, var2, var3, var4);
   }

   public void b(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_trade", var1, var2, var3, var4);
   }

   public void c(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_pickup", var1, var2, var3, var4);
   }

   public void b(L1PcInstance var1, String var2, L1ItemInstance var3) {
      this.a("history_resolvent", var1, var2, var3, 1);
   }

   public void d(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_sell", var1, var2, var3, var4);
   }

   public void e(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_warehouse", var1, var2, var3, var4);
   }

   public void f(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_warehouse_clan", var1, var2, var3, var4);
   }

   public void g(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_warehouse_elf", var1, var2, var3, var4);
   }

   public void h(L1PcInstance var1, String var2, L1ItemInstance var3, int var4) {
      this.a("history_world_shop", var1, var2, var3, var4);
   }

   private void a(String var1, L1PcInstance var2, String var3, L1ItemInstance var4, int var5) {
      Connection var6 = null;
      PreparedStatement var7 = null;

      try {
         var6 = DatabaseFactory.a().b();
         var7 = var6.prepareStatement("INSERT INTO " + var1 + " SET account=?, char_name=?, type=?, itemid=?, description=?, record_time=?");
         var7.setString(1, var2.bc());
         var7.setString(2, var2.eu());
         var7.setString(3, var3);
         var7.setInt(4, var4.N());
         String var8 = this.a(var4) + " " + var5 + "個";
         var7.setString(5, var8);
         var7.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
         var7.execute();
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var7);
         SQLUtil.a(var6);
      }
   }

   public void a(L1PcInstance var1, String var2, String var3) {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("INSERT INTO history_chat SET account=?, char_name=?, type=?, description=?, record_time=?");
         var5.setString(1, var1.bc());
         var5.setString(2, var1.eu());
         var5.setString(3, var2);
         var5.setString(4, var3);
         var5.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
         var5.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   private void a(String var1, int var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("DELETE FROM " + var1 + " WHERE  record_time < ?");
         var4.setTimestamp(1, new Timestamp(System.currentTimeMillis() - var2 * 86400 * 1000));
         var4.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   private String a(L1ItemInstance var1) {
      StringBuilder var2 = new StringBuilder();
      if (var1.F() == 0) {
         var2.append("祝福的 ");
      } else if (var1.F() == 2) {
         var2.append("詛咒的 ");
      }

      if (var1.g()) {
         int var3 = var1.L();
         if (var3 > 0) {
            if (var1.K() == 1) {
               var2.append(new String[]{"", "地之", "崩裂", "地靈", "輝岩", "馬普勒"}[var3]);
            } else if (var1.K() == 2) {
               var2.append(new String[]{"", "火之", "烈焰", "火靈", "赤炎", "帕格里奧"}[var3]);
            } else if (var1.K() == 4) {
               var2.append(new String[]{"", "水之", "海嘯", "水靈", "霜凍", "伊娃"}[var3]);
            } else if (var1.K() == 8) {
               var2.append(new String[]{"", "風之", "暴風", "風靈", "蒼嵐", "沙哈"}[var3]);
            }
         }
      }

      if (var1.g() || var1.h()) {
         if (var1.G() >= 0) {
            var2.append("+" + var1.G() + " ");
         } else if (var1.G() < 0) {
            var2.append(String.valueOf(var1.G()) + " ");
         }
      }

      if (var1.N() == 21363 && var1.G() >= 10) {
         var2.append("絕對奪魂T恤(魔法)");
      } else if (var1.N() == 21364 && var1.G() >= 10) {
         var2.append("絕對奪魂T恤(近戰)");
      } else if (var1.N() == 21365 && var1.G() >= 10) {
         var2.append("絕對奪魂T恤(遠攻)");
      } else {
         var2.append(var1.b());
      }

      if (var1.a().aM() > 0) {
         var2.append(" (" + var1.I() + ")");
      }

      if (var1.N() == 20383) {
         var2.append(" (" + var1.I() + ")");
      }

      if (var1.a().T() > 0 && !var1.f()) {
         var2.append(" [" + var1.M() + "]");
      }

      if (var1.N() == 640615 && var1.M() != 0) {
         var2.append(" -" + (var1.M() - 1399));
      }

      if (var1.bb() != null) {
         SimpleDateFormat var5 = new SimpleDateFormat(" [MM-dd HH:mm]");
         String var4 = var5.format(var1.bb());
         var2.append(var4);
      }

      if (var1.N() == 40312 && var1.M() != 0) {
         var2.append(InnTable.a(var1));
      }

      return var2.toString();
   }
}
