#!/usr/bin/env python3
from pathlib import Path

ROOT = Path(__file__).resolve().parents[3]
SCANNER = ROOT / "NativeSendXrefScanner.cs"
CONTROL = ROOT / "NativeSendProbeControl.cs"
PROBE = ROOT / "tools" / "run_850_wp7_live_send_iat_v20.ps1"
COMPARE = ROOT / "tools" / "compare_850_wp7_live_send_iat_v20.ps1"

scanner = SCANNER.read_text(encoding="utf-8")
control = CONTROL.read_text(encoding="utf-8")
probe = PROBE.read_text(encoding="utf-8")
compare = COMPARE.read_text(encoding="utf-8")

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

assert "0x00EA5898" in probe
assert "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4" in probe
assert "ReadProcessMemory" in probe
assert "SEND_TARGET_EXPORT_MATCH" in probe
assert "SCAN_SCOPE=LIN.BIN2_EXECUTABLE_MEM_IMAGE_ONLY" in probe
assert "CALLER_DEPTH=1" in probe
assert "WP7_NATIVE_USEITEM_PASS=NO" in probe

assert "DISTINCT_PROCESS_INSTANCE" in compare
assert "SEND_IAT_RVA_STABLE" in compare
assert "STABLE_EXACT_TARGET" in compare
assert "PASS_RESTART_STABLE_LIVE_SEND_IAT" in compare
assert "WP7_NATIVE_USEITEM_PASS=NO" in compare

combined = scanner + "\n" + control + "\n" + probe + "\n" + compare
for forbidden in (
    "WriteProcessMemory(",
    "CreateRemoteThread(",
    "VirtualAllocEx(",
):
    assert forbidden not in combined, forbidden

print("PASS_WP7_LIVE_IAT_CONTRACT")
