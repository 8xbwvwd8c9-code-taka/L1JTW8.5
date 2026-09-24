package l1r.aq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.ArmorSetTable;
import l1r.ao.ItemEnchantLevelTable;
import l1r.ao.SkillsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_Ability;
import l1r.be.S_AddSkill;
import l1r.be.S_DelSkill;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SPMR;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.bf.S_060;
import l1r.bh.L1Item;

public class L1EquipmentSlot {
   private final L1PcInstance a;
   private final CopyOnWriteArrayList<ArmorSetTable.L1R_a> b;
   private final CopyOnWriteArrayList<L1ItemInstance> c;
   private final ArrayList<L1ItemInstance> d;

   public L1EquipmentSlot(L1PcInstance var1) {
      this.a = var1;
      this.c = new CopyOnWriteArrayList<>();
      this.d = new ArrayList<>();
      this.b = new CopyOnWriteArrayList<>();
   }

   private void d(L1ItemInstance var1) {
      var1.e(this.a);
      this.c.add(var1);
      this.a.a(this.c);
      int var2 = var1.a().aP();
      int var3 = var1.a().aB();
      var3 = var3 < 0 ? 15 : var3;
      this.a.a(new S_PacketBox(160, var3, var2));
      if (var1.N() == 145 || var1.N() == 149) {
         this.a.C(true);
      }

      for (ArmorSetTable.L1R_a var4 : ArmorSetTable.a().b()) {
         if (var4.a(var1.N()) && var4.a(this.a)) {
            var4.a(this.a, var1, true);
            var4.b(this.a);
            this.b.add(var4);
         }
      }
   }

   private void e(L1ItemInstance var1) {
      var1.u();
      this.c.remove(var1);
      if (this.a.bB(91)) {
         this.a.bz(91);
      }

      this.a.a(this.c);
      this.a.a(new S_PacketBox(160, 1, 0));
      if (var1.N() == 145 || var1.N() == 149) {
         this.a.C(false);
      }

      for (ArmorSetTable.L1R_a var2 : ArmorSetTable.a().b()) {
         if (var2.a(var1.N()) && this.b.contains(var2) && !var2.a(this.a)) {
            var2.a(this.a, var1, false);
            var2.c(this.a);
            this.b.remove(var2);
         }
      }
   }

