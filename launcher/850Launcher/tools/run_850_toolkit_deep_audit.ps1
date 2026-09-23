param(
    [string]$ToolkitRoot = "I:\L共通工具\LineageAIResourceToolkit",
    [string]$ClientRoot = "I:\8.50c客服端",
    [string]$OutputRoot = ""
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($OutputRoot)) {
    $OutputRoot = Join-Path $ToolkitRoot "outputs\850_launcher_deep_audit"
}

$wrapper = Join-Path $ToolkitRoot "scripts\lineage-tool.ps1"
if (-not (Test-Path -LiteralPath $wrapper)) { throw "Toolkit wrapper not found: $wrapper" }
if (-not (Test-Path -LiteralPath $ClientRoot)) { throw "Client root not found: $ClientRoot" }

New-Item -ItemType Directory -Path $OutputRoot -Force | Out-Null
$extractRoot = Join-Path $OutputRoot "extracted"
New-Item -ItemType Directory -Path $extractRoot -Force | Out-Null

function SafeName([string]$value) {
    if ([string]::IsNullOrWhiteSpace($value)) { return "empty" }
    return ($value -replace '[^0-9A-Za-z._-]+','_').Trim('_')
}

function Invoke-ToolkitReadOnly {
    param(
        [Parameter(Mandatory=$true)][string[]]$ToolArgs,
        [Parameter(Mandatory=$true)][string]$OutputFile
    )
    $dest = Join-Path $OutputRoot $OutputFile
    $allArgs = @('-NoProfile','-ExecutionPolicy','Bypass','-File',$wrapper) + $ToolArgs
    "COMMAND=powershell.exe $($allArgs -join ' ')" | Out-File -LiteralPath $dest -Encoding utf8
    & powershell.exe @allArgs 2>&1 | Out-File -LiteralPath $dest -Encoding utf8 -Append
    $code = $LASTEXITCODE
    "EXIT_CODE=$code" | Out-File -LiteralPath $dest -Encoding utf8 -Append
    return $code
}

$summary = Join-Path $OutputRoot "_SUMMARY.txt"
@(
    "MODE=850_TOOLKIT_DEEP_RESOURCE_AUDIT",
    "TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))",
    "TOOLKIT_ROOT=$ToolkitRoot",
    "CLIENT_ROOT=$ClientRoot",
    "OUTPUT_ROOT=$OutputRoot",
    "SOURCE_MODIFIED=NO",
    "CLIENT_WRITE=NO",
    "TOOLKIT_WRITE_SCOPE=outputs_only"
) | Out-File -LiteralPath $summary -Encoding utf8

$idx = @{}
foreach ($name in @('Text.idx','Sprite.idx','Sprite00.idx','Tile.idx')) {
    $path = Join-Path $ClientRoot $name
    if (Test-Path -LiteralPath $path) {
        $idx[$name] = $path
        Add-Content -LiteralPath $summary -Encoding utf8 -Value "$($name.ToUpperInvariant().Replace('.','_'))=$path"
    }
}

# Search high-value resource names across the main client archives.
$terms = @(
    'MainCharInfoUI','ClassSlotData','effectlist','spell','buff','skill','status','charinfo',
    'inventory','item','doll','pet','summon','quick','shortcut','slot','gauge','hp','mp','icon','ui'
)

foreach ($archiveName in @('Text.idx','Sprite.idx','Sprite00.idx','Tile.idx')) {
    if (-not $idx.ContainsKey($archiveName)) { continue }
    $archive = $idx[$archiveName]
    $archiveSafe = SafeName $archiveName
    foreach ($term in $terms) {
        $out = "search_${archiveSafe}_$(SafeName $term).txt"
        Invoke-ToolkitReadOnly -ToolArgs @('pak','search',$archive,$term) -OutputFile $out | Out-Null
    }
}

# Pull only selected static Text resources into the toolkit output area. This is read-only
# with respect to the client archive and gives later analysis actual payload bytes/text.
if ($idx.ContainsKey('Text.idx')) {
    $textIdx = $idx['Text.idx']
    $targets = @(
        'string-c.tbl',
        'ItemDesc-c.tbl',
        'itemextra.tbl',
        'ShopItemDesc.tbl',
        'ConfirmItem.tbl',
        'alertitems.tbl',
        'spelldesc-c.tbl',
        'spelldur.tbl',
        'SpellExDesc-c.tbl',
        'MagicDoll.tbl',
        'CharAttrType.tbl',
        'psbuff.json',
        'summonlist-c.html',
        'ntexpet-c.tbl'
    )

    foreach ($target in $targets) {
        $dest = Join-Path $extractRoot (SafeName $target)
        $log = "extract_$(SafeName $target).txt"
        Invoke-ToolkitReadOnly -ToolArgs @('pak','extract',$textIdx,$target,'-o',$dest) -OutputFile $log | Out-Null
    }
}

# Compact inventory of produced payloads and reports.
$payloadManifest = Join-Path $OutputRoot '_EXTRACTED_MANIFEST.csv'
Get-ChildItem -LiteralPath $extractRoot -File -ErrorAction SilentlyContinue |
    Sort-Object Name |
    ForEach-Object {
        $h = Get-FileHash -LiteralPath $_.FullName -Algorithm SHA256
        [PSCustomObject]@{ Name=$_.Name; Length=$_.Length; SHA256=$h.Hash }
    } |
    Export-Csv -LiteralPath $payloadManifest -NoTypeInformation -Encoding UTF8

Add-Content -LiteralPath $summary -Encoding utf8 -Value "FINISHED=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))"
Add-Content -LiteralPath $summary -Encoding utf8 -Value 'STATUS=PASS_DEEP_REPORTS_GENERATED'

$reportManifest = Join-Path $OutputRoot '_REPORT_MANIFEST.csv'
Get-ChildItem -LiteralPath $OutputRoot -File |
    Where-Object { $_.FullName -ne $reportManifest } |
    Sort-Object Name |
    ForEach-Object {
        $h = Get-FileHash -LiteralPath $_.FullName -Algorithm SHA256
        [PSCustomObject]@{ Name=$_.Name; Length=$_.Length; SHA256=$h.Hash }
    } |
    Export-Csv -LiteralPath $reportManifest -NoTypeInformation -Encoding UTF8

Write-Host "STATUS=PASS_DEEP_REPORTS_GENERATED"
Write-Host "OUTPUT=$OutputRoot"
Write-Host "SOURCE_MODIFIED=NO"
