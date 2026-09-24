#!/usr/bin/env python3
import csv
import struct
from pathlib import Path


def load_reverse_map(path: Path) -> dict[str, str]:
    reverse: dict[str, str] = {}
    with Path(path).open(encoding="utf-8-sig", newline="") as f:
        for row in csv.DictReader(f):
            old = row["OldInternal"].strip()
            new = row["NewInternal"].strip()
            if not old or not new:
                raise ValueError("empty class mapping")
            prior = reverse.get(new)
            if prior is not None and prior != old:
                raise ValueError(f"duplicate normalized identity: {new}: {prior} vs {old}")
            reverse[new] = old
    return reverse


def _u2(buf: bytes | bytearray, off: int) -> int:
    return struct.unpack_from(">H", buf, off)[0]


def _u4(buf: bytes | bytearray, off: int) -> int:
    return struct.unpack_from(">I", buf, off)[0]


def _parse_cp(data: bytes):
    if len(data) < 10 or data[:4] != b"\xca\xfe\xba\xbe":
        raise ValueError("not a JVM classfile")
    cp_count = _u2(data, 8)
    p = 10
    entries = [None] * cp_count
    i = 1
    while i < cp_count:
        if p >= len(data):
            raise ValueError("truncated constant pool")
        tag = data[p]
        start = p
        p += 1
        if tag == 1:
            if p + 2 > len(data):
                raise ValueError("truncated UTF8 length")
            n = _u2(data, p)
            p += 2
            if p + n > len(data):
                raise ValueError("truncated UTF8 bytes")
            raw = data[p:p + n]
            p += n
            entries[i] = {"tag": tag, "text": raw.decode("utf-8", errors="strict")}
        elif tag in (3, 4):
            p += 4
            entries[i] = {"tag": tag, "raw": data[start:p]}
        elif tag in (5, 6):
            p += 8
            entries[i] = {"tag": tag, "raw": data[start:p]}
            i += 1
        elif tag in (7, 8, 16, 19, 20):
            ref = _u2(data, p)
            p += 2
            entries[i] = {"tag": tag, "ref": ref, "raw": data[start:p]}
        elif tag in (9, 10, 11, 12, 17, 18):
            a, b = struct.unpack_from(">HH", data, p)
            p += 4
            entries[i] = {"tag": tag, "a": a, "b": b, "raw": data[start:p]}
        elif tag == 15:
            p += 3
            entries[i] = {"tag": tag, "raw": data[start:p]}
        else:
            raise ValueError(f"unsupported constant-pool tag {tag} at index {i}")
        if p > len(data):
            raise ValueError("truncated constant pool entry")
        i += 1
    return entries, p


def _cp_utf8(entries, idx: int):
    if not idx or idx >= len(entries):
        return None
    entry = entries[idx]
    return entry["text"] if entry and entry["tag"] == 1 else None


def _cp_class_name(entries, idx: int):
    if not idx or idx >= len(entries):
        return None
    entry = entries[idx]
    if not entry or entry["tag"] != 7:
        return None
    return _cp_utf8(entries, entry["ref"])


def _skip_attrs(buf: bytes | bytearray, p: int) -> int:
    count = _u2(buf, p)
    p += 2
    for _ in range(count):
        length = _u4(buf, p + 2)
        p += 6 + length
        if p > len(buf):
            raise ValueError("truncated attribute")
    return p


def _class_attr_offsets(body: bytes | bytearray):
    if len(body) < 8:
        raise ValueError("truncated class body")
    p = 6
    interfaces_count = _u2(body, p)
    p += 2 + interfaces_count * 2

    fields_count = _u2(body, p)
    p += 2
    for _ in range(fields_count):
        p += 6
        p = _skip_attrs(body, p)

    methods_count = _u2(body, p)
    p += 2
    for _ in range(methods_count):
        p += 6
        p = _skip_attrs(body, p)

    attrs_count = _u2(body, p)
    p += 2
    attrs = []
    for _ in range(attrs_count):
        name_idx = _u2(body, p)
        length = _u4(body, p + 2)
        info_off = p + 6
        if info_off + length > len(body):
            raise ValueError("truncated class attribute")
        attrs.append((name_idx, info_off, length))
        p = info_off + length
    return attrs


def _rewrite_text(text: str, reverse: dict[str, str]) -> str:
    out = text
    for new, old in sorted(reverse.items(), key=lambda kv: len(kv[0]), reverse=True):
        if new in out:
            out = out.replace(new, old)
    return out


