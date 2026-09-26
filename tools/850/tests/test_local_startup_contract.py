import importlib.util
import tempfile
import unittest
import zipfile
from pathlib import Path
from unittest import mock


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


def write_dev_base(path: Path, *, include_server: bool) -> None:
    path.parent.mkdir(parents=True, exist_ok=True)
    entry = "l1j/server/Server.class" if include_server else "l1j/server/NotServer.class"
    with zipfile.ZipFile(path, "w") as archive:
        archive.writestr(entry, b"placeholder")


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

    def test_stale_dev_base_retries_transient_windows_delete_denial(self):
        mod = load_fast_dev()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            build = root / ".build850"
            dev_base = build / "cache" / "850-dev-base.jar"
            write_dev_base(dev_base, include_server=False)
            (build / "state.json").write_text("{}", encoding="utf-8")
            (build / "dependency-index.json").write_text("{}", encoding="utf-8")
            (build / "cache" / "850-dev-base.key.json").write_text("{}", encoding="utf-8")

            class FakeBootstrap:
                @staticmethod
                def ensure_fast_dev(repo_root):
                    repo_root = Path(repo_root)
                    write_dev_base(
                        repo_root / ".build850" / "cache" / "850-dev-base.jar",
                        include_server=True,
                    )
                    (repo_root / ".build850" / "state.json").write_text("{}", encoding="utf-8")
                    (repo_root / ".build850" / "dependency-index.json").write_text("{}", encoding="utf-8")

            original_loader = mod._load_module

            def fake_loader(path, name):
                if Path(path).name == "ensure_dev.py":
                    return FakeBootstrap
                return original_loader(path, name)

            mod._load_module = fake_loader
            original_unlink = Path.unlink
            attempts = 0

            def transient_unlink(path_self, *args, **kwargs):
                nonlocal attempts
                if Path(path_self) == dev_base:
                    attempts += 1
                    if attempts == 1:
                        raise PermissionError(5, "Access is denied", str(path_self))
                return original_unlink(path_self, *args, **kwargs)

            with mock.patch.object(Path, "unlink", transient_unlink):
                mod._ensure_baseline(root)

            self.assertEqual(attempts, 2)
            self.assertTrue(mod._baseline_ready(root))

    def test_stale_dev_base_quarantines_after_persistent_windows_delete_denial(self):
        mod = load_fast_dev()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            build = root / ".build850"
            cache = build / "cache"
            dev_base = cache / "850-dev-base.jar"
            write_dev_base(dev_base, include_server=False)
            (build / "state.json").write_text("{}", encoding="utf-8")
            (build / "dependency-index.json").write_text("{}", encoding="utf-8")
            (cache / "850-dev-base.key.json").write_text("{}", encoding="utf-8")

            quarantined_seen = []

            class FakeBootstrap:
                @staticmethod
                def ensure_fast_dev(repo_root):
                    repo_root = Path(repo_root)
                    active = repo_root / ".build850" / "cache" / "850-dev-base.jar"
                    quarantined = sorted(active.parent.glob("850-dev-base.jar.stale-*"))
                    if active.exists():
                        raise AssertionError("stale active dev-base must be moved before bootstrap")
                    if len(quarantined) != 1:
                        raise AssertionError(f"expected exactly one quarantined dev-base, got {quarantined}")
                    quarantined_seen.extend(quarantined)
                    write_dev_base(active, include_server=True)
                    (repo_root / ".build850" / "state.json").write_text("{}", encoding="utf-8")
                    (repo_root / ".build850" / "dependency-index.json").write_text("{}", encoding="utf-8")

            original_loader = mod._load_module

            def fake_loader(path, name):
                if Path(path).name == "ensure_dev.py":
                    return FakeBootstrap
                return original_loader(path, name)

            mod._load_module = fake_loader
            original_unlink = Path.unlink
            attempts = 0

            def persistent_unlink(path_self, *args, **kwargs):
                nonlocal attempts
                if Path(path_self) == dev_base:
                    attempts += 1
                    raise PermissionError(5, "Access is denied", str(path_self))
                return original_unlink(path_self, *args, **kwargs)

            with mock.patch.object(Path, "unlink", persistent_unlink):
                mod._ensure_baseline(root)

            self.assertEqual(attempts, 5)
            self.assertEqual(len(quarantined_seen), 1)
            self.assertFalse(quarantined_seen[0].exists())
            self.assertTrue(mod._baseline_ready(root))

    def test_ci_uses_shared_runtime_config_helper_not_inline_jdbc_patch(self):
        text = WORKFLOW.read_text(encoding="utf-8")
        self.assertIn("ensure_runtime_jdbc_config", text)
        self.assertNotIn("RUNTIME_JDBC_SSL_DISABLED", text)
        self.assertNotIn("text = text.replace(base, secure, 1)", text)


if __name__ == "__main__":
    unittest.main()
