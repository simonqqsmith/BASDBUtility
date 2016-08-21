conn / as sysdba
alter user sysman account lock;
alter user dbsnmp account lock;
alter system set job_queue_processes=0;
-- @@after_refresh.sql
-- alter system set job_queue_processes=10;
exit