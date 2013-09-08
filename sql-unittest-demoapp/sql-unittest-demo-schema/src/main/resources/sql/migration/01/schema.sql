drop table SCHEMA_VERSION;
create table SCHEMA_VERSION (
    VERSION number(10,0) not null,
    MIGRATION_DATE date not null,
    unique (VERSION)
);

drop table PERSISTENT_PROPERTY;
create table PERSISTENT_PROPERTY (
    KEY varchar(255) not null,
    VALUE varchar(255) not null,
    SERVER varchar(255) default 'all',
    unique (KEY, SERVER)
);

drop table PERSISTENT_PROP_ENDRINGSLOGG;
create table PERSISTENT_PROP_ENDRINGSLOGG (
    ID number(19) constraint NN_PERS_PROP_ENDRINGSLOGG_547 not null,
    KEY varchar2(255),
    SERVER varchar2(255),
    OLDVALUE varchar2(255),
    NEWVALUE varchar2(255),
    TID timestamp(6),
    USERNAME varchar2(255 char),
    constraint PK_PERS_PROP_ENDRINGSLOGG_547 primary key (ID) using index(create unique index IX_PERS_PROP_ENDRINGSLOGG_547 on PERSISTENT_PROP_ENDRINGSLOGG(ID))
);

drop table SEQUENCE_NUMBER cascade constraints;
create table SEQUENCE_NUMBER (
    SEQUENCE_KEY varchar2(255 char) not null,
    SEQUENCE_NUMBER varchar2(255 char),
    constraint PK_SEQUENCE_NUMBER primary key (SEQUENCE_KEY)
);

drop table KORREKSJON cascade constraints;
create table KORREKSJON (
    ID number(19,0) not null,
    VERSION number(10,0),
    KORREKSJON_TYPE varchar2(255 char) not null,
    BESTILLINGSNUMMER varchar2(255 char) not null,
    BESTILLINGSDATO date not null,
    CREATED_AT timestamp not null,
    CREATED_BY_USER varchar2(255 char) not null,
    STATUS varchar2(255 char) not null,
    DEBET_ACCOUNT varchar2(255 char) not null,
    CREDIT_ACCOUNT varchar2(255 char) not null,
    AMOUNT number(19,2) not null,
    BBS_DATO date not null,
    CAUSE varchar2(255 char) not null,
    FRITEKST varchar2(255 char),
    GEBYR_BANKREGNUMMER varchar2(255 char),
    constraint PK_KORREKSJON primary key (ID),
    constraint UNIQUE_KORREKSJON unique (KORREKSJON_TYPE, BESTILLINGSNUMMER, BESTILLINGSDATO, STATUS)
);

drop sequence KORREKSJON_SEQUENCE;
create sequence KORREKSJON_SEQUENCE;
