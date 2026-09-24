from __future__ import annotations

from typing import Iterable, Mapping, NamedTuple


class PackageMapEntry(NamedTuple):
    original_internal: str
    recovered_internal: str
    dev_internal: str
    source_file: str
    category: str


def _stem(source_file: str) -> str:
    if not source_file or not source_file.endswith(".java"):
        raise ValueError(f"invalid SourceFile: {source_file!r}")
    return source_file[:-5]


def _normalize_rule(rule: Mapping[str, str], family: str) -> tuple[str, str]:
    package = str(rule.get("package", "")).strip().strip("/")
    category = str(rule.get("category", "")).strip()
    if not package or not category:
        raise ValueError(f"invalid package rule for family: {family}")
    return package, category


def _rule_for_identity(
    original_internal: str, rules: Mapping[str, object]
) -> tuple[str, str]:
    families = rules.get("families", {})
    overrides = rules.get("overrides", {})
    if not isinstance(families, Mapping) or not isinstance(overrides, Mapping):
        raise ValueError("rules must contain mapping objects: families, overrides")

    override = overrides.get(original_internal)
    if override is not None:
        if not isinstance(override, Mapping):
            raise ValueError(f"invalid override for: {original_internal}")
        return _normalize_rule(override, original_internal)

    if "/" not in original_internal:
        raise ValueError(f"invalid application identity: {original_internal}")
    family = original_internal.split("/", 1)[0]
    family_rule = families.get(family)
    if family_rule is None:
        raise ValueError(f"unmapped application family: {family}")
    if not isinstance(family_rule, Mapping):
        raise ValueError(f"invalid package rule for family: {family}")
    return _normalize_rule(family_rule, family)


def build_package_map(
    mapping_rows: Iterable[Mapping[str, str]], rules: Mapping[str, object]
) -> list[PackageMapEntry]:
    result: list[PackageMapEntry] = []
    for row in mapping_rows:
        original_dotted = str(row.get("Class", "")).strip()
        source_file = str(row.get("SourceFile", "")).strip()
        source_stem = _stem(source_file)
        if not original_dotted:
            raise ValueError("missing Class")

        original_internal = original_dotted.replace(".", "/")

        # Legacy fixture input: already-readable l1j.server names stay readable.
        # The authoritative namespace builder below handles the real
        # l1j/server/a -> Config recovery case.
        if original_internal.startswith("l1j/server/"):
            result.append(
                PackageMapEntry(
                    original_internal=original_internal,
                    recovered_internal=original_internal,
                    dev_internal=original_internal,
                    source_file=source_file,
                    category="server",
                )
            )
            continue

        family = original_internal.split("/", 1)[0]
        recovered_internal = f"l1r/{family}/{source_stem}"
        package, category = _rule_for_identity(original_internal, rules)
        result.append(
            PackageMapEntry(
                original_internal=original_internal,
                recovered_internal=recovered_internal,
                dev_internal=f"{package}/{source_stem}",
                source_file=source_file,
                category=category,
            )
        )

    return result


def build_package_map_from_namespace(
    namespace_rows: Iterable[Mapping[str, str]], rules: Mapping[str, object]
) -> list[PackageMapEntry]:
    """Build the real Fast Dev map from accepted namespace recovery output.

    ``source_namespace_map.csv`` records collision-safe recovered top-level names,
    including ``__obf_*`` variants for duplicate SourceFile groups. Inner rows are
    excluded because their bytecode/source references follow their top-level owner.
    """
    result: list[PackageMapEntry] = []
    for row in namespace_rows:
        if str(row.get("Kind", "")).strip() != "TOP_LEVEL":
            continue

        original_internal = str(row.get("OldInternal", "")).strip().strip("/")
        recovered_internal = str(row.get("NewInternal", "")).strip().strip("/")
        if not original_internal or not recovered_internal:
            raise ValueError("namespace TOP_LEVEL row missing OldInternal/NewInternal")
        if "$" in original_internal or "$" in recovered_internal:
            raise ValueError(
                f"TOP_LEVEL row unexpectedly contains inner identity: {original_internal}"
            )
        if not recovered_internal.startswith("l1r/"):
            raise ValueError(f"unexpected recovered namespace: {recovered_internal}")

        simple_name = recovered_internal.rsplit("/", 1)[-1]
        source_file = simple_name + ".java"

        if original_internal.startswith("l1j/server/"):
            package = "l1j/server"
            category = "server"
        else:
            package, category = _rule_for_identity(original_internal, rules)

        result.append(
            PackageMapEntry(
                original_internal=original_internal,
                recovered_internal=recovered_internal,
                dev_internal=f"{package}/{simple_name}",
                source_file=source_file,
                category=category,
            )
        )

    return result


def build_class_map_from_namespace(
    namespace_rows: Iterable[Mapping[str, str]],
    top_level_entries: Iterable[PackageMapEntry],
) -> dict[str, str]:
    """Expand 788 top-level mappings to every accepted application class.

    Inner-class names are never guessed. The recovered namespace map already
    contains the exact renamed owner and exact ``$...`` suffix. We preserve that
    suffix and attach it to the semantic Dev top-level owner.
    """
    rows = list(namespace_rows)
    tops = list(top_level_entries)
    by_original = {entry.original_internal: entry for entry in tops}
    if len(by_original) != len(tops):
        raise ValueError("duplicate top-level original identity")

    result: dict[str, str] = {}
    seen_dev: set[str] = set()
    for row in rows:
        original = str(row.get("OldInternal", "")).strip().strip("/")
        recovered = str(row.get("NewInternal", "")).strip().strip("/")
        if not original or not recovered:
            raise ValueError("namespace row missing OldInternal/NewInternal")

        owner_original = original.split("$", 1)[0]
        owner = by_original.get(owner_original)
        if owner is None:
            raise ValueError(f"inner/runtime class has no top-level owner: {original}")

        owner_recovered = owner.recovered_internal
        if recovered == owner_recovered:
            dev = owner.dev_internal
        else:
            required_prefix = owner_recovered + "$"
            if not recovered.startswith(required_prefix):
                raise ValueError(
                    f"recovered inner identity escaped owner: {recovered} !~ {required_prefix}"
                )
            dev = owner.dev_internal + recovered[len(owner_recovered):]

        if original in result:
            raise ValueError(f"duplicate runtime OriginalInternal: {original}")
        if dev in seen_dev:
            raise ValueError(f"duplicate runtime DevInternal: {dev}")
        result[original] = dev
        seen_dev.add(dev)

    return result


def validate_package_map(entries: Iterable[PackageMapEntry]) -> None:
    seen_original: dict[str, PackageMapEntry] = {}
    seen_recovered: dict[str, PackageMapEntry] = {}
    seen_dev: dict[str, PackageMapEntry] = {}

    for entry in entries:
        if entry.original_internal in seen_original:
            raise ValueError(f"duplicate OriginalInternal: {entry.original_internal}")
        if entry.recovered_internal in seen_recovered:
            raise ValueError(f"duplicate RecoveredInternal: {entry.recovered_internal}")
        if entry.dev_internal in seen_dev:
            raise ValueError(f"duplicate DevInternal: {entry.dev_internal}")
        seen_original[entry.original_internal] = entry
        seen_recovered[entry.recovered_internal] = entry
        seen_dev[entry.dev_internal] = entry
