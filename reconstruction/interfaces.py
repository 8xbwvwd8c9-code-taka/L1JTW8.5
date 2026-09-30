"""Semantic ports; implementations are supplied by the caller."""
from dataclasses import dataclass, field
from typing import Any, Mapping, Protocol


@dataclass(frozen=True)
class Intent:
    capability: str
    payload: Mapping[str, Any] = field(default_factory=dict)


@dataclass(frozen=True)
class Snapshot:
    available: bool
    data: Mapping[str, Any] = field(default_factory=dict)


class Adapter(Protocol):
    def observe(self) -> Snapshot: ...
    def perform(self, intent: Intent) -> bool: ...


class Policy(Protocol):
    def approve(self, intent: Intent, snapshot: Snapshot) -> bool: ...


class DenyPolicy:
    def approve(self, intent: Intent, snapshot: Snapshot) -> bool:
        return False
