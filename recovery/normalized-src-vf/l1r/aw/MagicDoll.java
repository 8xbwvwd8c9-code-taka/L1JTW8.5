package l1r.aw;

import l1r.ao.NpcTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;
import l1r.l1j.server.Config;

public class MagicDoll {
   public static void a(L1PcInstance var0, L1ItemInstance var1) {
      for (L1DollInstance var2 : var0.el().values()) {
         if (var2.f() == var1.fr()) {
            var2.e();
            return;
         }
      }

      if (!var0.j().g(41246, 50)) {
         var0.a(new S_ServerMessage(337, "$5240"));
      } else if (var0.el().size() >= Config.ao) {
         var0.a(new S_ServerMessage(1529, String.valueOf(Config.ao)));
      } else {
         L1Npc var4 = NpcTable.a().a(189998);
         if (var1.br() != 0 && var1.bq() != null) {
            L1DollInstance var5 = new L1DollInstance(var4, var0, var1);
            var0.a(new S_SkillSound(var5.fr(), 5935));
            var0.b(new S_SkillSound(var5.fr(), 5935));
            var0.a(new S_PacketBox(56, 1800));
            var0.a(new S_OwnCharStatus(var0));
            var0.j().b(41246, 50);
         } else {
            var0.a(new S_SystemMessage("魔法娃娃召喚異常，請告知GM從何取得此娃娃"));
         }
      }
   }
}
