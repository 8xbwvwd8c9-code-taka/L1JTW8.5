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
VALIDATOR_PATH = ROOT / "tools" / "production-rebuild" / "validate_repaired_test_jar.py"
INVERSE_PATH = ROOT / "tools" / "production-rebuild" / "inverse_remap.py"


def load_module(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(path)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class ValidateRepairedJarTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def compile_fixture(self, root: Path):
        src = root / "src"
        out = root / "classes"
        (src / "l1r" / "aj").mkdir(parents=True)
        out.mkdir()
        (src / "l1r" / "aj" / "Login.java").write_text(
            textwrap.dedent(
                """
                package l1r.aj;
                public class Login {
                    public String marker = "literal:l1r/aj/Login";
                }
                """
            ).strip() + "\n",
            encoding="utf-8",
        )
        subprocess.run(
            ["javac", "-source", "8", "-target", "8", "-d", str(out), str(src / "l1r" / "aj" / "Login.java")],
            check=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        return out

    def mapping_csv(self, root: Path):
        p = root / "map.csv"
        p.write_text(
            "OldInternal,NewInternal,Kind\n"
            "aj/bf,l1r/aj/Login,TOP_LEVEL\n",
            encoding="utf-8",
        )
        return p

    def make_jars(self, root: Path):
        inverse = load_module(INVERSE_PATH, "inverse_for_validator_test")
        classes = self.compile_fixture(root)
        reverse = inverse.load_reverse_map(self.mapping_csv(root))
        remapped = inverse.remap_class_bytes((classes / "l1r" / "aj" / "Login.class").read_bytes(), reverse)

        original = root / "original.jar"
        test = root / "test.jar"
        manifest = b"Manifest-Version: 1.0\r\nMain-Class: l1j.server.Server\r\n\r\n"
        resource = b"UNCHANGED-RESOURCE"
        donor_class = b"\xca\xfe\xba\xbeDONOR"
        with zipfile.ZipFile(original, "w") as z:
            z.writestr("META-INF/MANIFEST.MF", manifest)
            z.writestr("data/demo.bin", resource)
            z.writestr("aj/bf.class", donor_class)
        with zipfile.ZipFile(test, "w") as z:
            z.writestr("META-INF/MANIFEST.MF", manifest)
            z.writestr("data/demo.bin", resource)
            z.writestr("aj/bf.class", remapped)

        state = {
            "replaced_classes": [{
                "ORIGINAL_CLASS_NAME": "aj/bf",
                "NORMALIZED_CLASS_NAME": "l1r/aj/Login",
                "REMAPPED_RUNTIME_CLASS_PATH": "aj/bf.class",
            }]
        }
        return original, test, state, remapped

    def test_valid_fixture_passes_and_literal_string_is_not_leakage(self):
        m = load_module(VALIDATOR_PATH, "validator_ok")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, test, state, _ = self.make_jars(root)
            result = m.validate_repaired_test_jar(
                original_jar=original,
                test_jar=test,
                build_state=state,
            )
            self.assertTrue(result["pass"])
            self.assertEqual(result["namespace_leaks"], [])

    def test_missing_replaced_entry_fails(self):
        m = load_module(VALIDATOR_PATH, "validator_missing")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, test, state, _ = self.make_jars(root)
            with zipfile.ZipFile(test, "w") as z:
                z.writestr("META-INF/MANIFEST.MF", b"Manifest-Version: 1.0\r\n\r\n")
                z.writestr("data/demo.bin", b"UNCHANGED-RESOURCE")
            with self.assertRaises(ValueError):
                m.validate_repaired_test_jar(original_jar=original, test_jar=test, build_state=state)

    def test_wrong_internal_name_fails(self):
        m = load_module(VALIDATOR_PATH, "validator_name")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, test, state, _ = self.make_jars(root)
            classes = self.compile_fixture(root / "other")
            wrong = (classes / "l1r" / "aj" / "Login.class").read_bytes()
            with zipfile.ZipFile(test, "w") as z:
                z.writestr("META-INF/MANIFEST.MF", b"Manifest-Version: 1.0\r\nMain-Class: l1j.server.Server\r\n\r\n")
                z.writestr("data/demo.bin", b"UNCHANGED-RESOURCE")
                z.writestr("aj/bf.class", wrong)
            with self.assertRaises(ValueError):
                m.validate_repaired_test_jar(original_jar=original, test_jar=test, build_state=state)

    def test_changed_manifest_or_resource_fails(self):
        m = load_module(VALIDATOR_PATH, "validator_resource")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, test, state, remapped = self.make_jars(root)
            with zipfile.ZipFile(test, "w") as z:
                z.writestr("META-INF/MANIFEST.MF", b"CHANGED")
                z.writestr("data/demo.bin", b"CHANGED")
                z.writestr("aj/bf.class", remapped)
            with self.assertRaises(ValueError):
                m.validate_repaired_test_jar(original_jar=original, test_jar=test, build_state=state)

    def test_normalized_class_entry_leak_fails(self):
        m = load_module(VALIDATOR_PATH, "validator_leak")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            original, test, state, remapped = self.make_jars(root)
            with zipfile.ZipFile(test, "a") as z:
                z.writestr("l1r/aj/Login.class", remapped)
            with self.assertRaises(ValueError):
                m.validate_repaired_test_jar(original_jar=original, test_jar=test, build_state=state)


if __name__ == "__main__":
    unittest.main()
