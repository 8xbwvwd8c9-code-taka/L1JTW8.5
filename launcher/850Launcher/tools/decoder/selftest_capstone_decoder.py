#!/usr/bin/env python3
from capstone import Cs, CS_ARCH_X86, CS_MODE_32

from decode_850_inventory_watch import Hit, decode_hit


def run_case(name, writer, eip, code_from, watch, regs):
    prefix_len = eip - code_from - len(writer)
    if prefix_len < 0:
        raise AssertionError(f"{name}: invalid fixture geometry")
    code = b"\x90" * prefix_len + writer + b"\x90" * 16
    hit = Hit(
        name="ROOT_GLOBAL",
        number=1,
        tid=1,
        watch=watch,
        post_value="0X00000000",
        eip=eip,
        eip_rva_text="0X00000000",
        regs=regs,
        code_from=code_from,
        code=code,
    )
    md = Cs(CS_ARCH_X86, CS_MODE_32)
    md.detail = True
    result = decode_hit(md, hit)
    if result["status"] != "PASS_UNIQUE_TARGET_WRITER":
        raise AssertionError(f"{name}: {result['status']} candidates={result['candidates']}")
    candidate = result["target_candidates"][0]
    if candidate["end"] != eip:
        raise AssertionError(f"{name}: writer does not end at EIP")
    print(f"PASS {name}: {candidate['mnemonic']} {candidate['op_str']}")


def main():
    watch = 0x016BCEE8
    regs = {
        "eax": 0x12345678,
        "ebx": 0,
        "ecx": 0,
        "edx": 0,
        "esi": 0,
        "edi": 0,
        "ebp": 0,
        "esp": 0x0012F000,
    }

    # mov dword ptr [0x016BCEE8], 0
    c705 = bytes.fromhex("C7 05 E8 CE 6B 01 00 00 00 00")
    run_case("C705_ZERO_CLEAR", c705, 0x00B01E80, 0x00B01E68, watch, regs)

    # mov dword ptr [0x016BCEE8], eax
    a3 = bytes.fromhex("A3 E8 CE 6B 01")
    run_case("A3_REGISTER_STORE", a3, 0x00B01025, 0x00B0100D, watch, regs)

    print("STATUS=PASS_CAPSTONE_DECODER_SELFTEST")


if __name__ == "__main__":
    main()
