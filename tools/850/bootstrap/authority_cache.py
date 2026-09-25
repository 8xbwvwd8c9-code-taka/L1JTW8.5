#!/usr/bin/env python3
from __future__ import annotations

import importlib.util
import json
import re
import shutil
import subprocess
import tarfile
import tempfile
from pathlib import Path


HERE = Path(__file__).resolve().parent
# Historical authority retained for callers that intentionally request the old
# production-rebuild baseline. Normal Fast Dev bootstrap resolves the latest
# completed branch tip and pins that exact SHA for the current cache.
PINNED_COMPLETED_COMMIT = "fc473aef65485d1524283fa34d01ab7fad9a7b93"
# Last normalized recovery-generation commit before completed repair promotions.
# It is the immutable lower bound for promotion-history traversal.
RECOVERY_BASELINE_COMMIT = "f49015ff55120eb414f1feba0b268bbb51484e16"
NORMALIZED_SOURCE_ROOT = "recovery/normalized-src-vf"
COMPLETED_BRANCH = "completed/l1jtw85-core-fixes"
REMOTE_COMPLETED_REF = f"refs/remotes/origin/{COMPLETED_BRANCH}"
LOCAL_COMPLETED_REF = f"refs/heads/{COMPLETED_BRANCH}"
COMMIT_RE = re.compile(r"^[0-9a-fA-F]{40}$")
PROMOTION_RE = re.compile(
    r"^(?:"
    r"fix\(l[123]\):\s+(?:promote|complete)\b"
    r"|promote\(l[123]\):\s+"
    r"|BUG-\d+-\d+(?:/\d+)*\s+promote\b"
    r")",
    re.IGNORECASE,
)
BUG_ID_RE = re.compile(r"\bBUG-(\d+)-(\d+(?:/\d+)*)\b", re.IGNORECASE)
AUTHORITY_CACHE_SCHEMA_VERSION = 2
ARCHIVE_PATHS = (
    "recovery/source_namespace_map.csv",
    "recovery/source_namespace_state.json",
    NORMALIZED_SOURCE_ROOT,
)