   private void f(L1ItemInstance var1) {
      L1Item var2 = var1.a();
      int var3 = var1.N();
      if (var1.i()) {
         this.a.bL(var2.X() - var1.O() - var1.ad());
      } else {
         this.a.bL(var2.X() - var1.G() - var1.O() - var1.ad());
      }

      this.a.F(var2.Y() + var1.ap());
      this.a.C(var1.aT());
      this.a.D(var2.Z());
      this.a.G(var2.aa() + var1.as());
      this.a.H(var2.ac() + var1.au() + var1.bl());
      this.a.I(var2.ab() + var1.at());
      this.a.J(var2.ad() + var1.av() + var1.bm());
      this.a.cd(var2.ai() + var1.aF() + var1.bk());
      this.a.ce(var2.aj() + var1.aA());
      this.a.cf(var2.ak() + var1.aB());
      this.a.cg(var2.al() + var1.aC());
      this.a.ch(var2.am() + var1.aE() + var1.bj());
      this.a.ci(var2.an() + var1.aD());
      this.a.cj(var2.ao() + var1.aG());
      this.a.cb(var2.ah() + var1.az() + var1.bi());
      this.a.bY(var2.af() + var1.ax() + var1.bg());
      this.a.bZ(var2.ae() + var1.aw() + var1.bf());
      this.a.ca(var2.ag() + var1.ay() + var1.bh());
      this.a.P(var1.aL() + var1.bo());
      this.a.cA(var1.aK() + var1.bn());
      this.a.Q(var2.au() + var1.ao());
      this.a.U(var2.as() + var1.ar());
      this.a.V(var1.aP());
      this.a.W(var1.aQ());
      this.a.X(var2.at() + var1.aR());
      this.a.ac(var2.aw() + var1.aS());
      this.a.R(var2.aq() + var1.aM());
      this.a.S(var2.ar() + var1.aN());
      this.a.T(var1.aO());
      this.a.Y(var1.aY());
      this.a.Z(var1.ba());
      if (var1.B()) {
         this.a.T(true);
      }

      if (var1.aK() + var1.bn() != 0) {
         this.a.a(new S_PacketBox(88, this.a.fk()));
      }

      this.d.add(var1);

      for (ArmorSetTable.L1R_a var4 : ArmorSetTable.a().b()) {
         if (var4.a(var3) && var4.a(this.a)) {
            var4.a(this.a, var1, true);
            if (!var1.h() || var1.a().aP() != 9) {
               var4.b(this.a);
               this.b.add(var4);
            } else if (!var4.d(this.a)) {
               var4.b(this.a);
               this.b.add(var4);
            }
         }
      }

      if (var3 == 20077 || var3 == 20062 || var3 == 120077) {
         new S_060().a(this.a, -1);
      } else if (var3 == 20281) {
         this.a.a(new S_Ability(2, true));
      } else if (var3 == 20288 || var3 == 21418) {
         this.a.a(new S_Ability(1, true));
      } else if (var3 == 20284) {
         this.a.a(new S_Ability(5, true));
      } else if (var3 == 20383) {
         if (var1.I() != 0) {
            var1.g(var1.I() - 1);
            this.a.j().b(var1);
         }
      } else if (var3 == 21397) {
         this.a.a(new S_ProtoBuffers(var2.g(), -1, 0, 5934, 5934, 4647, 4647, 0, 1));
      } else if (var3 >= 21123 && var3 <= 21126) {
         this.a.E(true);
      } else if (var3 >= 21119 && var3 <= 21122) {
         this.a.D(true);
      } else if (var3 == 21446) {
         this.a.D(true);
      } else if (var3 == 21484) {
         this.a.P(true);
      } else if (var3 == 21485) {
         this.a.Q(true);
      } else if (var3 == 21486) {
         this.a.R(true);
      } else if (var3 == 21205 || var3 == 21517) {
         this.a.bm(var1.G() * 2);
      } else if (var3 == 21509) {
         this.a.bn(var1.G());
         this.a.M(true);
      } else if (var3 == 21510 || var3 == 21511) {
         this.a.bo(var1.G());
         this.a.bp(var1.G());
      } else if (var3 == 21473) {
         this.a.H(true);
         this.a.G(true);
      } else if (var3 == 21206) {
         this.a.F(true);
         this.a.G(true);
      } else if (var3 == 21207 || var3 == 21208) {
         this.a.G(true);
      } else if (var3 >= 21527 && var3 <= 21530) {
         this.a.bq(var1.aZ());
      } else if (var3 >= 21213 && var3 <= 21221) {
         this.a.I(true);
      } else if (var3 == 21474) {
         this.a.J(true);
      } else if (var3 == 21500) {
         this.a.S(true);
      } else if (var3 >= 21437 && var3 <= 21440) {
         this.a.K(true);
      } else if (var3 >= 21420 && var3 <= 21423) {
         this.a.L(true);
      } else if (var3 >= 21369 && var3 <= 21371) {
         this.a.O(true);
      } else if (var3 >= 21363 && var3 <= 21365) {
         if (var1.G() >= 10) {
            this.a.M(true);
         } else {
            this.a.N(true);
         }
      }

      var1.e(this.a);
   }

   public List<L1ItemInstance> a() {
      return this.d;
   }

