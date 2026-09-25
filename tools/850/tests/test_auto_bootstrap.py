import importlib.util
import json
import shutil
import subprocess
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "auto_bootstrap.py"
BUILD_DEV_BASE_PATH = ROOT / "tools" / "850" / "bootstrap" / "build_dev_base.py"


def load(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


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


def write_authority(root: Path, marker: str) -> None:
    recovery = root / "recovery"
    normalized = recovery / "normalized-src-vf" / "l1r" / "aa"
    normalized.mkdir(parents=True, exist_ok=True)
    (recovery / "source_namespace_map.csv").write_text(
        "Kind,OldInternal,NewInternal\nTOP_LEVEL,aa/a,l1r/aa/A\n",
        encoding="utf-8",
    )
    (recovery / "source_namespace_state.json").write_text(
        json.dumps(
            {
                "top_level_mappings": 1,
                "application_class_mappings": 1,
                "duplicate_sourcefile_groups": 0,
            }
        ),
        encoding="utf-8",
    )
    (normalized / "A.java").write_text(
        "package l1r.aa; public class A { "
        f"public static final String MARKER = \"{marker}\"; "
        "public String marker() { return MARKER; } }\n",
        encoding="utf-8",
    )

    rules = root / "tools" / "850" / "bootstrap" / "package_rules.json"
    rules.parent.mkdir(parents=True, exist_ok=True)
    rules.write_text(
        json.dumps(
            {
                "families": {"aa": {"package": "l1j/server/test", "category": "test"}},
                "overrides": {},
            }
        ),
        encoding="utf-8",
    )


def make_original_jar(root: Path) -> Path:
    src = root / "prod-src" / "aa"
    classes = root / "prod-classes"
    src.mkdir(parents=True)
    classes.mkdir()
    (src / "a.java").write_text(
        "package aa; public class a { "
        "public static final String MARKER = \"production-old\"; "
        "public String marker() { return MARKER; } }\n",
        encoding="utf-8",
    )
    subprocess.run(
        [
            "javac", "-encoding", "UTF-8", "-source", "8", "-target", "8",
            "-d", str(classes), str(src / "a.java"),
        ],
        check=True,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        text=True,
    )
    jar = root / "l1jserver2.jar"
    with zipfile.ZipFile(jar, "w") as archive:
        archive.write(classes / "aa" / "a.class", "aa/a.class")
        archive.writestr("META-INF/MANIFEST.MF", "Manifest-Version: 1.0\n\n")
        archive.writestr("config/keep.txt", b"resource")
    return jar


class AutoBootstrapContracts(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        for command in ("git", "javac"):
            if shutil.which(command) is None:
                raise unittest.SkipTest(f"{command} is required")

    def test_first_bootstrap_uses_completed_promotion_and_excludes_work_head(self):
        mod = load(MODULE_PATH, "fast_dev_auto_bootstrap")
        class_tools = load(BUILD_DEV_BASE_PATH, "fast_dev_auto_bootstrap_class_tools")

        with tempfile.TemporaryDirectory() as td:
            repo = Path(td) / "repo"
            repo.mkdir()
            git(repo, "init")
            git(repo, "config", "user.email", "test@example.invalid")
            git(repo, "config", "user.name", "Fast Dev Test")

            write_authority(repo, "baseline")
            git(repo, "add", ".")
            git(repo, "commit", "-m", "recovery baseline")
            baseline = git(repo, "rev-parse", "HEAD")

            write_authority(repo, "completed-repair")
            git(repo, "add", ".")
            git(repo, "commit", "-m", "fix(l2): promote BUG-850-001 completed repair")
            completed = git(repo, "rev-parse", "HEAD")
            git(repo, "branch", mod.COMPLETED_BRANCH)

            write_authority(repo, "work-in-progress-must-not-win")
            git(repo, "add", ".")
            git(repo, "commit", "-m", "wip repair")
            self.assertNotEqual(git(repo, "rev-parse", "HEAD"), completed)

            original = make_original_jar(repo)
            before = mod.sha256_file(original)

            result = mod.bootstrap_fast_dev(
                repo,
                fetch_latest=False,
                baseline_commit=baseline,
            )

            self.assertEqual(result["completed_authority_commit"], completed)
            self.assertEqual(result["promoted_source_count"], 1)
            self.assertEqual(mod.sha256_file(original), before)

            core_source = repo / "core" / "src" / "l1j" / "server" / "test" / "A.java"
            self.assertTrue(core_source.is_file())
            source_text = core_source.read_text(encoding="utf-8")
            self.assertIn("completed-repair", source_text)
            self.assertNotIn("work-in-progress-must-not-win", source_text)

            dev_base = repo / ".build850" / "cache" / "850-dev-base.jar"
            self.assertTrue(dev_base.is_file())
            with zipfile.ZipFile(dev_base) as archive:
                values = class_tools.class_utf8_values(
                    archive.read("l1j/server/test/A.class")
                )
                self.assertEqual(archive.read("config/keep.txt"), b"resource")
            self.assertIn("completed-repair", values)
            self.assertNotIn("production-old", values)
            self.assertNotIn("work-in-progress-must-not-win", values)

            state = json.loads((repo / ".build850" / "state.json").read_text(encoding="utf-8"))
            deps = json.loads(
                (repo / ".build850" / "dependency-index.json").read_text(encoding="utf-8")
            )
            self.assertEqual(state["sources"]["l1j/server/test/A.java"]["origin"], "baseline")
            self.assertTrue(deps["complete"])

            marker = json.loads((repo / "core" / "FAST_DEV_AUTHORITY.json").read_text(encoding="utf-8"))
            self.assertEqual(marker["commit"], completed)
            self.assertEqual(marker["baseline_commit"], baseline)


if __name__ == "__main__":
    unittest.main()
