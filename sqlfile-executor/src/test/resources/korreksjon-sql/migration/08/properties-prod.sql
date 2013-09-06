


-- setter testindikator i nibe-fil: SKRUS AV I PRODUKSJON!
delete from PERPERSISTENT_PROPERTY where KEY = 'testconfiguration.nibe';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.nibe', 'false', 'all');

--  THIS VALUE IS FOR NIBEADAPTER PROD
delete from PERPERSISTENT_PROPERTY where KEY = 'nibeadapterDestinationUrl';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('nibeadapterDestinationUrl', 'x-catssh://adapt.ssh@tbbsh-pa-vip//G/adap02/nankorin', 'all');
