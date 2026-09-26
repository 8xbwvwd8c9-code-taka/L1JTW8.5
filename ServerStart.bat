@ECHO OFF
CD /D "%~dp0"

powershell.exe -NoProfile -ExecutionPolicy Bypass -File "%~dp0build850.ps1" -Run

PAUSE
