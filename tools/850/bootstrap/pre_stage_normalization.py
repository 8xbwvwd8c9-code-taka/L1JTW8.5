#!/usr/bin/env python3
from __future__ import annotations

import re
from pathlib import Path


# Recovery-only source representation repairs proven during the normalized
# recovery pipeline. These transformations do not change gameplay behavior;
# they only restore Java source forms that javac can express while preserving
# the donor call targets/types.
_GENERIC_REPLACEMENTS = (
    (
        "l1r/aq/L1CastleLocation.java",
        "for (Entry var1 : aI.entrySet())",
        "for (Entry<Integer, L1Location> var1 : aI.entrySet())",
    ),
    (
        "l1r/aq/L1CastleLocation.java",
        "for (Entry var1 : aJ.entrySet())",
        "for (Entry<Integer, L1MapArea> var1 : aJ.entrySet())",
    ),
    (
        "l1r/aq/L1CastleLocation.java",
        "for (Entry var3 : aK.entrySet())",
        "for (Entry<Integer, Integer> var3 : aK.entrySet())",
    ),
    (
        "l1r/ao/DropTable.java",
        "HashMap var1 = new HashMap<>();",
        "HashMap<Integer, ArrayList<L1Drop>> var1 = new HashMap<>();",
    ),
    (
        "l1r/ao/DropTable.java",
        "ArrayList var14 = var1.get(var13.e());",
        "ArrayList<L1Drop> var14 = var1.get(var13.e());",
    ),
    (
        "l1r/ao/DropTable.java",
        "List var4 = this.c.get(var3);",
        "List<L1Drop> var4 = this.c.get(var3);",
    ),
    (
        "l1r/aq/L1Getback.java",
        "ArrayList var5 = b.get(var4.g);",
        "ArrayList<L1Getback> var5 = b.get(var4.g);",
    ),
    (
        "l1r/aq/L1Getback.java",
        "List var6 = b.get(var5);",
        "List<L1Getback> var6 = b.get(var5);",
    ),
    (
        "l1r/be/S_Bookmarks.java",
        "ArrayList var2 = new ArrayList<>();",
        "ArrayList<L1BookMark> var2 = new ArrayList<>();",
    ),
    (
        "l1r/be/S_Party.java",
        "CopyOnWriteArrayList var3 = var1.aL().c();",
        "CopyOnWriteArrayList<L1PcInstance> var3 = var1.aL().c();",
    ),
    (
        "l1r/be/S_Party.java",
        "CopyOnWriteArrayList var2 = var1.aL().c();",
        "CopyOnWriteArrayList<L1PcInstance> var2 = var1.aL().c();",
    ),
    (
        "l1r/be/S_PrivateShop.java",
        "List var5 = var4.aU();",
        "List<L1PrivateShopSellList> var5 = var4.aU();",
    ),
    (
        "l1r/be/S_PrivateShop.java",
        "List var18 = var4.aV();",
        "List<L1PrivateShopBuyList> var18 = var4.aV();",
    ),
    (
        "l1r/aq/L1Buddy.java",
        "for (Entry var3 : this.b.entrySet())",
        "for (Entry<Integer, String> var3 : this.b.entrySet())",
    ),
    (
        "l1r/ba/HomeTownTimer.java",
        "Collection var1 = L1World.a().c();",
        "Collection<L1PcInstance> var1 = L1World.a().c();",
    ),
    (
        "l1r/aq/L1Teleport.java",
        "HashSet var7 = new HashSet<>();",
        "HashSet<L1PcInstance> var7 = new HashSet<>();",
    ),
)

_BRIDGE_RX = re.compile(
    r"(?ms)^(?P<indent>\s*)// \$VF: synthetic method\s*\n"
    r"(?P=indent)@Override\s*\n"
    r"(?P=indent)public int compare\(Object (?P<v1>[A-Za-z_$][\w$]*), Object (?P<v2>[A-Za-z_$][\w$]*)\) \{\s*\n"
    r"(?P=indent)\s*return this\.(?P<delegate>[A-Za-z_$][\w$]*)\(\((?P<t1>[^)]+)\)(?P=v1), \((?P<t2>[^)]+)\)(?P=v2)\);\s*\n"
    r"(?P=indent)\}"
)


def _baseline_only_generated(path: Path, source_root: Path) -> bool:
    rel = path.relative_to(source_root).as_posix()
    return rel.startswith("l1r/an/PBMessageALL") and rel.endswith(".java")


