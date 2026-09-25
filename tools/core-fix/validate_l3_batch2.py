#!/usr/bin/env python3
from pathlib import Path

def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    p=s.find(n)
    if p<0: raise AssertionError(f"{l}: missing {n!r}")
    return p
def before(s,a,b,l):
    if req(s,a,l+"_A") >= req(s,b,l+"_B"): raise AssertionError(f"{l}: order")

shop=t("recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java")
shop_o=t("recovered-src-obf/aj/cd.java")
inv=t("recovery/normalized-src-vf/l1r/au/L1PcInventory.java")
inv_o=t("recovered-src-obf/au/g.java")
pb=t("recovery/normalized-src-vf/l1r/aj/C_ProtoBuffers.java")
pb_o=t("recovered-src-obf/aj/bs.java")
ps=t("recovery/normalized-src-vf/l1r/be/S_PrivateShop.java")
ps_o=t("recovered-src-obf/be/db.java")

before(shop, "if (var30 == null)", "L1ItemInstance var33 = var30.a", "BUG016_N")
before(shop_o, "if (shopData == null)", "q item = shopData.a", "BUG016_O")
before(inv, "if (this.a.isEmpty())", "Random.a(this.a.size())", "BUG017_N")
before(inv_o, "if (this.a.isEmpty())", "bi.i.a(this.a.size())", "BUG017_O")

# Optional reward index must be rejected before any mandatory reward is granted.
before(pb, "if (var91.q()) {\n                  int var147 = var91.r();", "for (int var146 = 0; var146 < var132.f().length", "BUG021_N")
req(pb, "var147 >= var132.i().length", "BUG021_N_ID")
req(pb, "var147 >= var132.j().length", "BUG021_N_COUNT")
req(pb, "var147 >= var132.k().length", "BUG021_N_ENCHANT")
before(pb_o, "if (msg.q()) {\n                            int idx = msg.r();", "while (i2 < qn.f().length)", "BUG021_O")

before(pb, "if (var129 == null || var4.dR() == null || var144 <= 0 || var144 > var4.dR().length)", "for (g var143 : var129.o())", "BUG022_N")
before(pb_o, "if (msg15 == null || pc.dR() == null || achievementIDX <= 0 || achievementIDX > pc.dR().length)", "for (g bs2 : msg15.o())", "BUG022_O")
req(pb, "long weeklyIndex = (long)var106 * 3L;", "BUG023_N_INDEX")
req(pb, "weeklyIndex >= weeklyData.length", "BUG023_N_BOUND")
req(pb, "weeklyData[(int)weeklyIndex][3] = 5;", "BUG023_N_WRITE")
req(pb_o, "long weeklyIndex = (long)line * 3L;", "BUG023_O_INDEX")
req(pb_o, "weeklyIndex >= weeklyData.length", "BUG023_O_BOUND")

before(pb, "if (var98 == null)", "L1ItemInstance var119 = var98.f()", "BUG024_N")
before(pb_o, "if (craft == null)", "q addchanceitem = craft.f()", "BUG024_O")
before(ps, "if (var25 == null)", "if (var25.N() == var26.N()", "BUG025_N")
before(ps_o, "if (item == null)", "if (item.N() != pcItem.N()", "BUG025_O")

print("L3_BATCH2_CONTRACT=PASS")
print("BUGS=850-016,850-017,850-021,850-022,850-023,850-024,850-025")
