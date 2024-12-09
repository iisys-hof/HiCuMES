@echo off

:: Parameters
SET SERVICE_NAME=hicumes-feinplanung

:: Stop running Service
for /F "tokens=3 delims=: " %%H in ('@sc query "%SERVICE_NAME%" ^| findstr "        STATE"') do (
  @if /I "%%H" NEQ "STOPPED" (
   sc stop %SERVICE_NAME%
   ECHO Waiting for service to Stop
   ::Sleep 1
   ping -n 1 localhost > nul
  )
)

sc delete %SERVICE_NAME%
pause.
