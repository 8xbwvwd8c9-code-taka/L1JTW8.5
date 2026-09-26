param(
    [string]$ToolRoot = $PSScriptRoot
)

$ErrorActionPreference = 'Stop'

$runner = Join-Path $ToolRoot 'run_850_inventory_invwin_16c_storage_v19.ps1'
$decoder = Join-Path $ToolRoot 'decoder\decode_850_inventory_invwin_16c_storage_v19.py'

if (-not (Test-Path -LiteralPath $runner)) { throw "Missing V19 runner: $runner" }
if (-not (Test-Path -LiteralPath $decoder)) { throw "Missing V19 decoder: $decoder" }

$runnerText = [IO.File]::ReadAllText($runner)
$decoderText = [IO.File]::ReadAllText($decoder)

$expected = @(
    '0x0087E900L',
    '0x0087E950L',
    '0x0087E960L',
    '0x0087E970L',
    '0x0087E980L'
)
foreach ($x in $expected) {
    if (-not $runnerText.Contains($x)) { throw "Runner missing exact target $x" }
}

$forbidden = @('HEAP_SCAN=YES','MEM_PRIVATE_SCAN=YES','MEMORY_WRITE=YES','REMOTE_CALL=YES')
foreach ($x in $forbidden) {
    if ($runnerText.Contains($x) -or $decoderText.Contains($x)) { throw "Forbidden safety setting present: $x" }
}

$requiredRunner = @(
    'EXACT_TARGET_ONLY=YES',
    'RUNTIME_MEM_IMAGE_ONLY=YES',
    'HELPER_DEPTH=1',
    'HEAP_SCAN=NO',
    'MEM_PRIVATE_SCAN=NO',
    'REMOTE_CALL=NO',
    'MEMORY_WRITE=NO'
)
foreach ($x in $requiredRunner) {
    if (-not $runnerText.Contains($x)) { throw "Runner missing safety marker $x" }
}

$requiredDecoder = @(
    'EXPECTED_TARGETS',
    'INVWIN_PLUS_0x16C_BEGIN_END_STORAGE',
    'AUTO_PROMOTION_ALLOWED=NO',
    'OBJECT_ID_SEMANTICS_PROVEN=NO',
    'ITEM_ID_SEMANTICS_PROVEN=NO',
    'COUNT_SEMANTICS_PROVEN=NO'
)
foreach ($x in $requiredDecoder) {
    if (-not $decoderText.Contains($x)) { throw "Decoder missing gate marker $x" }
}

Write-Host 'STATUS=PASS_V19_STATIC_CONTRACT'
Write-Host 'TARGET_COUNT=5'
Write-Host 'EXACT_TARGET_ONLY=YES'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'REMOTE_CALL=NO'
Write-Host 'MEMORY_WRITE=NO'
