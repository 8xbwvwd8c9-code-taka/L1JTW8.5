package l1r.au;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterItemTable;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1GuardianSoul;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.be.S_AddItem;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_DeleteInventoryItem;
import l1r.be.S_EquipmentSlot;
import l1r.be.S_ItemAttribute;
import l1r.be.S_ItemColor;
import l1r.be.S_ItemDesc;
import l1r.be.S_ItemName;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1QuestNew;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1PcInventory extends L1Inventory {
   private static final Logger g = Logger.getLogger(L1PcInventory.class.getName());
   private static final int h = 180;
   private final L1PcInstance i;
   private L1ItemInstance j = null;
   private boolean[] k = new boolean[2];
   private boolean[] l = new boolean[2];
   private boolean[] m = new boolean[2];
   private int[] n = new int[4];

   public L1PcInventory(L1PcInstance var1) {
      this.i = var1;
   }

   public L1PcInstance b() {
      return this.i;
   }

   public int h() {
      return this.o(this.e());
   }

   private int o(int var1) {
      if (Config.J == 0.0) {
         return 0;
      }

      double var2 = this.i.K();
      return var1 > var2 ? 100 : (int)(var1 * 100 / var2);
   }

   @Override
   public int a(L1ItemInstance var1, int var2) {
      if (var1 == null) {
         return -1;
      }

      if (this.c() <= 180 && (this.c() != 180 || var1.d() && this.f(var1.N()))) {
         int var3 = this.e() + var1.a().l() * var2 / 1000 + 1;
         if (var3 < 0 || var1.a().l() * var2 / 1000 < 0) {
            this.p(82);
            return 2;
         }

         if (this.o(var3) >= 100) {
            this.p(82);
            return 2;
         }

         long var4 = this.g(40308);
         if (var4 + var2 > 2000000000L) {
            this.b().a(new S_SystemMessage("\\aG所持有的金幣超過了2000000000上限"));
            return 3;
         }

         if (var1.N() == 640102) {
            if (L1GuardianSoul.a().a) {
               L1World.a().b(var1);
               return -1;
            }

            L1GuardianSoul.a().a = true;
            var1.a(new Timestamp(System.currentTimeMillis()));
            L1GuardianSoul.a().a(this.i.fr(), 0L);
         }

         return 0;
      } else {
         this.p(263);
         return 1;
      }
   }

   private void p(int var1) {
      if (this.i.cq() && var1 == 82) {
         var1 = 1518;
      }

      this.i.a(new S_ServerMessage(var1));
   }

   @Override
   public synchronized L1ItemInstance d(L1ItemInstance var1) {
      if (var1.E() <= 0) {
         return null;
      }

      int var2 = var1.N();
      if (var1.d()) {
         L1ItemInstance var3 = this.d(var2, var1.F());
         if (var2 == 40309) {
            var3 = this.a(var1.a().j());
         } else if (var2 == 40312) {
            var3 = this.c(var2);
         }

         if (var3 != null && var3.F() == var1.F()) {
            var3.e(var3.E() + var1.E());
            this.b(var3);
            return var3;
         }
      }

      int var4 = var1.a().aM();
      if (var1.N() >= 21340 && var1.N() <= 21349) {
         var1.d(this.i.ay());
      } else if (var2 == 40309) {
         L1BugBearRace.a().b(var1);
      } else if (var2 == 41401) {
         var4 -= Random.a(5);
      } else if (var2 == 20383) {
         var4 = 50;
      }

      var1.g(var4);
      if (var1.f() && var1.a().aP() == 2) {
         var1.j(var1.a().d());
      } else if (var1.N() != 40312 && var1.N() != 640615) {
         var1.j(var1.a().T());
      }

      var1.n();
      this.a.add(var1);
      this.a(var1);
      return var1;
   }

   @Override
   public synchronized L1ItemInstance e(L1ItemInstance var1) {
      if (var1.N() == 40312) {
         L1ItemInstance var7 = this.c(var1.M());
         if (var7 != null) {
            var7.e(var7.E() + var1.E());
            this.b(var7);
            return var7;
         }
      } else if (var1.d()) {
         L1ItemInstance var6 = this.d(var1.N(), var1.F());
         if (var6 != null && var6.F() == var1.F()) {
            var6.e(var6.E() + var1.E());
            this.b(var6);
            return var6;
         }
      } else if (var1.bb() != null) {
         Timestamp var2 = new Timestamp(System.currentTimeMillis());
         if (var1.bb().before(var2)) {
            this.i.a(new S_ServerMessage(2535, var1.b(), "0"));
            if (var1.N() >= 21246 && var1.N() <= 21251) {
               int var8 = var1.G();
               int var9 = var1.F();
               boolean var10 = var1.C();
               var1 = ItemTable.a().b(20085);
               var1.a(var8);
               var1.a(var10);
               var1.f(var9);
            } else if (var1.N() >= 21252 && var1.N() <= 21257) {
               int var3 = var1.G();
               int var4 = var1.F();
               boolean var5 = var1.C();
               var1 = ItemTable.a().b(20084);
               var1.a(var3);
               var1.a(var5);
               var1.f(var4);
            } else {
               if (var1.N() < 21261 || var1.N() > 21300) {
                  this.c(var1);
                  return null;
               }

               var1.b((Timestamp)null);
            }
         }
      }

      this.a.add(var1);
      this.a(var1);
      return var1;
   }

   @Override
   public void a() {
      try {
         this.a.clear();
         this.n = new int[4];
         this.m = new boolean[2];
         this.l = new boolean[2];
         this.k = new boolean[2];
         Timestamp var1 = new Timestamp(System.currentTimeMillis());

         for (L1ItemInstance var2 : CharacterItemTable.a().a(this.i.fr())) {
            if (var2.bb() != null && var2.bb().before(var1)) {
               this.i.a(new S_ServerMessage(2535, var2.b(), "0"));
               CharacterItemTable.a().a(var2);
               if (var2.N() >= 21246 && var2.N() <= 21251) {
                  int var10 = var2.G();
                  int var12 = var2.F();
                  boolean var13 = var2.D();
                  boolean var14 = var2.C();
                  var2 = ItemTable.a().b(20085);
                  var2.a(var10);
                  var2.a(var14);
                  var2.f(var12);
                  var2.b(var13);
                  CharacterItemTable.a().a(this.i.fr(), var2);
               } else if (var2.N() >= 21252 && var2.N() <= 21257) {
                  int var9 = var2.G();
                  int var11 = var2.F();
                  boolean var6 = var2.D();
                  boolean var7 = var2.C();
                  var2 = ItemTable.a().b(20084);
                  var2.a(var9);
                  var2.a(var7);
                  var2.f(var11);
                  var2.b(var6);
                  CharacterItemTable.a().a(this.i.fr(), var2);
               } else {
                  if (var2.N() < 21261 || var2.N() > 21300) {
                     continue;
                  }

                  int var4 = var2.N();
                  boolean var5 = var2.C();
                  var2 = ItemTable.a().b(var4);
                  var2.a(var5);
                  CharacterItemTable.a().a(this.i.fr(), var2);
               }
            }

            this.a.add(var2);
            if (var2.D()) {
               if (this.i.ev() >= var2.a().o() && (var2.a().p() <= 0 || this.i.ev() <= var2.a().p())) {
                  this.i.bK().a(var2);
               } else {
                  var2.b(false);
               }
            }

            if (var2.f() && var2.a().aP() == 2) {
               var2.j(var2.a().d());
            }

            if (var2.N() >= 21340 && var2.N() <= 21349) {
               var2.d(this.i.ay());
            }

            if (var2.N() == 40309) {
               L1BugBearRace.a().a(var2);
            }

            L1World.a().a(var2);
         }
      } catch (Exception var8) {
         g.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      }
   }

   @Override
   public void a(L1ItemInstance var1) {
      for (L1QuestNew var2 : this.i.dS().values()) {
         for (int var4 = 0; var4 < var2.r().length; var4++) {
            if (var2.r()[var4] == var1.N() && var2.t()[var4] <= var1.G()) {
               var2.a(var4, var1.E());
            }
         }
      }

      this.i.a(new S_AddItem(var1));
      if (var1.N() == 640100 || var1.N() == 640102) {
         this.i.ae();
      }

      if (var1.a().l() != 0) {
         this.i.a(new S_ProtoBuffers(485, this.i));
      }

      try {
         CharacterItemTable.a().a(this.i.fr(), var1);
      } catch (Exception var5) {
         g.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
      }
   }

   @Override
   public void b(L1ItemInstance var1) {
      for (L1QuestNew var2 : this.i.dS().values()) {
         for (int var4 = 0; var4 < var2.r().length; var4++) {
            if (var2.r()[var4] == var1.N() && var2.t()[var4] <= var1.G()) {
               var2.a(var4, var1.E());
            }
         }
      }

      if (var1.o[1] != var1.X() || var1.p[1] != var1.Y() || var1.q[1] != var1.Z() || var1.r[1] != var1.aa()) {
         this.i.a(new S_ItemDesc(var1));
         var1.o[1] = var1.X();
         var1.p[1] = var1.Y();
         var1.q[1] = var1.Z();
         var1.r[1] = var1.aa();
      }

      if (var1.n[1] != var1.L()) {
         this.i.a(new S_ItemDesc(var1));
         var1.n[1] = var1.L();
      }

      if (var1.m[1] != var1.K()) {
         this.i.a(new S_ItemDesc(var1));
         var1.m[1] = var1.K();
      }

      if (var1.j[1] != var1.bb()) {
         this.i.a(new S_ItemDesc(var1));
         var1.j[1] = var1.bb();
      }

      if (var1.g[1] != var1.H()) {
         this.i.a(new S_ItemDesc(var1));
         var1.g[1] = var1.H();
      }

      if (var1.i[1] != var1.M()) {
         this.i.a(new S_ItemName(var1));
         var1.i[1] = var1.M();
      }

      if (var1.h[1] != var1.I()) {
         this.i.a(new S_ItemName(var1));
         var1.h[1] = var1.I();
      }

      if (var1.c[1] != var1.D()) {
         this.i.a(new S_ItemName(var1));
         var1.c[1] = var1.D();
      }

      if (var1.d[1] != var1.U()) {
         this.i.a(new S_ItemName(var1));
         var1.d[1] = var1.U();
      }

      if (var1.b[1] != var1.N()) {
         this.i.a(new S_ItemDesc(var1));
         this.i.a(new S_ItemColor(var1));
         this.i.a(new S_ProtoBuffers(485, this.i));
         var1.b[1] = var1.N();
      }

      if (var1.a[1] != var1.E()) {
         this.i.a(new S_ItemDesc(var1));
         this.i.a(new S_ProtoBuffers(485, this.i));
         var1.a[1] = var1.E();
      }

      if (var1.l[1] != var1.F()) {
         this.i.a(new S_ItemColor(var1));
         this.i.a(new S_ItemAttribute(var1));
         var1.l[1] = var1.F();
      }

      if (var1.e[1] != var1.G()) {
         this.i.a(new S_ItemDesc(var1));
         this.i.a(new S_ItemAttribute(var1));
         var1.e[1] = var1.G();
      }

      if (var1.f[1] != var1.C()) {
         this.i.a(new S_ItemDesc(var1));
         this.i.a(new S_ItemColor(var1));
         var1.f[1] = var1.C();
      }

      if (var1.a().u()) {
         this.i(var1);
      }
   }

   public void i(L1ItemInstance var1) {
      try {
         CharacterItemTable var2 = CharacterItemTable.a();
         if (var1.o[0] != var1.X() || var1.p[0] != var1.Y() || var1.q[0] != var1.Z() || var1.r[0] != var1.aa()) {
            var2.o(var1);
            var1.o[0] = var1.X();
            var1.p[0] = var1.Y();
            var1.q[0] = var1.Z();
            var1.r[0] = var1.aa();
         }

         if (var1.n[0] != var1.L()) {
            var2.n(var1);
            var1.n[0] = var1.L();
         }

         if (var1.m[0] != var1.K()) {
            var2.m(var1);
            var1.m[0] = var1.K();
         }

         if (var1.l[0] != var1.F()) {
            var2.l(var1);
            var1.l[0] = var1.F();
         }

         if (var1.i[0] != var1.M()) {
            var2.f(var1);
            var1.i[0] = var1.M();
         }

         if (var1.j[0] != var1.bb()) {
            var2.g(var1);
            var1.j[0] = var1.bb();
         }

         if (var1.h[0] != var1.I()) {
            var2.e(var1);
            var1.h[0] = var1.I();
         }

         if (var1.b[0] != var1.N()) {
            var2.b(var1);
            var1.b[0] = var1.N();
         }

         if (var1.k[0] != var1.J()) {
            var2.k(var1);
            var1.k[0] = var1.J();
         }

         if (var1.a[0] != var1.E()) {
            var2.c(var1);
            var1.a[0] = var1.E();
         }

         if (var1.c[0] != var1.D()) {
            var2.i(var1);
            var1.c[0] = var1.D();
         }

         if (var1.e[0] != var1.G()) {
            var2.h(var1);
            var1.e[0] = var1.G();
         }

         if (var1.f[0] != var1.C()) {
            var2.j(var1);
            var1.f[0] = var1.C();
         }

         if (var1.g[0] != var1.H()) {
            var2.d(var1);
            var1.g[0] = var1.H();
         }
      } catch (Exception var3) {
         g.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      }
   }

   public void j(L1ItemInstance var1) {
      this.b(var1);
      this.i(var1);
   }

   @Override
   public void c(L1ItemInstance var1) {
      try {
         CharacterItemTable.a().a(var1);
      } catch (Exception var5) {
         g.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
      }

      if (var1.D()) {
         this.a(var1, false);
      }

      if (var1.a().l() != 0) {
         this.i.a(new S_ProtoBuffers(485, this.i));
      }

      this.i.a(new S_DeleteInventoryItem(var1));
      this.a.remove(var1);
      if (var1.N() == 640100) {
         this.i.bz(25005);
      }

      for (L1QuestNew var2 : this.i.dS().values()) {
         for (int var4 = 0; var4 < var2.r().length; var4++) {
            if (var2.r()[var4] == var1.N() && var2.t()[var4] <= var1.G()) {
               var2.a(var4, Math.max(0, var2.B()[var4] - var1.E()));
            }
         }
      }
   }

   public void a(L1ItemInstance var1, boolean var2) {
      if (var1.D() != var2) {
         if (var2) {
            var1.b(true);
            this.i.bK().a(var1);
         } else {
            if ((var1.N() == 20077 || var1.N() == 20062 || var1.N() == 120077) && this.i.ff()) {
               this.i.s();
               return;
            }

            var1.b(false);
            this.i.bK().b(var1);
         }

         this.b(var1);
         int var3 = this.l(var1);
         if (var3 >= 0) {
            this.i.a(new S_EquipmentSlot(var1.fr(), var3, var1.D()));
         }

         this.i.a(new S_OwnCharStatus(this.i));
         if (var1.g()) {
            this.i.a(new S_CharVisualUpdate(this.i));
            this.i.b(new S_CharVisualUpdate(this.i));
         }

         this.i.a(new S_PacketBox(132, this.i.u()));
         this.i.a(new S_ProtoBuffers(485, this.i));
      }
   }

   public boolean h(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.N() == var1 && var2.D()) {
            return true;
         }
      }

      return false;
   }

   public boolean b(int[] var1) {
      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];
         if (!this.h(var2)) {
            return false;
         }
      }

      return true;
   }

   public int i(int var1) {
      int var2 = 0;

      for (L1ItemInstance var3 : this.a) {
         if (var3.h() && var3.a().aP() == var1 && var3.D()) {
            var2++;
         }
      }

      return var2;
   }

   public L1ItemInstance j(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.h() && var2.a().aP() == var1 && var2.D()) {
            return var2;
         }
      }

      return null;
   }

   public ArrayList<L1ItemInstance> i() {
      ArrayList var1 = new ArrayList<>();

      for (L1ItemInstance var2 : this.a) {
         if (var2.h() && var2.a().aP() == 9 && var2.D()) {
            var1.add(var2);
         }
      }

      return var1;
   }

   public void k(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.fr() == var1) {
            if (var2.g() && L1PolyMorph.a(this.i, var2.a().aP())) {
               this.a(var2, true);
            }

            if (var2.h() && L1PolyMorph.b(this.i, var2.a().aP()) && (var2.N() != 21397 || this.i.fp() == 1700 || this.i.fp() == 1703)) {
               this.a(var2, true);
            }
         }
      }
   }

   public void l(int var1) {
      for (L1ItemInstance var2 : this.a) {
         if (var2.D()) {
            if (var2.g() && !L1PolyMorph.a(this.i, var2.a().aP())) {
               this.a(var2, false);
            }

            if (var2.h() && !L1PolyMorph.b(this.i, var2.a().aP())) {
               this.a(var2, false);
            }
         }
      }
   }

   public void j() {
      for (L1ItemInstance var1 : this.a) {
         if (var1.D()) {
            this.a(var1, false);
         }
      }
   }

   public boolean m(int var1) {
      boolean var2 = false;

      for (L1ItemInstance var3 : this.a) {
         if (var3.N() == var1 && var3.D()) {
            this.a(var3, false);
            var2 = true;
         }
      }

      return var2;
   }

   protected int k() {
      return this.j == null ? 0 : this.j.fr();
   }

   public L1ItemInstance n(int var1) {
      int var2 = 0;
      if (var1 == 62) {
         var2 = 15;
      }

      if (this.j != null && this.j.E() > 1 && this.j.a().aP() == var2) {
         return this.j;
      }

      for (L1ItemInstance var3 : this.a) {
         if (var3.f() && var3.a().aP() == var2) {
            this.j = var3;
            return this.j;
         }
      }

      this.j = null;
      return this.j;
   }

   public void k(L1ItemInstance var1) {
      this.j = var1;
   }

   public int l() {
      int var1 = 0;

      for (L1ItemInstance var2 : this.a) {
         if (var2.D()) {
            var1 += var2.a().O() + var2.ag() + var2.bc() + var2.bD();
         }
      }

      return var1;
   }

   public int m() {
      int var1 = 0;

      for (L1ItemInstance var2 : this.a) {
         if (var2.D()) {
            var1 += var2.a().P() + var2.ah() + var2.bd() + var2.bC();
            if (var2.N() == 330 || var2.N() == 332) {
               var1 += var2.G();
            }
         }
      }

      return var1;
   }

   public L1ItemInstance n() {
      if (this.a.isEmpty()) {
         return null;
      }

      int var1 = Random.a(this.a.size());
      L1ItemInstance var2 = this.a.get(var1);
      if (var2.N() != 40308 && var2.a().s()) {
         for (L1NpcInstance var3 : this.i.ek().values()) {
            if (var3 instanceof L1PetInstance) {
               L1PetInstance var5 = (L1PetInstance)var3;
               if (var2.fr() == var5.k()) {
                  return null;
               }
            }
         }

         this.a(var2, false);
         return var2;
      } else {
         return null;
      }
   }

   private int o() {
      for (int var1 = 0; var1 < this.k.length; var1++) {
         if (!this.k[var1]) {
            this.k[var1] = true;
            return 9 - var1;
         }
      }

      return -1;
   }

   private int p() {
      for (int var1 = 0; var1 < this.l.length; var1++) {
         if (!this.l[var1]) {
            this.l[var1] = true;
            return (var1 + 1) * 13;
         }
      }

      return -1;
   }

   private int q() {
      for (int var1 = 0; var1 < this.m.length; var1++) {
         if (!this.m[var1]) {
            this.m[var1] = true;
            return 23 + var1 * 4;
         }
      }

      return -1;
   }

   public int l(L1ItemInstance var1) {
      int var2 = 0;
      if (var1.g()) {
         if (var1.D()) {
            var2 = -1;
            if (var1.V() == 0) {
               var2 = this.o();
               var1.k(var2);
            }
         } else {
            var2 = var1.V();
            this.k[9 - var2] = false;
            var1.k(0);
         }
      } else if (var1.h()) {
         switch (var1.a().aP()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 11:
            case 12:
            case 15:
            case 16:
            case 18:
            case 29:
            case 30:
               var2 = var1.a().aP();
               break;
            case 8:
            case 10:
               var2 = 8;
               break;
            case 9:
               if (var1.D()) {
                  if (var1.V() == 0) {
                     var2 = this.r();
                     var1.k(var2);
                  } else {
                     var2 = -1;
                  }
               } else {
                  var2 = var1.V();
                  this.n[var1.V() - 19] = 0;
                  var1.k(0);
               }
               break;
            case 13:
               if (var1.D()) {
                  var2 = -1;
                  if (var1.V() == 0) {
                     var2 = this.p();
                     var1.k(var2);
                  }
               } else {
                  var2 = var1.V();
                  this.l[var2 / 13 - 1] = false;
                  var1.k(0);
               }
            case 14:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            default:
               break;
            case 23:
               if (var1.D()) {
                  var2 = -1;
                  if (var1.V() == 0) {
                     var2 = this.q();
                     var1.k(var2);
                  }
               } else {
                  var2 = var1.V();
                  this.m[(var2 - 23) / 4] = false;
                  var1.k(0);
               }
         }
      }

      return var2;
   }

   private int r() {
      for (int var1 = 0; var1 < this.n.length; var1++) {
         if (this.n[var1] == 0) {
            this.n[var1] = 1;
            return var1 + 19;
         }
      }

      return -1;
   }
}
