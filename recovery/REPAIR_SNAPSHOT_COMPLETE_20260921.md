# L1JTW8.5 Recovered Core — Decompiled / Repair Snapshot

Date: 2026-09-21

Branch: `completed/l1jtw85-decompiled-fixes-20260921`

## Purpose

This branch is the frozen snapshot of the recovered/decompiled 8.5 core plus all repair commits that had already been staged before the project returned to audit-only mode.

## Status

- Recovered/decompiled source: present under `recovered-src-obf/`.
- Repair work already staged: preserved on this branch.
- New repair work: **STOPPED**.
- Active work after this snapshot: **audit / bug discovery / severity classification only** on `analysis/l1jtw85-bug-audit`.
- Do not add new Java/SQL behavior changes here unless the user explicitly switches back to repair mode.

## Validation boundary

This branch is a repair snapshot, not a claim that the entire recovered tree is source-only build clean.

Known recovery baseline:

- per-class sanitized-reference compile: 584 / 788 PASS;
- 204 recovered classes still fail that recovery compile gate;
- normalized full-source compile: FAIL.

Therefore:

- existing repair commits are preserved;
- do not reinterpret remaining decompiler/recovery compile failures as original runtime bugs without independent evidence;
- future audit findings belong on the audit branch, not here.

## Branch roles

- `analysis/l1jtw85-bug-audit`: active audit-only branch.
- `completed/l1jtw85-decompiled-fixes-20260921`: frozen repaired snapshot.
- `fix/l1jtw85-audit-remediation`: legacy repair work branch; no longer the active workflow.

## Audit source of truth

Use:

`recovery/BUG_AUDIT_2026-09-20.md`

on `analysis/l1jtw85-bug-audit`.

Finding identity is determined by the actual `## BUG/RISK/NOTE-850-NNN` headers in that report. Historical staging text must not override reconciled finding numbers.