def _replace_known_form(source_root: Path, rel: str, old: str, new: str) -> int:
    path = source_root / rel
    if not path.is_file():
        return 0
    text = path.read_text(encoding="utf-8", errors="replace")
    count = text.count(old)
    if count > 1:
        raise RuntimeError(
            f"pre-stage normalization found duplicate historical form: {rel}: {old!r} count={count}"
        )
    if count == 0:
        return 0
    path.write_text(text.replace(old, new, 1), encoding="utf-8")
    return 1


def normalize_pre_stage_sources(authority_root: Path) -> dict[str, object]:
    """Apply idempotent pre-stage representation repairs to one authority copy.

    This runs after completed-repair overlay, so completed gameplay fixes remain
    authoritative. Each transform only recognizes the historical decompiler form;
    already-normalized or subsequently repaired code is left untouched.
    """
    authority_root = Path(authority_root).resolve()
    source_root = authority_root / "recovery" / "normalized-src-vf"
    if not source_root.is_dir():
        raise FileNotFoundError(source_root)

    package_shadow_repairs = 0
    package_shadow_files: list[str] = []
    for path in sorted(source_root.rglob("*.java")):
        if _baseline_only_generated(path, source_root):
            continue
        text = path.read_text(encoding="utf-8", errors="replace")
        if not re.search(r"(?m)^import\s+a\.g\s*;\s*$", text):
            continue
        new, count = re.subn(r"\ba\.g\.a\s*\(", "g.a(", text)
        if count:
            path.write_text(new, encoding="utf-8")
            package_shadow_repairs += count
            package_shadow_files.append(path.relative_to(source_root).as_posix())

    # L1Craft declares both members named `a` and `g`. Package qualification,
    # imported-type qualification and a static import are therefore all shadowed
    # in expression/method lookup. Put `g` in a cast type context instead; Java 8
    # resolves it to imported type a.g, and invoking its static a(...) through a
    # null-typed expression does not dereference at runtime or expand class ABI.
    l1craft_repairs = 0
    craft = source_root / "l1r" / "aq" / "L1Craft.java"
    if craft.is_file():
        text = craft.read_text(encoding="utf-8", errors="replace")
        text = re.sub(r"(?m)^import static a\.g\.a;\s*\n", "", text)
        text, l1craft_repairs = re.subn(
            r"\bg\.a\s*\(",
            "((g)null).a(",
            text,
        )
        if l1craft_repairs:
            craft.write_text(text, encoding="utf-8")

    generic_repairs = 0
    generic_files: set[str] = set()
    for rel, old, new in _GENERIC_REPLACEMENTS:
        count = _replace_known_form(source_root, rel, old, new)
        if count:
            generic_repairs += count
            generic_files.add(rel)

    comparator_repairs = 0
    comparator_files: set[str] = set()
    for path in sorted(source_root.rglob("*.java")):
        if _baseline_only_generated(path, source_root):
            continue
        text = path.read_text(encoding="utf-8", errors="replace")

        def bridge_repl(match: re.Match[str]) -> str:
            nonlocal comparator_repairs
            t1 = match.group("t1").strip()
            t2 = match.group("t2").strip()
            if t1 != t2:
                return match.group(0)
            comparator_repairs += 1
            comparator_files.add(path.relative_to(source_root).as_posix())
            indent = match.group("indent")
            v1 = match.group("v1")
            v2 = match.group("v2")
            delegate = match.group("delegate")
            return (
                f"{indent}@Override\n"
                f"{indent}public int compare({t1} {v1}, {t1} {v2}) {{\n"
                f"{indent}   return this.{delegate}({v1}, {v2});\n"
                f"{indent}}}"
            )

        new = _BRIDGE_RX.sub(bridge_repl, text)
        if new != text:
            path.write_text(new, encoding="utf-8")

    return {
        "package_shadow_repairs": package_shadow_repairs,
        "package_shadow_files": sorted(set(package_shadow_files)),
        # Historical key kept stable for callers; the representation is now a
        # type-context call rather than a static import.
        "l1craft_static_owner_repairs": l1craft_repairs,
        "generic_type_repairs": generic_repairs,
        "generic_type_repair_files": sorted(generic_files),
        "comparator_bridge_repairs": comparator_repairs,
        "comparator_bridge_files": sorted(comparator_files),
        "gameplay_logic_changed": False,
    }
