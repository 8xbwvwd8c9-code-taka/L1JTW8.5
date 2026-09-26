import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
SERVER_START = ROOT / "ServerStart.bat"
SERVER_PROPERTIES = ROOT / "config" / "server.properties"
WORKFLOW = ROOT / ".github" / "workflows" / "850-fast-dev.yml"


class LocalStartupContractTests(unittest.TestCase):
    def test_server_start_runs_fast_dev_instead_of_production_jar(self):
        text = SERVER_START.read_text(encoding="utf-8-sig")
        normalized = text.replace("\\", "/").lower()
        self.assertIn("build850.ps1", normalized)
        self.assertIn("-run", normalized)
        self.assertNotIn("-jar l1jserver2.jar", normalized)

    def test_server_properties_disables_legacy_mysql_ssl_negotiation(self):
        text = SERVER_PROPERTIES.read_text(encoding="utf-8-sig")
        url_lines = [line.strip() for line in text.splitlines() if line.strip().startswith("URL=")]
        self.assertEqual(len(url_lines), 1)
        self.assertIn("useSSL=false", url_lines[0])

    def test_ci_does_not_patch_jdbc_url_at_runtime(self):
        text = WORKFLOW.read_text(encoding="utf-8")
        self.assertNotIn("RUNTIME_JDBC_SSL_DISABLED", text)
        self.assertNotIn("text = text.replace(base, secure, 1)", text)


if __name__ == "__main__":
    unittest.main()
