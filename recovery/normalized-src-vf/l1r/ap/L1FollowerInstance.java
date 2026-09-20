package l1r.ap;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ao.ItemTable;
import l1r.ao.NpcTable;
import l1r.aq.L1Character;
import l1r.aq.L1NpcTalkData;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_FollowerPack;
import l1r.bh.L1Npc;
import l1r.bi.LineageUtil;

public class L1FollowerInstance extends L1NpcInstance {
   private static final Logger y = Logger.getLogger(L1FollowerInstance.class.getName());

   @Override
   public boolean a() {
      for (L1Object var1 : L1World.a().e(this)) {
         if (var1 instanceof L1NpcInstance) {
            L1NpcInstance var3 = (L1NpcInstance)var1;
            if (var3.U_().b() == 70740 && this.U_().b() == 71093) {
               this.V(true);
               L1PcInstance var7 = (L1PcInstance)this.k;
               if (!var7.j().f(40593)) {
                  ItemTable.a(var7, 40593, 1);
               }

               this.aa_();
               return true;
            }

            if (var3.U_().b() == 70811 && this.U_().b() == 71094) {
               this.V(true);
               L1PcInstance var6 = (L1PcInstance)this.k;
               if (!var6.j().f(40582) && !var6.j().f(40583)) {
                  ItemTable.a(var6, 40582, 1);
               }

               this.aa_();
               return true;
            }

            if (var3.U_().b() == 70964 && this.U_().b() == 70957) {
               if (this.fu().c(this.k.fu()) < 3) {
                  L1PcInstance var5 = (L1PcInstance)this.k;
                  if (var5.fs() >= 32917 && var5.fs() <= 32921 && var5.ft() >= 32974 && var5.ft() <= 32978 && var5.fp() == 410) {
                     this.V(true);
                     ItemTable.a(var5, 41003, 1);
                     var5.bb().a(38, 0);
                     this.aa_();
                     return true;
                  }
               }
            } else if (var3.U_().b() == 71114 && this.U_().b() == 81350 && this.fu().c(this.k.fu()) < 15) {
               L1PcInstance var4 = (L1PcInstance)this.k;
               if (var4.fs() >= 32542 && var4.fs() <= 32585 && var4.ft() >= 32656 && var4.ft() <= 32698 && var4.fp() == 400) {
                  this.V(true);
                  ItemTable.a(var4, 49163, 1);
                  var4.bb().a(4, 4);
                  this.aa_();
                  return true;
               }
            }
         }
      }

      if (!this.k.eX() && this.fu().c(this.k.fu()) <= 10) {
         if (this.k != null && this.k.fp() == this.fp() && this.fu().c(this.k.fu()) > 2) {
            this.g(this.a(this.k.fs(), this.k.ft()));
            this.v(this.f(this.N(), 0));
         }

         return false;
      } else {
         this.V(true);
         this.a(this.U_().b(), this.fs(), this.ft(), this.fb(), this.fp());
         this.aa_();
         return true;
      }
   }

   public L1FollowerInstance(L1Npc var1, L1NpcInstance var2, L1Character var3) {
      super(var1);
      this.cF(IdFactory.a().c());
      this.e(var3);
      this.cG(var2.fs());
      this.cH(var2.ft());
      this.cE(var2.fp());
      this.ct(var2.fb());
      this.s(var2.aa());
      var2.V(true);
      var2.X(true);
      var2.aa_();
      L1World.a().a(this);
      L1World.a().c(this);

      for (L1PcInstance var4 : L1World.a().f(this)) {
         this.b(var4);
      }

      this.q();
      this.k.a(this);
   }

   @Override
   public synchronized void aa_() {
      this.k.em().remove(this.fr());
      super.aa_();
   }

   @Override
   public void a(L1PcInstance var1) {
      if (!this.eX()) {
         L1NpcTalkData.a(this, var1);
      }
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_FollowerPack(this));
   }

   public void a(int var1, int var2, int var3, int var4, int var5) {
      L1Npc var6 = NpcTable.a().a(var1);
      if (var6 != null) {
         L1NpcInstance var7 = null;

         try {
            var7 = LineageUtil.a(var6);
            var7.cF(IdFactory.a().c());
            var7.cG(var2);
            var7.cH(var3);
            var7.q(var2);
            var7.r(var3);
            var7.cE(var5);
            var7.ct(var4);
            L1World.a().a(var7);
            L1World.a().c(var7);
            L1Object var8 = L1World.a().a(var7.fr());
            L1QuestInstance var9 = (L1QuestInstance)var8;
            var9.Z_();
            var9.fg();
            var9.a_(0);
         } catch (Exception var10) {
            y.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         }
      }
   }
}
