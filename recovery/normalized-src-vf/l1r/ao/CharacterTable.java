package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.ax.L1Map;
import l1r.ax.L1WorldMap;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterTable {
   private static final Logger a = Logger.getLogger(CharacterTable.class.getName());
   private static CharacterTable b;
   private final ConcurrentHashMap<String, CharacterTable.L1R_a> c = new ConcurrentHashMap<>();

   public static CharacterTable a() {
      if (b == null) {
         b = new CharacterTable();
      }

      return b;
   }

   public CharacterTable() {
      this.e();
      this.d();
      this.b();
   }

   public void a(L1PcInstance var1) throws Exception {
      synchronized (var1) {
         Connection var3 = null;
         PreparedStatement var4 = null;

         try {
            int var5 = 0;
            var3 = DatabaseFactory.a().b();
            var4 = var3.prepareStatement(
               "INSERT INTO characters SET account_name=?,objid=?,char_name=?,birthday=?,level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,MasterID=?,ClanID=?,Clanname=?,ClanRank=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,Pay=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,DeleteTime=?"
            );
            var4.setString(++var5, var1.bc());
            var4.setInt(++var5, var1.fr());
            var4.setString(++var5, var1.et());
            var4.setInt(++var5, var1.o());
            var4.setInt(++var5, var1.ev());
            var4.setInt(++var5, var1.bz());
            var4.setInt(++var5, var1.m());
            var4.setInt(++var5, var1.bd());
            var4.setInt(++var5, Math.max(1, var1.ea()));
            var4.setInt(++var5, var1.be());
            var4.setInt(++var5, var1.eb());
            var4.setInt(++var5, var1.ey());
            var4.setInt(++var5, var1.bf());
            var4.setInt(++var5, var1.bg());
            var4.setInt(++var5, var1.bh());
            var4.setInt(++var5, var1.bi());
            var4.setInt(++var5, var1.bj());
            var4.setInt(++var5, var1.bk());
            var4.setInt(++var5, var1.k());
            var4.setInt(++var5, var1.aB());
            var4.setInt(++var5, var1.aJ());
            var4.setInt(++var5, var1.ay());
            var4.setInt(++var5, var1.fb());
            var4.setInt(++var5, var1.fs());
            var4.setInt(++var5, var1.ft());
            var4.setInt(++var5, var1.fp());
            var4.setInt(++var5, var1.fj());
            var4.setInt(++var5, var1.fa());
            var4.setString(++var5, var1.eZ());
            var4.setInt(++var5, var1.cE());
            var4.setInt(++var5, var1.aF());
            var4.setString(++var5, var1.aG());
            var4.setInt(++var5, var1.aH());
            var4.setInt(++var5, var1.bA());
            var4.setInt(++var5, var1.bB());
            var4.setInt(++var5, var1.bC());
            var4.setInt(++var5, var1.aD());
            var4.setInt(++var5, var1.aE());
            var4.setInt(++var5, var1.ca());
            var4.setInt(++var5, var1.bD());
            var4.setInt(++var5, var1.az());
            var4.setInt(++var5, var1.bE());
            var4.setInt(++var5, var1.bF());
            var4.setInt(++var5, var1.bG());
            var4.setInt(++var5, 0);
            var4.setInt(++var5, var1.bI());
            var4.setBoolean(++var5, var1.bJ());
            var4.setInt(++var5, var1.P());
            var4.setTimestamp(++var5, var1.bO());
            var4.setTimestamp(++var5, var1.bP());
            var4.setTimestamp(++var5, var1.bQ());
            var4.execute();
         } catch (SQLException var10) {
            a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            SQLUtil.a(var4);
            SQLUtil.a(var3);
         }

         String var64 = var1.et();
         if (!this.c.containsKey(var64)) {
            CharacterTable.L1R_a var6 = new CharacterTable.L1R_a();
            var6.b = var64;
            var6.a = var1.fr();
            this.c.put(var64, var6);
         }
      }
   }

   public void b(L1PcInstance var1) throws Exception {
      synchronized (var1) {
         Connection var3 = null;
         PreparedStatement var4 = null;

         try {
            int var5 = 0;
            var3 = DatabaseFactory.a().b();
            var4 = var3.prepareStatement(
               "UPDATE characters SET level=?,HighLevel=?,Exp=?,MaxHp=?,CurHp=?,MaxMp=?,CurMp=?,Ac=?,Str=?,Con=?,Dex=?,Cha=?,Intel=?,Wis=?,Status=?,Class=?,Sex=?,Type=?,Heading=?,LocX=?,LocY=?,MapID=?,Food=?,Lawful=?,Title=?,MasterID=?,ClanID=?,Clanname=?,ClanRank=?,BonusStatus=?,ElixirStatus=?,ElfAttr=?,PKcount=?,PkCountForElf=?,ExpRes=?,PartnerID=?,AccessLevel=?,OnlineStatus=?,HomeTownID=?,Contribution=?,HellTime=?,Banned=?,Karma=?,LastPk=?,LastPkForElf=?,LogoutTime=?,DeleteTime=?,TamUseTime=?,EinhasadsBless=?,CharStoreSpace=?,RuneOpenStatus=?,WsaRecord=?,MapTime_1=?,MapTime_2=?,MapTime_3=?,MapTime_4=?,MapTime_5=?,MapTime_6=?,MapTime_7=? WHERE objid=?"
            );
            var4.setInt(++var5, var1.ev());
            var4.setInt(++var5, var1.bz());
            var4.setInt(++var5, var1.m());
            var4.setInt(++var5, var1.bd());
            var4.setInt(++var5, Math.max(1, var1.ea()));
            var4.setInt(++var5, var1.be());
            var4.setInt(++var5, var1.eb());
            var4.setInt(++var5, var1.ey());
            var4.setInt(++var5, var1.bf());
            var4.setInt(++var5, var1.bg());
            var4.setInt(++var5, var1.bh());
            var4.setInt(++var5, var1.bi());
            var4.setInt(++var5, var1.bj());
            var4.setInt(++var5, var1.bk());
            var4.setInt(++var5, var1.k());
            var4.setInt(++var5, var1.aB());
            var4.setInt(++var5, var1.aJ());
            var4.setInt(++var5, var1.ay());
            var4.setInt(++var5, var1.fb());
            var4.setInt(++var5, var1.fs());
            var4.setInt(++var5, var1.ft());
            var4.setInt(++var5, var1.fp());
            var4.setInt(++var5, var1.fj());
            var4.setInt(++var5, var1.fa());
            var4.setString(++var5, var1.eZ());
            var4.setInt(++var5, var1.cE());
            var4.setInt(++var5, var1.aF());
            var4.setString(++var5, var1.aG());
            var4.setInt(++var5, var1.aH());
            var4.setInt(++var5, var1.bA());
            var4.setInt(++var5, var1.bB());
            var4.setInt(++var5, var1.bC());
            var4.setInt(++var5, var1.aD());
            var4.setInt(++var5, var1.aE());
            var4.setInt(++var5, var1.ca());
            var4.setInt(++var5, var1.bD());
            var4.setInt(++var5, var1.az());
            var4.setInt(++var5, var1.bE());
            var4.setInt(++var5, var1.bF());
            var4.setInt(++var5, var1.bG());
            var4.setInt(++var5, var1.bI());
            var4.setBoolean(++var5, var1.bJ());
            var4.setInt(++var5, var1.P());
            var4.setTimestamp(++var5, var1.bO());
            var4.setTimestamp(++var5, var1.bP());
            var4.setTimestamp(++var5, var1.cB());
            var4.setTimestamp(++var5, var1.bQ());
            var4.setTimestamp(++var5, var1.bR());
            var4.setInt(++var5, var1.cC());
            var4.setInt(++var5, var1.cJ());
            var4.setInt(++var5, var1.cP());
            var4.setInt(++var5, var1.cQ());
            var4.setInt(++var5, var1.cS());
            var4.setInt(++var5, var1.cT());
            var4.setInt(++var5, var1.cU());
            var4.setInt(++var5, var1.cV());
            var4.setInt(++var5, var1.cW());
            var4.setInt(++var5, var1.cX());
            var4.setInt(++var5, var1.cY());
            var4.setInt(++var5, var1.fr());
            var4.execute();
            var4.close();
         } catch (SQLException var10) {
            a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
         } finally {
            SQLUtil.a(var4);
            SQLUtil.a(var3);
         }
      }
   }

   public void a(String var1, String var2) throws Exception {
      Connection var3 = null;

      try {
         var3 = DatabaseFactory.a().b();
         int var4;
         try (PreparedStatement var5 = var3.prepareStatement("SELECT objid FROM characters WHERE account_name=? AND char_name=?")) {
            var5.setString(1, var1);
            var5.setString(2, var2);
            try (ResultSet var6 = var5.executeQuery()) {
               if (!var6.next()) {
                  return;
               }
               var4 = var6.getInt("objid");
            }
         }

         executeDeleteById(var3, "DELETE FROM character_buddys WHERE char_id=?", var4);
         try (PreparedStatement var7 = var3.prepareStatement("DELETE FROM character_buddys WHERE buddy_id=? OR buddy_name=?")) {
            var7.setInt(1, var4);
            var7.setString(2, var2);
            var7.executeUpdate();
         }
         executeDeleteById(var3, "DELETE FROM character_buff WHERE char_obj_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_config WHERE object_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_equip WHERE id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_gift WHERE objid=?", var4);
         executeDeleteById(var3, "DELETE FROM character_items WHERE char_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_quests WHERE char_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_quests_new WHERE objid=?", var4);
         executeDeleteById(var3, "DELETE FROM character_skills WHERE char_obj_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_teleport WHERE char_id=?", var4);
         executeDeleteById(var3, "DELETE FROM character_warehouse_only WHERE char_objid=?", var4);
         executeDeleteById(var3, "DELETE FROM clan_members WHERE char_id=?", var4);
         executeDeleteById(var3, "DELETE FROM mail WHERE inbox_id=?", var4);
         try (PreparedStatement var8 = var3.prepareStatement("DELETE FROM soul_tower WHERE name=?")) {
            var8.setString(1, var2);
            var8.executeUpdate();
         }
         executeDeleteById(var3, "DELETE FROM characters WHERE objid=?", var4);

         this.c.remove(var2);
         MailTable.a().removeInboxCache(var4);
         BuddyTable.a().removeDeletedCharacter(var4, var2);
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3);
      }
   }

   private static void executeDeleteById(Connection var0, String var1, int var2) throws SQLException {
      try (PreparedStatement var3 = var0.prepareStatement(var1)) {
         var3.setInt(1, var2);
         var3.executeUpdate();
      }
   }


   public L1PcInstance a(String var1) throws Exception {
      L1PcInstance var2 = null;
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM characters WHERE char_name=?");
         var4.setString(1, var1);
         var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = new L1PcInstance();
            var2.d(var5.getString("account_name"));
            var2.cF(var5.getInt("objid"));
            var2.e(var5.getString("char_name"));
            var2.a(var5.getTimestamp("birthday"));
            var2.ax(var5.getInt("HighLevel"));
            var2.k(var5.getInt("Exp"));
            var2.m(var5.getInt("MaxHp"));
            var2.bx(Math.max(1, var5.getInt("CurHp")));
            var2.X(false);
            var2.cq(0);
            var2.n(var5.getInt("MaxMp"));
            var2.by(var5.getInt("CurMp"));
            var2.o(var5.getInt("Str"));
            var2.p(var5.getInt("Con"));
            var2.q(var5.getInt("Dex"));
            var2.r(var5.getInt("Cha"));
            var2.s(var5.getInt("Intel"));
            var2.t(var5.getInt("Wis"));
            int var6 = var5.getInt("Class");
            var2.i(var6);
            var2.cw(var6);
            var2.aj(var5.getInt("Sex"));
            var2.ad(var5.getInt("Type"));
            int var7 = var5.getInt("Heading");
            var2.ct(var7 > 7 ? 0 : var7);
            var2.cG(var5.getInt("locX"));
            var2.cH(var5.getInt("locY"));
            var2.cE(var5.getInt("MapID"));
            var2.c_(var5.getInt("Food"));
            var2.cr(var5.getInt("Lawful"));
            var2.f(var5.getString("Title"));
            var2.aV(var5.getInt("MasterID"));
            var2.ah(var5.getInt("ClanID"));
            var2.c(var5.getString("Clanname"));
            var2.ai(var5.getInt("ClanRank"));
            var2.ay(var5.getInt("BonusStatus"));
            var2.az(var5.getInt("ElixirStatus"));
            var2.aA(var5.getInt("ElfAttr"));
            var2.af(var5.getInt("PKcount"));
            var2.ag(var5.getInt("PkCountForElf"));
            var2.aH(var5.getInt("ExpRes"));
            var2.aB(var5.getInt("PartnerID"));
            var2.ae(var5.getInt("AccessLevel"));
            var2.aC(var5.getInt("OnlineStatus"));
            var2.aD(var5.getInt("HomeTownID"));
            var2.aE(var5.getInt("Contribution"));
            var2.aF(var5.getInt("Pay"));
            var2.aG(var5.getInt("HellTime"));
            var2.i(var5.getBoolean("Banned"));
            var2.A(var5.getInt("Karma"));
            var2.b(var5.getTimestamp("LastPk"));
            var2.c(var5.getTimestamp("LastPkForElf"));
            var2.f(var5.getTimestamp("LogoutTime"));
            var2.d(var5.getTimestamp("DeleteTime"));
            var2.e(var5.getTimestamp("TamUseTime"));
            var2.ao(var5.getInt("OriginalStr"));
            var2.ap(var5.getInt("OriginalCon"));
            var2.aq(var5.getInt("OriginalDex"));
            var2.ar(var5.getInt("OriginalCha"));
            var2.as(var5.getInt("OriginalInt"));
            var2.at(var5.getInt("OriginalWis"));
            var2.K(var5.getInt("EinhasadsBless"));
            var2.aZ(var5.getInt("BookMarkSpace"));
            var2.ba(var5.getInt("CharStoreSpace"));
            var2.bc(var5.getInt("RuneOpenStatus"));
            var2.bd(var5.getInt("WsaRecord"));
            var2.be(var5.getInt("MapTime_1"));
            var2.bf(var5.getInt("MapTime_2"));
            var2.bg(var5.getInt("MapTime_3"));
            var2.bh(var5.getInt("MapTime_4"));
            var2.bi(var5.getInt("MapTime_5"));
            var2.bj(var5.getInt("MapTime_6"));
            var2.bk(var5.getInt("MapTime_7"));
            var5.close();
            var2.aa();
            var2.cu(0);
            var2.cv(0);
            var2.b(false);
            var2.bb().a();
         }
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }

   public L1PcInstance b(String var1) throws Exception {
      L1PcInstance var2 = null;

      try {
         var2 = this.a(var1);
         L1Map var3 = L1WorldMap.b().a(var2.fp());
         if (!var3.b(var2.fs(), var2.ft())) {
            var2.cG(33087);
            var2.cH(33396);
            var2.cE(4);
         }
      } catch (Exception var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }

      return var2;
   }

   public void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement(
            "UPDATE characters SET MapTime_1=21600,MapTime_2=7200,MapTime_3=21600,MapTime_4=21600,MapTime_5=7200,MapTime_6=14400,MapTime_7=14400"
         );
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public void a(int var1, long var2) {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("UPDATE characters SET TamUseTime=? WHERE objid=?");
         var5.setTimestamp(1, new Timestamp(System.currentTimeMillis() + var2));
         var5.setInt(2, var1);
         var5.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   public void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE characters SET TamUseTime=? WHERE objid=?");
         var3.setTimestamp(1, null);
         var3.setInt(2, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private void d() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("UPDATE characters SET OnlineStatus=0");
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public void c(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE characters SET OnlineStatus=? WHERE objid=?");
         var3.setInt(1, var1.bE());
         var3.setInt(2, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE characters SET PartnerID=0 WHERE objid=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void d(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement(
            "UPDATE characters SET OriginalStr= ?, OriginalCon= ?, OriginalDex= ?, OriginalCha= ?, OriginalInt= ?, OriginalWis= ? WHERE objid=?"
         );
         var3.setInt(1, var1.bf());
         var3.setInt(2, var1.bg());
         var3.setInt(3, var1.bh());
         var3.setInt(4, var1.bi());
         var3.setInt(5, var1.bj());
         var3.setInt(6, var1.bk());
         var3.setInt(7, var1.fr());
         var3.execute();
      } catch (Exception var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public boolean c(String var1) {
      boolean var2 = true;
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT account_name FROM characters WHERE char_name=?");
         var4.setString(1, var1);
         var5 = var4.executeQuery();
         var2 = var5.next();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }

   private void e() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM characters");
         var3 = var2.executeQuery();

         while (var3.next()) {
            CharacterTable.L1R_a var4 = new CharacterTable.L1R_a();
            var4.b = var3.getString("char_name");
            var4.a = var3.getInt("objid");
            this.c.put(var4.b, var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public CharacterTable.L1R_a[] c() {
      return this.c.values().toArray(new CharacterTable.L1R_a[this.c.size()]);
   }

   public class L1R_a {
      public int a;
      public String b;
   }
}
