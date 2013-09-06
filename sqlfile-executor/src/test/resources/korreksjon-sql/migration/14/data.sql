delete from PERSISTENT_PROPERTY where key = 'testconfiguration.pibService';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.pibService', 'false', 'all');

-- no longer we include the update path when specifying the server
update PERSISTENT_PROPERTY
set VALUE='http://vm-papp-3-ops:30692'
where VALUE ='http://vm-papp-3-ops:30692/bestilling/korreksjon/ubehandlede';

insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (14, (select sysdate from dual));

commit;
