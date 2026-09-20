package l1r.aj;

import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_DeleteCharOK;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_DeleteChar extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_DeleteChar.class.getName());

   public C_DeleteChar(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      String var3 = this.g();

      try {
         L1PcInstance var4 = CharacterTable.a().a(var3);
         if (var4 != null && var4.ev() > 55 && Config.as) {
            if (var4.ay() < 32) {
               if (var4.x()) {
                  var4.ad(32);
               } else if (var4.z()) {
                  var4.ad(33);
               } else if (var4.A()) {
                  var4.ad(34);
               } else if (var4.B()) {
                  var4.ad(35);
               } else if (var4.C()) {
                  var4.ad(36);
               } else if (var4.D()) {
                  var4.ad(37);
               } else if (var4.E()) {
                  var4.ad(38);
               } else if (var4.F()) {
                  var4.ad(39);
               }

               Timestamp var7 = new Timestamp(System.currentTimeMillis() + 604800000L);
               var4.d(var7);
               var4.I();
            } else {
               if (var4.x()) {
                  var4.ad(0);
               } else if (var4.z()) {
                  var4.ad(1);
               } else if (var4.A()) {
                  var4.ad(2);
               } else if (var4.B()) {
                  var4.ad(3);
               } else if (var4.C()) {
                  var4.ad(4);
               } else if (var4.D()) {
                  var4.ad(5);
               } else if (var4.E()) {
                  var4.ad(6);
               } else if (var4.F()) {
                  var4.ad(7);
               }

               var4.d((Timestamp)null);
               var4.I();
            }

            var2.a(new S_DeleteCharOK(81));
            return;
         }

         if (var4 != null) {
            L1Clan var5 = ClanTable.a().a(var4.aF());
            if (var5 != null) {
               var5.b(var3);
            }
         }

         CharacterTable.a().a(var2.a(), var3);
      } catch (Exception var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         var2.c();
         return;
      }

      var2.a(new S_DeleteCharOK(5));
   }

   @Override
   public String a() {
      return "C_DELETE_CHAR";
   }
}
