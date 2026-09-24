from __future__ import annotations

from copy import deepcopy
from typing import Iterable, Mapping


PENDING = "PENDING"
IN_REPAIR = "IN_REPAIR"
PROMOTED_NOT_SYNCED = "PROMOTED_NOT_SYNCED"
SYNCED_DEV = "SYNCED_DEV"
DEFERRED_COMPILE = "DEFERRED_COMPILE"
VALIDATED_DEV = "VALIDATED_DEV"

PRESERVED_COMPLETED_STATES = {SYNCED_DEV, DEFERRED_COMPILE, VALIDATED_DEV}
SYNC_ELIGIBLE_STATES = {PROMOTED_NOT_SYNCED, DEFERRED_COMPILE}


def _blank_entry(bug_id: str) -> dict:
    return {
        "bug_id": bug_id,
        "level": "L2",
        "state": PENDING,
        "work_commit": None,
        "completed_commit": None,
        "source_files": [],
        "last_completed_seen": None,
        "dev_sync_status": None,
        "dev_compile_status": None,
        "last_sync_time": None,
    }


def reconcile_registry(
    *,
    audit_bug_ids: Iterable[str],
    completed_bug_ids: Iterable[str],
    in_repair_bug_ids: Iterable[str],
    existing_registry: Mapping[str, Mapping],
) -> dict[str, dict]:
    audit = set(audit_bug_ids)
    completed = set(completed_bug_ids)
    in_repair = set(in_repair_bug_ids)
    bug_ids = sorted(audit | completed | set(existing_registry))

    registry: dict[str, dict] = {}
    for bug_id in bug_ids:
        previous = existing_registry.get(bug_id)
        entry = _blank_entry(bug_id)
        if previous:
            entry.update(deepcopy(dict(previous)))
        entry["bug_id"] = bug_id
        entry.setdefault("level", "L2")
        entry.setdefault("source_files", [])

        if bug_id in completed:
            if entry.get("state") not in PRESERVED_COMPLETED_STATES:
                entry["state"] = PROMOTED_NOT_SYNCED
        elif bug_id in in_repair:
            entry["state"] = IN_REPAIR
        else:
            entry["state"] = PENDING

        registry[bug_id] = entry

    return registry


def is_sync_eligible(entry: Mapping) -> bool:
    return entry.get("state") in SYNC_ELIGIBLE_STATES
