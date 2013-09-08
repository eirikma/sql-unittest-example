
-- file for inserting data that should be set to the same value in all environments

delete from  PERSISTENT_PROPERTY where key='environmentLevel' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE) values ('environmentLevel', 'TEST')  ;

delete from PERSISTENT_PROPERTY where key = 'arkiv.url';
INSERT INTO PERSISTENT_PROPERTY(KEY, VALUE, server) VALUES ('arkiv.url', 'http://hm-ktapp-2-ops:30165/arkiv-rest/bbs/blankett/giro_meldinger/', 'all');


insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (18, (select sysdate from dual));

