param(
    [Parameter(Mandatory=$true)][string]$FirstPath,
    [Parameter(Mandatory=$true)][string]$SecondPath,
    [string]$OutputPath = "I:\L共通工具\LineageAIResourceToolkit\outputs\850_wp7_live_send_iat_restart_v20.txt"
)

$ErrorActionPreference='Stop'

function Read-Evidence([string]$path){
    if(-not (Test-Path -LiteralPath $path)){throw "Evidence not found: $path"}
    $rows=Get-Content -LiteralPath $path
    $kv=@{}
    $targets=New-Object System.Collections.Generic.List[string]
    foreach($raw in $rows){
        $line=$raw.Trim()
        if($line -match '^([A-Z0-9_]+)=(.*)$'){$kv[$matches[1]]=$matches[2];continue}
        if($line -match '^EXACT_TARGET=\d+\s+FUNCTION_RVA=(0x[0-9A-Fa-f]+)'){$targets.Add($matches[1].ToUpperInvariant())}
    }
    return [pscustomobject]@{Path=[IO.Path]::GetFullPath($path);Kv=$kv;Targets=@($targets | Sort-Object -Unique)}
}

$a=Read-Evidence $FirstPath
$b=Read-Evidence $SecondPath

$reasons=New-Object System.Collections.Generic.List[string]
$authorityOk=($a.Kv['CLIENT_AUTHORITY'] -eq '1' -and $b.Kv['CLIENT_AUTHORITY'] -eq '1')
if(-not $authorityOk){$reasons.Add('CLIENT_AUTHORITY')}

$shaOk=($a.Kv['CLIENT_SHA256'] -and $a.Kv['CLIENT_SHA256'] -eq $b.Kv['CLIENT_SHA256'])
if(-not $shaOk){$reasons.Add('CLIENT_SHA256')}

$distinctProcess=($a.Kv['PROCESS_START_UTC'] -and $b.Kv['PROCESS_START_UTC'] -and $a.Kv['PROCESS_START_UTC'] -ne $b.Kv['PROCESS_START_UTC'])
if(-not $distinctProcess){$reasons.Add('DISTINCT_PROCESS_INSTANCE')}

$iatStable=($a.Kv['SEND_IAT_RVA'] -and $a.Kv['SEND_IAT_RVA'] -eq $b.Kv['SEND_IAT_RVA'])
if(-not $iatStable){$reasons.Add('SEND_IAT_RVA')}

$exportMatch=($a.Kv['SEND_TARGET_EXPORT_MATCH'] -eq '1' -and $b.Kv['SEND_TARGET_EXPORT_MATCH'] -eq '1')
if(-not $exportMatch){$reasons.Add('SEND_TARGET_EXPORT_MATCH')}

$stableTargets=@($a.Targets | Where-Object {$b.Targets -contains $_} | Sort-Object -Unique)
if($stableTargets.Count -eq 0){$reasons.Add('EXACT_TARGET_INTERSECTION')}

$status=if($reasons.Count -eq 0){'PASS_RESTART_STABLE_LIVE_SEND_IAT'}else{'PARTIAL_RESTART_STABILITY'}

$lines=New-Object System.Collections.Generic.List[string]
$lines.Add("TIME=$((Get-Date).ToString('yyyy-MM-dd HH:mm:ss'))")
$lines.Add('MODE=850_WP7_LIVE_SEND_IAT_RESTART_V20')
$lines.Add("FIRST=$($a.Path)")
$lines.Add("SECOND=$($b.Path)")
$lines.Add("FIRST_PROCESS_START_UTC=$($a.Kv['PROCESS_START_UTC'])")
$lines.Add("SECOND_PROCESS_START_UTC=$($b.Kv['PROCESS_START_UTC'])")
$lines.Add("DISTINCT_PROCESS_INSTANCE=$(if($distinctProcess){1}else{0})")
$lines.Add("CLIENT_AUTHORITY_BOTH=$(if($authorityOk){1}else{0})")
$lines.Add("CLIENT_SHA_MATCH=$(if($shaOk){1}else{0})")
$lines.Add("SEND_IAT_RVA_STABLE=$(if($iatStable){1}else{0})")
$lines.Add("SEND_TARGET_EXPORT_MATCH_BOTH=$(if($exportMatch){1}else{0})")
$lines.Add("STABLE_EXACT_TARGET_COUNT=$($stableTargets.Count)")
foreach($rva in $stableTargets){$lines.Add("STABLE_EXACT_TARGET=$rva")}
$lines.Add("STATUS=$status")
$lines.Add("BLOCKERS=$($reasons -join ',')")
$lines.Add('OBJECT_ID_PROVEN=NO')
$lines.Add('ITEM_SPECIFIC_ACTION_PROVEN=NO')
$lines.Add('WP7_NATIVE_USEITEM_PASS=NO')
$lines.Add('MEMORY_WRITE=NO')
$lines.Add('PACKET_SEND=NO')
$lines.Add('NEXT=If PASS, run controlled no-action -> one manual potion -> no-action correlation only on STABLE_EXACT_TARGET RVAs.')

$outDir=Split-Path -Parent $OutputPath
if($outDir){New-Item -ItemType Directory -Force -Path $outDir | Out-Null}
$lines | Set-Content -LiteralPath $OutputPath -Encoding UTF8
$lines | ForEach-Object {Write-Host $_}
Write-Host "OUTPUT=$OutputPath"
