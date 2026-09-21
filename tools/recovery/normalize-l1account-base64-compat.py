#!/usr/bin/env python3
import json
from pathlib import Path

P=Path('_normalized-stage-src/l1r/bh/L1Account.java')
OUT=Path('recovery/l1account_base64_compat_normalization.json')
MD=Path('recovery/L1ACCOUNT_BASE64_COMPAT_NORMALIZATION.md')

text=P.read_text(encoding='utf-8',errors='replace')
old_import='import sun.misc.BASE64Encoder;'
new_import='import java.util.Base64;'
old_call='return new BASE64Encoder().encode(var1);'
new_call='return Base64.getEncoder().encodeToString(var1);'

ni=text.count(old_import)
nc=text.count(old_call)
if ni!=1 or nc!=1:
    raise SystemExit(f'BASE64 compatibility target mismatch: import={ni}, call={nc}')

text=text.replace(old_import,new_import,1)
text=text.replace(old_call,new_call,1)
P.write_text(text,encoding='utf-8')

state={
  'error_family':'REMOVED_JDK_INTERNAL_BASE64_ENCODER',
  'imports_rewritten':ni,
  'calls_rewritten':nc,
  'old_api':'sun.misc.BASE64Encoder.encode(byte[])',
  'new_api':'java.util.Base64.getEncoder().encodeToString(byte[])',
  'input_source':'MessageDigest.getInstance("SHA") digest',
  'digest_length_bytes':20,
  'jdk8_base64encoder_bytes_per_line':57,
  'line_break_behavior_equivalent_for_this_call':True,
  'alphabet_and_padding_equivalent':True,
  'password_hash_string_format_changed':False,
  'gameplay_logic_changed':False,
}
OUT.write_text(json.dumps(state,indent=2)+'\n',encoding='utf-8')

MD.write_text(
 '# L1Account Base64 Compatibility Normalization\n\n'
 '- Legacy API: `sun.misc.BASE64Encoder.encode(byte[])`.\n'
 '- Recovery API: `java.util.Base64.getEncoder().encodeToString(byte[])`.\n'
 '- Input is the SHA-1 digest produced immediately above: **20 bytes**.\n'
 '- JDK8 BASE64Encoder line width is **57 input bytes**; a 20-byte digest does not receive a line suffix.\n'
 '- Standard Base64 alphabet and padding are equivalent for this input.\n'
 '- Existing password hash string format changed: **NO**.\n'
 '- Gameplay/account semantics changed: **NO**.\n',
 encoding='utf-8'
)
print(json.dumps(state,indent=2))
