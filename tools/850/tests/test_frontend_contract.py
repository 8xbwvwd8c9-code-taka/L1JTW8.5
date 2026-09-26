import importlib.util
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "fast_dev.py"
PS1_PATH = ROOT / "build850.ps1"
CMD_PATH = ROOT / "build850.cmd"


def load_module():
    spec = importlib.util.spec_from_file_location("fast_dev_frontend", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class FrontendContractTests(unittest.TestCase):
    def test_default_mode_is_incremental(self):
        mod = load_module()
        args = mod.parse_cli([])
        self.assertEqual(args.mode, "incremental")
        self.assertFalse(args.run)
        self.assertFalse(args.watch)

    def test_run_classpath_prioritizes_overlay_then_dev_base_then_lib(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            cp = mod.runtime_classpath(root)
            self.assertEqual(cp[0], root / ".build850" / "classes")
            self.assertEqual(cp[1], root / ".build850" / "cache" / "850-dev-base.jar")
            self.assertEqual(cp[2], root / "lib" / "*")

    def test_run_server_matches_production_noverify_policy(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            config = root / "config"
            config.mkdir()
            (config / "server.properties").write_text(
                "URL=jdbc:mysql://localhost/8.5?useUnicode=true&characterEncoding=utf8&useSSL=false\n",
                encoding="utf-8",
            )
            calls = []

            def fake_call(command, cwd=None):
                calls.append((list(command), Path(cwd)))
                return 0

            mod.subprocess.call = fake_call
            rc = mod.run_server(root)

            self.assertEqual(rc, 0)
            self.assertEqual(len(calls), 1)
            command, cwd = calls[0]
            self.assertEqual(command[0:2], ["java", "-noverify"])
            self.assertEqual(command[-1], "l1j.server.Server")
            self.assertEqual(cwd, root)

    def test_watch_monitors_core_src_only(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            self.assertEqual(mod.watch_root(root), root / "core" / "src")

    def test_watch_recompiles_once_after_core_source_change(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            source = root / "core" / "src" / "l1j" / "server" / "Example.java"
            source.parent.mkdir(parents=True)
            source.write_text("class Example {}\n", encoding="utf-8")

            class FakeCompiler:
                def __init__(self):
                    self.calls = 0

                def compile_changed(self):
                    self.calls += 1
                    return {"mode": "incremental", "compiled_identities": ["l1j.server.Example"]}

            compiler = FakeCompiler()
            sleeps = 0
            original_sleep = mod.time.sleep

            def fake_sleep(_interval):
                nonlocal sleeps
                sleeps += 1
                if sleeps == 1:
                    source.write_text("class Example { int changed; }\n", encoding="utf-8")
                    return
                raise KeyboardInterrupt

            mod.time.sleep = fake_sleep
            try:
                mod.watch_loop(root, compiler, interval=0)
            finally:
                mod.time.sleep = original_sleep

            self.assertEqual(compiler.calls, 1)

    def test_clean_removes_only_fast_dev_state_and_preserves_production_jar(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            build = root / ".build850"
            build.mkdir()
            (build / "state.json").write_text("{}", encoding="utf-8")
            prod = root / "l1jserver2.jar"
            prod.write_bytes(b"production")
            recovery = root / "recovery"
            recovery.mkdir()
            (recovery / "keep.txt").write_text("keep", encoding="utf-8")
            mod.clean_build_state(root)
            self.assertFalse(build.exists())
            self.assertEqual(prod.read_bytes(), b"production")
            self.assertTrue((recovery / "keep.txt").is_file())

    def test_clean_mode_rebootstraps_after_clearing_state(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            build = root / ".build850"
            build.mkdir()
            (build / "stale.txt").write_text("stale", encoding="utf-8")
            calls = []
            mod.ROOT = root

            def fake_ensure_baseline(repo_root):
                repo_root = Path(repo_root)
                calls.append(repo_root)
                self.assertFalse((repo_root / ".build850" / "stale.txt").exists())
                cache = repo_root / ".build850" / "cache"
                cache.mkdir(parents=True, exist_ok=True)
                (cache / "850-dev-base.jar").write_bytes(b"base")
                (repo_root / ".build850" / "state.json").write_text("{}", encoding="utf-8")
                (repo_root / ".build850" / "dependency-index.json").write_text("{}", encoding="utf-8")

            mod._ensure_baseline = fake_ensure_baseline
            rc = mod.main(["-Clean"])

            self.assertEqual(rc, 0)
            self.assertEqual(calls, [root])
            self.assertTrue((root / ".build850" / "cache" / "850-dev-base.jar").is_file())
            self.assertTrue((root / ".build850" / "state.json").is_file())
            self.assertTrue((root / ".build850" / "dependency-index.json").is_file())

    def test_cli_rejects_conflicting_compile_modes(self):
        mod = load_module()
        with self.assertRaises(SystemExit):
            mod.parse_cli(["-Full", "-Clean"])

    def test_compiler_bootstraps_missing_baseline_automatically(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            calls = []

            class FakeBootstrap:
                @staticmethod
                def ensure_fast_dev(repo_root):
                    calls.append(Path(repo_root))
                    build = Path(repo_root) / ".build850"
                    dev_base = build / "cache" / "850-dev-base.jar"
                    dev_base.parent.mkdir(parents=True, exist_ok=True)
                    dev_base.write_bytes(b"base")
                    (build / "state.json").write_text("{}", encoding="utf-8")
                    (build / "dependency-index.json").write_text("{}", encoding="utf-8")

            class FakeCompiler:
                def __init__(self, **kwargs):
                    self.kwargs = kwargs

            class FakeIncremental:
                IncrementalCompiler = FakeCompiler

            original_loader = mod._load_module

            def fake_loader(path, name):
                path = Path(path)
                if path.name == "ensure_dev.py":
                    return FakeBootstrap
                if path.name == "incremental.py":
                    return FakeIncremental
                return original_loader(path, name)

            mod._load_module = fake_loader
            compiler = mod._compiler(root)

            self.assertEqual(calls, [root])
            self.assertEqual(
                compiler.kwargs["classpath"][0],
                root / ".build850" / "cache" / "850-dev-base.jar",
            )

    def test_compiler_ready_fast_path_does_not_refresh_bootstrap(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            build = root / ".build850"
            dev_base = build / "cache" / "850-dev-base.jar"
            dev_base.parent.mkdir(parents=True)
            dev_base.write_bytes(b"base")
            (build / "state.json").write_text("{}", encoding="utf-8")
            (build / "dependency-index.json").write_text("{}", encoding="utf-8")

            class FakeCompiler:
                def __init__(self, **kwargs):
                    self.kwargs = kwargs

            class FakeIncremental:
                IncrementalCompiler = FakeCompiler

            original_loader = mod._load_module

            def fake_loader(path, name):
                path = Path(path)
                if path.name == "ensure_dev.py":
                    raise AssertionError("ready fast path must not bootstrap/fetch")
                if path.name == "incremental.py":
                    return FakeIncremental
                return original_loader(path, name)

            mod._load_module = fake_loader
            compiler = mod._compiler(root)
            self.assertEqual(compiler.kwargs["state_path"], build / "state.json")

    def test_sync_completed_forces_latest_completed_authority_and_working_source_refresh(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            calls = []

            class FakeBootstrap:
                @staticmethod
                def ensure_fast_dev(repo_root, *, fetch_latest=True, sync_working_core=False):
                    calls.append((Path(repo_root), fetch_latest, sync_working_core))
                    return {
                        "authority_commit": "b" * 40,
                        "completed_source_count": 17,
                        "rebuilt": True,
                        "core_action": "synced",
                    }

            original_loader = mod._load_module

            def fake_loader(path, name):
                if Path(path).name == "ensure_dev.py":
                    return FakeBootstrap
                return original_loader(path, name)

            mod._load_module = fake_loader
            result = mod.sync_completed(root)

            self.assertEqual(calls, [(root, True, True)])
            self.assertEqual(result["authority_commit"], "b" * 40)
            self.assertTrue(result["rebuilt"])
            self.assertEqual(result["core_action"], "synced")

    def test_sync_mode_calls_sync_helper_without_compiling(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            calls = []
            mod.ROOT = root
            mod.sync_completed = lambda repo_root: calls.append(Path(repo_root)) or {
                "authority_commit": "c" * 40,
                "completed_source_count": 9,
                "rebuilt": False,
                "core_action": "preserved",
            }
            mod._compiler = lambda *_args, **_kwargs: (_ for _ in ()).throw(
                AssertionError("-Sync must not compile working sources")
            )

            rc = mod.main(["-Sync"])

            self.assertEqual(rc, 0)
            self.assertEqual(calls, [root])

    def test_wrappers_are_thin_and_do_not_replace_production_jar(self):
        self.assertTrue(PS1_PATH.is_file())
        self.assertTrue(CMD_PATH.is_file())
        ps1 = PS1_PATH.read_text(encoding="utf-8")
        cmd = CMD_PATH.read_text(encoding="utf-8")
        self.assertIn("tools/850/fast_dev.py", ps1.replace("\\", "/"))
        self.assertIn("build850.ps1", cmd)
        forbidden = ["Copy-Item", "Move-Item", "Rename-Item", "Remove-Item l1jserver2.jar"]
        for token in forbidden:
            self.assertNotIn(token, ps1)


if __name__ == "__main__":
    unittest.main()
