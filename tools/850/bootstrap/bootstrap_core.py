from __future__ import annotations

import re
from collections import defaultdict
from typing import Iterable, Mapping


PACKAGE_RE = re.compile(r"(?m)^\s*package\s+[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*\s*;")
EMBEDDED_RECOVERY_RE = re.compile(r"[A-Za-z0-9_$]l1r\.")
PROTECTED_SOURCE_RE = re.compile(
    r'(?:"(?:\\.|[^"\\])*"|\'(?:\\.|[^\'\\])*\'|//[^\n]*|/\*.*?\*/)',
    re.DOTALL,
)
QUALIFIED_IDENTIFIER_RE = re.compile(
    r"(?<![A-Za-z0-9_$])[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)+(?![A-Za-z0-9_$])"
)


def _dot(internal: str) -> str:
    return internal.replace("/", ".")


def _package_of(internal: str) -> str:
    if "/" not in internal:
        return ""
    return internal.rsplit("/", 1)[0]


def _is_java_identifier_part(char: str) -> bool:
    return char.isalnum() or char in "_$"


def _restore_embedded_recovery_collisions(source: str, entries: Iterable) -> str:
    """Undo recovery identities that were accidentally embedded in other names.

    The historical normalized JAR used substring replacement on structural UTF8.
    Short obfuscated identities could therefore match inside non-application names;
    for example ``ax/c`` inside ``javax/crypto/Cipher`` became an embedded
    ``l1r/ax/L1MapArea`` identity. A valid Java type identity cannot be directly
    glued to another identifier character, so only those impossible embedded
    occurrences are restored to their original identity. Strings, chars and
    comments are left byte-for-byte unchanged.
    """
    if EMBEDDED_RECOVERY_RE.search(source) is None:
        return source

    pairs = sorted(
        ((_dot(e.recovered_internal), _dot(e.original_internal)) for e in entries),
        key=lambda pair: len(pair[0]),
        reverse=True,
    )

    def repair_identifier(match: re.Match[str]) -> str:
        token = match.group(0)
        for recovered, original in pairs:
            start = 0
            while True:
                pos = token.find(recovered, start)
                if pos < 0:
                    break
                end = pos + len(recovered)
                embedded_left = pos > 0 and _is_java_identifier_part(token[pos - 1])
                embedded_right = end < len(token) and _is_java_identifier_part(token[end])
                if embedded_left or embedded_right:
                    token = token[:pos] + original + token[end:]
                    start = pos + len(original)
                else:
                    start = end
        return token

    parts: list[str] = []
    cursor = 0
    for protected in PROTECTED_SOURCE_RE.finditer(source):
        code = source[cursor:protected.start()]
        parts.append(QUALIFIED_IDENTIFIER_RE.sub(repair_identifier, code))
        parts.append(protected.group(0))
        cursor = protected.end()
    parts.append(QUALIFIED_IDENTIFIER_RE.sub(repair_identifier, source[cursor:]))
    return "".join(parts)


