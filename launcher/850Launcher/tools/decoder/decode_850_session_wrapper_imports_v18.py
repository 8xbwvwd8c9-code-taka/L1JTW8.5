#!/usr/bin/env python3
from __future__ import annotations

import argparse
import dataclasses
import hashlib
import os
import struct
from typing import Iterable, List


@dataclasses.dataclass
class Section:
    name: str
    virtual_size: int
    virtual_address: int
    raw_size: int
    raw_address: int
    characteristics: int

    @property
    def executable(self) -> bool:
        return bool(self.characteristics & 0x20000000)


@dataclasses.dataclass
class ImportEntry:
    dll: str
    name: str
    iat_rva: int
    by_ordinal: bool = False
    ordinal: int = 0


@dataclasses.dataclass
class PeImage:
    image_base: int
    size_of_image: int
    sections: List[Section]
    imports: List[ImportEntry]


@dataclasses.dataclass
class Xref:
    dll: str
    name: str
    iat_rva: int
    rva: int
    kind: str


def _u16(data: bytes, off: int) -> int:
    if off < 0 or off + 2 > len(data):
        raise ValueError('read16 out of range')
    return struct.unpack_from('<H', data, off)[0]


def _u32(data: bytes, off: int) -> int:
    if off < 0 or off + 4 > len(data):
        raise ValueError('read32 out of range')
    return struct.unpack_from('<I', data, off)[0]


def _ascii_z(data: bytes, off: int, max_len: int = 260) -> str:
    if off < 0 or off >= len(data):
        return ''
    end = min(len(data), off + max_len)
    zero = data.find(b'\0', off, end)
    if zero >= 0:
        end = zero
    return data[off:end].decode('ascii', errors='replace')


def _rva_to_offset(rva: int, sections: Iterable[Section], file_len: int) -> int:
    for s in sections:
        span = max(s.virtual_size, s.raw_size)
        if s.virtual_address <= rva < s.virtual_address + span:
            off = s.raw_address + (rva - s.virtual_address)
            return off if 0 <= off < file_len else -1
    return rva if 0 <= rva < file_len else -1


def parse_pe32(path: str) -> PeImage:
    with open(path, 'rb') as f:
        data = f.read()
    if len(data) < 0x100 or data[:2] != b'MZ':
        raise ValueError('not a valid MZ/PE image')
    pe = _u32(data, 0x3C)
    if pe < 0 or pe + 0x100 > len(data) or data[pe:pe + 4] != b'PE\0\0':
        raise ValueError('invalid PE header')
    fh = pe + 4
    section_count = _u16(data, fh + 2)
    optional_size = _u16(data, fh + 16)
    optional = fh + 20
    if optional + optional_size > len(data) or _u16(data, optional) != 0x10B:
        raise ValueError('only PE32/x86 is supported')

    image_base = _u32(data, optional + 28)
    size_of_image = _u32(data, optional + 56)
    sections: List[Section] = []
    sh = optional + optional_size
    for i in range(section_count):
        off = sh + i * 40
        if off + 40 > len(data):
            break
        sections.append(Section(
            name=_ascii_z(data, off, 8),
            virtual_size=_u32(data, off + 8),
            virtual_address=_u32(data, off + 12),
            raw_size=_u32(data, off + 16),
            raw_address=_u32(data, off + 20),
            characteristics=_u32(data, off + 36),
        ))

    imports: List[ImportEntry] = []
    import_rva = _u32(data, optional + 104)
    if not import_rva:
        return PeImage(image_base, size_of_image, sections, imports)
    import_off = _rva_to_offset(import_rva, sections, len(data))
    if import_off < 0:
        raise ValueError('import directory RVA cannot be mapped')

    for descriptor_index in range(4096):
        desc = import_off + descriptor_index * 20
        if desc + 20 > len(data):
            break
        oft = _u32(data, desc)
        name_rva = _u32(data, desc + 12)
        first_thunk = _u32(data, desc + 16)
        if oft == 0 and name_rva == 0 and first_thunk == 0:
            break
        name_off = _rva_to_offset(name_rva, sections, len(data))
        dll = _ascii_z(data, name_off) if name_off >= 0 else ''
        lookup_rva = oft or first_thunk
        lookup_off = _rva_to_offset(lookup_rva, sections, len(data))
        if lookup_off < 0:
            continue
        for thunk_index in range(65536):
            thunk_off = lookup_off + thunk_index * 4
            if thunk_off + 4 > len(data):
                break
            thunk = _u32(data, thunk_off)
            if thunk == 0:
                break
            iat_rva = first_thunk + thunk_index * 4
            if thunk & 0x80000000:
                ordinal = thunk & 0xFFFF
                imports.append(ImportEntry(dll, f'#{ordinal}', iat_rva, True, ordinal))
            else:
                ibn_off = _rva_to_offset(thunk, sections, len(data))
                if ibn_off < 0 or ibn_off + 2 >= len(data):
                    continue
                imports.append(ImportEntry(dll, _ascii_z(data, ibn_off + 2), iat_rva))
    return PeImage(image_base, size_of_image, sections, imports)


