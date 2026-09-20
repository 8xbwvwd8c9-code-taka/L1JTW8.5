param(
    [string]$SqlPath = (Join-Path $PSScriptRoot "..\..\db\8.5.sql"),
    [string]$OutCsv  = (Join-Path $PSScriptRoot "..\..\recovery\db_table_index.csv")
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path -LiteralPath $SqlPath)) {
    throw "SQL file not found: $SqlPath"
}

$rx = [regex]'(?i)^\s*CREATE\s+TABLE\s+(?:IF\s+NOT\s+EXISTS\s+)?`?([^`\s(]+)`?'
$rows = [System.Collections.Generic.List[object]]::new()
$lineNo = 0

Get-Content -LiteralPath $SqlPath -Encoding UTF8 | ForEach-Object {
    $lineNo++
    $m = $rx.Match($_)
    if ($m.Success) {
        $rows.Add([pscustomobject]@{
            Table = $m.Groups[1].Value
            Line  = $lineNo
        })
    }
}

$rows |
    Sort-Object Table -Unique |
    Export-Csv -LiteralPath $OutCsv -NoTypeInformation -Encoding UTF8

Write-Host ("TABLES={0}" -f $rows.Count)
Write-Host ("OUT={0}" -f $OutCsv)
