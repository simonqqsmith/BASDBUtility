set serverout on 
spool presql.log
DECLARE
vc_logmode VARCHAR2(20);
BEGIN
SELECT log_mode INTO vc_logmode FROM v$database;
IF vc_logmode='NOARCHIVELOG' THEN
BEGIN
dbms_output.put_line('setting ARCHIVELOG on');
execute immediate 'shutdown immediate';
execute immediate 'startup mount';
execute immediate 'alter database archivelog';
execute immediate 'alter database open';
END:
ELSE
dbms_output.put_line('already in ARCHIVELOG mode');
END IF;
END;
/
spool off