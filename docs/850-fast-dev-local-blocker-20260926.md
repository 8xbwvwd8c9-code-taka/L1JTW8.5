# L1JTW8.5 Fast Dev — Windows Local Blocker 2026-09-26

Branch: `work/l1jtw85-fast-dev-build`

## Status

```text
ERROR=ERROR-LOCAL-005
AREA=Fast Dev local startup / generated Dev Base replacement
STATUS=BLOCKED
CLIENT_GATE=NOT_STARTED
PRODUCTION_JAR=UNCHANGED
```

## Current failing local behavior

After pulling the Fast Dev startup/cache fixes and launching `ServerStart.bat`, Windows still fails while replacing a stale generated Dev Base:

```text
Traceback (most recent call last):
  File "I:\L1JTW8.5\tools\850\fast_dev.py", line 295, in <module>
    raise SystemExit(main())
  File "I:\L1JTW8.5\tools\850\fast_dev.py", line 282, in main
    compiler = _compiler(root)
  File "I:\L1JTW8.5\tools\850\fast_dev.py", line 201, in _compiler
    _ensure_baseline(root)
  File "I:\L1JTW8.5\tools\850\fast_dev.py", line 171, in _ensure_baseline
    _unlink_with_retry(dev_base)
  File "I:\L1JTW8.5\tools\850\fast_dev.py", line 155, in _unlink_with_retry
    path.unlink()
PermissionError: [WinError 5] 存取被拒。: 'I:\\L1JTW8.5\\.build850\\cache\\850-dev-base.jar'
```

The failure occurs before Java runtime startup, before port 2000, and before the 8.50c client gate.

## Evidence collected on the affected Windows host

Target file:

```text
I:\L1JTW8.5\.build850\cache\850-dev-base.jar
Length=5874688
Attributes=Archive
IsReadOnly=False
LastWriteTime=2026/9/25 23:47:37
```

At diagnostic time:

```text
JAVA_OR_PYTHON_PROCESS_MATCH=NONE
PORT_2000_LISTENER=NONE
EXCLUSIVE_OPEN=PASS
TEMP_DELETE_IN_CACHE_DIR=PASS
RENAME_OUT=PASS
RENAME_BACK=PASS
```

ACL evidence:

```text
FILE_OWNER=BUILTIN\Administrators
FILE_OWNER_RIGHTS=FullControl
SYSTEM=FullControl
ADMINISTRATORS=FullControl
CACHE_DIRECTORY_CURRENT_USER/MAPPED_SIDS=Modify,Synchronize
AUTHENTICATED_USERS=Modify,Synchronize
```

Therefore the observed failure is not explained by a permanent read-only flag, a persistent Java/Python process, or an obvious directory-wide inability to create/delete files.

## TDD history

Initial stale-cache validation fix:

```text
COMMIT=165175611ad96594fa9b96534edbf60c43a796f6
BEHAVIOR=850-dev-base.jar must contain l1j/server/Server.class to be considered ready
```

The first local Windows failure then occurred on direct `dev_base.unlink()` with `WinError 5`.

Transient-lock hypothesis test:

```text
RED_COMMIT=8801c5291c2ad25d7a9a0742b200e5f4decdc9e3
MAIN_RUN=221
RUN_ID=36217170269
RESULT=FAIL_AS_EXPECTED
FAILURE=test_stale_dev_base_retries_transient_windows_delete_denial
```

Retry implementation:

```text
GREEN_COMMIT=be83ea61f80c2eb9dacb8a0cff6ea6c44ec15844
MAIN_RUN=222
RUN_ID=36217261271
STATUS=PASS
FULL_COMPILE_RUN=45
RUN_ID=36217261261
STATUS=PASS
```

CI proves the synthetic one-shot `PermissionError` retry contract, bootstrap, compile, MySQL 5.7, DB import, DB-backed runtime smoke, and full readable compile remain green at `be83ea61...`.

However, the real Windows host still exhausts `_unlink_with_retry()` and throws `WinError 5`. Therefore `be83ea61...` is **not locally validated** for this failure mode.

## Important correction to prior hypothesis

Do not treat the problem as solved merely because:

```text
EXCLUSIVE_OPEN=PASS
RENAME_OUT=PASS
RENAME_BACK=PASS
```

Those tests show that the file can be manipulated manually at diagnostic time. They do not prove that the Python `unlink()` path can successfully delete it during Fast Dev bootstrap. The actual local failure persisted after retry support was added.

## Next investigation target

Continue systematic debugging from the Python delete boundary. Do not broaden into gameplay/core logic yet.

Recommended next evidence, in order:

1. Reproduce deletion with the same Python 3.13 interpreter outside Fast Dev using `Path.unlink()` against the exact file.
2. Compare `Path.unlink()` vs `os.remove()` vs rename-then-delete on the same file.
3. Capture `PermissionError.winerror`, `errno`, retry count, elapsed time, and file existence/attributes on every attempt.
4. If rename succeeds while unlink consistently fails, consider an atomic quarantine/rename strategy for stale generated cache instead of requiring immediate deletion.
5. Check whether cache-key deletion behaves differently from JAR deletion.
6. Only after the generated baseline can be rebuilt locally should `ServerStart.bat` proceed to Java/MySQL/runtime/client validation.

Do not ask the user to delete all `.build850` as the permanent solution; Fast Dev should recover stale generated state itself.

## Client gate remains blocked

```text
ACCOUNT_LOGIN=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
CHAR_SELECT=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
ENTER_GAME=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
```

No client PASS may be inferred until Windows Fast Dev server startup reaches a live port 2000 and the unchanged 8.50c client is manually tested.
