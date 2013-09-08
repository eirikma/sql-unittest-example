

-- setter testindikator i nibe-fil: SKRUS AV I PRODUKSJON!
delete from PERPERSISTENT_PROPERTY where KEY = 'testconfiguration.nibe';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.nibe', 'true', 'all');


--  THIS VALUE IS FOR NIBEADAPTER PREPROD
delete from PERPERSISTENT_PROPERTY where KEY = 'nibeadapterDestinationUrl';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('nibeadapterDestinationUrl', 'x-catssh://adapt.ssh@tbbsg-ppa-vip//G/adap02/nankorin', 'all');

