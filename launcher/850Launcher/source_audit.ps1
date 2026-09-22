param()

$ErrorActionPreference = "Stop"

$root = $PSScriptRoot
$project = Join-Path $root "850Launcher.csproj"

if (-not (Test-Path -LiteralPath $project)) {
    Write-Host "STATUS=FAIL"
    Write-Host "REASON=PROJECT_NOT_FOUND"
    exit 1
}

$actual = @(
    Get-ChildItem -LiteralPath $root -Filter *.cs -File |
    ForEach-Object { $_.Name } |
    Sort-Object
)

$projectText = Get-Content -LiteralPath $project -Raw
$compiled = @(
    [regex]::Matches($projectText, '<Compile\s+Include="([^"]+)"\s*/>') |
    ForEach-Object { $_.Groups[1].Value } |
    Sort-Object
)

$missingFromProject = @(
    $actual | Where-Object { $_ -notin $compiled }
)

$missingSource = @(
    $compiled | Where-Object { $_ -notin $actual }
)

$errors = New-Object System.Collections.Generic.List[string]

foreach ($name in $missingFromProject) {
    $errors.Add("CS_NOT_IN_PROJECT:$name")
}

foreach ($name in $missingSource) {
    $errors.Add("PROJECT_SOURCE_MISSING:$name")
}

function Test-CSharpLexical {
    param(
        [string]$Path,
        [string]$Text
    )

    $state = "normal"
    $line = 1
    $startLine = 0

    for ($i = 0; $i -lt $Text.Length; $i++) {
        $ch = $Text[$i]
        $next = if ($i + 1 -lt $Text.Length) { $Text[$i + 1] } else { [char]0 }

        switch ($state) {
            "normal" {
                if ($ch -eq '/' -and $next -eq '/') {
                    $state = "lineComment"
                    $i++
                    continue
                }

                if ($ch -eq '/' -and $next -eq '*') {
                    $state = "blockComment"
                    $i++
                    continue
                }

                if ($ch -eq "'") {
                    $state = "char"
                    $startLine = $line
                    continue
                }

                if ($ch -eq '"') {
                    $prev1 = if ($i -ge 1) { $Text[$i - 1] } else { [char]0 }
                    $prev2 = if ($i -ge 2) { $Text[$i - 2] } else { [char]0 }

                    $isVerbatim =
                        ($prev1 -eq '@') -or
                        ($prev1 -eq '$' -and $prev2 -eq '@')

                    $state = if ($isVerbatim) { "verbatimString" } else { "string" }
                    $startLine = $line
                    continue
                }
            }

            "lineComment" {
                if ([int]$ch -eq 10) {
                    $state = "normal"
                }
            }

            "blockComment" {
                if ($ch -eq '*' -and $next -eq '/') {
                    $state = "normal"
                    $i++
                }
            }

            "char" {
                if ($ch -eq '\') {
                    $i++
                    continue
                }

                if ($ch -eq "'") {
                    $state = "normal"
                    continue
                }

                if ([int]$ch -eq 10) {
                    $errors.Add("CHAR_LITERAL_NEWLINE:$($Path):$startLine")
                    $state = "normal"
                }
            }

            "string" {
                if ($ch -eq '\') {
                    $i++
                    continue
                }

                if ($ch -eq '"') {
                    $state = "normal"
                    continue
                }

                if ([int]$ch -eq 10) {
                    $errors.Add("ORDINARY_STRING_NEWLINE:$($Path):$startLine")
                    $state = "normal"
                }
            }

            "verbatimString" {
                if ($ch -eq '"') {
                    if ($next -eq '"') {
                        $i++
                        continue
                    }

                    $state = "normal"
                }
            }
        }

        if ([int]$ch -eq 10) {
            $line++
        }
    }

    if ($state -eq "string") {
        $errors.Add("UNTERMINATED_STRING:$($Path):$startLine")
    }

    if ($state -eq "char") {
        $errors.Add("UNTERMINATED_CHAR:$($Path):$startLine")
    }
}

foreach ($file in Get-ChildItem -LiteralPath $root -Filter *.cs -File) {
    $text = Get-Content -LiteralPath $file.FullName -Raw

    Test-CSharpLexical -Path $file.Name -Text $text

    if ($text.Contains('"""')) {
        $errors.Add("TRIPLE_QUOTE_CSHARP4:$($file.Name)")
    }

    if ($text -match '\(\?<[^>]+>d[+*?]') {
        $errors.Add("REGEX_MISSING_BACKSLASH_D:$($file.Name)")
    }

    if ($text -match '\(\?<[^>]+>S[+*?]') {
        $errors.Add("REGEX_MISSING_BACKSLASH_S:$($file.Name)")
    }
}

if ($errors.Count -gt 0) {
    Write-Host "STATUS=FAIL"
    Write-Host "CS_FILES=$($actual.Count)"
    Write-Host "CSPROJ_COMPILE_ENTRIES=$($compiled.Count)"
    foreach ($errorText in $errors) {
        Write-Host "ERROR=$errorText"
    }
    exit 1
}

Write-Host "STATUS=PASS"
Write-Host "CS_FILES=$($actual.Count)"
Write-Host "CSPROJ_COMPILE_ENTRIES=$($compiled.Count)"
Write-Host "MISSING_FROM_PROJECT=0"
Write-Host "MISSING_SOURCE_FILES=0"
Write-Host "LEXICAL_ESCAPE_AUDIT=PASS"
exit 0
