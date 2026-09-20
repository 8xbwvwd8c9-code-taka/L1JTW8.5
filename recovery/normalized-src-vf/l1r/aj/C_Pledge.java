package l1r.aj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ao.ExpTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.be.S_PacketBox;
import l1r.be.S_Pledge;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class C_Pledge extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_Pledge.class.getName());

   public C_Pledge(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         if (var3.aF() > 0) {
            L1Clan var4 = ClanTable.a().a(var3.aF());
            var3.a(new S_ProtoBuffers(333, var3));
            var3.a(new S_ProtoBuffers(325, var3));
            var3.a(new S_Pledge(var4.e()));
            var3.a(new S_Pledge(this.b(var4.e())));
            L1PcInstance[] var5 = var4.b().toArray(new L1PcInstance[var4.b().size()]);
            var3.a(new S_PacketBox(171, var5));
         } else {
            var3.a(new S_ServerMessage(1064));
         }
      }
   }

   private ArrayList<C_Pledge.L1R_a> b(int var1) {
      ArrayList var2 = new ArrayList<>();
      L1Clan var3 = ClanTable.a().a(var1);
      Connection var4 = null;
      PreparedStatement var5 = null;
      ResultSet var6 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("SELECT * FROM characters WHERE char_name=?");

         for (String var7 : var3.p()) {
            var5.setString(1, var7);
            var6 = var5.executeQuery();
            if (var6.next()) {
               C_Pledge.L1R_a var9 = new C_Pledge.L1R_a();
               var9.a = var6.getString("char_name");
               var9.b = var6.getInt("ClanRank");
               var9.c = ExpTable.c(var6.getInt("Exp"));
               var9.d = var6.getInt("objid");
               var9.e = var6.getInt("Type");
               ClanMembersTable.a().a(var9);
               var2.add(var9);
            }
         }
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var6, var5, var4);
      }

      return var2;
   }

   @Override
   public String a() {
      return "C_Pledge";
   }

   public class L1R_a {
      public String a;
      public int b;
      public int c;
      public int d;
      public int e;
      public int f;
      public String g = "";
   }
}
