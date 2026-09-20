#!/usr/bin/env python3
import csv
import json
import struct
import zipfile
from collections import defaultdict
from pathlib import Path

SRC = Path("l1jserver2.jar")
MAP = Path("recovery/core_class_map.csv")
OUT = Path("recovery/l1jserver2-source-normalized.jar")
NONAPP = Path("recovery/l1jserver2-nonapp-ref.jar")
CSV_OUT = Path("recovery/source_namespace_map.csv")
JSON_OUT = Path("recovery/source_namespace_state.json")
MD_OUT = Path("recovery/SOURCE_NAMESPACE_NORMALIZATION.md")

with MAP.open(encoding="utf-8-sig", newline="") as f:
    rows = list(csv.DictReader(f))

def internal_name(dotted):
    return dotted.replace(".", "/")

groups = defaultdict(list)
for r in rows:
    old = internal_name(r["ObfuscatedClass"])
    pkg = old.rsplit("/", 1)[0]
    stem = Path(r["SourceFile"]).stem
    groups[(pkg, stem)].append(old)

top_map = {}
for r in rows:
    old = internal_name(r["ObfuscatedClass"])
    pkg = old.rsplit("/", 1)[0]
    old_simple = old.rsplit("/", 1)[-1]
    stem = Path(r["SourceFile"]).stem
    new_simple = stem if len(groups[(pkg, stem)]) == 1 else f"{stem}__obf_{old_simple}"
    top_map[old] = f"l1r/{pkg}/{new_simple}"

with zipfile.ZipFile(SRC, "r") as zin:
    class_entries = [n[:-6] for n in zin.namelist() if n.endswith(".class")]

all_map = {}
top_sorted = sorted(top_map.items(), key=lambda kv: len(kv[0]), reverse=True)
for cls in class_entries:
    for old_top, new_top in top_sorted:
        if cls == old_top:
            all_map[cls] = new_top
            break
        if cls.startswith(old_top + "$"):
            all_map[cls] = new_top + cls[len(old_top):]
            break

def parse_cp(data):
    if data[:4] != b"\xca\xfe\xba\xbe":
        raise ValueError("not class")
    cp_count = struct.unpack_from(">H", data, 8)[0]
    p = 10
    entries = [None] * cp_count
    i = 1
    while i < cp_count:
        tag = data[p]
        start = p
        p += 1
        if tag == 1:
            n = struct.unpack_from(">H", data, p)[0]
            p += 2
            raw = data[p:p+n]
            p += n
            entries[i] = {"tag": tag, "text": raw.decode("utf-8", errors="replace")}
        elif tag in (3,4):
            p += 4
            entries[i] = {"tag": tag, "raw": data[start:p]}
        elif tag in (5,6):
            p += 8
            entries[i] = {"tag": tag, "raw": data[start:p]}
            i += 1
        elif tag in (7,8,16,19,20):
            ref = struct.unpack_from(">H", data, p)[0]
            p += 2
            entries[i] = {"tag": tag, "ref": ref, "raw": data[start:p]}
        elif tag in (9,10,11,12,17,18):
            a,b = struct.unpack_from(">HH", data, p)
            p += 4
            entries[i] = {"tag": tag, "a": a, "b": b, "raw": data[start:p]}
        elif tag == 15:
            p += 3
            entries[i] = {"tag": tag, "raw": data[start:p]}
        else:
            raise ValueError(f"unsupported cp tag {tag} at {i}")
        i += 1
    return entries, p

rename_pairs = sorted(all_map.items(), key=lambda kv: len(kv[0]), reverse=True)

def rewrite_text(text):
    out = text
    for old, new in rename_pairs:
        if old in out:
            out = out.replace(old, new)
    return out

