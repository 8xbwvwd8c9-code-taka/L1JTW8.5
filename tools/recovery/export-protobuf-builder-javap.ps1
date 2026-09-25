$ErrorActionPreference = "Stop"

$jar = "./l1jserver2.jar"
$out = "./recovery/protobuf-builder-javap.txt"
New-Item -ItemType Directory -Force -Path "./recovery" | Out-Null

$classes = @(
  'an.a$a$a','an.a$c$a','an.a$e$a','an.a$g$a','an.a$i$a',
  'an.b$a$a','an.b$c$a','an.b$e$a','an.b$g$a','an.b$i$a',
  'an.c$a$a','an.c$c$a','an.c$e$a','an.c$g$a','an.c$i$a',
  'an.d$a$a','an.d$c$a','an.d$e$a','an.d$g$a','an.d$i$a',
  'an.e$a$a','an.e$c$a','an.e$e$a','an.e$g$a','an.e$i$a',
  'an.f$a$a','an.f$c$a','an.f$e$a','an.f$g$a','an.f$i$a',
  'an.g$a$a','an.g$c$a','an.g$e$a','an.g$g$a','an.g$i$a',
  'an.h$a$a','an.h$c$a','an.h$e$a','an.h$g$a','an.h$i$a',
  'an.i$a$a','an.i$c$a','an.i$e$a','an.i$g$a'
)

"PROTOBUF_BUILDER_JAVAP_BEGIN" | Set-Content -Encoding UTF8 $out
foreach ($c in $classes) {
  "===== CLASS $c =====" | Add-Content -Encoding UTF8 $out
  & javap -classpath $jar -p -c -s $c 2>&1 | Add-Content -Encoding UTF8 $out
  "" | Add-Content -Encoding UTF8 $out
}
"PROTOBUF_BUILDER_JAVAP_END" | Add-Content -Encoding UTF8 $out

Write-Host "CLASSES=$($classes.Count)"
Write-Host "OUT=$out"
