@echo off

:: Parameters
SET SERVICE_NAME=hicumes-feinplanung
SET DISPLAY_NAME="HiCuMES-Feinplanung"
SET BINARY_NAME=Api.exe
SET SERVICE_DESCRIPTION="HiCuMES"

:: Check Privileges
cd /d %~dp0%
SET HOME=%~dp0%

:: Service Install
SET BIN_PATH=%HOME%%BINARY_NAME%
ECHO Installing Service %DISPLAY_NAME% (%SERVICE_NAME%)
ECHO with binary "%BIN_PATH%"

sc create %SERVICE_NAME% binPath= "%BIN_PATH%" DisplayName= %DISPLAY_NAME%
sc failure %SERVICE_NAME%  actions= restart/10000/restart/10000/restart/10000 reset= 86400
sc description %SERVICE_NAME% "%SERVICE_DESCRIPTION%"
sc config %SERVICE_NAME% start= auto
sc start %SERVICE_NAME%

pause.

