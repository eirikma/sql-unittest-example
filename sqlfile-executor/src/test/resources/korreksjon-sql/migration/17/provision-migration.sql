
-- script for å kjøre migration nr 17 i multi-schemaoppsett

define environment='prod';

alter session set current_schema='KORREKSJON';

@schema.sql;
@grants.sql;
@data.sql;
commit;


alter session set current_schema='KORREKSJON_APP';
@synonyms.sql;

alter session set current_schema='KORREKSJON';


insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (17, (select sysdate from dual));
commit;

