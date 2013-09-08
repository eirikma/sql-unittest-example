set serveroutput on;
set feedback on;
spool install_korreksjon.log;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/1/schema.sql;
@migration/1/indeces.sql;
@migration/1/data.sql;
@migration/1/grants.sql;

-- Logg inn med din favoritt-bruker
--connect korreksjon_app/&&korreksjon_app_pw@&&oracle_sid
alter session set current_schema=KORREKSJON_APP;

@migration/1/synonyms.sql;

spool off;
