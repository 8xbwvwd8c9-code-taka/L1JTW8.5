#!/usr/bin/env python3
import csv
import json
import re
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

def safe_inner_segment(seg):
    # Preserve anonymous/local numeric identity. Named obfuscated segments get
    # a recovery-only Java-safe prefix so they cannot shadow packages/types.
    if seg.isdigit():
        return seg
    cleaned = re.sub(r"[^A-Za-z0-9_$]", "_", seg)
    if not cleaned or cleaned[0].isdigit():
        cleaned = "_" + cleaned
    return "L1R_" + cleaned

def normalize_inner_suffix(suffix):
    # suffix format: $a$a, $1, $1$a ...
    if not suffix:
        return suffix
    parts = suffix.split("$")[1:]
    return "$" + "$".join(safe_inner_segment(p) for p in parts)

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
            suffix = cls[len(old_top):]
            all_map[cls] = new_top + normalize_inner_suffix(suffix)
            break

named_inner_map = {
    old: new for old, new in all_map.items()
    if "$" in old and not old.rsplit("$", 1)[-1].isdigit()
}

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

def cp_utf8(entries, idx):
    if not idx or idx >= len(entries):
        return None
    e = entries[idx]
    return e["text"] if e and e["tag"] == 1 else None

def cp_class_name(entries, idx):
    if not idx or idx >= len(entries):
        return None
    e = entries[idx]
    if not e or e["tag"] != 7:
        return None
    return cp_utf8(entries, e["ref"])

def u2(buf, off):
    return struct.unpack_from(">H", buf, off)[0]

def u4(buf, off):
    return struct.unpack_from(">I", buf, off)[0]

def skip_attrs(buf, p):
    count = u2(buf, p)
    p += 2
    for _ in range(count):
        length = u4(buf, p + 2)
        p += 6 + length
    return p

def class_attr_offsets(body):
    p = 0
    p += 6  # access_flags, this_class, super_class
    interfaces_count = u2(body, p)
    p += 2 + interfaces_count * 2

    fields_count = u2(body, p)
    p += 2
    for _ in range(fields_count):
        p += 6
        p = skip_attrs(body, p)

    methods_count = u2(body, p)
    p += 2
    for _ in range(methods_count):
        p += 6
        p = skip_attrs(body, p)

    attrs_count = u2(body, p)
    p += 2
    attrs = []
    for _ in range(attrs_count):
        name_idx = u2(body, p)
        length = u4(body, p + 2)
        info_off = p + 6
        attrs.append((name_idx, info_off, length))
        p = info_off + length
    return attrs

rename_pairs = sorted(all_map.items(), key=lambda kv: len(kv[0]), reverse=True)

def rewrite_text(text):
    # Rewrite only JVM type identities, never arbitrary substrings.
    # The previous raw .replace() could corrupt unrelated JDK names:
    # old "ax/c" matched inside "javax/crypto/Cipher".
    out = text
    for old, new in rename_pairs:
        if out == old:
            out = new
            continue
        # Descriptors / signatures encode reference types with leading L.
        out = out.replace("L" + old + ";", "L" + new + ";")
        out = out.replace("L" + old + "<", "L" + new + "<")
        out = out.replace("L" + old + ".", "L" + new + ".")
    return out

def desired_inner_simple(old_internal):
    new = all_map.get(old_internal)
    if not new or "$" not in new:
        return None
    seg = new.rsplit("$", 1)[-1]
    if seg.isdigit():
        return None
    return seg

