#!/usr/bin/env python3
import argparse
import json
import os
import shutil
import subprocess
import sys
from pathlib import Path


_THIS_DIR = Path(__file__).resolve().parent
if str(_THIS_DIR) not in sys.path:
    sys.path.insert(0, str(_THIS_DIR))

import select_completed_repairs as mapping_policy
import select_deployable_repairs as deploy_policy


def _run_capture(cmd, *, cwd: Path, check=True):
    return subprocess.run(
        [str(x) for x in cmd],
        cwd=cwd,
        check=check,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
        encoding="utf-8",
        errors="replace",
    )


def _promotion_records(repo_root: Path, completed_ref: str):
    log = _run_capture(
        ["git", "log", "--first-parent", "--reverse", "--format=%H%x09%s", completed_ref],
        cwd=repo_root,
    )
    records = []
    for line in log.stdout.splitlines():
        if not line.strip() or "\t" not in line:
            continue
        sha, message = line.split("\t", 1)
        if not deploy_policy.promotion_bug_id(message):
            continue
        files = _run_capture(
            ["git", "show", "--format=", "--name-only", sha], cwd=repo_root
        )
        records.append({
            "sha": sha.strip(),
            "message": message.strip(),
            "files": [row.strip() for row in files.stdout.splitlines() if row.strip()],
        })
    return records


def _git_show_text(repo_root: Path, ref: str, path: str):
    proc = _run_capture(["git", "show", f"{ref}:{path}"], cwd=repo_root, check=False)
    if proc.returncode != 0:
        return None, proc.stderr.strip()
    return proc.stdout, ""


def _copy_candidate_classes(candidate_out: Path, accepted_out: Path, normalized_top: str):
    accepted = []
    for source in sorted(candidate_out.rglob("*.class")):
        rel = source.relative_to(candidate_out).as_posix()
        internal = rel[:-6]
        if internal != normalized_top and not internal.startswith(normalized_top + "$"):
            raise ValueError(
                f"candidate emitted class outside selected top {normalized_top}: {rel}"
            )
        target = accepted_out / rel
        target.parent.mkdir(parents=True, exist_ok=True)
        if target.exists() and target.read_bytes() != source.read_bytes():
            raise ValueError(f"deployable class collision: {rel}")
        shutil.copy2(source, target)
        accepted.append(rel)
    return accepted


def _error_signatures(text: str, limit=20):
    rows = []
    for line in text.splitlines():
        stripped = line.strip()
        if "error:" in stripped:
            rows.append(stripped[stripped.index("error:"):])
        if len(rows) >= limit:
            break
    return rows


