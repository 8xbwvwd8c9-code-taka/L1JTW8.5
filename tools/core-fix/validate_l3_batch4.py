#!/usr/bin/env python3
from pathlib import Path

def t(p): return Path(p).read_text(encoding="utf-8")
def req(s,n,l):
    p=s.find(n)
    if p<0: raise AssertionError(f"{l}: missing {n!r}")
    return p
def before(s,a,b,l):
    if req(s,a,l+"_A") >= req(s,b,l+"_B"): raise AssertionError(f"{l}: order")

ct=t("recovery/normalized-src-vf/l1r/ao/CharacterTable.java")
cto=t("recovered-src-obf/ao/o.java")
mail=t("recovery/normalized-src-vf/l1r/aj/C_Mail.java")
mailo=t("recovered-src-obf/aj/bg.java")
mt=t("recovery/normalized-src-vf/l1r/ao/MailTable.java")
mto=t("recovered-src-obf/ao/an.java")
buddy=t("recovery/normalized-src-vf/l1r/aj/C_AddBuddy.java")
buddyo=t("recovered-src-obf/aj/b.java")
bt=t("recovery/normalized-src-vf/l1r/ao/BuddyTable.java")
bto=t("recovered-src-obf/ao/f.java")

# 028: every delete statement is try-with-resources or helper-scoped.
req(ct,"private static void executeDeleteById","BUG028_N_HELPER")
req(cto,"private static void executeDeleteById","BUG028_O_HELPER")
req(ct,"try (PreparedStatement var5 =", "BUG028_N_CHECK")
req(cto,"try (PreparedStatement check =", "BUG028_O_CHECK")

# 066/067: delete mail + reverse buddies using authoritative objid, then clear caches.
req(ct,'DELETE FROM mail WHERE inbox_id=?',"BUG066_N_DB")
req(cto,'DELETE FROM mail WHERE inbox_id=?',"BUG066_O_DB")
req(ct,'DELETE FROM character_buddys WHERE buddy_id=? OR buddy_name=?',"BUG067_N_DB")
req(cto,'DELETE FROM character_buddys WHERE buddy_id=? OR buddy_name=?',"BUG067_O_DB")
req(ct,"MailTable.a().removeInboxCache(var4);","BUG066_N_CACHE")
req(cto,"an.a().removeInboxCache(objid);","BUG066_O_CACHE")
req(ct,"BuddyTable.a().removeDeletedCharacter(var4, var2);","BUG067_N_CACHE")
req(cto,"f.a().removeDeletedCharacter(objid, charName);","BUG067_O_CACHE")

# 069/076/077: null first; persistence before live mutation.
before(mail,"if (var24 == null)","if (var24.f() == 0)","BUG069_N_READ")
before(mailo,"if (mail == null)","if (mail.f() == 0)","BUG069_O_READ")
before(mail,"if (!MailTable.a().d(var19))","var24.c(1)","BUG076_N")
before(mailo,"if (!an.a().d(mailId))","mail.c(1)","BUG076_O")
before(mail,"if (!MailTable.a().b(var16, S_Mail.c))","var21.b(S_Mail.c)","BUG077_N")
before(mailo,"if (!an.a().b(mailId, bw.c))","mail.b(bw.c)","BUG077_O")
req(mt,"public boolean d(int var1)","BUG076_N_HELPER")
req(mt,"public boolean b(int var1, int var2)","BUG077_N_HELPER")
req(mto,"public boolean d(int mailId)","BUG076_O_HELPER")
req(mto,"public boolean b(int mailId, int type)","BUG077_O_HELPER")

# 070: two-byte sentinel probe must prove i+1 in range; copy lengths are clamped.
req(mt,"var8 + 1 < var4.length","BUG070_N_LOOP")
req(mt,"if (var21 + var9 > var4.length)","BUG070_N_CLAMP")
req(mto,"i2 + 1 < text.length","BUG070_O_LOOP")
req(mto,"if (subjectLength + contentLength > text.length)","BUG070_O_CLAMP")

# 071: fee checks occur after recipient/mailbox or clan validation.
before(mail,"if (this.a(var7, S_Mail.a) >= 40)","if (!var3.j().b(40308, 50))","BUG071_N_ONLINE")
offline = req(mail,"if (this.a(var27, S_Mail.a) >= 40)","BUG071_N_OFFLINE_A")
offline_fee = mail.find("if (!var3.j().b(40308, 50))", offline)
if offline_fee < 0 or offline_fee <= offline:
    raise AssertionError("BUG071_N_OFFLINE: fee not after offline validation")
before(mail,"if (var25 == null)","if (!var3.j().b(40308, 1000))","BUG071_N_CLAN")
before(mailo,"if (this.a(receiver, bw.a) >= 40)","if (!pc.j().b(40308, 50))","BUG071_O_ONLINE")
offline_o = req(mailo,"if (this.a(restorePc, bw.a) >= 40)","BUG071_O_OFFLINE_A")
offline_fee_o = mailo.find("if (!pc.j().b(40308, 50))", offline_o)
if offline_fee_o < 0 or offline_fee_o <= offline_o:
    raise AssertionError("BUG071_O_OFFLINE: fee not after offline validation")
before(mailo,"if (clan == null)","if (!pc.j().b(40308, 1000))","BUG071_O_CLAN")

# 073: DB insert succeeds before live buddy mutation.
before(buddy,"BuddyTable.a().b(var3.fr(), var6.a, var6.b)","var4.a(var6.a, var6.b)","BUG073_N")
before(buddyo,"f.a().b(pc.fr(), cn2.a, cn2.b)","buddyList.a(cn2.a, cn2.b)","BUG073_O")
req(bt,"public boolean b(int var1, int var2, String var3)","BUG073_N_HELPER")
req(bto,"public boolean b(int charId, int objId, String name)","BUG073_O_HELPER")

# 079: batch mail count bounded by remaining packet bytes before id loop.
before(mail,"var18 > (var1.length - 6) / 4","for (int var23 = 0; var23 < var18; var23++)","BUG079_N")
before(mailo,"count > (abyte0.length - 6) / 4","while (i2 < count)","BUG079_O")

print("L3_BATCH4_CONTRACT=PASS")
print("BUGS=850-028,850-066,850-067,850-069,850-070,850-071,850-073,850-076,850-077,850-079")
