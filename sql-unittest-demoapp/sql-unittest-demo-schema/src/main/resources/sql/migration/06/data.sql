insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (6, (select sysdate from dual));

commit;
