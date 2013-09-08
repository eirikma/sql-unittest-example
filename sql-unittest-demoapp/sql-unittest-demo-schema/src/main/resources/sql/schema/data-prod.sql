
-- file for inserting data that should only be set in prod

delete from  PERSISTENT_PROPERTY where key='environmentLevel' ;
insert  into PERSISTENT_PROPERTY(KEY, VALUE) values ('environmentLevel', 'PROD')  ;

delete from PERSISTENT_PROPERTY where key = 'arkiv.url';
INSERT INTO PERSISTENT_PROPERTY(KEY, VALUE, server) VALUES ('arkiv.url', 'https://arkiv.bbs.no/arkiv-rest/bbs/blankett/giro_meldinger/', 'all');

delete from PERSISTENT_PROPERTY where key = 'arkiv.keystore.location';
INSERT INTO PERSISTENT_PROPERTY(KEY, VALUE, server) VALUES ('arkiv.keystore.location', '/opt/korreksjon/prd/korreksjon-server/blankett_181209_075344_11013-EK-1-1155176000_Auth.p12', 'all');

delete from  PERSISTENT_PROPERTY where key='testconfiguration.nibe' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.nibe', 'false', 'all');

delete from PERSISTENT_PROPERTY where key = 'testconfiguration.pibService';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.pibService', 'false', 'all');

delete from PERSISTENT_PROPERTY where key = 'driftskonsoll.authentication.environment';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('driftskonsoll.authentication.environment', 'SITEMINDER', 'all');

delete from PERSISTENT_PROPERTY where key = 'ldapPersistorRdn';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('ldapPersistorRdn', 'cn=asazapp,o=bbs', 'all');

-- in lowercase to support shared-config-war
delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername1', 'hm-psm-1-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername2', 'r5-psm-2-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName1', 'hm-psm-1-pub.bbsas.no', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName2', 'r5-psm-2-pub.bbsas.no', 'all');

-- huh? dette ser ikke riktig ut?
delete from PERSISTENT_PROPERTY where key = 'drift.app.namespace';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.namespace', 'no.bbs.blankett.drift', 'all');

delete from PERSISTENT_PROPERTY where key = 'drift.app.name';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.name', 'korreksjon-appkonsoll', 'all');

-- menu elements for core-proxy
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (1,1,'Kø','/korreksjon-core-proxy/',null,1,'Ko','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (2,2,'Oversikt','/korreksjon/managedjobs.do?maxCount=100',null,2,'Blankett','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (3,3,'Konfigurasjon','/korreksjon-shared-config/',null,3,'Config','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (4,4,'Helsestatus','/korreksjon/healthcheck.do',null,4,'Blankett','no.bbs.blankett.drift','read',null);

-- configuration for core-proxy and shared-config
delete from PERSISTENT_PROPERTY where key = 'environment';
insert into persistent_property (key, value) values ('environment', 'P');
delete from PERSISTENT_PROPERTY where key = 'core/proxy/war/environment';
insert into persistent_property (key, value) values ('core/proxy/war/environment', 'P');
