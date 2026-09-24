#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SCANNER = ROOT / "NativeSendXrefScanner.cs"
CONTROL = ROOT / "NativeSendProbeControl.cs"

scanner = SCANNER.read_text(encoding="utf-8")
control = CONTROL.read_text(encoding="utf-8")

assert "ScanKnownRuntimeIat" in scanner
assert "runtime.ClientHashAuthoritative" in scanner
assert "TryReadBytes" in scanner
assert "KNOWN_RUNTIME_IAT" in scanner

assert "0x00EA5898U" in control
assert "LIVE_IAT_CANDIDATE" in control
assert "NativeCallGraphScanner.Build" in control
assert "WP7_NATIVE_USEITEM_PASS=NO" in control
assert "ITEM_SPECIFIC_ACTION_PROVEN=NO" in control
assert "LIVE_IAT_RESTART_STABLE=NO" in control

combined = scanner + "\n" + control
for forbidden in (
    "WriteProcessMemory(",
    "CreateRemoteThread(",
    "VirtualAllocEx(",
):
    assert forbidden not in combined, forbidden

print("PASS_WP7_LIVE_IAT_CONTRACT")
