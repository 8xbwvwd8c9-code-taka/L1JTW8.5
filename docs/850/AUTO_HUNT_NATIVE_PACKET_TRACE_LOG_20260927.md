# L1JTW8.5 850 Native Auto-Hunt Packet Trace Log — 2026-09-27

## STATUS

```text
BRANCH=work/850-auto-hunting
NATIVE_UI_ENTRY=RUNTIME_CORRELATION_REQUIRED
PACKET_TRACE_INFRA=READY
PACKET_OPCODE=NOT_ASSUMED
PACKET_ADAPTER=NOT_WIRED_YET
FULL_PROJECT_COMPILE=NOT_CLAIMED
```

## Authoritative findings

850 `class_source_mapping.csv` maps:

```text
ai.e  = PacketHandler.java
bj.d  = ClientThread.java
aj.bs = C_ProtoBuffers.java
aj.aq = C_ExtraCommand.java
aj.t  = C_CharcterConfig.java
```

### Raw inbound dispatch

Source:

```text
recovered-src-obf/ai/e.java
method: a(byte[] abyte0)
```

The first decoded client byte is used as the opcode:

```text
i2 = abyte0[0] & 0xFF
```

The existing handler already contains a diagnostic dump guarded by `l1j.server.a.d`:

```text
opcode + resolved C_* handler class
hex dump of the decoded packet bytes
```

This dump happens before `cpacket.i()` executes.

Known mapping observed in the authoritative 850 dispatcher:

```text
opcode 25 -> aj.bs -> C_ProtoBuffers
```

This is only a dispatcher fact. It is **not** yet evidence that the native auto-hunt button uses opcode 25.

### Candidate paths ruled out as direct auto-hunt ON/OFF transport

`recovered-src-obf/aj/aq.java` (`C_ExtraCommand`) reads an action ID and broadcasts character action graphics. It does not expose an auto-hunt start/stop state machine.

`recovered-src-obf/aj/t.java` (`C_CharcterConfig`) reads a byte blob and persists character configuration data. It does not directly start/stop auto-hunt.

`C_ProtoBuffers` remains a plausible modern UI transport candidate because many 850 UI operations are multiplexed there, but it stays **UNVERIFIED** until native ON/OFF runtime correlation proves it.

## Recovered trace switch

Before this change, `PacketHandler` checked `l1j.server.a.d`, but `recovered-src-obf/l1j/server/a.java` did not load any setting into that field, leaving the existing dump effectively disconnected.

The trace switch is now restored through:

```text
config/server.properties
PacketDebug=false
```

and:

```text
recovered-src-obf/l1j/server/a.java
Config.b()
  -> d = Boolean.parseBoolean(serverSettings.getProperty("PacketDebug", "false"));
```

The repository default remains `false`; this is diagnostic-only infrastructure.

## Runtime correlation procedure

On the authoritative 850 client/server pair:

```text
1. Set config/server.properties: PacketDebug=true
2. Restart the 850 server so Config.b() reloads the property.
3. Log in and reach a quiet state with no unnecessary actions.
4. Capture a short baseline of packet output.
5. Click the native auto-hunt button OFF -> ON exactly once.
6. Record the new `opcode: N [Handler]` line and the following packet hex dump.
7. Click the same button ON -> OFF exactly once.
8. Record the new opcode / handler / bytes again.
9. Repeat ON once to confirm repeated-state behavior and payload stability.
10. Set PacketDebug=false again after capture.
```

Correlation must distinguish background keepalive/movement packets from the packet(s) appearing only when the native button is toggled.

Do not wire `AutoHuntService` until the ON/OFF capture proves the transport and state field/command semantics.

## TDD / validation

RED gate added first:

```text
tools/auto-hunt/validate_native_packet_trace.py
commit=f601535dc1b3ece55004a78fdeb4fcfda0da2baa
```

Observed RED failure before production change:

```text
AssertionError: Config must load PacketDebug from server.properties with a fail-closed false default
```

GREEN implementation:

```text
aeb8e904b1de2b5f72c6a6d91f1f4f297a3828fd
  recovered-src-obf/l1j/server/a.java

94d9d42bda8aecc0c29fdb16571df1c2fa8c6c2b
  config/server.properties
```

Fresh source-contract result after the minimal change:

```text
AUTO_HUNT_NATIVE_PACKET_TRACE=PASS
```

This validation covers the diagnostic source contract only. A full-project compile was not executed and is not claimed.

## Modified files

```text
tools/auto-hunt/validate_native_packet_trace.py
recovered-src-obf/l1j/server/a.java
config/server.properties
docs/850/AUTO_HUNT_NATIVE_PACKET_TRACE_LOG_20260927.md
```

## Blocker

The remaining blocker is runtime evidence from the actual 850 native button. `Lin.bin2` is a binary client module and the GitHub text connector cannot decode it as source; therefore the exact click handler/native send path cannot be proven statically from the repository connection alone.

The server side is now ready to capture the authoritative transport without guessing an opcode.

## NEXT

```text
NATIVE_BUTTON_OFF_TO_ON_CAPTURE
NATIVE_BUTTON_ON_TO_OFF_CAPTURE
COMPARE_OPCODE_HANDLER_PAYLOAD
PROVE_EXACT_SERVER_HANDLER
ONLY_THEN_WIRE_850_PACKET_ADAPTER_TO_AutoHuntService
```
