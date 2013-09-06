insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (3, (select sysdate from dual));

commit;
