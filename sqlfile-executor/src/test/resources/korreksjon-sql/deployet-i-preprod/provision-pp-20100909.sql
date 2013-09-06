set serveroutput on;
set feedback on;
spool install_korreksjon.log;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/2/schema-meldingspumpe.sql;
@migration/2/schema-filarkiv.sql;
@migration/2/data.sql;
@migration/2/grants.sql;

-- Logg inn med din favoritt-bruker
--connect korreksjon_app/&&korreksjon_app_pw@&&oracle_sid
alter session set current_schema=KORREKSJON_APP;

@migration/2/synonyms.sql;

spool off;