   private void g(L1ItemInstance var1) {
      L1Item var2 = var1.a();
      int var3 = var1.N();
      if (var1.i()) {
         this.a.bL(-(var2.X() - var1.O() - var1.ad()));
      } else {
         this.a.bL(-(var2.X() - var1.G() - var1.O() - var1.ad()));
      }

      this.a.F(-var2.Y() - var1.ap());
      this.a.C(-var1.aT());
      this.a.D(-var2.Z());
      this.a.G(-var2.aa() - var1.as());
      this.a.H(-var2.ac() - var1.au() - var1.bl());
      this.a.I(-var2.ab() - var1.at());
      this.a.J(-var2.ad() - var1.av() - var1.bm());
      this.a.cd(-var2.ai() - var1.aF() - var1.bk());
      this.a.ce(-var2.aj() - var1.aA());
      this.a.cf(-var2.ak() - var1.aB());
      this.a.cg(-var2.al() - var1.aC());
      this.a.ch(-var2.am() - var1.aE() - var1.bj());
      this.a.ci(-var2.an() - var1.aD());
      this.a.cj(-var2.ao() - var1.aG());
      this.a.cb(-var2.ah() - var1.az() - var1.bi());
      this.a.bY(-var2.af() - var1.ax() - var1.bg());
      this.a.bZ(-var2.ae() - var1.aw() - var1.bf());
      this.a.ca(-var2.ag() - var1.ay() - var1.bh());
      this.a.P(-var1.aL() - var1.bo());
      this.a.cA(-var1.aK() - var1.bn());
      this.a.Q(-var2.au() - var1.ao());
      this.a.U(-var2.as() - var1.ar());
      this.a.V(-var1.aP());
      this.a.W(-var1.aQ());
      this.a.X(-var2.at() - var1.aR());
      this.a.ac(-var2.aw() - var1.aS());
      this.a.R(-var2.aq() - var1.aM());
      this.a.S(-var2.ar() - var1.aN());
      this.a.T(-var1.aO());
      this.a.Y(-var1.aY());
      this.a.Z(-var1.ba());
      if (var1.B()) {
         boolean var4 = false;

         for (L1ItemInstance var5 : this.d) {
            if (var5.fr() != var1.fr() && var5.B()) {
               var4 = true;
               break;
            }
         }

         this.a.T(var4);
      }

      if (var1.aK() + var1.bn() != 0) {
         this.a.a(new S_PacketBox(88, this.a.fk()));
      }

      for (ArmorSetTable.L1R_a var7 : ArmorSetTable.a().b()) {
         if (var7.a(var3) && this.b.contains(var7) && !var7.a(this.a)) {
            var7.a(this.a, var1, false);
            var7.c(this.a);
            this.b.remove(var7);
         }
      }

      if (var3 == 20077 || var3 == 20062 || var3 == 120077) {
         this.a.s();
      } else if (var3 == 20281) {
         this.a.a(new S_Ability(2, false));
      } else if (var3 == 20288 || var3 == 21418) {
         this.a.a(new S_Ability(1, false));
      } else if (var3 == 20284) {
         this.a.a(new S_Ability(5, false));
      } else if (var3 == 21397) {
         this.a.a(new S_ProtoBuffers(var2.g(), 0, 0, 5934, 5934, 0, 0, 0, 1));
      } else if (var3 >= 21123 && var3 <= 21126) {
         this.a.E(false);
      } else if (var3 >= 21119 && var3 <= 21122) {
         this.a.D(false);
      } else if (var3 == 21446) {
         this.a.D(false);
      } else if (var3 == 21484) {
         this.a.P(false);
      } else if (var3 == 21485) {
         this.a.Q(false);
      } else if (var3 == 21486) {
         this.a.R(false);
      } else if (var3 == 21205 || var3 == 21517) {
         this.a.bm(0);
      } else if (var3 == 21509) {
         this.a.bn(0);
         this.a.M(false);
      } else if (var3 == 21510 || var3 == 21511) {
         this.a.bo(0);
         this.a.bp(0);
      } else if (var3 >= 21527 && var3 <= 21530) {
         this.a.bq(0);
      } else if (var3 == 21473) {
         this.a.H(false);
         this.a.G(false);
      } else if (var3 == 21206) {
         this.a.F(false);
         this.a.G(false);
      } else if (var3 == 21207 || var3 == 21208) {
         this.a.G(false);
      } else if (var3 >= 21213 && var3 <= 21221) {
         this.a.I(false);
      } else if (var3 == 21474) {
         this.a.J(false);
      } else if (var3 == 21500) {
         this.a.S(false);
      } else if (var3 >= 21437 && var3 <= 21440) {
         this.a.K(false);
      } else if (var3 >= 21420 && var3 <= 21423) {
         this.a.L(false);
      } else if (var3 >= 21369 && var3 <= 21371) {
         this.a.O(false);
      } else if (var3 >= 21363 && var3 <= 21365) {
         if (var1.G() >= 10) {
            this.a.M(false);
         } else {
            this.a.N(false);
         }
      }

      var1.u();
      this.d.remove(var1);
   }

