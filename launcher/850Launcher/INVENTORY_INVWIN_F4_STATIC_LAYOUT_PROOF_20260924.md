# 850 Inventory INVWIN+0xF4 static layout proof — 2026-09-24

## Authority

```text
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
CLIENT_AUTHORITY=1
ROOT_OWNER=PASS_RESTART_STABLE
SUBOBJECT_OWNER=INVWIN+0xF4
```

## V9 semantic chain

The V9 callers establish a 4-byte element container shape:

```text
SIZE helper RVA 0x004CE990:
  call 0x004CD8D0
  call 0x004CD890
  size = (*endField - *beginField) >> 2

INDEX helper RVA 0x004CC180:
  call 0x004CD870
  begin = *returnedFieldAddress
  elementAddress = begin + index*4

ELEMENT_STRIDE=4
SIZE_FORMULA=(END-BEGIN)/4
```

## V10 wrapper chain

```text
BEGIN_MUT   0x004CD870 -> call 0x004CD670
BEGIN_CONST 0x004CD890 -> call 0x004CD690
END_MUT     0x004CD8B0 -> call 0x004CD670 -> add eax,4
END_CONST   0x004CD8D0 -> call 0x004CD690 -> add eax,4
```

Therefore the begin and end field addresses are adjacent and separated by exactly four bytes once the base accessor result is known.

## V11 base-accessor closure

V11 captured 96 bytes at both `0x004CD670` and `0x004CD690`.

The mutable wrapper calls `0x004CD6D0`; the const wrapper calls `0x004CD6E0`.

Both target bodies are the same identity accessor:

```asm
push ebp
mov  ebp,esp
push ecx
mov  [ebp-4],ecx
mov  eax,[ebp-4]
mov  esp,ebp
pop  ebp
ret
```

So both return the incoming `this` pointer unchanged.

## Static layout conclusion

```text
BASE_ACCESSORS_AGREE=YES
BASE_FIELD_ADDRESS_OFFSET=0x0
BEGIN_FIELD_OFFSET=0x0
END_FIELD_OFFSET=0x4
BEGIN_END_DISTINCT=YES
ELEMENT_STRIDE=4
SIZE_FORMULA=(END-BEGIN)/4
STATUS=PASS_F4_BEGIN_END_LAYOUT_PROVEN_STATIC
```

For the owning INVWIN object:

```text
COLLECTION_OBJECT = INVWIN + 0xF4
BEGIN_VALUE        = *(INVWIN + 0xF4)
END_VALUE          = *(INVWIN + 0xF8)
ELEMENT_COUNT      = (END_VALUE - BEGIN_VALUE) / 4
```

This proves the static begin/end layout only. It does not yet prove that each element corresponds one-to-one with a distinct inventory item record.

## Runtime promotion gate

Use `run_850_inventory_invwin_f4_collection_change_v12.ps1`.

Required controlled evidence:

```text
existing-stack quantity-only change -> delta count 0
one distinct record add             -> delta used bytes +4 / count +1
one distinct record remove          -> delta used bytes -4 / count -1
```

Only after symmetric runtime evidence may the `INVWIN+0xF4` layout be promoted to the runtime-controlled inventory collection candidate.

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
COLLECTION_BUFFER_DEREFERENCE=NO
MEMORY_WRITE=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```
