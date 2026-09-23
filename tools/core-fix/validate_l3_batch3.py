#!/usr/bin/env python3
from pathlib import Path
def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    p=s.find(n)
    if p<0: raise AssertionError(f"{l}: missing {n!r}")
    return p
def before(s,a,b,l):
    if req(s,a,l+"_A") >= req(s,b,l+"_B"): raise AssertionError(f"{l}: order")
u=t("recovery/normalized-src-vf/l1r/aj/C_UseSkill.java"); uo=t("recovered-src-obf/aj/cr.java")
sw=t("recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java"); swo=t("recovered-src-obf/aj/cd.java")
r=t("recovery/normalized-src-vf/l1r/aj/C_Result.java"); ro=t("recovered-src-obf/aj/bx.java")
sp=t("recovery/normalized-src-vf/l1r/be/S_ProtoBuffers.java"); spo=t("recovered-src-obf/be/dc.java")
before(u,"if (SkillsTable.a().a(var6) == null)","SkillsTable.a().a(var6).s()","BUG035_N")
before(uo,"if (be.a().a(skillId) == null)","be.a().a(skillId).s()","BUG035_O")
before(sw,"if (var22 == null)","var22.f(var21)","BUG037_N")
before(swo,"if (clan == null)","clan.f(announce)","BUG037_O")
before(r,"if (var60 < 0 || var60 >= var44.size())","var44.get(var60)","BUG038_N_SELL")
before(r,"if (var62 < 0 || var62 >= var41.size())","var41.get(var62)","BUG038_N_BUY")
before(ro,"if (order < 0 || order >= sellList.size())","sellList.get(order)","BUG038_O_SELL")
before(ro,"if (order < 0 || order >= buyList.size())","buyList.get(order)","BUG038_O_BUY")
req(sp,"for (int var9 : var2)","BUG051_N")
req(spo,"for (int i2 : equipList_2)","BUG051_O")
print("L3_BATCH3_CONTRACT=PASS")
print("BUGS=850-035,850-037,850-038,850-051")
