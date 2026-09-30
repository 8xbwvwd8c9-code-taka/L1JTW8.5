"""Explicit-intent lifecycle. No scheduling, targeting, or hunting policy."""
from enum import Enum

from .contracts import ContractError, ContractRegistry
from .interfaces import Adapter, DenyPolicy, Intent, Policy


class State(Enum):
    STOPPED = "stopped"
    READY = "ready"
    PAUSED = "paused"
    FAULTED = "faulted"


class BotController:
    def __init__(self, adapter: Adapter, registry: ContractRegistry,
                 policy: Policy | None = None):
        self.adapter = adapter
        self.registry = registry
        self.policy = policy if policy is not None else DenyPolicy()
        self.state = State.STOPPED
        self.last_error: str | None = None

    def start(self) -> None:
        if self.state is not State.STOPPED:
            raise ValueError("Start requires STOPPED")
        self.last_error = None
        self.state = State.READY

    def pause(self) -> None:
        if self.state is not State.READY:
            raise ValueError("Pause requires READY")
        self.state = State.PAUSED

    def resume(self) -> None:
        if self.state is not State.PAUSED:
            raise ValueError("Resume requires PAUSED")
        self.state = State.READY

    def stop(self) -> None:
        self.state = State.STOPPED

    def submit(self, intent: Intent,
               required_facts: tuple[tuple[str, str], ...] = ()) -> bool:
        if self.state is not State.READY:
            return False
        if (not isinstance(intent, Intent) or not isinstance(intent.capability, str)
                or not intent.capability.strip() or not required_facts):
            self.last_error = "Intent requires a capability and confirmed dependencies"
            return False
        try:
            for source, key in required_facts:
                self.registry.require(source, key)
        except (ContractError, ValueError, TypeError) as exc:
            self.last_error = str(exc)
            return False
        try:
            snapshot = self.adapter.observe()
            if not snapshot.available or self.policy.approve(intent, snapshot) is not True:
                return False
            success = self.adapter.perform(intent)
            if success is not True:
                self.last_error = "Adapter did not acknowledge intent"
                self.state = State.FAULTED
                return False
            self.last_error = None
            return True
        except Exception as exc:
            self.last_error = f"{type(exc).__name__}: {exc}"
            self.state = State.FAULTED
            return False
