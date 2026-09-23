package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Account;
import l1r.bi.GeneralThreadPool;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class AccountTable {
   private static final Logger b = Logger.getLogger(AccountTable.class.getName());
   private static AccountTable c;
   public final ConcurrentHashMap<String, ClientThread> a = new ConcurrentHashMap<>();

   public static AccountTable a() {
      if (c == null) {
         c = new AccountTable();
      }

      return c;
   }

   private AccountTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE accounts SET online=0, OnlineStatus=0 WHERE online=1 OR OnlineStatus=1");
         var2.execute();
      } catch (SQLException var7) {
         b.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public L1Account a(String var1, String var2, String var3, String var4) {
      Connection var5 = null;
      PreparedStatement var6 = null;

      try {
         L1Account var7 = new L1Account();
         var7.c(var1);
         var7.e(L1Account.b(var2));
         var7.d(var3);
         var7.f(var4);
         var7.g(119);
         var5 = DatabaseFactory.a().b();
         String var8 = "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,online=?,banned=?,character_slot=?,OnlineStatus=?,WorldShopAdena=?,TamPoint=?";
         var6 = var5.prepareStatement(
            "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,online=?,banned=?,character_slot=?,OnlineStatus=?,WorldShopAdena=?,TamPoint=?"
         );
         var6.setString(1, var7.d());
         var6.setString(2, var7.f());
         var6.setTimestamp(3, var7.g());
         var6.setInt(4, 0);
         var6.setString(5, var7.e());
         var6.setString(6, var7.i());
         var6.setInt(7, 0);
         var6.setBoolean(8, var7.j());
         var6.setBoolean(9, var7.n());
         var6.setBoolean(10, var7.o());
         var6.setInt(11, var7.p());
         var6.setInt(12, var7.q());
         var6.execute();
         System.out.println("created new account for " + var1);
         return var7;
      } catch (SQLException var13) {
         b.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5);
      }

      return null;
   }

   public boolean a(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM accounts WHERE ip=?");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         var4.last();
         return var4.getRow() < 6;
      } catch (SQLException var10) {
         b.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return false;
   }

   public boolean b(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM accounts WHERE host=?");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         var4.last();
         return var4.getRow() < 6;
      } catch (SQLException var10) {
         b.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return false;
   }

   public L1Account c(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         String var5 = "SELECT * FROM accounts WHERE login=? LIMIT 1";
         var3 = var2.prepareStatement("SELECT * FROM accounts WHERE login=? LIMIT 1");
         var3.setString(1, var1);
         var4 = var3.executeQuery();
         if (var4.next()) {
            L1Account var6 = new L1Account();
            var6.c(var4.getString("login"));
            var6.e(var4.getString("password"));
            var6.a(var4.getTimestamp("lastactive"));
            var6.d(var4.getInt("access_level"));
            var6.d(var4.getString("ip"));
            var6.f(var4.getString("host"));
            var6.a(var4.getBoolean("banned"));
            var6.c(var4.getBoolean("online"));
            var6.e(var4.getInt("character_slot"));
            var6.f(var4.getInt("warepassword"));
            var6.d(var4.getBoolean("OnlineStatus"));
            var6.g(var4.getInt("WorldShopAdena"));
            var6.h(var4.getInt("TamPoint"));
            return var6;
         }
      } catch (SQLException var11) {
         b.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
         return null;
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return null;
   }

   public void a(L1Account var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE accounts SET WorldShopAdena=? ,access_level=? WHERE login=?");
         var3.setInt(1, var1.p());
         var3.setInt(2, var1.h());
         var3.setString(3, var1.d());
         var3.execute();
      } catch (Exception var8) {
         b.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(L1Account var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      Timestamp var4 = new Timestamp(System.currentTimeMillis());

      try {
         var2 = DatabaseFactory.a().b();
         String var5 = "UPDATE accounts SET lastactive=?, ip=? ,online=1 WHERE login = ?";
         var3 = var2.prepareStatement("UPDATE accounts SET lastactive=?, ip=? ,online=1 WHERE login = ?");
         var3.setTimestamp(1, var4);
         var3.setString(2, var1.e());
         var3.setString(3, var1.d());
         var3.execute();
         var1.a(var4);
      } catch (Exception var9) {
         b.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void c(L1Account var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE accounts SET character_slot=? WHERE login=?");
         var3.setInt(1, var1.k());
         var3.setString(2, var1.d());
         var3.execute();
      } catch (Exception var8) {
         b.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void d(L1Account var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE accounts SET TamPoint=? WHERE login=?");
         var3.setInt(1, var1.q());
         var3.setString(2, var1.d());
         var3.execute();
      } catch (Exception var8) {
         b.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public synchronized void a(L1Account var1, boolean var2) {
      if (var1 != null) {
         Connection var3 = null;
         PreparedStatement var4 = null;

         try {
            var3 = DatabaseFactory.a().b();
            var4 = var3.prepareStatement("UPDATE accounts SET online=?,ip=?,host=? WHERE login=?");
            var4.setBoolean(1, var2);
            var4.setString(2, var1.e());
            var4.setString(3, var1.i());
            var4.setString(4, var1.d());
            var4.execute();
            var1.c(var2);
         } catch (SQLException var9) {
            AccountTable.b.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         } finally {
            SQLUtil.a(var4);
            SQLUtil.a(var3);
         }
      }
   }

   public synchronized void b(L1Account var1, boolean var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("UPDATE accounts SET OnlineStatus=? WHERE login=?");
         var4.setBoolean(1, var2);
         var4.setString(2, var1.d());
         var4.execute();
         var1.d(var2);
      } catch (SQLException var9) {
         AccountTable.b.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   public void d(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE accounts SET banned=1, online=0, OnlineStatus=0 WHERE login=?");
         var3.setString(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         b.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(L1Account var1, int var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("UPDATE accounts SET warepassword = ? WHERE login = ?");
         var4.setInt(1, var2);
         var4.setString(2, var1.d());
         var4.execute();
         var1.f(var2);
      } catch (SQLException var9) {
         b.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   private void a(final ClientThread var1) {
      if (var1 != null) {
         GeneralThreadPool.a().b(new Runnable() {
            @Override
            public void run() {
               if (var1.f() != null) {
                  var1.f().a(new S_ServerMessage(357));
               }

               try {
                  Thread.sleep(1000L);
               } catch (Exception var2) {
                  AccountTable.b.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
               }

               var1.c();
            }
         });
      }
   }

   public synchronized void a(L1Account var1, ClientThread var2) {
      if (!var1.l()) {
         throw new IllegalArgumentException("帳戶沒有通過認證");
      }

      if (this.a.size() >= Config.o && !var1.a()) {
         throw new IllegalArgumentException("線上人數已經飽和，切斷 (" + var2.g() + ") 的連線。");
      }

      if (this.a.containsKey(var1.d())) {
         this.a(this.a.remove(var1.d()));
         throw new IllegalArgumentException("同個帳號已經登入，切斷 (" + var2.g() + ") 的連線。");
      }

      this.a.put(var1.d(), var2);
      this.a(var1, true);
   }

   public synchronized void e(String var1) {
      if (var1 != null) {
         this.a.remove(var1);
      }
   }
}
