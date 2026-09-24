import importlib.util
import shutil
import subprocess
import tempfile
import textwrap
import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
MODULE_PATH = ROOT / "tools" / "production-rebuild" / "inverse_remap.py"


def load_module():
    spec = importlib.util.spec_from_file_location("inverse_remap", MODULE_PATH)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {MODULE_PATH}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


class InverseRemapTests(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        if shutil.which("javac") is None:
            raise unittest.SkipTest("javac is required")

    def compile_fixture(self, root: Path):
        src = root / "src"
        out = root / "classes"
        (src / "l1r" / "ai").mkdir(parents=True)
        (src / "l1r" / "aj").mkdir(parents=True)
        out.mkdir()

        (src / "l1r" / "ai" / "Handler.java").write_text(
            "package l1r.ai; public class Handler {}\n", encoding="utf-8"
        )
        (src / "l1r" / "aj" / "Login.java").write_text(
            textwrap.dedent(
                """
                package l1r.aj;
                public class Login {
                    public l1r.ai.Handler handler;
                    public String marker = "literal-prefix:l1r/ai/Handler:literal-suffix";
                    public static class L1R_x { public l1r.ai.Handler nested; }
                    public Runnable anon() {
                        return new Runnable() { public void run() {} };
                    }
                }
                """
            ).strip()
            + "\n",
            encoding="utf-8",
        )
        subprocess.run(
            [
                "javac",
                "-encoding",
                "UTF-8",
                "-source",
                "8",
                "-target",
                "8",
                "-d",
                str(out),
                str(src / "l1r" / "ai" / "Handler.java"),
                str(src / "l1r" / "aj" / "Login.java"),
            ],
            check=True,
            stdout=subprocess.PIPE,
            stderr=subprocess.PIPE,
            text=True,
        )
        return out

    def mapping_csv(self, root: Path):
        mapping = root / "map.csv"
        mapping.write_text(
            "OldInternal,NewInternal,Kind\n"
            "ai/e,l1r/ai/Handler,TOP_LEVEL\n"
            "aj/bf,l1r/aj/Login,TOP_LEVEL\n"
            "aj/bf$x,l1r/aj/Login$L1R_x,NAMED_INNER\n"
            "aj/bf$1,l1r/aj/Login$1,ANONYMOUS\n",
            encoding="utf-8",
        )
        return mapping

    def test_load_reverse_map_and_reject_duplicate_normalized_identity(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            mapping = self.mapping_csv(root)
            reverse = m.load_reverse_map(mapping)
            self.assertEqual(reverse["l1r/aj/Login"], "aj/bf")
            self.assertEqual(reverse["l1r/aj/Login$L1R_x"], "aj/bf$x")

            bad = root / "bad.csv"
            bad.write_text(
                "OldInternal,NewInternal,Kind\n"
                "aj/a,l1r/aj/Login,TOP_LEVEL\n"
                "aj/b,l1r/aj/Login,TOP_LEVEL\n",
                encoding="utf-8",
            )
            with self.assertRaises(ValueError):
                m.load_reverse_map(bad)

    def test_top_level_and_descriptor_references_are_restored_but_literal_is_preserved(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            classes = self.compile_fixture(root)
            reverse = m.load_reverse_map(self.mapping_csv(root))
            original = (classes / "l1r" / "aj" / "Login.class").read_bytes()
            remapped = m.remap_class_bytes(original, reverse)

            self.assertEqual(m.class_internal_name(remapped), "aj/bf")
            utf8 = m.class_utf8_values(remapped)
            self.assertIn("Lai/e;", utf8)
            self.assertIn("literal-prefix:l1r/ai/Handler:literal-suffix", utf8)
            self.assertNotIn("Ll1r/ai/Handler;", utf8)

    def test_named_inner_simple_name_is_restored(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            classes = self.compile_fixture(root)
            reverse = m.load_reverse_map(self.mapping_csv(root))
            data = (classes / "l1r" / "aj" / "Login$L1R_x.class").read_bytes()
            remapped = m.remap_class_bytes(data, reverse)

            self.assertEqual(m.class_internal_name(remapped), "aj/bf$x")
            self.assertIn("x", m.inner_class_simple_names(remapped))
            self.assertNotIn("L1R_x", m.inner_class_simple_names(remapped))

    def test_anonymous_inner_numeric_identity_is_preserved(self):
        m = load_module()
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            classes = self.compile_fixture(root)
            reverse = m.load_reverse_map(self.mapping_csv(root))
            data = (classes / "l1r" / "aj" / "Login$1.class").read_bytes()
            remapped = m.remap_class_bytes(data, reverse)

            self.assertEqual(m.class_internal_name(remapped), "aj/bf$1")
            self.assertNotIn("L1R_1", m.class_utf8_values(remapped))

    def test_runtime_output_path_fails_closed_for_unmapped_normalized_class(self):
        m = load_module()
        reverse = {"l1r/aj/Login": "aj/bf"}
        self.assertEqual(
            m.runtime_output_path(Path("l1r/aj/Login.class"), reverse),
            Path("aj/bf.class"),
        )
        with self.assertRaises(KeyError):
            m.runtime_output_path(Path("l1r/aj/Unknown.class"), reverse)


if __name__ == "__main__":
    unittest.main()
