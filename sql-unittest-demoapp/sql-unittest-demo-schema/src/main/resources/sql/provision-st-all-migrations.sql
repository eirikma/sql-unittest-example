/*
    Purpose:
    Provision new database for 'systemtest' (st) av Korreksjon by running through all available migration scripts
*/

set serveroutput on;
set feedback off;

-- environment variable for selecting persistent properties file to set:
-- values:  st / prod / preprod / utv
-- todo: merge with environmentLevel persistent_property
define environment = st;

alter session set current_schema=&&username;


@migration/01/schema.sql;
@migration/01/indeces.sql;
@migration/01/data.sql;

@migration/02/schema-meldingspumpe.sql;
@migration/02/schema-filarkiv.sql;
@migration/02/data.sql;

@migration/03/schema.sql;
@migration/03/data.sql;

@migration/04/schema.sql;
@migration/04/indeces.sql;
@migration/04/data.sql;

@migration/05/schema.sql;
@migration/05/data.sql;

@migration/06/data.sql;

@migration/07/data.sql;

@migration/08/schema.sql;
@migration/08/properties-&&environment.sql;
@migration/08/data.sql;

@migration/09/schema.sql;
@migration/09/indeces.sql;

@migration/10/schema.sql;

@migration/11/schema.sql;
@migration/11/data.sql;

delete from PERSISTENT_PROPERTY where KEY = 'testconfiguration.nibe';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.nibe', 'true', 'all');

delete from PERSISTENT_PROPERTY where KEY = 'nibeadapterDestinationUrl';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('nibeadapterDestinationUrl', 'file:/K/data/to_host/nibe', 'all');

-- todo: merge with psql variable 'environment'. they mean more or less the same
-- name of values to look up an no.bbs.korreksjon.EnvironmentLevelLevel instance for the application:
-- values :   TEST / PREPROD / PROD
insert  into PERSISTENT_PROPERTY(KEY, VALUE) values ('environmentLevel', 'TEST')  ;

@migration/13/schema.sql ;
@migration/13/data.sql ;

delete from PERSISTENT_PROPERTY where key = 'bbsonlineBestillingServer';
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('bbsonlineBestillingServer','http://vm-ppapp-17-ops:30392','all');

@migration/14/schema.sql;
@migration/14/data.sql;

@migration/15/schema.sql;
@migration/15/systemtest-schema.sql;

@migration/16/schema.sql;

@migration/17/schema.sql;
@migration/17/data.sql;

@migration/19/data.sql;

delete from PERSISTENT_PROPERTY where KEY = 'testconfiguration.pibService';
insert into PERSISTENT_PROPERTY(KEY, VALUE, SERVER) values('testconfiguration.pibService', 'true', 'all');


commit;
