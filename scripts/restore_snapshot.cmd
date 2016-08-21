@echo off

echo '******* Check the environment variables have been set correctly in setEnv.cmd before continuing ********'
pause

:PRECHECK
echo Are the environment variables correct?
set /P CHECKRESP=Type y or n and press enter:
if %CHECKRESP%==y goto RESTOREDB
if %CHECKRESP%==n goto QUITTING
goto PRECHECK

rem set env 
call %~dp0setEnv.cmd

:RESTOREDB
rem Restoring database
rman cmdfile=%SCRIPT_BASE%\restore_from_cold_backup.rman '%RESTORE_PFILE%' '%RESTORE_CFILE%' '%RMAN_FRA%' log=%SCRIPT_LOGS%\restore_snapshot%RTIMESTAMP%.log
goto COMPLETE

:COMPLETE
echo '******* Tasks complete, please check logs. ********'
pause
exit

:QUITTING
echo 'You have quit the application, no action has been taken.'
pause
exit
