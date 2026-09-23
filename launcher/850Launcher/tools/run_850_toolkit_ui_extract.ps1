param(
    [string]$ToolkitRoot = "I:\L共通工具\LineageAIResourceToolkit",
    [string]$ClientRoot = "I:\8.50c客服端",
    [string]$OutputRoot = ""
)

$ErrorActionPreference = "Stop"

if ([string]::IsNullOrWhiteSpace($OutputRoot)) {
    $OutputRoot = Join-Path $ToolkitRoot "outputs\850_launcher_ui_extract"
}

$wrapper = Join-Path $ToolkitRoot "scripts\lineage-tool.ps1"
if (-not (Test-Path -LiteralPath $wrapper)) { throw "Toolkit wrapper not found: $wrapper" }
if (-not (Test-Path -LiteralPath $ClientRoot)) { throw "Client root not found: $ClientRoot" }

New-Item -ItemType Directory -Path $OutputRoot -Force | Out-Null
$extractRoot = Join-Path $OutputRoot "extracted"
$decryptRoot = Join-Path $OutputRoot "decrypted"
New-Item -ItemType Directory -Path $extractRoot -Force | Out-Null
New-Item -ItemType Directory -Path $decryptRoot -Force | Out-Null

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
    "MODE=850_TOOLKIT_UI_RESOURCE_EXTRACT",
    "TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))",
    "TOOLKIT_ROOT=$ToolkitRoot",
    "CLIENT_ROOT=$ClientRoot",
    "OUTPUT_ROOT=$OutputRoot",
    "SOURCE_MODIFIED=NO",
    "CLIENT_WRITE=NO",
    "TOOLKIT_WRITE_SCOPE=outputs_only"
) | Out-File -LiteralPath $summary -Encoding utf8

# Always check wrapper/XML help before using extraction/decryption routes.
Invoke-ToolkitReadOnly -ToolArgs @('--help') -OutputFile '00_wrapper_help.txt' | Out-Null
Invoke-ToolkitReadOnly -ToolArgs @('pak','--help') -OutputFile '01_pak_help.txt' | Out-Null
Invoke-ToolkitReadOnly -ToolArgs @('xml','--help') -OutputFile '02_xml_help.txt' | Out-Null

$tileIdx = Join-Path $ClientRoot 'Tile.idx'
$spriteIdx = Join-Path $ClientRoot 'Sprite.idx'
if (-not (Test-Path -LiteralPath $tileIdx)) { throw "Tile.idx not found: $tileIdx" }

$tileTargets = @(
    'MainCharInfoUI.xml',
    'ClassSlotData.xml',
    'ReInventory.xml',
    'TmpInvUI.xml',
    'EquipNStatusUI.xml',
    'ReNStatusUIEx-c.xml',
    'ReNStatusWinUI-c.xml',
    'uistatus.xml',
    'uistatus86.xml',
    'RankBuffMatching.xml',
    'effectlist.xml',
    'effectlist2.xml',
    'effectlist2-c.xml',
    'SpellList.xml',
    'SpellUI.xml',
    'passiveSpells.xml',
    'PetSummonUI.xml',
    'SummonUI.xml',
    'summonList.xml',
    'MagicDollDesc.xml',
    'PromoteDollUI.xml',
    'WidgetSlot.xml',
    'WidgetSubSlot.xml',
    'Shortcut.xml',
    'QuickMotionUI.xml',
    'ItemNotiPeriod.xml',
    'uimagicitem.xml'
)

$extracted = @()
foreach ($target in $tileTargets) {
    $safe = SafeName $target
    $dest = Join-Path $extractRoot $safe
    $log = "extract_Tile_$(SafeName $target).txt"
    $code = Invoke-ToolkitReadOnly -ToolArgs @('pak','extract',$tileIdx,$target,'-o',$dest) -OutputFile $log
    if ($code -eq 0 -and (Test-Path -LiteralPath $dest)) {
        $extracted += [PSCustomObject]@{ Archive='Tile.idx'; Name=$target; Path=$dest }
    }
}

if (Test-Path -LiteralPath $spriteIdx) {
    foreach ($target in @('SpellIcon.idx','SpellDesc-k.tbl','itemextra.tbl')) {
        $safe = SafeName $target
        $dest = Join-Path $extractRoot ("Sprite_" + $safe)
        $log = "extract_Sprite_$(SafeName $target).txt"
        $code = Invoke-ToolkitReadOnly -ToolArgs @('pak','extract',$spriteIdx,$target,'-o',$dest) -OutputFile $log
        if ($code -eq 0 -and (Test-Path -LiteralPath $dest)) {
            $extracted += [PSCustomObject]@{ Archive='Sprite.idx'; Name=$target; Path=$dest }
        }
    }
}

