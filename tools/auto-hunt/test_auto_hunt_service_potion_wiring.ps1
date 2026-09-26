param(
    [string]$RepoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
)

$ErrorActionPreference = 'Stop'
$service = Join-Path $RepoRoot 'recovered-src-obf\auto\hunt\AutoHuntService.java'
$session = Join-Path $RepoRoot 'recovered-src-obf\auto\hunt\AutoHunt850Session.java'
if (-not (Test-Path -LiteralPath $service)) { throw "Missing service: $service" }
if (-not (Test-Path -LiteralPath $session)) { throw "Missing session: $session" }

$serviceText = [IO.File]::ReadAllText($service)
$sessionText = [IO.File]::ReadAllText($session)

$requiredSession = @(
    'interface TickAction',
    'boolean onTick()',
    'if (tickAction != null && !tickAction.onTick())',
    'tickAction.reset()'
)
foreach ($x in $requiredSession) {
    if (-not $sessionText.Contains($x)) { throw "Session missing pre-target contract: $x" }
}

$requiredService = @(
    'new AutoHunt850Session.TickAction()',
    'AutoHuntConsumableController.tryConsume(',
    'potionAdapter.reset()'
)
foreach ($x in $requiredService) {
    if (-not $serviceText.Contains($x)) { throw "Service missing potion tick wiring: $x" }
}

$tickPos = $serviceText.IndexOf('new AutoHunt850Session.TickAction()')
$targetPos = $serviceText.IndexOf('new AutoHunt850Session.TargetAction()')
$potionPos = $serviceText.IndexOf('AutoHuntConsumableController.tryConsume(')
if ($tickPos -lt 0 -or $targetPos -lt 0 -or $potionPos -lt 0) { throw 'Missing wiring markers' }
if (-not ($tickPos -lt $potionPos -and $potionPos -lt $targetPos)) {
    throw 'Potion controller must be wired in pre-target action, before combat TargetAction'
}

Write-Host 'STATUS=PASS_AUTO_HUNT_SERVICE_POTION_WIRING'
Write-Host 'POTION_PHASE=PRE_TARGET_TICK'
Write-Host 'NO_TARGET_REQUIRED=YES'
Write-Host 'STOP_RESET=YES'
