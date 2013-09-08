-- in lowercase to support shared-config-war
delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername1', 'hm-ppsm-2-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername2', 'r5-ppsm-2-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName1', 'hm-ppsm-2-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName2', 'r5-ppsm-2-pub.bbsas.no', 'all');


delete from  PERSISTENT_PROPERTY where key='environmentLevel' ;
insert  into PERSISTENT_PROPERTY(KEY, VALUE) values ('environmentLevel', 'PREPROD')  ;

delete from PERSISTENT_PROPERTY where key = 'driftskonsoll.authentication.environment';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('driftskonsoll.authentication.environment', 'SITEMINDER', 'all');

delete from PERSISTENT_PROPERTY where key = 'ldapPersistorRdn';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('ldapPersistorRdn', 'cn=asazapp,o=bbs', 'all');

-- huh ? burde kanskje hatt no.bbs.korreksjon.drift ?
delete from PERSISTENT_PROPERTY where key = 'drift.app.namespace';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.namespace', 'no.bbs.blankett.drift', 'all');

delete from PERSISTENT_PROPERTY where key = 'drift.app.name';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.name', 'korreksjon-appkonsoll', 'all');
