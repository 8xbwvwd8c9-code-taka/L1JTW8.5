package l1r.aj;

import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_CharEvent;
import l1r.bj.ClientThread;

public class C_EmblemUpload extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_EmblemUpload.class.getName());
   private static final String b = "[C] C_EmblemUpload";

   public C_EmblemUpload(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         if (var3.aH() == 4 || var3.aH() == 10) {
            if (var3.aF() != 0) {
               int var4 = IdFactory.a().d();
               String var5 = String.valueOf(var4);
               FileOutputStream var6 = null;

               try {
                  var6 = new FileOutputStream("./emblem/" + var5);

                  for (short var7 = 0; var7 < 384; var7++) {
                     var6.write(this.c());
                  }
               } catch (Exception var12) {
                  a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
                  throw var12;
               } finally {
                  if (var6 != null) {
                     var6.close();
                  }

                  Object var14 = null;
               }

               L1Clan var15 = ClanTable.a().a(var3.aF());
               var15.d(var4);
               ClanTable.a().b(var15);

               for (L1PcInstance var8 : var15.b()) {
                  var8.a(new S_CharEvent(60, var8.fr(), var4));
                  var8.b(new S_CharEvent(60, var8.fr(), var4));
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_EmblemUpload";
   }
}
