package l1r.aj;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.RSAPrivateKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import l1r.ai.GameServer;
import l1r.ao.AccountTable;
import l1r.ao.AnnounceTable;
import l1r.aq.L1LoginCharList;
import l1r.be.S_LoginResult;
import l1r.be.S_News;
import l1r.bh.L1Account;
import l1r.bi.BinaryOutputStream;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_Login extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_Login.class.getName());

   public C_Login(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      byte[] var3 = this.a(128);
      int var4 = this.b();
      byte[] var5 = this.a(128);
      String var6 = "A15763CE9AE64683EE7D36A7886AAAC57367390105650530AEDC442FA9B173E1D183C4A96E0CAC1B0B58EA14AAD712FF923E0E17F52579E13E0CDEBF29107EE7CDFF62D3F84FD7D13896DCC80AECAD96A64AB7D3A1F3203E38C130AE70E088295890437ED267E040CB07353F805FA031721FDB8EBBFE827062F1D6B2EAF04961";
      String var7 = "84BFE5C421D9649DBB05D5896509F6F6BD6C6A0FB8039590A7DF5E4F253D77CE8ED7EF6144C63279920D979467B8CCCDF1E3132F8D2A47AD17A3F946AA200379A1D366D4161989AB621D49715BC6EB172FEFA4F1D7871A9860C40A614FAAA8D418EF88E927B66E752C1303F90F7476335E16608BE6221954DD8F5D541D73C2BD";
      PrivateKey var8 = this.a(
         "A15763CE9AE64683EE7D36A7886AAAC57367390105650530AEDC442FA9B173E1D183C4A96E0CAC1B0B58EA14AAD712FF923E0E17F52579E13E0CDEBF29107EE7CDFF62D3F84FD7D13896DCC80AECAD96A64AB7D3A1F3203E38C130AE70E088295890437ED267E040CB07353F805FA031721FDB8EBBFE827062F1D6B2EAF04961",
         "84BFE5C421D9649DBB05D5896509F6F6BD6C6A0FB8039590A7DF5E4F253D77CE8ED7EF6144C63279920D979467B8CCCDF1E3132F8D2A47AD17A3F946AA200379A1D366D4161989AB621D49715BC6EB172FEFA4F1D7871A9860C40A614FAAA8D418EF88E927B66E752C1303F90F7476335E16608BE6221954DD8F5D541D73C2BD"
      );
      Cipher var9 = Cipher.getInstance("RSA");
      var9.init(2, var8);
      byte[] var10 = var9.doFinal(var3);
      String var11 = new String(var10, Config.k);
      byte[] var12 = var9.doFinal(var5);
      int var13 = var2.a;

      for (int var14 = var4 - 1; var14 > 0; var14--) {
         int var15 = 0;

         for (int var27 = 0; var27 < 4; var27++) {
            var12[(var14 << 2) + var27] = (byte)(var12[(var14 << 2) + var27] ^ var12[(var14 << 2) - 4 + var27]);
         }

         var15 = 0;
         int var16 = var12[(var14 << 2) + var15++] & 255;
         var16 |= var12[(var14 << 2) + var15++] << 8 & 0xFF00;
         var16 |= var12[(var14 << 2) + var15++] << 16 & 0xFF0000;
         var16 |= var12[(var14 << 2) + var15++] << 24 & 0xFF000000;
         var16 = Integer.rotateRight(var16, var14) ^ var13;
         var15 = 0;
         var12[(var14 << 2) + var15++] = (byte)(var16 & 0xFF);
         var12[(var14 << 2) + var15++] = (byte)(var16 >> 8 & 0xFF);
         var12[(var14 << 2) + var15++] = (byte)(var16 >> 16 & 0xFF);
         var12[(var14 << 2) + var15++] = (byte)(var16 >> 24 & 0xFF);
      }

      byte[] var26 = new byte[]{(byte)var13, (byte)(var13 >> 8), (byte)(var13 >> 16), (byte)(var13 >> 24)};

      for (int var38 = 0; var38 < 4; var38++) {
         var12[var38] ^= var26[var38];
      }

      BinaryOutputStream var39 = new BinaryOutputStream();

      for (int var44 = 0; var44 < var4; var44++) {
         int var17 = 0;
         int var18 = var12[(var44 << 2) + var17++] & 255;
         var18 |= var12[(var44 << 2) + var17++] << 8 & 0xFF00;
         var18 |= var12[(var44 << 2) + var17++] << 16 & 0xFF0000;
         var18 |= var12[(var44 << 2) + var17++] << 24 & 0xFF000000;
         if (var18 == 0) {
            break;
         }

         var39.c(var18);
      }

      String var45 = var11.toLowerCase();
      String var50 = new String(var39.b(), Config.k);
      var39.close();
      String var54 = var2.g();
      String var19 = "";
      String var20 = "";
      if (var45.contains(":")) {
         var19 = var45.split(":")[0];
         var20 = var45.split(":")[1];
      } else {
         var19 = var45;
      }

      if (var50.contains(":")) {
         String[] var21 = var50.split(":");
         var50 = var21[0];
      }

      if (!var19.matches("[0-9a-zA-Z]*")) {
         a.log(Level.SEVERE, "不合法的帳號字元。account=" + var19 + " ip=" + var54);
         var2.a(new S_LoginResult(9));
      } else {
         L1Account var56 = AccountTable.a().c(var19);
         if (var56 == null) {
            if (!Config.n) {
               a.log(Level.SEVERE, "目前不可創帳號。account=" + var19 + " ip=" + var54);
               var2.a(new S_LoginResult(155));
               return;
            }

            if (!var20.equals("")) {
               if (!AccountTable.a().b(var20)) {
                  a.log(Level.SEVERE, "創帳號數量超過限制。account=" + var19 + " mac=" + var20);
                  var2.a(new S_LoginResult(156));
                  return;
               }
            } else if (!AccountTable.a().a(var54)) {
               a.log(Level.SEVERE, "創帳號數量超過限制。account=" + var19 + " ip=" + var54);
               var2.a(new S_LoginResult(156));
               return;
            }

            var56 = AccountTable.a().a(var19, var50, var54, var20);
         }

         if (!var56.a(var50)) {
            a.log(Level.SEVERE, "密碼錯誤。account=" + var19 + " ip=" + var54);
            var2.a(new S_LoginResult(149));
         } else if (var56.n()) {
            boolean var57 = false;

            for (ClientThread var58 : GameServer.a().c()) {
               if (var58.a() != null && var58.a().equals(var19)) {
                  a.log(Level.SEVERE, "已使用中-踢掉重複的連線及帳號。account=" + var19 + " ip=" + var54);
                  var57 = true;
                  var58.a(0);
               }
            }

            if (!var57) {
               AccountTable.a().a(var56, false);
               AccountTable.a().b(var56, false);
               AccountTable.a().e(var56.d());
               a.log(Level.SEVERE, "已使用中-踢掉重複的帳號。account=" + var19 + " ip=" + var54);
            }

            var2.a(new S_LoginResult(22));
         } else if (var56.j()) {
            a.log(Level.SEVERE, "禁止登入的帳號嘗試登入。account=" + var19 + " ip=" + var54);
            var2.a(new S_LoginResult(153));
         } else {
            if (Config.u > 0) {
               int var22 = 0;

               for (ClientThread var23 : GameServer.a().c()) {
                  if (var54.equalsIgnoreCase(var23.g())) {
                     var22++;
                  }
               }

               if (var22 > Config.u) {
                  a.log(Level.SEVERE, "超過多開設定-拒絕登入。account=" + var19 + " ip=" + var54);
                  var2.a(new S_LoginResult(38));
                  return;
               }
            }

            try {
               var56.d(var54);
               var56.f(var20);
               AccountTable.a().a(var56, var2);
               var2.a(var56);
               var2.a(new S_LoginResult(0));
               if (Config.x) {
                  AnnounceTable.a().a(var2);
                  var2.a(new S_News(var2.b()));
               } else {
                  L1LoginCharList.a(var2);
               }
            } catch (Exception var25) {
               a.log(Level.SEVERE, "登入帳號時異常。account=" + var19 + " ip=" + var54);
               a.log(Level.SEVERE, var25.getLocalizedMessage(), var25);
               var2.a(new S_LoginResult(158));
               var2.c();
            }
         }
      }
   }

   private PrivateKey a(String var1, String var2) throws Exception {
      BigInteger var3 = new BigInteger(var1, 16);
      BigInteger var4 = new BigInteger(var2, 16);
      RSAPrivateKeySpec var5 = new RSAPrivateKeySpec(var3, var4);
      KeyFactory var6 = KeyFactory.getInstance("RSA");
      return var6.generatePrivate(var5);
   }

   @Override
   public String a() {
      return "C_Login";
   }
}