_SYSTEM_DLLS = {
    'kernel32.dll', 'kernelbase.dll', 'ntdll.dll', 'user32.dll', 'gdi32.dll', 'gdi32full.dll',
    'advapi32.dll', 'ole32.dll', 'oleaut32.dll', 'shell32.dll', 'shlwapi.dll', 'comdlg32.dll',
    'comctl32.dll', 'winmm.dll', 'version.dll', 'imm32.dll', 'rpcrt4.dll', 'ws2_32.dll',
    'wsock32.dll', 'wininet.dll', 'winhttp.dll', 'crypt32.dll', 'bcrypt.dll', 'secur32.dll',
    'setupapi.dll', 'psapi.dll', 'dbghelp.dll', 'dinput.dll', 'ddraw.dll', 'dsound.dll',
    'mpr.dll', 'urlmon.dll', 'msvcrt.dll', 'msvcp_win.dll', 'ucrtbase.dll',
}


def is_local_client_module(app_dir: str, dll: str) -> bool:
    if not dll or dll.lower() in _SYSTEM_DLLS:
        return False
    try:
        wanted = dll.lower()
        for name in os.listdir(app_dir):
            if name.lower() == wanted and os.path.isfile(os.path.join(app_dir, name)):
                return True
    except OSError:
        return False
    return False


def scan_iat_xrefs(path: str, image: PeImage, imports: Iterable[ImportEntry]) -> List[Xref]:
    with open(path, 'rb') as f:
        data = f.read()
    targets = {}
    for imp in imports:
        absolute = image.image_base + imp.iat_rva
        if 0 <= absolute <= 0xFFFFFFFF:
            targets[absolute] = imp
    out: List[Xref] = []
    for s in image.sections:
        if not s.executable or s.raw_size <= 0:
            continue
        start = s.raw_address
        end = min(len(data), start + s.raw_size)
        if start < 0 or start >= end:
            continue
        i = start
        while i + 6 <= end:
            if data[i] == 0xFF and data[i + 1] in (0x15, 0x25):
                operand = _u32(data, i + 2)
                imp = targets.get(operand)
                if imp is not None:
                    out.append(Xref(
                        dll=imp.dll,
                        name=imp.name,
                        iat_rva=imp.iat_rva,
                        rva=s.virtual_address + (i - start),
                        kind='CALL [IAT]' if data[i + 1] == 0x15 else 'JMP [IAT]',
                    ))
            i += 1
    return out


def _sha256(path: str) -> str:
    h = hashlib.sha256()
    with open(path, 'rb') as f:
        for chunk in iter(lambda: f.read(1024 * 1024), b''):
            h.update(chunk)
    return h.hexdigest().upper()


