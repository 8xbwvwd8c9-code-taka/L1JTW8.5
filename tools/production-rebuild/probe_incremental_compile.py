#!/usr/bin/env python3
import argparse
import json
import os
import shutil
import subprocess
import sys
from pathlib import Path


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo-root", default=".")
    parser.add_argument("--baseline-ref", required=True)
    args = parser.parse_args()

    root = Path(args.repo_root).resolve()
    sys.path.insert(0, str(root / "tools" / "production-rebuild"))
    import select_completed_repairs as selector

    print("=== PROBE: build normalized donor ABI ===", flush=True)
    subprocess.run(
        [sys.executable, "tools/normalized-recovery/build-normalized-jar.py"],
        cwd=root,
        check=True,
    )

    mapping = root / "recovery" / "source_namespace_map.csv"
    top_map = selector.load_top_level_mappings(mapping)
    changes = selector.git_normalized_changes(root, args.baseline_ref, "HEAD")
    selected = selector.select_from_changes(changes, top_map)
    print(json.dumps({"selected_count": len(selected), "selected": selected}, indent=2), flush=True)

    sources = [root / "recovery" / "normalized-src-vf" / (top + ".java") for top in selected]
    missing = [p.as_posix() for p in sources if not p.is_file()]
    if missing:
        raise SystemExit("selected source missing: " + ", ".join(missing))

    out = root / "recovery" / "production-build" / "probe-selected-classes"
    if out.exists():
        shutil.rmtree(out)
    out.mkdir(parents=True, exist_ok=True)
    source_list = root / "recovery" / "production-build" / "probe-selected-sources.txt"
    source_list.write_text("\n".join(p.relative_to(root).as_posix() for p in sources) + "\n", encoding="utf-8")

    deps = [root / "recovery" / "l1jserver2-source-normalized.jar"]
    deps.extend(sorted((root / "lib").glob("*.jar")))
    cp = os.pathsep.join(str(p) for p in deps)
    log = root / "recovery" / "production-build" / "probe-selected-javac.log"
    cmd = [
        "javac", "-encoding", "UTF-8", "-source", "8", "-target", "8", "-proc:none",
        "-Xmaxerrs", "5000", "-Xmaxwarns", "5000", "-cp", cp,
        "-d", str(out), "@" + str(source_list),
    ]
    print("+ " + " ".join(cmd[:12]) + " ...", flush=True)
    with log.open("w", encoding="utf-8", errors="replace") as f:
        proc = subprocess.run(cmd, cwd=root, stdout=f, stderr=subprocess.STDOUT, text=True)

    built = sorted(p.relative_to(out).as_posix() for p in out.rglob("*.class"))
    text = log.read_text(encoding="utf-8", errors="replace")
    result = {
        "javac_exit": proc.returncode,
        "selected_top_count": len(selected),
        "generated_class_count": len(built),
        "normalized_donor_abi": "recovery/l1jserver2-source-normalized.jar",
        "direct_original_game_jar_on_classpath": False,
    }
    print(json.dumps(result, indent=2), flush=True)
    if proc.returncode != 0:
        print("=== probe javac first 160 lines ===", flush=True)
        for line in text.splitlines()[:160]:
            print(line, flush=True)
        raise SystemExit(proc.returncode)

    reverse = {}
    import csv
    with mapping.open(encoding="utf-8-sig", newline="") as f:
        for row in csv.DictReader(f):
            reverse[row["NewInternal"]] = row["OldInternal"]
    unmapped = [p[:-6] for p in built if p[:-6] not in reverse]
    if unmapped:
        print(json.dumps({"unmapped_generated_classes": unmapped}, indent=2), flush=True)
        raise SystemExit("probe generated unmapped classes")

    print("PROBE_INCREMENTAL_COMPILE=PASS", flush=True)


if __name__ == "__main__":
    main()
