# Reconstruction scaffold

Python standard-library only. These semantic interfaces run against caller-supplied
offline adapters. No native adapter, memory access, protocol encoding, scheduler,
target selection, hunting policy, RVA, ABI, or donor numeric constants are implemented.

`load_confirmed_maps(Path(... / "maps"), expected_sha256)` reads five canonical
CSV files in read-only mode, checks schema/status/evidence metadata and target
digest consistency, rejects duplicate keys, then projects CONFIRMED/STABLE rows
to immutable semantic facts within an explicit Bot controller/entity allowlist.
Source CSV digests remain in `source_hashes` for
reproducibility. Native columns are discarded. Network, spell, and unrelated
semantics are excluded. Existing source promotion is trusted; this loader does
not independently re-prove evidence or infer callable native contracts.

`BotController` starts STOPPED. `start` enters READY; `pause` and `resume` control
explicit dispatch; `stop` also resets a FAULTED controller to STOPPED. Adapter
exceptions or unsuccessful acknowledgements enter FAULTED. Every submission
requires nonempty confirmed dependencies, available observations, and explicit
policy approval. The default policy denies all submissions. An approved intent
is only an offline adapter instruction; confirmed metadata is never native-call
authorization. Capability strings are application intent labels, not verified
native capabilities. Caller policy must associate them with the correct
semantic dependencies. No actions are generated automatically.

Candidate HP/MP fields and candidate Bot UI callbacks do not become confirmed
facts. Empty runtime-map fields stay unknown. Contracts, binaries, and source
maps are never changed by this package.
