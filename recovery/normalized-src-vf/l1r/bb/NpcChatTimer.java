package l1r.bb;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1NpcInstance;
import l1r.aq.L1World;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_ServerMessage;
import l1r.bh.L1NpcChat;
import l1r.bi.GeneralThreadPool;

public class NpcChatTimer extends TimerTask {
   private static final Logger a = Logger.getLogger(NpcChatTimer.class.getName());
   private final L1NpcInstance b;
   private final L1NpcChat c;

   public NpcChatTimer(L1NpcInstance var1, L1NpcChat var2) {
      this.b = var1;
      this.c = var2;
   }

   public void a() {
      GeneralThreadPool.a().a(this, this.c.c());
   }

   @Override
   public void run() {
      try {
         if (this.b == null || this.c == null) {
            return;
         }

         if (this.b.ac() != 0 || this.b.ah()) {
            return;
         }

         int var1 = this.c.i();
         String var2 = this.c.d();
         String var3 = this.c.e();
         String var4 = this.c.f();
         String var5 = this.c.g();
         String var6 = this.c.h();
         if (!var2.equals("")) {
            this.a(this.b, var2);
         }

         if (!var3.equals("")) {
            Thread.sleep(var1);
            this.a(this.b, var3);
         }

         if (!var4.equals("")) {
            Thread.sleep(var1);
            this.a(this.b, var4);
         }

         if (!var5.equals("")) {
            Thread.sleep(var1);
            this.a(this.b, var5);
         }

         if (!var6.equals("")) {
            Thread.sleep(var1);
            this.a(this.b, var6);
         }

         if (this.c.l()) {
            GeneralThreadPool.a().a(this, this.c.m());
         }
      } catch (Throwable var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      }
   }

   private void a(L1NpcInstance var1, String var2) {
      if (this.c.b() != 0 || !var1.eX()) {
         if (this.c.b() != 1 || var1.eX()) {
            if (this.c.b() != 2 || !var1.eX()) {
               if (!var2.contains("$")) {
                  var1.b(new S_ServerMessage(Integer.parseInt(var2)));
               } else if (this.c.k()) {
                  L1World.a().a(new S_NpcChatPacket(var1, var2, 3));
               } else if (this.c.j()) {
                  var1.d(new S_NpcChatPacket(var1, var2, 2));
               } else if (this.c.o()) {
                  var1.b(new S_NpcChatPacket(var1, var2, 21));
               } else {
                  var1.b(new S_NpcChatPacket(var1, var2, 0));
               }
            }
         }
      }
   }
}
