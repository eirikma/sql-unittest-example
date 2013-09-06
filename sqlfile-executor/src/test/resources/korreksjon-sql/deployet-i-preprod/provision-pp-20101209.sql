
set serveroutput on;
set feedback on;
spool install_korreksjon.log;

define environment = preprod;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

-- NB! Utført manuelt --

@migration/10/schema.sql;
@migration/10/grants.sql;

alter session set current_schema=KORREKSJON_APP;

@migration/10/synonyms.sql;

commit;
spool off;

