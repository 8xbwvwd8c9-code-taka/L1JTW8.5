#!/usr/bin/env python3
from pathlib import Path

def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    if n not in s: raise AssertionError(f"{l}: missing {n!r}")
def before(s,a,b,l):
    pa=s.find(a); pb=s.find(b)
    if pa < 0 or pb < 0 or pa >= pb: raise AssertionError(f"{l}: order/missing")

br=t("recovery/normalized-src-vf/l1r/aj/C_BoardRead.java"); bro=t("recovered-src-obf/aj/n.java")
bp=t("recovery/normalized-src-vf/l1r/aj/C_BoardPage.java"); bpo=t("recovered-src-obf/aj/m.java")
bd=t("recovery/normalized-src-vf/l1r/aj/C_BoardDelete.java"); bdo=t("recovered-src-obf/aj/l.java")
pet=t("recovery/normalized-src-vf/l1r/aj/C_UsePetItem.java"); peto=t("recovered-src-obf/aj/cq.java")
loc=t("recovery/normalized-src-vf/l1r/aj/C_SendLocation.java"); loco=t("recovered-src-obf/aj/bz.java")
party=t("recovery/normalized-src-vf/l1r/aj/C_BanParty.java"); partyo=t("recovered-src-obf/aj/i.java")
head=t("recovery/normalized-src-vf/l1r/aj/C_ChangeHeading.java"); heado=t("recovered-src-obf/aj/r.java")
shop=t("recovery/normalized-src-vf/l1r/be/S_PrivateShop.java"); shopo=t("recovered-src-obf/be/db.java")
rank=t("recovery/normalized-src-vf/l1r/ao/RankingTable.java"); ranko=t("recovered-src-obf/ao/ba.java")
inv=t("recovery/normalized-src-vf/l1r/au/L1PcInventory.java"); invo=t("recovered-src-obf/au/g.java")
sw=t("recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java"); swo=t("recovered-src-obf/aj/cd.java")
book=t("recovery/normalized-src-vf/l1r/bh/L1BookMark.java"); booko=t("recovered-src-obf/bh/c.java")
spawn=t("recovery/normalized-src-vf/l1r/ao/SpawnTable.java"); spawno=t("recovered-src-obf/ao/bg.java")
nspawn=t("recovery/normalized-src-vf/l1r/ao/NpcSpawnTable.java"); nspawno=t("recovered-src-obf/ao/at.java")

for s,typ,call,l in [
    (br,"L1BoardInstance","var7.b(var6, var4)","182N"),(bro,"c","board.b(pc, topicNumber)","182O"),
    (bp,"L1BoardInstance","var7.a(var3, var5)","183N"),(bpo,"c","board.a(pc, topicNumber)","183O")]:
    req(s,"instanceof "+typ,l+"_TYPE"); req(s,"> 3",l+"_RANGE"); before(s,"instanceof "+typ,call,l+"_ORDER")
req(bd,"instanceof L1BoardInstance","184N_TYPE"); req(bd,"var6.f(var5) > 3","184N_RANGE"); before(bd,"instanceof L1BoardInstance","var7.f()", "184N_ORDER")
req(bdo,"instanceof c","184O_TYPE"); req(bdo,"pc.f(obj) > 3","184O_RANGE"); before(bdo,"instanceof c","topic.f()", "184O_ORDER")

for s,lo,hi,pt,pi,l in [
    (pet,"var6 < 0","var6 >= var7.y().d().size()","var9 == null","var11 == null","201N"),
    (peto,"listNo < 0","listNo >= pet.y().d().size()","petType == null","petItem == null","201O")]:
    for n in (lo,hi,pt,pi): req(s,n,l)

req(loc,"var24 < 0 || var24 >= var48.length","217N"); req(loco,"point < 0 || point >= loc.length","217O")
req(party,"!var3.q() || var3.aL() == null","223N"); req(partyo,"!player.q() || player.aL() == null","223O")
req(head,"var4 < 0 || var4 >= 8","229N"); req(heado,"heading < 0 || heading >= 8","229O")
req(shop,"if (!(var4Object instanceof L1PcInstance))","231N"); req(shopo,"if (!(shopObject instanceof u))","231O")
req(rank,"else if (var6.e == 7) {\n               this.u.add(var6);","247N")
req(ranko,"else if (data.e == 7) {\n                            this.u.add(data);","247O")
req(rank,"while (var1.size() > 50)","248N"); req(ranko,"while (list.size() > 50)","248O")
req(inv,"Math.max(0, var2.B()[var4] - var1.E())","278N"); req(invo,"Math.max(0, qn.B()[i2] - item.E())","278O")
req(sw,"if (!(var9Object instanceof L1NpcInstance))","279N"); req(swo,"if (!(npcObject instanceof t))","279O")
req(book,"if (var2 < var0.cI())","288N"); req(booko,"if (size >= pc.cI())","288O")

for s,l in [(spawn,"268_SN"),(spawno,"268_SO"),(nspawn,"268_NN"),(nspawno,"268_NO")]:
    req(s,"RETURN_GENERATED_KEYS",l+"_KEYS")
    req(s,"getGeneratedKeys()",l+"_READ")
req(spawn,"var12.c.put(var10, var11)","268_SN_LIVE")
req(spawno,"table.c.put(id, spawn)","268_SO_LIVE")
req(nspawn,"this.c.put(var8, var9)","268_NN_LIVE")
req(nspawno,"this.c.put(id, spawn)","268_NO_LIVE")

print("L3_BATCH6_CONTRACT=PASS")
print("BUGS=850-182,850-183,850-184,850-201,850-217,850-223,850-229,850-231,850-247,850-248,850-268,850-278,850-279,850-288")
