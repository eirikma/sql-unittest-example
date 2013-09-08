
delete from PERSISTENT_PROPERTY where key = 'nibe.hoved.intervalStart';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStart','14:30','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.hoved.intervalStop';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStop','15:25','all');

delete from PERSISTENT_PROPERTY where key = 'bbsonlineBestillingServer';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('bbsonlineBestillingServer','http://vm-papp-3-ops:30692/bestilling/korreksjon/ubehandlede','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.intervalFrequency';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.intervalFrequency','5','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.morgen.intervalStart';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStart','01:00','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.morgen.intervalStop';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStop','05:00','all');

delete from PERSISTENT_PROPERTY where key = 'testconfiguration.bestillingService';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('testconfiguration.bestillingService','false',null);

delete from PERSISTENT_PROPERTY where key = 'arkiv.url';
INSERT INTO PERSISTENT_PROPERTY(KEY, VALUE, server) VALUES ('arkiv.url', 'https://arkiv.bbs.no/arkiv-rest/bbs/blankett/giro_meldinger/', 'all');
