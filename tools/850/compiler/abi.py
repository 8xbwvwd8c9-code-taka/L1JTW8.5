#!/usr/bin/env python3
from __future__ import annotations

import hashlib
import re


_COMMENT_BLOCK = re.compile(r"/\*.*?\*/", re.S)
_COMMENT_LINE = re.compile(r"//.*?(?=\n|$)")
_PUBLIC_DECL = re.compile(r"\b(public|protected)\s+([^;{]+)(?=[;{])")
_PACKAGE = re.compile(r"\bpackage\s+([A-Za-z_$][\w$]*(?:\.[A-Za-z_$][\w$]*)*)\s*;")


def _strip_comments(text: str) -> str:
    return _COMMENT_LINE.sub("", _COMMENT_BLOCK.sub("", text))


def _normalize_space(text: str) -> str:
    return " ".join(text.split())


def public_abi_surface(source_text: str) -> list[str]:
    """Return a stable source-level public/protected declaration surface.

    Fast Dev uses this as a conservative change detector. It intentionally
    ignores method bodies; when it cannot model a dependency closure safely,
    the caller escalates to full compilation rather than trusting this alone.
    """
    clean = _strip_comments(source_text)
    surface: list[str] = []
    package = _PACKAGE.search(clean)
    if package:
        surface.append("package " + package.group(1))
    for match in _PUBLIC_DECL.finditer(clean):
        surface.append(match.group(1) + " " + _normalize_space(match.group(2)))
    return surface


def public_abi_fingerprint(source_text: str) -> str:
    payload = "\n".join(public_abi_surface(source_text)).encode("utf-8")
    return hashlib.sha256(payload).hexdigest()
