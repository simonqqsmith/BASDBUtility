rem -----------------------------------------------------------------------
rem set generic variables
rem -----------------------------------------------------------------------
set ORACLE_SID=orcl
set ORACLE_HOME=C:\app\oracle\product\11.2.0\dbhome_1
set SCRIPT_BASE=C:\app\refresh
set SCRIPT_LOGS=C:\app\refresh\log
set NLS_DATE_FORMAT=dd-Mon-yy HH24:MI:SS


rem -----------------------------------------------------------------------
rem set hot_backup variables
rem -----------------------------------------------------------------------
set REFRESH_FRA=C:\app\oracle\flash_recovery_area\orcl\refresh

rem -----------------------------------------------------------------------
rem set snapshot variables
rem -----------------------------------------------------------------------
set RMAN_FRA=C:\app\oracle\flash_recovery_area\orcl\snapshot
set RESTORE_PFILE=C:\app\oracle\flash_recovery_area\orcl\snapshot\pfile20160328072118.ora
set RESTORE_CFILE=C:\app\oracle\flash_recovery_area\orcl\snapshot\CNTRL_19_1_907658680

rem -----------------------------------------------------------------------
rem set refresh variables
rem -----------------------------------------------------------------------
set RMAN_BKP=C:\app\oracle\flash_recovery_area\orcl
set DFNEWNAME=C:\app\oracle\oradata
set DBLOGFILE1=C:\app\oracle\oradata\dev1
set DBLOGFILE2=C:\app\oracle\oradata\dev1

rem set to a point in time after the hot backup control file creation format 'YYYYMMDDHH24MISS'
set RECOVERYPOINT=20160815104400

rem -----------------------------------------------------------------------
rem auto build variables
rem -----------------------------------------------------------------------
set PATH=%ORACLE_HOME%\bin;%PATH%
set ATIMESTAMP=%date:~10,4%%date:~7,2%%date:~4,2%%time:~0,2%%time:~3,2%%time:~6,2%
set RTIMESTAMP=%ATIMESTAMP: =0%
set PFILE=%ORACLE_HOME%\database\INIT%ORACLE_SID%.ORA

rem -----------------------------------------------------------------------
rem Change directory
rem -----------------------------------------------------------------------
cd /d %SCRIPT_BASE%