grant select, insert on SCHEMA_VERSION to KORREKSJON_APPLICATION_ROLE;

grant select, update, insert, delete on PERSISTENT_PROPERTY to KORREKSJON_APPLICATION_ROLE;
grant select, insert on PERSISTENT_PROP_ENDRINGSLOGG to KORREKSJON_APPLICATION_ROLE;

grant select, update, insert on SEQUENCE_NUMBER to KORREKSJON_APPLICATION_ROLE;

grant select, update, insert on KORREKSJON to KORREKSJON_APPLICATION_ROLE;
grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;