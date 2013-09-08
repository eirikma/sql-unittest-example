insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (5, (select sysdate from dual));

commit;
