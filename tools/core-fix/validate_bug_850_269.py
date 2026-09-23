from pathlib import Path

N = Path("recovery/normalized-src-vf/l1r/aq/L1SpawnBoss.java").read_text(encoding="utf-8")
O = Path("recovered-src-obf/aq/ah.java").read_text(encoding="utf-8")


def req(text, needle, label):
    if needle not in text:
        raise SystemExit(f"FAIL missing {label}: {needle}")


req(N, "var2 += (long)(var25 - var7) * 60L * 1000L;", "normalized same-hour minute conversion")
req(O, "delay += (long)(set_minute - now_minute) * 60L * 1000L;", "obfuscated same-hour minute conversion")

if "var2 += var25 - var7;" in N:
    raise SystemExit("FAIL normalized millisecond bug remains")
if "delay += (long)(set_minute - now_minute);" in O:
    raise SystemExit("FAIL obfuscated millisecond bug remains")

print("BUG_850_269_CONTRACT=PASS")
print("SAME_HOUR_MINUTE_TO_MS=PASS")
