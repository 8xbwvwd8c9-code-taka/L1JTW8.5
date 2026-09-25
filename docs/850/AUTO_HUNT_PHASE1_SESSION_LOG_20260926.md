# L1JTW8.5 Auto Hunt Phase-1 Session Log — 2026-09-26

Branch: `work/850-auto-hunting`

## Scope

This entry records the first production-facing Phase-1 runtime primitives after MAP-C. It intentionally does not add target selection, movement, attack, skill, consumable, loot, safety-teleport policy, or UI behavior.

## 850 authority used

- `recovered-src-obf/ap/u.java` (`L1PcInstance`)
  - `aK()` -> current `ClientThread`; null means detached/offline.
  - `eX()` -> native dead state.
  - `ea()` -> current HP; `<=0` is also treated as terminal for auto-hunt.
  - `aR()` -> native teleport-in-progress state. `d(boolean)` is the matching setter used by `L1Teleport`.
  - `fp()` -> current map id.
  - `fr()` -> object id used by the service ownership registry.
- `recovered-src-obf/bi/e.java` (`GeneralThreadPool`)
  - player scheduled pool: `bi.e.a().b(Runnable, initialDelay, period)`.
  - returns `ScheduledFuture<?>`, matching 850's existing player-owned cancellation pattern.

## New runtime files

### `recovered-src-obf/auto/hunt/AutoHuntLifecycle.java`

Single lifecycle generation + action generation state machine.

Invariants:

- start/start is idempotent;
- stop/stop is idempotent;
- stop invalidates stale session tokens;
- restart gets a newer session generation;
- map changes invalidate delayed action generation without necessarily stopping the session.

### `recovered-src-obf/auto/hunt/AutoHuntSession.java`

Owns exactly one `ScheduledFuture<?>` for one auto-hunt session.

Per tick gates:

1. session token still current;
2. host still connected;
3. host not dead;
4. host not teleporting;
5. observe current map and invalidate delayed action generation on map change;
6. invoke the current phase tick only after all gates pass.

Stale scheduled callbacks from an older session generation return without stopping or executing a newer session.

### `recovered-src-obf/auto/hunt/AutoHunt850Session.java`

850 adapter that binds `AutoHuntSession` to native `ap.u` and `bi.e` methods.

Exact bindings:

```text
connected    -> pc.aK() != null
dead         -> pc.eX() || pc.ea() <= 0
teleporting  -> pc.aR()
map id       -> pc.fp()
scheduler    -> bi.e.a().b(task, initialDelay, period)
```

`onTick()` is intentionally empty in this phase. Combat/movement/skill behavior is not yet copied from L381.

### `recovered-src-obf/auto/hunt/AutoHuntService.java`

Single authoritative service ownership layer.

Ownership key is player object id plus object identity:

- same `L1PcInstance` start is idempotent and reuses the same running session;
- a different `L1PcInstance` with the same objid is treated as reconnect/replacement and the stale session is stopped before replacement;
- stop only affects the exact PC object that owns the registry entry;
- no L381 global move/magic/safe/summon timer topology is introduced.

Default Phase-1 tick period: `200 ms`.

## Regression tests

- `tools/auto-hunt/AutoHuntLifecycleTest.java`
- `tools/auto-hunt/AutoHuntSessionTest.java`
- `tools/auto-hunt/AutoHunt850SessionTest.java`
- `tools/auto-hunt/AutoHuntServiceTest.java`

TDD RED evidence observed before implementation:

- lifecycle test failed while `AutoHuntLifecycle` was absent;
- session test: `javac` 5 symbol errors while `AutoHuntSession` was absent;
- 850 adapter test: `javac` 3 symbol errors while `AutoHunt850Session` was absent;
- service test: `javac` 11 symbol errors while `AutoHuntService` was absent.

GREEN evidence observed after minimal implementations:

```text
AUTO_HUNT_LIFECYCLE_TEST=PASS
AUTO_HUNT_SESSION_TEST=PASS
AUTO_HUNT_850_SESSION_TEST=PASS
AUTO_HUNT_SERVICE_TEST=PASS
```

## Commits in this phase

```text
d12b318  test: define auto-hunt session ownership gates
acec875  feat: add single-owner auto-hunt session
38830cc  test: define 850 auto-hunt host adapter gates
76e8721  feat: bind auto-hunt session to 850 player state
34a4a52  test: define single auto-hunt service ownership
7e0e280  feat: add single auto-hunt session service
```

Earlier supporting commits on the same branch:

```text
325123a  docs: map 850 auto-hunt lifecycle scheduler
8f49109  chore: import repaired core source for auto-hunt
beb3993  test: add auto-hunt lifecycle regression
 db7aa45 feat: add auto-hunt lifecycle state machine
```

## Lifecycle hook status

The service stop primitive now exists, but the large recovered host files have not yet been rewritten in this connector session.

Required next insertion points are already mapped:

1. synchronous death transition `ap.u.b(lastAttacker)` -> `AutoHuntService.stop(this)` before asynchronous death work is queued;
2. full cleanup `ap.u.p()` -> `AutoHuntService.stop(this)` before player/world/client teardown;
3. native teleport commit `aq.am.a(pc)` -> stop or explicit stale-action invalidation before map/object transfer, according to the Phase-2 teleport policy;
4. explicit UI/user Stop packet -> `AutoHuntService.stop(pc)`.

Until those insertion points are patched, periodic gates protect the running task from acting while disconnected/dead/teleporting, but lifecycle teardown is not yet synchronous at the host transition itself.

## Validation boundary

PASS in this entry means the isolated lifecycle/session/service ownership regressions passed. It does **not** mean the entire recovered 850 source tree or production JAR has been rebuilt in this step.

## Blockers / hygiene note

During this session a connector misuse accidentally created `work/850-auto-hunting-temp-DO-NOT-CREATE`. No code or documentation was written on that branch. The currently exposed GitHub connector has no delete-ref action, so it must be deleted with a Git client/GitHub UI/Codex that has ref deletion capability. All actual work remains on `work/850-auto-hunting`.

## Next

1. patch death + `p()` cleanup hooks to call the single service stop primitive;
2. add a static/regression verifier proving both hooks are present exactly once;
3. verify no stale task survives death/disconnect/restart;
4. only then begin basic target-search / movement / attack mapping onto 850 native APIs.
