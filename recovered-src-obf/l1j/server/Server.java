/*
 * Decompiled with CFR 0.152.
 */
package l1j.server;

import ai.c;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import l1j.server.a;
import l1j.server.b;

public class Server {
    private static final Logger a = Logger.getLogger(Server.class.getName());

    public static void main(String[] args) throws Exception {
        try {
            BufferedInputStream is = new BufferedInputStream(new FileInputStream("./config/log.properties"));
            LogManager.getLogManager().readConfiguration(is);
            ((InputStream)is).close();
            l1j.server.a.b();
            TimeZone.setDefault(TimeZone.getTimeZone(l1j.server.a.l));
            b.a(l1j.server.a.h, l1j.server.a.i, l1j.server.a.j);
            b.a();
            l1j.server.a.a();
            l1j.server.a.f = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Config.GAME_SERVER_IP.." + l1j.server.a.f);
            c.a().b();
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            System.exit(0);
        }
    }
}

