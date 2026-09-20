package l1r.l1j.server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import l1r.ai.GameServer;

public class Server {
   private static final Logger a = Logger.getLogger(Server.class.getName());

   public static void main(String[] var0) throws Exception {
      try {
         InputStream var1 = new BufferedInputStream(new FileInputStream("./config/log.properties"));
         LogManager.getLogManager().readConfiguration(var1);
         var1.close();
         Config.b();
         TimeZone.setDefault(TimeZone.getTimeZone(Config.l));
         DatabaseFactory.a(Config.h, Config.i, Config.j);
         DatabaseFactory.a();
         Config.a();
         Config.f = InetAddress.getLocalHost().getHostAddress();
         System.out.println("Config.GAME_SERVER_IP.." + Config.f);
         GameServer.a().b();
      } catch (Exception var2) {
         a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         System.exit(0);
      }
   }
}
