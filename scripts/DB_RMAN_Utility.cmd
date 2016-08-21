@echo off

echo '******* Check the environment variables have been set correctly in setEnv.cmd before continuing ********'
pause

:PRECHECK
echo Are the environment variables correct?
set /P CHECKRESP=Type y or n and press enter:
if %CHECKRESP%==y goto MENU
if %CHECKRESP%==n goto QUITTING
goto PRECHECK

rem set env 
call %~dp0setEnv.cmd

:MENU
echo ------------------------------------
echo select option 1,2,3 or 4 to continue
echo ------------------------------------
echo
echo 1. Create a Hot Backup
echo 2. Restore from a Hot Backup (Refresh)
echo 3. Create a Cold Backup (Snapshot)
echo 4. Restore from a Cold Backup (Reset)
echo 5. Quit
echo

set /P M=Type 1, 2, 3 or 4 and press enter:
if %M%==1 goto HOTBACKUP
if %M%==2 goto HOTRESTORE
if %M%==3 goto COLDBACKUP
if %M%==4 goto COLDRESTORE
if %M%==5 goto QUITTING
goto MENU

:HOTBACKUP
rem Hot Backup
call hot_backup.cmd
goto COMPLETE

:HOTRESTORE
rem Refresh
call refresh.cmd
goto COMPLETE

:COLDBACKUP
rem Snapshot
call snapshot.cmd
goto COMPLETE

:COLDRESTORE
rem Reset
call restore_snapshot.cmd
goto COMPLETE

:COMPLETE
echo '******* Tasks complete, please check logs. ********'
pause
exit

:QUITTING
echo 'You have quit the application, no action has been taken.'
pause
exit
