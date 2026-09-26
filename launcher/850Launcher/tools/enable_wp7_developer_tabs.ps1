param(
    [string]$MainFormPath = (Join-Path (Split-Path $PSScriptRoot -Parent) 'MainForm.cs')
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path -LiteralPath $MainFormPath)) {
    throw "MainForm.cs not found: $MainFormPath"
}

$text = Get-Content -LiteralPath $MainFormPath -Raw
$original = $text

$tabAnchor = '                tabs.TabPages.Add(BuildItemUseProtocolTab());'
$tabInsert = @'
                tabs.TabPages.Add(BuildItemUseProtocolTab());
                tabs.TabPages.Add(BuildItemUseBehaviorTab());
                tabs.TabPages.Add(BuildItemUseNativeCorrelationTab());
'@

if ($text -notmatch 'BuildItemUseBehaviorTab\(\)') {
    if (-not $text.Contains($tabAnchor)) {
        throw 'Tab anchor not found; MainForm layout changed.'
    }
    $text = $text.Replace($tabAnchor, $tabInsert.TrimEnd("`r", "`n"))
}

$methodAnchor = @'
        private TabPage BuildNativeSendProbeTab()
        {
'@

$methodInsert = @'
        private TabPage BuildItemUseBehaviorTab()
        {
            var p = NewPage("UseItem行為");
            p.Controls.Add(new ItemUseBehaviorCorrelationControl(_appDir));
            return p;
        }

        private TabPage BuildItemUseNativeCorrelationTab()
        {
            var p = NewPage("UseItem Native");
            p.Controls.Add(new ItemUseNativeCorrelationControl(_appDir));
            return p;
        }

        private TabPage BuildNativeSendProbeTab()
        {
'@

if ($text -notmatch 'private TabPage BuildItemUseBehaviorTab\(\)') {
    if (-not $text.Contains($methodAnchor.TrimStart("`r", "`n"))) {
        throw 'Method anchor not found; MainForm layout changed.'
    }
    $text = $text.Replace(
        $methodAnchor.TrimStart("`r", "`n"),
        $methodInsert.TrimStart("`r", "`n"))
}

if ($text -eq $original) {
    Write-Host 'STATUS=NOOP_ALREADY_ENABLED'
    exit 0
}

$backup = "$MainFormPath.wp7-before"
if (-not (Test-Path -LiteralPath $backup)) {
    [System.IO.File]::WriteAllText($backup, $original, [System.Text.UTF8Encoding]::new($false))
}

[System.IO.File]::WriteAllText($MainFormPath, $text, [System.Text.UTF8Encoding]::new($false))
Write-Host 'STATUS=PASS_PATCHED'
Write-Host "FILE=$MainFormPath"
Write-Host "BACKUP=$backup"
Write-Host 'WP7_TABS=UseItem行為;UseItem Native'
