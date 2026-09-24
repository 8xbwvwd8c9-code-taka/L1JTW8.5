import importlib.util
from pathlib import Path
import unittest

MODULE_PATH = Path(__file__).resolve().parents[1] / 'decode_850_inventory_invwin_runtime_helpers_v16r.py'
spec = importlib.util.spec_from_file_location('v16r_decoder', MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)


class V16RDecoderTests(unittest.TestCase):
    def test_known_index_anchor_requires_begin_mut_and_scale4(self):
        got = mod.classify_known_helper(0x004CC180, [0x004CD870], ['lea eax, [eax + ecx*4]'])
        self.assertEqual('INDEX_ACCESSOR', got)
        bad = mod.classify_known_helper(0x004CC180, [], ['lea eax, [eax + ecx*4]'])
        self.assertEqual('ANCHOR_MISMATCH', bad)

    def test_known_size_anchor_requires_begin_end_and_div4(self):
        calls = [0x004CD8D0, 0x004CD890]
        got = mod.classify_known_helper(0x004CE990, calls, ['sar ecx, 2'])
        self.assertEqual('SIZE_ACCESSOR', got)
        bad = mod.classify_known_helper(0x004CE990, calls, ['mov eax, ecx'])
        self.assertEqual('ANCHOR_MISMATCH', bad)

    def test_suspicious_ratio_flags_packed_garbage_pattern(self):
        noisy = ['lcall', 'int1', 'retf', 'out']
        clean = ['push', 'mov', 'call', 'mov', 'lea', 'ret']
        self.assertGreater(mod.suspicious_ratio(noisy), 0.5)
        self.assertEqual(0.0, mod.suspicious_ratio(clean))


if __name__ == '__main__':
    unittest.main()
