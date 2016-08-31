conn / as sysdba
shutdown immediate;
startup nomount pfile='&1';
exit