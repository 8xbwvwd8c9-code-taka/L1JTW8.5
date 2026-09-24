from __future__ import annotations

import re
from collections import defaultdict
from typing import Iterable, Mapping


PACKAGE_RE = re.compile(r"(?m)^\s*package\s+[A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*\s*;")


def _dot(internal: str) -> str:
    return internal.replace("/", ".")


def _package_of(internal: str) -> str:
    if "/" not in internal:
        return ""
    return internal.rsplit("/", 1)[0]


def rewrite_java_source(source: str, target_entry, all_entries: Iterable) -> str:
    entries = list(all_entries)
    target_package = _dot(_package_of(target_entry.dev_internal))

    if target_package:
        replacement = f"package {target_package};"
        if PACKAGE_RE.search(source):
            source = PACKAGE_RE.sub(replacement, source, count=1)
        else:
            source = replacement + "\n" + source

    # Rewrite exact recovered class identities first, longest names first.
    exact_pairs = sorted(
        ((_dot(e.recovered_internal), _dot(e.dev_internal)) for e in entries),
        key=lambda pair: len(pair[0]),
        reverse=True,
    )
    for recovered, dev in exact_pairs:
        if recovered != dev:
            source = source.replace(recovered, dev)

    # A wildcard import/package-qualified reference can be rewritten only when an
    # entire recovered package maps to one dev package. Split packages are left
    # untouched so a later validation gate can fail closed instead of guessing.
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
    del quarantined_sources  # Explicitly quarantined: never considered active authority.
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
