param(
    [string]$ClientPath = 'I:\8.50c客服端\Lin.bin2',
    [string]$OutputDir = 'I:\L共通工具\LineageAIResourceToolkit\outputs',
    [switch]$SkipSetup
)

$ErrorActionPreference = 'Stop'
$ExpectedSha256 = 'FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4'
$OutPath = Join-Path $OutputDir '850_inventory_invwin_16c_storage_v19.txt'
$DecoderDir = Join-Path $PSScriptRoot 'decoder'
$DecoderPath = Join-Path $DecoderDir 'decode_850_inventory_invwin_16c_storage_v19.py'
$SetupPath = Join-Path $DecoderDir 'setup_capstone_decoder.ps1'
$PythonPath = Join-Path $DecoderDir '.venv\Scripts\python.exe'

if (-not (Test-Path -LiteralPath $ClientPath)) { throw "Client not found: $ClientPath" }
if (-not (Test-Path -LiteralPath $DecoderPath)) { throw "Missing V19 decoder: $DecoderPath" }

$full = [IO.Path]::GetFullPath($ClientPath)
$sha = (Get-FileHash -LiteralPath $full -Algorithm SHA256).Hash.ToUpperInvariant()
if ($sha -ne $ExpectedSha256) { throw "Client authority mismatch: $sha" }

if (-not $SkipSetup -or -not (Test-Path -LiteralPath $PythonPath)) {
    if (-not (Test-Path -LiteralPath $SetupPath)) { throw "Missing decoder setup: $SetupPath" }
    & $SetupPath
}
if (-not (Test-Path -LiteralPath $PythonPath)) { throw "Decoder Python missing after setup: $PythonPath" }

$version = (& $PythonPath -c "import importlib.metadata as m; print(m.version('capstone'))" | Select-Object -Last 1).Trim()
if ($version -ne '5.0.9') { throw "Unexpected Capstone package version: $version" }

New-Item -ItemType Directory -Force -Path $OutputDir | Out-Null
& $PythonPath $DecoderPath --client $full --output $OutPath
if ($LASTEXITCODE -ne 0) { throw "V19 decoder failed: exit=$LASTEXITCODE" }
if (-not (Test-Path -LiteralPath $OutPath)) { throw "V19 output missing: $OutPath" }

Write-Host 'STATUS=PASS_INVWIN_16C_STORAGE_V19_OFFLINE_EXECUTED'
Write-Host "OUTPUT=$OutPath"
Write-Host 'TARGETS=0x0087E900,0x0087E950,0x0087E960,0x0087E970,0x0087E980'
Write-Host 'EXACT_TARGET_ONLY=YES'
Write-Host 'FILE_IMAGE_ONLY=YES'
Write-Host 'PROCESS_ATTACH=NO'
Write-Host 'HELPER_DEPTH=1'
Write-Host 'HEAP_SCAN=NO'
Write-Host 'MEM_PRIVATE_SCAN=NO'
Write-Host 'REMOTE_CALL=NO'
Write-Host 'MEMORY_WRITE=NO'
