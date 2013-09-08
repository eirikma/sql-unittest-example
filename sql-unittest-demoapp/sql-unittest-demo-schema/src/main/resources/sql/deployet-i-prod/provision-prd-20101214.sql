set serveroutput on;
set feedback on;
spool install_korreksjon.log;

define environment = prod;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

@migration/8/schema.sql;
@migration/8/users.sql;
@migration/8/grants.sql;
@migration/8/properties-&&environment..sql;
@migration/8/data.sql;

@migration/9/schema.sql;
@migration/9/indeces.sql;

--@migration/10/schema.sql;
--@migration/10/grants.sql;
--@migration/10/data.sql;

-- Logg inn med din favoritt-bruker
--connect korreksjon_app/&&korreksjon_app_pw@&&oracle_sid
alter session set current_schema=KORREKSJON_APP;

--@migration/10/synonyms.sql;

commit;
spool off;
