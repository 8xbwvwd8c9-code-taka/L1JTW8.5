#!/usr/bin/env python3
import importlib.util
import sys
from pathlib import Path

HERE = Path(__file__).resolve().parent
TARGET = HERE.parent / "decode_850_local_handoff_callers_v19.py"

spec = importlib.util.spec_from_file_location("v19", TARGET)
mod = importlib.util.module_from_spec(spec)
sys.modules[spec.name] = mod
spec.loader.exec_module(mod)

sample = r"""
MODE=850_SESSION_WRAPPER_IMPORTS_V18
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=ABC
CLIENT_AUTHORITY=1

[LOCAL_CLIENT_IAT_XREFS]
DLL=SomeLocal.dll NAME=Foo IAT_RVA=0x00002000 CALL_RVA=0x00001020 KIND=CALL [IAT]
DLL=LIBEAY32.dll NAME=SSL_write IAT_RVA=0x00002010 CALL_RVA=0x00001040 KIND=CALL [IAT]
DLL=LIBEAY32.dll NAME=EVP_EncryptUpdate IAT_RVA=0x00002014 CALL_RVA=0x00001060 KIND=CALL [IAT]

LIBEAY32_IMPORTED=1
LIBEAY32_IAT_XREFS=2
STATUS=PASS_LIBEAY32_HANDOFF_CANDIDATE
"""

meta, xrefs = mod.parse_v18_local_evidence(sample)
assert meta["CLIENT_AUTHORITY"] == "1"
assert len(xrefs) == 3
assert xrefs[1].dll.lower() == "libeay32.dll"
assert xrefs[1].name == "SSL_write"
assert xrefs[1].call_rva == 0x1040

focus = mod.select_focus_xrefs(xrefs)
assert len(focus) == 2
assert all(x.dll.lower() == "libeay32.dll" for x in focus)

sections = [
    mod.Section(".text", 0x1000, 0x300, 0x200, 0x300, 0x60000020)
]
data = bytearray(0x800)
# Function owner prologue at RVA 0x1010 => raw 0x210
data[0x210:0x213] = b"\x55\x8B\xEC"
# IAT callsite at RVA 0x1040 => raw 0x240
data[0x240:0x246] = b"\xFF\x15\x10\x20\x40\x00"
start, boundary = mod.find_function_start(0x1040, data, sections)
assert start == 0x1010
assert boundary == "PROLOGUE_55_8B_EC"

# direct caller at RVA 0x1100 calling 0x1010
call_rva = 0x1100
rel = 0x1010 - (call_rva + 5)
raw = 0x200 + (call_rva - 0x1000)
data[raw] = 0xE8
data[raw+1:raw+5] = int(rel).to_bytes(4, "little", signed=True)
callers = mod.find_direct_callers(0x1010, data, sections)
assert callers == [0x1100]

score = mod.rank_candidate(
    dll="LIBEAY32.dll",
    name="SSL_write",
    boundary="PROLOGUE_55_8B_EC",
    imm_5e_hits=1,
    direct_caller_count=1,
)
assert score >= 10

print("PASS_V19_CORE_TESTS")
