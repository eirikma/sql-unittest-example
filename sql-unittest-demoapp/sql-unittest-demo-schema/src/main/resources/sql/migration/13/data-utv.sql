
delete from PERSISTENT_PROPERTY where key = 'nibe.hoved.intervalStart';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStart','12:00','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.hoved.intervalStop';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStop','17:00','all');

delete from PERSISTENT_PROPERTY where key = 'bbsonlineBestillingServer';
-- utkommentert med vilje: Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('bbsonlineBestillingServer','http://vm-ppapp-17-ops:30392/bestilling/korreksjon/ubehandlede','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.intervalFrequency';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.intervalFrequency','2','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.morgen.intervalStart';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStart','01:00','all');

delete from PERSISTENT_PROPERTY where key = 'nibe.morgen.intervalStop';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStop','10:00','all');

delete from PERSISTENT_PROPERTY where key = 'testconfiguration.bestillingService';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('testconfiguration.bestillingService','true',null);

delete from PERSISTENT_PROPERTY where key = 'arkiv.url';
INSERT INTO PERSISTENT_PROPERTY(KEY, VALUE, server) VALUES ('arkiv.url', 'http://hm-ktapp-2-ops:30165/arkiv-rest/bbs/blankett/giro_meldinger/', 'all');

delete from PERSISTENT_PROPERTY where key = 'operatorDetails';