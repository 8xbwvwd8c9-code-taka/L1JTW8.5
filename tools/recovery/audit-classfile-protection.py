#!/usr/bin/env python3
import csv
import io
import json
import re
import struct
import zipfile
from collections import Counter, defaultdict
from pathlib import Path

JAR = Path("l1jserver2.jar")
OUT = Path("recovery")
OUT.mkdir(parents=True, exist_ok=True)

APP_PACKAGES = {
    "ai","aj","ak","al","am","an","ao","ap","aq","ar","as","at",
    "au","av","aw","ax","ay","az","ba","bb","bc","bd","be","bf",
    "bg","bh","bi","bj",
}

ACC_BRIDGE = 0x0040
ACC_SYNTHETIC = 0x1000

class ClassReader:
    def __init__(self, data: bytes):
        self.f = io.BytesIO(data)
        self.cp = [None]

    def u1(self):
        return struct.unpack(">B", self.f.read(1))[0]

    def u2(self):
        return struct.unpack(">H", self.f.read(2))[0]

    def u4(self):
        return struct.unpack(">I", self.f.read(4))[0]

    def skip(self, n):
        self.f.seek(n, io.SEEK_CUR)

    def utf8(self, idx):
        e = self.cp[idx]
        return e[1] if e and e[0] == "Utf8" else None

    def class_name(self, idx):
        e = self.cp[idx]
        if not e or e[0] != "Class":
            return None
        return self.utf8(e[1])

    def read_attributes(self, count, want_source=False):
        source = None
        for _ in range(count):
            name_idx = self.u2()
            length = self.u4()
            name = self.utf8(name_idx)
            if want_source and name == "SourceFile" and length == 2:
                source = self.utf8(self.u2())
            else:
                self.skip(length)
        return source

    def read_member(self, kind):
        access = self.u2()
        name = self.utf8(self.u2())
        desc = self.utf8(self.u2())
        acount = self.u2()
        self.read_attributes(acount)
        return {
            "kind": kind,
            "access": access,
            "name": name,
            "descriptor": desc,
            "synthetic": bool(access & ACC_SYNTHETIC),
            "bridge": kind == "method" and bool(access & ACC_BRIDGE),
        }

    def parse(self):
        if self.u4() != 0xCAFEBABE:
            raise ValueError("not a class file")
        minor = self.u2()
        major = self.u2()

        cp_count = self.u2()
        i = 1
        while i < cp_count:
            tag = self.u1()
            if tag == 1:
                n = self.u2()
                raw = self.f.read(n)
                self.cp.append(("Utf8", raw.decode("utf-8", errors="replace")))
            elif tag in (3, 4):
                self.skip(4)
                self.cp.append(("Num",))
            elif tag in (5, 6):
                self.skip(8)
                self.cp.append(("Wide",))
                self.cp.append(None)
                i += 1
            elif tag == 7:
                self.cp.append(("Class", self.u2()))
            elif tag == 8:
                self.cp.append(("String", self.u2()))
            elif tag in (9, 10, 11, 12, 17, 18):
                self.skip(4)
                self.cp.append(("Pair",))
            elif tag == 15:
                self.skip(3)
                self.cp.append(("Handle",))
            elif tag in (16, 19, 20):
                self.skip(2)
                self.cp.append(("Index",))
            else:
                raise ValueError(f"unsupported constant-pool tag {tag}")
            i += 1

        access = self.u2()
        this_class = self.u2()
        super_class = self.u2()
        name = self.class_name(this_class)
        super_name = self.class_name(super_class) if super_class else ""

        interfaces_count = self.u2()
        interfaces = [self.class_name(self.u2()) for _ in range(interfaces_count)]

        fields_count = self.u2()
        fields = [self.read_member("field") for _ in range(fields_count)]

        methods_count = self.u2()
        methods = [self.read_member("method") for _ in range(methods_count)]

        attrs_count = self.u2()
        source_file = self.read_attributes(attrs_count, want_source=True)

        return {
            "minor": minor,
            "major": major,
            "access": access,
            "name": name,
            "super": super_name,
            "interfaces": interfaces,
            "source_file": source_file,
            "synthetic": bool(access & ACC_SYNTHETIC),
            "fields": fields,
            "methods": methods,
        }


def is_app_path(path: str) -> bool:
    if path.startswith("l1j/server/"):
        return True
    first = path.split("/", 1)[0]
    return first in APP_PACKAGES


