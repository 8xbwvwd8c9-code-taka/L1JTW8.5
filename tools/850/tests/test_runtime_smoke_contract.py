from pathlib import Path
import unittest


ROOT = Path(__file__).resolve().parents[3]
WORKFLOW = ROOT / ".github" / "workflows" / "850-fast-dev.yml"


class FastDevRuntimeSmokeContract(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.text = WORKFLOW.read_text(encoding="utf-8")

    def test_uses_mysql57_with_850_legacy_sql_mode_and_real_dump(self):
        self.assertIn("mysql:5.7", self.text)
        self.assertIn("--sql-mode=NO_ENGINE_SUBSTITUTION", self.text)
        self.assertIn("db/8.5.sql", self.text)
        self.assertIn("MYSQL_AUTH_READY=YES", self.text)

    def test_runtime_uses_fast_dev_overlay_classpath_not_production_jar(self):
        self.assertIn(".build850/classes:.build850/cache/850-dev-base.jar:lib/*", self.text)
        self.assertIn("l1j.server.Server", self.text)
        self.assertNotIn("java -jar l1jserver2.jar", self.text)

    def test_runtime_smoke_disables_legacy_mysql_ssl_only_in_runner(self):
        self.assertIn("useSSL=false", self.text)
        self.assertIn("RUNTIME_JDBC_SSL_DISABLED=YES", self.text)

    def test_runtime_smoke_requires_live_process_and_port_2000(self):
        self.assertIn("FAST_DEV_RUNTIME_SMOKE=PASS", self.text)
        self.assertIn("PORT_2000=LISTENING", self.text)
        self.assertIn("127.0.0.1", self.text)
        self.assertIn("2000", self.text)

    def test_production_jar_sha_is_checked_before_and_after(self):
        sha = "8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814"
        self.assertGreaterEqual(self.text.count(sha), 1)
        self.assertIn('original_sha="$(sha256sum l1jserver2.jar', self.text)
        self.assertIn('sha256sum l1jserver2.jar', self.text)
        self.assertIn('= "$original_sha"', self.text)


if __name__ == "__main__":
    unittest.main()
