set serveroutput on;
set feedback on;
spool install_korreksjon.log;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/5/schema.sql;
@migration/5/data.sql;

spool off;

-- NB! migration 6 og 7 er utført manuelt på PP