def _restore_donor_backed_decompiler_artifacts(source: str, recovered_internal: str) -> str:
    """Restore exact source artifacts proven by the repaired obfuscated donor.

    These repairs are recovery-only syntax/type restorations. They are intentionally
    identity-scoped and do not infer gameplay behavior. The repaired donor preserves
    overload-disambiguating casts, collection element types, imports and synthetic
    bridge shape that Vineflower lost or rendered as uncompilable source.
    """
    if recovered_internal == "l1r/ap/L1PcInstance":
        source = source.replace(
            "   @Override\n   public void c(int var1) {",
            "   public void c(int var1) {",
            1,
        )
        source = source.replace(
            "if (var4.d(this)) {",
            "if (var4.d((L1Character)this)) {",
            1,
        )
        source = source.replace(
            "if (!this.b(var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {",
            "if (!this.b((L1Object)var1) && var1.fp() == this.fp() && !(var1 instanceof L1EffectInstance)) {",
            1,
        )
    elif recovered_internal == "l1r/ap/L1PetInstance":
        for signature in (
            "public void d(int var1) {",
            "public void b(boolean var1) {",
            "public void i() {",
            "public void b(L1ItemInstance var1) {",
        ):
            source = source.replace(
                "   @Override\n   " + signature,
                "   " + signature,
                1,
            )
    elif recovered_internal == "l1r/ap/L1NpcInstance":
        source = source.replace(
            "ArrayList var1 = new ArrayList<>();",
            "ArrayList<L1GroundInventory> var1 = new ArrayList<>();",
            2,
        )
        source = source.replace(
            "LinkedList var18 = new LinkedList<>();",
            "LinkedList<int[]> var18 = new LinkedList<>();",
            1,
        )
    elif recovered_internal == "l1r/aq/L1Teleport":
        source = source.replace(
            "HashSet var7 = new HashSet<>();",
            "HashSet<L1PcInstance> var7 = new HashSet<>();",
            1,
        )
    elif recovered_internal == "l1r/aj/C_Amount":
        missing_imports = [
            import_line
            for import_line in (
                "import l1r.ao.ClanTable;",
                "import l1r.aq.L1Clan;",
            )
            if import_line not in source
        ]
        if missing_imports:
            source = source.replace(
                "package l1r.aj;",
                "package l1r.aj;\n\n" + "\n".join(missing_imports),
                1,
            )
    elif recovered_internal == "l1r/aj/C_ItemUSe":
        for target in ("var150", "var183"):
            source = source.replace(
                f"var3.ct(var3.a({target}));",
                f"var3.ct(var3.a((L1Object){target}));",
                1,
            )
    elif recovered_internal == "l1r/au/L1Inventory":
        source = source.replace(
            "public L1ItemInstance[] f(int var1, int var2) {\n      ArrayList var3 = new ArrayList<>();",
            "public L1ItemInstance[] f(int var1, int var2) {\n      ArrayList<L1ItemInstance> var3 = new ArrayList<>();",
            1,
        )
        source = source.replace(
            "private L1ItemInstance[] h(int var1) {\n      ArrayList var2 = new ArrayList<>();",
            "private L1ItemInstance[] h(int var1) {\n      ArrayList<L1ItemInstance> var2 = new ArrayList<>();",
            1,
        )
    elif recovered_internal == "l1r/aj/C_ProtoBuffers":
        source = source.replace(
            "ArrayList var152 = new ArrayList<>();",
            "ArrayList<L1ItemInstance> var152 = new ArrayList<>();",
            1,
        )
    elif recovered_internal == "l1r/aj/C_Result":
        source = source.replace(
            "HashMap var32 = LuckyDrawTable.a().c(var2.e().d());",
            "HashMap<Integer, L1ItemInstance> var32 = LuckyDrawTable.a().c(var2.e().d());",
            1,
        )
        source = source.replace(
            "CopyOnWriteArrayList var44 = var37.aU();",
            "CopyOnWriteArrayList<L1PrivateShopSellList> var44 = var37.aU();",
            1,
        )
        source = source.replace(
            "List var41 = var35.aV();",
            "List<L1PrivateShopBuyList> var41 = var35.aV();",
            1,
        )
    elif recovered_internal == "l1r/aj/C_ShopWorld":
        source = source.replace(
            "ConcurrentHashMap var13 = ShopWorldTable.a().a(var2.a());",
            "ConcurrentHashMap<Integer, L1ItemInstance> var13 = ShopWorldTable.a().a(var2.a());",
            1,
        )
        source = source.replace(
            "ConcurrentHashMap var28 = ShopWorldTable.a().a(var2.a());",
            "ConcurrentHashMap<Integer, L1ItemInstance> var28 = ShopWorldTable.a().a(var2.a());",
            1,
        )
    elif recovered_internal == "l1r/ao/RankingTable":
        source = source.replace(
            "int var7 = var3.get(var6);",
            "int var7 = ((Integer)var3.get(var6)).intValue();",
            1,
        )
    elif recovered_internal == "l1r/ao/ShopTable":
        source = source.replace(
            "HashMap var4 = ItemTable.a().c();",
            "HashMap<Integer, L1Item> var4 = ItemTable.a().c();",
            1,
        )
        source = source.replace(
            "ArrayList var5 = new ArrayList<>();",
            "ArrayList<L1ShopItem> var5 = new ArrayList<>();",
            1,
        )
        source = source.replace(
            "List var6 = var1.b();",
            "List<L1ShopItem> var6 = var1.b();",
            1,
        )
    elif recovered_internal == "l1r/ba/HomeTownTimer":
        source = source.replace(
            "Collection var1 = L1World.a().c();",
            "Collection<L1PcInstance> var1 = L1World.a().c();",
            1,
        )
    elif recovered_internal == "l1r/be/S_PrivateShop":
        missing_imports = [
            import_line
            for import_line in (
                "import java.util.ArrayList;",
                "import java.util.concurrent.CopyOnWriteArrayList;",
            )
            if import_line not in source
        ]
        if missing_imports:
            source = source.replace(
                "import java.util.List;",
                "import java.util.List;\n" + "\n".join(missing_imports),
                1,
            )
        source = source.replace(
            "List var5 = var4.aU();",
            "CopyOnWriteArrayList<L1PrivateShopSellList> var5 = var4.aU();",
            1,
        )
        source = source.replace(
            "List var18 = var4.aV();",
            "ArrayList<L1PrivateShopBuyList> var18 = var4.aV();",
            1,
        )
    elif recovered_internal == "l1r/be/S_ProtoBuffers":
        source = source.replace("a.g.a(", "((a.g)null).a(")
    return source


