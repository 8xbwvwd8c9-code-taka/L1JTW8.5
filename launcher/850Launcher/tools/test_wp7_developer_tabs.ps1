$ErrorActionPreference = 'Stop'
$main = Join-Path (Split-Path $PSScriptRoot -Parent) 'MainForm.cs'
if (-not (Test-Path -LiteralPath $main)) { throw "Missing MainForm.cs: $main" }
$text = Get-Content -LiteralPath $main -Raw

$required = @(
    'BuildItemUseBehaviorTab',
    'new ItemUseBehaviorCorrelationControl(_appDir)',
    'BuildItemUseNativeCorrelationTab',
    'new ItemUseNativeCorrelationControl(_appDir)'
)

foreach ($needle in $required) {
    if ($text -notmatch [regex]::Escape($needle)) {
        throw "RED: missing WP7 developer UI wiring: $needle"
    }
}

$behaviorPos = $text.IndexOf('tabs.TabPages.Add(BuildItemUseBehaviorTab())')
$nativePos = $text.IndexOf('tabs.TabPages.Add(BuildItemUseNativeCorrelationTab())')
if ($behaviorPos -lt 0 -or $nativePos -lt 0 -or $behaviorPos -ge $nativePos) {
    throw 'WP7 tab ordering invalid: behavior verification must appear before native correlation.'
}

Write-Host 'STATUS=PASS_WP7_DEVELOPER_TABS'
Write-Host 'ORDER=BEHAVIOR_THEN_NATIVE'
