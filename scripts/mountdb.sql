conn / as sysdba
shutdown immediate;
startup nomount pfile='&1';
alter system set job_queue_processes=0 scope=memory;
exit