package l1r.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ak.L1Commands;
import l1r.al.L1CommandExecutor;
import l1r.ao.DoorTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_Ability;
import l1r.be.S_AddItem;
import l1r.be.S_DoActionGFX;
import l1r.be.S_EquipmentSlot;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Command;
import l1r.bi.LineageUtil;
import l1r.l1j.server.Config;

public class GMCommands {
   private static final Logger a = Logger.getLogger(GMCommands.class.getName());
   private static GMCommands b;
   private static HashMap<Integer, String> c = new HashMap<>();

   private GMCommands() {
   }

   public static GMCommands a() {
      if (b == null) {
         b = new GMCommands();
      }

      return b;
   }

   private boolean a(L1PcInstance var1, String var2, String var3) {
      try {
         L1Command var4 = L1Commands.a(var2);
         if (var4 == null) {
            return false;
         } else if (var1.az() < var4.b()) {
            var1.a(new S_ServerMessage(74, "指令" + var2));
            return true;
         } else {
            L1CommandExecutor var5 = LineageUtil.c(var4.c());
            var5.a(var1, var2, var3);
            System.out.println(var1.et() + "使用 ." + var2 + " " + var3 + "的指令。");
            return true;
         }
      } catch (Exception var6) {
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         return false;
      }
   }

   public void a(L1PcInstance var1, String var2) {
      StringTokenizer var3 = new StringTokenizer(var2);
      if (!var3.hasMoreTokens()) {
         var1.a(new S_SystemMessage("指令參數不足。"));
         return;
      }

      String var4 = var3.nextToken();
      String var5 = "";

      while (var3.hasMoreTokens()) {
         var5 = var5 + var3.nextToken() + ' ';
      }

      var5 = var5.trim();
      var1.a(new S_Ability(3, 1));
      if (var4.equalsIgnoreCase("1")) {
         StringTokenizer var20 = new StringTokenizer(var5);
         if (!var20.hasMoreTokens()) {
            var1.a(new S_SystemMessage("指令參數不足。"));
            return;
         }

         int var26 = Integer.parseInt(var20.nextToken());
         L1ItemInstance var32 = var1.j().b(40308);
         var1.a(new S_EquipmentSlot(var32.fr(), var26, true));
      } else if (var4.equalsIgnoreCase("ca")) {
         StringTokenizer var19 = new StringTokenizer(var5);
         if (var19.countTokens() < 2) {
            var1.a(new S_SystemMessage("指令參數不足。"));
            return;
         }

         int var25 = Integer.parseInt(var19.nextToken());
         int var31 = Integer.parseInt(var19.nextToken());
         var1.a(new S_SkillSound(var1.fr(), var25));
         var1.a(new S_DoActionGFX(var1.fr(), var31));
      } else {
         if (var4.equalsIgnoreCase("6")) {
            L1ItemInstance var6 = var1.j().b(40308);

            for (int var7 = 0; var7 < 256; var7++) {
               var1.a(new S_AddItem(var6, var7));
            }
         } else {
            if (var4.equalsIgnoreCase("4")) {
               StringTokenizer var18 = new StringTokenizer(var5);
               if (!var18.hasMoreTokens()) {
                  var1.a(new S_SystemMessage("指令參數不足。"));
                  return;
               }

               int var24 = Integer.parseInt(var18.nextToken());
               L1DoorInstance[] var35;
               int var34 = (var35 = DoorTable.b().c()).length;

               for (int var33 = 0; var33 < var34; var33++) {
                  L1DoorInstance var30 = var35[var33];
                  if (var30.fe() == var24) {
                     L1Teleport.a(var1, var30.fs(), var30.ft(), var30.fp(), 0, true);
                     break;
                  }
               }

               return;
            }

            if (var4.equalsIgnoreCase("3")) {
               for (L1Object var17 : L1World.a().b(var1, 3)) {
                  if (var17 instanceof L1NpcInstance) {
                     L1NpcInstance var29 = (L1NpcInstance)var17;
                     if (var29.z() == 45060) {
                        var29.aa_();
                     }
                  }
               }

               return;
            }

            if (var4.equalsIgnoreCase("2")) {
               try {
                  File var16 = new File("./Result.txt");
                  LineNumberReader var22 = new LineNumberReader(new BufferedReader(new FileReader(var16)));
                  String var8 = null;
                  String var9 = "";

                  while ((var8 = var22.readLine()) != null) {
                     var8 = var8.trim();
                     var9 = var9 + var8 + " ";
                     if (!var8.startsWith("#") && !var8.startsWith(" ") && var8.length() == 0) {
                     }
                  }

                  String[] var10 = var9.trim().split(" ");
                  byte[] var11 = new byte[var10.length];

                  for (int var12 = 0; var12 < var10.length; var12++) {
                     var11[var12] = this.a(var10[var12])[0];
                  }

                  var1.a(new S_PacketBox(var11));
                  var22.close();
               } catch (Exception var13) {
                  a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
               }

               return;
            }

            if (var4.equalsIgnoreCase("0")) {
               var1.j(1026, 1200000);
               var1.a(new S_SkillBrave(var1.fr(), 5, 1200));
               var1.b(new S_SkillBrave(var1.fr(), 5, 1200));
               var1.cv(5);
               var1.a(new S_SkillSound(var1.fr(), 12360));
               var1.b(new S_SkillSound(var1.fr(), 12360));
               return;
            }

            if (var4.equalsIgnoreCase("cp")) {
               Config.d = !Config.d;
               var1.a(new S_SystemMessage("Config.C_PACKET= " + Config.d));
               return;
            }

            if (var4.equalsIgnoreCase("sp")) {
               Config.e = !Config.e;
               var1.a(new S_SystemMessage("Config.S_PACKET= " + Config.e));
               return;
            }

            if (var4.equalsIgnoreCase("clearinv")) {
               for (L1ItemInstance var15 : var1.j().d()) {
                  if (var15.N() != 640104
                     && var15.N() != 640382
                     && var15.N() != 40308
                     && var15.N() != 640268
                     && var15.N() != 640312
                     && var15.N() != 640621
                     && var15.N() != 640769
                     && var15.G() < 5
                     && !var15.D()) {
                     var1.j().f(var15);
                  }
               }

               var1.a(new S_SystemMessage("Inventory clear !!"));
               return;
            }
         }

         if (this.a(var1, var4, var5)) {
            if (!var4.equalsIgnoreCase("r")) {
               c.put(var1.fr(), var2);
            }
         } else if (var4.equalsIgnoreCase("r")) {
            if (!c.containsKey(var1.fr())) {
               var1.a(new S_ServerMessage(74, "指令" + var4));
            } else {
               this.b(var1, var5);
            }
         } else {
            var1.a(new S_SystemMessage("指令 " + var4 + " 不存在。"));
         }
      }
   }

   private void b(L1PcInstance var1, String var2) {
      try {
         String var3 = c.get(var1.fr());
         if (var2.isEmpty()) {
            var1.a(new S_SystemMessage("指令 " + var3 + " 重新執行。"));
            this.a(var1, var3);
         } else {
            StringTokenizer var4 = new StringTokenizer(var3);
            String var5 = var4.nextToken() + " " + var2;
            var1.a(new S_SystemMessage("指令 " + var5 + " 執行。"));
            this.a(var1, var5);
         }
      } catch (Exception var6) {
         var1.a(new S_SystemMessage(".r 指令錯誤。"));
         a.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
      }
   }

   private byte[] a(String var1) {
      char[] var2 = var1.toCharArray();
      int var3 = var2.length / 2;
      byte[] var4 = new byte[var3];

      for (int var5 = 0; var5 < var3; var5++) {
         int var6 = Character.digit(var2[var5 * 2], 16);
         int var7 = Character.digit(var2[var5 * 2 + 1], 16);
         int var8 = var6 << 4 | var7;
         if (var8 > 127) {
            var8 -= 256;
         }

         var4[var5] = (byte)var8;
      }

      return var4;
   }
}
