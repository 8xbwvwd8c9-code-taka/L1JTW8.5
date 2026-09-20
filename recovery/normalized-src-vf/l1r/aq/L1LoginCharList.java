package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.AccountTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.be.S_CharAmount;
import l1r.be.S_CharEvent;
import l1r.be.S_CharList;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class L1LoginCharList {
   private static final Logger a = Logger.getLogger(L1LoginCharList.class.getName());

   public static void a(ClientThread var0) {
      b(var0);
      c(var0);
      int var1 = var0.e().b();
      var0.a(new S_CharAmount(var1, var0));
      var0.a(new S_CharEvent(10));
      if (var1 > 0) {
         d(var0);
      }

      var0.a(new S_CharEvent(64));
      var0.a(new S_ProtoBuffers(450, var0.e().q()));
      AccountTable.a().d(var0.e());
      AccountTable.a().b(var0.e());
   }

   private static void b(ClientThread var0) {
      String[] var1 = new String[0];
      String[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         String var2 = var5[var3];
         String[] var6 = var2.trim().split(" ");
         byte[] var7 = new byte[var6.length];

         for (int var8 = 0; var8 < var6.length; var8++) {
            var7[var8] = a(var6[var8])[0];
         }

         var0.a(new S_PacketBox(var7));
      }
   }

   private static byte[] a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length / 2;
      byte[] var3 = new byte[var2];

      for (int var4 = 0; var4 < var2; var4++) {
         int var5 = Character.digit(var1[var4 * 2], 16);
         int var6 = Character.digit(var1[var4 * 2 + 1], 16);
         int var7 = var5 << 4 | var6;
         if (var7 > 127) {
            var7 -= 256;
         }

         var3[var4] = (byte)var7;
      }

      return var3;
   }

   private static void c(ClientThread var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
         var2.setString(1, var0.a());
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("char_name");
            String var5 = var3.getString("Clanname");
            Timestamp var6 = var3.getTimestamp("DeleteTime");
            if (var6 != null) {
               Calendar var7 = Calendar.getInstance();
               long var8 = (var7.getTimeInMillis() - var6.getTime()) / 1000L / 3600L;
               if (var8 >= 0L) {
                  L1Clan var10 = ClanTable.a().c(var5);
                  if (var10 != null) {
                     var10.b(var4);
                  }

                  CharacterTable.a().a(var0.a(), var4);
               }
            }
         }
      } catch (Exception var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private static void d(ClientThread var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
         var2.setString(1, var0.a());
         var3 = var2.executeQuery();

         while (var3.next()) {
            String var4 = var3.getString("char_name");
            String var5 = var3.getString("Clanname");
            int var6 = var3.getInt("Type");
            byte var7 = var3.getByte("Sex");
            int var8 = var3.getInt("Lawful");
            int var9 = var3.getInt("CurHp");
            if (var9 < 1) {
               var9 = 1;
            } else if (var9 > 32767) {
               var9 = 32767;
            }

            int var10 = var3.getInt("CurMp");
            if (var10 < 1) {
               var10 = 1;
            } else if (var10 > 32767) {
               var10 = 32767;
            }

            int var11 = var3.getInt("level");
            if (var11 < 1) {
               var11 = 1;
            } else if (var11 > 127) {
               var11 = 127;
            }

            int var12 = var3.getInt("Ac");
            int var13 = var3.getInt("Str");
            int var14 = var3.getInt("Dex");
            int var15 = var3.getInt("Con");
            int var16 = var3.getInt("Wis");
            int var17 = var3.getInt("Cha");
            int var18 = var3.getInt("Intel");
            int var19 = var3.getInt("AccessLevel");
            Timestamp var20 = var3.getTimestamp("birthday");
            SimpleDateFormat var21 = new SimpleDateFormat("yyyyMMdd");
            int var22 = Integer.parseInt(var21.format(var20.getTime()));
            S_CharList var23 = new S_CharList(var4, var5, var6, var7, var8, var9, var10, var12, var11, var13, var14, var15, var16, var17, var18, var19, var22);
            var0.a(var23);
            Timestamp var24 = var0.e().g();
            Timestamp var25 = var3.getTimestamp("TamUseTime");
            Timestamp var26 = new Timestamp(System.currentTimeMillis());
            if (var25 != null) {
               long var27 = 0L;
               if (var25.after(var26)) {
                  var27 = var26.getTime() - var24.getTime();
                  var0.e().c();
               } else {
                  var27 = var25.getTime() - var24.getTime();
                  CharacterTable.a().a(var3.getInt("objid"));
               }

               if (var25.getTime() - var26.getTime() > 0L) {
                  var0.e().c((int)(var25.getTime() - var26.getTime()) / 1000);
               }

               var0.e().b(1200 * (int)(var27 / 1000000L));
            }
         }
      } catch (Exception var32) {
         a.log(Level.SEVERE, var32.getLocalizedMessage(), var32);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }
}
