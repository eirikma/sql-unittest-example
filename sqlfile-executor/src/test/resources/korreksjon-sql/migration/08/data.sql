insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (8, (select sysdate from dual));

commit;