def top_level_name(internal_name: str) -> str:
    return internal_name.split("$", 1)[0]


def package_of(internal_name: str) -> str:
    return internal_name.rsplit("/", 1)[0].replace("/", ".") if "/" in internal_name else ""


with zipfile.ZipFile(JAR) as zf:
    names = zf.namelist()
    class_paths = [n for n in names if n.endswith(".class")]
    app_paths = [n for n in class_paths if is_app_path(n)]

    parsed = []
    errors = []
    for path in app_paths:
        try:
            info = ClassReader(zf.read(path)).parse()
            info["path"] = path
            parsed.append(info)
        except Exception as exc:
            errors.append({"path": path, "error": repr(exc)})

inventory_rows = []
synthetic_field_count = 0
synthetic_method_count = 0
synthetic_ctor_count = 0
bridge_method_count = 0
affected_synthetic = set()
affected_bridge = set()
method_collisions = []
field_collisions = []

for c in parsed:
    name = c["name"]
    is_inner = "$" in name
    is_anon = bool(re.search(r"\$\d+(?:$|\$)", name))
    syn_fields = sum(1 for x in c["fields"] if x["synthetic"])
    syn_methods = sum(1 for x in c["methods"] if x["synthetic"] and x["name"] != "<init>")
    syn_ctors = sum(1 for x in c["methods"] if x["synthetic"] and x["name"] == "<init>")
    bridges = sum(1 for x in c["methods"] if x["bridge"])

    synthetic_field_count += syn_fields
    synthetic_method_count += syn_methods
    synthetic_ctor_count += syn_ctors
    bridge_method_count += bridges
    if c["synthetic"] or syn_fields or syn_methods or syn_ctors:
        affected_synthetic.add(name)
    if bridges:
        affected_bridge.add(name)

    inventory_rows.append({
        "ClassPath": c["path"],
        "InternalName": name,
        "Package": package_of(name),
        "TopLevelClass": top_level_name(name),
        "Kind": "ANONYMOUS" if is_anon else ("INNER" if is_inner else "TOP_LEVEL"),
        "SourceFile": c["source_file"] or "",
        "MajorVersion": c["major"],
        "ClassSynthetic": int(c["synthetic"]),
        "SyntheticFields": syn_fields,
        "SyntheticMethods": syn_methods,
        "SyntheticConstructors": syn_ctors,
        "BridgeMethods": bridges,
    })

    mgroups = defaultdict(set)
    for m in c["methods"]:
        desc = m["descriptor"] or ""
        if ")" not in desc:
            continue
        args = desc[:desc.index(")") + 1]
        ret = desc[desc.index(")") + 1:]
        mgroups[(m["name"], args)].add(ret)
    for (mname, args), returns in mgroups.items():
        if len(returns) > 1:
            method_collisions.append({
                "Class": name.replace("/", "."),
                "Method": mname,
                "ArgsDescriptor": args,
                "ReturnDescriptors": ";".join(sorted(returns)),
                "ReturnVariantCount": len(returns),
            })

    fgroups = defaultdict(set)
    for f in c["fields"]:
        fgroups[f["name"]].add(f["descriptor"])
    for fname, descs in fgroups.items():
        if len(descs) > 1:
            field_collisions.append({
                "Class": name.replace("/", "."),
                "Field": fname,
                "Descriptors": ";".join(sorted(descs)),
                "DescriptorVariantCount": len(descs),
            })

with (OUT / "class_inventory.csv").open("w", encoding="utf-8", newline="") as f:
    w = csv.DictWriter(f, fieldnames=list(inventory_rows[0].keys()))
    w.writeheader()
    w.writerows(sorted(inventory_rows, key=lambda r: r["InternalName"]))

