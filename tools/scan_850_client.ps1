param(
    [Parameter(Mandatory=$true)]
    [string]$ClientPath,
    [string]$OutDir = ".\artifacts\850-client-static"
)

$ErrorActionPreference = "Stop"
$path = (Resolve-Path $ClientPath).Path
New-Item -ItemType Directory -Force -Path $OutDir | Out-Null
$out = (Resolve-Path $OutDir).Path

function Write-Utf8([string]$Path, [string[]]$Lines) {
    [IO.File]::WriteAllLines($Path, $Lines, [Text.UTF8Encoding]::new($false))
}

$fi = Get-Item $path
$sha256 = (Get-FileHash $path -Algorithm SHA256).Hash
$sha1 = (Get-FileHash $path -Algorithm SHA1).Hash
$md5 = (Get-FileHash $path -Algorithm MD5).Hash

Write-Utf8 (Join-Path $out "HASHES.txt") @(
    "FILE=$($fi.Name)",
    "SIZE=$($fi.Length)",
    "SHA256=$sha256",
    "SHA1=$sha1",
    "MD5=$md5"
)

$bytes = [IO.File]::ReadAllBytes($path)
if ($bytes.Length -lt 0x100 -or $bytes[0] -ne 0x4D -or $bytes[1] -ne 0x5A) {
    throw "Not a valid MZ/PE candidate: $path"
}

$e_lfanew = [BitConverter]::ToInt32($bytes, 0x3C)
if ($e_lfanew -lt 0 -or ($e_lfanew + 24) -ge $bytes.Length) {
    throw "Invalid PE header offset"
}
if ($bytes[$e_lfanew] -ne 0x50 -or $bytes[$e_lfanew+1] -ne 0x45) {
    throw "PE signature missing"
}

$machine = [BitConverter]::ToUInt16($bytes, $e_lfanew + 4)
$sections = [BitConverter]::ToUInt16($bytes, $e_lfanew + 6)
$optSize = [BitConverter]::ToUInt16($bytes, $e_lfanew + 20)
$magic = [BitConverter]::ToUInt16($bytes, $e_lfanew + 24)
$arch = switch ($machine) {
    0x014c { "x86" }
    0x8664 { "x64" }
    default { ('0x{0:X4}' -f $machine) }
}
$peKind = switch ($magic) {
    0x10b { "PE32" }
    0x20b { "PE32+" }
    default { ('0x{0:X4}' -f $magic) }
}

$ascii = [Text.Encoding]::ASCII.GetString($bytes)
$asciiMatches = [regex]::Matches($ascii, '[ -~]{4,}') | ForEach-Object Value
$asciiMatches = $asciiMatches | Sort-Object -Unique
Write-Utf8 (Join-Path $out "STRINGS_ASCII.txt") $asciiMatches

$unicodeText = [Text.Encoding]::Unicode.GetString($bytes)
$unicodeMatches = [regex]::Matches($unicodeText, '[\x20-\x7E]{4,}') | ForEach-Object Value
$unicodeMatches = $unicodeMatches | Sort-Object -Unique
Write-Utf8 (Join-Path $out "STRINGS_UTF16.txt") $unicodeMatches

$terms = @(
    'item','inventory','equip','skill','spell','buff',
    'hp','mp','health','mana','player','character',
    'object','count','enchant','use','packet','socket',
    'login','server','port','127.0.0.1'
)
$interesting = foreach ($s in ($asciiMatches + $unicodeMatches)) {
    foreach ($t in $terms) {
        if ($s.IndexOf($t, [StringComparison]::OrdinalIgnoreCase) -ge 0) {
            $s
            break
        }
    }
}
$interesting = $interesting | Sort-Object -Unique
Write-Utf8 (Join-Path $out "INTERESTING_STRINGS.txt") $interesting

$toolResults = @()
foreach ($cmd in @("dumpbin.exe","llvm-objdump.exe","objdump.exe")) {
    $g = Get-Command $cmd -ErrorAction SilentlyContinue
    if (-not $g) { continue }
    try {
        if ($cmd -eq "dumpbin.exe") {
            & $g.Source /headers /imports $path 2>&1 |
                Out-File -Encoding utf8 (Join-Path $out "PE_HEADERS_IMPORTS.txt")
        } elseif ($cmd -eq "llvm-objdump.exe") {
            & $g.Source --private-headers --all-headers $path 2>&1 |
                Out-File -Encoding utf8 (Join-Path $out "PE_HEADERS_IMPORTS.txt")
        } else {
            & $g.Source -x -p $path 2>&1 |
                Out-File -Encoding utf8 (Join-Path $out "PE_HEADERS_IMPORTS.txt")
        }
        $toolResults += "PE_TOOL=$cmd"
        break
    } catch {
        $toolResults += "PE_TOOL_FAIL=$cmd : $($_.Exception.Message)"
    }
}
if (-not $toolResults) {
    $toolResults += "PE_TOOL=NONE"
}

$summary = @(
    "SOURCE_ENCODING=ASCII_SAFE",
    "STATUS=PASS",
    "FILE=$($fi.Name)",
    "SIZE=$($fi.Length)",
    "ARCH=$arch",
    "PE_KIND=$peKind",
    "SECTIONS=$sections",
    "OPTIONAL_HEADER_SIZE=$optSize",
    "SHA256=$sha256"
) + $toolResults + @(
    "ASCII_STRINGS=$($asciiMatches.Count)",
    "UTF16_STRINGS=$($unicodeMatches.Count)",
    "INTERESTING_STRINGS=$($interesting.Count)",
    "NEXT=Use this artifact only as 850 evidence; do not import donor addresses."
)
Write-Utf8 (Join-Path $out "SUMMARY.txt") $summary

$summary | ForEach-Object { Write-Host $_ }
