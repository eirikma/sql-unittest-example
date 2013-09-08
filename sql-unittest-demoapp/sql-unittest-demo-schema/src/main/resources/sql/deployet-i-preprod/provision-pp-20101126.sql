
set serveroutput on;
set feedback on;
spool install_korreksjon.log;

define environment = preprod;

-- Logg inn med din favoritt-bruker
--connect korreksjon/&&korreksjon_pw@&&oracle_sid
alter session set current_schema=KORREKSJON;

-- NB! migration 6 og 7 er utført manuelt på PP

@migration/8/schema.sql;
@migration/8/users.sql;
@migration/8/grants.sql;
@migration/8/properties-&&environment..sql;
@migration/8/data.sql;

commit;
spool off;

