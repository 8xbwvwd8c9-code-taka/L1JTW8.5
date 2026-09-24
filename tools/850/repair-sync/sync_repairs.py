from __future__ import annotations

from typing import Iterable, Mapping


def build_promotion_scopes(records: Iterable[Mapping]) -> dict[str, dict]:
    scopes: dict[str, dict] = {}
    for record in records:
        commit = str(record.get("commit", "")).strip()
        bug_ids = [str(v).strip() for v in record.get("bug_ids", []) if str(v).strip()]
        source_files = [
            str(v).strip() for v in record.get("source_files", []) if str(v).strip()
        ]
        if not commit:
            raise ValueError("promotion record missing commit")
        if not bug_ids:
            raise ValueError(f"promotion record {commit} has no BUG IDs")
        if not source_files:
            raise ValueError(f"promotion record {commit} has no source files")

        for bug_id in bug_ids:
            scope = scopes.setdefault(bug_id, {"commits": [], "source_files": []})
            if commit not in scope["commits"]:
                scope["commits"].append(commit)
            for source_file in source_files:
                if source_file not in scope["source_files"]:
                    scope["source_files"].append(source_file)

    return scopes
