
-- file for inserting data that should only be set in systemtest

delete from PERSISTENT_PROPERTY where key='environmentLevel' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE) values ('environmentLevel', 'TEST')  ;

delete from PERSISTENT_PROPERTY where key='testconfiguration.nibe' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.nibe', 'true', 'all');

delete from PERSISTENT_PROPERTY where key='nibeadapterDestinationUrl' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('nibeadapterDestinationUrl', 'file:/K/data/to_host/nibe', 'all');

delete from PERSISTENT_PROPERTY where key='testconfiguration.bankService' ;
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.bankService', 'false', 'all');

delete from PERSISTENT_PROPERTY where key = 'arkiv.url';

delete from PERSISTENT_PROPERTY where key = 'testconfiguration.pibService';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.pibService', 'false', 'all');

-- in lowercase to support shared-config-war
delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername1', 'hm-stsm-1-pub', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazpolicyservername2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazpolicyservername2', 'hm-stsm-1-pub', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName1';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName1', 'hm-stsm-1-pub', 'all');

delete from PERSISTENT_PROPERTY where key = 'asazPolicyServerName2';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('asazPolicyServerName2', 'hm-stsm-1-pub', 'all');

delete from PERSISTENT_PROPERTY where key = 'driftskonsoll.authentication.environment';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('driftskonsoll.authentication.environment', 'TEST', 'all');

delete from PERSISTENT_PROPERTY where key = 'ldapPersistorRdn';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('ldapPersistorRdn', 'cn=asazapp,o=bbs', 'all');

-- skulle nok hatt no.bbs.korreksjon.drift, men det er nok ikke opprettet (hvor det naa enn skal opprettes)?
delete from PERSISTENT_PROPERTY where key = 'drift.app.namespace';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.namespace', 'no.bbs.blankett.drift', 'all');

delete from PERSISTENT_PROPERTY where key = 'drift.app.name';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('drift.app.name', 'korreksjon-appkonsoll', 'all');


-- k�er

INSERT INTO WORK_QUEUE(ID,ENTITY_SERVICE_NAME,CONSUMER_ID,STATUS,START_TIME,SORT,BESKRIVELSE,WORK_QUEUE_GROUP) VALUES (1,'nibeManager','meldingspumpa',0,null,0,null,null);
INSERT INTO WORK_QUEUE(ID,ENTITY_SERVICE_NAME,CONSUMER_ID,STATUS,START_TIME,SORT,BESKRIVELSE,WORK_QUEUE_GROUP) VALUES (2,'nibeFilTransferService','meldingspumpa',0,null,0,null,null);
INSERT INTO WORK_QUEUE(ID,ENTITY_SERVICE_NAME,CONSUMER_ID,STATUS,START_TIME,SORT,BESKRIVELSE,WORK_QUEUE_GROUP) VALUES (3,'bestillingsDbStatusOppdateringService','meldingspumpa',0,null,0,null,null);

-- Add groups for core-proxy and shared-config apps

INSERT INTO PERSISTENT_PROPERTY_GROUP (ID, NAVN, SORTERING) VALUES (1, 'Korreksjon', 1);
UPDATE PERSISTENT_PROPERTY set PERSISTENT_PROPERTY_GROUP = 1 where PERSISTENT_PROPERTY_GROUP is NULL;

INSERT INTO WORK_QUEUE_GROUP (ID, GROUP_NAME, SORT) VALUES (1, 'Korreksjon', 1);
UPDATE WORK_QUEUE set WORK_QUEUE_GROUP = 1 where WORK_QUEUE_GROUP is NULL;