def patch_class(data):
    entries, cp_end = parse_cp(data)
    body = bytearray(data[cp_end:])

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

    # Patch InnerClasses.inner_name_index. Append UTF8 entries so all original
    # constant-pool indexes remain stable.
    existing_utf8 = {
        e["text"]: idx for idx,e in enumerate(entries)
        if idx and e and e["tag"] == 1
    }
    added_names = {}
    inner_name_patches = 0

    for name_idx, info_off, length in class_attr_offsets(body):
        if cp_utf8(entries, name_idx) != "InnerClasses":
            continue
        if length < 2:
            continue
        n = u2(body, info_off)
        q = info_off + 2
        for _ in range(n):
            inner_class_info_idx = u2(body, q)
            inner_name_idx_off = q + 4
            inner_name_idx = u2(body, inner_name_idx_off)
            old_internal = cp_class_name(entries, inner_class_info_idx)
            desired = desired_inner_simple(old_internal) if old_internal else None
            if desired and inner_name_idx != 0:
                idx = existing_utf8.get(desired)
                if idx is None:
                    idx = added_names.get(desired)
                if idx is None:
                    idx = len(entries)
                    entries.append({"tag": 1, "text": desired})
                    added_names[desired] = idx
                    existing_utf8[desired] = idx
                struct.pack_into(">H", body, inner_name_idx_off, idx)
                inner_name_patches += 1
            q += 8

    # Rebuild constant pool with structural type references normalized.
    cp_out = bytearray()
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
            cp_out.append(1)
            cp_out += struct.pack(">H", len(raw))
            cp_out += raw
            if new_text != text:
                changed += 1
        else:
            cp_out += e["raw"]
        i += 2 if e["tag"] in (5,6) else 1

    out = bytearray()
    out += data[:8]
    out += struct.pack(">H", len(entries))
    out += cp_out
    out += body
    return bytes(out), changed, skipped, inner_name_patches, len(added_names)

zip_rows = []
changed_utf8 = 0
skipped_strings = 0
renamed_class_entries = 0
inner_name_patches = 0
inner_name_utf8_added = 0

with zipfile.ZipFile(SRC, "r") as zin, \
     zipfile.ZipFile(OUT, "w", compression=zipfile.ZIP_DEFLATED) as zout, \
     zipfile.ZipFile(NONAPP, "w", compression=zipfile.ZIP_DEFLATED) as nonapp:
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
                data, chg, skip, ip, ia = patch_class(data)
                changed_utf8 += chg
                skipped_strings += skip
                inner_name_patches += ip
                inner_name_utf8_added += ia
                renamed_class_entries += 1
                suffix = old_internal.rsplit("$", 1)[-1] if "$" in old_internal else ""
                kind = "TOP_LEVEL"
                if "$" in old_internal:
                    kind = "ANONYMOUS" if suffix.isdigit() else "NAMED_INNER"
                zip_rows.append({
                    "OldInternal": old_internal,
                    "NewInternal": all_map[old_internal],
                    "Kind": kind,
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
    "named_inner_mappings": len(named_inner_map),
    "renamed_class_entries": renamed_class_entries,
    "innerclasses_name_index_patches": inner_name_patches,
    "inner_name_utf8_entries_added": inner_name_utf8_added,
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
    f"- Named inner mappings: **{len(named_inner_map)}**",
    f"- Renamed class entries: **{renamed_class_entries}**",
    f"- InnerClasses name-index patches: **{inner_name_patches}**",
    f"- Inner-name UTF8 entries added: **{inner_name_utf8_added}**",
    f"- Rewritten structural UTF8 entries: **{changed_utf8}**",
    f"- Preserved string-constant hits: **{skipped_strings}**",
    f"- Duplicate SourceFile groups: **{len(dups)}**",
    "",
    "Rules:",
    "- Package prefix: l1r/ + original obfuscated package.",
    "- Top-level simple name: preserved SourceFile stem.",
    "- Duplicate SourceFile stem in one package: append __obf_<old-simple>.",
    "- Named inner segments get L1R_ prefix.",
    "- Numeric anonymous/local segments remain numeric.",
    "- InnerClasses.inner_name_index is patched to the normalized named-inner simple name.",
    "- String constants are preserved when constant-string-only.",
    "- This JAR is not a runtime replacement.",
    "",
    "Duplicate SourceFile groups:",
]
for pkg,stem,vals in sorted(dups):
    md.append(f"- {pkg}/{stem}: " + ", ".join(vals))
MD_OUT.write_text("\n".join(md) + "\n", encoding="utf-8")
print(json.dumps(state, indent=2))
