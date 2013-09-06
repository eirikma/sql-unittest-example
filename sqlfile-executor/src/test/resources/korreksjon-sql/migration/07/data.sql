insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (7, (select sysdate from dual));

commit;
