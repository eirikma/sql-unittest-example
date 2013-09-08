set serveroutput on;
set feedback on;
spool install_korreksjon.log;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/3/schema.sql;
@migration/3/data.sql;

spool off;
