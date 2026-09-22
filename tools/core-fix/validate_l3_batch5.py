#!/usr/bin/env python3
from pathlib import Path

def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    p=s.find(n)
    if p<0: raise AssertionError(f"{l}: missing {n!r}")
    return p
def before(s,a,b,l):
    if req(s,a,l+"_A") >= req(s,b,l+"_B"): raise AssertionError(f"{l}: order")

us=t("recovery/normalized-src-vf/l1r/aj/C_UseSkill.java"); uo=t("recovered-src-obf/aj/cr.java")
at=t("recovery/normalized-src-vf/l1r/aj/C_Attr.java"); ato=t("recovered-src-obf/aj/f.java")
amount=t("recovery/normalized-src-vf/l1r/aj/C_Amount.java"); amounto=t("recovered-src-obf/aj/c.java")
auction=t("recovery/normalized-src-vf/l1r/be/S_AuctionBoardRead.java"); auctiono=t("recovered-src-obf/be/j.java")
board=t("recovery/normalized-src-vf/l1r/aj/C_BoardWrite.java"); boardo=t("recovered-src-obf/aj/o.java")
clan=t("recovery/normalized-src-vf/l1r/ao/ClanTable.java"); clano=t("recovered-src-obf/ao/q.java")
book=t("recovery/normalized-src-vf/l1r/bh/L1BookMark.java"); booko=t("recovered-src-obf/bh/c.java")

before(us,"if (var17 == null)","var17.a(var3","BUG094_N")
before(uo,"if (executor == null)","executor.a(pc","BUG094_O")

req(clan,'DELETE FROM clan_warehouse_history WHERE clan_id=?',"BUG096_N_HISTORY")
req(clano,'DELETE FROM clan_warehouse_history WHERE clan_id=?',"BUG096_O_HISTORY")
before(clan,"var3.setAutoCommit(false)","var3.commit()","BUG096_N_TX")
before(clano,"con.setAutoCommit(false)","con.commit()","BUG096_O_TX")
req(clan,"var3.rollback()","BUG096_N_ROLLBACK")
req(clano,"con.rollback()","BUG096_O_ROLLBACK")

case630 = req(at,"case 630:","BUG108_N_CASE")
guard630 = at.find("if (var16 == null)", case630)
use630 = at.find("if (var6 == 0)", case630)
if guard630 < 0 or use630 < 0 or guard630 >= use630:
    raise AssertionError("BUG108_N: null guard not before case-630 use")
case630o = req(ato,"case 630:","BUG108_O_CASE")
guard630o = ato.find("if (fightPc == null)", case630o)
use630o = ato.find("if (c2 == 0)", case630o)
if guard630o < 0 or use630o < 0 or guard630o >= use630o:
    raise AssertionError("BUG108_O: null guard not before case-630 use")
before(at,"if (var55 == null)","var55.a(var27)","BUG111_N")
before(ato,"if (house == null)","house.a(name)","BUG111_O")
before(at,"if (!(var14Object instanceof L1PetInstance))","L1PetInstance var14 =","BUG115_N")
before(ato,"if (!(petObject instanceof ap.v))","ap.v pet =","BUG115_O")

before(book,'var0.a(new S_ServerMessage(327));\n               return;',"L1BookMark var3 =","BUG120_N")
before(booko,'pc.a(new ds(327));\n                return;',"bookmark = new c();","BUG120_O")
req(book,'var3.setAutoCommit(false)',"BUG123_N_TX")
req(book,'var5.addBatch()',"BUG123_N_BATCH")
req(book,'var5.executeBatch()',"BUG123_N_EXEC")
before(book,"var3.commit()","var0.ba().remove(var2)","BUG123_N_COMMIT_RAM")
req(book,"var3.rollback()","BUG123_N_ROLLBACK")
req(booko,'con.setAutoCommit(false)',"BUG123_O_TX")
if 'update.addBatch()' not in booko and 'UPDATE character_teleport SET order_id=order_id-1 WHERE char_id=? AND order_id>?' not in booko:
    raise AssertionError("BUG123_O_BATCH: neither batch reorder nor transactional SQL decrement found")
before(booko,"con.commit()","pc.ba().remove(book)","BUG123_O_COMMIT_RAM")

before(amount,"if (var8Object instanceof L1NpcInstance)","L1NpcInstance var8 =","BUG138_N")
before(amounto,"if (!(object instanceof t))","t npc = (t)object","BUG138_O")

before(auction,"if (var4 == null || var4.j() == null)","this.a(var4.c())","BUG148_N")
before(auctiono,"if (house == null || house.j() == null)","this.a(house.c())","BUG148_O")

before(board,"var4.length() > 16 || var5.length() > 1000","L1BoardTopic.a","BUG153_N")
before(boardo,"title.length() > 16 || content.length() > 1000","b.a(pc.et()","BUG153_O")
print("L3_BATCH5_CONTRACT=PASS")
print("BUGS=850-094,850-096,850-108,850-111,850-115,850-120,850-123,850-138,850-148,850-153")
