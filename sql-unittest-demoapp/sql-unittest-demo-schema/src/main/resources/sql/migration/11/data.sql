insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (11, (select sysdate from dual));

commit;