   public void a(L1ItemInstance var1) {
      L1Item var2 = var1.a();
      if (!var1.f()) {
         if (var2.M() != 0) {
            this.a.bH(var2.M());
         }

         if (var2.N() != 0) {
            this.a.bJ(var2.N());
         }

         if (var1.ab() != 0) {
            this.a.bH(var1.ab());
         }

         if (var1.ac() != 0) {
            this.a.bJ(var1.ac());
         }

         this.a.bN(var2.G() + var1.ai());
         this.a.bP(var2.I() + var1.ak());
         this.a.bR(var2.H() + var1.aj());
         this.a.bV(var2.J() + var1.al());
         this.a.bX(var2.K() + var1.am());
         if (var2.K() + var1.am() != 0) {
            this.a.Y();
         }

         this.a.bT(var2.L() + var1.an());
         int var3 = 0;
         var3 += var1.o() + var1.af() + var1.be();
         if (var1.N() == 20236 && this.a.A()) {
            var3 += 5;
         }

         if (var3 != 0) {
            this.a.co(var3);
         }

         if (var2.Q() + var1.ae() + var1.bp() != 0) {
            this.a.cp(var2.Q() + var1.ae() + var1.bp());
         }

         this.a.ab(var2.ay() + var1.aH());
         this.a.aa(var2.av() + var1.aq());
         if (var2.S()) {
            this.a.E(1);
            this.a.V();
            if (this.a.fc() != 1) {
               this.a.cu(1);
               this.a.a(new S_SkillHaste(this.a.fr(), 1, -1));
               this.a.b(new S_SkillHaste(this.a.fr(), 1, -1));
            }
         }

         if (var1.aU()) {
            this.a.z(true);
         }

         if (var1.aV()) {
            this.a.A(true);
         }

         if (var1.aW()) {
            this.a.B(true);
         }

         if (var1.N() == 20383 && this.a.bB(1000)) {
            this.a.bA(1000);
            this.a.a(new S_SkillBrave(this.a.fr(), 0, 0));
            this.a.b(new S_SkillBrave(this.a.fr(), 0, 0));
            this.a.cv(0);
         }

         this.a.bK().c(var1);
         if (var1.g()) {
            this.d(var1);
         } else if (var1.h()) {
            this.f(var1);
         }

         ItemEnchantLevelTable.a().onEquip(this.a, var1);
         this.a.a(new S_SPMR(this.a));
      }
   }

