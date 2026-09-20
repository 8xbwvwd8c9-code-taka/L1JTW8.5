package l1r.al;

import java.util.HashMap;
import java.util.StringTokenizer;
import l1r.ao.NpcSpawnTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Spawn;
import l1r.aq.L1Teleport;
import l1r.be.S_SystemMessage;

public class L1ToSpawn implements L1CommandExecutor {
   private static final HashMap<Integer, Integer> a = new HashMap<>();

   private L1ToSpawn() {
   }

   public static L1CommandExecutor a() {
      return new L1ToSpawn();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         if (!a.containsKey(var1.fr())) {
            a.put(var1.fr(), 0);
         }

         int var4 = a.get(var1.fr());
         if (var3.isEmpty() || var3.equals("+")) {
            var4++;
         } else if (var3.equals("-")) {
            var4--;
         } else {
            StringTokenizer var5 = new StringTokenizer(var3);
            var4 = Integer.parseInt(var5.nextToken());
         }

         L1Spawn var8 = NpcSpawnTable.a().a(var4);
         if (var8 == null) {
            var8 = SpawnTable.a().a(var4);
         }

         if (var8 != null) {
            L1Teleport.a(var1, var8.f(), var8.g(), var8.n(), 5, true);
            var1.a(new S_SystemMessage("spawnid(" + var4 + ")已傳送到"));
         } else {
            var1.a(new S_SystemMessage("spawnid(" + var4 + ")找不到"));
         }

         a.put(var1.fr(), var4);
      } catch (Exception var6) {
         var1.a(new S_SystemMessage(var2 + " spawnid|+|-"));
      }
   }
}
