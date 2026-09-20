package l1r.ai;

import l1r.aj.C_AddBookmark;
import l1r.aj.C_AddBuddy;
import l1r.aj.C_Amount;
import l1r.aj.C_Attack;
import l1r.aj.C_AttackContinue;
import l1r.aj.C_Attr;
import l1r.aj.C_AuthLogin;
import l1r.aj.C_BanClan;
import l1r.aj.C_BanParty;
import l1r.aj.C_Blink;
import l1r.aj.C_Board;
import l1r.aj.C_BoardDelete;
import l1r.aj.C_BoardPage;
import l1r.aj.C_BoardRead;
import l1r.aj.C_BoardWrite;
import l1r.aj.C_Buddy;
import l1r.aj.C_CallUser;
import l1r.aj.C_ChangeHeading;
import l1r.aj.C_CharReset;
import l1r.aj.C_CharcterConfig;
import l1r.aj.C_Chat;
import l1r.aj.C_ChatOnOff;
import l1r.aj.C_ChatParty;
import l1r.aj.C_ChatWhisper;
import l1r.aj.C_CheckPK;
import l1r.aj.C_CreateChar;
import l1r.aj.C_CreateClan;
import l1r.aj.C_CreateParty;
import l1r.aj.C_DelBuddy;
import l1r.aj.C_DeleteBookmark;
import l1r.aj.C_DeleteChar;
import l1r.aj.C_DeleteInventoryItem;
import l1r.aj.C_Deposit;
import l1r.aj.C_Disconnect;
import l1r.aj.C_Door;
import l1r.aj.C_Drawal;
import l1r.aj.C_DropItem;
import l1r.aj.C_EmblemDownload;
import l1r.aj.C_EmblemUpload;
import l1r.aj.C_EnterPortal;
import l1r.aj.C_Exclude;
import l1r.aj.C_ExitGhost;
import l1r.aj.C_ExtraCommand;
import l1r.aj.C_Fight;
import l1r.aj.C_FishClick;
import l1r.aj.C_FixWeapon;
import l1r.aj.C_FixWeaponList;
import l1r.aj.C_GMTeleport;
import l1r.aj.C_GiveItem;
import l1r.aj.C_GotoMap;
import l1r.aj.C_GotoPortal;
import l1r.aj.C_ItemUSe;
import l1r.aj.C_JoinClan;
import l1r.aj.C_KeepALIVE;
import l1r.aj.C_LeaveClan;
import l1r.aj.C_LeaveParty;
import l1r.aj.C_Login;
import l1r.aj.C_LoginToServer;
import l1r.aj.C_Mail;
import l1r.aj.C_MercenaryArrange;
import l1r.aj.C_MercenaryEmpoly;
import l1r.aj.C_MoveChar;
import l1r.aj.C_NpcAction;
import l1r.aj.C_NpcTalk;
import l1r.aj.C_Party;
import l1r.aj.C_PetMenu;
import l1r.aj.C_PickUpItem;
import l1r.aj.C_Pledge;
import l1r.aj.C_PledgeWatch;
import l1r.aj.C_Propose;
import l1r.aj.C_ProtoBuffers;
import l1r.aj.C_Rank;
import l1r.aj.C_ReadNews;
import l1r.aj.C_Restart;
import l1r.aj.C_RestartDead;
import l1r.aj.C_Result;
import l1r.aj.C_SelectTarget;
import l1r.aj.C_SendLocation;
import l1r.aj.C_ServerVersion;
import l1r.aj.C_Shop;
import l1r.aj.C_ShopList;
import l1r.aj.C_ShopWorld;
import l1r.aj.C_SkillBuy;
import l1r.aj.C_SkillBuyItem;
import l1r.aj.C_SkillBuyItemOK;
import l1r.aj.C_SkillBuyOK;
import l1r.aj.C_TaxRate;
import l1r.aj.C_Teleport;
import l1r.aj.C_TeleportUser;
import l1r.aj.C_Title;
import l1r.aj.C_Trade;
import l1r.aj.C_TradeAddItem;
import l1r.aj.C_TradeCancel;
import l1r.aj.C_TradeOK;
import l1r.aj.C_UsePetItem;
import l1r.aj.C_UseSkill;
import l1r.aj.C_War;
import l1r.aj.C_WarePassword;
import l1r.aj.C_Who;
import l1r.aj.ClientBasePacket;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class PacketHandler {
   private final ClientThread a;

   public PacketHandler(ClientThread var1) {
      this.a = var1;
   }

   public void a(byte[] var1) throws Exception {
      int var2 = var1[0] & 255;
      ClientBasePacket var3 = null;
      switch (var2) {
         case 2:
            var3 = new C_Trade(var1, this.a);
            break;
         case 4:
            var3 = new C_ExtraCommand(var1, this.a);
            break;
         case 5:
            var3 = new C_Result(var1, this.a);
            break;
         case 7:
            var3 = new C_BoardRead(var1, this.a);
            break;
         case 9:
            var3 = new C_PickUpItem(var1, this.a);
            break;
         case 11:
            var3 = new C_NpcTalk(var1, this.a);
            break;
         case 14:
            var3 = new C_Party(var1, this.a);
            break;
         case 15:
            var3 = new C_BoardDelete(var1, this.a);
            break;
         case 19:
            var3 = new C_ReadNews(var1, this.a);
            break;
         case 20:
            var3 = new C_FixWeapon(var1, this.a);
            break;
         case 22:
            var3 = new C_Rank(var1, this.a);
            break;
         case 23:
            var3 = new C_Disconnect(var1, this.a);
            break;
         case 25:
            var3 = new C_ProtoBuffers(var1, this.a);
            break;
         case 26:
            var3 = new C_LeaveClan(var1, this.a);
            break;
         case 28:
            var3 = new C_SelectTarget(var1, this.a);
            break;
         case 31:
            var3 = new C_AddBuddy(var1, this.a);
            break;
         case 32:
            var3 = new C_BoardWrite(var1, this.a);
            break;
         case 33:
            var3 = new C_PetMenu(var1, this.a);
            break;
         case 35:
            var3 = new C_Buddy(var1, this.a);
            break;
         case 38:
            var3 = new C_DeleteBookmark(var1, this.a);
            break;
         case 39:
            var3 = new C_ChangeHeading(var1, this.a);
            break;
         case 41:
            var3 = new C_GiveItem(var1, this.a);
            break;
         case 43:
            var3 = new C_Pledge(var1, this.a);
            break;
         case 44:
            var3 = new C_NpcAction(var1, this.a);
            break;
         case 45:
            var3 = new C_MoveChar(var1, this.a);
            break;
         case 49:
            var3 = new C_ChatWhisper(var1, this.a);
            break;
         case 52:
            var3 = new C_Board(var1, this.a);
            break;
         case 54:
            var3 = new C_FishClick(var1, this.a);
            break;
         case 56:
            var3 = new C_EnterPortal(var1, this.a);
            break;
         case 58:
            var3 = new C_Propose(var1, this.a);
            break;
         case 59:
            var3 = new C_RestartDead(var1, this.a);
            break;
         case 60:
            var3 = new C_Chat(var1, this.a);
            break;
         case 61:
            var3 = new C_UsePetItem(var1, this.a);
            break;
         case 69:
            var3 = new C_AuthLogin(var1, this.a);
            break;
         case 72:
            var3 = new C_ServerVersion(var1, this.a);
            break;
         case 73:
            var3 = new C_TradeOK(var1, this.a);
            break;
         case 74:
            var3 = new C_ChatParty(var1, this.a);
            break;
         case 77:
            var3 = new C_AddBookmark(var1, this.a);
            break;
         case 82:
            var3 = new C_SkillBuyOK(var1, this.a);
            break;
         case 83:
            var3 = new C_AttackContinue(var1, this.a);
            break;
         case 84:
            var3 = new C_Door(var1, this.a);
            break;
         case 88:
            var3 = new C_BanClan(var1, this.a);
            break;
         case 89:
            var3 = new C_TeleportUser(var1, this.a);
            break;
         case 90:
            var3 = new C_CreateChar(var1, this.a);
            break;
         case 94:
            var3 = new C_ItemUSe(var1, this.a);
            break;
         case 95:
            var3 = new C_Who(var1, this.a);
            break;
         case 100:
            var3 = new C_GotoPortal(var1, this.a);
            break;
         case 101:
            var3 = new C_PledgeWatch(var1, this.a);
            break;
         case 104:
            var3 = new C_Deposit(var1, this.a);
            break;
         case 106:
         case 111:
            var3 = new C_Attack(var1, this.a);
            break;
         case 113:
            var3 = new C_SendLocation(var1, this.a);
            break;
         case 115:
            var3 = new C_MercenaryEmpoly(var1, this.a);
            break;
         case 117:
            var3 = new C_Chat(var1, this.a);
            break;
         case 118:
            var3 = new C_Attr(var1, this.a);
            break;
         case 121:
            var3 = new C_Shop(var1, this.a);
            break;
         case 126:
            var3 = new C_SkillBuyItem(var1, this.a);
            break;
         case 127:
            var3 = new C_CreateParty(var1, this.a);
            break;
         case 128:
            var3 = new C_UseSkill(var1, this.a);
            break;
         case 129:
            var3 = new C_ChatOnOff(var1, this.a);
            break;
         case 132:
            var3 = new C_TaxRate(var1, this.a);
            break;
         case 135:
            var3 = new C_EmblemDownload(var1, this.a);
            break;
         case 141:
            var3 = new C_CreateClan(var1, this.a);
            break;
         case 143:
            var3 = new C_LeaveParty(var1, this.a);
            break;
         case 145:
            var3 = new C_DeleteInventoryItem(var1, this.a);
            break;
         case 148:
            var3 = new C_ExitGhost(var1, this.a);
            break;
         case 151:
            var3 = new C_EmblemUpload(var1, this.a);
            break;
         case 152:
            var3 = new C_ShopList(var1, this.a);
            break;
         case 153:
            var3 = new C_BoardPage(var1, this.a);
            break;
         case 158:
            var3 = new C_LoginToServer(var1, this.a);
            break;
         case 163:
            var3 = new C_DropItem(var1, this.a);
            break;
         case 167:
            var3 = new C_Amount(var1, this.a);
            break;
         case 169:
            var3 = new C_CallUser(var1, this.a);
            break;
         case 171:
            var3 = new C_KeepALIVE(var1, this.a);
            break;
         case 173:
            var3 = new C_CheckPK(var1, this.a);
            break;
         case 177:
            var3 = new C_Title(var1, this.a);
            break;
         case 178:
            var3 = new C_Exclude(var1, this.a);
            break;
         case 186:
            var3 = new C_ShopWorld(var1, this.a);
            break;
         case 188:
            var3 = new C_GMTeleport(var1, this.a);
            break;
         case 189:
            var3 = new C_DeleteChar(var1, this.a);
            break;
         case 191:
            var3 = new C_BanParty(var1, this.a);
            break;
         case 201:
            var3 = new C_GotoMap(var1, this.a);
            break;
         case 202:
            var3 = new C_War(var1, this.a);
            break;
         case 208:
            var3 = new C_Teleport(var1, this.a);
            break;
         case 209:
            var3 = new C_TradeAddItem(var1, this.a);
            break;
         case 215:
            var3 = new C_Restart(var1, this.a);
            break;
         case 217:
            var3 = new C_JoinClan(var1, this.a);
            break;
         case 220:
            var3 = new C_TradeCancel(var1, this.a);
            break;
         case 221:
            var3 = new C_Login(var1, this.a);
            break;
         case 222:
            var3 = new C_WarePassword(var1, this.a);
            break;
         case 223:
            var3 = new C_SkillBuy(var1, this.a);
            break;
         case 233:
            var3 = new C_Drawal(var1, this.a);
            break;
         case 238:
            var3 = new C_MercenaryArrange(var1, this.a);
            break;
         case 241:
            var3 = new C_Fight(var1, this.a);
            break;
         case 244:
            var3 = new C_FixWeaponList(var1, this.a);
            break;
         case 245:
            var3 = new C_SkillBuyItemOK(var1, this.a);
            break;
         case 247:
            var3 = new C_Mail(var1, this.a);
            break;
         case 249:
            var3 = new C_CharcterConfig(var1, this.a);
            break;
         case 251:
            var3 = new C_CharReset(var1, this.a);
            break;
         case 252:
            var3 = new C_DelBuddy(var1, this.a);
            break;
         case 254:
            var3 = new C_Blink(var1, this.a);
      }

      if (Config.d && var2 != 45) {
         String var4 = var3 == null ? "" : var3.getClass().getSimpleName();
         System.out.println("opcode: " + var2 + " [" + var4 + "]");
         System.out.println(LineageUtil.a(var1));
      }

      if (var3 != null) {
         var3.i();
      }
   }
}
