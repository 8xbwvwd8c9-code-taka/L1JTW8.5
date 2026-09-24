import importlib.util
from pathlib import Path
import unittest

MODULE_PATH = Path(__file__).resolve().parents[1] / 'decode_850_inventory_invwin_direct_helpers_v16.py'
spec = importlib.util.spec_from_file_location('v16_decoder', MODULE_PATH)
mod = importlib.util.module_from_spec(spec)
spec.loader.exec_module(mod)


class V16CoreTests(unittest.TestCase):
    def test_merge_helper_reasons_dedupes_target_and_reason(self):
        rows = [
            {'target_rva':0x1234,'reason':'THIS_INVWIN','method_rva':0x10,'insn_rva':0x20},
            {'target_rva':0x1234,'reason':'THIS_INVWIN','method_rva':0x10,'insn_rva':0x20},
            {'target_rva':0x1234,'reason':'RETURN_OBJECT_USE','method_rva':0x10,'insn_rva':0x30},
            {'target_rva':0x2000,'reason':'STACK_ARG_INVWIN','method_rva':0x40,'insn_rva':0x50},
        ]
        merged = mod.merge_helper_reasons(rows)
        self.assertEqual([0x1234,0x2000],[x['target_rva'] for x in merged])
        self.assertEqual({'THIS_INVWIN','RETURN_OBJECT_USE'}, set(merged[0]['reasons']))
        self.assertEqual(2, merged[0]['callsite_count'])

    def test_rva_to_file_offset_maps_section(self):
        sections = [
            {'virtual_address':0x1000,'virtual_size':0x600,'raw_size':0x400,'raw_ptr':0x200},
            {'virtual_address':0x2000,'virtual_size':0x200,'raw_size':0x200,'raw_ptr':0x600},
        ]
        self.assertEqual(0x250, mod.rva_to_file_offset(0x1050, sections))
        self.assertEqual(0x650, mod.rva_to_file_offset(0x2050, sections))
        self.assertIsNone(mod.rva_to_file_offset(0x2500, sections))

    def test_promote_requires_strong_data_semantics(self):
        self.assertFalse(mod.strong_data_semantics({'find_lookup_hits':0,'container_hits':2,'objectid_cmp_hits':0,'erase_insert_hits':0}))
        self.assertTrue(mod.strong_data_semantics({'find_lookup_hits':1,'container_hits':1,'objectid_cmp_hits':0,'erase_insert_hits':0}))
        self.assertTrue(mod.strong_data_semantics({'find_lookup_hits':0,'container_hits':0,'objectid_cmp_hits':1,'erase_insert_hits':0}))
        self.assertTrue(mod.strong_data_semantics({'find_lookup_hits':0,'container_hits':0,'objectid_cmp_hits':0,'erase_insert_hits':1}))


if __name__ == '__main__':
    unittest.main()
