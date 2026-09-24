# L1JTW8.5 Production Rebuild — Local Reproduction / Login Runbook

## Authority and safety boundary

```text
LOCAL_REPO=I:\L1JTW8.5
UNCHANGED_CLIENT_BINARY=I:\8.50c客服端\Lin.bin2
COMPLETED_REF=3d4a392593d61c9203e3f22a154e752c2c51fa97
ORIGINAL_JAR=I:\L1JTW8.5\l1jserver2.jar
ORIGINAL_SHA256=8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
TEST_JAR=I:\L1JTW8.5\recovery\production-build\l1jserver2.repaired-test.jar
```

Never overwrite `l1jserver2.jar`; this runbook validates a separate `l1jserver2.repaired-test.jar` only.

Do not change the client, DB schema, launcher, port, or production JAR as part of this validation. The local driver uses the current authoritative `config/server.properties` and does not rewrite it.

## Prerequisites

- Windows PowerShell 7+.
- Python available as `python`.
- Java 8 `java` and Java 8 `javac` on `PATH`; `javac -version` must report `1.8.x`.
- Local MySQL runtime and the existing authoritative game database are already available according to the current `config/server.properties`.
- The JDBC `URL=` in `config/server.properties` must already include `useSSL=false`; current Java 8 plus the bundled legacy MySQL JDBC driver can fail TLS negotiation otherwise.
- Nothing else may already be listening on **port 2000** when starting the repaired test server.
- Keep `I:\8.50c客服端\Lin.bin2` unchanged for the final client gate.

## 1. Build the repaired test JAR

From PowerShell:

```powershell
Set-Location 'I:\L1JTW8.5'
pwsh -File '.\tools\production-rebuild\build-production-test.ps1'
```

The driver performs these fail-closed checks:

1. verifies `l1jserver2.jar` exists and SHA-256 equals `8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814`;
2. ensures completed repair authority `3d4a392593d61c9203e3f22a154e752c2c51fa97` is available;
3. requires Java 8 `javac`;
4. runs `tools\production-rebuild\run_production_rebuild.py`;
5. builds only `recovery\production-build\l1jserver2.repaired-test.jar`;
6. re-hashes the original production JAR and fails if one byte changed;
7. verifies `PIPELINE_RESULT.json` reports structural PASS and the pinned completed authority.

Expected terminal evidence includes:

```text
ORIGINAL_JAR_UNCHANGED=YES
BUILD_STRUCTURAL_GATE=PASS
TEST_JAR=I:\L1JTW8.5\recovery\production-build\l1jserver2.repaired-test.jar
RUNTIME_STARTUP_GATE=NOT_RUN
REAL_CLIENT_LOGIN_GATE=NOT_RUN
```

Do not continue if any of those build/safety checks fail.

## 2. Start the repaired test server without replacing production

First stop any existing L1JTW8.5 server so port 2000 is free. Then run:

```powershell
Set-Location 'I:\L1JTW8.5'
pwsh -File '.\tools\production-rebuild\build-production-test.ps1' -StartServer
```

`-StartServer` launches the repaired test JAR directly. It does not copy, move, rename, delete, or replace `l1jserver2.jar`.

The local startup gate requires the Java process to remain alive and to own **port 2000**. It writes:

```text
I:\L1JTW8.5\recovery\production-build\LOCAL_RUNTIME.log
I:\L1JTW8.5\recovery\production-build\LOCAL_RUNTIME.err.log
I:\L1JTW8.5\recovery\production-build\LOCAL_RUNTIME.pid
```

Expected startup evidence:

```text
RUNTIME_STARTUP_GATE=PASS
PORT_2000=LISTENING
REAL_CLIENT_LOGIN_GATE=MANUAL_PENDING
```

Also inspect `LOCAL_RUNTIME.log`. Before attempting the client gate, the normal startup path must reach the same runtime milestones as the authoritative server, including **C3P0** / DB initialization, map loading, sprite/resource initialization where logged, mob spawning, server initialization, and waiting for client connections. A process merely remaining alive is not enough.

## 3. Unchanged 8.50c client gate

Use the existing client installation and leave `I:\8.50c客服端\Lin.bin2` unchanged.

The required sequence is deliberately explicit:

```text
account login -> character select -> enter-game
```

Pass criteria:

- **account login**: the normal account reaches the character list without a disconnect/crash;
- **character select**: the existing character can be selected and the server accepts the session transition;
- **enter-game**: the character reaches the game world and remains connected long enough to confirm the normal world session;
- server log shows no new class linkage, verifier, `NoClassDefFoundError`, `NoSuchMethodError`, DB-loader, or protocol exception caused by the repaired JAR.

A successful TCP connection alone is not LOGIN PASS. CI already proves startup/port reachability; this local gate proves the unchanged 8.50c account/session path.

## 4. Record the local result

Copy the following block into the production-rebuild handoff/status record after the manual client run:

```text
LOCAL_RUNTIME_DATE=YYYY-MM-DD
LOCAL_REPO=I:\L1JTW8.5
ORIGINAL_SHA256=8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
ORIGINAL_JAR_UNCHANGED=YES
TEST_JAR_SHA256=<from build-production-test.ps1>
C3P0_DB_INIT=PASS|FAIL
MAP_INIT=PASS|FAIL
SPR_RESOURCE_INIT=PASS|FAIL|NOT_LOGGED
MOB_INIT=PASS|FAIL
PORT_2000=PASS|FAIL
ACCOUNT_LOGIN=PASS|FAIL
CHARACTER_SELECT=PASS|FAIL
ENTER_GAME=PASS|FAIL
SERVER_EXCEPTION=NONE|<first concrete exception>
REAL_CLIENT_LOGIN_GATE=PASS|FAIL
```

Only mark `REAL_CLIENT_LOGIN_GATE=PASS` when account login, character select, and enter-game all pass with the unchanged client.

## 5. Stop the repaired test server

After the local login check:

```powershell
$pidFile = 'I:\L1JTW8.5\recovery\production-build\LOCAL_RUNTIME.pid'
if (Test-Path $pidFile) {
    $serverPid = [int](Get-Content $pidFile -Raw)
    Stop-Process -Id $serverPid -ErrorAction SilentlyContinue
}
```

This stops only the repaired-test Java process recorded by the local driver. It does not touch the production JAR.

## 6. Final classification

```text
BUILD_GATE=PASS           # already automated
STRUCTURAL_GATE=PASS      # already automated
DB_STARTUP_GATE=PASS      # CI smoke already automated; local must remain consistent
RUNTIME_STARTUP_GATE=PASS # local repaired-test server must reach port 2000
REAL_CLIENT_LOGIN_GATE=PASS|NOT_RUN|FAIL
FUNCTIONAL_GAMEPLAY_GATE=NOT_RUN until targeted repair checks are exercised
```

The rebuild task is **PARTIAL** while `REAL_CLIENT_LOGIN_GATE` is `NOT_RUN`. It may be reported as final rebuild PASS only after the unchanged 8.50c client completes account login, character select, and enter-game successfully.
