@echo off

echo '******* Backing up database online ********'

rem set env 
call %~dp0setEnv.cmd

:BACKUPDB
rem Backing up database online
echo "cmdfile=%SCRIPT_BASE%\hot_backup.rman '%REFRESH_FRA%' %RTIMESTAMP% log=%SCRIPT_LOGS%\hot_backup%RTIMESTAMP%.log"
goto COMPLETE

:COMPLETE
echo '******* Tasks complete, please check logs. ********'
exit