# XML resources can be encrypted in the archive. Keep the extracted raw copy and write
# a separate decrypted copy under outputs only. Failure to decrypt is recorded, not fatal.
foreach ($row in $extracted | Where-Object { $_.Name -like '*.xml' }) {
    $safe = SafeName $row.Name
    $dec = Join-Path $decryptRoot ($safe -replace '\.xml$','.decrypted.xml')
    $log = "decrypt_$(SafeName $row.Name).txt"
    try {
        Invoke-ToolkitReadOnly -ToolArgs @('xml','decrypt',$row.Path,'-o',$dec) -OutputFile $log | Out-Null
    } catch {
        "DECRYPT_ERROR=$($_.Exception.GetType().Name): $($_.Exception.Message)" |
            Out-File -LiteralPath (Join-Path $OutputRoot $log) -Encoding utf8 -Append
    }
}

# Search the resulting plaintext/decrypted XML for high-value binding/control vocabulary.
$tokens = @('hp','mp','health','mana','gauge','bar','inventory','item','slot','buff','spell','skill','pet','summon','doll','quick','shortcut','status','current','max')
$correlationPath = Join-Path $OutputRoot '_UI_TOKEN_CORRELATION.txt'
"MODE=850_UI_TOKEN_CORRELATION" | Out-File -LiteralPath $correlationPath -Encoding utf8
foreach ($file in @(Get-ChildItem -LiteralPath $decryptRoot -File -ErrorAction SilentlyContinue) + @(Get-ChildItem -LiteralPath $extractRoot -File -ErrorAction SilentlyContinue)) {
    if ($file.Extension -notin @('.xml','.tbl','.json','.html','.idx')) { continue }
    $text = $null
    foreach ($encName in @('utf8','default')) {
        try {
            if ($encName -eq 'utf8') { $text = Get-Content -LiteralPath $file.FullName -Raw -Encoding UTF8 -ErrorAction Stop }
            else { $text = Get-Content -LiteralPath $file.FullName -Raw -ErrorAction Stop }
            if ($null -ne $text) { break }
        } catch { }
    }
    if ($null -eq $text) { continue }
    $matched = @()
    foreach ($token in $tokens) {
        if ($text.IndexOf($token,[System.StringComparison]::OrdinalIgnoreCase) -ge 0) { $matched += $token }
    }
    if ($matched.Count -gt 0) {
        Add-Content -LiteralPath $correlationPath -Encoding utf8 -Value ("FILE=" + $file.Name + " TOKENS=" + ($matched -join ','))
    }
}

$payloadManifest = Join-Path $OutputRoot '_PAYLOAD_MANIFEST.csv'
Get-ChildItem -LiteralPath $extractRoot,$decryptRoot -File -ErrorAction SilentlyContinue |
    Sort-Object FullName |
    ForEach-Object {
        $h = Get-FileHash -LiteralPath $_.FullName -Algorithm SHA256
        [PSCustomObject]@{ Path=$_.FullName; Length=$_.Length; SHA256=$h.Hash }
    } |
    Export-Csv -LiteralPath $payloadManifest -NoTypeInformation -Encoding UTF8

Add-Content -LiteralPath $summary -Encoding utf8 -Value "EXTRACTED_COUNT=$($extracted.Count)"
Add-Content -LiteralPath $summary -Encoding utf8 -Value "FINISHED=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))"
Add-Content -LiteralPath $summary -Encoding utf8 -Value 'STATUS=PASS_UI_RESOURCES_GENERATED'

$reportManifest = Join-Path $OutputRoot '_REPORT_MANIFEST.csv'
Get-ChildItem -LiteralPath $OutputRoot -File |
    Where-Object { $_.FullName -ne $reportManifest } |
    Sort-Object Name |
    ForEach-Object {
        $h = Get-FileHash -LiteralPath $_.FullName -Algorithm SHA256
        [PSCustomObject]@{ Name=$_.Name; Length=$_.Length; SHA256=$h.Hash }
    } |
    Export-Csv -LiteralPath $reportManifest -NoTypeInformation -Encoding UTF8

Write-Host "STATUS=PASS_UI_RESOURCES_GENERATED"
Write-Host "OUTPUT=$OutputRoot"
Write-Host "SOURCE_MODIFIED=NO"
