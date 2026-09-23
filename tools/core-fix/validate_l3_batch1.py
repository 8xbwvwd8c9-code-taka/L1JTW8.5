#!/usr/bin/env python3
from pathlib import Path

VALIDATOR_VERSION = "1"

def text(path):
    return Path(path).read_text(encoding="utf-8")

def require(src, needle, label):
    pos = src.find(needle)
    if pos < 0:
        raise AssertionError(f"{label}: missing {needle!r}")
    return pos

def ordered(src, first, second, label):
    a = require(src, first, label + "_FIRST")
    b = require(src, second, label + "_SECOND")
    if a >= b:
        raise AssertionError(f"{label}: order changed")

gm = text("recovery/normalized-src-vf/l1r/ai/GMCommands.java")
gm_o = text("recovered-src-obf/ai/b.java")
allbuff = text("recovery/normalized-src-vf/l1r/al/L1AllBuff.java")
allbuff_o = text("recovered-src-obf/al/e.java")
client = text("recovery/normalized-src-vf/l1r/bj/ClientThread.java")
client_o = text("recovered-src-obf/bj/d.java")
login = text("recovery/normalized-src-vf/l1r/aj/C_Login.java")
login_o = text("recovered-src-obf/aj/be.java")
create = text("recovery/normalized-src-vf/l1r/aj/C_CreateChar.java")
create_o = text("recovered-src-obf/aj/z.java")
move = text("recovery/normalized-src-vf/l1r/aj/C_MoveChar.java")
move_o = text("recovered-src-obf/aj/bj.java")
craft = text("recovery/normalized-src-vf/l1r/ao/CraftListTable.java")
craft_o = text("recovered-src-obf/ao/s.java")
config = text("recovery/normalized-src-vf/l1r/l1j/server/Config.java")
config_table = text("recovery/normalized-src-vf/l1r/ao/ConfigTable.java")

# BUG-850-001 / 002: malformed or missing command arguments fail closed before nextToken().
require(gm, "if (!var3.hasMoreTokens())", "BUG001_NORM_ROOT")
require(gm, "if (var19.countTokens() < 2)", "BUG001_NORM_CA")
require(gm_o, "if (!token.hasMoreTokens())", "BUG001_OBF_ROOT")
require(gm_o, "if (st.countTokens() < 2)", "BUG001_OBF_CA")
require(allbuff, "if (!var4.hasMoreTokens())", "BUG002_NORM")
require(allbuff_o, "if (!st.hasMoreTokens())", "BUG002_OBF")

# BUG-850-003: either missing header byte terminates packet receive.
require(client, "if (var1 < 0 || var2 < 0)", "BUG003_NORM")
require(client_o, "if (hiByte < 0 || loByte < 0)", "BUG003_OBF")

# BUG-850-005: 128-byte password buffer is limited to 1..32 four-byte blocks.
require(login, "if (var4 < 1 || var4 > var5.length / 4)", "BUG005_NORM")
require(login_o, "if (pwLenth < 1 || pwLenth > pws.length / 4)", "BUG005_OBF")

# BUG-850-007 / 008: client-controlled indexes are checked before fixed-size arrays.
ordered(create, "if (var3.ay() < 0 || var3.ay() >= a.length)", "a[var3.ay()]", "BUG007_NORM")
ordered(create_o, "if (pc.ay() < 0 || pc.ay() >= a.length)", "a[pc.ay()]", "BUG007_OBF")
ordered(move, "if (var6 < 0 || var6 >= this.a.length)", "this.a[var6][0]", "BUG008_NORM")
ordered(move_o, "if (heading < 0 || heading >= this.a.length)", "this.a[heading][0]", "BUG008_OBF")

# Runtime source map for movement mode is DB-backed ConfigTable with default ClientLanguage=4.
require(config, 'm = Short.parseShort(var0.a("ClientLanguage", "4"));', "BUG008_CONFIG")
for table in ("_config", "_config_other", "_config_world"):
    require(config_table, f'this.a("{table}")', "BUG008_CONFIG_TABLE")

# BUG-850-009: first craft query resources close before handles are reused.
ordered(craft, "SQLUtil.a(var3, var2, var1);", 'var2 = var1.prepareStatement("SELECT * FROM craft_exchange")', "BUG009_NORM")
ordered(craft_o, "j.a(rs, pstm, con);", 'pstm = con.prepareStatement("SELECT * FROM craft_exchange")', "BUG009_OBF")
require(craft, 'prepareStatement("SELECT * FROM craft")', "BUG009_DB_CRAFT")
require(craft, 'prepareStatement("SELECT * FROM craft_exchange")', "BUG009_DB_EXCHANGE")

print(f"VALIDATOR_VERSION={VALIDATOR_VERSION}")
print("L3_BATCH1_CONTRACT=PASS")
print("BUGS=850-001,850-002,850-003,850-005,850-007,850-008,850-009")
print("BUG850008_CONFIG=ClientLanguage(default=4) via _config/_config_other/_config_world")
print("BUG850009_DB=craft,craft_exchange")
