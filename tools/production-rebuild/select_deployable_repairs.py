#!/usr/bin/env python3
import re


NORMALIZED_PREFIX = "recovery/normalized-src-vf/"
_PROMOTION_RE = re.compile(r"^fix\(l[123]\): promote (BUG-850-\d+)\b", re.IGNORECASE)


def promotion_bug_id(message: str):
    first_line = str(message).splitlines()[0].strip()
    match = _PROMOTION_RE.match(first_line)
    return match.group(1).upper() if match else None


def select_promotion_candidates(promotions, top_level_mappings: dict[str, str]):
    by_top = {}
    for row in promotions:
        bug_id = promotion_bug_id(row.get("message", ""))
        if not bug_id:
            continue
        sha = str(row.get("sha", "")).strip()
        if not sha:
            raise ValueError(f"promotion {bug_id} missing commit sha")
        for raw_path in row.get("files", []):
            path = str(raw_path).replace("\\", "/").strip()
            if not path.startswith(NORMALIZED_PREFIX) or not path.endswith(".java"):
                continue
            normalized_top = path[len(NORMALIZED_PREFIX):-5]
            if normalized_top not in top_level_mappings:
                raise ValueError(
                    f"promoted normalized source has no runtime TOP_LEVEL mapping: {normalized_top}"
                )
            candidate = by_top.setdefault(
                normalized_top,
                {
                    "normalized_top": normalized_top,
                    "runtime_top": top_level_mappings[normalized_top],
                    "source_path": path,
                    "bug_ids": [],
                    "promotion_commits": [],
                    "latest_promotion_commit": None,
                },
            )
            if bug_id not in candidate["bug_ids"]:
                candidate["bug_ids"].append(bug_id)
            if sha not in candidate["promotion_commits"]:
                candidate["promotion_commits"].append(sha)
            candidate["latest_promotion_commit"] = sha
    return [by_top[key] for key in sorted(by_top)]


def classify_compile_result(candidate, *, javac_exit: int, generated_classes):
    top = str(candidate["normalized_top"]).strip()
    classes = sorted({str(path).replace("\\", "/") for path in generated_classes})
    expected = top + ".class"
    if javac_exit != 0:
        status = "DEFERRED"
        reason = "JAVAC_EXIT_NONZERO"
    elif expected not in classes:
        status = "DEFERRED"
        reason = "EXPECTED_TOP_CLASS_MISSING"
    else:
        status = "DEPLOYABLE"
        reason = None
    return {
        **candidate,
        "status": status,
        "reason": reason,
        "javac_exit": int(javac_exit),
        "generated_class_count": len(classes),
        "generated_classes": classes,
    }
