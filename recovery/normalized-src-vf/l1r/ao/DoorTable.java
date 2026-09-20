package l1r.ao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import l1r.ai.IdFactory;
import l1r.ap.L1DoorInstance;
import l1r.aq.L1Location;
import l1r.aq.L1World;
import l1r.bh.L1DoorGfx;
import l1r.bh.L1DoorSpawn;
import l1r.bh.L1Npc;

public class DoorTable {
   private static DoorTable a;
   private final ConcurrentHashMap<L1Location, L1DoorInstance> b = new ConcurrentHashMap<>();
   private final ConcurrentHashMap<L1Location, L1DoorInstance> c = new ConcurrentHashMap<>();

   public static void a() {
      a = new DoorTable();
   }

   public static DoorTable b() {
      return a;
   }

   private DoorTable() {
      this.d();
   }

   private void d() {
      for (L1DoorSpawn var1 : L1DoorSpawn.i()) {
         L1Location var3 = new L1Location(var1.c(), var1.d(), var1.e());
         if (this.b.containsKey(var3)) {
            System.out.println(String.format("Duplicate door location: id = %d", var1.a()));
         } else {
            this.a(var1.a(), var1.b(), var3, var1.f(), var1.g(), var1.h());
         }
      }
   }

   private void a(L1DoorInstance var1) {
      for (L1Location var2 : this.c(var1)) {
         this.c.put(var2, var1);
      }
   }

   private void b(L1DoorInstance var1) {
      for (L1Location var2 : this.c(var1)) {
         this.c.remove(var2);
      }
   }

   private List<L1Location> c(L1DoorInstance var1) {
      ArrayList var2 = new ArrayList<>();
      int var3 = var1.ac_();
      int var4 = var1.n();
      if (var1.j() == 0) {
         for (int var5 = var3; var5 <= var4; var5++) {
            var2.add(new L1Location(var5, var1.ft(), var1.fp()));
         }
      } else {
         for (int var6 = var3; var6 <= var4; var6++) {
            var2.add(new L1Location(var1.fs(), var6, var1.fp()));
         }
      }

      return var2;
   }

   public L1DoorInstance a(int var1, L1DoorGfx var2, L1Location var3, int var4, int var5, boolean var6) {
      if (this.b.containsKey(var3)) {
         return null;
      }

      L1Npc var7 = NpcTable.a().a(189997);
      var7.k(var2.a());
      L1DoorInstance var8 = new L1DoorInstance(var7, var1, var2, var3, var4, var5, var6);
      var8.cF(IdFactory.a().c());
      L1World.a().a(var8);
      L1World.a().c(var8);
      this.b.put(var8.fu(), var8);
      this.a(var8);
      return var8;
   }

   public void a(L1Location var1) {
      L1DoorInstance var2 = this.b.remove(var1);
      if (var2 != null) {
         this.b(var2);
         var2.aa_();
      }
   }

   public L1DoorInstance a(int var1, int var2, int var3) {
      for (L1DoorInstance var4 : this.b.values()) {
         if (var4.fs() == var1 && var4.ft() == var2 && var4.fp() == var3) {
            return var4;
         }
      }

      return null;
   }

   public L1DoorInstance[] c() {
      return this.b.values().toArray(new L1DoorInstance[this.b.size()]);
   }
}
