import importlib.util
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
SERVER_START = ROOT / "ServerStart.bat"
FAST_DEV = ROOT / "tools" / "850" / "fast_dev.py"
WORKFLOW = ROOT / ".github" / "workflows" / "850-fast-dev.yml"


def load_fast_dev():
    spec = importlib.util.spec_from_file_location("fast_dev_local_startup", FAST_DEV)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {FAST_DEV}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class LocalStartupContractTests(unittest.TestCase):
    def test_server_start_runs_fast_dev_instead_of_production_jar(self):
        text = SERVER_START.read_text(encoding="utf-8-sig")
        normalized = text.replace("\\", "/").lower()
        self.assertIn("build850.ps1", normalized)
        self.assertIn("-run", normalized)
        self.assertNotIn("-jar l1jserver2.jar", normalized)

    def test_runtime_config_disables_legacy_mysql_ssl_without_touching_credentials(self):
        mod = load_fast_dev()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            config = root / "config"
            config.mkdir(parents=True)
            path = config / "server.properties"
            original = (
                "URL=jdbc:mysql://localhost/8.5?useUnicode=true&characterEncoding=utf8\n"
                "Login=root\n"
                "Password=keep-this-value\n"
            )
            path.write_text(original, encoding="utf-8")

            changed = mod.ensure_runtime_jdbc_config(root)
            text = path.read_text(encoding="utf-8-sig")

            self.assertTrue(changed)
            self.assertIn(
                "URL=jdbc:mysql://localhost/8.5?useUnicode=true&characterEncoding=utf8&useSSL=false",
                text,
            )
            self.assertIn("Login=root", text)
            self.assertIn("Password=keep-this-value", text)

            after_first = path.read_bytes()
            self.assertFalse(mod.ensure_runtime_jdbc_config(root))
            self.assertEqual(path.read_bytes(), after_first)

    def test_ci_uses_shared_runtime_config_helper_not_inline_jdbc_patch(self):
        text = WORKFLOW.read_text(encoding="utf-8")
        self.assertIn("ensure_runtime_jdbc_config", text)
        self.assertNotIn("RUNTIME_JDBC_SSL_DISABLED", text)
        self.assertNotIn("text = text.replace(base, secure, 1)", text)


if __name__ == "__main__":
    unittest.main()
