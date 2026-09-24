package l1r.ai;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.am.MonsterListReader;
import l1r.ao.AccountTable;
import l1r.ao.AnnounceTable;
import l1r.ao.BossSpawnTable;
import l1r.ao.CastleTable;
import l1r.ao.CharacterGiftTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.CraftListTable;
import l1r.ao.DoorTable;
import l1r.ao.DropTable;
import l1r.ao.FieldSpawnTable;
import l1r.ao.FurnitureSpawnTable;
import l1r.ao.GetBackRestartTable;
import l1r.ao.HistoryTable;
import l1r.ao.HtmlCraftTable;
import l1r.ao.HtmlTable;
import l1r.ao.HtmlTeleportTable;
import l1r.ao.InnTable;
import l1r.ao.IpTable;
import l1r.ao.ItemContinuousEffectTable;
import l1r.ao.ItemEnchantLevelTable;
import l1r.ao.ItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.MagicDollTable;
import l1r.ao.MailTable;
import l1r.ao.MapsTable;
import l1r.ao.MobGroupTable;
import l1r.ao.MobQuestWeekTable;
import l1r.ao.MobSkillsTable;
import l1r.ao.NpcChatTable;
import l1r.ao.NpcSpawnTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTable;
import l1r.ao.PetTypeTable;
import l1r.ao.PolyTable;
import l1r.ao.QuestNewTable;
import l1r.ao.RankingTable;
import l1r.ao.ResolventTable;
import l1r.ao.ShopTable;
import l1r.ao.ShopWorldTable;
import l1r.ao.SkillsTable;
import l1r.ao.SpawnTable;
import l1r.ao.TrapSpawnTable;
import l1r.ao.TreasureBoxTable;
import l1r.ao.WeaponSkillTable;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Dungeon;
import l1r.aq.L1Getback;
import l1r.aq.L1GuardianSoul;
import l1r.aq.L1Master;
import l1r.aq.L1World;
import l1r.as.L1BugBearRace;
import l1r.as.L1CastleWar;
import l1r.as.L1IceQueen;
import l1r.as.L1SoulStone;
import l1r.as.L1SoulTower;
import l1r.as.L1ThebesBattle;
import l1r.at.L1GameTimeClock;
import l1r.ax.L1WorldMap;
import l1r.ba.AnnounceTimer;
import l1r.ba.ClearGroundTimer;
import l1r.ba.ClientAliveTimer;
import l1r.ba.CurrentTimeReseter;
import l1r.ba.ElementalStoneTimer;
import l1r.ba.FieldLightTimer;
import l1r.ba.HomeTownTimer;
import l1r.ba.HouseTimer;
import l1r.ba.InventoryTimer;
import l1r.bc.FishingTimer;
import l1r.be.S_ServerMessage;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class GameServer {
   private static final Logger b = Logger.getLogger(GameServer.class.getName());
   public final int a = (int)(System.currentTimeMillis() / 1000L);
   private static GameServer c;
   private final ConcurrentHashMap<String, GameServer.L1R_b> d = new ConcurrentHashMap<>();
   private final CopyOnWriteArrayList<String> e = new CopyOnWriteArrayList<>();
   private final CopyOnWriteArrayList<ClientThread> f = new CopyOnWriteArrayList<>();
   private GameServer.L1R_e g = null;
   private static int h = 0;

   public static GameServer a() {
      if (c == null) {
         c = new GameServer();
      }

      return c;
   }

   public void b() throws Exception {
      if (Config.c) {
         Config.o = 10;
      }

      double var1 = Config.B;
      double var3 = Config.C;
      double var5 = Config.D;
      double var7 = Config.F;
      double var9 = Config.E;
      int var11 = Config.N;
      int var12 = Config.g;
      System.out.println("┌「經驗值」: " + var1 + "【倍】");
      System.out.println("├「正義值」: " + var3 + "【倍】");
      System.out.println("├「友好度」: " + var5 + "【倍】");
      System.out.println("├「物品掉落」: " + var7 + "【倍】");
      System.out.println("├「金幣掉落」: " + var9 + "【倍】");
      System.out.println("├「廣播頻道可用等級」: " + var11 + "【級】");
      System.out.println("└「Non-PvP設定」: " + (Config.R ? "【有效 (PvP不可)】" : "【無效 (PvP可能)】") + "\n");
      System.out.println("連線人數上限: " + Config.o + " 人 ");
      if (Config.c) {
         System.out.println("【！！！】此模擬器僅提供單機測試，已限制連線人數");
         System.out.println("【！！！】此模擬器僅提供單機測試，已限制連線人數");
         System.out.println("【！！！】此模擬器僅提供單機測試，已限制連線人數");
      }

      IdFactory.a();
      L1WorldMap.b();
      MonsterListReader.a();
      AccountTable.a();
      CharacterTable.a();
      L1GameTimeClock.a();
      if (Config.am > 0) {
         ElementalStoneTimer var13 = ElementalStoneTimer.a();
         GeneralThreadPool.a().a(var13);
      }

      HomeTownTimer.a();
      FishingTimer.b();
      FieldLightTimer var14 = FieldLightTimer.a();
      GeneralThreadPool.a().a(var14);
      AnnounceTimer.a();
      ClientAliveTimer.a();
      NpcTable.a();
      if (!NpcTable.a().b()) {
         throw new Exception("Could not initialize the npc table");
      }

      MapsTable.a();
      DoorTable.a();
      ListSprReader__obf_c.a();
      AnnounceTable.a();
      L1SoulTower.a();
      SpawnTable.a();
      MobGroupTable.a();
      SkillsTable.a();
      PolyTable.a();
      ResolventTable.a();
      ItemTable.a();
      ItemEnchantLevelTable.a();
      ItemContinuousEffectTable.a();
      DropTable.a();
      ShopTable.a();
      L1World.a();
      TrapSpawnTable.a();
      NpcSpawnTable.a();
      IpTable.a();
      PetTable.a();
      ClanTable.a();
      CastleTable.a();
      L1CastleLocation.a();
      GetBackRestartTable.a();
      GeneralThreadPool.a();
      WeaponSkillTable.a();
      L1Getback.a();
      PetTypeTable.a();
      TreasureBoxTable.a();
      BossSpawnTable.a();
      FurnitureSpawnTable.a();
      NpcChatTable.a();
      MailTable.a();
      L1BugBearRace.a();
      InnTable.a();
      L1Dungeon.a();
      MagicDollTable.b();
      FieldSpawnTable.a();
      RankingTable.a();
      ShopWorldTable.a();
      LuckyDrawTable.a();
      HtmlCraftTable.a();
      HtmlTeleportTable.a();
      HtmlTable.a();
      CraftListTable.a();
      CharacterGiftTable.a();
      MobSkillsTable.a();
      HistoryTable.a();
      ClearGroundTimer.a();
      InventoryTimer.a();
      HouseTimer.a();
      L1CastleWar.a();
      MobQuestWeekTable.a();
      L1SoulStone.a();
      L1IceQueen.a();
      L1GuardianSoul.a();
      L1Master.a();
      QuestNewTable.a();
      LineageUtil.c();
      CurrentTimeReseter.a();
      L1ThebesBattle.a();
      LineageUtil.b();
      System.out.println("初始化完畢");
      GeneralThreadPool.a().a(new GameServer.L1R_a(this.d), 60000L, 60000L);
      GeneralThreadPool.a().a(new GameServer.L1R_a(this.e), 1800000L, 1800000L);
      GeneralThreadPool.a().a(new GameServer.L1R_d(new ServerSocket(Config.g, -1), null));
      System.out.println("使用了: " + LineageUtil.a() + "MB 的記憶體");
      System.out.println(GeneralThreadPool.a().b());
      System.out.println("等待客戶端連接中...");
   }

   public void a(ClientThread var1) {
      this.f.add(var1);
   }

   public void b(ClientThread var1) {
      this.f.remove(var1);
   }

   public CopyOnWriteArrayList<ClientThread> c() {
      return this.f;
   }

   public void a(int var1, boolean var2) {
      if (this.g == null) {
         this.g = new GameServer.L1R_e(var1, var2);
         GeneralThreadPool.a().a(this.g);
      }
   }

   public void a(boolean var1) {
      for (ClientThread var2 : this.c()) {
         if (var2 != null) {
            try {
               var2.a(231);
            } catch (Exception var5) {
               b.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
            }
         }
      }

      Config.b = true;
      GeneralThreadPool.a().a(new GameServer.L1R_c(null), 60000L);

      try {
         int var7 = 0;

         while (true) {
            if (this.c().isEmpty() || var7++ > 180) {
               if (var1) {
                  Runtime.getRuntime().exec("cmd /c start " + Config.A);
               }

               System.exit(0);
            }

            Thread.sleep(1000L);
         }
      } catch (Exception var6) {
         b.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      }
   }

   public void d() {
      if (this.g != null) {
         this.g.interrupt();
         this.g = null;
      }
   }

   public static int e() {
      return ++h;
   }

   class L1R_a extends TimerTask {
      private CopyOnWriteArrayList<?> b = null;
      private ConcurrentHashMap<String, GameServer.L1R_b> c = null;

      public L1R_a(CopyOnWriteArrayList<?> var2) {
         this.b = var2;
      }

      public L1R_a(ConcurrentHashMap<String, GameServer.L1R_b> var2) {
         this.c = var2;
      }

      @Override
      public void run() {
         try {
            if (this.b != null) {
               this.b.clear();
            } else if (this.c != null) {
               this.c.clear();
            }
         } catch (Exception var2) {
            GameServer.b.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }
   }

   class L1R_b {
      public String a;
      public int b = 0;

      public L1R_b(String var2) {
         this.a = var2;
      }
   }

   private class L1R_c implements Runnable {
      private L1R_c() {
      }

      @Override
      public void run() {
         System.exit(0);
      }

      // $VF: synthetic method
      L1R_c(GameServer.L1R_c var2) {
         this();
      }
   }

   private class L1R_d extends Thread {
      private final ServerSocket b;

      private L1R_d(ServerSocket var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         try {
            while (true) {
               Socket var1 = this.b.accept();
               if (!Config.b) {
                  if (var1 == null) {
                     continue;
                  }

                  String var2 = var1.getInetAddress().getHostAddress();
                  System.out.println("從 " + var1.getInetAddress() + " 試圖連線");
                  if (IpTable.a().b(var2)) {
                     System.out.println("banned IP(" + var2 + ")");
                  } else {
                     String var3 = var1.getInetAddress().getHostAddress();
                     if (GameServer.this.e.contains(var3)) {
                        continue;
                     }

                     GameServer.L1R_b var4;
                     if (GameServer.this.d.containsKey(var3)) {
                        var4 = GameServer.this.d.get(var3);
                     } else {
                        var4 = GameServer.this.new L1R_b(var3);
                        GameServer.this.d.put(var3, var4);
                     }

                     if (++var4.b > 60) {
                        if (!GameServer.this.e.contains(var3)) {
                           GameServer.this.e.add(var3);
                        }

                        GameServer.b.log(Level.SEVERE, "短時間內連線次數過多加入黑名單 IP=" + var3);
                     } else {
                        var1.setTcpNoDelay(true);
                        var1.setSoLinger(true, 0);
                        if (!Config.c || GameServer.this.c().size() < 10) {
                           ClientThread var5 = new ClientThread(var1);
                           GameServer.this.a(var5);
                           GeneralThreadPool.a().b(var5);
                        }
                     }
                  }
                  continue;
               }
            }
         } catch (Exception var6) {
            GameServer.b.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         }
      }

      // $VF: synthetic method
      L1R_d(ServerSocket var2, GameServer.L1R_d var3) {
         this(var2);
      }
   }

   private class L1R_e extends Thread {
      private final int b;
      private boolean c = false;

      public L1R_e(int var2, boolean var3) {
         this.b = var2;
         this.c = var3;
      }

      @Override
      public void run() {
         L1World var1 = L1World.a();

         try {
            int var2 = this.b;
            var1.d("伺服器即將關閉。");
            var1.d("請玩家移動到安全區域先行登出");

            while (var2 > 0) {
               if (var2 <= 30) {
                  var1.a(new S_ServerMessage(72, "" + var2));
               } else if (var2 % 60 == 0) {
                  var1.a(new S_ServerMessage(72, "" + var2));
               }

               Thread.sleep(1000L);
               var2--;
            }

            GameServer.this.a(this.c);
         } catch (InterruptedException var3) {
            var1.d("已取消伺服器關機。伺服器將會正常運作。");
         }
      }
   }
}
