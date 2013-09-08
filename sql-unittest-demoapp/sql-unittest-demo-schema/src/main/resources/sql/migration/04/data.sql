insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (4, (select sysdate from dual));

commit;
