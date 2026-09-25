import unittest
from pathlib import Path


ROOT = Path(__file__).resolve().parents[3]
DRIVER = ROOT / "build850.ps1"


class WindowsUtf8DriverContracts(unittest.TestCase):
    def test_build_driver_forces_python_utf8_mode(self):
        text = DRIVER.read_text(encoding="utf-8")
        self.assertRegex(
            text,
            r"&\s+\$Python\.Source\s+-X\s+utf8\s+\$Script",
            "build850.ps1 must start Python in UTF-8 mode so Windows CP950 cannot decode Git UTF-8 output",
        )


if __name__ == "__main__":
    unittest.main()
