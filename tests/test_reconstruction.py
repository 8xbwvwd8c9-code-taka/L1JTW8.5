"""Offline contract safety tests. Fixtures carry invented semantic metadata only."""
import ast
import csv
import dataclasses
import json
import io
from pathlib import Path
import re
import unittest
from unittest.mock import patch

from reconstruction.contracts import (
    ContractError, ContractRegistry, Fact, SEMANTIC_ALLOWLIST, SOURCES,
    load_confirmed_maps,
)
from reconstruction.controller import BotController, State
from reconstruction.interfaces import Intent, Snapshot

ROOT = Path(__file__).resolve().parents[1]
SHA = "a" * 64
DEP = ("functions", "bot_controller_get_target_id")


class FakeAdapter:
    def __init__(self, available=True, result=True, fault=None):
        self.available, self.result, self.fault = available, result, fault
        self.observed = 0
        self.performed = []

    def observe(self):
        self.observed += 1
        if self.fault == "observe":
            raise RuntimeError("synthetic observation fault")
        return Snapshot(self.available)

    def perform(self, intent):
        self.performed.append(intent)
        if self.fault == "perform":
            raise RuntimeError("synthetic action fault")
        return self.result


class Approve:
    def approve(self, intent, snapshot):
        return True


def registry():
    return ContractRegistry((Fact(*DEP, "CONFIRMED", "fixture:1", SHA),), SHA)


class ControllerTests(unittest.TestCase):
    def make(self, adapter=None, policy=None):
        adapter = adapter or FakeAdapter()
        return BotController(adapter, registry(), policy), adapter

    def test_default_denial_and_stopped_gate(self):
        controller, adapter = self.make()
        self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
        self.assertEqual(adapter.observed, 0)
        controller.start()
        self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
        self.assertEqual(adapter.performed, [])
        self.assertEqual(controller.state, State.READY)

    def test_lifecycle(self):
        controller, _ = self.make()
        for method in (controller.pause, controller.resume):
            with self.assertRaises(ValueError):
                method()
        controller.start()
        with self.assertRaises(ValueError):
            controller.start()
        with self.assertRaises(ValueError):
            controller.resume()
        controller.pause()
        self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
        with self.assertRaises(ValueError):
            controller.pause()
        controller.resume()
        controller.stop()
        self.assertEqual(controller.state, State.STOPPED)

    def test_missing_empty_dependencies_and_capability(self):
        controller, adapter = self.make(policy=Approve())
        controller.start()
        for intent, dependencies in ((Intent("synthetic"), ()),
                                     (Intent(" "), (DEP,)),
                                     (Intent("synthetic"), (("functions", "missing"),))):
            with self.subTest(dependencies=dependencies):
                self.assertFalse(controller.submit(intent, dependencies))
        self.assertEqual(adapter.observed, 0)
        self.assertEqual(adapter.performed, [])

    def test_unavailable_observation(self):
        controller, adapter = self.make(FakeAdapter(available=False), Approve())
        controller.start()
        self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
        self.assertEqual(adapter.performed, [])
        self.assertEqual(controller.state, State.READY)

    def test_explicit_approval(self):
        controller, adapter = self.make(policy=Approve())
        controller.start()
        self.assertTrue(controller.submit(Intent("synthetic"), (DEP,)))
        self.assertEqual(len(adapter.performed), 1)

    def test_adapter_faults_and_unacknowledged_action(self):
        for adapter in (FakeAdapter(fault="observe"), FakeAdapter(fault="perform"),
                        FakeAdapter(result=False), FakeAdapter(result=1)):
            with self.subTest(fault=adapter.fault, result=adapter.result):
                controller, _ = self.make(adapter, Approve())
                controller.start()
                self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
                self.assertEqual(controller.state, State.FAULTED)
                self.assertTrue(controller.last_error)
                prior = adapter.observed
                self.assertFalse(controller.submit(Intent("synthetic"), (DEP,)))
                self.assertEqual(adapter.observed, prior)
                controller.stop()
                controller.start()
                self.assertIsNone(controller.last_error)


