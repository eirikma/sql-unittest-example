
set serveroutput on;
set feedback on;
spool install_korreksjon.log;

define environment = preprod;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

-- NB! Utført manuelt --

@migration/9/schema.sql;
@migration/9/indices.sql;

commit;
spool off;

