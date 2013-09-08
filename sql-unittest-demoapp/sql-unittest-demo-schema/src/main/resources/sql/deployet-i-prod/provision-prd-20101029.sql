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

@migration/2/schema-meldingspumpe.sql;
@migration/2/schema-filarkiv.sql;
@migration/2/data.sql;
@migration/2/grants.sql;

@migration/3/schema.sql;
@migration/3/data.sql;

@migration/4/schema.sql;
@migration/4/indeces.sql;
@migration/4/data.sql;

@migration/5/schema.sql;
@migration/5/data.sql;

@migration/6/data.sql;

@migration/7/data.sql;
@migration/7/grants.sql;

-- Logg inn med din favoritt-bruker
--connect korreksjon_app/&&korreksjon_app_pw@&&oracle_sid
alter session set current_schema=KORREKSJON_APP;

@migration/1/synonyms.sql;
@migration/2/synonyms.sql;
@migration/6/synonyms.sql;

spool off;
