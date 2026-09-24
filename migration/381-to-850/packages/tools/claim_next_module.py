#!/usr/bin/env python3
import argparse
import json
import re
import sys
from datetime import datetime, timezone
from pathlib import Path


COMPLETION_FIELDS = {
    "work_commit",
    "completed_commit",
    "validation_path",
    "homepage_anchor",
    "ci_url",
    "completed_at",
}
COMMIT_SHA = re.compile(r"[0-9a-f]{40}")


def _active_items(queue):
    return [item for item in queue["items"] if item.get("status") == "ACTIVE"]


def claim_next(queue: dict, actor: str) -> dict:
    if not isinstance(actor, str) or not actor.strip():
        raise ValueError("CLAIM_ACTOR_REQUIRED")
    if _active_items(queue):
        raise ValueError("QUEUE_ACTIVE_MODULE_EXISTS")
    for item in queue["items"]:
        if item.get("status") == "PENDING":
            item["status"] = "ACTIVE"
            item["claim"] = {
                "actor": actor.strip(),
                "claimed_at": datetime.now(timezone.utc).isoformat(),
            }
            return item
    raise ValueError("QUEUE_NO_PENDING_MODULE")


def _find_module(queue, module_id):
    for item in queue["items"]:
        if item.get("module_id") == module_id:
            return item
    raise ValueError("MODULE_NOT_FOUND")


def _valid_completion(evidence):
    if not COMPLETION_FIELDS.issubset(evidence):
        return False
    if not COMMIT_SHA.fullmatch(str(evidence["work_commit"])):
        return False
    if not COMMIT_SHA.fullmatch(str(evidence["completed_commit"])):
        return False
    return all(str(evidence[field]).strip() for field in COMPLETION_FIELDS)


def complete_active(queue: dict, module_id: str, evidence: dict) -> dict:
    item = _find_module(queue, module_id)
    if item.get("status") != "ACTIVE":
        raise ValueError("MODULE_NOT_ACTIVE")
    if not isinstance(evidence, dict):
        raise ValueError("EVIDENCE_INVALID")
    outcome = evidence.get("outcome")
    if outcome == "COMPLETED":
        if not _valid_completion(evidence):
            raise ValueError("COMPLETION_EVIDENCE_MISSING")
    elif outcome == "HOLD":
        blockers = evidence.get("blockers")
        if (
            not isinstance(blockers, list)
            or not blockers
            or not all(isinstance(value, str) and value.strip() for value in blockers)
            or not isinstance(evidence.get("validation_path"), str)
            or not evidence["validation_path"].strip()
        ):
            raise ValueError("HOLD_EVIDENCE_MISSING")
    else:
        raise ValueError("OUTCOME_INVALID")
    item["status"] = outcome
    item["evidence"] = evidence.copy()
    item.pop("claim", None)
    return item


def _write_queue(path, queue):
    temporary = path.with_suffix(path.suffix + ".tmp")
    temporary.write_text(
        json.dumps(queue, ensure_ascii=False, indent=2) + "\n", encoding="utf-8"
    )
    temporary.replace(path)


def main(argv=None):
    parser = argparse.ArgumentParser(description="Claim or finish the next reverse migration module.")
    parser.add_argument("queue", type=Path)
    subparsers = parser.add_subparsers(dest="command", required=True)
    claim_parser = subparsers.add_parser("claim")
    claim_parser.add_argument("--actor", required=True)
    finish_parser = subparsers.add_parser("finish")
    finish_parser.add_argument("--module-id", required=True)
    finish_parser.add_argument("--evidence", required=True, type=Path)
    args = parser.parse_args(argv)

    try:
        queue = json.loads(args.queue.read_text(encoding="utf-8"))
        if args.command == "claim":
            item = claim_next(queue, args.actor)
        else:
            evidence = json.loads(args.evidence.read_text(encoding="utf-8"))
            item = complete_active(queue, args.module_id, evidence)
        _write_queue(args.queue, queue)
    except (OSError, json.JSONDecodeError, ValueError) as error:
        print(str(error), file=sys.stderr)
        return 1
    print(
        f"QUEUE_STATE module_id={item['module_id']} "
        f"module_name={item['module_name']} status={item['status']}"
    )
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
