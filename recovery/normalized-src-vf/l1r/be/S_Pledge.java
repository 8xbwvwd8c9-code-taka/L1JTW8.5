package l1r.be;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aj.C_Pledge;
import l1r.ao.ClanTable;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.bi.LineageUtil;

public class S_Pledge extends ServerBasePacket {
   private static final Logger a = Logger.getLogger(S_Pledge.class.getName());

   public S_Pledge(int var1) {
      L1Clan var2 = ClanTable.a().a(var1);
      this.c(121);
      this.c(167);
      this.a(var2.f());
      this.a(var2.l());
      this.a(var2.i());
      this.c(var2.n() > 0 ? 1 : 0);
      this.c(var2.m() > 0 ? 1 : 0);
      this.c(L1World.a().b(var2.f()) ? 1 : 0);
      this.a((int)(var2.g().getTime() / 1000L));

      try {
         byte[] var3 = var2.h().getBytes(aV);
         byte[] var4 = Arrays.copyOf(var3, 478);
         this.a(var4);
      } catch (Exception var5) {
         a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
      }
   }

   public S_Pledge(ArrayList<C_Pledge.L1R_a> var1) {
      this.c(121);
      this.c(170);
      this.b(1);
      this.c(var1.size());

      for (C_Pledge.L1R_a var2 : var1) {
         this.a(var2.a);
         this.c(var2.b);
         this.c(var2.c);
         byte[] var4 = Arrays.copyOf(LineageUtil.b(var2.g), 62);
         this.a(var4);
         this.a(var2.d);
         this.c(var2.e);
         this.a(var2.f);
      }
   }

   public S_Pledge(String var1, String var2) {
      this.c(121);
      this.c(169);
      this.a(var1);

      try {
         byte[] var3 = Arrays.copyOf(var2.getBytes(aV), 62);
         this.a(var3);
      } catch (Exception var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Pledge";
   }
}
