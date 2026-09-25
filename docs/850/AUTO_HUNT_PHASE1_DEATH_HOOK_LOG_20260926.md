# L1JTW8.5 Auto Hunt Phase-1 Death Hook Log

Date: 2026-09-26
Branch: `work/850-auto-hunting`

## Goal

Close the remaining synchronous death-stop gap without modifying the large recovered `ap/u.java` death method directly.

## Authority / evidence

850 recovered core maps player death state through `aq.f`:

- `eX()` returns the shared dead flag `ap`.
- `X(boolean flag)` writes that dead flag.
- `ap.u.b(lastAttacker)` performs duplicate-death rejection and then enters the dead transition before queuing the remaining death work asynchronously.

Because `ap.u` extends `aq.f`, stopping auto-hunt inside `aq.f.X(true)` is synchronous with the authoritative dead-state transition and covers every player path that marks the shared dead flag, while `X(false)` revival does not stop anything.

## Implementation

Modified:

- `recovered-src-obf/aq/f.java`

Added host dependency:

```java
import auto.hunt.AutoHuntService;
```

Dead-state setter now performs:

```java
public void X(boolean flag) {
    if (flag && this instanceof u) {
        AutoHuntService.stop((u)this);
    }
    this.ap = flag;
}
```

This preserves NPC/non-player behavior and keeps revival (`flag == false`) untouched.

## Regression verifier

Added:

- `tools/auto-hunt/validate_death_state_hook.py`

The verifier requires:

- the `AutoHuntService` import,
- exactly one synchronous `AutoHuntService.stop((u)this)` call,
- that call to exist only inside the `flag && this instanceof u` death gate.

Existing `AutoHuntServiceTest` already verifies that service stop cancels the owned future and removes registry ownership, and that a replacement player object with the same objid cancels the stale session.

## Diff safety

A cumulative compare against pre-death-hook HEAD `5197096edb274aaa22ba74fa203b063cab4eea1e` was used after whole-file replacement.

Final semantic code delta in `aq/f.java` is only:

- one import,
- three-line player/death stop gate.

The compare also reports one deleted trailing blank line at EOF; no Java statement, field, method, string, collection type, or recovered behavior remains changed outside the intended hook. Earlier intermediate escaping/collection reconstruction mistakes were detected by commit diff inspection and corrected before closure.

## Lifecycle result

Phase-1 synchronous stop coverage now has explicit host hooks for:

- death: `aq.f.X(true)` -> `AutoHuntService.stop((u)this)`
- disconnect: `aj.ah` -> `AutoHuntService.stop(pc)` before `client.c()`
- restart: `aj.bv` -> `AutoHuntService.stop(pc)` before `pc.p()`

The session itself continues to guard dead/disconnected/teleport/map-change state on every tick as a second line of defense.

## Validation status

- Death hook source contract: PASS by branch source inspection / static verifier contract.
- Disconnect hook source contract: PASS.
- Restart hook source contract: PASS.
- Service stop cancellation/registry semantics: covered by existing regression test.
- Full recovered-core compile: NOT RUN in this connector session; the execution environment cannot clone/download the repository directly and GitHub connector exposes text mutations/inspection rather than a runnable checkout.

## Next

MAP-C lifecycle architecture can be treated as closed for source design/host-hook coverage. Next Phase-1 work is target selection, then basic movement and basic attack, continuing to use 850 native APIs and the single session/scheduler owner.
