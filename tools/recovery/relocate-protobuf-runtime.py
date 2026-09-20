#!/usr/bin/env python3
import json
import struct
import zipfile
from pathlib import Path

SRC = Path("recovery/compile-ref-protobuf-obf.jar")
OUT = Path("recovery/compile-ref-protobuf-l1rpb.jar")
STATE = Path("recovery/compile_ref_protobuf_l1rpb.json")
REPORT = Path("recovery/COMPILE_REF_PROTOBUF_L1RPB.md")

OLD = b"a/"
NEW = b"l1rpb/"

def rewrite_utf8(raw: bytes) -> bytes:
    out = raw
    if out.startswith(OLD):
        out = NEW + out[len(OLD):]
    out = out.replace(b"La/", b"Ll1rpb/")
    return out

def rewrite_class(data: bytes):
    if len(data) < 10 or data[:4] != b"\xca\xfe\xba\xbe":
        return data, 0
    pos = 8
    cp_count = struct.unpack_from(">H", data, pos)[0]
    pos += 2
    out = bytearray(data[:10])
    changed = 0
    i = 1
    while i < cp_count:
        tag = data[pos]
        out.append(tag)
        pos += 1
        if tag == 1:
            n = struct.unpack_from(">H", data, pos)[0]
            pos += 2
            raw = data[pos:pos+n]
            pos += n
            new = rewrite_utf8(raw)
            if new != raw:
                changed += 1
            out += struct.pack(">H", len(new))
            out += new
        elif tag in (3, 4):
            out += data[pos:pos+4]; pos += 4
        elif tag in (5, 6):
            out += data[pos:pos+8]; pos += 8
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            out += data[pos:pos+2]; pos += 2
        elif tag in (9, 10, 11, 12, 17, 18):
            out += data[pos:pos+4]; pos += 4
        elif tag == 15:
            out += data[pos:pos+3]; pos += 3
        else:
            raise ValueError(f"unknown constant-pool tag {tag} at cp#{i}")
        i += 1
    out += data[pos:]
    return bytes(out), changed

if not SRC.exists():
    raise SystemExit(f"missing input: {SRC}")

classes = 0
changed_classes = 0
changed_utf8 = 0
with zipfile.ZipFile(SRC, "r") as zin, zipfile.ZipFile(OUT, "w", zipfile.ZIP_DEFLATED) as zout:
    for info in zin.infolist():
        raw = zin.read(info.filename)
        name = info.filename
        if name.endswith(".class"):
            classes += 1
            raw, n = rewrite_class(raw)
            if n:
                changed_classes += 1
                changed_utf8 += n
            if name.startswith("a/"):
                name = "l1rpb/" + name[2:]
        zout.writestr(name, raw)

state = {
    "source": SRC.as_posix(),
    "output": OUT.as_posix(),
    "old_runtime_package": "a/**",
    "new_runtime_package": "l1rpb/**",
    "class_files": classes,
    "changed_classes": changed_classes,
    "changed_constant_pool_utf8_entries": changed_utf8,
    "purpose": "RECOVERY_SOURCE_REPRESENTATION_ONLY",
    "gameplay_logic_changed": False,
    "final_runtime_artifact": False,
}
STATE.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")
REPORT.write_text(
    "# Relocated Protobuf Runtime Compile Reference\n\n"
    "Recovery-only source representation transform. The embedded protobuf runtime package is relocated from `a/**` to `l1rpb/**` so Java source can reference it without colliding with obfuscated/generated type names.\n\n"
    f"- Class files: **{classes}**\n"
    f"- Classes with rewritten constant-pool UTF8 entries: **{changed_classes}**\n"
    f"- Rewritten UTF8 entries: **{changed_utf8}**\n"
    "- Gameplay logic changed: **NO**\n"
    "- Final runtime artifact: **NO**\n"
    "- Final validation must normalize this namespace back to donor `a/**`.\n",
    encoding="utf-8",
)
print(json.dumps(state, indent=2))
