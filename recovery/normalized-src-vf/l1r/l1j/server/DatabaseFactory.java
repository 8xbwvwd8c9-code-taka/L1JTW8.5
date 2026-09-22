package l1r.l1j.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseFactory {
   private static final Logger b = Logger.getLogger(DatabaseFactory.class.getName());
   private static DatabaseFactory c;
   public ComboPooledDataSource a;
   private static String d;
   private static String e;
   private static String f;

   public static void a(String var0, String var1, String var2) {
      d = var0;
      e = var1;
      f = var2;
   }

   public DatabaseFactory() throws SQLException {
      try {
         this.a = new ComboPooledDataSource();
         this.a.setDriverClass(Driver.class.getTypeName());
         this.a.setJdbcUrl(d);
         this.a.setUser(DatabaseFactory.e);
         this.a.setPassword(f);
         this.a.setAutoCommitOnClose(true);
         this.a.setInitialPoolSize(10);
         this.a.setMinPoolSize(10);
         this.a.setMaxPoolSize(100);
         this.a.setAcquireRetryAttempts(30);
         this.a.setAcquireRetryDelay(500);
         this.a.setCheckoutTimeout(30000);
         this.a.setAcquireIncrement(5);
         this.a.setAutomaticTestTable("connection_test_table");
         this.a.setTestConnectionOnCheckin(false);
         this.a.setIdleConnectionTestPeriod(60);
         this.a.setMaxIdleTime(14400);
         this.a.setBreakAfterAcquireFailure(false);
         this.a.getConnection().close();
      } catch (SQLException var2) {
         b.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         throw var2;
      } catch (Exception var3) {
         b.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         throw new SQLException("could not init DB connection:" + var3);
      }
   }

   public static DatabaseFactory a() throws SQLException {
      if (c == null) {
         c = new DatabaseFactory();
      }

      return c;
   }

   public Connection b() throws SQLException {
      return this.a.getConnection();
   }
}
