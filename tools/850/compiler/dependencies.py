#!/usr/bin/env python3
from __future__ import annotations

import re
from pathlib import Path
from typing import Mapping


_PACKAGE = re.compile(r"\bpackage\s+([A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*)\s*;")
_IMPORT = re.compile(r"\bimport\s+(?!static\s+)([A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*(?:\.\*)?)\s*;")


def source_identity(path: Path, source_text: str) -> str:
    package = _PACKAGE.search(source_text)
    simple = Path(path).stem
    return f"{package.group(1)}.{simple}" if package else simple


def direct_dependencies(source_text: str, known_identities: set[str]) -> tuple[set[str], bool]:
    deps: set[str] = set()
    complete = True
    for match in _IMPORT.finditer(source_text):
        imported = match.group(1)
        if imported.endswith(".*"):
            complete = False
            continue
        if imported in known_identities:
            deps.add(imported)
    # Also catch fully-qualified references when no import is present.
    for identity in known_identities:
        if re.search(r"(?<![\w$])" + re.escape(identity) + r"(?![\w$])", source_text):
            deps.add(identity)
    return deps, complete


def build_dependency_index(sources: Mapping[str, str]) -> dict[str, object]:
    identities = set(sources)
    reverse: dict[str, set[str]] = {identity: set() for identity in identities}
    direct: dict[str, list[str]] = {}
    complete = True
    for identity, text in sources.items():
        deps, local_complete = direct_dependencies(text, identities)
        deps.discard(identity)
        complete = complete and local_complete
        direct[identity] = sorted(deps)
        for dep in deps:
            reverse.setdefault(dep, set()).add(identity)
    return {
        "complete": complete,
        "direct": direct,
        "reverse": {key: sorted(value) for key, value in sorted(reverse.items())},
    }


def reverse_closure(changed: set[str], index: Mapping[str, object]) -> set[str]:
    reverse = index.get("reverse")
    if not isinstance(reverse, dict):
        raise ValueError("dependency index missing reverse map")
    closure = set(changed)
    queue = list(changed)
    while queue:
        current = queue.pop()
        for dependent in reverse.get(current, []):
            if dependent not in closure:
                closure.add(dependent)
                queue.append(dependent)
    return closure
