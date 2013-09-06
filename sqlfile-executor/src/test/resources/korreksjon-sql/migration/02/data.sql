insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (2, (select sysdate from dual));

commit;
