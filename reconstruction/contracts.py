"""Read canonical CSV metadata without exposing addresses or native bindings."""
from __future__ import annotations

import csv
import hashlib
import io
import re
from dataclasses import dataclass
from pathlib import Path


class ContractError(ValueError):
    pass


SOURCES = {
    "functions": "name", "globals": "name", "ui_events": "event_name",
    "ui_windows": "window_name", "entity_resolution": "kind",
}
STATUSES = frozenset({"CANDIDATE", "CONFIRMED", "STABLE", "REJECTED", "UNKNOWN"})
# Deliberately narrow semantic surface for this work package.
SEMANTIC_ALLOWLIST = {
    "functions": frozenset({"bot_controller_state_singleton_getter", "bot_controller_get_target_id"}),
    "globals": frozenset({"bot_controller_state_singleton"}),
    "ui_events": frozenset({"BotOpenUI", "HandleKeyboard", "ShowSubMenu", "EnableSubMenuOption"}),
    "ui_windows": frozenset({"BotWindow"}),
    "entity_resolution": frozenset({"ENTITY_LOOKUP", "ENTITY_MANAGER", "ENTITY_ID_FIELD", "ENTITY_COORDS", "ENTITY_ACTION_QUEUE"}),
}


@dataclass(frozen=True)
class Fact:
    source: str
    key: str
    status: str
    evidence_ref: str
    target_sha256: str


@dataclass(frozen=True)
class ContractRegistry:
    facts: tuple[Fact, ...]
    target_sha256: str
    source_hashes: tuple[tuple[str, str], ...] = ()

    def __post_init__(self) -> None:
        _validate_sha(self.target_sha256)
        seen = set()
        for fact in self.facts:
            identity = (fact.source, fact.key)
            if (fact.source not in SOURCES or not fact.key.strip()
                    or fact.key not in SEMANTIC_ALLOWLIST.get(fact.source, ())
                    or fact.status not in {"CONFIRMED", "STABLE"}
                    or not fact.evidence_ref.strip()
                    or fact.target_sha256.upper() != self.target_sha256.upper()
                    or identity in seen):
                raise ContractError("Invalid or duplicate confirmed fact")
            seen.add(identity)

    def require(self, source: str, key: str) -> Fact:
        for fact in self.facts:
            if (fact.source, fact.key) == (source, key):
                return fact
        raise ContractError(f"Confirmed contract unavailable: {source}/{key}")


def _validate_sha(value: str) -> None:
    if not isinstance(value, str) or not re.fullmatch(r"[a-fA-F0-9]{64}", value):
        raise ContractError("Expected target SHA256 must contain 64 hexadecimal digits")


def load_confirmed_maps(root: Path, expected_sha256: str) -> ContractRegistry:
    """Load metadata only; approval is inherited from canonical source statuses.

    The caller pins the target digest. This does not inspect any client binary,
    establish an ABI, validate native callability, or promote source facts.
    """
    _validate_sha(expected_sha256)
    facts = []
    hashes = []
    for source, key_column in SOURCES.items():
        path = Path(root) / f"{source}.csv"
        raw = path.read_bytes()
        hashes.append((path.name, hashlib.sha256(raw).hexdigest()))
        reader = csv.DictReader(io.StringIO(raw.decode("utf-8-sig")), strict=True)
        required = {key_column, "status", "evidence_ref", "target_sha256"}
        if (not reader.fieldnames or len(set(reader.fieldnames)) != len(reader.fieldnames)
                or not required.issubset(reader.fieldnames)):
            raise ContractError(f"Invalid CSV headers: {path.name}")
        seen = set()
        try:
            for row in reader:
                if None in row or any(value is None for value in row.values()):
                    raise ContractError(f"Malformed CSV row: {path.name}")
                status = row["status"].strip()
                key = row[key_column].strip()
                sha = row["target_sha256"].strip()
                evidence = row["evidence_ref"].strip()
                if status not in STATUSES or not key or not evidence:
                    raise ContractError(f"Invalid metadata: {path.name}")
                _validate_sha(sha)
                if sha.upper() != expected_sha256.upper():
                    raise ContractError(f"Target SHA mismatch: {path.name}")
                if status not in {"CONFIRMED", "STABLE"}:
                    continue
                # Out-of-scope mechanics are never offered as controller dependencies.
                if key not in SEMANTIC_ALLOWLIST[source]:
                    continue
                if key in seen:
                    raise ContractError(f"Duplicate promoted semantic key: {path.name}/{key}")
                seen.add(key)
                facts.append(Fact(source, key, status, evidence, sha.upper()))
        except csv.Error as exc:
            raise ContractError(f"Malformed CSV: {path.name}") from exc
    return ContractRegistry(tuple(facts), expected_sha256.upper(), tuple(hashes))
