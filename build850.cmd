@echo off
setlocal
pwsh -NoLogo -NoProfile -ExecutionPolicy Bypass -File "%~dp0build850.ps1" %*
exit /b %ERRORLEVEL%
