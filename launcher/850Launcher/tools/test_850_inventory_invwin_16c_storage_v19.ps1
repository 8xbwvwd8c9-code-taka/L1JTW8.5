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
    '0x0087E900',
    '0x0087E950',
    '0x0087E960',
    '0x0087E970',
    '0x0087E980'
)
foreach ($x in $expected) {
    if (-not $decoderText.Contains($x)) { throw "Decoder missing exact target $x" }
}

$forbidden = @(
    'OpenProcess',
    'ReadProcessMemory',
    'VirtualQueryEx',
    'HEAP_SCAN=YES',
    'MEM_PRIVATE_SCAN=YES',
    'MEMORY_WRITE=YES',
    'REMOTE_CALL=YES'
)
foreach ($x in $forbidden) {
    if ($runnerText.Contains($x) -or $decoderText.Contains($x)) { throw "Forbidden V19 behavior present: $x" }
}

$requiredRunner = @(
    'EXACT_TARGET_ONLY=YES',
    'FILE_IMAGE_ONLY=YES',
    'PROCESS_ATTACH=NO',
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
    'COUNT_SEMANTICS_PROVEN=NO',
    'BLOCKED_STATIC_PE_VIRTUAL_ONLY',
    'FILE_BACKED=NO',
    'VIRTUAL_SIZE=',
    'RAW_SIZE='
)
foreach ($x in $requiredDecoder) {
    if (-not $decoderText.Contains($x)) { throw "Decoder missing gate marker $x" }
}

Write-Host 'STATUS=PASS_V19_STATIC_CONTRACT'
Write-Host 'TARGET_COUNT=5'
Write-Host 'EXACT_TARGET_ONLY=YES'
Write-Host 'FILE_IMAGE_ONLY=YES'
Write-Host 'PROCESS_ATTACH=NO'
Write-Host 'VIRTUAL_ONLY_DIAGNOSTIC=REQUIRED'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'REMOTE_CALL=NO'
Write-Host 'MEMORY_WRITE=NO'