def patch_class(data):
    entries, cp_end = parse_cp(data)
    string_utf8 = set()
    structural_utf8 = set()
    for e in entries:
        if not e:
            continue
        if e["tag"] == 8:
            string_utf8.add(e["ref"])
        elif e["tag"] in (7,16,19,20):
            structural_utf8.add(e["ref"])
        elif e["tag"] == 12:
            structural_utf8.add(e["b"])

    out = bytearray(data[:10])
    changed = 0
    skipped = 0
    i = 1
    while i < len(entries):
        e = entries[i]
        if e is None:
            i += 1
            continue
        if e["tag"] == 1:
            text = e["text"]
            has_hit = any(old in text for old,_ in rename_pairs)
            if has_hit and i in string_utf8 and i not in structural_utf8:
                new_text = text
                skipped += 1
            else:
                new_text = rewrite_text(text)
            raw = new_text.encode("utf-8")
            out.append(1)
            out += struct.pack(">H", len(raw))
            out += raw
            if new_text != text:
                changed += 1
        else:
            out += e["raw"]
        i += 2 if e["tag"] in (5,6) else 1
    out += data[cp_end:]
    return bytes(out), changed, skipped

zip_rows = []
changed_utf8 = 0
skipped_strings = 0
renamed_class_entries = 0

with zipfile.ZipFile(SRC, "r") as zin,      zipfile.ZipFile(OUT, "w", compression=zipfile.ZIP_DEFLATED) as zout,      zipfile.ZipFile(NONAPP, "w", compression=zipfile.ZIP_DEFLATED) as nonapp:
    for info in zin.infolist():
        original = zin.read(info.filename)
        data = original
        out_name = info.filename
        is_app_class = False
        if info.filename.endswith(".class"):
            old_internal = info.filename[:-6]
            if old_internal in all_map:
                is_app_class = True
                out_name = all_map[old_internal] + ".class"
                data, chg, skip = patch_class(data)
                changed_utf8 += chg
                skipped_strings += skip
                renamed_class_entries += 1
                zip_rows.append({
                    "OldInternal": old_internal,
                    "NewInternal": all_map[old_internal],
                    "Kind": "INNER" if "$" in old_internal else "TOP_LEVEL",
                })
        zi = zipfile.ZipInfo(out_name, info.date_time)
        zi.compress_type = zipfile.ZIP_DEFLATED
        zi.external_attr = info.external_attr
        zout.writestr(zi, data)
        if not is_app_class:
            zi2 = zipfile.ZipInfo(info.filename, info.date_time)
            zi2.compress_type = zipfile.ZIP_DEFLATED
            zi2.external_attr = info.external_attr
            nonapp.writestr(zi2, original)

with CSV_OUT.open("w", encoding="utf-8", newline="") as f:
    fields = ["OldInternal","NewInternal","Kind"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(sorted(zip_rows, key=lambda r:r["OldInternal"]))

dups = [(pkg,stem,vals) for (pkg,stem),vals in groups.items() if len(vals)>1]
state = {
    "source": SRC.as_posix(),
    "normalized_jar": OUT.as_posix(),
    "nonapp_reference": NONAPP.as_posix(),
    "top_level_mappings": len(top_map),
    "application_class_mappings": len(all_map),
    "renamed_class_entries": renamed_class_entries,
    "utf8_entries_rewritten": changed_utf8,
    "string_constant_hits_preserved": skipped_strings,
    "duplicate_sourcefile_groups": len(dups),
    "logic_changed": False,
    "runtime_intended": False,
    "purpose": "SOURCE_RECOVERY_ONLY",
}
JSON_OUT.write_text(json.dumps(state, indent=2) + "\n", encoding="utf-8")

md = [
    "# L1JTW8.5 Source Namespace Normalization",
    "",
    "Recovery-only class/package normalization. Original donor JAR remains unchanged.",
    "",
    f"- Top-level mappings: **{len(top_map)}**",
    f"- Application class mappings incl. inner/anonymous: **{len(all_map)}**",
    f"- Renamed class entries: **{renamed_class_entries}**",
    f"- Rewritten structural UTF8 entries: **{changed_utf8}**",
    f"- Preserved string-constant hits: **{skipped_strings}**",
    f"- Duplicate SourceFile groups: **{len(dups)}**",
    "",
    "Rules:",
    "- Package prefix: l1r/ + original obfuscated package.",
    "- Top-level simple name: preserved SourceFile stem.",
    "- Duplicate SourceFile stem in one package: append __obf_<old-simple>.",
    "- Inner/anonymous suffixes remain donor suffixes.",
    "- String constants are preserved when constant-string-only.",
    "- This JAR is not a runtime replacement.",
    "",
    "Duplicate SourceFile groups:",
]
for pkg,stem,vals in sorted(dups):
    md.append(f"- {pkg}/{stem}: " + ", ".join(vals))
MD_OUT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
