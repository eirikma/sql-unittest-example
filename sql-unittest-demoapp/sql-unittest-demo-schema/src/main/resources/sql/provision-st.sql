/*
    Purpose:
    Provision new database for 'systemtest' (st) av Korreksjon by setting up the standard schema
*/
set serveroutput on;
set feedback off;

-- environment variable for selecting persistent properties file to set:
-- values:  st / prod / preprod / utv
-- todo: merge with environmentLevel persistent_property
define environment = st;

-- connect &&username/&&password@&&oracle_sid
alter session set current_schema=&&username;

@schema/external/fakturering/fakt-drop-tables.sql;
@schema/drop-schema.sql;

@provision-singleuser.sql;

commit;
