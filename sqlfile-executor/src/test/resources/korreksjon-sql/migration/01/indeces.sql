drop index KORREKSJON_TYPE_IDX;
create index KORREKSJON_TYPE_IDX on KORREKSJON(KORREKSJON_TYPE);

drop index KORREKSJON_STATUS_IDX;
create index KORREKSJON_STATUS_IDX on KORREKSJON(STATUS);

drop index KORREKSJON_GROUP_ID_IDX;
create index KORREKSJON_GROUP_ID_IDX on KORREKSJON(BESTILLINGSNUMMER, BESTILLINGSDATO);