from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CONFIG = ROOT / "recovered-src-obf" / "l1j" / "server" / "a.java"
PACKET_HANDLER = ROOT / "recovered-src-obf" / "ai" / "e.java"
SERVER_PROPERTIES = ROOT / "config" / "server.properties"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def main() -> None:
    config = CONFIG.read_text(encoding="utf-8")
    packet_handler = PACKET_HANDLER.read_text(encoding="utf-8")
    server_properties = SERVER_PROPERTIES.read_text(encoding="utf-8-sig")

    require(
        'd = Boolean.parseBoolean(serverSettings.getProperty("PacketDebug", "false"));' in config,
        "Config must load PacketDebug from server.properties with a fail-closed false default",
    )
    require(
        "if (l1j.server.a.d && i2 != 45)" in packet_handler,
        "PacketHandler must keep the existing Config.d packet-dump gate",
    )
    require(
        'System.out.println("opcode: " + i2 + " [" + name + "]");' in packet_handler,
        "PacketHandler trace must print opcode and resolved handler class",
    )
    require(
        "System.out.println(bi.g.a(abyte0));" in packet_handler,
        "PacketHandler trace must print decoded packet bytes",
    )
    require(
        "PacketDebug=false" in server_properties,
        "server.properties must keep PacketDebug disabled by default",
    )
    require(
        "PacketDebug=true" not in server_properties,
        "repository default must never enable packet tracing",
    )

    print("AUTO_HUNT_NATIVE_PACKET_TRACE=PASS")


if __name__ == "__main__":
    main()