def _desired_inner_simple(normalized_internal: str | None, reverse: dict[str, str]):
    if not normalized_internal:
        return None
    old = reverse.get(normalized_internal)
    if not old or "$" not in old:
        return None
    segment = old.rsplit("$", 1)[-1]
    return None if segment.isdigit() else segment


def remap_class_bytes(data: bytes, reverse: dict[str, str]) -> bytes:
    entries, cp_end = _parse_cp(data)
    body = bytearray(data[cp_end:])

    this_class_idx = _u2(body, 2)
    this_name = _cp_class_name(entries, this_class_idx)
    if not this_name or this_name not in reverse:
        raise KeyError(f"unmapped normalized class: {this_name}")

    string_utf8 = set()
    structural_utf8 = set()
    for entry in entries:
        if not entry:
            continue
        if entry["tag"] == 8:
            string_utf8.add(entry["ref"])
        elif entry["tag"] in (7, 16, 19, 20):
            structural_utf8.add(entry["ref"])
        elif entry["tag"] == 12:
            structural_utf8.add(entry["b"])

    existing_utf8 = {
        entry["text"]: idx
        for idx, entry in enumerate(entries)
        if idx and entry and entry["tag"] == 1
    }

    for name_idx, info_off, length in _class_attr_offsets(body):
        if _cp_utf8(entries, name_idx) != "InnerClasses" or length < 2:
            continue
        count = _u2(body, info_off)
        q = info_off + 2
        for _ in range(count):
            inner_class_info_idx = _u2(body, q)
            inner_name_idx_off = q + 4
            inner_name_idx = _u2(body, inner_name_idx_off)
            normalized_inner = _cp_class_name(entries, inner_class_info_idx)
            desired = _desired_inner_simple(normalized_inner, reverse)
            if desired and inner_name_idx != 0:
                idx = existing_utf8.get(desired)
                if idx is None:
                    idx = len(entries)
                    entries.append({"tag": 1, "text": desired})
                    existing_utf8[desired] = idx
                struct.pack_into(">H", body, inner_name_idx_off, idx)
            q += 8

    cp_out = bytearray()
    i = 1
    while i < len(entries):
        entry = entries[i]
        if entry is None:
            i += 1
            continue
        if entry["tag"] == 1:
            text = entry["text"]
            has_hit = any(new in text for new in reverse)
            if has_hit and i in string_utf8 and i not in structural_utf8:
                new_text = text
            else:
                new_text = _rewrite_text(text, reverse)
            raw = new_text.encode("utf-8")
            cp_out.append(1)
            cp_out += struct.pack(">H", len(raw))
            cp_out += raw
        else:
            cp_out += entry["raw"]
        i += 2 if entry["tag"] in (5, 6) else 1

    out = bytearray()
    out += data[:8]
    out += struct.pack(">H", len(entries))
    out += cp_out
    out += body
    return bytes(out)


def class_utf8_values(data: bytes) -> list[str]:
    entries, _ = _parse_cp(data)
    return [entry["text"] for entry in entries if entry and entry["tag"] == 1]


def class_internal_name(data: bytes) -> str:
    entries, cp_end = _parse_cp(data)
    body = data[cp_end:]
    this_class_idx = _u2(body, 2)
    name = _cp_class_name(entries, this_class_idx)
    if not name:
        raise ValueError("class has no valid this_class name")
    return name


def inner_class_simple_names(data: bytes) -> list[str]:
    entries, cp_end = _parse_cp(data)
    body = data[cp_end:]
    names: list[str] = []
    for name_idx, info_off, length in _class_attr_offsets(body):
        if _cp_utf8(entries, name_idx) != "InnerClasses" or length < 2:
            continue
        count = _u2(body, info_off)
        q = info_off + 2
        for _ in range(count):
            inner_name_idx = _u2(body, q + 4)
            if inner_name_idx:
                name = _cp_utf8(entries, inner_name_idx)
                if name is not None:
                    names.append(name)
            q += 8
    return names


def runtime_output_path(class_path: Path, reverse: dict[str, str]) -> Path:
    path = Path(class_path)
    if path.suffix != ".class":
        raise ValueError(f"not a class path: {path}")
    normalized = path.as_posix()[:-6]
    try:
        original = reverse[normalized]
    except KeyError as exc:
        raise KeyError(f"unmapped normalized class: {normalized}") from exc
    return Path(original + ".class")
