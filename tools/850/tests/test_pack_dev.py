import hashlib
import importlib.util
import tempfile
import unittest
import zipfile
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
PACK_PATH = ROOT / "tools" / "850" / "release" / "pack_dev.py"
REGISTRY_PATH = ROOT / "tools" / "850" / "repair-sync" / "repair_registry.py"


def load(path: Path, name: str):
    spec = importlib.util.spec_from_file_location(name, path)
    if spec is None or spec.loader is None:
        raise ImportError(f"cannot load {path}")
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


def sha(path: Path) -> str:
    return hashlib.sha256(path.read_bytes()).hexdigest()


class PackDevTests(unittest.TestCase):
    def make_base(self, root: Path):
        base = root / "850-dev-base.jar"
        with zipfile.ZipFile(base, "w") as zf:
            zf.writestr("dev/A.class", b"base-A")
            zf.writestr("dev/B.class", b"base-B")
            zf.writestr("data/keep.txt", b"resource")
            zf.writestr("META-INF/MANIFEST.MF", "Manifest-Version: 1.0\nMain-Class: l1j.server.Server\n\n")
        return base

    def make_overlay(self, root: Path):
        overlay = root / "classes"
        (overlay / "dev").mkdir(parents=True)
        (overlay / "dev" / "A.class").write_bytes(b"overlay-A")
        (overlay / "dev" / "C.class").write_bytes(b"overlay-C")
        return overlay

    def test_overlay_wins_and_new_overlay_class_is_added(self):
        mod = load(PACK_PATH, "fast_dev_pack_overlay")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            base = self.make_base(root)
            overlay = self.make_overlay(root)
            output = root / "dist" / "dev.jar"
            mod.pack_dev(base, overlay, output)
            with zipfile.ZipFile(output) as zf:
                self.assertEqual(zf.read("dev/A.class"), b"overlay-A")
                self.assertEqual(zf.read("dev/B.class"), b"base-B")
                self.assertEqual(zf.read("dev/C.class"), b"overlay-C")

    def test_resources_and_manifest_are_preserved(self):
        mod = load(PACK_PATH, "fast_dev_pack_resources")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            base = self.make_base(root)
            output = root / "dist" / "dev.jar"
            mod.pack_dev(base, self.make_overlay(root), output)
            with zipfile.ZipFile(output) as zf:
                self.assertEqual(zf.read("data/keep.txt"), b"resource")
                self.assertIn(b"Main-Class: l1j.server.Server", zf.read("META-INF/MANIFEST.MF"))

    def test_pack_is_deterministic_and_base_is_immutable(self):
        mod = load(PACK_PATH, "fast_dev_pack_deterministic")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            base = self.make_base(root)
            overlay = self.make_overlay(root)
            base_before = sha(base)
            out1 = root / "a.jar"
            out2 = root / "b.jar"
            mod.pack_dev(base, overlay, out1)
            mod.pack_dev(base, overlay, out2)
            self.assertEqual(sha(out1), sha(out2))
            self.assertEqual(sha(base), base_before)

    def test_pack_refuses_to_overwrite_dev_base(self):
        mod = load(PACK_PATH, "fast_dev_pack_guard")
        with tempfile.TemporaryDirectory() as td:
            root = Path(td)
            base = self.make_base(root)
            with self.assertRaises(ValueError):
                mod.pack_dev(base, self.make_overlay(root), base)

    def test_work_only_repair_is_not_sync_eligible(self):
        registry = load(REGISTRY_PATH, "fast_dev_registry_pack_contract")
        reconciled = registry.reconcile_registry(
            audit_bug_ids=["BUG-850-999"],
            completed_bug_ids=[],
            in_repair_bug_ids=["BUG-850-999"],
            existing_registry={},
        )
        entry = reconciled["BUG-850-999"]
        self.assertEqual(entry["state"], registry.IN_REPAIR)
        self.assertFalse(registry.is_sync_eligible(entry))


if __name__ == "__main__":
    unittest.main()
