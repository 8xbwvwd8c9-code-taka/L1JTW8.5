$ErrorActionPreference = 'Stop'

$here = Split-Path -Parent $MyInvocation.MyCommand.Path
$bootstrap = Join-Path $here 'PacketDebugBootstrap.java'
if (-not (Test-Path $bootstrap)) {
    throw "Missing bootstrap source: $bootstrap"
}

$temp = Join-Path ([System.IO.Path]::GetTempPath()) ("850-packet-debug-test-" + [Guid]::NewGuid().ToString('N'))
$src = Join-Path $temp 'src'
$classes = Join-Path $temp 'classes'
$fakePkg = Join-Path $src 'l1j\server'

New-Item -ItemType Directory -Force -Path $fakePkg, $classes | Out-Null

@'
package l1j.server;
public final class Config {
    public static boolean d = false;
}
'@ | Set-Content -Encoding ascii (Join-Path $fakePkg 'Config.java')

@'
package l1j.server;
public final class Server {
    public static void main(String[] args) {
        System.out.println("SERVER_DEBUG=" + Config.d);
    }
}
'@ | Set-Content -Encoding ascii (Join-Path $fakePkg 'Server.java')

try {
    & javac -source 8 -target 8 -encoding UTF-8 -d $classes `
        (Join-Path $fakePkg 'Config.java') `
        (Join-Path $fakePkg 'Server.java') `
        $bootstrap
    if ($LASTEXITCODE -ne 0) {
        throw "javac failed: exit=$LASTEXITCODE"
    }

    $normal = (& java -cp $classes l1j.server.Server 2>&1 | Out-String).Trim()
    if ($normal -notmatch 'SERVER_DEBUG=false') {
        throw "OFF test failed. Output: $normal"
    }

    $debug = (& java -cp $classes PacketDebugBootstrap 2>&1 | Out-String).Trim()
    if ($debug -notmatch '\[850_PACKET_DEBUG\] Config\.d: false -> true') {
        throw "Bootstrap marker missing. Output: $debug"
    }
    if ($debug -notmatch 'SERVER_DEBUG=true') {
        throw "ON test failed. Output: $debug"
    }

    'SELF_TEST=PASS'
    'NORMAL_SERVER_DEBUG=false'
    'PACKET_DEBUG_SERVER_DEBUG=true'
}
finally {
    Remove-Item -Recurse -Force $temp -ErrorAction SilentlyContinue
}
