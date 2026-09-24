import re
import unittest
from pathlib import Path


REPO_ROOT = Path(__file__).resolve().parents[3]
DRIVER = REPO_ROOT / "tools" / "production-rebuild" / "build-production-test.ps1"
RUNBOOK = REPO_ROOT / "recovery" / "BUILD_REPRODUCTION.md"

EXPECTED_ORIGINAL_SHA256 = "8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814"
EXPECTED_COMPLETED_REF = "3d4a392593d61c9203e3f22a154e752c2c51fa97"


class LocalProductionDriverContractTests(unittest.TestCase):
    def test_driver_is_pinned_and_never_overwrites_production_jar(self):
        self.assertTrue(DRIVER.is_file(), f"missing local driver: {DRIVER}")
        text = DRIVER.read_text(encoding="utf-8")

        self.assertIn("I:\\L1JTW8.5", text)
        self.assertIn(EXPECTED_ORIGINAL_SHA256, text)
        self.assertIn(EXPECTED_COMPLETED_REF, text)
        self.assertIn("run_production_rebuild.py", text)
        self.assertIn("l1jserver2.repaired-test.jar", text)
        self.assertIn("Get-FileHash", text)

        self.assertNotRegex(text, r"(?i)Copy-Item[^\r\n]*l1jserver2\.jar")
        self.assertNotRegex(text, r"(?i)Move-Item[^\r\n]*l1jserver2\.jar")
        self.assertNotRegex(text, r"(?i)Remove-Item[^\r\n]*l1jserver2\.jar")
        self.assertNotRegex(text, r"(?i)Rename-Item[^\r\n]*l1jserver2\.jar")

    def test_runbook_preserves_manual_runtime_and_login_boundaries(self):
        self.assertTrue(RUNBOOK.is_file(), f"missing runbook: {RUNBOOK}")
        text = RUNBOOK.read_text(encoding="utf-8")

        for required in (
            "I:\\L1JTW8.5",
            "I:\\8.50c客服端\\Lin.bin2",
            EXPECTED_ORIGINAL_SHA256,
            "l1jserver2.repaired-test.jar",
            "C3P0",
            "port 2000",
            "account login",
            "character select",
            "enter-game",
            "useSSL=false",
        ):
            self.assertIn(required, text)

        self.assertRegex(text, re.compile(r"(?i)never.*overwrite.*l1jserver2\.jar"))


if __name__ == "__main__":
    unittest.main()
