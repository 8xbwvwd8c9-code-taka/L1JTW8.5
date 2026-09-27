#!/usr/bin/env python3
"""Read-only text scanner for L880C client-resource evidence.

The scanner never writes to an input path. It accepts files or directories,
tries the common encodings used by Lineage client resources/logs, skips
NUL-heavy binary data, and emits a deterministic Markdown evidence report.
"""
from __future__ import annotations

import argparse
from pathlib import Path
from typing import NamedTuple, Sequence

DEFAULT_TERMS: tuple[str, ...] = (
    "自動狩獵",
    "內掛設定",
    "內掛",
    "自動",
    "狩獵",
    "設定",
    "autohunt",
    "auto hunt",
    "auto",
    "hunt",
    "setting",
    "config",
    "ui",
    "macro",
)

ENCODINGS: tuple[str, ...] = ("utf-8", "cp950", "big5")


class Hit(NamedTuple):
    path: str
    encoding: str
    line: int
    term: str
    snippet: str


def _iter_files(paths: Sequence[Path]) -> list[Path]:
    files: set[Path] = set()
    for raw_path in paths:
        path = raw_path.expanduser()
        if path.is_file():
            files.add(path.resolve())
        elif path.is_dir():
            files.update(candidate.resolve() for candidate in path.rglob("*") if candidate.is_file())
        else:
            raise FileNotFoundError(f"input path does not exist: {path}")
    return sorted(files, key=lambda p: p.as_posix().casefold())


def _decode_text(data: bytes) -> tuple[str, str] | None:
    if not data:
        return "", "utf-8"
    if data.count(b"\x00") / len(data) >= 0.05:
        return None
    if data.startswith(b"\xef\xbb\xbf"):
        try:
            return data.decode("utf-8-sig"), "utf-8-sig"
        except UnicodeDecodeError:
            pass
    for encoding in ENCODINGS:
        try:
            return data.decode(encoding), encoding
        except UnicodeDecodeError:
            continue
    return None


def _best_term(line: str, terms: Sequence[str]) -> str | None:
    folded = line.casefold()
    matches = [term for term in terms if term and term.casefold() in folded]
    if not matches:
        return None
    return sorted(matches, key=lambda term: (-len(term), term.casefold(), term))[0]


def scan_paths(paths: Sequence[Path], terms: Sequence[str]) -> tuple[list[Hit], int]:
    hits: list[Hit] = []
    scanned = 0
    normalized_terms = tuple(dict.fromkeys(term.strip() for term in terms if term.strip()))
    for path in _iter_files(paths):
        decoded = _decode_text(path.read_bytes())
        if decoded is None:
            continue
        text, encoding = decoded
        scanned += 1
        for line_number, line in enumerate(text.splitlines(), 1):
            term = _best_term(line, normalized_terms)
            if term is None:
                continue
            snippet = " ".join(line.strip().split())[:240]
            hits.append(Hit(path.as_posix(), encoding, line_number, term, snippet))
    hits.sort(key=lambda hit: (hit.path.casefold(), hit.line, hit.term.casefold(), hit.snippet))
    return hits, scanned


def _escape_cell(value: str) -> str:
    return value.replace("|", "\\|").replace("\n", " ").replace("\r", " ")


def render_report(paths: Sequence[Path], hits: Sequence[Hit], scanned: int) -> str:
    source_lines = [f"- `{path.expanduser().resolve().as_posix()}`" for path in paths]
    lines = [
        "# L880C Auto-Hunt Client Resource Scan",
        "",
        "## Scope",
        "",
        *source_lines,
        "",
        f"TEXT_FILES_SCANNED={scanned}",
        f"MATCHES={len(hits)}",
        "SOURCE_MODIFIED=NO",
        "",
        "## Result",
        "",
    ]
    if not hits:
        lines.extend(
            [
                "`NO_MATCHES_OBSERVED`",
                "",
                "This is an observed no-match in the supplied text inputs; it is not proof that the client resource or behavior is absent.",
                "",
            ]
        )
    else:
        lines.extend(
            [
                "| File | Encoding | Line | Term | Snippet |",
                "| --- | --- | ---: | --- | --- |",
            ]
        )
        for hit in hits:
            lines.append(
                "| `{}` | {} | {} | `{}` | {} |".format(
                    _escape_cell(hit.path),
                    _escape_cell(hit.encoding),
                    hit.line,
                    _escape_cell(hit.term),
                    _escape_cell(hit.snippet),
                )
            )
        lines.append("")
    lines.extend(
        [
            "## Interpretation Guard",
            "",
            "Filename/text matches are evidence candidates only. They do not prove the native 850 auto-hunt packet opcode, handler, or ON/OFF payload semantics.",
            "",
        ]
    )
    return "\n".join(lines)


def _parse_args(argv: Sequence[str] | None = None) -> argparse.Namespace:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", action="append", required=True, help="Input text file or directory; repeatable")
    parser.add_argument("--output", required=True, help="Markdown report path")
    parser.add_argument("--term", action="append", help="Override default search terms; repeatable")
    return parser.parse_args(argv)


def main(argv: Sequence[str] | None = None) -> int:
    args = _parse_args(argv)
    inputs = [Path(value) for value in args.input]
    terms = tuple(args.term) if args.term else DEFAULT_TERMS
    hits, scanned = scan_paths(inputs, terms)
    report = render_report(inputs, hits, scanned)
    output = Path(args.output)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(report, encoding="utf-8", newline="\n")
    print(f"REPORT={output}")
    print(f"TEXT_FILES_SCANNED={scanned}")
    print(f"MATCHES={len(hits)}")
    print("SOURCE_MODIFIED=NO")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
