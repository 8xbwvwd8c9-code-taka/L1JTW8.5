from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
DISCONNECT = ROOT / "recovered-src-obf" / "aj" / "ah.java"
RESTART = ROOT / "recovered-src-obf" / "aj" / "bv.java"
SERVICE = ROOT / "recovered-src-obf" / "auto" / "hunt" / "AutoHuntService.java"


def require(condition: bool, message: str) -> None:
    if not condition:
        raise AssertionError(message)


def main() -> None:
    disconnect = DISCONNECT.read_text(encoding="utf-8")
    restart = RESTART.read_text(encoding="utf-8")
    service = SERVICE.read_text(encoding="utf-8")

    require("public static void stop(u pc)" in service, "AutoHuntService.stop(u) must remain static")

    disconnect_stop = disconnect.index("AutoHuntService.stop(pc);")
    disconnect_cleanup = disconnect.index("client.c();")
    require(disconnect_stop < disconnect_cleanup, "disconnect must stop auto-hunt before client cleanup")

    restart_stop = restart.index("AutoHuntService.stop(pc);")
    restart_cleanup = restart.index("pc.p();")
    require(restart_stop < restart_cleanup, "restart must stop auto-hunt before player cleanup")

    require(disconnect.count("AutoHuntService.stop(pc);") == 1, "disconnect hook must be exactly once")
    require(restart.count("AutoHuntService.stop(pc);") == 1, "restart hook must be exactly once")

    print("AUTO_HUNT_PACKET_LIFECYCLE_HOOKS=PASS")


if __name__ == "__main__":
    main()
