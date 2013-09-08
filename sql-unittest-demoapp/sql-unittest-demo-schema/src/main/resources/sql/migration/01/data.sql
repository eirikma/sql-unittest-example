insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (1, (select sysdate from dual));

commit;
