set serveroutput on;
set feedback on;
spool install_korreksjon.log;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/4/schema.sql;
@migration/4/indeces.sql;
@migration/4/data.sql;

spool off;
