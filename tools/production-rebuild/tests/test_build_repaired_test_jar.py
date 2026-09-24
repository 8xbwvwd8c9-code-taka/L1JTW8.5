import hashlib
import importlib.util
import shutil
import subprocess
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "production-rebuild" / "build_repaired_test_jar.py"
INVERSE_PATH = ROOT / "tools" / "production-rebuild" / "inverse_remap.py"


def load_module(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def sha256(path: Path) -> str:
    h = hashlib.sha256()
    with path.open("rb") as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest().upper()


class BuildRepairedJarTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def compile_sources(self, root: Path):
        orig_src = root / "orig-src"
        orig_out = root / "orig-classes"
        norm_src = root / "norm-src"
        norm_out = root / "norm-classes"
        (orig_src / "ai").mkdir(parents=True)
        (orig_src / "aj").mkdir(parents=True)
        (norm_src / "l1r" / "ai").mkdir(parents=True)
        (norm_src / "l1r" / "aj").mkdir(parents=True)
        orig_out.mkdir()
        norm_out.mkdir()

        (orig_src / "ai" / "e.java").write_text(
            "package ai; public class e { public int oldValue = 1; }\n",
            encoding="utf-8",
        )
        (orig_src / "aj" / "bf.java").write_text(
            "package aj; public class bf { public ai.e handler; public int oldValue = 1; }\n",
            encoding="utf-8",
        )
        (norm_src / "l1r" / "ai" / "Handler.java").write_text(
            "package l1r.ai; public class Handler { public int oldValue = 1; }\n",
            encoding="utf-8",
        )
        (norm_src / "l1r" / "aj" / "Login.java").write_text(
            "package l1r.aj; public class Login { public l1r.ai.Handler handler; public int repairedValue = 2; }\n",
            encoding="utf-8",
        )

        subprocess.run(
            ["javac", "-source", "8", "-target", "8", "-d", str(orig_out),
             str(orig_src / "ai" / "e.java"), str(orig_src / "aj" / "bf.java")],
            check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True,
        )
        subprocess.run(
            ["javac", "-source", "8", "-target", "8", "-d", str(norm_out),
             str(norm_src / "l1r" / "ai" / "Handler.java"),
             str(norm_src / "l1r" / "aj" / "Login.java")],
            check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True,
        )
        return orig_out, norm_out

    def make_mapping(self, root: Path):
        mapping = root / "map.csv"
        mapping.write_text(
            "OldInternal,NewInternal,Kind\n"
            "ai/e,l1r/ai/Handler,TOP_LEVEL\n"
            "aj/bf,l1r/aj/Login,TOP_LEVEL\n",
            encoding="utf-8",
        )
        return mapping

    def make_original_jar(self, root: Path, orig_classes: Path):
        jar = root / "l1jserver2.jar"
        with zipfile.ZipFile(jar, "w", compression=zipfile.ZIP_DEFLATED) as z:
            z.writestr("META-INF/MANIFEST.MF", b"Manifest-Version: 1.0\nMain-Class: aj.bf\n\n")
            z.writestr("config/server.properties", b"Port=2000\n")
            z.write(orig_classes / "ai" / "e.class", "ai/e.class")
            z.write(orig_classes / "aj" / "bf.class", "aj/bf.class")
        return jar

    def test_build_replaces_only_selected_completed_class_and_preserves_original(self):
        m = load_module(MODULE_PATH, "build_repaired_test_jar")
        inverse = load_module(INVERSE_PATH, "inverse_remap_for_build_test")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            orig_classes, norm_classes = self.compile_sources(root)
            mapping = self.make_mapping(root)
            original = self.make_original_jar(root, orig_classes)
            original_hash = sha256(original)
            original_bytes = original.read_bytes()
            output = root / "out" / "l1jserver2.repaired-test.jar"

            result = m.build_repaired_test_jar(
                original_jar=original,
                normalized_classes=norm_classes,
                mapping_csv=mapping,
                selected_tops={"l1r/aj/Login"},
                output_jar=output,
                expected_original_sha256=original_hash,
                source_authority="completed/test@abc",
            )

            self.assertEqual(original.read_bytes(), original_bytes)
            self.assertEqual(sha256(original), original_hash)
            self.assertTrue(output.exists())
            self.assertEqual(result["replaced_class_count"], 1)
            self.assertEqual(result["production_jar_modified"], False)

            with zipfile.ZipFile(original) as z0, zipfile.ZipFile(output) as z1:
                self.assertEqual(z1.read("META-INF/MANIFEST.MF"), z0.read("META-INF/MANIFEST.MF"))
                self.assertEqual(z1.read("config/server.properties"), z0.read("config/server.properties"))
                self.assertEqual(z1.read("ai/e.class"), z0.read("ai/e.class"))
                self.assertNotEqual(z1.read("aj/bf.class"), z0.read("aj/bf.class"))
                self.assertNotIn("l1r/aj/Login.class", z1.namelist())
                self.assertEqual(inverse.class_internal_name(z1.read("aj/bf.class")), "aj/bf")
                self.assertIn("Lai/e;", inverse.class_utf8_values(z1.read("aj/bf.class")))

            self.assertTrue((output.parent / "REPLACED_CLASSES.md").exists())
            self.assertTrue((output.parent / "production_build_state.json").exists())

    def test_hash_gate_fails_closed_without_creating_output(self):
        m = load_module(MODULE_PATH, "build_repaired_test_jar_bad_hash")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            orig_classes, norm_classes = self.compile_sources(root)
            original = self.make_original_jar(root, orig_classes)
            output = root / "out.jar"
            with self.assertRaises(ValueError):
                m.build_repaired_test_jar(
                    original_jar=original,
                    normalized_classes=norm_classes,
                    mapping_csv=self.make_mapping(root),
                    selected_tops={"l1r/aj/Login"},
                    output_jar=output,
                    expected_original_sha256="00" * 32,
                    source_authority="completed/test@abc",
                )
            self.assertFalse(output.exists())

    def test_unselected_normalized_classes_are_not_patched(self):
        m = load_module(MODULE_PATH, "build_repaired_test_jar_selection")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            orig_classes, norm_classes = self.compile_sources(root)
            original = self.make_original_jar(root, orig_classes)
            output = root / "out.jar"
            m.build_repaired_test_jar(
                original_jar=original,
                normalized_classes=norm_classes,
                mapping_csv=self.make_mapping(root),
                selected_tops={"l1r/aj/Login"},
                output_jar=output,
                expected_original_sha256=sha256(original),
                source_authority="completed/test@abc",
            )
            with zipfile.ZipFile(original) as z0, zipfile.ZipFile(output) as z1:
                self.assertEqual(z1.read("ai/e.class"), z0.read("ai/e.class"))


if __name__ == "__main__":
    unittest.main()
