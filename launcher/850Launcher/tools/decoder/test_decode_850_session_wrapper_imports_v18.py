import os
import struct
import tempfile
import unittest

import decode_850_session_wrapper_imports_v18 as v18


def build_test_pe(path):
    data = bytearray(0x800)
    data[0:2] = b'MZ'
    struct.pack_into('<I', data, 0x3C, 0x80)
    pe = 0x80
    data[pe:pe + 4] = b'PE\0\0'
    fh = pe + 4
    struct.pack_into('<H', data, fh + 0, 0x14C)
    struct.pack_into('<H', data, fh + 2, 2)
    struct.pack_into('<H', data, fh + 16, 0xE0)
    oh = fh + 20
    struct.pack_into('<H', data, oh + 0, 0x10B)
    struct.pack_into('<I', data, oh + 28, 0x00400000)
    struct.pack_into('<I', data, oh + 56, 0x3000)
    struct.pack_into('<I', data, oh + 104, 0x2000)
    struct.pack_into('<I', data, oh + 108, 40)

    sh = oh + 0xE0
    data[sh:sh + 8] = b'.text\0\0\0'
    struct.pack_into('<I', data, sh + 8, 0x200)
    struct.pack_into('<I', data, sh + 12, 0x1000)
    struct.pack_into('<I', data, sh + 16, 0x200)
    struct.pack_into('<I', data, sh + 20, 0x200)
    struct.pack_into('<I', data, sh + 36, 0x60000020)

    sh2 = sh + 40
    data[sh2:sh2 + 8] = b'.idata\0\0'
    struct.pack_into('<I', data, sh2 + 8, 0x200)
    struct.pack_into('<I', data, sh2 + 12, 0x2000)
    struct.pack_into('<I', data, sh2 + 16, 0x200)
    struct.pack_into('<I', data, sh2 + 20, 0x400)
    struct.pack_into('<I', data, sh2 + 36, 0xC0000040)

    struct.pack_into('<IIIII', data, 0x400, 0x2040, 0, 0, 0x2080, 0x2060)
    struct.pack_into('<I', data, 0x440, 0x20A0)
    struct.pack_into('<I', data, 0x444, 0)
    struct.pack_into('<II', data, 0x460, 0, 0)
    data[0x480:0x480 + 13] = b'LIBEAY32.dll\0'
    struct.pack_into('<H', data, 0x4A0, 0)
    data[0x4A2:0x4A2 + 10] = b'SSL_write\0'

    data[0x210:0x216] = b'\xFF\x15' + struct.pack('<I', 0x00402060)
    with open(path, 'wb') as f:
        f.write(data)


class V18Tests(unittest.TestCase):
    def test_parse_import_and_static_xref(self):
        with tempfile.TemporaryDirectory() as td:
            path = os.path.join(td, 'Lin.bin2')
            build_test_pe(path)
            image = v18.parse_pe32(path)
            self.assertEqual(image.image_base, 0x00400000)
            self.assertEqual(len(image.imports), 1)
            imp = image.imports[0]
            self.assertEqual(imp.dll.lower(), 'libeay32.dll')
            self.assertEqual(imp.name, 'SSL_write')
            self.assertEqual(imp.iat_rva, 0x2060)
            xrefs = v18.scan_iat_xrefs(path, image, [imp])
            self.assertEqual([(x.rva, x.kind) for x in xrefs], [(0x1010, 'CALL [IAT]')])

    def test_local_module_focus_prefers_existing_client_dll(self):
        with tempfile.TemporaryDirectory() as td:
            open(os.path.join(td, 'LIBEAY32.dll'), 'wb').close()
            self.assertTrue(v18.is_local_client_module(td, 'LIBEAY32.dll'))
            self.assertFalse(v18.is_local_client_module(td, 'KERNEL32.dll'))


if __name__ == '__main__':
    unittest.main()