def main() -> int:
    ap = argparse.ArgumentParser(description='V18 bounded offline session-wrapper import trace for Lin.bin2')
    ap.add_argument('--client', required=True)
    ap.add_argument('--output', required=True)
    ap.add_argument('--expected-sha256', default='FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4')
    ns = ap.parse_args()

    client = os.path.abspath(ns.client)
    app_dir = os.path.dirname(client)
    actual_sha = _sha256(client)
    authoritative = actual_sha == ns.expected_sha256.upper()
    image = parse_pe32(client)

    by_dll = {}
    for imp in image.imports:
        by_dll.setdefault(imp.dll, []).append(imp)

    local_dlls = [dll for dll in by_dll if is_local_client_module(app_dir, dll)]
    local_imports = [imp for dll in local_dlls for imp in by_dll[dll]]
    xrefs = scan_iat_xrefs(client, image, local_imports)

    lines = [
        'MODE=850_SESSION_WRAPPER_IMPORTS_V18',
        f'CLIENT={client}',
        f'CLIENT_SHA256={actual_sha}',
        f'CLIENT_AUTHORITY={1 if authoritative else 0}',
        f'IMAGE_BASE=0x{image.image_base:08X}',
        f'IMPORT_DLL_COUNT={len(by_dll)}',
        f'IMPORT_ENTRY_COUNT={len(image.imports)}',
        f'LOCAL_IMPORTED_DLL_COUNT={len(local_dlls)}',
        f'LOCAL_IMPORTED_ENTRY_COUNT={len(local_imports)}',
        f'LOCAL_IAT_XREF_COUNT={len(xrefs)}',
        'STATIC_FILE_READ_ONLY=YES',
        'RUNTIME_ATTACH=NO',
        'MEMORY_WRITE=NO',
        '',
        '[IMPORT_DLLS]',
    ]
    for dll in sorted(by_dll, key=str.lower):
        lines.append(f'DLL={dll} IMPORTS={len(by_dll[dll])} LOCAL_CLIENT_MODULE={1 if dll in local_dlls else 0}')

    lines += ['', '[LOCAL_CLIENT_IMPORTS]']
    for dll in sorted(local_dlls, key=str.lower):
        for imp in by_dll[dll]:
            lines.append(
                f'DLL={dll} NAME={imp.name} ORDINAL={imp.ordinal if imp.by_ordinal else ""} '
                f'IAT_RVA=0x{imp.iat_rva:08X}'
            )

    lines += ['', '[LOCAL_CLIENT_IAT_XREFS]']
    for x in sorted(xrefs, key=lambda z: (z.rva, z.dll.lower(), z.name.lower())):
        lines.append(
            f'DLL={x.dll} NAME={x.name} IAT_RVA=0x{x.iat_rva:08X} '
            f'CALL_RVA=0x{x.rva:08X} KIND={x.kind}'
        )

    has_libeay = any(dll.lower() == 'libeay32.dll' for dll in local_dlls)
    libeay_xrefs = sum(1 for x in xrefs if x.dll.lower() == 'libeay32.dll')
    if has_libeay and libeay_xrefs:
        status = 'PASS_LIBEAY32_HANDOFF_CANDIDATE'
        next_action = 'Classify only Lin.bin2 callsites targeting LIBEAY32 imports; do not scan unrelated socket owners.'
    elif local_dlls and xrefs:
        status = 'PASS_LOCAL_WRAPPER_CALLS_FOUND'
        next_action = 'Classify only local-module IAT callsites listed above and identify session/packet semantics.'
    elif local_dlls:
        status = 'PASS_LOCAL_IMPORTS_NO_DIRECT_IAT_XREF'
        next_action = 'Inspect bounded import-thunk/indirect-call handoff for listed local modules.'
    else:
        status = 'NO_LOCAL_WRAPPER_IMPORTS'
        next_action = 'Do not infer a wrapper; pivot to bounded runtime module-to-client handoff evidence.'

    lines += [
        '',
        f'LIBEAY32_IMPORTED={1 if has_libeay else 0}',
        f'LIBEAY32_IAT_XREFS={libeay_xrefs}',
        f'STATUS={status if authoritative else "BLOCKED_CLIENT_HASH_MISMATCH"}',
        'FORMAL_WP7=NOT_YET',
        f'NEXT={next_action if authoritative else "Use only authoritative Lin.bin2 SHA before semantic promotion."}',
    ]

    os.makedirs(os.path.dirname(os.path.abspath(ns.output)), exist_ok=True)
    with open(ns.output, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\n'.join(lines) + '\n')
    print('\n'.join(lines[-7:]))
    return 0


if __name__ == '__main__':
    raise SystemExit(main())
