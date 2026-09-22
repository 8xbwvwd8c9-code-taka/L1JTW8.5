#!/usr/bin/env python3
from pathlib import Path

def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    if n not in s: raise AssertionError(f"{l}: missing {n!r}")
def before(s,a,b,l):
    pa=s.find(a); pb=s.find(b)
    if pa < 0 or pb < 0 or pa >= pb: raise AssertionError(f"{l}: order/missing")

pc=t("recovery/normalized-src-vf/l1r/ap/L1PcInstance.java"); pco=t("recovered-src-obf/ap/u.java")
poly=t("recovery/normalized-src-vf/l1r/aq/L1PolyMorph.java"); polyo=t("recovered-src-obf/aq/ae.java")
iu=t("recovery/normalized-src-vf/l1r/aj/C_ItemUSe.java"); iuo=t("recovered-src-obf/aj/az.java")
inv=t("recovery/normalized-src-vf/l1r/au/L1PcInventory.java"); invo=t("recovered-src-obf/au/g.java")
tp=t("recovery/normalized-src-vf/l1r/aq/L1Teleport.java"); tpo=t("recovered-src-obf/aq/am.java")
ex=t("recovery/normalized-src-vf/l1r/aj/C_ExitGhost.java"); exo=t("recovered-src-obf/aj/ap.java")

for s,l in [(pc,"271_PC_N"),(pco,"271_PC_O")]:
    req(s,"l1rActivePolyMorphRule",l+"_FIELD")
    req(s,"getActivePolyMorphRule()",l+"_GET")
    req(s,"setActivePolyMorphRule",l+"_SET")
for s,l in [(poly,"271_POLY_N"),(polyo,"271_POLY_O")]:
    req(s,"setActivePolyMorphRule",l+"_BIND")
    req(s,"getActivePolyMorphRule()",l+"_READ")
req(poly,"var4.j().l(var1)","271_POLY_N_RECHECK"); req(polyo,"pc.j().l(polyId)","271_POLY_O_RECHECK")
req(iu,"L1PolyMorph.b(var1, var3)","271_IU_N_ARMOR"); req(iu,"L1PolyMorph.a(var1, var4)","271_IU_N_WEAPON")
req(iuo,"ae.b(pc, type)","271_IU_O_ARMOR"); req(iuo,"ae.a(pc, weapon_type)","271_IU_O_WEAPON")
for n in ["L1PolyMorph.a(this.i,","L1PolyMorph.b(this.i,"]: req(inv,n,"271_INV_N")
for n in ["ae.a(this.i,","ae.b(this.i,"]: req(invo,n,"271_INV_O")

for s,l in [(pc,"285_PC_N"),(pco,"285_PC_O")]:
    for n in ["l1rGhostSaveLocX","l1rGhostSaveLocY","l1rGhostSaveMapId","l1rGhostSaveHeading","l1rGhostReturnPending","makeReadyEndGhost()","finishGhostReturn()"]:
        req(s,n,l)
before(pc,"if (var1 && !this.bi)","this.bi = var1","285_PC_N_SAVE")
before(pco,"if (isGhost && !this.bi)","this.bi = isGhost","285_PC_O_SAVE")
req(ex,"var3.makeReadyEndGhost();","285_EXIT_N"); req(exo,"pc.makeReadyEndGhost();","285_EXIT_O")
before(tp,"var0.finishGhostReturn();","if (!var0.bN() && !var0.aA())","285_TP_N_ORDER")
before(tpo,"pc.finishGhostReturn();","if (!pc.bN() && !pc.aA())","285_TP_O_ORDER")

print("L3_BATCH7_CONTRACT=PASS")
print("BUGS=850-271,850-285")
