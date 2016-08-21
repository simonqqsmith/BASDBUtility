@echo off

echo '******* Check the environment variables have been set correctly in setEnv.cmd before continuing ********'
pause

:PRECHECK
echo Are the environment variables correct?
set /P CHECKRESP=Type y or n and press enter:
if %CHECKRESP%==y goto REFRESHDB
if %CHECKRESP%==n goto QUITTING
goto PRECHECK

rem set env 
call %~dp0setEnv.cmd

:REFRESHDB
rem Duplicating database
sqlplus -s /nolog @mountdb.sql '%PFILE%'
rman cmdfile=%SCRIPT_BASE%\duplicate_db_from_backup.rman '%PFILE%' '%DFNEWNAME%' %ORACLE_SID% %RECOVERYPOINT% '%RMAN_BKP%' '%DBLOGFILE1%' '%DBLOGFILE2%' log=%SCRIPT_LOGS%\refresh%RTIMESTAMP%.log
sqlplus -s /nolog @post_refresh.sql
goto COMPLETE

:COMPLETE
echo '******* Tasks complete, please check logs. ********'
pause
exit

:QUITTING
echo 'You have quit the application, no action has been taken.'
pause
exit