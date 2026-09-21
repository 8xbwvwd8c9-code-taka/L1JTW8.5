# Post-javac0 Method / Field ABI Audit

Status: **PASS**

## Field identity

- Missing / extra: **131 / 175**
- Access-flag mismatches: **1**
- Generic Signature mismatches: **0**

## Method identity

- Missing / extra: **1347 / 2986**
- Known WP2 generated builder bridge extras: **660 / 660**
- Unclassified extras: **2326**
- Linkage-flag mismatches excluding BRIDGE/SYNTHETIC: **0**
- Generic Signature mismatches: **3**
- Exceptions mismatches: **0**

## Gates

- FIELD_DESCRIPTOR_IDENTITY_EXACT: **False**
- METHOD_DESCRIPTOR_IDENTITY_EXACT: **False**
- METHOD_DESCRIPTOR_AFTER_WP2_CLASSIFICATION: **False**
- WP2_BRIDGE_CLASSIFICATION_COMPLETE: **True**
