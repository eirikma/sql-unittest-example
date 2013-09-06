
-- unique indices are part of the schema, and are found in schema.sql

create index KORR_SUCCESSOR_IDX on KORREKSJON(FORRIGE_ID);

create index KORR_CREATED_AT_IDX on KORREKSJON(CREATED_AT);

create index KORR_BESTILLINGSDATO_IDX on KORREKSJON(BESTILLINGSDATO);

create index KORR_BESTILLINGSNUMMER_IDX on KORREKSJON(BESTILLINGSNUMMER);


-- VALGT DETTE BORT til fordel for dobbelt lenket liste
--------------------------------------------------------------
-- create unique index KORREKSJON_SINGLE_EDIT_IDX on KORREKSJON(STATUS, STATUS_NUMBER)
--------------------------------------------------------------




