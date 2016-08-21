@echo off

echo '******* Check the environment variables have been set correctly in setEnv.cmd before continuing ********'
pause

:PRECHECK
echo Are the environment variables correct?
set /P CHECKRESP=Type y or n and press enter:
if %CHECKRESP%==y goto BACKUPDB
if %CHECKRESP%==n goto QUITTING
goto PRECHECK

rem set env 
call %~dp0setEnv.cmd

:BACKUPDB
rem Backing up database offline
rman cmdfile=%SCRIPT_BASE%\coldbackup.rman '%RMAN_FRA%' %RTIMESTAMP% log=%SCRIPT_LOGS%\snapshot%RTIMESTAMP%.log
goto COMPLETE

:COMPLETE
echo '******* Tasks complete, please check logs. ********'
pause
exit

:QUITTING
echo 'You have quit the application, no action has been taken.'
pause
exit