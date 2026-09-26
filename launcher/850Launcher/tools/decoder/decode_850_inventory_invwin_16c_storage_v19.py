#!/usr/bin/env python3
import argparse
import hashlib
import importlib.metadata as importlib_metadata
import struct
from pathlib import Path

EXPECTED_SHA = "FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4"
EXPECTED_TARGETS = [0x0087E900, 0x0087E950, 0x0087E960, 0x0087E970, 0x0087E980]
CAPTURE_BYTES = 192
MAX_INSNS = 120
FOCUS = "INVWIN_PLUS_0x16C_BEGIN_END_STORAGE"


def u16(data, off): return struct.unpack_from('<H', data, off)[0]
def u32(data, off): return struct.unpack_from('<I', data, off)[0]


def pe_sections(data):
    if data[:2] != b'MZ':
        raise SystemExit('Not an MZ image')
    pe = u32(data, 0x3C)
    if data[pe:pe+4] != b'PE\0\0':
        raise SystemExit('Missing PE signature')
    coff = pe + 4
    section_count = u16(data, coff + 2)
    opt_size = u16(data, coff + 16)
    opt = coff + 20
    magic = u16(data, opt)
    if magic != 0x10B:
        raise SystemExit(f'Expected PE32 image, magic=0x{magic:04X}')
    image_base = u32(data, opt + 28)
    sec = opt + opt_size
    out = []
    for i in range(section_count):
        p = sec + i * 40
        name = data[p:p+8].split(b'\0', 1)[0].decode('ascii', errors='replace')
        virtual_size = u32(data, p + 8)
        virtual_address = u32(data, p + 12)
        raw_size = u32(data, p + 16)
        raw_ptr = u32(data, p + 20)
        out.append((name, virtual_address, virtual_size, raw_ptr, raw_size))
    return image_base, out


def locate_rva(rva, sections):
    for name, va, vs, raw, raw_size in sections:
        span = max(vs, raw_size)
        if va <= rva < va + span:
            delta = rva - va
            file_backed = delta < raw_size
            offset = raw + delta if file_backed else None
            return {
                'name': name,
                'va': va,
                'virtual_size': vs,
                'raw_ptr': raw,
                'raw_size': raw_size,
                'delta': delta,
                'file_backed': file_backed,
                'offset': offset,
            }
    return None


def disassemble(blob, va, image_base):
    from capstone import Cs, CS_ARCH_X86, CS_MODE_32
    from capstone.x86 import X86_OP_IMM, X86_OP_MEM, X86_REG_ECX, X86_REG_EAX, X86_REG_EDX
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    rows, calls, mems, rets = [], [], [], []
    for i, insn in enumerate(md.disasm(blob, va)):
        if i >= MAX_INSNS:
            break
        rva = insn.address - image_base
        asm = f'{insn.mnemonic} {insn.op_str}'.rstrip()
        rows.append((rva, insn.bytes.hex(' ').upper(), asm))
        if insn.mnemonic.lower() == 'call' and insn.operands:
            op = insn.operands[0]
            if op.type == X86_OP_IMM:
                target = int(op.imm) & 0xFFFFFFFF
                calls.append(target - image_base if target >= image_base else target)
        if insn.mnemonic.lower().startswith('ret'):
            rets.append(rva)
        for op in insn.operands:
            if op.type != X86_OP_MEM:
                continue
            mem = op.mem
            if mem.index == 0 and mem.base in (X86_REG_ECX, X86_REG_EAX, X86_REG_EDX):
                mems.append((rva, insn.reg_name(mem.base), int(mem.disp), asm))
    return rows, calls, mems, rets