def compile_deployable_repairs(*, repo_root: Path, completed_ref: str, output_dir: Path):
    repo_root = Path(repo_root).resolve()
    output_dir = Path(output_dir)
    if not output_dir.is_absolute():
        output_dir = repo_root / output_dir

    mapping_csv = repo_root / "recovery" / "source_namespace_map.csv"
    normalized_jar = repo_root / "recovery" / "l1jserver2-source-normalized.jar"
    original_jar = repo_root / "l1jserver2.jar"
    build_normalized = repo_root / "tools" / "normalized-recovery" / "build-normalized-jar.py"
    for required in (mapping_csv, original_jar, build_normalized):
        if not required.exists():
            raise FileNotFoundError(required)

    if output_dir.exists():
        shutil.rmtree(output_dir)
    accepted_out = output_dir / "classes"
    source_root = output_dir / "sources"
    work_root = output_dir / "work"
    log_root = output_dir / "logs"
    for directory in (accepted_out, source_root, work_root, log_root):
        directory.mkdir(parents=True, exist_ok=True)

    print("=== build normalized donor ABI ===", flush=True)
    subprocess.run([sys.executable, str(build_normalized)], cwd=repo_root, check=True)
    if not normalized_jar.is_file():
        raise FileNotFoundError(normalized_jar)

    mappings = mapping_policy.load_top_level_mappings(mapping_csv)
    promotions = _promotion_records(repo_root, completed_ref)
    candidates = deploy_policy.select_promotion_candidates(promotions, mappings)
    if not candidates:
        raise ValueError("no promoted normalized repair candidates found")

    print(json.dumps({
        "completed_ref": completed_ref,
        "promotion_commit_count": len(promotions),
        "candidate_count": len(candidates),
        "candidates": [c["normalized_top"] for c in candidates],
    }, indent=2, ensure_ascii=False), flush=True)

    materialized = []
    source_missing = []
    for candidate in candidates:
        text, error = _git_show_text(repo_root, completed_ref, candidate["source_path"])
        if text is None:
            source_missing.append({
                **candidate,
                "status": "DEFERRED",
                "reason": "SOURCE_NOT_PRESENT_AT_AUTHORITY",
                "javac_exit": None,
                "generated_class_count": 0,
                "generated_classes": [],
                "error_signatures": [error] if error else [],
            })
            continue
        source = source_root / (candidate["normalized_top"] + ".java")
        source.parent.mkdir(parents=True, exist_ok=True)
        source.write_text(text, encoding="utf-8")
        materialized.append((candidate, source))

    lib_jars = sorted((repo_root / "lib").glob("*.jar"))
    pending = list(materialized)
    deployable = []
    last_deferred = {}
    round_no = 0

    while pending:
        round_no += 1
        progressed = False
        next_pending = []
        for candidate, source in pending:
            safe_name = candidate["normalized_top"].replace("/", "__")
            candidate_out = work_root / f"round-{round_no}" / safe_name / "classes"
            candidate_out.mkdir(parents=True, exist_ok=True)
            log_path = log_root / f"{safe_name}.round-{round_no}.log"

            cp_entries = []
            if any(accepted_out.rglob("*.class")):
                cp_entries.append(accepted_out)
            cp_entries.append(normalized_jar)
            cp_entries.extend(lib_jars)
            classpath = os.pathsep.join(str(p) for p in cp_entries)
            cmd = [
                "javac", "-proc:none", "-encoding", "UTF-8", "-source", "8", "-target", "8",
                "-sourcepath", "", "-cp", classpath, "-d", str(candidate_out), str(source),
            ]
            proc = subprocess.run(
                cmd,
                cwd=repo_root,
                stdout=subprocess.PIPE,
                stderr=subprocess.STDOUT,
                text=True,
                encoding="utf-8",
                errors="replace",
            )
            log_path.write_text(proc.stdout, encoding="utf-8")
            generated = sorted(
                p.relative_to(candidate_out).as_posix()
                for p in candidate_out.rglob("*.class")
            )
            result = deploy_policy.classify_compile_result(
                candidate, javac_exit=proc.returncode, generated_classes=generated
            )
            result["round"] = round_no
            result["log"] = log_path.relative_to(repo_root).as_posix()
            result["error_signatures"] = _error_signatures(proc.stdout)

            if result["status"] == "DEPLOYABLE":
                copied = _copy_candidate_classes(
                    candidate_out, accepted_out, candidate["normalized_top"]
                )
                result["generated_classes"] = copied
                result["generated_class_count"] = len(copied)
                deployable.append(result)
                last_deferred.pop(candidate["normalized_top"], None)
                progressed = True
                print(
                    f"DEPLOYABLE {candidate['normalized_top']} "
                    f"bugs={','.join(candidate['bug_ids'])} classes={len(copied)}",
                    flush=True,
                )
            else:
                last_deferred[candidate["normalized_top"]] = result
                next_pending.append((candidate, source))

        if not progressed:
            break
        pending = next_pending

    deferred = source_missing + [
        last_deferred[key] for key in sorted(last_deferred)
    ]
    deployable = sorted(deployable, key=lambda row: row["normalized_top"])
    deployable_tops = [row["normalized_top"] for row in deployable]
    if not deployable_tops:
        raise ValueError("no promoted repair candidate passed Java 8 deployability gate")

    state = {
        "completed_ref": completed_ref,
        "policy": "PROMOTION_COMMIT_PLUS_CURRENT_JAVA8_RC0_ONLY",
        "promotion_commit_count": len(promotions),
        "candidate_count": len(candidates),
        "deployable_count": len(deployable),
        "deferred_count": len(deferred),
        "deployable_normalized_tops": deployable_tops,
        "compiled_classes_dir": accepted_out.relative_to(repo_root).as_posix(),
        "deployable": deployable,
        "deferred": deferred,
    }
    output_dir.mkdir(parents=True, exist_ok=True)
    (output_dir / "DEPLOYABILITY_STATE.json").write_text(
        json.dumps(state, indent=2, ensure_ascii=False) + "\n", encoding="utf-8"
    )
    lines = [
        "# L1JTW8.5 Repair Deployability Gate",
        "",
        f"- Completed authority: `{completed_ref}`",
        f"- Promotion candidates: **{len(candidates)}**",
        f"- Deployable: **{len(deployable)}**",
        f"- Deferred: **{len(deferred)}**",
        "",
        "## Deployable",
        "",
    ]
    for row in deployable:
        lines.append(
            f"- `{row['normalized_top']}` → `{row['runtime_top']}`; "
            f"bugs={','.join(row['bug_ids'])}; javac=0; classes={row['generated_class_count']}"
        )
    lines.extend(["", "## Deferred", ""])
    for row in deferred:
        lines.append(
            f"- `{row['normalized_top']}` → `{row['runtime_top']}`; "
            f"bugs={','.join(row['bug_ids'])}; reason={row['reason']}; javac={row['javac_exit']}"
        )
    (output_dir / "DEPLOYABILITY_STATE.md").write_text(
        "\n".join(lines) + "\n", encoding="utf-8"
    )
    print(json.dumps(state, indent=2, ensure_ascii=False), flush=True)
    return state


def main():
    parser = argparse.ArgumentParser(
        description="Compile only promoted L1JTW8.5 repairs that currently pass Java 8"
    )
    parser.add_argument("--repo-root", default=".")
    parser.add_argument("--completed-ref", required=True)
    parser.add_argument("--output-dir", default="recovery/production-build/deployable")
    args = parser.parse_args()
    compile_deployable_repairs(
        repo_root=Path(args.repo_root),
        completed_ref=args.completed_ref,
        output_dir=Path(args.output_dir),
    )


if __name__ == "__main__":
    main()