class ContractTests(unittest.TestCase):
    def setUp(self):
        self.root = Path("synthetic-contract-fixtures")
        self.files = {}
        self.reader = patch.object(Path, "read_bytes", lambda path: self.files[path.name])
        self.rows = {}
        for source in SOURCES:
            key = sorted(SEMANTIC_ALLOWLIST[source])[0]
            self.rows[source] = [(key, "CONFIRMED", "fixture:1", SHA, "opaque native payload")]
        self.write()

    def write(self):
        for source, key_column in SOURCES.items():
            handle = io.StringIO(newline="")
            writer = csv.writer(handle)
            writer.writerow([key_column, "status", "evidence_ref", "target_sha256", "rva"])
            writer.writerows(self.rows[source])
            self.files[source + ".csv"] = handle.getvalue().encode("utf-8")

    def load(self):
        with self.reader:
            return load_confirmed_maps(self.root, SHA)

    def test_metadata_projection_and_source_hashes(self):
        result = self.load()
        self.assertEqual(len(result.facts), len(SOURCES))
        self.assertEqual(set(dataclasses.asdict(result.facts[0])),
                         {"source", "key", "status", "evidence_ref", "target_sha256"})
        self.assertNotIn("opaque native payload", repr(result))
        self.assertEqual(len(result.source_hashes), len(SOURCES))
        self.assertTrue(all(re.fullmatch(r"[a-f0-9]{64}", digest)
                            for _, digest in result.source_hashes))

    def test_unconfirmed_and_out_of_scope_excluded(self):
        for status in ("UNKNOWN", "CANDIDATE", "REJECTED"):
            self.rows["functions"] = [(DEP[1], status, "fixture:1", SHA, "opaque")]
            self.write()
            with self.assertRaises(ContractError):
                self.load().require(*DEP)
        self.rows["functions"] = [("unrelated_transport_fact", "CONFIRMED", "fixture:1", SHA, "opaque")]
        self.write()
        self.assertFalse(any(f.source == "functions" for f in self.load().facts))

    def test_malformed_metadata(self):
        invalid = [(DEP[1], "BAD_STATUS", "fixture:1", SHA, "opaque"),
                   ("", "CONFIRMED", "fixture:1", SHA, "opaque"),
                   (DEP[1], "CONFIRMED", "", SHA, "opaque"),
                   (DEP[1], "CONFIRMED", "fixture:1", "invalid", "opaque"),
                   (DEP[1], "CONFIRMED", "fixture:1", "b" * 64, "opaque")]
        for row in invalid:
            with self.subTest(row=row):
                self.rows["functions"] = [row]
                self.write()
                with self.assertRaises(ContractError):
                    self.load()

    def test_duplicates_and_malformed_csv(self):
        self.rows["functions"] *= 2
        self.write()
        with self.assertRaises(ContractError):
            self.load()
        for content in ("name,status\n", "name,name,status,evidence_ref,target_sha256\n",
                        "name,status,evidence_ref,target_sha256\na,CONFIRMED,e\n",
                        'name,status,evidence_ref,target_sha256\n"unterminated'):
            with self.subTest(content=content):
                self.files["functions.csv"] = content.encode("utf-8")
                with self.assertRaises(ContractError):
                    self.load()

    def test_registry_rejects_candidate_duplicate_and_bad_target(self):
        for facts in ((Fact(*DEP, "CANDIDATE", "fixture:1", SHA),),
                      registry().facts * 2,
                      (Fact("functions", "outside_semantic_allowlist", "CONFIRMED", "fixture:1", SHA),),
                      (Fact(*DEP, "CONFIRMED", "fixture:1", "b" * 64),)):
            with self.assertRaises(ContractError):
                ContractRegistry(facts, SHA)
        with self.assertRaises(ContractError):
            ContractRegistry((), "invalid")

    def test_canonical_maps_read_only_projection(self):
        canonical = Path("I:/850C-Client-RE/maps")
        paths = [canonical / (source + ".csv") for source in SOURCES]
        if not all(path.exists() for path in paths):
            self.skipTest("canonical maps absent")
        before = {path: path.read_bytes() for path in paths}
        reader = csv.DictReader(before[paths[0]].decode("utf-8-sig").splitlines())
        target = next(reader)["target_sha256"]
        result = load_confirmed_maps(canonical, target)
        self.assertTrue(result.facts)
        for fact in result.facts:
            self.assertIn(fact.key, SEMANTIC_ALLOWLIST[fact.source])
            self.assertIn(fact.status, {"CONFIRMED", "STABLE"})
        self.assertEqual(before, {path: path.read_bytes() for path in paths})


