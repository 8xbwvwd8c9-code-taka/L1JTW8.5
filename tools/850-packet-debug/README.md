# 850 Packet Debug

Standalone diagnostic launcher for the 8.50 server packet trace.

## Purpose

The recovered `l1j.server.PacketHandler` already contains a legacy trace gate:

```java
if (Config.d && opcode != 45) {
    System.out.println("opcode: " + opcode + " [" + handler + "]");
    System.out.println(LineageUtil.a(packet));
}
```

The normal recovered `Config` does not load a `PacketDebug` property, so normal startup leaves `Config.d` disabled. This module enables that existing gate only for a dedicated debug launch. It does not modify AutoHunt behavior, opcode routing, combat code, DB logic, or `Lin.bin2`.

## Files

- `PacketDebugBootstrap.java` — sets `l1j.server.Config.d=true` by reflection, then invokes the normal `l1j.server.Server.main()`.
- `packet-debug.ps1` — builds the bootstrap and launches the normal 850 dev runtime with packet tracing enabled.
- `self-test.ps1` — isolated OFF/ON test using fake `Config` and `Server` classes.

Generated bootstrap classes go under `.build850/packet-debug/classes`.
The default trace log is `logs/850-packet-debug.log`.

## One-time cleanup of the temporary Config.java experiment

If the earlier temporary `PacketDebug` loader patch is still present locally, restore the backup and rebuild once:

```powershell
cd I:\L1JTW8.5
Copy-Item .\core\src\l1j\server\Config.java.packetdebug.bak `
          .\core\src\l1j\server\Config.java -Force
.\build850.ps1
```

`PacketDebug=true` in `config/server.properties` is not required by this module. It may be removed or left inert after the original `Config.java` is restored.

## Self-test

```powershell
cd I:\L1JTW8.5
.\tools\850-packet-debug\self-test.ps1
```

Expected:

```text
SELF_TEST=PASS
NORMAL_SERVER_DEBUG=false
PACKET_DEBUG_SERVER_DEBUG=true
```

## Build only

```powershell
.\tools\850-packet-debug\packet-debug.ps1 -BuildOnly
```

## Run packet-debug server

Stop any existing 850 dev server first, then:

```powershell
cd I:\L1JTW8.5
.\tools\850-packet-debug\packet-debug.ps1
```

Expected startup marker:

```text
PACKET_DEBUG=ON
[850_PACKET_DEBUG] Config.d: false -> true
```

After a client connects, the server console and `logs/850-packet-debug.log` will contain entries such as:

```text
opcode: 25 [C_ProtoBuffers]
0000: 19 08 02 03 00 08 8f 02
```

## Normal server

Use the normal command when packet tracing is not needed:

```powershell
.\build850.ps1 -Run
```

Normal startup does not use `PacketDebugBootstrap`, so this module does not enable `Config.d`.
