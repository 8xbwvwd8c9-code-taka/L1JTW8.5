import hashlib
import importlib.util
import tempfile
import unittest
from pathlib import Path

ROOT = Path(__file__).resolve().parent
SCANNER = ROOT / "scan_880_client_resources.py"
WRAPPER = ROOT / "scan_880_auto_hunt_ui.ps1"


def load_scanner():
    spec = importlib.util.spec_from_file_location("scan_880_client_resources", SCANNER)
    if spec is None or spec.loader is None:
        raise RuntimeError("unable to load scanner spec")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def sha256(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


class ResourceScannerTest(unittest.TestCase):
    def setUp(self):
        self.scanner = load_scanner()

    def test_utf8_and_cp950_hits_are_reported_with_encoding(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            utf8_file = root / "ui_utf8.txt"
            cp950_file = root / "ui_cp950.txt"
            utf8_file.write_text("window=自動狩獵設定\n", encoding="utf-8")
            cp950_file.write_bytes("功能=內掛設定\r\n".encode("cp950"))

            hits, scanned = self.scanner.scan_paths([root], self.scanner.DEFAULT_TERMS)

            self.assertEqual(2, scanned)
            by_name = {Path(hit.path).name: hit for hit in hits}
            self.assertIn("ui_utf8.txt", by_name)
            self.assertIn("ui_cp950.txt", by_name)
            self.assertEqual("utf-8", by_name["ui_utf8.txt"].encoding)
            self.assertEqual("cp950", by_name["ui_cp950.txt"].encoding)
            self.assertIn("自動狩獵", by_name["ui_utf8.txt"].term)
            self.assertIn("內掛", by_name["ui_cp950.txt"].term)

    def test_ascii_terms_are_case_insensitive_and_report_is_deterministic(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            (root / "b.txt").write_text("AutoHuntPanel.xml\n", encoding="utf-8")
            (root / "a.txt").write_text("HUNT_SETTING\n", encoding="utf-8")

            hits1, scanned1 = self.scanner.scan_paths([root], self.scanner.DEFAULT_TERMS)
            hits2, scanned2 = self.scanner.scan_paths([root], self.scanner.DEFAULT_TERMS)
            report1 = self.scanner.render_report([root], hits1, scanned1)
            report2 = self.scanner.render_report([root], hits2, scanned2)

            self.assertEqual(report1, report2)
            self.assertLess(report1.index("a.txt"), report1.index("b.txt"))
            self.assertIn("auto", report1.lower())
            self.assertIn("hunt", report1.lower())

    def test_no_match_is_observation_not_absence_claim(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            (root / "neutral.txt").write_text("inventory only\n", encoding="utf-8")
            hits, scanned = self.scanner.scan_paths([root], self.scanner.DEFAULT_TERMS)
            report = self.scanner.render_report([root], hits, scanned)

            self.assertFalse(hits)
            self.assertIn("NO_MATCHES_OBSERVED", report)
            self.assertIn("not proof", report.lower())

    def test_scanner_does_not_modify_inputs_and_skips_nul_heavy_binary(self):
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            text_file = root / "safe.txt"
            binary_file = root / "binary.bin"
            text_file.write_text("Auto Hunt UI\n", encoding="utf-8")
            binary_file.write_bytes((b"\x00" * 64) + b"auto hunt")
            before = {path.name: sha256(path) for path in (text_file, binary_file)}

            hits, scanned = self.scanner.scan_paths([root], self.scanner.DEFAULT_TERMS)
            after = {path.name: sha256(path) for path in (text_file, binary_file)}

            self.assertEqual(before, after)
            self.assertEqual(1, scanned)
            self.assertTrue(all(Path(hit.path).name == "safe.txt" for hit in hits))

    def test_powershell_wrapper_is_read_only_and_uses_toolkit_entrypoint(self):
        text = WRAPPER.read_text(encoding="utf-8")
        lowered = text.lower()
        self.assertIn(r"scripts\lineage-tool.ps1", lowered)
        self.assertIn("pak list", lowered)
        self.assertIn("--filter", lowered)
        self.assertIn("copy-item", lowered)
        for forbidden in (" pak add ", " pak delete ", " pak replace ", " pak import ", " pak encrypt ", " --apply"):
            self.assertNotIn(forbidden, lowered)
        self.assertNotIn("pakviewer-cli.exe", lowered)


if __name__ == "__main__":
    unittest.main()