   public void b(L1ItemInstance var1) {
      L1Item var2 = var1.a();
      if (!var1.f()) {
         if (var2.M() != 0) {
            this.a.bH(-var2.M());
         }

         if (var2.N() != 0) {
            this.a.bJ(-var2.N());
         }

         if (var1.ab() != 0) {
            this.a.bH(-var1.ab());
         }

         if (var1.ac() != 0) {
            this.a.bJ(-var1.ac());
         }

         this.a.bN(-var2.G() - var1.ai());
         this.a.bP(-var2.I() - var1.ak());
         this.a.bR(-var2.H() - var1.aj());
         this.a.bV(-var2.J() - var1.al());
         this.a.bX(-var2.K() - var1.am());
         if (var2.K() + var1.am() != 0) {
            this.a.Y();
         }

         this.a.bT(-var2.L() - var1.an());
         int var3 = 0;
         var3 -= var1.o() + var1.af() + var1.be();
         if (var1.N() == 20236 && this.a.A()) {
            var3 -= 5;
         }

         if (var3 != 0) {
            this.a.co(var3);
         }

         if (var2.Q() + var1.ae() + var1.bp() != 0) {
            this.a.cp(-var2.Q() - var1.ae() - var1.bp());
         }

         this.a.ab(-var2.ay() - var1.aH());
         this.a.aa(-var2.av() - var1.aq());
         if (var2.S()) {
            this.a.E(-1);
            if (this.a.bU() == 0) {
               this.a.cu(0);
               this.a.a(new S_SkillHaste(this.a.fr(), 0, 0));
               this.a.b(new S_SkillHaste(this.a.fr(), 0, 0));
            }
         }

         if (var1.aU()) {
            this.a.z(false);
         }

         if (var1.aV()) {
            this.a.A(false);
         }

         if (var1.aW()) {
            this.a.B(false);
         }

         this.a.bK().a(this.a.fr(), var1);
         if (var1.g()) {
            this.e(var1);
         } else if (var1.h()) {
            this.g(var1);
         }

         ItemEnchantLevelTable.a().onUnequip(this.a, var1);
         this.a.a(new S_SPMR(this.a));
      }
   }

   public void c(L1ItemInstance var1) {
      switch (var1.N()) {
         case 20008:
            this.a.f(43);
            this.a.a(new S_AddSkill(this.a, 43));
            break;
         case 20013:
            this.a.f(26);
            this.a.f(43);
            this.a.a(new S_AddSkill(this.a, 26, 43));
            break;
         case 20014:
            this.a.f(1);
            this.a.f(19);
            this.a.a(new S_AddSkill(this.a, 1, 19));
            break;
         case 20015:
            this.a.f(12);
            this.a.f(13);
            this.a.f(42);
            this.a.a(new S_AddSkill(this.a, 12, 13, 42));
            break;
         case 20023:
            this.a.f(43);
            this.a.f(54);
            this.a.a(new S_AddSkill(this.a, 43, 54));
      }
   }

   private void a(int var1, L1ItemInstance var2) {
      switch (var2.N()) {
         case 20008:
            if (!SkillsTable.a().a(var1, 43)) {
               this.a.g(43);
               this.a.a(new S_DelSkill(43));
            }
            break;
         case 20013:
            if (!SkillsTable.a().a(var1, 26)) {
               this.a.g(26);
               this.a.a(new S_DelSkill(26));
            }

            if (!SkillsTable.a().a(var1, 43)) {
               this.a.g(43);
               this.a.a(new S_DelSkill(43));
            }
            break;
         case 20014:
            if (!SkillsTable.a().a(var1, 1)) {
               this.a.g(1);
               this.a.a(new S_DelSkill(1));
            }

            if (!SkillsTable.a().a(var1, 19)) {
               this.a.g(19);
               this.a.a(new S_DelSkill(19));
            }
            break;
         case 20015:
            if (!SkillsTable.a().a(var1, 12)) {
               this.a.g(12);
               this.a.a(new S_DelSkill(12));
            }

            if (!SkillsTable.a().a(var1, 13)) {
               this.a.g(13);
               this.a.a(new S_DelSkill(13));
            }

            if (!SkillsTable.a().a(var1, 42)) {
               this.a.g(42);
               this.a.a(new S_DelSkill(42));
            }
            break;
         case 20023:
            if (!SkillsTable.a().a(var1, 43)) {
               this.a.g(43);
               this.a.a(new S_DelSkill(43));
            }

            if (!SkillsTable.a().a(var1, 54)) {
               this.a.g(54);
               this.a.a(new S_DelSkill(54));
            }
      }
   }
}