def main():
    ap = argparse.ArgumentParser(description='Offline exact-RVA V19 decoder for INVWIN+0x16C storage helpers')
    ap.add_argument('--client', required=True)
    ap.add_argument('--output', required=True)
    args = ap.parse_args()

    client = Path(args.client)
    data = client.read_bytes()
    sha = hashlib.sha256(data).hexdigest().upper()
    if sha != EXPECTED_SHA:
        raise SystemExit(f'Client authority mismatch: {sha}')

    image_base, sections = pe_sections(data)
    try:
        capstone_package = importlib_metadata.version('capstone')
    except importlib_metadata.PackageNotFoundError:
        raise SystemExit('capstone package not installed')

    out = [
        'MODE=850_INVENTORY_INVWIN_16C_STORAGE_DECODE_V19',
        f'CLIENT={client}',
        f'CLIENT_SHA256={sha}',
        'CLIENT_AUTHORITY=1',
        f'IMAGE_BASE=0x{image_base:08X}',
        f'TARGET_COUNT={len(EXPECTED_TARGETS)}',
        f'CAPTURE_BYTES_PER_TARGET={CAPTURE_BYTES}',
        'SOURCE=STATIC_PE_FILE_IMAGE_EXACT_TARGETS',
        'EXACT_TARGET_ONLY=YES',
        'FILE_IMAGE_ONLY=YES',
        'PROCESS_ATTACH=NO',
        f'FOCUS={FOCUS}',
        'HELPER_DEPTH=1',
        'HEAP_SCAN=NO',
        'MEM_PRIVATE_SCAN=NO',
        'REMOTE_CALL=NO',
        'MEMORY_WRITE=NO',
        f'CAPSTONE_PACKAGE_VERSION={capstone_package}',
        '',
        '[TARGETS]'
    ]

    primary_ecx = []
    virtual_only = []
    unmapped = []

    for rva in EXPECTED_TARGETS:
        loc = locate_rva(rva, sections)
        if loc is None:
            unmapped.append(rva)
            out.append(f'TARGET_RVA=0x{rva:08X} MAPPED=NO FILE_BACKED=NO')
            continue

        if not loc['file_backed']:
            virtual_only.append(rva)
            out.append(
                f"TARGET_RVA=0x{rva:08X} SECTION={loc['name']} FILE_BACKED=NO "
                f"SECTION_RVA=0x{loc['va']:08X} VIRTUAL_SIZE=0x{loc['virtual_size']:X} "
                f"RAW_PTR=0x{loc['raw_ptr']:08X} RAW_SIZE=0x{loc['raw_size']:X} DELTA=0x{loc['delta']:X}"
            )
            continue

        off = loc['offset']
        blob = data[off:off + CAPTURE_BYTES]
        rows, calls, mems, rets = disassemble(blob, image_base + rva, image_base)
        cls = 'PRIMARY_STORAGE_LANDING' if rva == 0x0087E900 else 'SIBLING_STORAGE_LANDING'
        out.append(
            f"TARGET_RVA=0x{rva:08X} FILE_OFFSET=0x{off:08X} SECTION={loc['name']} FILE_BACKED=YES "
            f"VIRTUAL_SIZE=0x{loc['virtual_size']:X} RAW_SIZE=0x{loc['raw_size']:X} "
            f"BYTES={len(blob)} DECODED_INSNS={len(rows)} CLASS={cls}"
        )
        out.append('  DIRECT_TARGETS=' + (','.join(f'0x{x:08X}' for x in calls) if calls else 'NONE'))
        out.append('  THIS_MEM=' + (';'.join(f'RVA=0x{rrva:08X},BASE={base},DISP={disp:+#x},ASM={asm}' for rrva, base, disp, asm in mems) if mems else 'NONE'))
        out.append('  RETS=' + (','.join(f'0x{x:08X}' for x in rets) if rets else 'NONE'))
        for rrva, b, asm in rows:
            out.append(f'  DISASM RVA=0x{rrva:08X} BYTES={b} ASM={asm}')
        if rva == 0x0087E900:
            primary_ecx = [(rrva, disp, asm) for rrva, base, disp, asm in mems if base == 'ecx']

    blocked = bool(virtual_only or unmapped)
    status = 'BLOCKED_STATIC_PE_VIRTUAL_ONLY' if virtual_only else ('BLOCKED_STATIC_PE_RVA_UNMAPPED' if unmapped else 'PASS_V19_OFFLINE_STORAGE_CAPTURE_VALIDATED')

    out += [
        '',
        '[DECISION]',
        f'STATUS={status}',
        f'VIRTUAL_ONLY_TARGET_COUNT={len(virtual_only)}',
        'VIRTUAL_ONLY_TARGETS=' + (','.join(f'0x{x:08X}' for x in virtual_only) if virtual_only else 'NONE'),
        f'UNMAPPED_TARGET_COUNT={len(unmapped)}',
        'UNMAPPED_TARGETS=' + (','.join(f'0x{x:08X}' for x in unmapped) if unmapped else 'NONE'),
        f'PRIMARY_0087E900_DIRECT_ECX_MEM_COUNT={len(primary_ecx)}',
        'PRIMARY_0087E900_DIRECT_ECX_MEM=' + (';'.join(f'RVA=0x{rrva:08X},DISP={disp:+#x},ASM={asm}' for rrva, disp, asm in primary_ecx) if primary_ecx else 'NONE'),
        'OFFLINE_LANE_CAN_PROVE_STORAGE=' + ('NO' if blocked else 'PENDING_REVIEW'),
        'AUTO_PROMOTION_ALLOWED=NO',
        'BEGIN_END_STORAGE_PROVEN=NO',
        'ELEMENT_IDENTITY_PROVEN=NO',
        'ITEM_RECORD_TRAVERSAL_PROVEN=NO',
        'OBJECT_ID_SEMANTICS_PROVEN=NO',
        'ITEM_ID_SEMANTICS_PROVEN=NO',
        'COUNT_SEMANTICS_PROVEN=NO',
        'FORMAL_WP5=NOT_YET',
        'FORMAL_WP6=NOT_YET',
        'HEAP_SCAN=NO',
        'MEM_PRIVATE_SCAN=NO',
        'REMOTE_CALL=NO',
        'MEMORY_WRITE=NO'
    ]

    dst = Path(args.output)
    dst.parent.mkdir(parents=True, exist_ok=True)
    dst.write_text('\n'.join(out) + '\n', encoding='utf-8')


if __name__ == '__main__':
    main()
