package l1r.aj;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.am.BadNamesList;
import l1r.ao.BeginnerTable;
import l1r.ao.CharacterTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_AddSkill;
import l1r.be.S_CharCreateStatus;
import l1r.be.S_NewCharPacket;
import l1r.bh.L1Account;
import l1r.bh.L1Skills;
import l1r.bi.CalcInitHpMp;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_CreateChar extends ClientBasePacket {
   private static final Logger b = Logger.getLogger(C_CreateChar.class.getName());
   public static final int[] a = new int[]{0, 61, 138, 734, 2786, 6658, 6671, 12490};
   private static final int[] c = new int[]{1, 48, 37, 1186, 2796, 6661, 6650, 12494};
   private static final int d = 32780;
   private static final int e = 32825;
   private static final short f = 7783;

   public C_CreateChar(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = new L1PcInstance();
      String var4 = this.g();
      L1Account var5 = var2.e();
      int var6 = var5.k();
      int var7 = Config.au + var6;
      var4 = var4.replaceAll("\\s", "").replaceAll("　", "");
      if (var4.length() != 0 && !b(var4)) {
         if (CharacterTable.a().c(var4)) {
            var2.a(new S_CharCreateStatus(6));
         } else if (var2.e().k() >= var7) {
            var2.a(new S_CharCreateStatus(21));
            System.out.println("account: " + var2.a() + " 超過角色上限數目: " + var7 + "。");
         } else {
            var3.e(var4);
            var3.ad(this.c());
            var3.aj(this.c());
            if (var3.ay() < 0 || var3.ay() >= a.length) {
               var2.a(new S_CharCreateStatus(21));
               return;
            }
            var3.i(var3.aJ() == 0 ? a[var3.ay()] : c[var3.ay()]);
            var3.o(this.c());
            var3.q(this.c());
            var3.p(this.c());
            var3.t(this.c());
            var3.r(this.c());
            var3.s(this.c());
            boolean var8 = false;
            int var9 = var3.aC().a()[0];
            int var10 = var3.aC().a()[1];
            int var11 = var3.aC().a()[2];
            int var12 = var3.aC().a()[3];
            int var13 = var3.aC().a()[4];
            int var14 = var3.aC().a()[5];
            int var15 = var3.aC().b();
            if (var3.bf() < var9
               || var3.bh() < var10
               || var3.bg() < var11
               || var3.bk() < var13
               || var3.bi() < var14
               || var3.bj() < var12
               || var3.bf() > var9 + var15
               || var3.bh() > var10 + var15
               || var3.bg() > var11 + var15
               || var3.bk() > var13 + var15
               || var3.bi() > var14 + var15
               || var3.bj() > var12 + var15) {
               var8 = true;
            }

            int var16 = var3.eB() + var3.eC() + var3.eA() + var3.eD() + var3.ez() + var3.eE();
            if (var16 == 75 && !var8) {
               var2.a(new S_CharCreateStatus(2));
               a(var2, var3);
            } else {
               var2.a(new S_CharCreateStatus(21));
               System.out.println("[點數ERROR]: statusAmount=" + var16 + " / isStatusError=" + var8);
            }
         }
      } else {
         var2.a(new S_CharCreateStatus(9));
      }
   }

   private static void a(ClientThread var0, L1PcInstance var1) throws IOException, Exception {
      var1.cF(IdFactory.a().d());
      var1.n();
      var1.cG(32780);
      var1.cH(32825);
      var1.cE(7783);
      int var2 = CalcInitHpMp.a(var1);
      int var3 = CalcInitHpMp.c(var1);
      var1.m(var2);
      var1.a(var2);
      var1.n(var3);
      var1.i_(var3);
      var1.c_(40);
      var1.W();
      var1.Y();
      if (var1.B()) {
         L1Skills var4 = SkillsTable.a().a(4);
         SkillsTable.a().a(var1.fr(), var4.a(), var4.b(), 0, 0);
         var1.a(new S_AddSkill(var1, 4));
      }

      BeginnerTable.a().a(var1);
      var1.d(var0.a());
      CharacterTable.a().a(var1);
      CharacterTable.a().d(var1);
      var0.a(new S_NewCharPacket(var1));
   }

   private static boolean a(String var0) {
      boolean var1 = true;
      char[] var2 = var0.toCharArray();

      for (int var3 = 0; var3 < var2.length; var3++) {
         if (!Character.isLetterOrDigit(var2[var3])) {
            var1 = false;
            break;
         }
      }

      return var1;
   }

   private static boolean b(String var0) {
      int var1 = 0;
      if (BadNamesList.a().a(var0)) {
         return true;
      }

      try {
         var1 = var0.getBytes(Config.k).length;
      } catch (UnsupportedEncodingException var3) {
         b.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         return false;
      }

      if (a(var0)) {
         return false;
      } else {
         return 5 >= var1 - var0.length() && 12 >= var1 ? !BadNamesList.a().a(var0) : false;
      }
   }

   @Override
   public String a() {
      return "C_CreateChar";
   }
}