def rewrite_java_source(source: str, target_entry, all_entries: Iterable) -> str:
    entries = list(all_entries)
    source = _restore_embedded_recovery_collisions(source, entries)
    source = _restore_donor_backed_decompiler_artifacts(
        source, target_entry.recovered_internal
    )
    target_package = _dot(_package_of(target_entry.dev_internal))

    if target_package:
        replacement = f"package {target_package};"
        if PACKAGE_RE.search(source):
            source = PACKAGE_RE.sub(replacement, source, count=1)
        else:
            source = replacement + "\n" + source

    exact_pairs = sorted(
        ((_dot(e.recovered_internal), _dot(e.dev_internal)) for e in entries),
        key=lambda pair: len(pair[0]),
        reverse=True,
    )
    for recovered, dev in exact_pairs:
        if recovered != dev:
            source = source.replace(recovered, dev)

    package_targets: dict[str, set[str]] = defaultdict(set)
    for entry in entries:
        package_targets[_package_of(entry.recovered_internal)].add(
            _package_of(entry.dev_internal)
        )
    for recovered_pkg, dev_pkgs in sorted(
        package_targets.items(), key=lambda item: len(item[0]), reverse=True
    ):
        if not recovered_pkg or len(dev_pkgs) != 1:
            continue
        dev_pkg = next(iter(dev_pkgs))
        recovered_dot = _dot(recovered_pkg)
        dev_dot = _dot(dev_pkg)
        if recovered_dot != dev_dot:
            source = source.replace(recovered_dot + ".", dev_dot + ".")

    return source


def build_core_tree(
    entries: Iterable,
    baseline_sources: Mapping[str, str],
    completed_sources: Mapping[str, str],
    quarantined_sources: Mapping[str, str] | None = None,
):
    del quarantined_sources
    entries = list(entries)

    seen_original: set[str] = set()
    seen_recovered: set[str] = set()
    seen_dev: set[str] = set()
    duplicates: list[str] = []
    for entry in entries:
        for value, seen, label in (
            (entry.original_internal, seen_original, "OriginalInternal"),
            (entry.recovered_internal, seen_recovered, "RecoveredInternal"),
            (entry.dev_internal, seen_dev, "DevInternal"),
        ):
            if value in seen:
                duplicates.append(f"{label}={value}")
            seen.add(value)
    if duplicates:
        raise ValueError("duplicate source identities: " + ", ".join(sorted(duplicates)))

    missing = [
        entry.recovered_internal
        for entry in entries
        if entry.recovered_internal not in completed_sources
        and entry.recovered_internal not in baseline_sources
    ]
    if missing:
        raise ValueError("missing source identities: " + ", ".join(sorted(missing)))

    tree: dict[str, str] = {}
    index: dict[str, dict[str, str]] = {}
    for entry in entries:
        if entry.recovered_internal in completed_sources:
            source = completed_sources[entry.recovered_internal]
            authority = "completed"
        else:
            source = baseline_sources[entry.recovered_internal]
            authority = "baseline"

        dev_path = entry.dev_internal + ".java"
        if dev_path in tree:
            raise ValueError(f"duplicate output path: {dev_path}")

        tree[dev_path] = rewrite_java_source(source, entry, entries)
        index[entry.dev_internal] = {
            "original_internal": entry.original_internal,
            "recovered_internal": entry.recovered_internal,
            "dev_internal": entry.dev_internal,
            "source_file": entry.source_file,
            "category": entry.category,
            "authority": authority,
        }

    if len(tree) != len(entries) or len(index) != len(entries):
        raise ValueError("source-count mismatch after bootstrap")

    return tree, index