class StaticSafetyTests(unittest.TestCase):
    def test_no_native_constants_or_native_io(self):
        prohibited = {"ctypes", "socket", "subprocess", "win32api", "win32process", "pymem", "requests"}
        for path in (ROOT / "reconstruction").rglob("*.py"):
            source = path.read_text(encoding="utf-8-sig")
            self.assertIsNone(re.search(r"\b0[xX][0-9a-fA-F]+\b", source), str(path))
            tree = ast.parse(source)
            for node in ast.walk(tree):
                if isinstance(node, ast.Import):
                    imports = [alias.name.split(".")[0] for alias in node.names]
                elif isinstance(node, ast.ImportFrom):
                    imports = [(node.module or "").split(".")[0]]
                else:
                    continue
                self.assertFalse(prohibited.intersection(imports), str(path))

    def test_write_partitions_are_disjoint(self):
        document = (ROOT / "donor" / "INTEGRATION.md").read_text(encoding="utf-8-sig")
        partitions = re.findall(r"\| (?:SUB[1-5]|主代理) \| ([^|]+) \|", document)
        self.assertEqual(len(partitions), 6)
        normalized = [part.strip().removesuffix("/**").rstrip("/") for part in partitions]
        for index, left in enumerate(normalized):
            for right in normalized[index + 1:]:
                self.assertNotEqual(left, right)
                self.assertFalse(left.startswith(right + "/") or right.startswith(left + "/"))

    def test_matrix_provenance_and_status_consistency(self):
        path = ROOT / "donor" / "matrix" / "feature_matrix.json"
        self.assertTrue(path.is_file(), "required feature matrix absent")
        content = json.loads(path.read_text(encoding="utf-8-sig"))
        self.assertIs(content["native_binding"], False)
        rows = content if isinstance(content, list) else content["rows"]
        expected = {"controller_lifecycle", "target_lookup_id", "action_queue", "item_action",
                    "ui_settings", "hpmp_observation", "timing_and_pause", "auto_hunt_policy",
                    "buff_and_conversion", "maintenance_actions", "environment_and_display",
                    "pet_summon_spirit"}
        self.assertEqual({row["feature_id"] for row in rows}, expected)
        self.assertEqual(len(rows), 12)
        canonical = Path(content["canonical_maps"])
        referenced_sources = {contract["source"] for row in rows
                              for contract in row["client_850"]["contracts"]}
        paths = {source: canonical / (source + ".csv") for source in referenced_sources}
        if not all(path.is_file() for path in paths.values()):
            self.skipTest("canonical CSVs absent; matrix source comparison unavailable")
        before = {source: path.read_bytes() for source, path in paths.items()}
        canonical_rows = {}
        referenced_keys = {(contract["source"], contract["key"]) for row in rows
                           for contract in row["client_850"]["contracts"]}
        for source, raw in before.items():
            key_column = SOURCES[source]
            for record in csv.DictReader(io.StringIO(raw.decode("utf-8-sig"))):
                identity = (source, record[key_column].strip())
                if identity not in referenced_keys:
                    continue
                self.assertFalse(identity in canonical_rows, "Duplicate referenced canonical semantic key")
                canonical_rows[identity] = {key: record[key] for key in ("status", "evidence_ref")}
        seen = set()
        for row in rows:
            feature = row["feature_id"]
            self.assertNotIn(feature, seen)
            seen.add(feature)
            for donor in ("donor_381", "donor_880"):
                evidence = row[donor]
                self.assertTrue(evidence["status"])
                if evidence["status"] != "UNKNOWN":
                    self.assertTrue(evidence["sources"], feature)
            client = row["client_850"]
            for contract in client["contracts"]:
                self.assertTrue(contract["source"])
                self.assertTrue(contract["key"])
                self.assertTrue(contract["evidence_ref"])
                self.assertIn(contract["status"], {"CONFIRMED", "STABLE", "UNKNOWN", "CANDIDATE", "REJECTED"})
                identity = (contract["source"], contract["key"])
                self.assertIn(identity, canonical_rows, feature)
                actual = canonical_rows[identity]
                self.assertEqual(contract["status"], actual["status"].strip(), feature)
                self.assertEqual(contract["evidence_ref"], actual["evidence_ref"].strip(), feature)
            if any(c["status"] not in {"CONFIRMED", "STABLE"} for c in client["contracts"]):
                self.assertEqual(client["availability"], "UNAVAILABLE", feature)
            if client["availability"] == "METADATA_ONLY":
                self.assertTrue(client["contracts"], feature)
                self.assertTrue(all(c["status"] in {"CONFIRMED", "STABLE"} for c in client["contracts"]))
                self.assertEqual(client["status"], "CONFIRMED_BOUNDARY", feature)
            if client["status"] in {"CONFIRMED", "STABLE", "CONFIRMED_BOUNDARY"}:
                self.assertTrue(client["contracts"], feature)
                self.assertTrue(all(c["status"] in {"CONFIRMED", "STABLE"} for c in client["contracts"]), feature)
            self.assertTrue(row["reconstruction"]["disposition"])
        self.assertEqual(before, {source: path.read_bytes() for source, path in paths.items()})


if __name__ == "__main__":
    unittest.main()