with (OUT / "jvm_signature_collisions.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["Class","Method","ArgsDescriptor","ReturnDescriptors","ReturnVariantCount"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(sorted(method_collisions, key=lambda r: (r["Class"], r["Method"], r["ArgsDescriptor"])))

with (OUT / "field_name_collisions.csv").open("w", encoding="utf-8", newline="") as f:
    fields = ["Class","Field","Descriptors","DescriptorVariantCount"]
    w = csv.DictWriter(f, fieldnames=fields)
    w.writeheader()
    w.writerows(sorted(field_collisions, key=lambda r: (r["Class"], r["Field"])))

kind_counts = Counter(r["Kind"] for r in inventory_rows)
major_counts = Counter(r["MajorVersion"] for r in inventory_rows)
source_present = sum(1 for r in inventory_rows if r["SourceFile"])

audit = {
    "jar_entries": len(names),
    "jar_class_files": len(class_paths),
    "application_class_files": len(app_paths),
    "parsed_application_classes": len(parsed),
    "parse_errors": errors,
    "top_level_classes": kind_counts["TOP_LEVEL"],
    "inner_classes": kind_counts["INNER"],
    "anonymous_classes": kind_counts["ANONYMOUS"],
    "sourcefile_present": source_present,
    "sourcefile_missing": len(parsed) - source_present,
    "class_major_versions": dict(sorted(major_counts.items())),
    "synthetic_classes": sum(1 for c in parsed if c["synthetic"]),
    "synthetic_fields": synthetic_field_count,
    "synthetic_methods": synthetic_method_count,
    "synthetic_constructors": synthetic_ctor_count,
    "synthetic_affected_classes": len(affected_synthetic),
    "bridge_methods": bridge_method_count,
    "bridge_affected_classes": len(affected_bridge),
    "jvm_return_type_only_method_collisions": len(method_collisions),
    "field_name_descriptor_collisions": len(field_collisions),
}

(OUT / "protection_audit.json").write_text(
    json.dumps(audit, indent=2, ensure_ascii=False) + "\n",
    encoding="utf-8",
)

comparison = []
comparison.append("# L1JTW8.5 Classfile Protection Audit")
comparison.append("")
comparison.append("Ground truth: `l1jserver2.jar`. This report is generated directly from classfile structures, not decompiler guesses.")
comparison.append("")
comparison.append("## Inventory")
comparison.append("")
comparison.append(f"- JAR entries: **{audit['jar_entries']}**")
comparison.append(f"- All JAR class files: **{audit['jar_class_files']}**")
comparison.append(f"- Application class files (L1J core filter): **{audit['application_class_files']}**")
comparison.append(f"- Parsed application classes: **{audit['parsed_application_classes']}**")
comparison.append(f"- Top-level: **{audit['top_level_classes']}**")
comparison.append(f"- Inner: **{audit['inner_classes']}**")
comparison.append(f"- Anonymous: **{audit['anonymous_classes']}**")
comparison.append(f"- SourceFile present: **{audit['sourcefile_present']} / {audit['parsed_application_classes']}**")
comparison.append(f"- Class major versions: **{audit['class_major_versions']}**")
comparison.append("")
comparison.append("## Protection / Java-representation audit")
comparison.append("")
comparison.append(f"- Synthetic classes: **{audit['synthetic_classes']}**")
comparison.append(f"- Synthetic fields: **{audit['synthetic_fields']}**")
comparison.append(f"- Synthetic methods: **{audit['synthetic_methods']}**")
comparison.append(f"- Synthetic constructors: **{audit['synthetic_constructors']}**")
comparison.append(f"- Classes affected by synthetic members/class flag: **{audit['synthetic_affected_classes']}**")
comparison.append(f"- Bridge methods: **{audit['bridge_methods']}** across **{audit['bridge_affected_classes']}** classes")
comparison.append(f"- Same-name/same-args methods differentiated only by return descriptor: **{audit['jvm_return_type_only_method_collisions']}**")
comparison.append(f"- Same-name fields with multiple descriptors in one class: **{audit['field_name_descriptor_collisions']}**")
comparison.append("")
comparison.append("## Interpretation gate")
comparison.append("")
comparison.append("- If synthetic member counts are widespread, create a compile-reference JAR that clears only ACC_SYNTHETIC, following the validated L380 recovery method.")
comparison.append("- If return-type-only method collisions exist, use descriptor-based rename maps in a recovery-only representation and update every call site consistently.")
comparison.append("- Inner/anonymous class inventory must be accounted for before any source-only full-tree compile can be accepted.")
comparison.append("- CFR output remains provisional until source-only compilation and ABI/descriptor validation pass.")
if errors:
    comparison.append("")
    comparison.append("## Parse errors")
    comparison.append("")
    for e in errors:
        comparison.append(f"- `{e['path']}`: `{e['error']}`")

(OUT / "PROTECTION_AUDIT.md").write_text("\n".join(comparison) + "\n", encoding="utf-8")

print(json.dumps(audit, indent=2))