def _load_local(filename: str, module_name: str):
    path = HERE / filename
    spec = importlib.util.spec_from_file_location(module_name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


_MIGRATE = _load_local("migrate_core.py", "fast_dev_authority_migrate_core")


def _git(repo_root: Path, *args: str, check: bool = True) -> subprocess.CompletedProcess[str]:
    proc = subprocess.run(
        ["git", *args],
        cwd=repo_root,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    if check and proc.returncode != 0:
        detail = proc.stderr.strip() or proc.stdout.strip() or "git command failed"
        raise RuntimeError(detail)
    return proc


def _exact_commit(value: str, label: str) -> str:
    commit = str(value).strip()
    if not COMMIT_RE.fullmatch(commit):
        raise ValueError(f"{label} must be an exact 40-hex SHA")
    return commit.lower()


def _commit_available(repo_root: Path, commit: str) -> bool:
    proc = _git(repo_root, "cat-file", "-e", f"{commit}^{{commit}}", check=False)
    return proc.returncode == 0


def _resolve_ref(repo_root: Path, ref: str) -> str | None:
    proc = _git(repo_root, "rev-parse", "--verify", f"{ref}^{{commit}}", check=False)
    if proc.returncode != 0:
        return None
    value = proc.stdout.strip()
    return value if len(value) == 40 else None


def resolve_completed_authority_commit(
    repo_root: Path,
    *,
    fetch_latest: bool = True,
) -> str:
    """Resolve the completed repair authority to one exact commit SHA.

    When fetch_latest is enabled, the completed branch is fetched into an
    explicit remote-tracking ref and a fetch failure is fatal. The active HEAD
    is never considered, so work/in-progress commits cannot become authority.
    """
    repo_root = Path(repo_root).resolve()

    if fetch_latest:
        proc = _git(
            repo_root,
            "fetch",
            "--no-tags",
            "origin",
            f"+refs/heads/{COMPLETED_BRANCH}:{REMOTE_COMPLETED_REF}",
            check=False,
        )
        if proc.returncode != 0:
            detail = proc.stderr.strip() or proc.stdout.strip() or "git fetch failed"
            raise RuntimeError(
                f"cannot refresh completed authority branch {COMPLETED_BRANCH}: {detail}"
            )

    refs = (REMOTE_COMPLETED_REF, LOCAL_COMPLETED_REF)
    for ref in refs:
        commit = _resolve_ref(repo_root, ref)
        if commit is not None:
            return commit

    raise RuntimeError(
        "completed authority branch unavailable: "
        f"{COMPLETED_BRANCH}; fetch the branch and retry"
    )


def ensure_completed_authority_commit(
    repo_root: Path,
    *,
    commit: str = PINNED_COMPLETED_COMMIT,
    fetch_if_missing: bool = True,
) -> None:
    repo_root = Path(repo_root).resolve()
    commit = _exact_commit(commit, "completed authority commit")
    if _commit_available(repo_root, commit):
        return

    if fetch_if_missing:
        _git(
            repo_root,
            "fetch",
            "--no-tags",
            "origin",
            f"{COMPLETED_BRANCH}:refs/remotes/origin/{COMPLETED_BRANCH}",
            check=False,
        )

    if not _commit_available(repo_root, commit):
        raise RuntimeError(
            "completed authority commit unavailable: "
            f"{commit}; fetch {COMPLETED_BRANCH} and retry"
        )


def ensure_recovery_baseline_commit(
    repo_root: Path,
    *,
    baseline_commit: str = RECOVERY_BASELINE_COMMIT,
    fetch_if_missing: bool = True,
) -> str:
    """Ensure the immutable recovery baseline commit is locally reachable.

    Normal full clones pay no fetch cost. A shallow clone is unshallowed from
    the completed authority branch only when the baseline anchor is missing.
    """
    repo_root = Path(repo_root).resolve()
    baseline = _exact_commit(baseline_commit, "recovery baseline commit")
    if _commit_available(repo_root, baseline):
        return baseline
    if not fetch_if_missing:
        raise RuntimeError(f"recovery baseline commit unavailable: {baseline}")

    shallow = _git(repo_root, "rev-parse", "--is-shallow-repository", check=False)
    is_shallow = shallow.returncode == 0 and shallow.stdout.strip().lower() == "true"
    command = ["fetch", "--no-tags"]
    if is_shallow:
        command.append("--unshallow")
    command.extend(
        [
            "origin",
            f"+refs/heads/{COMPLETED_BRANCH}:{REMOTE_COMPLETED_REF}",
        ]
    )
    proc = _git(repo_root, *command, check=False)
    if proc.returncode != 0:
        detail = proc.stderr.strip() or proc.stdout.strip() or "git fetch failed"
        raise RuntimeError(
            f"cannot recover baseline history from {COMPLETED_BRANCH}: {detail}"
        )
    if not _commit_available(repo_root, baseline):
        raise RuntimeError(
            "recovery baseline commit unavailable after completed-branch history fetch: "
            f"{baseline}"
        )
    return baseline


def _promotion_history(repo_root: Path, baseline: str, completed: str) -> list[tuple[str, str]]:
    history = _git(
        repo_root,
        "log",
        "--reverse",
        "--topo-order",
        "--format=%H%x09%s",
        f"{baseline}..{completed}",
    )
    records: list[tuple[str, str]] = []
    for line in history.stdout.splitlines():
        if not line.strip() or "\t" not in line:
            continue
        sha, subject = line.split("\t", 1)
        subject = subject.strip()
        if PROMOTION_RE.match(subject):
            records.append((_exact_commit(sha, "completed repair commit"), subject))
    return records


def _promotion_commits(repo_root: Path, baseline: str, completed: str) -> list[str]:
    return [sha for sha, _ in _promotion_history(repo_root, baseline, completed)]


def _promotion_bug_ids(subject: str) -> set[str]:
    bug_ids: set[str] = set()
    for match in BUG_ID_RE.finditer(subject):
        family = match.group(1)
        for issue in match.group(2).split("/"):
            bug_ids.add(f"BUG-{family}-{issue}")
    return bug_ids


def _promotion_java_changes(repo_root: Path, promotion_commit: str) -> list[tuple[str, str]]:
    diff = _git(
        repo_root,
        "show",
        "--format=",
        "--name-status",
        "--no-renames",
        promotion_commit,
        "--",
        NORMALIZED_SOURCE_ROOT,
    )
    changes: list[tuple[str, str]] = []
    for raw_line in diff.stdout.splitlines():
        if not raw_line.strip():
            continue
        fields = raw_line.split("\t")
        if len(fields) != 2:
            raise RuntimeError(f"malformed promotion normalized source record: {raw_line}")
        status, path = fields
        if not path.endswith(".java"):
            continue
        changes.append((status, path))
    return changes


def _validate_promotion_paths(
    repo_root: Path,
    promotion_commit: str,
) -> list[str]:
    paths: set[str] = set()
    for status, path in _promotion_java_changes(repo_root, promotion_commit):
        code = status[:1]
        if code == "D":
            raise RuntimeError(f"deleted normalized source is not overlay-safe: {path}")
        if code not in {"A", "M"}:
            raise RuntimeError(
                f"unsupported normalized source promotion status {status}: {path}"
            )
        paths.add(path)
    return sorted(paths)


def completed_repair_source_scopes(
    repo_root: Path,
    *,
    commit: str,
    baseline_commit: str = RECOVERY_BASELINE_COMMIT,
    fetch_if_missing: bool = True,
) -> list[dict[str, list[str]]]:
    """Return atomic completed repair source scopes in promotion history order.

    Each authoritative promotion commit starts as one scope. If later promotions
    touch a source already present in an earlier scope, or carry a BUG identity
    already present in that scope, those scopes are merged transitively. This
    preserves the smallest atomic compile/publication boundary required by both
    overlapping source edits and split commits belonging to one completed repair.
    """
    repo_root = Path(repo_root).resolve()
    completed = _exact_commit(commit, "completed authority commit")
    baseline = ensure_recovery_baseline_commit(
        repo_root,
        baseline_commit=baseline_commit,
        fetch_if_missing=fetch_if_missing,
    )

    if not _commit_available(repo_root, completed):
        raise RuntimeError(f"completed authority commit unavailable: {completed}")

    ancestor = _git(
        repo_root,
        "merge-base",
        "--is-ancestor",
        baseline,
        completed,
        check=False,
    )
    if ancestor.returncode != 0:
        raise RuntimeError(
            "recovery baseline is not an ancestor of completed authority: "
            f"{baseline} -> {completed}"
        )

    scopes: list[dict[str, list[str]]] = []
    scope_bug_ids: list[set[str]] = []
    for promotion_commit, subject in _promotion_history(repo_root, baseline, completed):
        paths = _validate_promotion_paths(repo_root, promotion_commit)
        if not paths:
            continue
        path_set = set(paths)
        bug_ids = _promotion_bug_ids(subject)
        overlapping = [
            index
            for index, scope in enumerate(scopes)
            if path_set.intersection(scope["source_paths"])
            or (bug_ids and bug_ids.intersection(scope_bug_ids[index]))
        ]
        if not overlapping:
            scopes.append(
                {
                    "commits": [promotion_commit],
                    "source_paths": paths,
                }
            )
            scope_bug_ids.append(set(bug_ids))
            continue

        first = overlapping[0]
        merged_commits: list[str] = []
        merged_paths = set(paths)
        merged_bug_ids = set(bug_ids)
        overlap_set = set(overlapping)
        retained: list[dict[str, list[str]]] = []
        retained_bug_ids: list[set[str]] = []
        for index, scope in enumerate(scopes):
            if index in overlap_set:
                merged_commits.extend(scope["commits"])
                merged_paths.update(scope["source_paths"])
                merged_bug_ids.update(scope_bug_ids[index])
            else:
                retained.append(scope)
                retained_bug_ids.append(scope_bug_ids[index])
        merged_commits.append(promotion_commit)
        merged_scope = {
            "commits": merged_commits,
            "source_paths": sorted(merged_paths),
        }
        retained.insert(first, merged_scope)
        retained_bug_ids.insert(first, merged_bug_ids)
        scopes = retained
        scope_bug_ids = retained_bug_ids

    return scopes


def completed_repair_source_paths(
    repo_root: Path,
    *,
    commit: str,
    baseline_commit: str = RECOVERY_BASELINE_COMMIT,
    fetch_if_missing: bool = True,
) -> list[str]:
    """Return the flat union of formally completed normalized Java sources."""
    scopes = completed_repair_source_scopes(
        repo_root,
        commit=commit,
        baseline_commit=baseline_commit,
        fetch_if_missing=fetch_if_missing,
    )
    return sorted(
        {
            path
            for scope in scopes
            for path in scope["source_paths"]
        }
    )


def _cache_hit(cache_core: Path, commit: str, baseline_commit: str) -> bool:
    marker = cache_core / "PINNED_AUTHORITY.json"
    source_root = cache_core / "src"
    if not marker.is_file() or not source_root.is_dir():
        return False
    try:
        payload = json.loads(marker.read_text(encoding="utf-8"))
    except (OSError, json.JSONDecodeError):
        return False
    return (
        payload.get("schema") == AUTHORITY_CACHE_SCHEMA_VERSION
        and payload.get("commit") == commit
        and payload.get("baseline_commit") == baseline_commit
        and any(source_root.rglob("*.java"))
    )


def _overlay_promoted_sources(
    repo_root: Path,
    authority_root: Path,
    *,
    completed_commit: str,
    promoted_sources: list[str],
) -> None:
    for relative in promoted_sources:
        proc = _git(repo_root, "show", f"{completed_commit}:{relative}")
        target = authority_root / relative
        target.parent.mkdir(parents=True, exist_ok=True)
        target.write_text(proc.stdout, encoding="utf-8")


def materialize_authority_core(
    repo_root: Path,
    cache_core: Path,
    *,
    commit: str | None = None,
    baseline_commit: str | None = None,
    fetch_if_missing: bool = True,
) -> dict[str, object]:
    """Materialize semantic core from exact Git authorities, never the worktree.

    ``commit`` is the exact completed-repair authority. When ``baseline_commit`` is
    supplied, the normalized tree starts from that recovery baseline and only Java
    files belonging to formal completed repair commits are overlaid from ``commit``.
    This is the normal Fast Dev policy: completed repairs win; unrepaired/in-progress
    cores remain at the recovery baseline. When ``baseline_commit`` is omitted the
    historical whole-completed-commit materialization behavior is retained for
    explicit legacy callers.
    """
    repo_root = Path(repo_root).resolve()
    cache_core = Path(cache_core).resolve()
    if commit is None:
        commit = resolve_completed_authority_commit(
            repo_root,
            fetch_latest=fetch_if_missing,
        )
    commit = _exact_commit(commit, "completed authority commit")

    ensure_completed_authority_commit(
        repo_root,
        commit=commit,
        fetch_if_missing=fetch_if_missing,
    )

    if baseline_commit is None:
        baseline = commit
        promoted_sources: list[str] = []
        archive_commit = commit
    else:
        baseline = ensure_recovery_baseline_commit(
            repo_root,
            baseline_commit=baseline_commit,
            fetch_if_missing=fetch_if_missing,
        )
        promoted_sources = completed_repair_source_paths(
            repo_root,
            commit=commit,
            baseline_commit=baseline,
            fetch_if_missing=fetch_if_missing,
        )
        archive_commit = baseline

    if _cache_hit(cache_core, commit, baseline):
        return {
            "commit": commit,
            "baseline_commit": baseline,
            "source_count": sum(1 for _ in (cache_core / "src").rglob("*.java")),
            "promoted_source_count": len(promoted_sources),
            "cached": True,
        }

    cache_core.parent.mkdir(parents=True, exist_ok=True)
    with tempfile.TemporaryDirectory(prefix="fast-dev-authority.", dir=cache_core.parent) as td:
        stage = Path(td)
        authority_root = stage / "authority"
        authority_root.mkdir()
        archive_path = stage / "authority.tar"

        _git(
            repo_root,
            "archive",
            "--format=tar",
            f"--output={archive_path}",
            archive_commit,
            *ARCHIVE_PATHS,
        )
        with tarfile.open(archive_path, "r") as archive:
            archive.extractall(authority_root, filter="data")

        if promoted_sources:
            _overlay_promoted_sources(
                repo_root,
                authority_root,
                completed_commit=commit,
                promoted_sources=promoted_sources,
            )

        rules_source = repo_root / "tools" / "850" / "bootstrap" / "package_rules.json"
        if not rules_source.is_file():
            raise FileNotFoundError(rules_source)
        rules_target = authority_root / "tools" / "850" / "bootstrap" / "package_rules.json"
        rules_target.parent.mkdir(parents=True, exist_ok=True)
        shutil.copy2(rules_source, rules_target)

        result = _MIGRATE.materialize_sources(authority_root, cache_core)

    marker = cache_core / "PINNED_AUTHORITY.json"
    marker.write_text(
        json.dumps(
            {
                "schema": AUTHORITY_CACHE_SCHEMA_VERSION,
                "commit": commit,
                "branch": COMPLETED_BRANCH,
                "baseline_commit": baseline,
                "promotion_only": baseline_commit is not None,
                "promoted_source_count": len(promoted_sources),
                "source_count": int(result["source_count"]),
            },
            indent=2,
        )
        + "\n",
        encoding="utf-8",
    )
    return {
        "commit": commit,
        "baseline_commit": baseline,
        "source_count": int(result["source_count"]),
        "promoted_source_count": len(promoted_sources),
        "cached": False,
    }
