import importlib.util
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "compile_ready_authority.py"
PRE_STAGE_PATH = ROOT / "tools" / "850" / "bootstrap" / "pre_stage_normalization.py"
BOOTSTRAP_PATH = ROOT / "tools" / "850" / "bootstrap" / "bootstrap_core.py"


def load_path(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def load_module():
    return load_path(MODULE_PATH, "fast_dev_compile_ready_authority")


def git(root: Path, *args: str) -> str:
    proc = subprocess.run(
        ["git", *args],
        cwd=root,
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    return proc.stdout.strip()


class CompileReadyAuthorityContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("git") is None:
            raise unittest.SkipTest("git is required")

    def test_default_normalizers_exclude_compile_view_only_aliases(self):
        mod = load_module()
        self.assertNotIn(
            "normalize-external-builder-alias-refs.py",
            mod.NORMALIZER_SCRIPTS,
        )
        self.assertNotIn(
            "normalize-nonprotobuf-runtime-g-calls.py",
            mod.NORMALIZER_SCRIPTS,
        )

    def test_semantic_bootstrap_preserves_generic_comparators(self):
        bootstrap = load_path(BOOTSTRAP_PATH, "fast_dev_bootstrap_core_contract")
        cases = (
            (
                "l1r/au/L1Inventory",
                "private class L1R_a<T> implements Comparator<L1ItemInstance> {\n}",
                "Comparator<L1ItemInstance>",
            ),
            (
                "l1r/ao/RankingTable",
                "new Comparator<RankingTable.L1R_a>() {\n};",
                "Comparator<RankingTable.L1R_a>",
            ),
            (
                "l1r/ao/ShopTable",
                "new Comparator<L1ShopItem>() {\n};",
                "Comparator<L1ShopItem>",
            ),
        )
        for recovered_internal, source, expected in cases:
            with self.subTest(recovered_internal=recovered_internal):
                transformed = bootstrap._restore_donor_backed_decompiler_artifacts(
                    source,
                    recovered_internal,
                )
                self.assertIn(expected, transformed)
                self.assertNotIn("new Comparator()", transformed)
                self.assertNotIn("implements Comparator {", transformed)

    def test_semantic_bootstrap_uses_type_context_for_shadowed_runtime_g(self):
        bootstrap = load_path(BOOTSTRAP_PATH, "fast_dev_runtime_g_shadow_contract")
        source = "import a.g; class X { int g; Object x(byte[] data) { return g.a(data); } }"
        transformed = bootstrap._restore_donor_backed_decompiler_artifacts(
            source,
            "l1r/be/S_ProtoBuffers",
        )
        self.assertIn("return ((g)null).a(data);", transformed)
        self.assertNotIn("return g.a(data);", transformed)
        self.assertNotIn("((a.g)null).a(", transformed)

    def test_pre_stage_repairs_shadow_calls_and_generics_idempotently(self):
        self.assertTrue(PRE_STAGE_PATH.is_file())
        pre = load_path(PRE_STAGE_PATH, "fast_dev_pre_stage_normalization")
        with tempfile.TemporaryDirectory() as td:
            authority = Path(td)
            source = authority / "recovery" / "normalized-src-vf"
            craft = source / "l1r" / "aq" / "L1Craft.java"
            buddy = source / "l1r" / "aq" / "L1Buddy.java"
            craft.parent.mkdir(parents=True)
            craft.write_text(
                "package l1r.aq;\nimport a.g;\npublic class L1Craft {\n"
                "  int a; int g;\n"
                + "".join(f"  Object m{i}() {{ return a.g.a(v{i}); }}\n" for i in range(18))
                + "}\n",
                encoding="utf-8",
            )
            buddy.write_text(
                "package l1r.aq;\n"
                "class L1Buddy { void x() { for (Entry var3 : this.b.entrySet()) {} } }\n",
                encoding="utf-8",
            )

            first = pre.normalize_pre_stage_sources(authority)
            craft_once = craft.read_text(encoding="utf-8")
            buddy_once = buddy.read_text(encoding="utf-8")
            self.assertNotIn("import static a.g.a;", craft_once)
            self.assertEqual(craft_once.count("return ((g)null).a(v"), 18)
            self.assertNotIn("a.g.a(", craft_once)
            self.assertNotIn("return g.a(", craft_once)
            self.assertIn("Entry<Integer, String> var3", buddy_once)
            self.assertEqual(first["l1craft_static_owner_repairs"], 18)
            self.assertGreaterEqual(first["generic_type_repairs"], 1)

            second = pre.normalize_pre_stage_sources(authority)
            self.assertEqual(craft.read_text(encoding="utf-8"), craft_once)
            self.assertEqual(buddy.read_text(encoding="utf-8"), buddy_once)
            self.assertEqual(second["l1craft_static_owner_repairs"], 0)
            self.assertEqual(second["generic_type_repairs"], 0)

    def test_replays_pinned_normalizers_without_using_current_worktree_tools(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            repo = root / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            tools = repo / "tools" / "recovery"
            tools.mkdir(parents=True)
            (tools / "build-normalized-stage.py").write_text(
                "from pathlib import Path\n"
                "import shutil\n"
                "src=Path('recovery/normalized-src-vf')\n"
                "dst=Path('_normalized-stage-src')\n"
                "shutil.copytree(src,dst)\n",
                encoding="utf-8",
            )
            (tools / "normalize-marker.py").write_text(
                "from pathlib import Path\n"
                "p=Path('_normalized-stage-src/l1r/aa/A.java')\n"
                "p.write_text(p.read_text(encoding='utf-8').replace('RAW','READY'),encoding='utf-8')\n",
                encoding="utf-8",
            )
            git(repo, "add", ".")
            git(repo, "commit", "-m", "pinned compile normalizers")
            normalizer_commit = git(repo, "rev-parse", "HEAD")

            # Current worktree intentionally no longer has recovery tools. The
            # compile-ready stage must come from the pinned historical commit.
            shutil.rmtree(tools)
            git(repo, "add", "-A")
            git(repo, "commit", "-m", "remove historical recovery tools")

            authority = root / "authority"
            source = authority / "recovery" / "normalized-src-vf" / "l1r" / "aa"
            source.mkdir(parents=True)
            (source / "A.java").write_text(
                "package l1r.aa; public class A { String marker = \"RAW\"; }\n",
                encoding="utf-8",
            )
            (authority / "recovery" / "source_namespace_map.csv").write_text(
                "Kind,OldInternal,NewInternal\nTOP_LEVEL,aa/a,l1r/aa/A\n",
                encoding="utf-8",
            )

            output = root / "compile-ready-authority"
            result = mod.prepare_compile_ready_authority(
                repo,
                authority,
                output,
                normalizer_commit=normalizer_commit,
                script_names=("build-normalized-stage.py", "normalize-marker.py"),
                pre_stage=False,
            )

            transformed = output / "recovery" / "normalized-src-vf" / "l1r" / "aa" / "A.java"
            self.assertIn("READY", transformed.read_text(encoding="utf-8"))
            self.assertNotIn("RAW", transformed.read_text(encoding="utf-8"))
            self.assertEqual(result["normalizer_commit"], normalizer_commit)
            self.assertEqual(result["source_count"], 1)
            self.assertEqual(
                result["scripts"],
                ["build-normalized-stage.py", "normalize-marker.py"],
            )
            self.assertFalse((output / "_normalized-stage-src").exists())
            self.assertFalse((output / "tools" / "recovery").exists())

    def test_normalizer_failure_is_fail_closed_and_does_not_publish_output(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            repo = root / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")
            tools = repo / "tools" / "recovery"
            tools.mkdir(parents=True)
            (tools / "build-normalized-stage.py").write_text(
                "raise SystemExit('intentional normalizer failure')\n",
                encoding="utf-8",
            )
            git(repo, "add", ".")
            git(repo, "commit", "-m", "failing normalizer")
            normalizer_commit = git(repo, "rev-parse", "HEAD")

            authority = root / "authority"
            source = authority / "recovery" / "normalized-src-vf"
            source.mkdir(parents=True)
            (source / "A.java").write_text("class A {}\n", encoding="utf-8")
            output = root / "compile-ready-authority"

            with self.assertRaisesRegex(RuntimeError, "build-normalized-stage.py"):
                mod.prepare_compile_ready_authority(
                    repo,
                    authority,
                    output,
                    normalizer_commit=normalizer_commit,
                    script_names=("build-normalized-stage.py",),
                    pre_stage=False,
                )
            self.assertFalse(output.exists())

    def test_authority_cache_wires_compile_ready_before_semantic_migration(self):
        cache_path = ROOT / "tools" / "850" / "bootstrap" / "authority_cache.py"
        text = cache_path.read_text(encoding="utf-8")
        self.assertIn(
            '_COMPILE_READY = _load_local("compile_ready_authority.py"',
            text,
        )
        prepare = "_COMPILE_READY.prepare_compile_ready_authority("
        migrate = "_MIGRATE.materialize_sources("
        self.assertIn(prepare, text)
        self.assertIn(migrate, text)
        self.assertLess(text.index(prepare), text.index(migrate))
        self.assertIn("AUTHORITY_CACHE_SCHEMA_VERSION = 5", text)


if __name__ == "__main__":
    unittest.main()
