#!/usr/bin/env python3
import importlib.util
from pathlib import Path

HERE = Path(__file__).resolve().parent
TARGET = HERE.parent / "decode_850_useitem_send_callers_v18.py"

spec = importlib.util.spec_from_file_location("v18", TARGET)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)

sample = r"""
[FILE]
NAME=Lineage.exe
PATH=I:\8.50c客服端\Lineage.exe
IMAGE_BASE=0x00400000
CORE_XREF NAME=send IAT_RVA=0x00100000 CALL_RVA=0x00012345 KIND=CALL [IAT]
CORE_XREF NAME=recv IAT_RVA=0x00100004 CALL_RVA=0x00012399 KIND=CALL [IAT]

[FILE]
NAME=chigamec.dll
PATH=I:\8.50c客服端\chigamec.dll
IMAGE_BASE=0x10000000
CORE_XREF NAME=sendto IAT_RVA=0x00022000 CALL_RVA=0x00004567 KIND=CALL [IAT]
"""

blocks = mod.parse_evidence(sample)
assert len(blocks) == 2
assert blocks[0].name == "Lineage.exe"
assert blocks[0].xrefs[0].name == "send"
assert blocks[0].xrefs[0].call_rva == 0x12345
assert blocks[1].xrefs[0].name == "sendto"

sections = [
    mod.Section(".text", 0x1000, 0x600, 0x400, 0x600, 0x60000020)
]
assert mod.rva_to_offset(0x1234, sections, 0x2000) == 0x634
assert mod.rva_to_offset(0x9000, sections, 0x2000) is None

print("PASS_V18_CORE_TESTS")
