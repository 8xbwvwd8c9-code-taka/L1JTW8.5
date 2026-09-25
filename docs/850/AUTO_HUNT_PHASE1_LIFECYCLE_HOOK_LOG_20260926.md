# L1JTW8.5 Auto Hunt Phase-1 Lifecycle Hook Log — 2026-09-26

- Branch: `work/850-auto-hunting`
- Scope: synchronous lifecycle stop hooks only
- Host authority: repaired 850 recovered source
- Auto-hunt owner: `auto.hunt.AutoHuntService`

## Completed hooks

### C_Disconnect

File: `recovered-src-obf/aj/ah.java`

Before `client.c()` the handler now reads the active player using `client.f()` and calls:

```java
AutoHuntService.stop(pc);
```

when the player is non-null.

Invariant: auto-hunt task cancellation/generation invalidation occurs before the client cleanup path begins.

### C_Restart

File: `recovered-src-obf/aj/bv.java`

Inside the existing synchronized player block and before `pc.p()` the handler now calls:

```java
AutoHuntService.stop(pc);
```

Invariant: restart cleanup cannot leave the registered auto-hunt session/task active.

## API correction found during verification

An initial hook draft incorrectly used `AutoHuntService.get().stop(pc)`. Fresh source verification showed `AutoHuntService` exposes static methods and has no `get()` method. Both lifecycle hooks were corrected to the actual API:

```java
AutoHuntService.stop(pc);
```

## Regression verifier

Added:

`tools/auto-hunt/validate_lifecycle_packet_hooks.py`

It verifies:

- `AutoHuntService.stop(u)` remains static,
- disconnect calls stop exactly once,
- disconnect stop occurs before `client.c()`,
- restart calls stop exactly once,
- restart stop occurs before `pc.p()`.

Fresh verifier result:

```text
AUTO_HUNT_PACKET_LIFECYCLE_HOOKS=PASS
```

The two modified packet handlers were also compiled against minimal host stubs; result:

```text
LIFECYCLE_PACKET_HOOK_COMPILE=PASS
```

## Death hook status

Authoritative death transition remains `ap.u.b(aq.f lastAttacker)`:

```text
synchronized(player)
  -> reject duplicate death
  -> X(true) / dead state
  -> death action state
then
  -> submit asynchronous death worker
```

The required auto-hunt stop point is at this synchronous transition, before the asynchronous death worker is submitted.

No smaller universally-correct death hook was proven. The normal physical attack path (`aq.c`) eventually calls player damage handling, but hooking there would not cover every death source and checking raw attack damage before host mitigation can create false stops. Therefore no unsafe partial death hook was added.

Current limitation: the available GitHub contents mutation API replaces complete files, while `ap/u.java` is a very large recovered source file and the connector does not expose a partial patch mutation. Reconstructing that file from truncated connector output would violate the source-authority rule. Death therefore remains an explicit blocker rather than receiving an unverified patch.

## Next

1. Obtain a safe full-file/patch write surface for `recovered-src-obf/ap/u.java`.
2. Insert exactly one `AutoHuntService.stop(this)` in the synchronous death transition before asynchronous death work submission.
3. Add death-hook static verifier and zombie-task regression.
4. Re-run lifecycle/session/service regression suite.
5. Only then continue to Target Search -> Move -> Basic Attack.
