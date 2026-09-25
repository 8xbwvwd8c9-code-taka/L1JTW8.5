/*
 * Decompiled with CFR 0.152.
 */
package l1j.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger b = Logger.getLogger(b.class.getName());
    private static b c;
    public ComboPooledDataSource a;
    private static String d;
    private static String e;
    private static String f;

    public static void a(String url, String user, String password) {
        d = url;
        e = user;
        f = password;
    }

    public b() throws SQLException {
        try {
            this.a = new ComboPooledDataSource();
            this.a.setDriverClass(Driver.class.getTypeName());
            this.a.setJdbcUrl(d);
            this.a.setUser(e);
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
        }
        catch (SQLException x2) {
            b.log(Level.SEVERE, x2.getLocalizedMessage(), x2);
            throw x2;
        }
        catch (Exception e2) {
            b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            throw new SQLException("could not init DB connection:" + e2);
        }
    }

    public static b a() throws SQLException {
        if (c == null) {
            c = new b();
        }
        return c;
    }

    public Connection b() throws SQLException {
        return this.a.getConnection();
    }
}

