insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (10, (select sysdate from dual));

commit;
