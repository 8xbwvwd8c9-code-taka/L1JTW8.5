import importlib.util
import json
import shutil
import subprocess
import tempfile
import textwrap
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "850" / "bootstrap" / "build_dev_base.py"


def load_module():
    spec = importlib.util.spec_from_file_location("build_dev_base", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class DevBaseTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def make_fixture_jar(self, root: Path):
        src = root / "src"
        out = root / "classes"
        (src / "oldpkg").mkdir(parents=True)
        out.mkdir()
        (src / "oldpkg" / "Helper.java").write_text(
            "package oldpkg; public class Helper {}\n", encoding="utf-8"
        )
        (src / "oldpkg" / "Root.java").write_text(
            textwrap.dedent(
                """
                package oldpkg;
                import java.util.List;
                public class Root {
                    public oldpkg.Helper helper;
                    public List<oldpkg.Helper> helpers;
                    public String marker = "literal oldpkg/Helper must stay literal";
                    public static class Named { public oldpkg.Helper nested; }
                }
                """
            ).strip() + "\n",
            encoding="utf-8",
        )
        subprocess.run(
            ["javac", "-source", "8", "-target", "8", "-d", str(out),
             str(src / "oldpkg" / "Helper.java"), str(src / "oldpkg" / "Root.java")],
            check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True,
        )
        jar_path = root / "original.jar"
        with zipfile.ZipFile(jar_path, "w") as zf:
            for path in out.rglob("*.class"):
                zf.write(path, path.relative_to(out).as_posix())
            zf.writestr("data/keep.txt", b"resource-bytes")
            zf.writestr("META-INF/MANIFEST.MF", "Manifest-Version: 1.0\nMain-Class: oldpkg.Root\n\n")
        return jar_path

    def mapping(self):
        return {
            "oldpkg/Helper": "dev/readable/Helper",
            "oldpkg/Root": "dev/readable/Root",
            "oldpkg/Root$Named": "dev/readable/Root$Named",
        }

    def test_relocates_internal_names_descriptors_signatures_and_inner_classes(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            output = root / "dev.jar"
            mod.build_dev_base(original, output, self.mapping())
            with zipfile.ZipFile(output) as zf:
                self.assertIn("dev/readable/Root.class", zf.namelist())
                self.assertIn("dev/readable/Root$Named.class", zf.namelist())
                data = zf.read("dev/readable/Root.class")
            values = mod.class_utf8_values(data)
            structural = "\n".join(values)
            self.assertIn("dev/readable/Helper", structural)
            self.assertNotIn("Loldpkg/Helper;", structural)

    def test_preserves_plain_string_constants(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            output = root / "dev.jar"
            mod.build_dev_base(original, output, self.mapping())
            with zipfile.ZipFile(output) as zf:
                values = mod.class_utf8_values(zf.read("dev/readable/Root.class"))
            self.assertIn("literal oldpkg/Helper must stay literal", values)

    def test_preserves_non_class_resources(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            output = root / "dev.jar"
            mod.build_dev_base(original, output, self.mapping())
            with zipfile.ZipFile(output) as zf:
                self.assertEqual(zf.read("data/keep.txt"), b"resource-bytes")

    def test_original_jar_is_immutable(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            before = mod.sha256_file(original)
            mod.build_dev_base(original, root / "dev.jar", self.mapping())
            self.assertEqual(mod.sha256_file(original), before)

    def test_cache_key_hit_and_miss(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            package_map = root / "package-map.csv"
            package_map.write_text("a,b\n", encoding="utf-8")
            key1 = mod.make_cache_key(original, package_map, java_major=8, schema_version="1")
            key2 = mod.make_cache_key(original, package_map, java_major=8, schema_version="1")
            self.assertEqual(key1, key2)
            package_map.write_text("a,c\n", encoding="utf-8")
            key3 = mod.make_cache_key(original, package_map, java_major=8, schema_version="1")
            self.assertNotEqual(key1, key3)
            cache_file = root / "cache-key.json"
            cache_file.write_text(json.dumps(key1), encoding="utf-8")
            self.assertTrue(mod.cache_matches(cache_file, key1))
            self.assertFalse(mod.cache_matches(cache_file, key3))

    def test_unmapped_application_class_fails_closed(self):
        mod = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original = self.make_fixture_jar(root)
            mapping = {"oldpkg/Root": "dev/readable/Root"}
            with self.assertRaises(KeyError):
                mod.build_dev_base(original, root / "dev.jar", mapping)


if __name__ == "__main__":
    unittest.main()
