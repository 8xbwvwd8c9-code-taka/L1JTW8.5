# L1JTW8.5 Recovery Split Status — 2026-09-20

Two-track layout:

- `recovery/processed/`: frozen processed checkpoint.
- `recovery/wip/`: unresolved queue.

Current split:
- processed per-class PASS = **584**
- WIP per-class FAIL = **204**

The active work branch remains `analysis/l1jtw85-recovery`.
A frozen checkpoint branch is created at this split commit so ongoing WIP changes do not rewrite the processed checkpoint.
